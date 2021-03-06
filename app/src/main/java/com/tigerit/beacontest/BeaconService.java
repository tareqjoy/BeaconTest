package com.tigerit.beacontest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

public class BeaconService extends Service implements BootstrapNotifier {
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;

    @Override
    public void onDestroy() {

     //   AlarmUtil.setAlarmLater(this);
        super.onDestroy();
        Log.d("MyApplicationName", "Service destroyed!");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyApplicationName", "Service created!");


    }


    public void setUpBeacon() {
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        // beaconManager.setBackgroundScanPeriod(10000);

        backgroundPowerSaver = new BackgroundPowerSaver(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        beaconManager.setBackgroundMode(true);
        // wake up the app when any beacon is seen (you can specify specific id filers in the parameters below)
        Region region = new Region("unqId", Identifier.parse("0x00000000000000000001"), Identifier.parse("0x123456789012"), null);


        // Region region = new Region("unqId", null, null, null);
        if (regionBootstrap != null) {
            regionBootstrap.disable();
        }
        regionBootstrap = new RegionBootstrap(this, region);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        setUpBeacon();
        return Service.START_STICKY;
    }

    @Override
    public void didEnterRegion(Region arg0) {

        regionBootstrap.disable();

        if(GlobalVariable.getIntelligentNotification(this)){
            GlobalVariable.setBeaconInRange(this, true);
        }

        if (!GlobalVariable.getNotificationShown(this)) {
            Log.d("MyApplicationName", "Beacon found and notified");
            GlobalVariable.setNotificationShown(this, true);
            NotificationUtil.sendNotification(this,
                    arg0.getId1() == null ? null : arg0.getId1().toString(),
                    arg0.getId2() == null ? null : arg0.getId2().toString(),
                    arg0.getId3() == null ? null : arg0.getId3().toString());
        } else {
            Log.d("MyApplicationName", "Beacon found and but not notified");
        }


        // stopSelf();
        //  setUpBeacon();
    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

        if (i == 0) {
            if (!GlobalVariable.getBeaconInRange(this)) {
                Log.d("MyApplicationName", "Previously, no beacon found. Will notify upon beacon found.");
                GlobalVariable.setNotificationShown(this, false);
            } else {
                Log.d("MyApplicationName", "Previously, a beacon found. Notification is paused. Will just check beacon availability.");
            }
            GlobalVariable.setBeaconInRange(this, false);
        }

        if (i == 1) {
            AlarmUtil.setAlarmLater(this);
        }
        Log.d("MyApplicationName", "Beacon state: " + (i == 0 ? "Scanning" : "Not Scanning"));
    }
}
