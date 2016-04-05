package ru.sudoteam.cyclecomputer.app;

import android.app.Activity;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.model.VKScopes;

/**
 * Created by bagrusss on 04.04.16.
 *
 */

public class AuthHelper {

    private static final String[] mScope = new String[]{
            VKScopes.FRIENDS,
            VKScopes.STATS,
            VKScopes.WALL,
            VKScopes.OFFLINE,
            VKScopes.GROUPS
    };

    public static void loginVK(Activity context) {
        VKSdk.login(context, mScope);
    }

    public static void loginGoogle(Activity context) {

    }
}
