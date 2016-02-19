package com.bastienleonard.tomate.ui.trellosetup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.bastienleonard.tomate.BaseActivity;
import com.bastienleonard.tomate.persistence.Facade;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.trello.loaders.BoardsLoader;
import com.bastienleonard.tomate.trello.loaders.ListsLoader;
import com.bastienleonard.tomate.trello.models.Board;
import com.bastienleonard.tomate.trello.models.TrelloList;
import com.bastienleonard.tomate.ui.tasks.TasksActivity;
import com.bastienleonard.tomate.ui.trellosetup.fragments.BasePickerFragment;
import com.bastienleonard.tomate.ui.trellosetup.fragments.BoardPickerFragment;
import com.bastienleonard.tomate.ui.trellosetup.fragments.DoingListPickerFragment;
import com.bastienleonard.tomate.ui.trellosetup.fragments.DoneListPickerFragment;
import com.bastienleonard.tomate.ui.trellosetup.fragments.ToDoListPickerFragment;
import com.bastienleonard.tomate.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SetupActivity extends BaseActivity implements OnItemPickedListener {
    private enum Step implements Parcelable {
        BOARD(R.string.board_label),
        TO_DO(R.string.to_do_label),
        DOING(R.string.doing_label),
        DONE(R.string.done_label);

        public static final Creator<Step> CREATOR = new Creator<Step>() {
            @Override
            public Step createFromParcel(Parcel in) {
                return Step.values()[in.readInt()];
            }

            @Override
            public Step[] newArray(int size) {
                return new Step[size];
            }
        };

        private final int mTitle;

        private Step(int title) {
            mTitle = title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(ordinal());
        }

        public String getTag() {
            return toString();
        }

        public String getTitle(Context context) {
            return context.getString(mTitle);
        }
    }

    private static final String TAG = "SetupActivity";
    private static final int BOARDS_LOADER_ID = 1;
    private static final int LISTS_LOADER_ID = 2;

    private static final String STATE_BOARD_ID = "boardId";
    private static final String STATE_TO_DO_ID = "toDoId";
    private static final String STATE_DOING_ID = "doingId";
    private static final String STATE_DONE_ID = "doneId";
    private static final String STATE_STEP = "step";

    private CoordinatorLayout mCoordinator;

    private Step mCurrentStep = Step.BOARD;
    private String mBoardId;
    private String mToDoId;
    private String mDoingId;
    private String mDoneId;
    private Handler mHandler;

    public static boolean trelloFullySetup(Context context) {
        return !TextUtils.isEmpty(Facade.getBoardId(context)) &&
                !TextUtils.isEmpty(Facade.getToDoListId(context)) &&
                !TextUtils.isEmpty(Facade.getDoingListId(context)) &&
                !TextUtils.isEmpty(Facade.getDoneListId(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_activity);
        setupToolbar();
        mCoordinator = (CoordinatorLayout) findViewById(R.id.coordinator);
        requestBoards();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, BoardPickerFragment.newInstance(), Step.BOARD.getTag())
                    .commit();
        } else {
            mBoardId = savedInstanceState.getString(STATE_BOARD_ID);
            mToDoId = savedInstanceState.getString(STATE_TO_DO_ID);
            mDoingId = savedInstanceState.getString(STATE_DOING_ID);
            mDoneId = savedInstanceState.getString(STATE_DONE_ID);
            mCurrentStep = savedInstanceState.getParcelable(STATE_STEP);
        }

        mHandler = new Handler();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_BOARD_ID, mBoardId);
        outState.putString(STATE_TO_DO_ID, mToDoId);
        outState.putString(STATE_DOING_ID, mDoingId);
        outState.putString(STATE_DONE_ID, mDoneId);
        outState.putParcelable(STATE_STEP, mCurrentStep);
    }

    public void requestBoards() {
        updateCurrentStep(Step.BOARD);
        getSupportLoaderManager().initLoader(BOARDS_LOADER_ID, null, new BoardsLoaderCallbacks());
    }

    public void requestToDo() {
        updateCurrentStep(Step.TO_DO);
        getSupportLoaderManager().initLoader(LISTS_LOADER_ID, null, new ListsLoaderCallbacks());
    }

    public void requestDoing() {
        updateCurrentStep(Step.DOING);
        getSupportLoaderManager().initLoader(LISTS_LOADER_ID, null, new ListsLoaderCallbacks());
    }

    @Override
    public void onBoardPicked(Board board) {
        mBoardId = board.getId();

        if (Facade.saveBoardId(this, mBoardId)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ToDoListPickerFragment.newInstance(), Step.TO_DO.getTag())
                    .addToBackStack(null)
                    .commit();
            requestToDo();
            LogUtils.i(TAG, "User picked board " + board);
        } else {
            LogUtils.e(TAG, "Failed to save board ID for " + board);
        }
    }

    @Override
    public void onTodoListPicked(TrelloList list) {
        mToDoId = list.getId();

        if (Facade.saveToDoListId(this, mToDoId)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DoingListPickerFragment.newInstance(), Step.DOING.getTag())
                    .addToBackStack(null)
                    .commit();
            requestDoing();
            LogUtils.i(TAG, "User picked to-do list " + list);
        } else {
            LogUtils.e(TAG, "Failed to save todo list ID for " + list);
        }
    }

    @Override
    public void onDoingListPicked(TrelloList list) {
        mDoingId = list.getId();

        if (Facade.saveDoingListId(this, mDoingId)) {
            updateCurrentStep(Step.DONE);
            LogUtils.i(TAG, "User picked doing list " + list);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DoneListPickerFragment.newInstance(), Step.DONE.getTag())
                    .addToBackStack(null)
                    .commit();
            getSupportLoaderManager().initLoader(LISTS_LOADER_ID, null, new ListsLoaderCallbacks());

        } else {
            LogUtils.e(TAG, "Failed to save doing list ID for " + list);
        }
    }

    @Override
    public void onDoneListPicked(TrelloList list) {
        mDoneId = list.getId();

        if (Facade.saveDoneListId(this, mDoneId)) {
            LogUtils.i(TAG, "User picked done list " + list);
        } else {
            LogUtils.e(TAG, "Failed to save done list ID for " + list);
        }

        startActivity(new Intent(this, TasksActivity.class));
        finish();
    }

    private BoardPickerFragment getBoardsFragment() {
        return (BoardPickerFragment) findFragment(Step.BOARD);
    }

    private ToDoListPickerFragment getToDoFragment() {
        return (ToDoListPickerFragment) findFragment(Step.TO_DO);
    }

    private DoingListPickerFragment getDoingFragment() {
        return (DoingListPickerFragment) findFragment(Step.DOING);
    }

    private DoneListPickerFragment getDoneFragment() {
        return (DoneListPickerFragment) findFragment(Step.DONE);
    }

    private BasePickerFragment<TrelloList> getListFragment(Step step) {
        return (BasePickerFragment<TrelloList>) findFragment(step);
    }

    private BasePickerFragment<?> findFragment(Step step) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(step.getTag());
        BasePickerFragment<?> result = null;

        if (f != null) {
            result = (BasePickerFragment<?>) f;
        }

        return result;
    }

    private void updateCurrentStep(Step newStep) {
        LogUtils.i(TAG, mCurrentStep + " -> " + newStep);
        mCurrentStep = newStep;
        getSupportActionBar().setTitle(mCurrentStep.getTitle(this));
    }

    private final class BoardsLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Board>> {
        @Override
        public Loader<List<Board>> onCreateLoader(int id, Bundle args) {
            return new BoardsLoader(SetupActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<Board>> loader, final List<Board> data) {
            if (data == null) {
                Snackbar.make(mCoordinator, R.string.error_loading_boards, Snackbar.LENGTH_LONG).show();
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getBoardsFragment().display(data);
                    }
                });
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Board>> loader) {
        }
    }

    private final class ListsLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<TrelloList>> {
        @Override
        public Loader<List<TrelloList>> onCreateLoader(int id, Bundle args) {
            return new ListsLoader(SetupActivity.this, mBoardId);
        }

        @Override
        public void onLoadFinished(Loader<List<TrelloList>> loader, final List<TrelloList> data) {
            if (data == null) {
                Snackbar.make(mCoordinator, R.string.error_loading_lists, Snackbar.LENGTH_LONG).show();
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        BasePickerFragment<TrelloList> f;
                        List<TrelloList> filtered;
                        Set<String> filteredIds = new HashSet<>();

                        switch (mCurrentStep) {
                            case DOING:
                                filteredIds.add(mToDoId);
                                break;
                            case DONE:
                                filteredIds.add(mToDoId);
                                filteredIds.add(mDoingId);
                                break;
                        }

                        f = getListFragment(mCurrentStep);

                        if (filteredIds.size() == 0) {
                            filtered = data;
                        } else {
                            filtered = new ArrayList<>(data.size() - filteredIds.size());

                            for (TrelloList l: data) {
                                if (!filteredIds.contains(l.getId())) {
                                    filtered.add(l);
                                }
                            }
                        }

                        f.display(filtered);
                    }
                });
            }
        }

        @Override
        public void onLoaderReset(Loader<List<TrelloList>> loader) {
        }
    }
}
