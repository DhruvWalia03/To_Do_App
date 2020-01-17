package com.example.todoapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDayActivity extends AppCompatActivity implements adapter.OnNoteListener {

    DataBaseHelper mydb;
    private Create_Task fragment;
    RecyclerView recyclerView;
    adapter ad;
    List<task_to_be_done> taskList;
    private String name, desc, date, day;
    String id ;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_day);
        mydb = new DataBaseHelper(this);

        taskList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void fragment1(String s1, String s2, String s3, String s4) {
        name = s1;
        desc = s2;
        date = s3;
        day = s4;


        Boolean isInserted = mydb.insertData(name, desc, date, day);
        if (isInserted == true) {
            Toast.makeText(MyDayActivity.this, "Task Inserted", Toast.LENGTH_SHORT).show();
            taskList.add(new task_to_be_done(name, desc, date, day));
            ad = new adapter(taskList, this, this);
            recyclerView.setAdapter(ad);
        }
            else

            Toast.makeText(MyDayActivity.this, "Task not Inserted", Toast.LENGTH_SHORT).show();


        android.app.FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.createTask);
        if (fragment != null) {
            android.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    public void createFragment(View view) {
        Fragment fragment = new blankFragment();
        if (view == findViewById(R.id.buttonview1))
            fragment = new Create_Task();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.createTask, fragment);
        ft.commit();
    }

    @Override
    public void onNoteClick(int position,String id) {
        Fragment fragment = new update_fragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.updation, fragment);
        ft.commit();
    }

    public void deleteData1() {
        Integer deletedRows = mydb.deleteData(id);
        if (deletedRows > 0) {
            Toast.makeText(MyDayActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            taskList = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            showData();
        }
        android.app.FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.updation);
        if (fragment != null) {
            android.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }

    }

    public void Cancel() {
        android.app.FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.updation);
        if (fragment != null) {
            android.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    public void showData() {
        Cursor res = mydb.getAllData();
        if (res.getCount() == 0)
            return;
        while (res.moveToNext()) {
            taskList.add(new task_to_be_done(res.getString(1), res.getString(2), res.getString(3), res.getString(4)));
            ad = new adapter(taskList, this, this);
            recyclerView.setAdapter(ad);
        }
    }

    public void onSignedOut(){ taskList.clear(); }
    public void onSignedIn(){ showData(); }
}
