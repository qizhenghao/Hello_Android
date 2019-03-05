package com.bruce.android.knowledge.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bruce.android.knowledge.bean.Demo;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by qizhenghao on 19/2/20.
 **/
@TargetApi(21)
public class TestGattServerActivity extends Activity {

    private static final String TAG = "Bruce";
    private BluetoothGattServer mGattServer;
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private ParcelUuid Service_UUID_INFO = new ParcelUuid(UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb"));
    private final String SOFTWARE_REVISION_STRING = "00002A28-0000-1000-8000-00805f9b34fb";
    private ParcelUuid Service_UUID_HEART = new ParcelUuid(UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb"));
    private ParcelUuid Service_UUID_TEST = new ParcelUuid(UUID.fromString("8CBEBE02-0001-0002-0003-010203040506"));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        Button sendBtn = new Button(this);
        sendBtn.setText("startAdvertising");
        linearLayout.addView(sendBtn);
        setContentView(linearLayout);

        initialize();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAdvertising();
            }
        });

        Demo.SearchResponse.Builder responseBuilder = Demo.SearchResponse.newBuilder();
        responseBuilder.setPageNumber(110);
        responseBuilder.setKey("测试");
        for (int i = 0; i < 3; i++) {
            Demo.SearchItem.Builder itemBuilder = Demo.SearchItem.newBuilder();
            itemBuilder.setId(1000 + i).setTitle("title").setContent("content");
            responseBuilder.addSearchItems(itemBuilder.build());
        }

        //序列化
        Demo.SearchResponse searchResponse = responseBuilder.build();
        Log.i("Bruce", searchResponse.toString());
        Log.i("Bruce", Arrays.toString(searchResponse.toByteArray()));

        //反序列化
        try {
            byte[] responseBytes = searchResponse.toByteArray();
            Demo.SearchResponse searchResponse1 = Demo.SearchResponse.parseFrom(responseBytes);
            Log.i("Bruce", searchResponse1.toString());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts BLE Advertising.
     */
    private void startAdvertising() {
        Log.d(TAG, "Service: Starting Advertising");
        AdvertiseSettings settings = buildAdvertiseSettings();
        AdvertiseData data = buildAdvertiseData();
        if (mBluetoothLeAdvertiser != null) {
            mBluetoothLeAdvertiser.startAdvertising(settings, data, mAdvertiseCallback);
        }
    }

    /**
     * Get references to system Bluetooth objects if we don't have them already.
     */
    private void initialize() {
        if (mBluetoothLeAdvertiser == null) {
            BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager != null) {
                BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
                if (mBluetoothAdapter != null) {
                    mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
                    mGattServer = mBluetoothManager.openGattServer(this, mGattServerCallback);
                    if (mGattServer == null) {
                        Toast.makeText(this, "bt_gatt_server_null", Toast.LENGTH_LONG).show();
                    } else {
                        addDeviceInfoService();/*build gatt server data here*/
                    }

                } else {
                    Toast.makeText(this, "mBluetoothAdapter_null", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "mBluetoothManager_null", Toast.LENGTH_LONG).show();
            }
        }

    }

    /*prepare service here*/
    private void addDeviceInfoService() {
        BluetoothGattService previousService = mGattServer.getService(Service_UUID_INFO.getUuid());
        if (null != previousService) mGattServer.removeService(previousService);
        BluetoothGattCharacteristic softwareVerCharacteristic = new BluetoothGattCharacteristic(
                UUID.fromString(SOFTWARE_REVISION_STRING), BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ);
        BluetoothGattService deviceInfoService = new BluetoothGattService(Service_UUID_INFO.getUuid(),
                BluetoothGattService.SERVICE_TYPE_PRIMARY);
        softwareVerCharacteristic.setValue("111".getBytes());
        deviceInfoService.addCharacteristic(softwareVerCharacteristic);
        mGattServer.addService(deviceInfoService);
    }

    private final AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            Log.d(TAG, "onStartSuccess: " + settingsInEffect.toString());
        }

        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            Log.d(TAG, "onStartFailure: " + errorCode);
        }
    };

    private final BluetoothGattServerCallback mGattServerCallback = new BluetoothGattServerCallback() {

        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            Log.d(TAG, "onConnectionStateChange: gatt server connection state changed, new state " + Integer.toString(newState));
            super.onConnectionStateChange(device, status, newState);
        }

        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            Log.d(TAG, "onServiceAdded: " + Integer.toString(status));
            super.onServiceAdded(status, service);
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            Log.d(TAG, "onCharacteristicReadRequest: " + "requestId" + Integer.toString(requestId) + "offset" + Integer.toString(offset));
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristic.getValue());
        }

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {

            Log.d(TAG, "onCharacteristicWriteRequest: " + "data = " + value.toString());
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
            /*store data here*/

        }

        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            Log.d(TAG, "onNotificationSent: status = " + Integer.toString(status));
            super.onNotificationSent(device, status);
        }

        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
            Log.d(TAG, "onDescriptorReadRequest: requestId = " + Integer.toString(requestId));
            super.onDescriptorReadRequest(device, requestId, offset, descriptor);
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, descriptor.getValue());
        }

        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            Log.d(TAG, "onDescriptorWriteRequest: requestId = " + Integer.toString(requestId));
            super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
        }

        @Override
        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            Log.d(TAG, "onExecuteWrite: requestId = " + Integer.toString(requestId));
            super.onExecuteWrite(device, requestId, execute);
            /*in case we stored data before, just execute the write action*/
        }

        @Override
        public void onMtuChanged(BluetoothDevice device, int mtu) {
            Log.d(TAG, "onMtuChanged: mtu = " + Integer.toString(mtu));
        }

    };

    /**
     * Returns an AdvertiseData object which includes the Service UUID and Device Name.
     */
    private AdvertiseData buildAdvertiseData() {

        /**
         * Note: There is a strict limit of 31 Bytes on packets sent over BLE Advertisements.
         *  This includes everything put into AdvertiseData including UUIDs, device info, &
         *  arbitrary service or manufacturer data.
         *  Attempting to send packets over this limit will result in a failure with error code
         *  AdvertiseCallback.ADVERTISE_FAILED_DATA_TOO_LARGE. Catch this error in the
         *  onStartFailure() method of an AdvertiseCallback implementation.
         */

        AdvertiseData.Builder dataBuilder = new AdvertiseData.Builder();
        dataBuilder.addServiceUuid(Service_UUID_INFO);
//        dataBuilder.addServiceUuid(Service_UUID_HEART);
//        dataBuilder.addServiceUuid(Service_UUID_TEST);
        dataBuilder.setIncludeDeviceName(true);

        /* For example - this will cause advertising to fail (exceeds size limit) */
        //String failureData = "asdghkajsghalkxcjhfa;sghtalksjcfhalskfjhasldkjfhdskf";
        //dataBuilder.addServiceData(Constants.Service_UUID, failureData.getBytes());

        return dataBuilder.build();
    }

    /**
     * Returns an AdvertiseSettings object set to use low power (to help preserve battery life)
     * and disable the built-in timeout since this code uses its own timeout runnable.
     */
    private AdvertiseSettings buildAdvertiseSettings() {
        AdvertiseSettings.Builder settingsBuilder = new AdvertiseSettings.Builder();
        settingsBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER);
        settingsBuilder.setTimeout(0);
        return settingsBuilder.build();
    }
}
