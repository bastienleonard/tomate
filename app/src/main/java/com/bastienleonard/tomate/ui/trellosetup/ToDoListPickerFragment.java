package com.bastienleonard.tomate.ui.trellosetup;

import com.bastienleonard.tomate.trello.models.TrelloList;

public final class ToDoListPickerFragment extends BasePickerFragment<TrelloList> {
    public static ToDoListPickerFragment newInstance() {
        return new ToDoListPickerFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((SetupActivity) getActivity()).requestToDo();
    }

    @Override
    public void onItemClicked(TrelloList list) {
        // We want the app to crash if the activity doesn't implement this, it's not recoverable
        // and has to be caught during development
        OnItemPickedListener l = (OnItemPickedListener) getActivity();

        l.onTodoListPicked(list);
    }
}
