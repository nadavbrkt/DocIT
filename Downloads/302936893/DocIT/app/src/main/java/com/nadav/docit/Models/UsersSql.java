package com.nadav.docit.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nadav.docit.Class.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nadav on 7/23/2016.
 */
public class UsersSql {
    // Table data
    final static String USERS_TABLE = "users";
    final static String USERS_TABLE_ID = "_id";
    final static String USERS_TABLE_FNAME = "fname";
    final static String USERS_TABLE_LNAME = "lname";
    final static String USERS_TABLE_EMAIL = "email";

    // Creates table
    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + USERS_TABLE + " (" +
                USERS_TABLE_ID + " TEXT PRIMARY KEY," +
                USERS_TABLE_FNAME + " TEXT," +
                USERS_TABLE_LNAME + " TEXT," +
                USERS_TABLE_EMAIL + " TEXT);");
    }

    // Drops table
    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + USERS_TABLE + ";");
    }

    // Get all users list
    public static List<User> getAllUsers(SQLiteDatabase db) {
        Cursor cursor = db.query(USERS_TABLE, null, null , null, null, null, null);
        List<User> users = new LinkedList<User>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USERS_TABLE_ID);
            int fnameIndex = cursor.getColumnIndex(USERS_TABLE_FNAME);
            int lnameIndex = cursor.getColumnIndex(USERS_TABLE_LNAME);
            int emailIndex = cursor.getColumnIndex(USERS_TABLE_EMAIL);
            do {
                String id = cursor.getString(idIndex);
                String fname = cursor.getString(fnameIndex);
                String lname = cursor.getString(lnameIndex);
                String email = cursor.getString(emailIndex);

                User user = new User(id, fname,lname, email, null );
                users.add(user);
            } while (cursor.moveToNext());
        }

        return users;
    }

    // Get user by id
    public static User getUserById(SQLiteDatabase db, String id) {
        String where = USERS_TABLE_ID + " = ?";
        String[] args = {id};
        Cursor cursor = db.query(USERS_TABLE, null, where, args, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USERS_TABLE_ID);
            int fnameIndex = cursor.getColumnIndex(USERS_TABLE_FNAME);
            int lnameIndex = cursor.getColumnIndex(USERS_TABLE_LNAME);
            int emailIndex = cursor.getColumnIndex(USERS_TABLE_EMAIL);

            String cursorId = cursor.getString(idIndex);
            String fname = cursor.getString(fnameIndex);
            String lname = cursor.getString(lnameIndex);
            String email = cursor.getString(emailIndex);

            User user = new User(id, fname,lname, email, null );
            return user;
        }

        return null;
    }

    // Add member
    public static void add(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        values.put(USERS_TABLE_ID, user.getKey());
        values.put(USERS_TABLE_FNAME, user.getFname());
        values.put(USERS_TABLE_LNAME, user.getLname());
        values.put(USERS_TABLE_EMAIL, user.getEmail());

        db.insertWithOnConflict(USERS_TABLE, USERS_TABLE_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    // Rewrite DB
    public static void reWriteDB(List<User> users, String time) {
        UsersSql.drop(SqlModel.getInstance().getWritableDB());
        UsersSql.create(SqlModel.getInstance().getWritableDB());
        for (User user : users) {
            UsersSql.add(SqlModel.getInstance().getWritableDB(), user);
        }

        UsersSql.setLastUpdateDate(SqlModel.getInstance().getWritableDB(), time);
    }

    public static String getLastUpdateDate(SQLiteDatabase db){
        return LastUpdateSql.getLastUpdate(db,USERS_TABLE);
    }
    public static void setLastUpdateDate(SQLiteDatabase db, String date){
        LastUpdateSql.setLastUpdate(db,USERS_TABLE, date);
    }
}
