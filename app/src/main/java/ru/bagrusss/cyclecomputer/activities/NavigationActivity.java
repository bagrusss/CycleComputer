package ru.bagrusss.cyclecomputer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Toolbar mToolbar;
    DrawerLayout mDrawer;
    Button mStartButtonB;
    FloatingActionButton mFab;
    NavigationView mNavigationView;
    ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(view -> Snackbar.make(view, "Action", Snackbar.LENGTH_LONG)
                .setAction("Show Toast", v -> {
                    Toast.makeText(this, "Toast", Toast.LENGTH_LONG).show();
                }).show());
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mStartButtonB = (Button) findViewById(R.id.start_button_b);
        mStartButtonB.setOnClickListener(this);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mToggle);
        mToggle.syncState();
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean ret = true;
        switch (id) {
            case R.id.nav_main:

                break;
            case R.id.nav_profile:

                break;
            case R.id.nav_displays:

                break;
            case R.id.nav_settings:

                break;
            case R.id.nav_display:
                //ret = false;
                startActivity(new Intent(this, EmulatorActivity.class));
                break;
            case R.id.nav_control:
                ret = false;
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return ret;
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), ActivityB.class));
    }
}
