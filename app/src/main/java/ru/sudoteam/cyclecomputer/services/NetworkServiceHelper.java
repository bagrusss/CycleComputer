package ru.sudoteam.cyclecomputer.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import ru.sudoteam.cyclecomputer.app.App;

/**
 * Created by bagrusss
 */

public class NetworkServiceHelper {

    private static final String APP_FRIENDS = "execute.getAppFriends";

    public static void startLoadFriends(final Context context, final int reqCode) {
        Intent intent = new Intent(context, NetworkService.class);
        switch (App.getAccountType()) {
            case App.KEY_AUTH_VK:
                VKRequest request = new VKRequest(APP_FRIENDS);
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        Log.i("ids : ", response.json.toString());
                        intent.setAction(NetworkService.ACTION_DB);
                        intent.putExtra(NetworkService.EXTRA_FRIENDS_JSON, response.json.toString());
                        intent.putExtra(NetworkService.EXTRA_REQUEST_CODE, reqCode);
                        context.startService(intent);
                    }

                    @Override
                    public void onError(VKError error) {
                        Log.e("Error", error.toString());
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case App.KEY_AUTH_GOOGLE:

                break;
        }

    }

}
