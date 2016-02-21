package com.bastienleonard.tomate.models;

import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;

public final class TasksCache {
    private SimpleArrayMap<String, Task> mTasks;

    public TasksCache() {
    }

    public void setTasks(SimpleArrayMap<String, Task> tasks) {
        mTasks = tasks;
    }

    @Nullable
    public Task get(String cardId) {
        if (mTasks == null) {
            return null;
        }

        return mTasks.get(cardId);
    }
}
