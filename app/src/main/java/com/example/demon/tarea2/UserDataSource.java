package com.example.demon.tarea2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource {

    private SQLiteDatabase database;
    private UserDBHelper userDBHelper;
    private String[] allColumns = {
            UserContract.COLUMN_NAME_EMAIL,
            UserContract.COLUMN_NAME_PASSWORD};

    public UserDataSource(Context context){
        userDBHelper = new UserDBHelper(context);
    }

    public void open() throws SQLException {
        database = userDBHelper.getWritableDatabase();
    }

    public void close(){
        userDBHelper.close();
    }

    /**
     * Function oriented to insert a new Alumno into DataBase
     * @param email
     * @param pass
     * @return row ID of the newly Alumno inserted row, or -1
     */
    public long insertUser(String email,String pass){
        ContentValues values = new ContentValues();
        values.put(UserContract.COLUMN_NAME_EMAIL, email);
        values.put(UserContract.COLUMN_NAME_PASSWORD, pass);
        long newRowId;
        newRowId = database.insert(UserContract.TABLE_NAME_USER,null,values);
        return newRowId;
    }

    /**
     * Function oriented to recovery all User's rows from Database
     * @return List of Users from Database
     */
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();
        User user;
        Cursor cursor = database.query(UserContract.TABLE_NAME_USER,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

    private User cursorToUser(Cursor cursor){
        User user = new User();
        user.setEmail(cursor.getString(0));
        user.setPassword(cursor.getString(1));
        return user;
    }

}
