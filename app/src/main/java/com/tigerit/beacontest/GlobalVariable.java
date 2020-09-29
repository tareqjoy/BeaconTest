package com.tigerit.beacontest;

import android.content.Context;

public class GlobalVariable {
    private GlobalVariable() {
    }

    private static boolean notificationShown = false;
    private static boolean beaconInRange = false;


    public static void setNotificationShown(Context context, boolean shown) {
        if (shown) {
            PrefUtil.setFlag(context, PrefUtil.FLAG_NOTIFICATION_SHOWN);
        } else {
            PrefUtil.removeFlag(context, PrefUtil.FLAG_NOTIFICATION_SHOWN);
        }

    }

    public static void setBeaconInRange(Context context, boolean inRange) {
        if (inRange) {
            PrefUtil.setFlag(context, PrefUtil.FLAG_BEACON_IN_RANGE);
        } else {
            PrefUtil.removeFlag(context, PrefUtil.FLAG_BEACON_IN_RANGE);
        }
    }

    public static void setIntelligentNotification(Context context, boolean notification) {
        if (notification) {
            PrefUtil.setFlag(context, PrefUtil.FLAG_INTELLIGENT);
            PrefUtil.removeFlag(context, PrefUtil.FLAG_NOTIFICATION_SHOWN);
        } else {
            PrefUtil.removeFlag(context, PrefUtil.FLAG_INTELLIGENT);
        }
    }

    public static boolean getNotificationShown(Context context) {

        return PrefUtil.getFlag(context, PrefUtil.FLAG_NOTIFICATION_SHOWN);


    }

    public static boolean getBeaconInRange(Context context) {
        return PrefUtil.getFlag(context, PrefUtil.FLAG_BEACON_IN_RANGE);

    }

    public static boolean getIntelligentNotification(Context context) {
        return PrefUtil.getFlag(context, PrefUtil.FLAG_INTELLIGENT, true);
    }
}
