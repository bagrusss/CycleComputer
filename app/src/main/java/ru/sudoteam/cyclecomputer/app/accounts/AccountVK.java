package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
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

import ru.sudoteam.cyclecomputer.app.App;

/**
 * Created by bagrusss on 06.04.16.
 * Account interface
 */

public class AccountVK implements Account {

    private static final String DEFAULT_ID = "1";

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

    @Override
    public void userInfo(UserLoaded userLoaded) {
        VKRequest request = VKApi.users().get(VKParameters.from(
                VKApiConst.USER_IDS, App.getAppPreferences().getString(App.KEY_USER_ID, DEFAULT_ID),
                VKApiConst.FIELDS, VKApiUser.FIELD_PHOTO_200));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKApiUser vkUser = ((VKList<VKApiUser>) response.parsedModel).get(0);
                User user = new User();
                user.firstName = vkUser.first_name;
                user.lastName = vkUser.last_name;
                user.imgURL = vkUser.photo_200;
                user.id = vkUser.id;
                userLoaded.onUserLoaded(user);
            }

            @Override
            public void onError(VKError error) {
                userLoaded.onError(new Error(error.errorCode, error.errorMessage));
            }
        });
    }

    @Override
    public void friends(FriendsLoaded friendsLoaded) {

    }

    @Override
    public void login(Activity activity) {
        VKSdk.login(activity, mScope);
    }

    @Override
    public boolean isLoginOK(int requestCode, int resultCode, Intent data, LoginCallback callback) {
        return VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                SharedPreferences.Editor editor = App.getAppPreferences().edit();
                editor.putInt(App.KEY_AUTH_TYPE, App.KEY_AUTH_VK)
                        .putString(App.KEY_USER_TOKEN, res.accessToken)
                        .putString(App.KEY_USER_ID, res.userId)
                        .apply();
                callback.onOK();
            }

            @Override
            public void onError(VKError error) {
                callback.onError(new Error(error.errorCode, error.errorMessage));
            }
        });
    }

    @Override
    public void logout() {
        VKSdk.logout();
    }

}
