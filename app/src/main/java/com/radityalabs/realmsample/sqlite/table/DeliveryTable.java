package com.radityalabs.realmsample.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by radityagumay on 3/26/17.
 */

public class DeliveryTable {

    private static final String TAG = DeliveryTable.class.getSimpleName();

    public static final String TABLE_NAME = "delivery_time";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "time";
    public static final String COLUMN_PACK_ID = "pack_id";

    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_NAME + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, "
            + COLUMN_PACK_ID + " INTEGER NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + "FOREIGN KEY(" + COLUMN_PACK_ID + ") REFERENCES " + PackTable.TABLE_NAME + "(" + PackTable.COLUMN_ID + ") "
            + ");";

    public static void onCreate(SQLiteDatabase db) {
        Log.d(TAG, DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
