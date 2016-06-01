package ru.sudoteam.emulator.services;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by fatman on 28/05/16.
 */
public class Listener extends Thread {

    private InputStream mmInStream;
    private OutputStream mmOutStream;
    private BluetoothSocket mSocket;

    public Listener(BluetoothSocket socket) {
        this.mSocket = socket;
    }

    @Override
    public void run() {
        configureSocket();
        handleSocket();
    }

    private void configureSocket() {
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = mSocket.getInputStream();
            tmpOut = mSocket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void handleSocket() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                // Send the obtained bytes to the UI activity

                //TODO handle message
                /*mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                        .sendToTarget();*/
            } catch (IOException e) {
                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) { }
    }

}
