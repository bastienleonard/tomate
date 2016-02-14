package com.bastienleonard.tomate.ui.tasks.fragments;

import android.os.Bundle;

import com.bastienleonard.tomate.ui.tasks.TasksRecyclerViewAdapter;

public final class DoneTasksFragment extends TasksFragment {
    public static DoneTasksFragment newInstance(String listId) {
        DoneTasksFragment f = new DoneTasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LIST_ID, listId);
        f.setArguments(args);
        return f;
    }

    @Override
    protected TasksRecyclerViewAdapter createAdapter() {
        TasksRecyclerViewAdapter a = new TasksRecyclerViewAdapter(this);
        a.setTimerEnabled(false);
        return a;
    }
}
