package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "Tasks_.db";
    public static final String TABLE_NAME= "TASKS";
    public static final String COL_1= "SERIAL_NO";
    public static final String COL_2= "NAME_OF_TASK";
    public static final String COL_3= "TIME";
    public static final String COL_4= "DATE";
    public static final String COL_5= "DAY";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table " + TABLE_NAME+ " (SERIAL_NO INTEGER PRIMARY KEY AUTOINCREMENT,NAME_OF_TASK TEXT,TIME TEXT,DATE TEXT,DAY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public Boolean insertData (String name, String time, String date, String day){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2 , name);
        contentValues.put(COL_3 , time);
        contentValues.put(COL_4 , date);
        contentValues.put(COL_5 , day);
        long result =db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_NAME,null);
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete(TABLE_NAME, "NAME_OF_TASK=?", new String[]{id});
    }
}
