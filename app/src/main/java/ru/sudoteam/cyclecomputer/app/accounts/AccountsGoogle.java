package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by bagrusss on 06.04.16.
 *
 */
public class AccountsGoogle implements Account {
    @Override
    public void refresh(Activity activity) {

    }

    @Override
    public void login(Activity activity) {

    }

    @Override
    public boolean onResult(int reqCode, int resCode, Intent intent) {
        return false;
    }
}
