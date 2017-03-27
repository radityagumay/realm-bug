package com.radityalabs.realmsample.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by radityagumay on 3/26/17.
 */

public class FoodTable {

    private static final String TAG = FoodTable.class.getSimpleName();

    public static final String TABLE_NAME = "food";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FOOD_ID = "food_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CALORIE = "calorie";
    public static final String COLUMN_STOCK = "stock";
    public static final String COLUMN_PACK_ID = "pack_id";
    public static final String COLUMN_CREATED_TIME = "created_time";

    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_NAME + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FOOD_ID + " INTEGER NOT NULL, "
            + COLUMN_PACK_ID + " INTEGER NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_IMAGE + " TEXT NOT NULL, "
            + COLUMN_PRICE + " INTEGER NOT NULL, "
            + COLUMN_CALORIE + " INTEGER NOT NULL, "
            + COLUMN_STOCK + " INTEGER NOT NULL, "
            + COLUMN_CREATED_TIME + " LONG NOT NULL, "
            + "FOREIGN KEY(" + COLUMN_PACK_ID + ") REFERENCES " + PackTable.TABLE_NAME + "(" + PackTable.COLUMN_ID + ") "
            + ");";

    public static void onCreate(SQLiteDatabase db) {
        Log.d(TAG, DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
