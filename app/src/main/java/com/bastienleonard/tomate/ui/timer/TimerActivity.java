package com.bastienleonard.tomate.ui.timer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bastienleonard.tomate.BaseActivity;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.models.Task;
import com.bastienleonard.tomate.persistence.UpdateTaskLoader;

import java.util.Locale;

public final class TimerActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {
    private static final String TAG = "TimerActivity";
    private static final String EXTRA_CARD_ID = "cardId";
    private static final String EXTRA_TASK = "task";
    private static final int WHAT_UPDATE_TIMER = 1;
    private static final long REFRESH_DELAY = 500L;
    private static final long POMODORO_DURATION = 25 * 60 * 1000L;

    private static final String STATE_CARD_ID = "cardId";
    private static final String STATE_TASK = "task";
    private static final String STATE_RUNNING = "running";
    private static final String STATE_REMAINING_TIME = "remainingTime";
    private static final String STATE_LAST_TICK = "lastTick";

    private static final int TASK_UPDATE_LOADER_ID = 1;

    private Handler mHandler;

    private String mCardId;
    private Task mTask;
    private boolean mRunning;
    private long mRemainingTime;
    private long mLastTick;

    private CoordinatorLayout mCoordinator;
    private TextView mTime;
    private Button mPauseResume;
    private Button mStop;

    public static void fillIntent(Intent intent, String cardId, Task task) {
        intent.putExtra(EXTRA_CARD_ID, cardId);
        intent.putExtra(EXTRA_TASK, task);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        setupToolbar();

        if (savedInstanceState == null) {
            mCardId = getIntent().getStringExtra(EXTRA_CARD_ID);
            mTask = getIntent().getParcelableExtra(EXTRA_TASK);
            mRunning = true;
            mRemainingTime = POMODORO_DURATION;
            mLastTick = SystemClock.elapsedRealtime();
        } else {
            mCardId = savedInstanceState.getString(STATE_CARD_ID);
            mTask = savedInstanceState.getParcelable(STATE_TASK);
            mRunning = savedInstanceState.getBoolean(STATE_RUNNING);
            mRemainingTime = savedInstanceState.getLong(STATE_REMAINING_TIME);
            mLastTick = savedInstanceState.getLong(STATE_LAST_TICK);
        }

        mCoordinator = (CoordinatorLayout) findViewById(R.id.coordinator);
        mTime = (TextView) findViewById(R.id.time);
        mPauseResume = (Button) findViewById(R.id.pause_resume);
        mPauseResume.setOnClickListener(this);
        mStop = (Button) findViewById(R.id.stop);
        mStop.setOnClickListener(this);
        mHandler = new Handler(this);
        updateTimer();
        updateButtons();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mRunning) {
            mHandler.sendEmptyMessage(WHAT_UPDATE_TIMER);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_CARD_ID, mCardId);
        outState.putParcelable(STATE_TASK, mTask);
        outState.putBoolean(STATE_RUNNING, mRunning);
        outState.putLong(STATE_REMAINING_TIME, mRemainingTime);
        outState.putLong(STATE_LAST_TICK, mLastTick);
    }

    @Override
    public void onBackPressed() {
        stop();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pause_resume:
                pauseOrResume();
                break;
            case R.id.stop:
                stop();
                break;
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case WHAT_UPDATE_TIMER:
                updateTick();
                return true;
            default:
                return false;
        }
    }

    private void pauseOrResume() {
        mRunning = !mRunning;

        if (mRunning) {
            mLastTick = SystemClock.elapsedRealtime();
            mHandler.sendEmptyMessage(WHAT_UPDATE_TIMER);
        } else {
            mHandler.removeMessages(WHAT_UPDATE_TIMER);
        }

        updateButtons();
    }

    private void stop() {
        mRunning = false;
        updateTimer();
        updateButtons();
        getSupportLoaderManager().initLoader(TASK_UPDATE_LOADER_ID,
                null,
                new TaskUpdateLoaderCallbacks(mCardId, POMODORO_DURATION - mRemainingTime));
        mRemainingTime = POMODORO_DURATION;
    }

    private void updateTick() {
        long newTick = SystemClock.elapsedRealtime();
        long elapsed = newTick - mLastTick;
        mRemainingTime -= elapsed;
        mLastTick = newTick;
        updateTimer();

        if (mRunning) {
            mHandler.sendEmptyMessageDelayed(WHAT_UPDATE_TIMER, REFRESH_DELAY);
        }
    }

    private void updateTimer() {
        long ticks = mRemainingTime / 1000;
        long minutes = ticks / 60;
        long seconds = ticks % 60;
        mTime.setText(String.format(Locale.US, "%d:%02d",
                minutes, seconds));
    }

    private void updateButtons() {
        if (mRunning) {
            mPauseResume.setText(R.string.pause);
        } else {
            mPauseResume.setText(R.string.resume);
        }
    }

    private final class TaskUpdateLoaderCallbacks implements LoaderManager.LoaderCallbacks<Boolean> {
        private final String mCardId;
        private final long mTime;

        public TaskUpdateLoaderCallbacks(String cardId, long time) {
            mCardId = cardId;
            mTime = time;
        }

        @Override
        public Loader<Boolean> onCreateLoader(int id, Bundle args) {
            return new UpdateTaskLoader(TimerActivity.this,
                    mCardId,
                    mTime);
        }

        @Override
        public void onLoadFinished(Loader<Boolean> loader, Boolean success) {
            if (!success) {
                Snackbar.make(mCoordinator, R.string.error_task_sync, Snackbar.LENGTH_LONG).show();
            }

            getSupportLoaderManager().destroyLoader(TASK_UPDATE_LOADER_ID);
        }

        @Override
        public void onLoaderReset(Loader<Boolean> loader) {
        }
    }
}
