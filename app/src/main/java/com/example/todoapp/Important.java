package com.example.todoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Important extends AppCompatActivity implements adapter2.OnNoteListener, Create_imp.BottomSheetListener{

    DataBasehelper1 mydb1;
    RecyclerView recyclerView;
    adapter2 ad;
    List<task_to_be_done> taskList;
    private String name;
    private String time;
    private String date;
    public static final String TAG = "BottomSheetDialogTheme";
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
    @Override
    public void fragment1(String s1, String s2, String s3, String s4, Calendar c) {
        name = s1;
        time = s2;
        date = s3;

        Calendar calendar1= Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , ''yy", Locale.ENGLISH);
        String date1 = dateFormat.format(calendar1.getTime());
        String h= String.valueOf(calendar1.get(Calendar.HOUR_OF_DAY));
        String m= String.valueOf(calendar1.get(Calendar.MINUTE));
        if(Integer.parseInt(m)<10)
            m="0"+m;
        if(Integer.parseInt(h)<10)
            h="0"+h;

        if (name.isEmpty()) {
            Toast.makeText(Important.this, " Not A Valid Task ", Toast.LENGTH_SHORT).show();
        } else if(date.equals(date1)) {
            if(time.substring(0,2).equals(h) && time.substring(3,5).equals(m))
                Toast.makeText(Important.this,"RUN HURRY UP!",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Important.this, " Enter It In My Day ", Toast.LENGTH_SHORT).show();
        }
        else {
            Boolean isInserted = mydb1.insertData(name, time, date, s4);
            if (isInserted) {
                Toast.makeText(Important.this, " Task Inserted ", Toast.LENGTH_SHORT).show();
                taskList.add(new task_to_be_done(name, time, date, s4));
                ad = new adapter2(taskList, this, this);
                recyclerView.setAdapter(ad);
                startAlarm(c);

            } else
                Toast.makeText(Important.this,  " Task not Inserted ", Toast.LENGTH_SHORT).show();
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
            ad = new adapter2(taskList, this, this);
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
        Create_imp create_imp = new Create_imp();
        create_imp.show(getSupportFragmentManager(),TAG);
    }
}
