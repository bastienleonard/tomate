package com.bastienleonard.tomate.ui.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bastienleonard.tomate.BaseAdapter;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.trello.models.Card;

final class TasksRecyclerViewAdapter extends BaseAdapter<Card, ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_text_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindItem(ViewHolder holder, Card card, int position) {
        holder.text.setText(card.getName());
    }
}
