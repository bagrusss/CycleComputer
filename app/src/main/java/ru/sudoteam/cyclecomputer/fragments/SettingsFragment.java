package ru.sudoteam.cyclecomputer.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import ru.sudoteam.cyclecomputer.R;


public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.fragment_settings);
    }

}
