package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.time.LocalDateTime ;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DBUserCalc extends SQLiteOpenHelper implements DBInterface{



    private SQLiteDatabase database;

    private static final String TABLE_RECORD = "UserFoodCalc";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USER_ID = "UserId";
    private static final String COLUMN_CAL = "Cal";
    private static final String COLUMN_CAR = "Car";
    private static final String COLUMN_FAT = "Fat";
    private static final String COLUMN_PRO = "Pro";
    private static final String COLUMN_lAST_CONNECT ="LastConnect";

    private static final String[] allColumns = {COLUMN_ID, COLUMN_USER_ID, COLUMN_CAL, COLUMN_CAR,COLUMN_FAT,COLUMN_PRO};

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USER_ID + " INTEGER," +
            COLUMN_CAL + " INTEGER," +
            COLUMN_CAR + " INTEGER," +
            COLUMN_FAT + " INTEGER," +
            COLUMN_lAST_CONNECT + " INTEGER," +
            COLUMN_PRO + " INTEGER)";

    public DBUserCalc(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_RECORD);
        onCreate(sqLiteDatabase);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long insert(long userId) {
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        String[] chosenColumns = {COLUMN_ID};
        String[] userIdCheck = {String.valueOf(userId)};
        Cursor selectUserId = database.query(TABLE_RECORD, chosenColumns, COLUMN_USER_ID + " =?", userIdCheck, null, null, null);
        if (selectUserId.getCount() == 0) {
            values.put(COLUMN_USER_ID, userId);
            values.put(COLUMN_CAL, 0);
            values.put(COLUMN_CAR, 0);
            values.put(COLUMN_FAT, 0);
            values.put(COLUMN_PRO, 0);
            LocalDateTime now = LocalDateTime.now();
            int dayOfMonth = now.getDayOfMonth();
            values.put(COLUMN_lAST_CONNECT,dayOfMonth);
            long id = database.insert(TABLE_RECORD, null, values);
            database.close();
            return id;
        }
        else if(selectUserId.getCount()>0)
            return 0;
        else
            return  -1;
    }

    public long resetUserCalc(long userId, int day){
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        String[] chosenColumns = {COLUMN_ID};
        String[] userIdCheck = {String.valueOf(userId)};
        Cursor selectId = database.query(TABLE_RECORD, chosenColumns, COLUMN_USER_ID + " =?", userIdCheck, null, null, null);
        if (selectId.getCount() > 0) {
            values.put(COLUMN_lAST_CONNECT, day);
            values.put(COLUMN_CAL, 0);
            values.put(COLUMN_CAR, 0);
            values.put(COLUMN_FAT, 0);
            values.put(COLUMN_PRO, 0);
            database.update(TABLE_RECORD,values,COLUMN_USER_ID + " =?" ,new String []{Long.toString(userId)});
            database.close();
            return userId;
        }
        else
            return  -1;
    }



    public long setNewValues(long userId, int cal, int car, int fat, int pro){
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        String[] chosenColumns = {COLUMN_ID};
        String[] userIdCheck = {String.valueOf(userId)};
        Cursor selectId = database.query(TABLE_RECORD, chosenColumns, COLUMN_USER_ID + " =?", userIdCheck, null, null, null);
        if (selectId.getCount() > 0) {
            values.put(COLUMN_CAL, (this.getUserCal(userId)+cal));
            values.put(COLUMN_CAR, (this.getUserCar(userId)+car));
            values.put(COLUMN_FAT, (this.getUserFat(userId)+fat));
            values.put(COLUMN_PRO, (this.getUserPro(userId)+pro));
            database.update(TABLE_RECORD,values,COLUMN_USER_ID + " =?",new String []{Long.toString(userId)});
            database.close();
            return userId;
        }
        else
            return  -1;
    }

    public int getLastConnectDay(long userId){
        int lastDay = 0;
        String [] choseColumns = {COLUMN_ID,COLUMN_lAST_CONNECT};
        String [] stringUserId = {String.valueOf(userId)};
        database = getReadableDatabase();
        Cursor selectId= database.query(TABLE_RECORD, choseColumns, COLUMN_USER_ID +" =?",stringUserId, null, null, null);
        if(selectId.getCount() >0){
            selectId.moveToFirst();
            lastDay = Integer.parseInt(selectId.getString(selectId.getColumnIndex(COLUMN_lAST_CONNECT)));
            return lastDay;
        }
        else
            return 0;
    }

    public int getUserCal(long userId){
        int userCal =0;
        String [] choseColumns = {COLUMN_ID,COLUMN_CAL};
        String [] stringUserId = {String.valueOf(userId)};
        database = getReadableDatabase();
        Cursor selectId= database.query(TABLE_RECORD, choseColumns, COLUMN_USER_ID +" =?", stringUserId, null, null, null);
        if(selectId.getCount() >0){
            selectId.moveToFirst();
            userCal = Integer.parseInt(selectId.getString(selectId.getColumnIndex(COLUMN_CAL)));
            return userCal;
        }
        else
            return 0;
    }
    public int getUserCar(long userId){
        int userCar =0;
        String [] choseColumns = {COLUMN_ID,COLUMN_CAR};
        String [] stringUserId = {String.valueOf(userId)};
        database = getReadableDatabase();
        Cursor selectId= database.query(TABLE_RECORD, choseColumns, COLUMN_USER_ID +" =?", stringUserId, null, null, null);
        if(selectId.getCount() >0){
            selectId.moveToFirst();
            userCar = Integer.parseInt(selectId.getString(selectId.getColumnIndex(COLUMN_CAR)));
            return userCar;
        }
        else
            return 0;
    }
    public int getUserFat(long userId){
        int userFat =0;
        String [] choseColumns = {COLUMN_ID,COLUMN_FAT};
        String [] stringUserId = {String.valueOf(userId)};
        database = getReadableDatabase();
        Cursor selectId= database.query(TABLE_RECORD, choseColumns, COLUMN_USER_ID +" =?", stringUserId, null, null, null);
        if(selectId.getCount() >0){
            selectId.moveToFirst();
            userFat = Integer.parseInt(selectId.getString(selectId.getColumnIndex(COLUMN_FAT)));
            return userFat;
        }
        else
            return 0;
    }
    public int getUserPro(long userId){
        int userPro =0;
        String [] choseColumns = {COLUMN_ID,COLUMN_PRO};
        String [] stringUserId = {String.valueOf(userId)};
        database = getReadableDatabase();
        Cursor selectId= database.query(TABLE_RECORD, choseColumns, COLUMN_USER_ID +" =?", stringUserId, null, null, null);
        if(selectId.getCount() >0){
            selectId.moveToFirst();
            userPro = Integer.parseInt(selectId.getString(selectId.getColumnIndex(COLUMN_PRO)));
            return userPro;
        }
        else
            return 0;
    }

    public void deleteTable(){
        database = getReadableDatabase();
        database.delete(TABLE_RECORD,null,null);
    }
}
