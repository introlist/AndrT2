package com.example.demon.tarea2;


import android.provider.BaseColumns;

public class UserContract implements BaseColumns{

    public static final String TABLE_NAME_USER = "User";
    public static final String COLUMN_NAME_EMAIL = "EMAL";
    public static final String COLUMN_NAME_PASSWORD = "contrasena";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_USERS =
            "CREATE TABLE " + TABLE_NAME_USER+ " (" +COLUMN_NAME_EMAIL+TEXT_TYPE+COMMA_SEP+
                    COLUMN_NAME_PASSWORD+TEXT_TYPE+" )";

    public static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + TABLE_NAME_USER;


}

