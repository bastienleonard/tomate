package com.bastienleonard.tomate.ui.trellosetup;

import com.bastienleonard.tomate.trello.models.TrelloList;

public final class DoingListPickerFragment extends BasePickerFragment<TrelloList> {
    public static DoingListPickerFragment newInstance() {
        return new DoingListPickerFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((SetupActivity) getActivity()).requestDoing();
    }

    @Override
    public void onItemClicked(TrelloList list) {
        // See comment in BoardPickerFragment
        OnItemPickedListener l = (OnItemPickedListener) getActivity();

        l.onDoingListPicked(list);
    }
}
