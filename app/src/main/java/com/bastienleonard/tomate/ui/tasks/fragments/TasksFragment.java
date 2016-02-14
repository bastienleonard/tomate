package com.bastienleonard.tomate.ui.tasks.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bastienleonard.tomate.BaseAdapter;
import com.bastienleonard.tomate.ExclusiveLayout;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.TomateApp;
import com.bastienleonard.tomate.models.Task;
import com.bastienleonard.tomate.trello.loaders.CardsLoader;
import com.bastienleonard.tomate.trello.models.Card;
import com.bastienleonard.tomate.ui.tasks.TasksRecyclerViewAdapter;
import com.bastienleonard.tomate.ui.timer.TimerActivity;

import java.util.List;

public class TasksFragment extends Fragment implements TasksRecyclerViewAdapter.OnTimerClickedListener, LoaderManager.LoaderCallbacks<List<Card>> {
    protected static final String ARG_LIST_ID = "listId";
    private static final int CARDS_LOADER_ID = 1;

    private BaseAdapter<Card, ? extends RecyclerView.ViewHolder> mAdapter;
    private ExclusiveLayout mExclusiveLayout;

    protected BaseAdapter<Card, ? extends RecyclerView.ViewHolder> createAdapter() {
        return new TasksRecyclerViewAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tasks_fragment, container, false);
        mExclusiveLayout = (ExclusiveLayout) view.findViewById(R.id.exclusive_layout);
        RecyclerView list = (RecyclerView) view.findViewById(R.id.list);
        mAdapter = createAdapter();
        list.setAdapter(mAdapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(CARDS_LOADER_ID, null, this);
    }

    @Override
    public void onTimerClicked(Card card){
        Intent intent = new Intent(getContext(), TimerActivity.class);
        TimerActivity.fillIntent(intent, card.getId(), TomateApp.get().getTaskCache().get(card.getId()));
        startActivity(intent);
    }

    @Override
    public Loader<List<Card>> onCreateLoader(int i, Bundle bundle) {
        return new CardsLoader(getContext(), getArguments().getString(ARG_LIST_ID));
    }

    @Override
    public void onLoadFinished(Loader<List<Card>> loader, List<Card> cards) {
        if (cards == null) {
            // TODO
        } else if (cards.size() == 0) {
            // TODO
        } else {
            mExclusiveLayout.showNext();
            mAdapter.setItems(cards);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Card>> loader) {
    }
}
