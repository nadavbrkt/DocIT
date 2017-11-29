package com.nadav.docit.Models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LastUpdateSql {
    // Table data
    final static String LAST_UPDATE_TABLE = "last_update";
    final static String LAST_UPDATE_TABLE_TNAME = "table_name";
    final static String LAST_UPDATE_TABLE_DATE = "date";

    // Creates data
    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + LAST_UPDATE_TABLE + " (" +
                LAST_UPDATE_TABLE_TNAME + " TEXT PRIMARY KEY," +
                LAST_UPDATE_TABLE_DATE + " TEXT);" );
    }

    // Drop table
    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + LAST_UPDATE_TABLE + ";");
    }

    // Get last update time
    public static String getLastUpdate(SQLiteDatabase db, String tableName) {
        String[] args = {tableName};
        Cursor cursor = db.query(LAST_UPDATE_TABLE, null, LAST_UPDATE_TABLE_TNAME + " = ?",args , null, null, null);

        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(LAST_UPDATE_TABLE_DATE));
        }
        return null;
    }

    // Sets last update time
    public static void setLastUpdate(SQLiteDatabase db, String table, String date) {
        ContentValues values = new ContentValues();
        values.put(LAST_UPDATE_TABLE_TNAME, table);
        values.put(LAST_UPDATE_TABLE_DATE, date);

        db.insertWithOnConflict(LAST_UPDATE_TABLE,LAST_UPDATE_TABLE_TNAME,values, SQLiteDatabase.CONFLICT_REPLACE);
    }
}
