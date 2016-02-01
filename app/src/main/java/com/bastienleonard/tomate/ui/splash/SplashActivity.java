package com.bastienleonard.tomate.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.ui.trellosetup.SetupActivity;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public final class SplashActivity extends AppCompatActivity implements Handler.Callback {
    private static final int WHAT_CONTINUE = 1;
    private static final String STATE_REMAINING = "remaining";

    private Handler mHandler;
    private long mRemaining = 2000L;
    private long mLastTick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash_activity);

        if (savedInstanceState != null) {
            mRemaining = savedInstanceState.getLong(STATE_REMAINING);
        }

        mHandler = new Handler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mRemaining > 0L) {
            mHandler.sendEmptyMessageDelayed(WHAT_CONTINUE, mRemaining);
        } else {
            goNext();
        }

        mLastTick = System.currentTimeMillis();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
        long currentTick = System.currentTimeMillis();
        mRemaining -= (System.currentTimeMillis() - mLastTick);
        mLastTick = currentTick;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        long currentTick = System.currentTimeMillis();
        mRemaining -= (System.currentTimeMillis() - mLastTick);
        mLastTick = currentTick;
        outState.putLong(STATE_REMAINING, mRemaining);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case WHAT_CONTINUE:
                goNext();
                return true;
            default:
                return false;
        }
    }

    private void goNext() {
        // TODO: only launch SetupActivity when needed
        startActivity(new Intent(this, SetupActivity.class));
    }
}
