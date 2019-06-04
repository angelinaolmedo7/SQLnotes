package com.example.mycontactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact2019";
    public static final String TABLE_NAME = "Contact_table";
    public static final String ID = "ID";
    public static final String COLUMN_NAME_CONTACT = "contact";
    public static final String COLUMN_CELL_CONTACT = "cell";
    public static final String COLUMN_HOME_CONTACT = "home";
    public static final String COLUMN_EMAIL_CONTACT = "email";
    public static final String COLUMN_ADDRESS_CONTACT = "address";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_CONTACT + " TEXT," +
                    COLUMN_CELL_CONTACT + " TEXT," +
                    COLUMN_HOME_CONTACT + " TEXT," +
                    COLUMN_EMAIL_CONTACT + " TEXT," +
                    COLUMN_ADDRESS_CONTACT + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("MyContactApp", "DatabaseHelper: Constructed DatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.d("MyContactApp", "DatabaseHelper: Created database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        Log.d("MyContactApp", "DatabaseHelper: Upgraded database");
        onCreate(db);
    }

    public boolean insertData(String name, String cell, String home, String email, String address){
        Log.d("MyContactApp", "DatabaseHelper: Inserting data");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CONTACT, name);
        contentValues.put(COLUMN_CELL_CONTACT, cell);
        contentValues.put(COLUMN_HOME_CONTACT, home);
        contentValues.put(COLUMN_EMAIL_CONTACT, email);
        contentValues.put(COLUMN_ADDRESS_CONTACT, address);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            Log.d("MyContactApp", "DatabaseHelper: Contact insert - FAILED");
            return false;
        }
        else {
            Log.d("MyContactApp", "DatabaseHelper: Contact insert - PASSED");
            return true;
        }
    }

    public Cursor getAllData(){
        Log.d("MyContactApp", "DatabaseHelper: getAllData called");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("MyContactApp", "DatabaseHelper: Got writable db");
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        Log.d("MyContactApp", "DatabaseHelper: Made cursor, count is "+res.getCount());
        return res;
    }

    public boolean deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

        return true;
    }

}
