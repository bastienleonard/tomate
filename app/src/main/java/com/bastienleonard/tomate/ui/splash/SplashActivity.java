package com.bastienleonard.tomate.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.bastienleonard.tomate.BaseActivity;
import com.bastienleonard.tomate.TomateApp;
import com.bastienleonard.tomate.models.Task;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.trello.TrelloLoginActivity;
import com.bastienleonard.tomate.persistence.TasksLoader;
import com.bastienleonard.tomate.trello.TrelloCredentials;
import com.bastienleonard.tomate.ui.tasks.TasksActivity;
import com.bastienleonard.tomate.ui.trellosetup.SetupActivity;

import java.util.List;

public final class SplashActivity extends BaseActivity implements Handler.Callback, LoaderManager.LoaderCallbacks<List<Task>> {
    private static final int WHAT_CONTINUE = 1;
    private static final String STATE_REMAINING = "remaining";
    private static final String STATE_TASKS_LOADED = "tasksLoaded";
    private static final int TASKS_LOADER_ID = 1;

    private Handler mHandler;
    private long mRemaining = 2 * 1000L; // Minimum duration during which the splash should be shown
    private long mLastTick;
    private boolean mTasksLoaded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        if (savedInstanceState != null) {
            mRemaining = savedInstanceState.getLong(STATE_REMAINING);
            mTasksLoaded = savedInstanceState.getBoolean(STATE_TASKS_LOADED);
        }

        mHandler = new Handler(this);
        getSupportLoaderManager().initLoader(TASKS_LOADER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mRemaining > 0L) {
            mHandler.sendEmptyMessageDelayed(WHAT_CONTINUE, mRemaining);
        } else {
            goNext(this);
        }

        mLastTick = SystemClock.elapsedRealtime();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
        long currentTick = SystemClock.elapsedRealtime();
        mRemaining -= (SystemClock.elapsedRealtime() - mLastTick);
        mLastTick = currentTick;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        long currentTick = SystemClock.elapsedRealtime();
        mRemaining -= (SystemClock.elapsedRealtime() - mLastTick);
        mLastTick = currentTick;
        outState.putLong(STATE_REMAINING, mRemaining);
        outState.putBoolean(STATE_TASKS_LOADED, mTasksLoaded);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case WHAT_CONTINUE:
                if (mTasksLoaded) {
                    goNext(this);
                }

                return true;
            default:
                return false;
        }
    }

    @Override
    public Loader<List<Task>> onCreateLoader(int id, Bundle args) {
        return new TasksLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Task>> loader, List<Task> tasks) {
        if (tasks != null) {
            TomateApp.get().getTaskCache().setTasks(tasks);
        }

        mTasksLoaded = true;

        if (mRemaining < 0L) {
            goNext(this);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Task>> loader) {
    }

    private static void goNext(Activity activity) {
        if (TextUtils.isEmpty(TrelloCredentials.getPersistedToken(activity))) {
            activity.startActivity(new Intent(activity, TrelloLoginActivity.class));
        } else if (SetupActivity.trelloFullySetup(activity)) {
            activity.startActivity(new Intent(activity, TasksActivity.class));
        } else {
            activity.startActivity(new Intent(activity, SetupActivity.class));
        }

        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
