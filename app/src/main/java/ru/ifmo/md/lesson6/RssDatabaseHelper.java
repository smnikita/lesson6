package ru.ifmo.md.lesson6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RssDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "rss_db";
    private static final int VERSION = 1;

    private static final String TABLE_CHANNEL = "channel";
    private static final String COLUMN_CHANNEL_ID = "_id";
    private static final String COLUMN_CHANNEL_TITLE = "title";
    private static final String COLUMN_CHANNEL_URL = "url";

    private static final String TABLE_ITEM = "item";
    private static final String COLUMN_ITEM_ID = "_id";
    private static final String COLUMN_ITEM_TITLE = "title";
    private static final String COLUMN_ITEM_DESCRIPTION = "description";
    private static final String COLUMN_ITEM_URL = "url";
    private static final String COLUMN_ITEM_DATE = "date";
    private static final String COLUMN_ITEM_CHANNEL_TITLE = "channel_title";

    private static final String INIT_CHANNEL_TABLE =
        "CREATE TABLE " + TABLE_CHANNEL + " (" +
        COLUMN_CHANNEL_ID + " INTEGER " + "PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_CHANNEL_TITLE + " TEXT, " +
        COLUMN_CHANNEL_URL + " TEXT);";

    private static final String INIT_ITEM_TABLE =
        "CREATE TABLE " + TABLE_ITEM + " (" +
        COLUMN_ITEM_ID + " INTEGER " + "PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_ITEM_TITLE + " TEXT, " +
        COLUMN_ITEM_DESCRIPTION + " TEXT, " +
        COLUMN_ITEM_URL + " TEXT, " +
        COLUMN_ITEM_DATE + " TEXT, " +
        COLUMN_ITEM_CHANNEL_TITLE  + " TEXT);";

    private SQLiteDatabase mReadableDB;
    private SQLiteDatabase mWritableDB;

    public RssDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        mReadableDB = getReadableDatabase();
        mWritableDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(INIT_CHANNEL_TABLE);
        db.execSQL(INIT_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF IT EXIST " + TABLE_CHANNEL);
        db.execSQL("DROP TABLE IF IT EXIST " + TABLE_ITEM);
        onCreate(db);
    }

    public long insertChannel(RssChannel channel) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CHANNEL_TITLE, channel.getTitle());
        cv.put(COLUMN_CHANNEL_URL, channel.getUrl());
        return mWritableDB.insert(TABLE_CHANNEL, null, cv);
    }

    public long insertItem(RssItem item) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_TITLE, item.getTitle());
        cv.put(COLUMN_ITEM_URL, item.getUrl());
        cv.put(COLUMN_ITEM_DESCRIPTION, item.getDescription());
        cv.put(COLUMN_ITEM_DATE, item.getDate());
        cv.put(COLUMN_ITEM_CHANNEL_TITLE, item.getChannelTitle());
        return mWritableDB.insert(TABLE_ITEM, null, cv);
    }

    public void deleteChannel(String title) {
        mWritableDB.delete(TABLE_CHANNEL, COLUMN_CHANNEL_TITLE + " = \"" + title + "\"", null);
        mWritableDB.delete(TABLE_ITEM, COLUMN_ITEM_CHANNEL_TITLE + " = \"" + title + "\"", null);
    }

    public Cursor getAllChannels() {
        return mReadableDB.query(
            TABLE_CHANNEL,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );
    }

    public Cursor getAllItems(String channelTitle) {
        Cursor cursor = mReadableDB.query(
            TABLE_ITEM,
            null,
            COLUMN_ITEM_CHANNEL_TITLE + " = ?",
            new String[]{String.valueOf(channelTitle)},
            null,
            null,
            null,
            null
        );
        return cursor;
    }


}
