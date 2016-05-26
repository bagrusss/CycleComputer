package ru.sudoteam.cyclecomputer.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.activities.MainActivity;

/**
 * Created by bagrusss on 11.04.16.
 * Application settings
 */

public class SettingsAppFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_app_settings);
        findPreference(getString(R.string.key_theme))
                .setOnPreferenceChangeListener((preference, newValue) -> {
                    Activity activity = getActivity();
                    activity.startActivity(new Intent(activity, MainActivity.class));
                    activity.finish();
                    return true;
                });

    }


}
