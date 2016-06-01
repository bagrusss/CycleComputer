package ru.sudoteam.cyclecomputer.services;

import android.content.Context;
import android.content.Intent;

/**
 * Created by bagrusss on 28.05.16.
 * *
 */

public class BluetoothServiceHelper {

    public static void LoadProfile(final Context context, final int reqCode) {
        Intent intent = new Intent(context, BluetoothService.class);
        intent.putExtra(BluetoothService.EXTRA_REQUEST_CODE, reqCode);
        intent.setAction(BluetoothService.ACTION_LOAD_PROFILE);
        context.startService(intent);
    }

    public static void scanDevice(final Context context, final int reqCode) {
        Intent intent = new Intent(context, BluetoothService.class);
        intent.putExtra(BluetoothService.EXTRA_REQUEST_CODE, reqCode);
        intent.setAction(BluetoothService.ACTION_SCAN_BLUETOOTH);
        context.startService(intent);
    }

}
