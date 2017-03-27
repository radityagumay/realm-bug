package com.radityalabs.realmsample;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by radityagumay on 3/24/17.
 */

public class FoodObject extends RealmObject {

    @Ignore
    public static final String FOOD_ID = "id";

    @PrimaryKey
    public long id;
    public String name;
    public int quantity;
    public long createdTime;
}
