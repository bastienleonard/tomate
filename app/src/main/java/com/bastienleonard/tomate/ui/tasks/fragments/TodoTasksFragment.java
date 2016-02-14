package com.bastienleonard.tomate.ui.tasks.fragments;

import android.os.Bundle;

public final class TodoTasksFragment extends TasksFragment {
    public static TodoTasksFragment newInstance(String listId) {
        TodoTasksFragment f = new TodoTasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LIST_ID, listId);
        f.setArguments(args);
        return f;
    }
}
