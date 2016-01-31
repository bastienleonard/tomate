package com.bastienleonard.tomate.ui.trellosetup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bastienleonard.tomate.R;

import java.util.ArrayList;
import java.util.List;

public final class BoardPickerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.board_picker_fragment, container, false);
        RecyclerView list = (RecyclerView) view.findViewById(R.id.list);
        Adapter adapter = new Adapter();
        list.setAdapter(adapter);
        return view;
    }

    private static final class ViewHolder  extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static final class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private List<String> items = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view =  inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItems(List<String> items) {
            this.items = items;
        }
    }
}
