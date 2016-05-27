package ru.sudoteam.cyclecomputer.services;

import android.app.IntentService;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ru.sudoteam.cyclecomputer.app.accounts.User;
import ru.sudoteam.cyclecomputer.app.eventbus.Event;
import ru.sudoteam.cyclecomputer.data.HelperDB;

public class NetworkService extends IntentService {

    public static final String ACTION_DB = "ru.sudoteam.cyclecomputer.ACTION_DB";
    public static final String EXTRA_FRIENDS = "FRIENDS";
    public static final String EXTRA_REQUEST_CODE = "REQUEST_CODE";

    EventBus mBus = EventBus.getDefault();

    public NetworkService() {
        super(NetworkService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@NotNull Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_DB:
                ArrayList<User> friends = intent.getParcelableArrayListExtra(EXTRA_FRIENDS);
                if (HelperDB.getInstance(this).insertFriends(friends)) {
                    Event event = new Event();
                    event.status = Event.OK;
                    event.action = intent.getIntExtra(EXTRA_REQUEST_CODE, 0);
                    mBus.post(event);
                }
                break;
            default:

        }

    }

}
