package ru.sudoteam.cyclecomputer.app.eventbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by bagrusss
 * *
 */

public class Bus extends BroadcastReceiver {

    private EventBus mBus = EventBus.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
