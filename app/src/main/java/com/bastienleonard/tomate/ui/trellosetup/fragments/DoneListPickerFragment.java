package com.bastienleonard.tomate.ui.trellosetup.fragments;

import com.bastienleonard.tomate.trello.models.TrelloList;
import com.bastienleonard.tomate.ui.trellosetup.OnItemPickedListener;
import com.bastienleonard.tomate.ui.trellosetup.SetupActivity;

public final class DoneListPickerFragment extends BasePickerFragment<TrelloList> {
    public static DoneListPickerFragment newInstance() {
        return new DoneListPickerFragment();
    }

    @Override
    public void onItemClicked(TrelloList list) {
        // See comment in BoardPickerFragment
        OnItemPickedListener l = (OnItemPickedListener) getActivity();

        l.onDoneListPicked(list);
    }

    @Override
    public void onRefresh() {
        ((SetupActivity) getActivity()).requestDone();
    }
}
