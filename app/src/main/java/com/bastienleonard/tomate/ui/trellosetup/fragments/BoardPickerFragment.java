package com.bastienleonard.tomate.ui.trellosetup.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bastienleonard.tomate.R;
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

    private static final class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);
        }
    }
}
