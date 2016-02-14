package com.bastienleonard.tomate;

import android.app.Application;

import com.bastienleonard.tomate.models.TasksCache;

public final class TomateApp extends Application {
    private static TomateApp sApp;

    private TasksCache mTasksCache = new TasksCache();

    public static TomateApp get() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }

    public TasksCache getTaskCache() {
        return mTasksCache;
    }
}
