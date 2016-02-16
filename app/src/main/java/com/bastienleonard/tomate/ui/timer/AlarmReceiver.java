package com.bastienleonard.tomate.ui.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import com.bastienleonard.tomate.R;

public final class AlarmReceiver extends BroadcastReceiver {
    public static final String ACTION_TIME_UP = "com.bastienleonard.tomate.actions.TIME_UP";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TimeActivityPresenceIndicator.isOpen(context)) {
            return;
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notifIntent = new Intent(context, TimerActivity.class);
        notifIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notifIntent.putExtra(TimerActivity.EXTRA_SKIP_NEXT_SOUND, true);
        PendingIntent notifPendingIntent = PendingIntent.getActivity(context,
                0,
                notifIntent,
                PendingIntent.FLAG_ONE_SHOT);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_timer_white_24dp)
                .setContentTitle(context.getString(R.string.timer_notification_title))
                .setContentText(context.getString(R.string.timer_notification_text))
                .setContentIntent(notifPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .build();
        notificationManager.notify(0, notification);
    }
}
