package ru.sudoteam.cyclecomputer.app.eventbus;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by bagrusss on 23.05.16.
 * *
 */
public class UniversalEvent extends Event {

    public ConcurrentMap<String, Object> params = new ConcurrentHashMap<>();

}
