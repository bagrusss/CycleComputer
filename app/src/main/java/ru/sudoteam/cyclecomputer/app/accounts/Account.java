package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by bagrusss on 06.04.16.
 *
 */
public interface Account {
    String firstName = null;
    String secondName = null;

    void refresh(Activity activity);

    void login(Activity activity);

    boolean onResult(int reqCode, int resCode, Intent intent);
}
