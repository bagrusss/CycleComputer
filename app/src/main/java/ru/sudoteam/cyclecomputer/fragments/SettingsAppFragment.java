package ru.sudoteam.cyclecomputer.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.activities.MainActivity;
import ru.sudoteam.cyclecomputer.app.App;

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
                    //TODO restart activity
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Важное сообщение!")
                            .setMessage("Вы уверены, что хотите сменить тему?")
                            .setCancelable(false)
                            .setNegativeButton("Да",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Activity activity = getActivity();
                                            activity.startActivity(new Intent(activity, MainActivity.class));
                                            activity.finish();

                                            SharedPreferences.Editor editor = App.getAppPreferences().edit();
                                            editor.putString(getString(R.string.key_theme), String.valueOf(newValue));
                                            editor.apply();
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                    return false;
                });

    }


}
