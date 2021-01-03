package com.example.todoapp;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Fragment;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyDayActivity extends AppCompatActivity implements adapter.OnNoteListener,Create_Task.BottomSheetListener {

    DataBaseHelper mydb;
    RecyclerView recyclerView;
    Button button;
    adapter ad;
    List<task_to_be_done1> taskList;
    private String name;
    private String time;
    private String date;
    public static final String TAG = "BottomSheetDialogTheme";
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_day);
        mydb = new DataBaseHelper(this);
        button = findViewById(R.id.buttonview1);
        taskList = new ArrayList<>();
        recyclerView =  findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        onSignedIn();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void fragment1(String s1, String s2, String s3, String s4, Calendar calendar) {
        name = s1;
        time = s2;
        date = s3;
        if (name.isEmpty()) {
            Toast.makeText(MyDayActivity.this, " Not A Valid Task ", Toast.LENGTH_SHORT).show();
        } else {
            Boolean isInserted = mydb.insertData(name, time, date, s4);
            if (isInserted) {
                Toast.makeText(MyDayActivity.this, " Task Inserted ", Toast.LENGTH_SHORT).show();
                taskList.add(new task_to_be_done1(name, time, date, s4));
                ad = new adapter(taskList, this, this);
                recyclerView.setAdapter(ad);
                startAlarm(calendar);
            } else
                Toast.makeText(MyDayActivity.this,  " Task not Inserted ", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        intent.putExtra("Title",name);
        intent.putExtra("Time",time);
        intent.putExtra("Date",date);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    public void createFragment(View view) {
        Create_Task create_task = new Create_Task();
        create_task.show(getSupportFragmentManager(),TAG);
    }

    @Override
    public void onNoteClick(int position , String name) {
            this.id=name;
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
            taskList.clear();
            showData();
            cancelAlarm();
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
        while (res.moveToNext()) {
            taskList.add(new task_to_be_done1(res.getString(1), res.getString(2), res.getString(3), res.getString(4)));
            ad = new adapter(taskList, this, this);
            recyclerView.setAdapter(ad);
        }
    }

    public void onSignedIn(){ showData(); }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

}
