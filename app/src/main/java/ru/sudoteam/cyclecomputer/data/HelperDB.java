package ru.sudoteam.cyclecomputer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bagrusss on 22.03.16.
 * Used for keep statistics
 */

public class HelperDB extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "cycle.db";

    public static final String TABLE_STATISTICS = "statistics";
    public static final String TABLE_DAY_STATISTICS = "day_statistics";
    public static final String TABLE_FRIENDS = "friends";

    public static final String ID = " _id ";

    //table statictics
    public static final String DISTANCE = " distance ";
    public static final String DATE = " d_date ";

    //table day_statistics
    public static final String TIME_START = " time_start ";
    public static final String TIME_STOP = " time_stop ";

    //friends
    public static final String USER_ID = " user_id ";
    public static final String FIRST_NAME = " first_name ";
    public static final String SECOND_NAME = " second_name ";
    public static final String IMAGE_URL = " image_url ";

    public HelperDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("CREATE TABLE " + TABLE_DAY_STATISTICS + '(' +
                    ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DISTANCE + "UNSIGNED INTEGER, " +
                    TIME_START + "INTEGER," +
                    TIME_STOP + "INTEGER);"
            );
            db.execSQL("CREATE TABLE " + TABLE_STATISTICS + '(' +
                    ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DATE + "INTEGER," +
                    DISTANCE + "UNSIGNED INTEGER);"
            );
            db.execSQL("CREATE TABLE " + TABLE_FRIENDS + '(' +
                    ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USER_ID + "UNSIGNED INTEGER UNIQUE," +
                    FIRST_NAME + "TEXT," +
                    SECOND_NAME + "TEXT," +
                    IMAGE_URL + "TEXT);"
            );
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
