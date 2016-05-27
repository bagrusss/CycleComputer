package ru.sudoteam.cyclecomputer.app.accounts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ru.sudoteam.cyclecomputer.app.App;

/**
 * Created by bagrusss on 06.04.16.
 * Account interface
 */

public class AccountVK implements Account {

    private static final String DEFAULT_ID = "1";
    private static final String APP_FRIENDS = "execute.getAppFriends";
    private final Gson mGson;

    private static final String RESPONSE = "response";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String ID = "id";

    public AccountVK(Context context, @NotNull Gson gson) {
        VKSdk.initialize(context);
        mGson = gson;
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
        VKRequest request = new VKRequest(APP_FRIENDS);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                JsonObject jObject = mGson.fromJson(response.json.toString(), JsonObject.class);
                JsonArray ja = jObject.get(RESPONSE).getAsJsonArray();
                ArrayList<User> users = new ArrayList<>(ja.size());
                for (JsonElement f : ja) {
                    JsonObject friend = f.getAsJsonObject();
                    User user = new User();
                    user.lastName = friend.get(FIRST_NAME).getAsString();
                    user.firstName = friend.get(LAST_NAME).getAsString();
                    user.imgURL = friend.get(VKApiUser.FIELD_PHOTO_200).getAsString();
                    user.id = friend.get(ID).getAsLong();
                    users.add(user);
                }
                friendsLoaded.onFriendsLoaded(users);
            }

            @Override
            public void onError(VKError error) {
                friendsLoaded.onError(new Error(error.errorCode, error.errorMessage));
            }
        });
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
