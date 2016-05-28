package ru.sudoteam.cyclecomputer.services;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import org.greenrobot.eventbus.EventBus;

import ru.sudoteam.cyclecomputer.app.eventbus.UniversalEvent;
import ru.sudoteam.cyclecomputer.fragments.ProfileFragment;

/**
 * An {@link IntentService} used for data transmission via bluetooth.
 * <p>
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothService extends IntentService {

    public static final String EXTRA_REQUEST_CODE = "REQUEST_CODE";
    public static final String ACTION_LOAD_PROFILE = "LOAD_PROFILE";
    public static final String ACTION_LOAD_DAILY_STATISTIC = "LOAD_DAILY_STATISTIC";

    private BluetoothManager mManager;
    private BluetoothAdapter mAdapter;
    private BluetoothLeScanner mLEScanner;

    public BluetoothService() {
        super(BluetoothService.class.getSimpleName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mAdapter = mManager.getAdapter();
        //mLEScanner=mAdapter.getBluetoothLeScanner();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();
        int reqCode = intent.getIntExtra(EXTRA_REQUEST_CODE, -1);
        if (mAdapter == null || !mAdapter.isEnabled()) {
            UniversalEvent event = new UniversalEvent();
            event.requestCode = reqCode;
            event.action = ProfileFragment.BT_ENABLE_REQUIRE;
            EventBus.getDefault().post(event);
            return;
        }
        switch (action) {
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
