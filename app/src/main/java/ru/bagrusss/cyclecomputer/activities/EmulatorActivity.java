package ru.bagrusss.cyclecomputer.activities;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class EmulatorActivity extends AppCompatActivity {

    EditText setSpeed;
    EditText setPositionX;
    EditText setPositionY;
    EditText setBattery;

    Spinner setUnitsSpinner;

    TextView units;
    TextView speed;
    TextView tripTime;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);

        setSpeed = (EditText) findViewById(R.id.setSpeed);
        setPositionX = (EditText) findViewById(R.id.setPositionX);
        setPositionY = (EditText) findViewById(R.id.setPositionY);
        setBattery = (EditText) findViewById(R.id.setBatteryCharge);

        units = (TextView) findViewById(R.id.speedMeasurementUnits);

        speed = (TextView) findViewById(R.id.speedValue);
        tripTime = (TextView) findViewById(R.id.tripTime);

        setUnitsSpinner = (Spinner) findViewById(R.id.setSpeedMeasurementUnits);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.units, R.layout.support_simple_spinner_dropdown_item);
        setUnitsSpinner.setAdapter(adapter);

        setUnitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                units.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        setSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                speed.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

        calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if (message.what == 173) {
                    calendar.add(Calendar.SECOND, 1);
                    tripTime.setText(String.valueOf(calendar.getTime()).substring(11, 19));
                }
            }
        };


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message incrementTimeMessage = new Message();
                incrementTimeMessage.what = 173;
                handler.sendMessage(incrementTimeMessage);
            }
        }, 0, 1000);
    }
}
