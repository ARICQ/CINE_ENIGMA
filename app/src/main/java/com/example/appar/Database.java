package com.example.appar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME = "cinema.db";
    public static  final String TABLE_NAME = "critique_table";
    public static  final String COL_1 = "ID";
    public static  final String COL_2 = "title";
    public static  final String COL_3 = "date";
    public static  final String COL_4 = "note_scenario";
    public static  final String COL_5 = "note_realisation";
    public static  final String COL_6 = "note_musique";
    public static  final String COL_7 = "description";


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, date TEXT, note_scenario TEXT, note_realisation TEXT, note_musique TEXT, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String title, String date, String note_scenario, String note_realisation, String note_musique, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, date);
        contentValues.put(COL_4, note_scenario);
        contentValues.put(COL_5, note_realisation);
        contentValues.put(COL_6, note_musique);
        contentValues.put(COL_7, description);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return  res;
    }


}
