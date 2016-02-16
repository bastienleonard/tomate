package com.bastienleonard.tomate.ui.trellosetup.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.ui.trellosetup.Adapter;
import com.bastienleonard.tomate.ui.trellosetup.Displayable;

import java.util.List;

public abstract class BasePickerFragment<T extends Displayable> extends Fragment implements Adapter.OnItemClickedListener<T> {
    protected Adapter<T> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_picker_fragment, container, false);
        RecyclerView list = (RecyclerView) view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Adapter<T>(this);
        list.setAdapter(mAdapter);
        return view;
    }

    public void display(List<T> items) {
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }
}
