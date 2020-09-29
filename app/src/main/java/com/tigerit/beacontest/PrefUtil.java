package com.tigerit.beacontest;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtil {

    public final static String FLAG_PERMISSION_ON="permssion on";
    public final static String FLAG_NOTIFICATION_SHOWN="notification shown";
    public final static String FLAG_BEACON_IN_RANGE="beacon in range";
    public final static String FLAG_INTELLIGENT="intelligent notifications";

    public static void setFlag(Context context, String flagName){
        SharedPreferences.Editor editor = context.getSharedPreferences(flagName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(flagName, true);
        editor.apply();
    }

    public static void removeFlag(Context context, String flagName){
        SharedPreferences.Editor editor = context.getSharedPreferences(flagName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(flagName, false);
        editor.apply();
    }

    public static boolean getFlag(Context context, String flagName){
        return getFlag(context, flagName, false);
    }

    public static boolean getFlag(Context context, String flagName, Boolean def){
        SharedPreferences editor = context.getSharedPreferences(flagName, Context.MODE_PRIVATE);
        return editor.getBoolean(flagName, def);
    }
   // public void

}
