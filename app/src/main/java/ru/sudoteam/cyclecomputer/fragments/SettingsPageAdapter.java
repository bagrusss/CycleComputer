package ru.sudoteam.cyclecomputer.fragments;

import android.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by bagrusss on 09.04.16.
 * Tabs settings
 */

public class SettingsPageAdapter extends FragmentStatePagerAdapter {

    private int mTabsCount;

    public SettingsPageAdapter(FragmentManager fm, int count) {
        super(fm);
        mTabsCount = count;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new CycleSettingsFragment();
                break;
            case 1:
                fragment = new SettingsAppFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTabsCount;
    }
}
