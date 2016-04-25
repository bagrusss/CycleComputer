package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

/**
 * Created by bagrusss on 06.04.16.
 * Used Google account on Device
 */

public class AccountsGoogle implements Account {

    @Override
    public User userInfo(Activity activity) {
        return null;
    }

    @Override
    public List<User> friends(Activity activity) {
        return null;
    }

    @Override
    public void login(Activity activity) {

    }

    @Override
    public void logout() {

    }

}
