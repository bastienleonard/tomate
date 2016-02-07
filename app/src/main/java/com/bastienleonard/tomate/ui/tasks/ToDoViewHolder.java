package com.bastienleonard.tomate.ui.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bastienleonard.tomate.R;

public final class ToDoViewHolder extends RecyclerView.ViewHolder {
    public TextView text;
    public ImageView start;

    public ToDoViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
        start = (ImageView) itemView.findViewById(R.id.start);
    }
}
