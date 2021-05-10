package com.example.todoapp;

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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Adapter.adapter;
import com.example.todoapp.Adapter.adapter2;
import com.example.todoapp.Model.task_to_be_done;
import com.example.todoapp.Model.task_to_be_done1;
import com.example.todoapp.Utils.DataBasehelper1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Important extends AppCompatActivity implements Create_imp.BottomSheetListener{

    DataBasehelper1 mydb1;
    RecyclerView recyclerView;
    FloatingActionButton button;
    adapter2 ad;
    List<task_to_be_done1> taskList;
    public static final String TAG = "BottomSheetDialogTheme";
    int id,posn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important);
        button = findViewById(R.id.addtaskbtn);
        mydb1 = new DataBasehelper1(this);
        mydb1.getWritableDatabase();
        taskList = new ArrayList<>();
        recyclerView =  findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        onSignedIn();
        button.setOnClickListener(v -> openFragment());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new RecyclerItemTouchHelper1(ad));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void openFragment () {
        Create_imp create_imp = new Create_imp();
        create_imp.show(getSupportFragmentManager(),TAG);
    }

    private void onSignedIn() { showData(); }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void fragment_insert(String s1, String s2, String s3, String s4, Calendar c) {
        Calendar calendar1= Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , ''yy", Locale.ENGLISH);
        String date1 = dateFormat.format(calendar1.getTime());
        String h= String.valueOf(calendar1.get(Calendar.HOUR_OF_DAY));
        String m= String.valueOf(calendar1.get(Calendar.MINUTE));
        if(Integer.parseInt(m)<10)
            m="0"+m;
        if(Integer.parseInt(h)<10)
            h="0"+h;
        if(s3.equals(date1)) {
            if(s2.substring(0,2).equals(h) && s2.substring(3,5).equals(m))
                Toast.makeText(Important.this,"RUN HURRY UP!",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Important.this, " Enter It In My Day ", Toast.LENGTH_SHORT).show();
        }
        else {
            task_to_be_done1 newtask = new task_to_be_done1(s1,s2,s3,s4);
            Boolean isInserted = mydb1.insertData(newtask);
            if (isInserted) {
                Toast.makeText(Important.this, " Task Inserted ", Toast.LENGTH_SHORT).show();
                showData();
                startAlarm(c, s1 ,s2 ,s3);
            } else
                Toast.makeText(Important.this,  " Task not Inserted ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void fragment_update(String id, String input, String s, String date, String day, Calendar c) {
        mydb1.updateTask(id,input,s,date,day);
        Toast.makeText(Important.this, " Task Updated ", Toast.LENGTH_SHORT).show();
        showData();
        startAlarm(c,input,s,date);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c, String name, String time, String date) {
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

    public void deleteData(int id, int posn) {
        this.id = id;
        this.posn = posn;
        Integer deletedRows = mydb1.deleteData(String.valueOf(id));
        if (deletedRows > 0) {
            Toast.makeText(Important.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            showData();
            cancelAlarm();
        }
    }

    public void showData() {
        taskList = new ArrayList<>();
        taskList= mydb1.getAllData();
        Collections.reverse(taskList);
        ad = new adapter2(mydb1,Important.this,taskList, this);
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
