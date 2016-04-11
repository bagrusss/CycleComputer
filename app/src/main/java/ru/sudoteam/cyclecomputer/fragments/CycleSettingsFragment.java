package ru.sudoteam.cyclecomputer.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import ru.sudoteam.cyclecomputer.R;


public class CycleSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.fragment_settings);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }

}
