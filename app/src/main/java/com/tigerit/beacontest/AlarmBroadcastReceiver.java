package com.tigerit.beacontest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.hasExtra(AlarmUtil.KEY_ALARM_ID) && intent.getIntExtra(AlarmUtil.KEY_ALARM_ID, -1) == AlarmUtil.ALARM_ID) {
            Log.d("MyApplicationName", "Alarm Broadcast recieved");
            startService(context);
        }

    }

    private void startService(Context context){
        Intent i = new Intent(context, BeaconService.class);
        context.startService(i);
    }
}
