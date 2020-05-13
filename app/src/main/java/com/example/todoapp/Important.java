package com.example.todoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.CollationElementIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Important extends AppCompatActivity implements adapter.OnNoteListener{

    DataBasehelper1 mydb1;
    RecyclerView recyclerView;
    adapter ad;
    List<task_to_be_done> taskList;
    private String name, time, date, day;
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important);

        mydb1 = new DataBasehelper1(this);

        taskList = new ArrayList<>();
        recyclerView =  findViewById(R.id.recyclerview_1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showData();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void fragment1(String s1, String s2, String s3, String s4, Calendar c) {
        name = s1;
        time = s2;
        date = s3;
        day = s4;

        Calendar calendar1= Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , ''yy", Locale.ENGLISH);
        String date1 = dateFormat.format(calendar1.getTime());

        if (name.isEmpty()) {
            Toast.makeText(Important.this, " Not A Valid Task ", Toast.LENGTH_SHORT).show();
        } else if(date.equals(date1)) {
            Toast.makeText(Important.this, " Enter It In My Day ", Toast.LENGTH_SHORT).show();
        }
        else {
            Boolean isInserted = mydb1.insertData(name, time, date, day);
            if (isInserted) {
                Toast.makeText(Important.this, " Task Inserted ", Toast.LENGTH_SHORT).show();
                taskList.add(new task_to_be_done(name, time, date, day));
                ad = new adapter(taskList, this, this);
                recyclerView.setAdapter(ad);
                startAlarm(c);

            } else
                Toast.makeText(Important.this,  " Task not Inserted ", Toast.LENGTH_SHORT).show();
        }

        android.app.FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.create_imp);
        if (fragment != null) {
            android.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c) {

        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        intent.putExtra("Title",name);
        intent.putExtra("Time",time);
        intent.putExtra("Date",date);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onNoteClick(int position, String name) {
        this.id=name;
        Fragment fragment = new updateFragment2();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.updation1, fragment);
        ft.commit();

    }

    public void deleteData1() {
        Integer deletedRows = mydb1.deleteData(id);
        if (deletedRows > 0) {
            Toast.makeText(Important.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            taskList.clear();
            showData();
            cancelAlarm();
        }
        android.app.FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.updation1);
        if (fragment != null) {
            android.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }

    }

    public void Cancel() {
        android.app.FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.updation1);
        if (fragment != null) {
            android.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    public void showData() {
        Cursor res = mydb1.getAllData();
        while (res.moveToNext()) {
            taskList.add(new task_to_be_done(res.getString(1), res.getString(2), res.getString(3), res.getString(4)));
            ad = new adapter(taskList, this, this);
            recyclerView.setAdapter(ad);
        }
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    public void openFragment (View view) {

        Fragment fragment = new blankFragment();
        if (view == findViewById(R.id.buttonview7))
            fragment = new Create_imp();
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction ft = fm1.beginTransaction();
        ft.replace(R.id.create_imp, fragment);
        ft.commit();
    }
}
