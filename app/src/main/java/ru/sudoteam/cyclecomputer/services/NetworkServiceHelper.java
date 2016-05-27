package ru.sudoteam.cyclecomputer.services;

import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ru.sudoteam.cyclecomputer.app.App;
import ru.sudoteam.cyclecomputer.app.accounts.Account;
import ru.sudoteam.cyclecomputer.app.accounts.Error;
import ru.sudoteam.cyclecomputer.app.accounts.User;
import ru.sudoteam.cyclecomputer.app.eventbus.Event;

/**
 * Created by bagrusss
 */

public class NetworkServiceHelper {


    public static void startLoadFriends(final Context context, final int reqCode) {
        Intent intent = new Intent(context, NetworkService.class);
        intent.setAction(NetworkService.ACTION_DB);
        App.getAccount().friends(new Account.FriendsLoaded() {
            @Override
            public void onFriendsLoaded(ArrayList<User> friends) {
                intent.putExtra(NetworkService.EXTRA_FRIENDS, friends);
                intent.putExtra(NetworkService.EXTRA_REQUEST_CODE, reqCode);
                context.startService(intent);
            }

            @Override
            public void onError(Error error) {
                Event event = new Event();
                event.requestCode = reqCode;
                event.status = error.code;
                EventBus.getDefault().post(new Event());
            }
        });

    }

}
