package com.radityalabs.realmsample;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by radityagumay on 3/24/17.
 */

public class PackObject extends RealmObject {

    @PrimaryKey
    public long id;
    public String name;
    public RealmList<FoodObject> foods = new RealmList<>();
}
