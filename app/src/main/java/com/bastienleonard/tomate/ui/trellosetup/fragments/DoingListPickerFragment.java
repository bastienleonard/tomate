package com.bastienleonard.tomate.ui.trellosetup.fragments;

import com.bastienleonard.tomate.trello.models.TrelloList;
import com.bastienleonard.tomate.ui.trellosetup.OnItemPickedListener;
import com.bastienleonard.tomate.ui.trellosetup.SetupActivity;

public final class DoingListPickerFragment extends BasePickerFragment<TrelloList> {
    public static DoingListPickerFragment newInstance() {
        return new DoingListPickerFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        onRefresh();
    }

    @Override
    public void onItemClicked(TrelloList list) {
        // See comment in BoardPickerFragment
        OnItemPickedListener l = (OnItemPickedListener) getActivity();

        l.onDoingListPicked(list);
    }

    @Override
    public void onRefresh() {
        ((SetupActivity) getActivity()).requestDoing();
    }
}
