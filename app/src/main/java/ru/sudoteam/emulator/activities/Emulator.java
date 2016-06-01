package ru.sudoteam.emulator.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.emulator.services.BluetoothEmulatorService;

public class Emulator extends AppCompatActivity {

    private int mX = 0;
    private int mY = 0;
    private int mSpeed = 0;
    private int mBattery = 0;

    private EditText mSpeedEdit;
    private EditText mPositionXEdit;
    private EditText mPositionYEdit;
    private EditText mBatteryEdit;

    private Spinner mUnitsSpinner;

    private TextView mUnitsView;
    private TextView mSpeedView;
    private TextView mTripTimeView;
    private Calendar mCalendar;
    private Timer mTimerSeconds;
    private Timer mTimerSending;

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSpeedEdit = (EditText) findViewById(R.id.set_speed);

        mPositionXEdit = (EditText) findViewById(R.id.set_position_x);
        mPositionYEdit = (EditText) findViewById(R.id.set_position_y);
        mBatteryEdit = (EditText) findViewById(R.id.set_battery_charge);

        mUnitsView = (TextView) findViewById(R.id.speed_measurement_units);

        mSpeedView = (TextView) findViewById(R.id.speed_value);
        mTripTimeView = (TextView) findViewById(R.id.trip_time);

        mUnitsSpinner = (Spinner) findViewById(R.id.set_speed_units);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.measure_units, R.layout.support_simple_spinner_dropdown_item);
        mUnitsSpinner.setAdapter(adapter);

        mUnitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mUnitsView.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        mSpeedEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSpeedView.setText(s);
                mSpeed = Integer.valueOf(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPositionXEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mX = Integer.valueOf(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPositionYEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mY = Integer.valueOf(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mBatteryEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBattery = Integer.valueOf(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.MILLISECOND, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);

    }

    @Override
    protected void onStart() {
        mTimerSeconds = new Timer();
        mTimerSeconds.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    mCalendar.add(Calendar.SECOND, 1);
                    mTripTimeView.setText(String.valueOf(mCalendar.getTime()).substring(11, 19));
                });
            }
        }, 0, 1000);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(BluetoothEmulatorService.ACTION_CLIENT_APPEARED)) {
                    if (mTimerSending == null) {
                        mTimerSending = new Timer();
                        mTimerSending.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent1 = new Intent();
                                intent1.putExtra(BluetoothEmulatorService.EXTRA_DATA_TO_SEND, mSpeedEdit.getText().toString().getBytes());
                            }
                        }, 0, 10000);
                    }
                } else if (intent.getAction().equals(BluetoothEmulatorService.ACTION_CLIENT_GONE)) {
                    if (mTimerSending != null) {
                        mTimerSending.cancel();
                        mTimerSending = null;
                    }
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothEmulatorService.ACTION_CLIENT_APPEARED);
        intentFilter.addAction(BluetoothEmulatorService.ACTION_CLIENT_GONE);

        registerReceiver(broadcastReceiver, intentFilter);

        Intent intent = new Intent(this, BluetoothEmulatorService.class);
        intent.setAction(BluetoothEmulatorService.ACTION_START_LISTENING);
        startService(intent);

        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mTimerSending != null) {
            mTimerSending.cancel();
            mTimerSending = null;
        }

        if (mTimerSeconds != null) {
            mTimerSeconds.cancel();
            mTimerSeconds = null;
        }

        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
