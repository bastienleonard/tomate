package com.bastienleonard.tomate.ui.tasks;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bastienleonard.tomate.persistence.Facade;
import com.bastienleonard.tomate.R;
import com.bastienleonard.tomate.ui.tasks.fragments.DoingTasksFragment;
import com.bastienleonard.tomate.ui.tasks.fragments.DoneTasksFragment;
import com.bastienleonard.tomate.ui.tasks.fragments.TodoTasksFragment;

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
                return TodoTasksFragment.newInstance(Facade.getToDoListId(mContext));
            case 1:
                return DoingTasksFragment.newInstance(Facade.getDoingListId(mContext));
            case 2:
                return DoneTasksFragment.newInstance(Facade.getDoneListId(mContext));
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
