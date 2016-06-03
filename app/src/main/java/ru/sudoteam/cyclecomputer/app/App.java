package ru.sudoteam.cyclecomputer.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.accounts.Account;
import ru.sudoteam.cyclecomputer.app.accounts.AccountGoogle;
import ru.sudoteam.cyclecomputer.app.accounts.AccountVK;
import ru.sudoteam.cyclecomputer.app.eventbus.UniversalEvent;


/**
 * Created by bagrusss on 26.03.16.
 * Initialization of application components
 */

public class App extends Application {

    private static Account account;
    private static SharedPreferences preferences;

    public static final String TAG_APPLICATION = "APPLICATION ";
    public static final Gson GSON = new Gson();

    //SharedPreferences
    public static final String SHARED_PREFERENCES = "CycleComp";
    public static final String KEY_USER_TOKEN = "user_access_token";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_AUTH_TYPE = "auth_type";

    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LATITUDE = "latitude";

    public static final int KEY_AUTH_VK = 2;
    public static final int KEY_AUTH_GOOGLE = 1;
    public static final int KEY_AUTH_NONE = 0;


    public static void setAccount(@NotNull Account acc) {
        account = acc;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO инициализация компонентов
        preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        int accType = preferences.getInt(KEY_AUTH_TYPE, KEY_AUTH_NONE);
        switch (accType) {
            case KEY_AUTH_GOOGLE:
                account = new AccountGoogle();
                break;
            case KEY_AUTH_NONE:

                //TODO break;
            case KEY_AUTH_VK:
                account = new AccountVK(getApplicationContext(), App.GSON);
        }

        /*LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        WeatherIntentService.startActionCheckWeather(this, lm);*/

        Log.i(TAG_APPLICATION, "App created");
      /*Thread.setDefaultUncaughtExceptionHandler(this::handleUncaughtException);
        EventBus.getDefault().register(this);*/
       // CustomActivityOnCrash.install(this);
    }

    public static Account getAccount() {
        return account;
    }

    public static SharedPreferences getAppPreferences() {
        return preferences;
    }

    public static SharedPreferences.Editor putDouble(final SharedPreferences.Editor editor,
                                                     final String key, final double value) {
        return editor.putLong(key, Double.doubleToRawLongBits(value));
    }

    public static double getDouble(final SharedPreferences prefs,
                                   final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

}
