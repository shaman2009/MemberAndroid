package com.dandelion.memberandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by FengxiangZhu on 13-12-28.
 */
public class DeviceUtil {
    public static String getWIFIMacAddress(Context context) {
        String wifiAddress;
        SharedPreferences sharedPreferences = context.getSharedPreferences("DEVICE", Context.MODE_PRIVATE);
        wifiAddress = sharedPreferences.getString("DEVICE_WIFI_ADDRESS", null);
        if (wifiAddress == null) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            wifiAddress = info.getMacAddress();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("DEVICE_WIFI_ADDRESS", wifiAddress);
            editor.commit();
        }
        return wifiAddress;
    }
}
