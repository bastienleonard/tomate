package com.bastienleonard.tomate.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.bastienleonard.tomate.Persistence;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.TrelloLoginActivity;
import com.bastienleonard.tomate.trello.TrelloCredentials;
import com.bastienleonard.tomate.ui.tasks.TasksActivity;
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
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case WHAT_CONTINUE:
                goNext(this);
                return true;
            default:
                return false;
        }
    }

    private static void goNext(Context context) {
        if (TextUtils.isEmpty(TrelloCredentials.getPersistedToken(context))) {
            context.startActivity(new Intent(context, TrelloLoginActivity.class));
        } else if (trelloFullySetup(context)) {
            context.startActivity(new Intent(context, TasksActivity.class));
        } else {
            context.startActivity(new Intent(context, SetupActivity.class));
        }
    }

    private static boolean trelloFullySetup(Context context) {
        return !TextUtils.isEmpty(Persistence.getBoardId(context)) &&
                !TextUtils.isEmpty(Persistence.getToDoListId(context)) &&
                !TextUtils.isEmpty(Persistence.getDoingListId(context)) &&
                !TextUtils.isEmpty(Persistence.getDoneListId(context));
    }
}
