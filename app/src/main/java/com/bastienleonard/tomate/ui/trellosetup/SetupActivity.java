package com.bastienleonard.tomate.ui.trellosetup;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.bastienleonard.tomate.BaseActivity;
import com.bastienleonard.tomate.Persistence;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.trello.loaders.BoardsLoader;
import com.bastienleonard.tomate.trello.loaders.ListsLoader;
import com.bastienleonard.tomate.trello.models.Board;
import com.bastienleonard.tomate.trello.models.TrelloList;
import com.bastienleonard.tomate.util.LogUtil;

import java.util.List;

// FIXME: handle back press correctly
// FIXME: show loading animations
// FIXME: don't allow selecting the same list in different fragments
// FIXME: save state for lists  IDs
public final class SetupActivity extends BaseActivity implements OnItemPickedListener {
    private enum Step implements Parcelable {
        BOARD,
        TO_DO,
        DOING,
        DONE;

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }
    }

    private static final String TAG = "SetupActivity";
    private static final int BOARDS_LOADER_ID = 1;
    private static final int LISTS_LOADER_ID = 2;

    private static final String BOARDS_TAG = "boards";
    private static final String TO_DO_TAG = "todo";
    private static final String DOING_TAG = "doing";
    private static final String DONE_TAG = "done";

    private static final String STATE_BOARD_ID = "boardId";
    private static final String STATE_STEP = "step";

    private Step mCurrentStep = Step.BOARD;
    private String mBoardId;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_activity);
        setupToolbar();
        getSupportLoaderManager().initLoader(BOARDS_LOADER_ID, null, new BoardsLoaderCallbacks());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, BoardPickerFragment.newInstance(), BOARDS_TAG)
                    .commit();
        } else {
            mBoardId = savedInstanceState.getString(STATE_BOARD_ID);
            mCurrentStep = savedInstanceState.getParcelable(STATE_STEP);
        }

        mHandler = new Handler();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_BOARD_ID, mBoardId);
        outState.putParcelable(STATE_STEP, mCurrentStep);
    }

    @Override
    public void onBoardPicked(Board board) {
        mBoardId = board.getId();

        if (Persistence.saveBoardId(this, mBoardId)) {
            updateCurrentStep(Step.TO_DO);
            LogUtil.i(TAG, "User picked board " + board);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ToDoListPickerFragment.newInstance(), TO_DO_TAG)
                    .addToBackStack(null)
                    .commit();
            getSupportLoaderManager().initLoader(LISTS_LOADER_ID, null, new ListsLoaderCallbacks());
        } else {
            // FIXME: handle error
            LogUtil.e(TAG, "Failed to save board ID for " + board);
        }
    }

    @Override
    public void onTodoListPicked(TrelloList list) {
        if (Persistence.saveToDoListId(this, list.getId())) {
            updateCurrentStep(Step.DOING);
            LogUtil.i(TAG, "User picked to-do list " + list);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DoingListPickerFragment.newInstance(), DOING_TAG)
                    .addToBackStack(null)
                    .commit();
            getSupportLoaderManager().initLoader(LISTS_LOADER_ID, null, new ListsLoaderCallbacks());
        } else {
            // FIXME: handle error
            LogUtil.e(TAG, "Failed to save todo list ID for " + list);
        }
    }

    @Override
    public void onDoingListPicked(TrelloList list) {
        if (Persistence.saveDoingListId(this, list.getId())) {
            updateCurrentStep(Step.DONE);
            LogUtil.i(TAG, "User picked doing list " + list);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DoneListPickerFragment.newInstance(), DONE_TAG)
                    .addToBackStack(null)
                    .commit();
            getSupportLoaderManager().initLoader(LISTS_LOADER_ID, null, new ListsLoaderCallbacks());

        } else {
            // FIXME: handle error
            LogUtil.e(TAG, "Failed to save doing list ID for " + list);
        }
    }

    @Override
    public void onDoneListPicked(TrelloList list) {
        if (Persistence.saveDoneListId(this, list.getId())) {
            LogUtil.i(TAG, "User picked done list " + list);
        } else {
            // FIXME: handle error
            LogUtil.e(TAG, "Failed to save done list ID for " + list);
        }
    }

    private BoardPickerFragment getBoardsFragment() {
        return (BoardPickerFragment) findFragment(BOARDS_TAG);
    }

    private ToDoListPickerFragment getToDoFragment() {
        return (ToDoListPickerFragment) findFragment(TO_DO_TAG);
    }

    private DoingListPickerFragment getDoingFragment() {
        return (DoingListPickerFragment) findFragment(DOING_TAG);
    }

    private DoneListPickerFragment getDoneFragment() {
        return (DoneListPickerFragment) findFragment(DONE_TAG);
    }

    private BasePickerFragment<?> findFragment(String tag) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
        BasePickerFragment<?> result = null;

        if (f != null) {
            result = (BasePickerFragment<?>) f;
        }

        return result;
    }

    private void updateCurrentStep(Step newStep) {
        LogUtil.i(TAG, mCurrentStep + " -> " + newStep);
        mCurrentStep = newStep;
    }

    private final class BoardsLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Board>> {
        @Override
        public Loader<List<Board>> onCreateLoader(int id, Bundle args) {
            return new BoardsLoader(SetupActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<Board>> loader, final List<Board> data) {
            if (data == null) {
                // TODO
            } else if (data.size() == 0) {
                // TODO
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
                // TODO
            } else if (data.size() == 0) {
                // TODO
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        BasePickerFragment<TrelloList> f;

                        switch (mCurrentStep) {
                            case TO_DO:
                                f = getToDoFragment();
                                break;
                            case DOING:
                                f = getDoingFragment();
                                break;
                            case DONE:
                                f = getDoneFragment();
                                break;
                            default:
                                throw new AssertionError();
                        }

                        f.display(data);
                    }
                });
            }
        }

        @Override
        public void onLoaderReset(Loader<List<TrelloList>> loader) {
        }
    }
}
