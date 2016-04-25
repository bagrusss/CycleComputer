package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKScopes;

import java.util.List;

import ru.sudoteam.cyclecomputer.app.App;

/**
 * Created by bagrusss on 06.04.16.
 * Account interface
 */

public class AccountVK implements Account {

    public AccountVK(Context context) {
        VKSdk.initialize(context);
    }

    private static final String[] mScope = new String[]{
            VKScopes.FRIENDS,
            VKScopes.STATS,
            VKScopes.WALL,
            VKScopes.OFFLINE,
            VKScopes.GROUPS
    };

    @Nullable
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
        VKSdk.login(activity, mScope);
    }

    @Override
    public void logout() {
        VKSdk.logout();
    }

}
