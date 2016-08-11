package com.bastienleonard.tomate.ui.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bastienleonard.tomate.BaseAdapter;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.TomateApp;
import com.bastienleonard.tomate.models.Task;
import com.bastienleonard.tomate.trello.models.Card;

public final class TasksRecyclerViewAdapter extends BaseAdapter<Card, ViewHolder> {
    public interface TasksRecyclerViewAdapterListener {
        void onTimerClicked(Card card);
        void onNextPageNeeded();
    }

    private static final String TAG = "TasksRecyclerViewAdapter";
    private static final int VIEW_TYPE_CARD = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    private final TasksRecyclerViewAdapterListener mListener;
    private boolean mTimerEnabled = true;

    public TasksRecyclerViewAdapter(TasksRecyclerViewAdapterListener onTimerClickedListener) {
        mListener = onTimerClickedListener;
    }

    public void setTimerEnabled(boolean enabled) {
        mTimerEnabled = enabled;
    }

    @Override
    public int getItemCount() {
        return mItems.size() + 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case VIEW_TYPE_CARD:
                return createCardViewHolder(inflater, parent);
            case VIEW_TYPE_LOADING:
                return createLoadingViewHolder(inflater, parent);
            default:
                throw new AssertionError("Invalid view type");
        }
    }

    private ViewHolder createCardViewHolder(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(mTimerEnabled ? R.layout.tasks_fragment_item : R.layout.tasks_fragment_item_no_timer,
                parent,
                false);
        final ViewHolder holder = new ViewHolder(view);

        if (holder.start != null) {
            holder.start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        Card card = mItems.get(position);
                        mListener.onTimerClicked(card);
                    }
                }
            });
        }

        return holder;
    }

    private static ViewHolder createLoadingViewHolder(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.tasks_fragment_loading, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case VIEW_TYPE_CARD:
                Card card = mItems.get(position);
                bindCardViewHolder(holder, card, mTimerEnabled);
                break;
            case VIEW_TYPE_LOADING:
                mListener.onNextPageNeeded();
                break;
        }
    }

    private static void bindCardViewHolder(ViewHolder holder, Card card, boolean timerEnabled) {
        holder.name.setText(card.getName());
        Task task = TomateApp.get().getTaskCache().get(card.getId()); // FIXME: avoid looking up everytime
        String timeSpent = "";

        if (task != null) {
            timeSpent = task.getPrettyTotalTime();
        }

        holder.timeSpent.setText(timeSpent);

        if (holder.start != null) {
            holder.start.setVisibility(timerEnabled ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mItems.size()) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_CARD;
        }
    }
}
