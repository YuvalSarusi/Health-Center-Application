package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.widget.EditText;

import androidx.annotation.Nullable;


public class DBHelper extends  SQLiteOpenHelper implements DBInterface{

    private static final int NO_USER_NAME_FOUNDED = 0;
    private static final int USER_NAME_DIFFERENT_FROM_PASS = -1;


    private SQLiteDatabase database;

    private static final String TABLE_RECORD = "UserInfo";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USER_NAME = "UserName";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_HEIGHT = "Height";
    private static final String COLUMN_WEIGHT = "Weight";


    private static final String[] allColumns = {COLUMN_ID, COLUMN_USER_NAME, COLUMN_PASSWORD, COLUMN_HEIGHT,COLUMN_WEIGHT};
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USER_NAME + " TEXT," +
            COLUMN_PASSWORD + " TEXT," +
            COLUMN_HEIGHT + " INTEGER," +
            COLUMN_WEIGHT + " INTEGER)";

    public DBHelper(@Nullable Context context) {
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



    public int checkUserLogin(EditText userName, EditText password){
        String [] choseColumns = {COLUMN_ID,COLUMN_USER_NAME, COLUMN_PASSWORD};
        String [] userNameCheck = {userName.getText().toString()};
        database = getWritableDatabase();
        database = getReadableDatabase();
        Cursor selectName= database.query(TABLE_RECORD, choseColumns, COLUMN_USER_NAME +" =?",userNameCheck, null, null, null);
        if(selectName.getCount() > 0){
            selectName.moveToFirst();
            String passwordToName = selectName.getString(selectName.getColumnIndex(COLUMN_PASSWORD));
            if(passwordToName.compareTo(password.getText().toString()) == 0){
                return Integer.parseInt(selectName.getString(selectName.getColumnIndex(COLUMN_ID)));
            }
            else{
                return USER_NAME_DIFFERENT_FROM_PASS;
            }
        }
        else{
            return NO_USER_NAME_FOUNDED;
        }
    }

    public long insert(String userName,String password, int height, int weight) {
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        String[] chosenColumns = {COLUMN_ID};
        String[] userNameCheck = {userName};
        Cursor selectNameId = database.query(TABLE_RECORD, chosenColumns, COLUMN_USER_NAME + " =?", userNameCheck, null, null, null);
        if (selectNameId.getCount() == 0) {
            values.put(COLUMN_USER_NAME, userName);
            values.put(COLUMN_PASSWORD, password);
            values.put(COLUMN_HEIGHT, height);
            values.put(COLUMN_WEIGHT, weight);
            long id = database.insert(TABLE_RECORD, null, values);
            database.close();
            return id;
        }
        else if(selectNameId.getCount()>0)
            return 0;
        else
            return  -1;
    }

    public int getUserHeight(long userId){
        int userHeight =0;
        String [] choseColumns = {COLUMN_ID,COLUMN_HEIGHT};
        String [] stringUserId = {String.valueOf(userId)};
        database = getReadableDatabase();
        Cursor selectId= database.query(TABLE_RECORD, choseColumns, COLUMN_ID +" =?", stringUserId, null, null, null);
        if(selectId.getCount() >0){
            selectId.moveToFirst();
            userHeight = Integer.parseInt(selectId.getString(selectId.getColumnIndex(COLUMN_HEIGHT)));
            return userHeight;
        }
        else
            return 0;
    }

    public int getUserWeight(long userId){
        int userWeight =0;
        String [] choseColumns = {COLUMN_ID,COLUMN_WEIGHT};
        String [] stringUserId = {String.valueOf(userId)};
        database = getReadableDatabase();
        Cursor selectId= database.query(TABLE_RECORD, choseColumns, COLUMN_ID +" =?",stringUserId, null, null, null);
        if(selectId.getCount() >0){
            selectId.moveToFirst();
            userWeight = Integer.parseInt(selectId.getString(selectId.getColumnIndex(COLUMN_WEIGHT)));
            return userWeight;
        }
        else
            return 0;
    }

    public String getUserName(long userId){
        String userName ="";
        String [] choseColumns = {COLUMN_ID,COLUMN_USER_NAME};
        String [] stringUserId = {String.valueOf(userId)};
        database = getReadableDatabase();
        Cursor selectId= database.query(TABLE_RECORD, choseColumns, COLUMN_ID +" =?",stringUserId, null, null, null);
        if(selectId.getCount() >0){
            selectId.moveToFirst();
            userName = selectId.getString(selectId.getColumnIndex(COLUMN_USER_NAME));
            return userName;
        }
        else
            return "";
    }


    public long changePass(String newPass, long userId){
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        String[] chosenColumns = {COLUMN_ID};
        String[] userIdCheck = {String.valueOf(userId)};
        Cursor selectId = database.query(TABLE_RECORD, chosenColumns, COLUMN_ID + " =?", userIdCheck, null, null, null);
        if (selectId.getCount() > 0) {
            values.put(COLUMN_PASSWORD, newPass);
            database.update(TABLE_RECORD,values,COLUMN_ID + "=?",new String []{Long.toString(userId)});
            database.close();
            return userId;
        }
        else
            return  -1;
    }

    public long changeHeight(int newHeight, long userId){
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        String[] choosenColumns = {COLUMN_ID};
        String[] userIdCheck = {String.valueOf(userId)};
        Cursor selectId = database.query(TABLE_RECORD, choosenColumns, COLUMN_ID + " =?", userIdCheck, null, null, null);
        if (selectId.getCount() > 0) {
            values.put(COLUMN_HEIGHT, newHeight);
            database.update(TABLE_RECORD,values,COLUMN_ID + "=?",new String []{Long.toString(userId)});
            database.close();
            return userId;
        }
        else
            return  -1;
    }

    public long changeWeight(int newWeight, long userId){
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        String[] choosenColumns = {COLUMN_ID};
        String[] userIdCheck = {String.valueOf(userId)};
        Cursor selectId = database.query(TABLE_RECORD, choosenColumns, COLUMN_ID + " =?", userIdCheck, null, null, null);
        if (selectId.getCount() > 0) {
            values.put(COLUMN_WEIGHT, newWeight);
            database.update(TABLE_RECORD,values,COLUMN_ID + "=?",new String []{Long.toString(userId)});
            database.close();
            return userId;
        }
        else
            return  -1;
    }

    public void deleteTable(){
        database = getReadableDatabase();
        database.delete(TABLE_RECORD,null,null);
    }

    // get the user back with the id
    // also possible to return only the id
    /*public long insert(String firstName, String lastName)
    {
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRIVATE, firstName);
        values.put(COLUMN_LAST, lastName);

        long id = database.insert(TABLE_RECORD, null, values);
        database.close();
        return id;
    }

    public void deleteById(long id )
    {
        database = getWritableDatabase(); // get access to write e data
        database.delete(TABLE_RECORD, COLUMN_ID + " = " + id, null);
        database.close(); // close the database
    }

    // return all rows in table
    public ArrayList<String> selectAll()
    {
        database = getReadableDatabase(); // get access to read the database
        ArrayList<String> users = new ArrayList<>();
        Cursor cursor = database.query(TABLE_RECORD, allColumns, null, null, null, null, null); // cursor points at a certain row
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_PRIVATE))+ " "+ cursor.getString(cursor.getColumnIndex(COLUMN_LAST));
                users.add(name);
            }
        }
        database.close();
        return users;
    }*/



}