package com.bastienleonard.tomate.ui.trellosetup.fragments;

import com.bastienleonard.tomate.trello.models.TrelloList;
import com.bastienleonard.tomate.ui.trellosetup.OnItemPickedListener;
import com.bastienleonard.tomate.ui.trellosetup.SetupActivity;

public final class ToDoListPickerFragment extends BasePickerFragment<TrelloList> {
    public static ToDoListPickerFragment newInstance() {
        return new ToDoListPickerFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        onRefresh();
    }

    @Override
    public void onItemClicked(TrelloList list) {
        // We want the app to crash if the activity doesn't implement this, it's not recoverable
        // and has to be caught during development
        OnItemPickedListener l = (OnItemPickedListener) getActivity();

        l.onTodoListPicked(list);
    }

    @Override
    public void onRefresh() {
        ((SetupActivity) getActivity()).requestToDo();
    }
}
