package com.bastienleonard.tomate.ui.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bastienleonard.tomate.BaseActivity;
import com.bastienleonard.tomate.R;

import java.util.Locale;

public final class TimerActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {
    private static final String TAG = "TimerActivity";
    private static final int WHAT_UPDATE_TIMER = 1;
    private static final long REFRESH_DELAY = 500L;

    private static final String STATE_RUNNING = "running";
    private static final String STATE_REMAINING_TIME = "remainingTime";
    private static final String STATE_LAST_TICK = "lastTick";

    private Handler mHandler;

    private boolean mRunning;
    private long mRemainingTime;
    private long mLastTick;

    private TextView mTime;
    private Button mPauseResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        setupToolbar();

        if (savedInstanceState == null) {
            mRunning = true;
            mRemainingTime = 25 * 60 * 1000L;
            mLastTick = SystemClock.elapsedRealtime();
        } else {
            mRunning = savedInstanceState.getBoolean(STATE_RUNNING);
            mRemainingTime = savedInstanceState.getLong(STATE_REMAINING_TIME);
            mLastTick = savedInstanceState.getLong(STATE_LAST_TICK);
        }

        mTime = (TextView) findViewById(R.id.time);
        mPauseResume = (Button) findViewById(R.id.pause_resume);
        mPauseResume.setOnClickListener(this);
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
        outState.putBoolean(STATE_RUNNING, mRunning);
        outState.putLong(STATE_REMAINING_TIME, mRemainingTime);
        outState.putLong(STATE_LAST_TICK, mLastTick);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pause_resume:
                pauseOrResume();
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
}
