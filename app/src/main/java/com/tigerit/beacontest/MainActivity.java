package com.tigerit.beacontest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_CODE = 1;

    private Button btnPermission, btnActivity, btnBluetooth, btnForce;
    private BluetoothAdapter bluetoothAdapter;
    private final String FORCE_GET_NOTIFICATION = "FORCE GET NOTIFICATIONS", INTELLIGENT_NOTIFICATION = "INTELLIGENT NOTIFICATIONS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnBluetooth = findViewById(R.id.btn_bluetooth);
        btnActivity = findViewById(R.id.btn_activity);
        btnPermission = findViewById(R.id.btn_permission);
        btnForce  = findViewById(R.id.btn_force);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            btnPermission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestPermissions();
                }
            });
        } else {
            btnPermission.setVisibility(View.GONE);
        }


        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBatteryOptimizationOff();
            }
        });

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBluetoothOn();
            }
        });


        if(GlobalVariable.getIntelligentNotification(getApplicationContext())){

            btnForce.setText(FORCE_GET_NOTIFICATION);
        } else {
            btnForce.setText(INTELLIGENT_NOTIFICATION);
        }
        btnForce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnForce.getText().equals(FORCE_GET_NOTIFICATION)) {

                    GlobalVariable.setIntelligentNotification(getApplicationContext(), false);
                    Log.d("MyApplicationName", "Forced get notification");

                    btnForce.setText(INTELLIGENT_NOTIFICATION);
                } else {
                    GlobalVariable.setIntelligentNotification(getApplicationContext(), true);
                    Log.d("MyApplicationName", "Intelligent notification");
                    btnForce.setText(FORCE_GET_NOTIFICATION);
                }
            }
        });

        checkBluetooth();
        checkBatteryOptimization();
        checkPermissions();
    //    startSafeBeaconAlarm();


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermissions() {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   startSafeBeaconAlarm();
                    //  turnOnBeacon();
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }


    private boolean checkBatteryOptimization() {

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!powerManager.isIgnoringBatteryOptimizations(getPackageName())) {
                return false;
            } else {
                btnActivity.setEnabled(false);
                return true;
            }
        }
        return true;
    }

    private boolean checkPermissions() {
        if (RequirementsUtil.checkPermissions(this)) {
            // You can use the API that requires the permission.
            //  turnOnBeacon();
            btnPermission.setEnabled(false);
            return true;


            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.

        }
        return false;
    }

    private boolean checkBluetooth() {
        if (RequirementsUtil.checkBluetooth()) {
            btnBluetooth.setEnabled(false);
            return true;
        }
        return false;
    }

    private void requestBatteryOptimizationOff() {
        Intent i = new Intent();
        i.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        i.setData(Uri.parse("package:" + getPackageName()));
        startActivity(i);
    }

    private void requestBluetoothOn() {
        bluetoothAdapter.enable();
    }

    private void startSafeBeaconAlarm(){
        checkBatteryOptimization();
        if(checkBluetooth() && checkPermissions()){
            AlarmUtil.setImmediateAlarm(this);
        }

    }

/*    private void turnOnBeacon(){
        ((BeaconTestApplication) getApplication()).setUpBeacon();
    }*/
}