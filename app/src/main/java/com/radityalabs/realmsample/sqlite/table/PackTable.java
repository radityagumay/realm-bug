package com.radityalabs.realmsample.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by radityagumay on 3/26/17.
 */

public class PackTable {

    private static final String TAG = PackTable.class.getSimpleName();

    public static final String TABLE_NAME = "pack";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";

    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_NAME + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL "
            + ");";

    public static void onCreate(SQLiteDatabase db) {
        Log.d(TAG, DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
