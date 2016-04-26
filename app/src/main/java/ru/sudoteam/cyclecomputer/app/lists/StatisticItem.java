package ru.sudoteam.cyclecomputer.app.lists;

/**
 * Created by bagrusss on 19.04.16.
 *
 */
public class StatisticItem {

    public StatisticItem(int resId, String title, String value) {
        this.resId = resId;
        this.title = title;
        this.value = value;
    }

    String title;
    String value;
    int resId;
}
