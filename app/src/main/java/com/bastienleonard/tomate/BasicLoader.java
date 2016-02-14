package com.bastienleonard.tomate;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class BasicLoader<T> extends AsyncTaskLoader<T> {
    public BasicLoader(Context context) {
        super(context);
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Override
    public void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
    }
}
