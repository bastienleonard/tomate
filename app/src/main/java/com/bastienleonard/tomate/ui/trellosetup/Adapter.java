package com.bastienleonard.tomate.ui.trellosetup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bastienleonard.tomate.R;

import java.util.ArrayList;
import java.util.List;

class Adapter<T extends Displayable> extends RecyclerView.Adapter<ViewHolder> {
    public interface OnItemClickedListener<T> {
        void onItemClicked(T item);
    }

    protected List<T> mItems = new ArrayList<>();
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        T list = mItems.get(position);
        holder.text.setText(list.displayText());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<T> items) {
        this.mItems = items;
    }
}
