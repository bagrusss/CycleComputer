package ru.sudoteam.cyclecomputer.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.web.WeatherRest;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class WeatherIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_CHECK_WEATHER = "ru.sudoteam.cyclecomputer.services.action.CHECK_WEATHER";

    // TODO: Rename parameters
    public static final String EXTRA_LATITUDE = "ru.sudoteam.cyclecomputer.services.extra.LATITUDE";
    private static final String EXTRA_LONGITUDE = "ru.sudoteam.cyclecomputer.services.extra.LONGITUDE";

    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionCheckWeather(Context context, String latitude, String longitude) {
        Intent intent = new Intent(context, WeatherIntentService.class);
        intent.setAction(ACTION_CHECK_WEATHER);
        intent.putExtra(EXTRA_LATITUDE, latitude);
        intent.putExtra(EXTRA_LONGITUDE, longitude);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_WEATHER.equals(action)) {
                final String latitude = intent.getStringExtra(EXTRA_LATITUDE);
                final String longitude = intent.getStringExtra(EXTRA_LONGITUDE);
                handleActionCheckWeather(latitude, longitude);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionCheckWeather(String latitude, String longitude) {
        if (WeatherRest.isGoodWeatherLabel(latitude, longitude)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            builder.setContentTitle(getString(R.string.notification_weather_title));
            builder.setContentText(getString(R.string.notification_weather_text));
            builder.setSmallIcon(0);

            NotificationManager mNotificationManager = (NotificationManager)
                                    getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, builder.build());
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
