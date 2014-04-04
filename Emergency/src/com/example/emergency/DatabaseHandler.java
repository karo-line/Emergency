package com.example.emergency;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "persons";
 
    // Login table name
    private static final String TABLE_LOGIN = "markers";
 
    // Login Table Columns names
    //private static final String KEY_ID = "id";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LON = "lon";
    private static final String KEY_UID = "uid";
    private static final String KEY_NATIONALITY = "nationality";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_REASON = "reason";
    private static final String KEY_ALIAS = "alias";
    //private static final String KEY_CREATED_AT = "created_at";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_UID + " INTEGER PRIMARY KEY,"
                + KEY_LAT + " DOUBLE,"
                + KEY_LON + " DOUBLE" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(double name, double email, int uid) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_LAT, name); // Name
        values.put(KEY_LON, email); // Email
        values.put(KEY_UID, uid); // Email
       
 
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }
     
    /**
     * Getting user data from database
     * */
    public HashMap<String, Double> getUserDetails(){
        HashMap<String,Double> user = new HashMap<String,Double>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
          
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("lat", cursor.getDouble(1));
            user.put("lon", cursor.getDouble(2));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
 
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
     
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }
 
}
