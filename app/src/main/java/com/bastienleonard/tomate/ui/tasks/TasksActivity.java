package com.bastienleonard.tomate.ui.tasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.bastienleonard.tomate.BaseActivity;
import com.bastienleonard.tomate.R;

public final class TasksActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_activity);
        setupToolbar();

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabsPagerAdapter adapter = new TabsPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}
