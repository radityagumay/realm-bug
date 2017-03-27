package com.radityalabs.realmsample;

import android.app.Application;

import com.radityalabs.realmsample.sqlite.SQLiteHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by radityagumay on 3/24/17.
 */

public class RealmApplication extends Application {

    private static RealmConfiguration mRealmConfiguration;
    private static SQLiteHelper helper;

    public static RealmConfiguration getRealmConfiguration() {
        return mRealmConfiguration;
    }

    public static SQLiteHelper getHelper(){
        return helper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        mRealmConfiguration = realmConfiguration();
        helper = SQLiteHelper.getInstance(this);
    }

    private RealmConfiguration realmConfiguration() {
        return new RealmConfiguration.Builder()
                .name("RealmSample.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}
