package ru.sudoteam.cyclecomputer.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.App;
import ru.sudoteam.cyclecomputer.app.utils.DisconnectDialogPreference;


public final class CycleSettingsFragment extends PreferenceFragment {

    Preference mDevicePreference;
    DisconnectDialogPreference mDialog;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.fragment_cycle_settings);
        mDialog = (DisconnectDialogPreference) findPreference(getString(R.string.key_disconnect));
        mDevicePreference = findPreference(getString(R.string.key_device));
        String deviceMAC = App.getAppPreferences().getString(getString(R.string.key_device), "");
        if (deviceMAC.equals("")) {
            PreferenceScreen screen = getPreferenceScreen();
            screen.removePreference(mDialog);
            screen.setEnabled(false);
            //Toast.makeText(getActivity(), R.string.need_connect_device, Toast.LENGTH_LONG).show();
            return;
        }
        mDevicePreference.setSummary(R.string.summary_device);
        mDevicePreference.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), deviceMAC, Toast.LENGTH_LONG).show();
            return true;
        });

    }

}
