package com.tigerit.beacontest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("MyApplicationName", "Alarm Broadcast recieved");
        Intent i = new Intent(context, BeaconService.class);
        context.startService(i);

    }
}
