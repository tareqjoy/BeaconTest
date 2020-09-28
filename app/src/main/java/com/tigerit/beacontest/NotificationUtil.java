package com.tigerit.beacontest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationUtil {

    public static void sendNotification(Context context, String id1, String id2, String id3) {

        //   Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.siren);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();

            String CHANNEL_ID = BuildConfig.APPLICATION_ID.concat("_notification_id");
            String CHANNEL_NAME = BuildConfig.APPLICATION_ID.concat("_notification_name");
            assert notificationManager != null;

            NotificationChannel mChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (mChannel == null) {
                mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                //    mChannel.setSound(sound, attributes);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("New Beacon Found")
                    .setContentText("ID1: "+id1+"\nID2: "+id2+"\nID3: "+id3)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_CALL)
                    .setContentIntent(openScreen(context))
                    .setAutoCancel(true);
            //   .setOngoing(true);

            Notification notification = builder.build();
            notificationManager.notify(200, notification);
        } else {
            Intent intent = new Intent(context, MainActivity.class);
            // IMPORTANT: in the AndroidManifest.xml definition of this activity, you must set android:launchMode="singleInstance" or you will get two instances
            // created when a user launches the activity manually and it gets launched from here.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }

    private static PendingIntent openScreen(Context context) {
        Intent fullScreenIntent = new Intent(context, MainActivity.class);
        fullScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fullScreenIntent.putExtra(String.valueOf(200), new int[]{200});
        return PendingIntent.getActivity(context, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
