package com.tigerit.beacontest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.ALARM_SERVICE;

public class AlarmUtil {

    private AlarmUtil(){}

    public final static int ALARM_ID=234324243;
    public final static String KEY_ALARM_ID="alarm id";
    public static String KEY_TRIGGER_TIME = "trigger time";


    private static void setAlarm(Context context, long secondsLater) {
        long triggerTime = System.currentTimeMillis()+ secondsLater *1000;
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(KEY_ALARM_ID, ALARM_ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), ALARM_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }



        Log.d("MyApplicationName", "Alarm set!");
    }

    public static void setAlarmLater(Context context){
        setAlarm(context, 45);
    }

    public static void setImmediateAlarm(Context context){
        setAlarm(context, 0);
    }
}
