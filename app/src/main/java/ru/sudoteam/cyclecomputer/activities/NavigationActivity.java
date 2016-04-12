package ru.sudoteam.cyclecomputer.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.fragments.MainFragment;
import ru.sudoteam.cyclecomputer.fragments.ProfileFragment;
import ru.sudoteam.cyclecomputer.fragments.SettingsFragment;

public class NavigationActivity extends CycleBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private FragmentManager mFragmentManager;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
        setContentView(R.layout.activity_navigation);
        mToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(mToolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
        mToolbar.setTitle(R.string.profile);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new ProfileFragment()).commit();
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
        if (id == R.id.action_settings) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new SettingsFragment()).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean ret = true;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        switch (id) {
            case R.id.nav_main:
                transaction.replace(R.id.fragment_container, new MainFragment()).commit();
                mToolbar.setTitle(R.string.main);
                break;
            case R.id.nav_settings:
             /*   transaction.replace(R.id.fragment_container, new SettingsFragment());
                mToolbar.setTitle(R.string.settings);*/
                startActivity(new Intent(mContext, SettingsActivity.class));
                break;
            case R.id.nav_about:
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
                if (mDrawer.isDrawerOpen(GravityCompat.START))
                    mDrawer.closeDrawer(GravityCompat.START);
                else mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onKeyDown(keycode, e);
    }

    //android bus

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profile_layout) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment()).commit();
        }
    }
}
