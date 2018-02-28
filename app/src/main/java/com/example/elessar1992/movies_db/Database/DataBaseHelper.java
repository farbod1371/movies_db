package com.example.elessar1992.movies_db.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.elessar1992.movies_db.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elessar1992 on 2/26/18.
 */

public class DataBaseHelper extends SQLiteOpenHelper
{

    // user Table
    public static final String DATABASE_NAME = "userdatabase.db";


    public static final String TABLE_NAME = "match";
    public static final String COL_id = "ID"; // same with event itd
    public static final String COL_firstName = "FirstName";
    public static final String COL_lastName = "LastName";
    public static final String COL_userName = "UserName";
    public static final String COL_email = "Email";
    public static final String COL_password = "Password";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COL_id + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_firstName + " TEXT," + COL_lastName + " TEXT," + COL_userName + " TEXT,"
            + COL_email + " TEXT," + COL_password + " TEXT" + ")";

    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
    }

    public long addUser(User user, long[] event_ids)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_firstName, user.getFirstname());
        values.put(COL_lastName, user.getLastname());
        values.put(COL_userName, user.getUsername());
        values.put(COL_email, user.getEmail());
        values.put(COL_password, user.getPassword());

        long user_id = db.insert(TABLE_NAME, null, values);

        /*for (long event_id : event_ids)
        {
            addUserEvent(user_id, event_id);
        }*/
        return user_id;
        //db.close();
    }

    public long updateUser(User user, long[] event_ids)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_firstName, user.getFirstname());
        values.put(COL_lastName, user.getLastname());
        values.put(COL_userName, user.getUsername());
        values.put(COL_email, user.getEmail());
        values.put(COL_password, user.getPassword());

        long user_newid = db.update(TABLE_NAME, values, COL_userName+"=?",
                new String[]{String.valueOf(user.getUsername())});
        return user_newid;
    }

    public List<User> getAllUser()
    {
        String[] columns = {
                COL_id,
                COL_email,
                COL_userName,
                COL_firstName,
                COL_lastName,
                COL_password
        };

        String sortOrder = COL_firstName + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, sortOrder);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst())

        {
            do
            {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_id))));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COL_userName)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COL_email)));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(COL_firstName)));
                user.setLastname(cursor.getString(cursor.getColumnIndex(COL_lastName)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COL_password)));
                userList.add(user);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public boolean checkUser(String email, String password)
    {
        String [] columns = {COL_id};
        SQLiteDatabase db = this.getReadableDatabase();
        String myQuery =  COL_email + " = ?" + " AND " + COL_password + " = ?";
        String [] argumentSelection = {email, password};
        Cursor cursor = db.query(TABLE_NAME,columns,myQuery,argumentSelection,null,null,null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if(cursorCount > 0)
        {
            return true;
        }
        return false;



    }
}
