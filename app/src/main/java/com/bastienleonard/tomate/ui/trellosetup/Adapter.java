package com.bastienleonard.tomate.ui.trellosetup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bastienleonard.tomate.BaseAdapter;
import com.bastienleonard.tomate.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class Adapter<T extends Displayable> extends BaseAdapter<T, ViewHolder> {
    public interface OnItemClickedListener<T> {
        void onItemClicked(T item);
    }

    private OnItemClickedListener<T> mListener;

    public Adapter(OnItemClickedListener<T> listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.list_text_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClicked(mItems.get(position));
                }
            }
        });
        return holder;
    }

    @Override
    protected void onBindItem(ViewHolder holder, T list, int position) {
        holder.text.setText(list.displayText());
    }

    @Override
    public void setItems(List<T> items) {
        super.setItems(items);
        Collections.sort(mItems, new Comparator<T>() {
            @Override
            public int compare(T a, T b) {
                return String.CASE_INSENSITIVE_ORDER.compare(a.displayText(), b.displayText());
            }
        });
    }
}
