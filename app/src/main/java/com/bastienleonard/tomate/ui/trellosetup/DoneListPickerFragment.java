package com.bastienleonard.tomate.ui.trellosetup;

import com.bastienleonard.tomate.trello.models.TrelloList;

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
}
