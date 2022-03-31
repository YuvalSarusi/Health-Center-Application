package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBFoodHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private static final String DATABASENAME = "HealthCenter3.db";
    private static final int DATABASEVERSION = 6;

    private static final String TABLE_RECORD = "UserFoodList";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USER_ID = "UserId";
    private static final String COLUMN_FOOD_NAME = "FoodName";
    private static final String COLUMN_SUM_OF_CAL = "SumOfCal";
    private static final String COLUMN_SUM_OF_CAR = "SumOfCar";
    private static final String COLUMN_SUM_OF_PRO = "SumOfPro";
    private static final String COLUMN_SUM_OF_FAT = "SumOfFat";
    private static final String COLUMN_PHOTO = "FoodImage";

    private static final String[] allColumns = {COLUMN_ID, COLUMN_FOOD_NAME, COLUMN_SUM_OF_CAL, COLUMN_SUM_OF_CAR, COLUMN_SUM_OF_PRO, COLUMN_SUM_OF_FAT, COLUMN_PHOTO};
    private static final String CREATE_TABLE_FOOD = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USER_ID + " INTEGER," +
            COLUMN_FOOD_NAME + " TEXT," +
            COLUMN_SUM_OF_CAL + " INTEGER," +
            COLUMN_SUM_OF_CAR + " INTEGER," +
            COLUMN_SUM_OF_PRO + " INTEGER," +
            COLUMN_SUM_OF_FAT + " INTEGER," +
            COLUMN_PHOTO + " BLOB );";

    public DBFoodHelper(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FOOD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_RECORD);
        onCreate(sqLiteDatabase);
    }

    public long insert(long userId, Food food) {
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME, food.getNameOfFood());
        values.put(COLUMN_USER_ID,userId);
        values.put(COLUMN_SUM_OF_CAL, food.getSumOfCal());
        values.put(COLUMN_SUM_OF_CAR, food.getSumOfCar());
        values.put(COLUMN_SUM_OF_PRO, food.getSumOfPro());
        values.put(COLUMN_SUM_OF_FAT, food.getSumOfFat());
        values.put(COLUMN_PHOTO,getBytes(food.getPhoto()));
        long id = database.insert(TABLE_RECORD, null, values);
        database.close();
        return id;
    }

    public ArrayList<String> selectNames()
    {
        database = getReadableDatabase(); // get access to read the database
        ArrayList<String> users = new ArrayList<String>();
        Cursor cursor = database.query(TABLE_RECORD, allColumns, null, null, null, null, null); // cursor points at a certain row
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME));
                int sumOfCal = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SUM_OF_CAL)));
                int sumOfCar = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SUM_OF_CAR)));
                int sumOfPro = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SUM_OF_PRO)));
                int sumOfFat = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SUM_OF_FAT)));
                //byte[] photo = Byte.parseByte(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                users.add(name);
            }
        }
        database.close();
        return users;
    }
    public void deleteById(long id)
    {
        database = getWritableDatabase(); // get access to write e data
        database.delete(TABLE_RECORD, COLUMN_ID + " =?",new String[]{Long.toString(id)});
        database.close(); // close the database
    }
    public ArrayList<Food> selectAll(long userId)
    {
        database = getReadableDatabase(); // get access to read the database
        ArrayList<Food> users = new ArrayList<Food>();
        String [] userIdString = {String.valueOf(userId)};
        Cursor cursor = database.query(TABLE_RECORD, allColumns, COLUMN_USER_ID +" =?", userIdString, null, null, null); // cursor points at a certain row
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME));
                int sumOfCal = cursor.getInt(cursor.getColumnIndex(COLUMN_SUM_OF_CAL));
                int sumOfCar = cursor.getInt(cursor.getColumnIndex(COLUMN_SUM_OF_CAR));
                int sumOfPro = cursor.getInt(cursor.getColumnIndex(COLUMN_SUM_OF_PRO));
                int sumOfFat = cursor.getInt(cursor.getColumnIndex(COLUMN_SUM_OF_FAT));
                byte[] photo = cursor.getBlob(cursor.getColumnIndex(COLUMN_PHOTO));
                Food food = new Food(name,sumOfCal,sumOfCar,sumOfPro,sumOfFat,getImage(photo));
                users.add(food);
            }
        }
        database.close();
        return users;
    }
    private  byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
    private Bitmap getImage(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void deleteTable(){
        database = getReadableDatabase();
        database.delete(TABLE_RECORD,null,null);
    }

}
