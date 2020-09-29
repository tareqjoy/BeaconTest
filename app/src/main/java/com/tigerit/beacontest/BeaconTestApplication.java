package com.tigerit.beacontest;

import android.app.Application;
import android.util.Log;

import org.altbeacon.beacon.startup.RegionBootstrap;

public class BeaconTestApplication extends Application /*implements BootstrapNotifier*/ {
    private static final String TAG = "MyApplicationName";
    private RegionBootstrap regionBootstrap;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App started up");
        if(RequirementsUtil.checkPermissions(this) && RequirementsUtil.checkBluetooth())
            AlarmUtil.setAlarmLater(this);

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
