package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;

import java.util.List;

/**
 * Created by bagrusss on 06.04.16.
 * *
 */

public interface Account {

    void userInfo(UserLoaded userLoaded);

    void friends(FriendsLoaded friendsLoaded);

    void login(Activity activity);

    void logout();

    interface ErrorOccur {
        void onError(Error error);
    }

    interface UserLoaded extends ErrorOccur {
        void onUserLoaded(User user);
    }

    interface FriendsLoaded extends ErrorOccur {
        void onFriendsLodaded(List<User> friends);
    }

}
