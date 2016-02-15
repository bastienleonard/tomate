package com.bastienleonard.tomate.utils;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;

public final class SoundUtils {
    private SoundUtils() {
    }

    public static void playNotification(Context context) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        RingtoneManager.getRingtone(context, uri).play();
    }
}
