package ru.sudoteam.cyclecomputer.fragments;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import ru.sudoteam.cyclecomputer.R;

/**
 * Created by bagrusss on 11.04.16.
 * Application settings
 */
public class SettingsAppFragment extends PreferenceFragment {
    ListPreference mThemePreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_app_settings);

        mThemePreference = (ListPreference) findPreference(getString(R.string.key_theme));
        mThemePreference.setOnPreferenceChangeListener((preference, newValue) -> {
            //TODO restart activity
            Toast.makeText(getActivity(), "Theme changed to "+ newValue, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

}
