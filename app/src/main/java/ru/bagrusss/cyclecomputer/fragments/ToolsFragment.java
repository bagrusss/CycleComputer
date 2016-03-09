package ru.bagrusss.cyclecomputer.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import ru.bagrusss.cyclecomputer.activities.R;

public class ToolsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.fragment_settings);
    }

}
