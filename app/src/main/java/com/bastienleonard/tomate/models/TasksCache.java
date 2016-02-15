package com.bastienleonard.tomate.models;

import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;

import java.util.List;

public final class TasksCache {
    private SimpleArrayMap<String, Task> mTasks;

    public TasksCache() {
    }

    // FIXME: directly parse the tasks JSON as a map if we never never need them as a list
    public void setTasks(List<Task> tasks) {
        if (mTasks == null) {
            mTasks = new SimpleArrayMap<>(tasks.size());
        }

        for (Task task: tasks) {
            mTasks.put(task.getCardId(), task);
        }
    }

    @Nullable
    public Task get(String cardId) {
        if (mTasks == null) {
            return null;
        }

        return mTasks.get(cardId);
    }
}
