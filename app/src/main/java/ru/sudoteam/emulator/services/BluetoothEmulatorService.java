package ru.sudoteam.emulator.services;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;
import java.util.UUID;

public class BluetoothEmulatorService extends Service {

    private static final String mName = "EmulatorService";

    public static final String ACTION_START_LISTENING = "ru.sudoteam.cyclecomputer.services.action.START_LISTENING_BLUETOOTH";
    public static final String ACTION_STOP_LISTENING = "ru.sudoteam.cyclecomputer.services.action.STOP_LISTENING_BLUETOOTH";
    public static final String ACTION_SEND_DATA = "ru.sudoteam.cyclecomputer.services.action.SEND_DATA_BLUETOOTH";
    public static final String ACTION_CLIENT_APPEARED = "ru.sudoteam.cyclecomputer.services.action.CLIENT_APPEARED";
    public static final String ACTION_CLIENT_GONE = "ru.sudoteam.cyclecomputer.services.action.CLIENT_GONE";

    public static final String EXTRA_DATA_TO_SEND = "ru.sudoteam.cyclecomputer.services.extra.DATA_TO_SEND_BLUETOOTH";

    private final BluetoothServerSocket mmServerSocket;
    private Listener mListener = null;

    public BluetoothEmulatorService() {

        BluetoothServerSocket tmp = null;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(mName, UUID.randomUUID());
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equals(ACTION_SEND_DATA)) {
            if (mListener != null) {
                mListener.write(intent.getByteArrayExtra(EXTRA_DATA_TO_SEND));
            }
        } else if (intent.getAction().equals(ACTION_START_LISTENING)) {
            if (mListener == null) {
                waitForSocket();
            }
        } else if (intent.getAction().equals(ACTION_STOP_LISTENING)) {
            mListener.cancel();
            mListener.interrupt();
            mListener = null;
        }

        return 0;
    }

    private void waitForSocket() {
        BluetoothSocket socket;

        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                mListener = new Listener(socket);
                sendBroadcast(new Intent(ACTION_CLIENT_APPEARED));
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}


