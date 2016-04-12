package ru.sudoteam.cyclecomputer.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import ru.sudoteam.cyclecomputer.R;


public final class CycleSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.fragment_cycle_settings);
    }

}
