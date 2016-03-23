package ru.bagrusss.cyclecomputer.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.bagrusss.cyclecomputer.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            //draw animation ok
            showStartButton();
        } else {
            //draw animation not support
        }
    }

    private void showStartButton() {
        mStartButton.setVisibility(View.VISIBLE);
        mStartButton.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(SplashActivity.this, NavigationActivity.class));
        finish();
    }
}
