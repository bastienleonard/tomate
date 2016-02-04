package com.bastienleonard.tomate.ui.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bastienleonard.tomate.R;

final class ViewHolder extends RecyclerView.ViewHolder {
    public TextView text;

    public ViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
    }
}
