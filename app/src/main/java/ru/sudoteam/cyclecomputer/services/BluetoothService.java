package ru.sudoteam.cyclecomputer.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * An {@link IntentService} used for data transmission via bluetooth.
 * <p>
 */
public class BluetoothService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "ru.bagrusss.cyclecomputer.services.action.FOO";
    private static final String ACTION_BAZ = "ru.bagrusss.cyclecomputer.services.action.BAZ";

    public static final String BLUETOOTH_SERVICE = "BluetoothService";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "ru.bagrusss.cyclecomputer.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "ru.bagrusss.cyclecomputer.services.extra.PARAM2";

    public BluetoothService() {
        super(BLUETOOTH_SERVICE);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, BluetoothService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, BluetoothService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();
        if (ACTION_FOO.equals(action)) {
            final String param1 = intent.getStringExtra(EXTRA_PARAM1);
            final String param2 = intent.getStringExtra(EXTRA_PARAM2);
            handleActionFoo(param1, param2);
        } else if (ACTION_BAZ.equals(action)) {
            final String param1 = intent.getStringExtra(EXTRA_PARAM1);
            final String param2 = intent.getStringExtra(EXTRA_PARAM2);
            handleActionBaz(param1, param2);
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String paheram2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}