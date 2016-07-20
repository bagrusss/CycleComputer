package ru.sudoteam.cyclecomputer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.App;

/**
 * Created by bagrusss on 06.04.16.
 * *
 */

public class CycleBaseActivity extends AppCompatActivity {
    protected Activity mContext = this;

    @StyleRes
    protected int mThemeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String theme = App.getAppPreferences().getString(getString(R.string.key_theme), "theme1");
        if (theme.equals("theme1"))
            mThemeId = R.style.Theme_Light_NoActionBar;
        else mThemeId = R.style.Theme_Dark_NoActionBar;
        setTheme(mThemeId);
        super.onCreate(savedInstanceState);
    }
}
