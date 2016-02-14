package com.bastienleonard.tomate.ui.tasks.fragments;

import android.os.Bundle;

public final class DoingTasksFragment extends TasksFragment {
    public static DoingTasksFragment newInstance(String listId) {
        DoingTasksFragment f = new DoingTasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LIST_ID, listId);
        f.setArguments(args);
        return f;
    }
}
