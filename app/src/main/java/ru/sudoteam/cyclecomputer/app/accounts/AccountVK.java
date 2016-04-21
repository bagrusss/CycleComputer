package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Intent;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.model.VKScopes;

import java.util.List;

/**
 * Created by bagrusss on 06.04.16.
 *
 */
public class AccountVK implements Account {

    private static final String[] mScope = new String[]{
            VKScopes.FRIENDS,
            VKScopes.STATS,
            VKScopes.WALL,
            VKScopes.OFFLINE,
            VKScopes.GROUPS
    };

    @Override
    public void refresh(Activity activity) {

    }

    @Override
    public List<User> friends(Activity activity) {
        return null;
    }

    @Override
    public void login(Activity activity) {
        VKSdk.login(activity, mScope);
    }

    @Override
    public boolean onResult(int reqCode, int resCode, Intent intent) {
        return false;
    }


}
