package com.tigerit.beacontest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.ALARM_SERVICE;

public class ServiceUtil {

    public static void startAlert(Context context) {

        int i = 5;
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 5000, 30000, pendingIntent);
        Toast.makeText(context, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();

        Log.d("MyApplicationName", "Alarm set!");
    }
}
