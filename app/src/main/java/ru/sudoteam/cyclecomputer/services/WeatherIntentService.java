package ru.sudoteam.cyclecomputer.services;

import android.Manifest;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

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
    public static final String WEATHER_SERVICE_TAG = "weather_check_service";

    // TODO: Rename parameters
    public static final String EXTRA_LATITUDE = "ru.sudoteam.cyclecomputer.services.extra.LATITUDE";
    public static final String EXTRA_LONGITUDE = "ru.sudoteam.cyclecomputer.services.extra.LONGITUDE";

    private static final int DAYS_BEFORE_REPEAT_BAD_WEATHER = 1;
    private static final int DAYS_BEFORE_REPEAT_GOOD_WEATHER = 7;
    private static final int DAYS_RANDOM_DELTA_GOOD_WEATHER = 7;

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
    public static void startActionCheckWeather(Context context, LocationManager lm) {

        Log.i(WEATHER_SERVICE_TAG, "Checking permission for location data.");

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        Intent intent = new Intent(context, WeatherIntentService.class);
        intent.setAction(WeatherIntentService.ACTION_CHECK_WEATHER);
        intent.putExtra(WeatherIntentService.EXTRA_LATITUDE, String.valueOf(latitude));
        intent.putExtra(WeatherIntentService.EXTRA_LONGITUDE, String.valueOf(longitude));

        Log.i(WEATHER_SERVICE_TAG, "Weather check intent created");

        if (isScheduled(context, intent)) return;

        Log.i(WEATHER_SERVICE_TAG, "Check is not scheduled, scheduling...");

        PendingIntent weatherPendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), weatherPendingIntent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_CHECK_WEATHER.equals(action)) {
                final String latitude = intent.getStringExtra(EXTRA_LATITUDE);
                final String longitude = intent.getStringExtra(EXTRA_LONGITUDE);

                Log.i(WEATHER_SERVICE_TAG, "weather check initialized at latitude "
                                            + latitude + " longitude " + longitude);

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
            builder.setSmallIcon(R.mipmap.ic_logo);

            NotificationManager mNotificationManager = (NotificationManager)
                                    getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, builder.build());
        }
    }

    private static boolean isScheduled(Context context, Intent intent) {
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }
}
