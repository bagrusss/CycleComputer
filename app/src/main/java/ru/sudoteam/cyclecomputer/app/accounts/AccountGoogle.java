package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

/**
 * Created by bagrusss on 06.04.16.
 * Used Google account on Device
 */

public class AccountGoogle implements Account {

    @Override
    public void userInfo(UserLoaded userLoaded) {

    }

    @Override
    public void friends(FriendsLoaded friendsLoaded) {

    }

    @Override
    public void login(Activity activity) {

    }

    @Override
    public boolean isLoginOK(int requestCode, int resultCode, Intent data, LoginCallback callback) {
        return false;
    }

    @Override
    public void logout() {

    }

}
