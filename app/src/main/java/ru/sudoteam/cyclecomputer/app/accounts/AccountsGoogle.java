package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

/**
 * Created by bagrusss on 06.04.16.
 *
 */
public class AccountsGoogle implements Account {
    @Override
    public void refresh(Activity activity) {

    }

    @Override
    public List<User> friends(Activity activity) {
        return null;
    }

    @Override
    public void login(Activity activity) {

    }

    @Override
    public boolean onResult(int reqCode, int resCode, Intent intent) {
        return false;
    }
}
