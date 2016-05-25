package ru.sudoteam.cyclecomputer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sudoteam.cyclecomputer.R;

/**
 * Created by bagrusss on 11.04.16.
 * MultiTab settings fragment
 */

public class SettingsFragment extends Fragment {

    ViewPager mPager;
    TabLayout mTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_settings, null);
        mTabLayout = (TabLayout) v.findViewById(R.id.settings_layout_tab);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.category_cycle_comp));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.category_app));
        mTabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);

        mPager = (ViewPager) v.findViewById(R.id.settings_pager);
        SettingsPageAdapter adapter =
                new SettingsPageAdapter(getActivity().getFragmentManager(), mTabLayout.getTabCount());
        mPager.setAdapter(adapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return v;
    }
}
