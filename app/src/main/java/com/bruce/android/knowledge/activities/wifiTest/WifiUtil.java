package com.bruce.android.knowledge.activities.wifiTest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zhangpengfei
 * Date: 13-7-10
 */
@SuppressWarnings("UnusedDeclaration")
public class WifiUtil {

    public static final String ENCRYPTION_WPA = "WPA";
    public static final String ENCRYPTION_WPA2 = "WPA2";
    public static final String ENCRYPTION_WEP = "WEP";
    public static final String ENCRYPTION_NONE = "NONE";
    public static final String KEY_MAC_ADDRESS = "MAC_ADDRESS";

    public static boolean isWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static int addWifiNetwork(Context context, String ssid, String password) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);


        WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + ssid + "\"";
            if (password != null && password.length() != 0) {
                wifiConfiguration.preSharedKey = "\"" + password + "\"";
                wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            } else {
                wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            }

            wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
            wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);

            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.NONE);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);

            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

        int networkId = wifiManager.addNetwork(wifiConfiguration);
        if (networkId != -1) {
            wifiManager.saveConfiguration();
        }

        return networkId;
    }

    public static WifiConfiguration isExist(String ssid, WifiManager wifiManager) {
        List<WifiConfiguration> configs = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration config : configs) {
            if (config.SSID.equals("\""+ssid+"\"")) {
                return config;
            }
        }
        return null;
    }

    public static int getNetworkId(Context context, String ssid) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> wifiConfigurations = wifiManager.getConfiguredNetworks();
        if (wifiConfigurations == null || wifiConfigurations.isEmpty()) {
            return -1;
        }

        for (WifiConfiguration wifiConfiguration : wifiConfigurations) {
            if (wifiConfiguration.SSID != null && stripSSID(wifiConfiguration.SSID).equals(stripSSID(ssid))) {
                return wifiConfiguration.networkId;
            }
        }

        return -1;
    }

    public static String getMacAddress(Context context) {
        SimpleSharedPref simpleSharedPref = new SimpleSharedPref(context, "WiFiUtil");
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null && !TextUtils.isEmpty(wifiInfo.getMacAddress()) && !"02:00:00:00:00:00".equals(wifiInfo.getMacAddress())) {
            return wifiInfo.getMacAddress();
        } else if (!TextUtils.isEmpty(simpleSharedPref.getString(KEY_MAC_ADDRESS))) {
            return simpleSharedPref.getString(KEY_MAC_ADDRESS);
        } else {
            return "02:00:00:00:00:00";
        }
    }

    public static String getSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            return null;
        }

        String ssid = wifiInfo.getSSID();
        if (ssid == null) {
            return null;
        }

        return stripSSID(ssid);
    }

    public static String stripSSID(String ssid) {
        if (TextUtils.isEmpty(ssid)) {
            return null;
        }

        // generally, wifi ssid contains a pair of quotation
        if (ssid.length() >= 3 && ssid.startsWith("\"") && ssid.endsWith("\"")) {
            return ssid.substring(1, ssid.length() - 1);
        }

        return ssid;
    }

    public static String getBSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo != null ? wifiInfo.getBSSID() : null;
    }

    public static int getRSSI(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo != null ? wifiInfo.getRssi() : Integer.MAX_VALUE;
    }

    public static int getSignalLevel(int rssi) {
        return WifiManager.calculateSignalLevel(rssi, 5);
    }

    public static List<ScanResult> getScanResult(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            return wifiManager.isWifiEnabled() ? wifiManager.getScanResults() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean is5G(ScanResult scanResult) {
        return scanResult.frequency >= 5170 && scanResult.frequency <= 5825;
    }

    public static String getEncryption(String capabilities) {
        List<String> encryptionList = new ArrayList<>();
        if (capabilities != null) {
            if (capabilities.toUpperCase().contains(ENCRYPTION_WPA)) {
                encryptionList.add(ENCRYPTION_WPA);
            }
            if (capabilities.toUpperCase().contains(ENCRYPTION_WPA2)) {
                encryptionList.add(ENCRYPTION_WPA2);
            }
            if (capabilities.toUpperCase().contains(ENCRYPTION_WEP)) {
                encryptionList.add(ENCRYPTION_WEP);
            }
        }
        if (encryptionList.isEmpty()) {
            encryptionList.add(ENCRYPTION_NONE);
        }

        return XMStringUtils.join(encryptionList, "/");
    }

    public static boolean isEncrypted(String encryption) {
        return !TextUtils.isEmpty(encryption) && !WifiUtil.ENCRYPTION_NONE.equalsIgnoreCase(encryption);
    }

    @SuppressWarnings("deprecation")
    public static String getWifiGatewayIp(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isWifi = (networkInfo != null) && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
        if (isWifi) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
            return Formatter.formatIpAddress(dhcpInfo.gateway);
        }

        return null;
    }

    @SuppressWarnings("deprecation")
    public static String getWifiLocalIp(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isWifi = (networkInfo != null) && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
        if (isWifi) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                int ipAddress = wifiInfo.getIpAddress();
                return Formatter.formatIpAddress(ipAddress);
            }
        }

        return null;
    }

    public static void gotoSystemWifiSettings(Context context) {
        String action = Settings.ACTION_WIFI_SETTINGS;
        Intent intent = getExplicitIntent(context, action);

        if (intent == null) {
            intent = new Intent(action);
        }
        context.startActivity(intent);
    }

    public static Intent getExplicitIntent(Context context, String action) {
        PackageManager pm = context.getPackageManager();
        Intent implicitIntent = new Intent(action);
        List<ResolveInfo> resolveInfos = pm.queryIntentServices(implicitIntent, 0);

        ResolveInfo serviceInfo = resolveInfos.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        Intent intent = new Intent(action);
        intent.setComponent(component);
        return intent;
    }

}
