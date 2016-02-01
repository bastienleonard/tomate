package com.bastienleonard.tomate.ui.trellosetup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bastienleonard.tomate.R;

final class ViewHolder extends RecyclerView.ViewHolder {
    public TextView text;

    public ViewHolder(View view) {
        super(view);
        text = (TextView) view.findViewById(R.id.text);
    }
}
