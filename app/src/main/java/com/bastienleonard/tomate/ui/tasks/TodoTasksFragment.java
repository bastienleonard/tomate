package com.bastienleonard.tomate.ui.tasks;

import android.content.Intent;
import android.os.Bundle;

import com.bastienleonard.tomate.trello.models.Card;
import com.bastienleonard.tomate.ui.timer.TimerActivity;

public final class TodoTasksFragment extends TasksFragment implements ToDoAdapter.OnTimerClickedListener {
    public static TodoTasksFragment newInstance(String listId) {
        TodoTasksFragment f = new TodoTasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LIST_ID, listId);
        f.setArguments(args);
        return f;
    }

    @Override
    protected ToDoAdapter createAdapter() {
        return new ToDoAdapter(this);
    }

    @Override
    public void onTimerClicked(Card card ){
        startActivity(new Intent(getContext(), TimerActivity.class));
    }
}
