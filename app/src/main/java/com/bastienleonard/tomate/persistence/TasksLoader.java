package com.bastienleonard.tomate.persistence;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import com.bastienleonard.tomate.BasicLoader;
import com.bastienleonard.tomate.models.Task;

import java.util.List;

public final class TasksLoader extends BasicLoader<SimpleArrayMap<String, Task>> {
    private static final String TAG = "TasksLoader";

    public TasksLoader(Context context) {
        super(context);
    }

    @Override
    public SimpleArrayMap<String, Task> loadInBackground() {
        return Facade.getTasks(getContext());
    }
}
