package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by bagrusss on 06.04.16.
 * *
 */

public interface Account {

    void userInfo(UserLoaded userLoaded);

    void friends(FriendsLoaded friendsLoaded);

    void login(Activity activity);

    boolean isLoginOK(int requestCode, int resultCode, Intent data, LoginCallback callback);

    void logout();

    interface ErrorOccur {
        void onError(Error error);
    }

    interface UserLoaded extends ErrorOccur {
        void onUserLoaded(User user);
    }

    interface FriendsLoaded extends ErrorOccur {
        void onFriendsLoaded(ArrayList<User> friends);
    }

    interface LoginCallback extends ErrorOccur {
        void onOK();
    }

}
