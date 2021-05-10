package com.example.todoapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todoapp.Model.task_to_be_done1;

import java.util.ArrayList;
import java.util.List;

public class DataBasehelper1 extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "ImpTasks.db";
    public static final String TABLE_NAME= "IMPTASKS";
    public static final String COL_1= "SERIAL_NO";
    public static final String COL_2= "NAME_OF_TASK";
    public static final String COL_3= "TIME";
    public static final String COL_4= "DATE";
    public static final String COL_5= "DAY";

    public DataBasehelper1(@Nullable Context context) {
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

    public Boolean insertData(task_to_be_done1 task) {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2, task.getName());
        contentValues.put(COL_3, task.getTime());
        contentValues.put(COL_4, task.getDate());
        contentValues.put(COL_5, task.getDay());
        long result =db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
    public List<task_to_be_done1> getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<task_to_be_done1> tasklist = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TABLE_NAME, null, null, null, null
                    , null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        task_to_be_done1 task = new task_to_be_done1
                                (cur.getString(cur.getColumnIndex(COL_2)),
                                        cur.getString(cur.getColumnIndex(COL_3)),
                                        cur.getString(cur.getColumnIndex(COL_4)),
                                        cur.getString(cur.getColumnIndex(COL_5)));
                        task.setId(cur.getInt(cur.getColumnIndex(COL_1)));
                        tasklist.add(task);
                    } while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return tasklist;
    }

    public void updateTask(String id, String name, String time, String date,String day)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, day);
        db.update(TABLE_NAME,contentValues, COL_1 + "=?", new String[]{id});
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete(TABLE_NAME,COL_1 +"=?",new String[]{id});
    }
}
