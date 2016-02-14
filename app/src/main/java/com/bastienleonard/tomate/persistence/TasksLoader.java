package com.bastienleonard.tomate.persistence;

import android.content.Context;

import com.bastienleonard.tomate.BasicLoader;
import com.bastienleonard.tomate.models.Task;

import java.util.List;

public final class TasksLoader extends BasicLoader<List<Task>> {
    private static final String TAG = "TasksLoader";

    public TasksLoader(Context context) {
        super(context);
    }

    @Override
    public List<Task> loadInBackground() {
        return Facade.getTasks(getContext());
    }
}
