package ru.sudoteam.cyclecomputer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bagrusss on 22.03.16.
 * Used for keep everyday statistics
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "cycle.db";
    public static final String TABLE_STATISTICS = "statistics";
    public static final String TABLE_DAY_STATISTICS = "day_statistics";
    public static final String TABLE_FRIENDS = "friends";

    public static final String ID = "_id";
    public static final String DISTANCE = "distance";
    public static final String DATE = "date";

    public static final String TIME_START = "time_start";
    public static final String TIME_STOP = "time_stop";

    public static final String USER_ID = "time_stop";


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
