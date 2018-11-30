package com.wafaaelm3andy.examplusingdb.LocalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wafaaelm3andy.examplusingdb.LocalDB.UserContract;

public class UserDbHelper extends SQLiteOpenHelper {
  SQLiteDatabase sqLiteDatabase ;

  // The database name
  private static final String DATABASE_NAME = "userList.db";
  // If you change the database schema, you must increment the database version
  private static final int DATABASE_VERSION = 1;

  // Constructor
  public UserDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    // Create a table to hold UserList data
    final String SQL_CREATE_USER_LIST_TABLE = "CREATE TABLE " +  UserContract.UserListEntry.TABLE_NAME + " (" +
            UserContract.UserListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            UserContract.UserListEntry.COLUMN_NAME + " TEXT NOT NULL ," +
            UserContract.UserListEntry.COLUMN_AGE + " INTEGER NOT NULL ," +
            UserContract.UserListEntry.COLUMN_GENDER + " TEXT NOT NULL " +
            "); ";

    //  Execute the query by calling execSQL on sqLiteDatabase and pass the string query SQL_CREATE_NOTELABLE
    sqLiteDatabase.execSQL(SQL_CREATE_USER_LIST_TABLE);

  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserContract.UserListEntry.TABLE_NAME);
    onCreate(sqLiteDatabase);
  }
  public long addNote(String name , int age , String gender ){
    ContentValues cv = new ContentValues();

    cv.put(UserContract.UserListEntry.COLUMN_NAME,name);
    cv.put(UserContract.UserListEntry.COLUMN_AGE,age);
    cv.put(UserContract.UserListEntry.COLUMN_GENDER,gender);

    return sqLiteDatabase.insert(UserContract.UserListEntry.TABLE_NAME, null, cv);



  }

}

