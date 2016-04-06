package ru.sudoteam.cyclecomputer.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.vk.sdk.VKSdk;

/**
 * Created by bagrusss on 26.03.16.
 * Initialization of application components
 */

public class App extends Application {
    public static final String TAG_APPLICATION = "APPLICATION ";
    public static final Gson GSON = new Gson();

    //SharedPreferences
    public static final String SHARED_PREFERENCES = "CycleComp";
    public static final String KEY_TOKEN = "access_token";
    public static final String KEY_VK_ID = "vk_id";
    public static final String KEY_AUTH_TYPE = "auth_type";
    public static final int KEY_AUTH_VK = 2;
    public static final int KEY_AUTH_GOOGLE = 1;
    public static final int KEY_AUTH_NONE = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO инициализация компонентов
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        switch (preferences.getInt(KEY_AUTH_TYPE, KEY_AUTH_NONE)) {
            case KEY_AUTH_GOOGLE:

                break;
            case KEY_AUTH_NONE:

                //TODO break;
            case KEY_AUTH_VK:
                VKSdk.initialize(getApplicationContext());

        }
        Log.i(TAG_APPLICATION, "App created");
        //Log.i(TAG_APPLICATION, "Hardware support" + KeyInfo.isInsideSecurityHardware());
    }

}
