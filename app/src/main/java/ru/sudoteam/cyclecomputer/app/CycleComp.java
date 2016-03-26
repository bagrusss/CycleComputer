package ru.sudoteam.cyclecomputer.app;

import android.app.Application;
import android.util.Log;

import com.vk.sdk.VKSdk;

/**
 * Created by bagrusss on 26.03.16.
 * Initialization of application components
 */

public class CycleComp extends Application {
    public static final String TAG_APPLICATION = "APPLICATION ";

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO инициализация компонентов
        VKSdk.initialize(getApplicationContext());
        Log.i(TAG_APPLICATION, "Application created");
    }
}
