package com.wafaaelm3andy.examplusingdb.LocalDB;

import android.provider.BaseColumns;

public class UserContract{
  public static final class UserListEntry implements BaseColumns {

    //table name and each of the db columns
    public static final String TABLE_NAME = "userList";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_GENDER = "gender";


  }

}
