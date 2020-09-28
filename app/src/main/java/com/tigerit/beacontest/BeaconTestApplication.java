package com.tigerit.beacontest;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeaconTestApplication extends Application /*implements BootstrapNotifier*/ {
    private static final String TAG = "MyApplicationName";
    private RegionBootstrap regionBootstrap;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App started up");

      //  setUpBeacon();
    }

/*
    public void setUpBeacon(){
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
         beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
         beaconManager.setBackgroundMode(true);
        // wake up the app when any beacon is seen (you can specify specific id filers in the parameters below)
        Region region = new Region("unqId", Identifier.parse("0x00000000000000000001"), Identifier.parse("0x123456789012"), null);

       // Region region = new Region("unqId", null, null, null);
        regionBootstrap = new RegionBootstrap(this, region);


    }


    @Override
    public void didDetermineStateForRegion(int arg0, Region arg1) {
        //0 = scanning started
        //1 = scanning stopped
        Log.d(TAG, "didDetermineStateForRegion called with state: "+arg0);


    }

    @Override
    public void didEnterRegion(Region arg0) {
        Log.d(TAG, "Got a didEnterRegion call");
        // This call to disable will make it so the activity below only gets launched the first time a beacon is seen (until the next time the app is launched)
        // if you want the Activity to launch every single time beacons come into view, remove this call.
        regionBootstrap.disable();

        sendNotification(arg0.getId1() == null ? null : arg0.getId1().toString(), arg0.getId2() == null ? null : arg0.getId2().toString(),arg0.getId3() == null ? null : arg0.getId3().toString());

        setUpBeacon();
    }



    @Override
    public void didExitRegion(Region arg0) {
        Log.d(TAG, "Connection to Beacon is lost");
        setUpBeacon();
    }
*/


}
