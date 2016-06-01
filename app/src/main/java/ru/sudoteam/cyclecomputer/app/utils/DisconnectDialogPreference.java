package ru.sudoteam.cyclecomputer.app.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * Created by bagrusss on 31.05.16.
 * *
 */

public class DisconnectDialogPreference extends DialogPreference {

    public DisconnectDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which==DialogInterface.BUTTON_POSITIVE){
            //TODO disconnect device. Open connect activity
        }
    }
}