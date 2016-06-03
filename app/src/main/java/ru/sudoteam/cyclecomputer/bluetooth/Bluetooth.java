package ru.sudoteam.cyclecomputer.bluetooth;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import co.lujun.lmbluetoothsdk.BluetoothLEController;

/**
 * Created by bagrusss on 03.06.16.
 * * This class keep information about connected device
 */

public class Bluetooth {

    private static BluetoothLEController mController;

    public static BluetoothLEController init(@NotNull Context context) {
        mController = BluetoothLEController.getInstance().build(context);
        return mController;
    }

    public static BluetoothLEController runAsServer() {
        mController.startAsServer();
        return mController;
    }

    public static boolean checkLE() {
        return mController.isSupportBLE();
    }

    private static void release() {
        mController.release();
    }
}
