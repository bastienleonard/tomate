package com.bastienleonard.tomate.ui.breaktimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bastienleonard.tomate.BaseActivity;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.utils.SoundUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class BreakTimerActivity extends BaseActivity implements Handler.Callback {
    private static final String TAG = "BreakTimerActivity";
    private static final String EXTRA_BREAK_TYPE = "breakType";
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BREAK_TYPE_SHORT, BREAK_TYPE_LONG})
    public @interface BreakType {}
    public static final int BREAK_TYPE_SHORT = 0;
    public static final int BREAK_TYPE_LONG = 1;

    private static final int WHAT_UPDATE_TIMER = 1;
    private static final long REFRESH_DELAY = 500L;

    private static final String STATE_BREAK_TYPE = "breakType";
    private static final String STATE_RUNNING = "running";
    private static final String STATE_REMAINING_TIME = "remainingTime";
    private static final String STATE_LAST_TICK = "lastTick";

    private Handler mHandler;

    private int mBreakType;
    private boolean mRunning;
    private long mRemainingTime;
    private long mLastTick;

    private TextView mTime;

    public static void fillIntent(Intent intent, @BreakType int breakType) {
        intent.putExtra(EXTRA_BREAK_TYPE, breakType);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        setupToolbar();

        if (savedInstanceState == null) {
            mBreakType = getIntent().getIntExtra(EXTRA_BREAK_TYPE, -1);
            mRunning = true;

            switch (mBreakType) {
                case BREAK_TYPE_SHORT:
                    mRemainingTime = 5 * 60 * 1000L;
                    break;
                case BREAK_TYPE_LONG:
                    mRemainingTime = 15 * 60 * 1000L;
                    break;
            }

            mLastTick = SystemClock.elapsedRealtime();
        } else {
            mBreakType = savedInstanceState.getInt(STATE_BREAK_TYPE);
            mRunning = savedInstanceState.getBoolean(STATE_RUNNING);
            mRemainingTime = savedInstanceState.getLong(STATE_REMAINING_TIME);
            mLastTick = savedInstanceState.getLong(STATE_LAST_TICK);
        }

        mTime = (TextView) findViewById(R.id.time);
        mHandler = new Handler(this);
        updateTimer();
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
        outState.putInt(STATE_BREAK_TYPE, mBreakType);
        outState.putBoolean(STATE_RUNNING, mRunning);
        outState.putLong(STATE_REMAINING_TIME, mRemainingTime);
        outState.putLong(STATE_LAST_TICK, mLastTick);
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

    private void updateTick() {
        long newTick = SystemClock.elapsedRealtime();
        long elapsed = newTick - mLastTick;
        mRemainingTime -= elapsed;

        mLastTick = newTick;
        updateTimer();

        if (mRemainingTime <= 0L) {
            mRemainingTime = 0L;
            SoundUtils.playNotification(this);
            finish();
        } else if (mRunning) {
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
}
