package ru.sudoteam.cyclecomputer.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.sdk.api.model.VKApiUser;

import org.jetbrains.annotations.NotNull;

/**
 * Created by bagrusss on 22.03.16.
 * Used for keep statistics
 */

public class HelperDB extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "cycle.db";

    private static HelperDB mInstance;

    public static final String TABLE_STATISTICS = "statistics";
    public static final String TABLE_DAY_STATISTICS = "day_statistics";
    public static final String TABLE_FRIENDS = "friends";

    public static final String ID = "_id";

    //table statistics
    public static final String DISTANCE = "distance";
    public static final String DATE = "d_date";

    //table day_statistics
    public static final String TIME_START = "time_start";
    public static final String TIME_STOP = "time_stop";

    //friends
    public static final String USER_ID = "user_id";
    public static final String FIRST_NAME = "first_name";
    public static final String SECOND_NAME = "second_name";
    public static final String IMAGE_URL = "image_url";

    private static final String INSERT_FRIENDS = "INSERT OR IGNORE INTO " + TABLE_FRIENDS +
            " (" + USER_ID + ',' + FIRST_NAME + ',' + SECOND_NAME + ',' + IMAGE_URL + ")" +
            " VALUES(?,?,?,?);";
    private static SQLiteDatabase mDB;

    private HelperDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mDB = getWritableDatabase();
    }

    public static HelperDB getInstance(Context cont) {
        HelperDB localInstance = mInstance;
        if (localInstance == null) {
            synchronized (HelperDB.class) {
                localInstance = mInstance;
                if (localInstance == null) {
                    mInstance = localInstance = new HelperDB(cont);
                }
            }
        }
        return localInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("CREATE TABLE " + TABLE_DAY_STATISTICS + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DISTANCE + " UNSIGNED INTEGER, " +
                    TIME_START + " INTEGER, " +
                    TIME_STOP + " INTEGER);"
            );
            db.execSQL("CREATE TABLE " + TABLE_STATISTICS + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DATE + " INTEGER, " +
                    DISTANCE + " UNSIGNED INTEGER);"
            );
            db.execSQL("CREATE TABLE " + TABLE_FRIENDS + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_ID + " UNSIGNED INTEGER UNIQUE, " +
                    FIRST_NAME + " TEXT, " +
                    SECOND_NAME + " TEXT, " +
                    IMAGE_URL + " TEXT);"
            );
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getAppFriends() {
        return mDB.query(TABLE_FRIENDS, null, null, null, null, null, null);
    }

    public boolean insertFriends(@NotNull JsonArray ja) {
        mDB.beginTransaction();
        SQLiteStatement statement = mDB.compileStatement(INSERT_FRIENDS);
        boolean isOK = false;
        try {
            for (JsonElement je : ja) {
                JsonObject jo = je.getAsJsonObject();
                statement.bindLong(1, jo.get("id").getAsLong());
                statement.bindString(2, jo.get("first_name").getAsString());
                statement.bindString(3, jo.get("last_name").getAsString());
                statement.bindString(4, jo.get(VKApiUser.FIELD_PHOTO_200).getAsString());
                statement.executeInsert();
            }
            mDB.setTransactionSuccessful();
            isOK = true;
        } catch (SQLException e) {
            Log.e("SQLite error", e.getLocalizedMessage());
        } finally {
            mDB.endTransaction();
            statement.close();
        }
        return isOK;
    }

    public static void closeDB() {
        if (mDB != null && mDB.isOpen())
            mDB.close();
        mInstance = null;
    }
}
