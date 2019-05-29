package com.example.mycontactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact2019";
    public static final String TABLE_NAME = "Contact_table";
    public static final String ID = "ID";
    public static final String COLUMN_NAME_CONTACT = "contact";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_CONTACT + " TEXT)";

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

    public boolean insertData(String name){
        Log.d("MyContactApp", "DatabaseHelper: Inserting data");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CONTACT, name);
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

    }
}