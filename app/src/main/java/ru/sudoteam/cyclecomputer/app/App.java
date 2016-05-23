package ru.sudoteam.cyclecomputer.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.accounts.Account;
import ru.sudoteam.cyclecomputer.app.accounts.AccountVK;


/**
 * Created by bagrusss on 26.03.16.
 * Initialization of application components
 */

public class App extends Application {

    private static Account account;
    private static SharedPreferences preferences;

    private static int mAccType;

    public static final String TAG_APPLICATION = "APPLICATION ";
    public static final Gson GSON = new Gson();

    //SharedPreferences
    public static final String SHARED_PREFERENCES = "CycleComp";
    public static final String KEY_USER_TOKEN = "user_access_token";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_AUTH_TYPE = "auth_type";

    public static final int KEY_AUTH_VK = 2;
    public static final int KEY_AUTH_GOOGLE = 1;
    public static final int KEY_AUTH_NONE = 0;

    public static int getAccountType() {
        return mAccType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO инициализация компонентов
        preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        mAccType = preferences.getInt(KEY_AUTH_TYPE, KEY_AUTH_NONE);
        switch (mAccType) {
            case KEY_AUTH_GOOGLE:

                break;
            case KEY_AUTH_NONE:

                //TODO break;
            case KEY_AUTH_VK:
                account = new AccountVK(getApplicationContext());
        }

        /*LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        WeatherIntentService.startActionCheckWeather(this, lm);*/

        Log.i(TAG_APPLICATION, "App created");

        //Thread.setDefaultUncaughtExceptionHandler(this::handleUncaughtException);
    }

    public static Account getAccount() {
        return account;
    }

    public static SharedPreferences getAppPreferences() {
        return preferences;
    }

    private void handleUncaughtException(Thread thread, Throwable e) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_err_title_ops)
                .setMessage(R.string.dialog_err_message_ops)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.cancel();
                    System.exit(0);
                })
                .setNegativeButton(R.string.dialog_err_bug_report, (dialog, which) -> {

                })
                .create()
                .show();

    }


}
