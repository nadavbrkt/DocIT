package com.nadav.docit.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nadav.docit.DocIT;

public class SqlModel {
    final static int VERSION = 6;
    Helper sqlDb;

    private final static SqlModel _instance = new SqlModel(DocIT.getAppContext());

    public static synchronized SqlModel getInstance() {
        return _instance;
    }

    private SqlModel(Context context){
        sqlDb = new Helper(context);
    }

    public SQLiteDatabase getWritableDB(){
        return sqlDb.getWritableDatabase();
    }

    public SQLiteDatabase getReadbleDB(){
        return sqlDb.getReadableDatabase();
    }

    class Helper extends SQLiteOpenHelper {
        public Helper(Context context) {
            super(context, "database.db", null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            UsersSql.create(db);
            LastUpdateSql.create(db);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            UsersSql.drop(db);
            LastUpdateSql.drop(db);
            onCreate(db);
        }
    }
}
