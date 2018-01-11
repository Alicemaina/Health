package com.example.alice.health.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.alice.health.MainActivity;
import com.example.alice.health.R;
import com.microsoft.windowsazure.notifications.NotificationsHandler;

/**
 * Created by alice on 1/2/18.
 */


public class MyHandler extends NotificationsHandler {
    private static final int NOTIFICATION_ID = 1;
    protected NotificationManager mNotificationManager;
    private Context ctx;

    @Override
    public void onReceive(Context context, Bundle bundle) {
        ctx = context;
        String nhMessage = bundle.getString("message");
        sendNotification(nhMessage);
        if (MainActivity.isVisible) {
            MainActivity.mainActivity.ToastNotify(nhMessage);
        }
    }

    private void sendNotification(String msg) {

        Intent intent = new Intent(ctx, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Notification Hub Demo");
        mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(msg));
        mBuilder.setSound(defaultSoundUri);
        mBuilder.setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
