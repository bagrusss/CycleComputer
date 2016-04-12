package ru.sudoteam.cyclecomputer.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

/**
 * Created by bagrusss on 09.04.16.
 * Tabs settings
 */

public class SettingsPageAdapter extends FragmentPagerAdapter {

    private int mTabsCount = 0;

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
                fragment = new DisplaysFragment();
                break;
            case 2:
                fragment = new SettingsAppFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTabsCount;
    }
}
