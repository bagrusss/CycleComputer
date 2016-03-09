package ru.bagrusss.cyclecomputer.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import ru.bagrusss.cyclecomputer.fragments.DisplayFragment;
import ru.bagrusss.cyclecomputer.fragments.ToolsFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private Button mStartButtonB;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
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
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ToolsFragment()).commit();
                break;
            case R.id.nav_display:
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new DisplayFragment()).commit();
                ret = false;
                break;
            case R.id.nav_control:
                ret = false;
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return ret;
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch (keycode) {
            case KeyEvent.KEYCODE_MENU:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onKeyDown(keycode, e);
    }
}
