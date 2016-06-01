package ru.sudoteam.cyclecomputer.activities;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.eventbus.UniversalEvent;
import ru.sudoteam.cyclecomputer.app.lists.ConnectAdapter;
import ru.sudoteam.cyclecomputer.services.BluetoothServiceHelper;

public class ConnectActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 45;
    private Set<BluetoothDevice> mDevices = new HashSet<>();
    private ListView mDevicesList;
    private ConnectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        mDevicesList = (ListView) findViewById(R.id.list_devices);
        mAdapter = new ConnectAdapter(this, mDevices);
        mDevicesList.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        scanDevices();
        super.onResume();
    }

    private void scanDevices() {
        mDevices.clear();
        BluetoothServiceHelper.scanDevice(this, REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(UniversalEvent event) {
        if (event.requestCode == REQUEST_CODE) {
            BluetoothDevice device = (BluetoothDevice) event.params.get("device");
            mDevices.add(device);
            runOnUiThread(() -> mAdapter.notifyDataSetChanged());
        }
    }

}
