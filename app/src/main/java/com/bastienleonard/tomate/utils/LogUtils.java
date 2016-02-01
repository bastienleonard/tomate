package com.bastienleonard.tomate.utils;

import android.util.Log;

import com.bastienleonard.tomate.BuildConfig;
import com.crashlytics.android.Crashlytics;

public final class LogUtils {
    private LogUtils() {
    }

    public static void e(String tag, Throwable t) {
        Crashlytics.getInstance().core.logException(t);
        log(Log.ERROR, tag, t);
    }

    public static void e(String tag, String message, Throwable t) {
        Crashlytics.getInstance().core.logException(t);
        log(Log.ERROR, tag, message, t);
    }

    public static void e(String tag, String content) {
        Crashlytics.getInstance().core.log(content);
        log(Log.ERROR, tag, content);
    }

    public static void w(String tag, Throwable t) {
        Crashlytics.getInstance().core.logException(t);
        log(Log.WARN, tag, t);
    }

    public static void w(String tag, String content) {
        Crashlytics.getInstance().core.log(content);
        log(Log.WARN, tag, content);
    }

    public static void i(String tag, String content) {
        Crashlytics.getInstance().core.log(content);
        log(Log.INFO, tag, content);
    }

    public static void d(String tag, String content) {
        Crashlytics.getInstance().core.log(content);
        log(Log.DEBUG, tag, content);
    }

    public static void v(String tag, String content) {
        Crashlytics.getInstance().core.log(content);
        log(Log.VERBOSE, tag, content);
    }

    private static void log(int level, String tag, String message, Throwable t) {
        if (BuildConfig.DEBUG) {
            tag = validTag(tag);

            switch (level) {
                case Log.ERROR:
                    Log.e(tag, message, t);
                    break;
                case Log.WARN:
                    Log.w(tag, message, t);
                    break;
                case Log.INFO:
                    Log.i(tag, message, t);
                    break;
                case Log.DEBUG:
                    Log.d(tag, message, t);
                    break;
                case Log.VERBOSE:
                    Log.v(tag, message, t);
                    break;
            }
        }
    }

    private static void log(int level, String tag, Throwable t) {
        log(level, tag, t.getMessage(), t);
    }

    private static void log(int level, String tag, String content) {
        if (BuildConfig.DEBUG) {
            Log.println(level, validTag(tag), content);
        }
    }

    private static String validTag(String tag) {
        if (tag.length() <= 23) {
            return tag;
        }

        return tag.substring(0, 23);
    }
}
