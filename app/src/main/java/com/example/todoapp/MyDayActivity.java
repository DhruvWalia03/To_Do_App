package com.example.todoapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Adapter.adapter;
import com.example.todoapp.Model.task_to_be_done;
import com.example.todoapp.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MyDayActivity extends AppCompatActivity implements Create_Task.BottomSheetListener {

    DataBaseHelper mydb;
    RecyclerView recyclerView;
    FloatingActionButton button;
    adapter ad;
    List<task_to_be_done> taskList;
    public static final String TAG = "BottomSheetDialogTheme";
    int id,position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_day);
        button = findViewById(R.id.addtaskbtn);
        recyclerView =  findViewById(R.id.recyclerview);
        mydb = new DataBaseHelper(this);
        mydb.getWritableDatabase();
        taskList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        onSignedIn();
        button.setOnClickListener(v -> createFragment());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new RecyclerItemTouchHelper(ad));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void createFragment() {
        Create_Task create_task = new Create_Task();
        create_task.show(getSupportFragmentManager(),TAG);
    }

    public void onSignedIn(){ showData(); }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void fragment_insert(String s1, String s2, String s3, String s4, Calendar calendar) {
        task_to_be_done newtask = new task_to_be_done(s1,s2,s3,s4);
        Boolean isInserted = mydb.insertData(newtask);
        if (isInserted) {
            Toast.makeText(MyDayActivity.this, " Task Inserted ", Toast.LENGTH_SHORT).show();
            showData();
            startAlarm(calendar, s1 ,s2 ,s3);
        } else
            Toast.makeText(MyDayActivity.this,  " Task not Inserted ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fragment_update(String id, String input1, String s, String date, String day, Calendar c) {
        mydb.updateTask(id,input1,s,date,day);
        Toast.makeText(MyDayActivity.this, " Task Updated ", Toast.LENGTH_SHORT).show();
        showData();
        startAlarm(c,input1,s,date);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar calendar, String name, String time, String date) {
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        intent.putExtra("Title",name);
        intent.putExtra("Time",time);
        intent.putExtra("Date", date);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void deleteData(int id, int posn) {
        this.id = id;
        this.position = posn;
        Integer deletedRows = mydb.deleteData(String.valueOf(id));
        if (deletedRows > 0) {
            Toast.makeText(MyDayActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            showData();
            cancelAlarm();
        }
    }

    public void showData() {
        taskList = new ArrayList<>();
        taskList= mydb.getAllData();
        Collections.reverse(taskList);
        ad = new adapter(mydb,MyDayActivity.this,taskList, this);
        recyclerView.setAdapter(ad);
        ad.notifyDataSetChanged();
    }


    public void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
