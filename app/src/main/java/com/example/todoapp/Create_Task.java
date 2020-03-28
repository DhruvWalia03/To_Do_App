package com.example.todoapp;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Create_Task extends Fragment
{
    private EditText title;
    TimePicker timePicker;
    private Button button;
    private String date , day,hours,mins;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
       View view= inflater.inflate(R.layout.fragment_create__task , container,false);
       title =view.findViewById(R.id.nameoftask1);
       timePicker =view.findViewById(R.id.time_picker);
       button=view.findViewById(R.id.buttonview3);
       Calendar calendar = Calendar.getInstance();
       SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , ''yy", Locale.ENGLISH);
       SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
       date = dateFormat.format(calendar.getTime());
       day = dayFormat.format(calendar.getTime());
       hours=String.valueOf(timePicker.getCurrentHour());
       mins=String.valueOf(timePicker.getCurrentMinute());
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CharSequence input1 = title.getText();
               MyDayActivity myDayActivity=(MyDayActivity) getActivity();
               myDayActivity.fragment1(input1.toString() ,hours+":"+mins , date ,day);

               }
       });

       return view;
    }

    /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        MyDayActivity myDayActivity1 = new MyDayActivity();
        myDayActivity1.startAlarm(c);
    }*/
}
