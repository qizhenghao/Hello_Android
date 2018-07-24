package com.bruce.android.knowledge.activities.wifiTest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.bruce.android.knowledge.base.KnowApplication;


/**
 * Created by huxiandong
 * on 14-12-22.
 */
public class WifiConnectExecutor {

    public interface Listener {
        void onSuccess();

        void onFailure();
    }

    private static final int MSG_ENABLE_NETWORK = 101;
    private static final int MSG_CONNECT_TIMEOUT = 102;
    private static final int MSG_CHECK_WIFI_STATE = 103;
    private static final int MSG_CONNECT_OVER = 104;

    private static final int ENABLE_NETWORK_WAITING = 4 * 1000;
    private static final int CHECK_WIFI_STATE_DURATION = 1 * 1000;
    private static final int CONNECT_MAX_TIME = 5 * 1000;
    private static final int CONNECT_OVER_WAITING = 5 * 1000;

    private Context mContext;
    private Listener mListener;
    private WifiManager mWifiManager;
    private InnerReceiver mReceiver;

    private int mNetworkId;
    private boolean mIsReadyToConnect;
    private boolean mIsConnecting;
    private boolean mIsConnected;
    private String mSsid;
    private String mSsidNaked;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ENABLE_NETWORK:
                    mIsReadyToConnect = false;
                    if (!mIsConnected) {
                        if (!KnowApplication.sWaitingSpecifiedWifiConnected) {
                            // prevent other executor connect over after this executor start connecting
                            Log.i("Bruce", "WifiConnectExecutor: set waiting flag true");
                            KnowApplication.sWaitingSpecifiedWifiConnected = true;
                        }

                        mIsConnecting = true;
                        mWifiManager.enableNetwork(mNetworkId, true);
                        mHandler.sendEmptyMessageDelayed(MSG_CHECK_WIFI_STATE, CHECK_WIFI_STATE_DURATION);
                    }
                    break;
                case MSG_CHECK_WIFI_STATE:
                    if (!mIsConnected) {
                        if (!checkConnected()) {
                            mHandler.sendEmptyMessageDelayed(MSG_CHECK_WIFI_STATE, CHECK_WIFI_STATE_DURATION);
                        }
                    }
                    break;
                case MSG_CONNECT_TIMEOUT:
                    if (mIsConnecting) {
                        mHandler.removeMessages(MSG_CHECK_WIFI_STATE);
                        mContext.unregisterReceiver(mReceiver);

                        mIsConnecting = false;

                        if (mListener != null) {
                            mListener.onFailure();
                        }

                        doAfterConnectOver();
                    }
                    break;
                case MSG_CONNECT_OVER:
                    if (KnowApplication.sWaitingSpecifiedWifiConnected) {
                        Log.i("Bruce", "WifiConnectExecutor: set waiting flag false");
                        KnowApplication.sWaitingSpecifiedWifiConnected = false;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public WifiConnectExecutor(Context context, Listener listener) {
        mContext = context;
        mListener = listener;

        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mReceiver = new InnerReceiver();
    }

    public void connect(String ssid, String password) {
        WifiConfiguration configuration = WifiUtil.isExist(ssid, mWifiManager);
        int networkId;
        if (configuration != null) {
            networkId = configuration.networkId;
            Log.i("Bruce", "old preSharedKey = " + configuration.preSharedKey + ", networkId = " + networkId);
            mWifiManager.disableNetwork(networkId);
            mWifiManager.removeNetwork(networkId);
//            mWifiManager.enableNetwork()

            configuration.preSharedKey = "\"" + password + "\"";
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            networkId =  mWifiManager.updateNetwork(configuration);
//            networkId = WifiUtil.addWifiNetwork(mContext, ssid, password);
            Log.i("Bruce", "new preSharedKey = " + configuration.preSharedKey + ", networkId = " + networkId);
        } else {
            networkId = WifiUtil.addWifiNetwork(mContext, ssid, password);
        }
        connect(networkId, ssid);
    }

    public void connect(int networkId, String ssid) {
//        if (ssid.equals(WifiUtil.getSSID(mContext))) {
//            if (mListener != null) {
//                mListener.onSuccess();
//            }
//            return;
//        }

        mNetworkId = networkId;
        if (mNetworkId == -1) {
            if (mListener != null) {
                mListener.onFailure();
            }
            return;
        }

        Log.i("Bruce", "WifiConnectExecutor: set waiting flag true");
        KnowApplication.sWaitingSpecifiedWifiConnected = true;

        mContext.registerReceiver(mReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        mIsReadyToConnect = true;
        mIsConnecting = false;
        mIsConnected = false;
        mWifiManager.disconnect();

        mHandler.sendEmptyMessageDelayed(MSG_ENABLE_NETWORK, ENABLE_NETWORK_WAITING);
        mHandler.sendEmptyMessageDelayed(MSG_CONNECT_TIMEOUT, CONNECT_MAX_TIME);

        mSsid = "\"" + ssid + "\"";
        mSsidNaked = ssid;
    }

    public void cancel() {
        if (mIsConnecting) {
            mHandler.removeMessages(MSG_CHECK_WIFI_STATE);
            mHandler.removeMessages(MSG_CONNECT_TIMEOUT);
            mContext.unregisterReceiver(mReceiver);
            mIsConnecting = false;
        } else if (mIsReadyToConnect) {
            mHandler.removeMessages(MSG_ENABLE_NETWORK);
            mHandler.removeMessages(MSG_CONNECT_TIMEOUT);
            mContext.unregisterReceiver(mReceiver);
            mIsReadyToConnect = false;
        }

        doAfterConnectOver();
    }

    private void doAfterConnectOver() {
        if (KnowApplication.sWaitingSpecifiedWifiConnected) {
            mHandler.sendEmptyMessageDelayed(MSG_CONNECT_OVER, CONNECT_OVER_WAITING);
        }
    }

    private boolean checkConnected() {
        if (mIsConnecting) {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork != null) {
                boolean isWifi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
                boolean isConnected = activeNetwork.getState() == NetworkInfo.State.CONNECTED;

                if (isWifi && isConnected && !TextUtils.isEmpty(mSsidNaked)) {
                    WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
                    if (wifiInfo != null) {
                        String connectedSsid = wifiInfo.getSSID();
                        if (!TextUtils.isEmpty(connectedSsid) && (connectedSsid.equals(mSsid) || connectedSsid.equals(mSsidNaked))) {
                            mIsConnecting = false;
                            mIsConnected = true;
                            mHandler.removeMessages(MSG_CHECK_WIFI_STATE);
                            mHandler.removeMessages(MSG_CONNECT_TIMEOUT);
                            mContext.unregisterReceiver(mReceiver);

                            if (mListener != null) {
                                mListener.onSuccess();
                            }

                            doAfterConnectOver();

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private class InnerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkConnected();
        }
    }

}
