package ru.bagrusss.cyclecomputer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ActivityB extends AppCompatActivity implements View.OnClickListener {
    Button mBackButton;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_b);
        mBackButton = (Button) findViewById(R.id.backButton);
        mBackButton.setOnClickListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

}
