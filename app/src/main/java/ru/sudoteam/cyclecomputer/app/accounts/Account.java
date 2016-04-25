package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by bagrusss on 06.04.16.
 *
 */

public interface Account {

    User userInfo(Activity activity);

    List<User> friends(Activity activity);

    void login(Activity activity);

    void logout();

}
