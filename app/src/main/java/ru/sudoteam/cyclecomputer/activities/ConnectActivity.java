package ru.sudoteam.cyclecomputer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Set;
import java.util.TreeSet;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.eventbus.UniversalEvent;
import ru.sudoteam.cyclecomputer.services.BluetoothServiceHelper;

public class ConnectActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 45;
    private Set<String> devices = new TreeSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void scanDevices() {
        devices.clear();
        BluetoothServiceHelper.scanDevice(this, REQUEST_CODE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UniversalEvent event) {
        if (event.requestCode == REQUEST_CODE) {

        }
    }

}
