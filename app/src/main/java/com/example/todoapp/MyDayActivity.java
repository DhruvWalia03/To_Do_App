package com.example.todoapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class MyDayActivity extends AppCompatActivity {

    private Create_Task fragment;
    RecyclerView recyclerView;
    adapter ad;
    List<task_to_be_done> taskList;
    private String name ,desc ,date ,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_day);
    }

    public void fragment1(String s1 , String s2 ,String s3 ,String s4) {
        name = s1;
        desc = s2;
        date = s3;
        day = s4;
        taskList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskList.add(new task_to_be_done(name, desc, date ,day));
        ad = new adapter(taskList, this);
        recyclerView.setAdapter(ad);
    }

    @Override
    public void onBackPressed() {
        android.app.FragmentManager manager = getFragmentManager();
        Fragment fragment= manager.findFragmentById(R.id.createTask);
        if(fragment!=null)
        {
            android.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
        else
            super.onBackPressed();
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
}
