package com.bastienleonard.tomate.ui.trellosetup.fragments;

import com.bastienleonard.tomate.trello.models.Board;
import com.bastienleonard.tomate.ui.trellosetup.OnItemPickedListener;
import com.bastienleonard.tomate.ui.trellosetup.SetupActivity;

public final class BoardPickerFragment extends BasePickerFragment<Board> {
    public static BoardPickerFragment newInstance() {
        return new BoardPickerFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((SetupActivity) getActivity()).requestBoards();
    }

    @Override
    public void onItemClicked(Board board) {
        // We want the app to crash if the activity doesn't implement this, it's not recoverable
        // and has to be caught during development
        OnItemPickedListener l = (OnItemPickedListener) getActivity();

        l.onBoardPicked(board);
    }

    }
}
