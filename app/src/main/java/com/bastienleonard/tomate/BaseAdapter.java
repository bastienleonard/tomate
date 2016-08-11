package com.bastienleonard.tomate;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> mItems = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected T getItem(int position) {
        return mItems.get(position);
    }

    public void setItems(List<T> items) {
        mItems = items;
    }
}
