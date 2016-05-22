package ru.sudoteam.cyclecomputer.services;

import android.app.IntentService;
import android.content.Intent;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import ru.sudoteam.cyclecomputer.app.App;
import ru.sudoteam.cyclecomputer.app.eventbus.Event;
import ru.sudoteam.cyclecomputer.data.HelperDB;

public class NetworkService extends IntentService {

    public static final String ACTION_DB = "ru.sudoteam.cyclecomputer.ACTION_DB";
    public static final String EXTRA_FRIENDS_JSON = "JSON_FRIENDS";
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
                JsonArray ja = App.GSON.fromJson(intent.getStringExtra(EXTRA_FRIENDS_JSON),
                        JsonObject.class).getAsJsonArray("response");
                if (HelperDB.getInstance(this).insertFriends(ja)) {
                    Event event = new Event();
                    event.staus = Event.OK;
                    event.action = intent.getIntExtra(EXTRA_REQUEST_CODE, 0);
                    mBus.post(event);
                }
                break;
            default:

        }

    }

}
