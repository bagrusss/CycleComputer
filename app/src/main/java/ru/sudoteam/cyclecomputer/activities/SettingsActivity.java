package ru.sudoteam.cyclecomputer.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.fragments.SettingsPageAdapter;

public class SettingsActivity extends AppCompatActivity {

    private ViewPager mPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTabLayout = (TabLayout) findViewById(R.id.settings_layout_tab);
        if (mTabLayout != null) {
            mTabLayout.addTab(mTabLayout.newTab().setText(R.string.category_cycle_comp));
            mTabLayout.addTab(mTabLayout.newTab().setText(R.string.displays));
            mTabLayout.addTab(mTabLayout.newTab().setText(R.string.category_app));
            mTabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);
        }
        mPager = (ViewPager) findViewById(R.id.settings_pager_);
        SettingsPageAdapter adapter =
                new SettingsPageAdapter(getFragmentManager(), mTabLayout.getTabCount());
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
