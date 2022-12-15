package com.example.dictonary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public
class SqliteDatabase extends SQLiteOpenHelper {
    private static final String TABLE ="dictionary.db";
    private static final String TABLE_NAME ="Dictionary";
    private static final int VERSION = 1;

    public
    SqliteDatabase(Context context) {

        super(context, TABLE_NAME, null, VERSION);
    }

    @Override
    public
    void onCreate(SQLiteDatabase db) {
        String q = "create table Dictionary (Word text primary key , Meaning text)";
                db.execSQL(q);
    }

    @Override
    public
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Dictionary");

    }
    public boolean addingValue(String word,String meaning){
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Word",word);
        contentValues.put("Meaning",meaning);
        long result = sqliteDatabase.insert(TABLE_NAME,null,contentValues);
        if (result==-1){
            return false;
        }
        else return true;

    }
}
