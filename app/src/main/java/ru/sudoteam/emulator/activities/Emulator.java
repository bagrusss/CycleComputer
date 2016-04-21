package ru.sudoteam.emulator.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Emulator extends AppCompatActivity {

    private EditText mSpeedEdit;
    private EditText mPositionXEdit;
    private EditText mPositionYEdit;
    private EditText mBatteryEdit;

    private Spinner mUnitsSpinner;

    private TextView mUnitsView;
    private TextView mSpeedView;
    private TextView mTripTimeView;
    private Calendar mCalendar;
    private Timer mTimer;

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
                this, R.array.units, R.layout.support_simple_spinner_dropdown_item);
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
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    mCalendar.add(Calendar.SECOND, 1);
                    mTripTimeView.setText(String.valueOf(mCalendar.getTime()).substring(11, 19));
                });
            }
        }, 0, 1000);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mTimer.cancel();
        mTimer = null;
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
