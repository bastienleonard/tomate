package com.bastienleonard.tomate.ui.tasks;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bastienleonard.tomate.Persistence;
import com.bastienleonard.tomate.R;

final class TabsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    public TabsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context.getApplicationContext();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TasksFragment.newInstance(Persistence.getToDoListId(mContext));
            case 1:
                return TasksFragment.newInstance(Persistence.getDoingListId(mContext));
            case 2:
                return TasksFragment.newInstance(Persistence.getDoneListId(mContext));
        }

        throw new AssertionError();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.tasks_tabs_titles)[position];
    }
}
