package com.radityalabs.realmsample.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.radityalabs.realmsample.sqlite.table.DeliveryTable;
import com.radityalabs.realmsample.sqlite.table.FoodTable;
import com.radityalabs.realmsample.sqlite.table.PackTable;


/**
 * Created by radityagumay on 3/26/17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final Object LOCK = new Object();
    private static SQLiteHelper sInstance;
    private static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/warisidi.db";

    public static SQLiteHelper getInstance(Context context) {
        synchronized (LOCK) {
            if (sInstance == null) {
                sInstance = new SQLiteHelper(context);
            }
        }
        return sInstance;
    }

    public SQLiteHelper(Context context) {
        super(context, SQLConfig.SQLConfig, null, SQLConfig.DATABASE_VERSION);
        //super(context, path, null, SQLConfig.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        PackTable.onCreate(db);
        DeliveryTable.onCreate(db);
        FoodTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        PackTable.onUpgrade(db, oldVersion, newVersion);
        DeliveryTable.onUpgrade(db, oldVersion, newVersion);
        FoodTable.onUpgrade(db, oldVersion, newVersion);
    }
}
