package com.bastienleonard.tomate.ui.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.trello.models.Card;

import java.util.ArrayList;
import java.util.List;

final class TasksRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Card> mCards = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_text_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Card card = mCards.get(position);
        holder.text.setText(card.getName());
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public void setCard(List<Card> cards) {
        mCards = cards;
    }
}
