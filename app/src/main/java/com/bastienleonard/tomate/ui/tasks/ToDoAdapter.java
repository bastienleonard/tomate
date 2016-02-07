package com.bastienleonard.tomate.ui.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bastienleonard.tomate.BaseAdapter;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.trello.models.Card;

public final class ToDoAdapter extends BaseAdapter<Card, ToDoViewHolder> {
    public interface OnTimerClickedListener {
        void onTimerClicked(Card card);
    }

    private final OnTimerClickedListener mOnTimerClickedListener;

    public ToDoAdapter(OnTimerClickedListener onTimerClickedListener) {
        mOnTimerClickedListener = onTimerClickedListener;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.todo_task_item, parent, false);
        final ToDoViewHolder holder = new ToDoViewHolder(view);
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    Card card = getItem(position);
                    mOnTimerClickedListener.onTimerClicked(card);
                }
            }
        });
        return holder;
    }

    @Override
    protected void onBindItem(ToDoViewHolder holder, Card card, int position) {
        holder.text.setText(card.getName());
    }
}
