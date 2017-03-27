package com.radityalabs.realmsample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.radityalabs.realmsample.sqlite.SQLiteHelper;
import com.radityalabs.realmsample.sqlite.table.FoodTable;
import com.radityalabs.realmsample.sqlite.table.PackTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupData();
    }

    private void setupData() {
        List<PackModel> packs = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            PackModel packModel = new PackModel();
            packModel.id = i;
            packModel.name = "Pack " + new Random().nextInt(100);

            List<FoodModel> foods = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                FoodModel foodModel = new FoodModel();
                foodModel.id = j;
                foodModel.packId = i;
                foodModel.name = "Food " + new Random().nextInt(100);
                foodModel.stock = new Random().nextInt(100);
                foodModel.price = new Random().nextInt(100);
                foodModel.image = "hrrp";
                foodModel.calorie = new Random().nextInt(100);
                foodModel.createTime = System.nanoTime();
                foods.add(foodModel);
            }
            packModel.foods.addAll(foods);
            packs.add(packModel);
        }

        insertdb(packs);
    }

    private final SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(this);

    private void insertdb(final List<PackModel> packs) {
        new AsyncTask<Void, Void, List<PackModel>>() {
            @Override
            protected List<PackModel> doInBackground(Void... params) {
                SQLiteDatabase db = sqliteHelper.getWritableDatabase();
                try {
                    String packQuery = "INSERT or REPLACE INTO " + PackTable.TABLE_NAME + " VALUES (?,?);";
                    SQLiteStatement packStatement = db.compileStatement(packQuery);
                    db.beginTransaction();
                    for (int i = 0; i < packs.size(); i++) {
                        packStatement.clearBindings();
                        packStatement.bindLong(1, packs.get(i).id);
                        packStatement.bindString(2, packs.get(i).name);
                        packStatement.execute();

                        try {
                            String foodQuery = "INSERT or REPLACE INTO " + FoodTable.TABLE_NAME + " VALUES (?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement foodStatement = db.compileStatement(foodQuery);
                            db.beginTransaction();
                            for (int j = 0; j < packs.get(i).foods.size(); j++) {
                                foodStatement.clearBindings();
                                foodStatement.bindLong(1, packs.get(i).foods.get(j).id);
                                foodStatement.bindLong(2, packs.get(i).foods.get(j).foodId);
                                foodStatement.bindLong(3, packs.get(i).id);
                                foodStatement.bindString(4, packs.get(i).foods.get(j).name);
                                foodStatement.bindString(5, packs.get(i).foods.get(j).image);
                                foodStatement.bindLong(6, packs.get(i).foods.get(j).price);
                                foodStatement.bindLong(7, packs.get(i).foods.get(j).calorie);
                                foodStatement.bindLong(8, packs.get(i).foods.get(j).stock);
                                foodStatement.bindLong(9, packs.get(i).foods.get(j).createTime);
                                foodStatement.execute();
                            }
                            db.setTransactionSuccessful();
                            db.endTransaction();
                        } catch (Exception ex) {
                            Log.e("TAG", ex.getMessage(), ex);
                        }
                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                } catch (Exception ex) {
                    Log.e("TAG", ex.getMessage(), ex);
                }

                /* query */
                List<PackModel> packs = new ArrayList<>();
                String packQuery = "SELECT * FROM " + PackTable.TABLE_NAME;
                Cursor packCursor = db.rawQuery(packQuery, null);
                try {
                    while (packCursor.moveToNext()) {
                        int packId = packCursor.getInt(0);
                        String packName = packCursor.getString(1);

                        String foodQuery = "SELECT * FROM " + FoodTable.TABLE_NAME + " WHERE " + FoodTable.COLUMN_PACK_ID + " = " + packId +
                                " GROUP BY food_id ORDER BY id DESC";
                        Cursor foodCursor = db.rawQuery(foodQuery, null);
                        List<FoodModel> foods = new ArrayList<>();
                        try {
                            while (foodCursor.moveToNext()) {
                                FoodModel food = new FoodModel();
                                food.id = foodCursor.getInt(0);
                                food.foodId = foodCursor.getInt(1);
                                food.packId = foodCursor.getInt(2);
                                food.name = foodCursor.getString(3);
                                food.image = foodCursor.getString(4);
                                food.price = foodCursor.getInt(5);
                                food.calorie = foodCursor.getInt(6);
                                food.stock = foodCursor.getInt(7);
                                food.createTime = foodCursor.getLong(8);
                                foods.add(food);
                            }
                        } finally {
                            if (foodCursor != null) {
                                foodCursor.close();
                            }
                        }
                        PackModel packModel = new PackModel();
                        packModel.id = packId;
                        packModel.name = packName;
                        packModel.foods.addAll(foods);
                        packs.add(packModel);
                    }
                } finally {
                    if (packCursor != null) {
                        packCursor.close();
                    }
                }
                return packs;
            }

            @Override
            protected void onPostExecute(List<PackModel> packObjects) {
                super.onPostExecute(packObjects);
            }
        }.execute();
    }


    /*private void insertdb(final List<PackModel> items) {
        new AsyncTask<Void, Void, List<PackObject>>() {
            @Override
            protected List<PackObject> doInBackground(Void... params) {
                final List<PackObject> packsObject = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    PackObject packObject = new PackObject();
                    packObject.id = items.get(i).id;
                    packObject.name = items.get(i).name;

                    List<FoodObject> foodsObject = new ArrayList<>();
                    for (int j = 0; j < items.get(i).foods.size(); j++) {
                        FoodObject foodObject = new FoodObject();
                        foodObject.id = items.get(i).foods.get(j).id;
                        foodObject.name = items.get(i).foods.get(j).name;
                        foodObject.createdTime = items.get(i).foods.get(j).createTime;

                        foodsObject.add(foodObject);
                    }
                    packObject.foods.addAll(foodsObject);
                    packsObject.add(packObject);
                }

                Realm realm = Realm.getInstance(RealmApplication.getRealmConfiguration());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(packsObject);
                    }
                });

                RealmResults<PackObject> locPack = realm.where(PackObject.class).findAll();
                for(int i = 0; i < locPack.size(); i++){
                    RealmResults<FoodObject> locFood = realm.where(locPack.ge)
                }



                return newPack;
            }

            @Override
            protected void onPostExecute(List<PackObject> items) {
                super.onPostExecute(items);
                Log.d("Test", "");
            }
        }.execute();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
