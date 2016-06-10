package ru.sudoteam.cyclecomputer.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.activities.MainActivity;
import ru.sudoteam.cyclecomputer.app.App;

/**
 * Created by bagrusss on 11.04.16.
 * Application settings
 */

public class SettingsAppFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(App.SHARED_PREFERENCES);
        manager.setSharedPreferencesMode(Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.fragment_app_settings);
        findPreference(getString(R.string.key_theme))
                .setOnPreferenceChangeListener((preference, newValue) -> {
                    Activity activity = getActivity();
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("settings", 0);
                    activity.finish();
                    activity.startActivity(intent);
                    return true;
                });

    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }


}
