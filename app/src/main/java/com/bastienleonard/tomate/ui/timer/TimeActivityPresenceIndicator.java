package com.bastienleonard.tomate.ui.timer;

import android.content.Context;

final class TimeActivityPresenceIndicator {
    private static final String PREF_NAME = "timerPresenceIndicator";
    private static final String KEY_IS_OPEN = "isOpen";

    public static boolean isOpen(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(KEY_IS_OPEN, false);
    }

    public void onStart(Context context) {
        setPref(context, true);
    }

    public void onStop(Context context) {
        setPref(context, false);
    }

    private void setPref(Context context, boolean value) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
                .putBoolean(KEY_IS_OPEN, value)
                .commit();
    }
}
