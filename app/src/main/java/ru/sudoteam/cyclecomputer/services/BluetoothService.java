package ru.sudoteam.cyclecomputer.services;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import ru.sudoteam.cyclecomputer.activities.MainActivity;
import ru.sudoteam.cyclecomputer.app.eventbus.UniversalEvent;

/**
 * An {@link IntentService} used for data transmission via bluetooth.
 * <p>
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothService extends IntentService {

    public static final String EXTRA_REQUEST_CODE = "REQUEST_CODE";
    public static final String ACTION_LOAD_PROFILE = "LOAD_PROFILE";
    public static final String ACTION_LOAD_DAILY_STATISTIC = "LOAD_DAILY_STATISTIC";
    public static final String ACTION_SCAN_BLUETOOTH = "SCAN_BLUETOOTH";

    private BluetoothManager mManager;
    private BluetoothAdapter mAdapter;
    private BluetoothLeScanner mLEScanner;

    private boolean isScan = false;
    private int mReqCode = -1;

    public BluetoothService() {
        super(BluetoothService.class.getSimpleName());
    }

    private BluetoothAdapter.LeScanCallback leScanCallback = (device, rssi, scanRecord) -> {
        if (isScan) {
            Log.d("bluetooth scan: ", device.getName() + " " + device.getAddress());
            UniversalEvent event = new UniversalEvent();
            event.requestCode = mReqCode;
            event.params.put("device", device);
            EventBus.getDefault().post(event);
        }
    };


    private final BluetoothGattCallback leCallback = new BluetoothGattCallback() {
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mAdapter = mManager.getAdapter();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();
        mReqCode = intent.getIntExtra(EXTRA_REQUEST_CODE, -1);
        if (mAdapter == null || !mAdapter.isEnabled()) {
            UniversalEvent event = new UniversalEvent();
            event.requestCode = mReqCode;
            event.action = MainActivity.BT_ENABLE_REQUIRE;
            EventBus.getDefault().post(event);
            return;
        }
        switch (action) {
            case ACTION_SCAN_BLUETOOTH:
                mAdapter.startLeScan(leScanCallback);
                isScan = true;
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isScan = false;
                mAdapter.stopLeScan(leScanCallback);
                break;
            case ACTION_LOAD_PROFILE:

                break;
            case ACTION_LOAD_DAILY_STATISTIC:

                break;
        }

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

}
