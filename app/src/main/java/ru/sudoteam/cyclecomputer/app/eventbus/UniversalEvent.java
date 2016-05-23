package ru.sudoteam.cyclecomputer.app.eventbus;

import java.util.Map;

/**
 * Created by bagrusss on 23.05.16.
 * *
 */
public class UniversalEvent extends Event {

    public Object mToWhom;
    public Map<String, Object> params;

}
