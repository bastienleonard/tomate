package com.bastienleonard.tomate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * This layout assumes that views will be added shortly after it was initialized, and won't change
 * later on. In particular, deleting child views isn't supported (mCurrent might not be in sync
 * anymore).
 */
public class ExclusiveLayout extends FrameLayout {
    protected int mCurrent = -1;

    public ExclusiveLayout(Context context) {
        super(context);
    }

    public ExclusiveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExclusiveLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onFinishInflate() {
        for (int i = 1; i < getChildCount(); ++i) {
            hideChild(i);
        }

        mCurrent = 0;
        super.onFinishInflate();
    }

    public void show(int index) {
        if (index != mCurrent && index >= 0 && index < getChildCount()) {
            hideChild(mCurrent);
            showChild(index);
            mCurrent = index;
        }
    }

    public int getCurrent() {
        return mCurrent;
    }

    public void showFirst() {
        show(0);
    }

    public void showLast() {
        show(getChildCount() - 1);
    }

    public void showNext() {
        show(mCurrent + 1);
    }

    public void showPrevious() {
        show(mCurrent - 1);
    }

    public void cycleNext() {
        int newIndex = mCurrent + 1;

        if (newIndex >= getChildCount()) {
            newIndex = 0;
        }

        show(newIndex);
    }

    public void cyclePrevious() {
        int newIndex = mCurrent - 1;

        if (newIndex < 0) {
            newIndex = getChildCount() - 1;
        }

        show(newIndex);
    }

    private void showChild(int index) {
        setChildVisibility(index, View.VISIBLE);
    }

    private void hideChild(int index) {
        setChildVisibility(index, View.GONE);
    }

    private void setChildVisibility(int index, int visibility) {
        getChildAt(index).setVisibility(visibility);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); ++i) {
            if (i != mCurrent) {
                hideChild(i);
            }
        }

        super.onLayout(changed, left, top, right, bottom);
    }
}
