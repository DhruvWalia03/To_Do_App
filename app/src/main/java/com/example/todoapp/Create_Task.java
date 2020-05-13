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
    Calendar c;
    private String date , day,hours,mins;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
       View view= inflater.inflate(R.layout.fragment_create__task , container,false);
       title =view.findViewById(R.id.nameoftask1);
       timePicker =view.findViewById(R.id.time_picker);
       button=view.findViewById(R.id.buttonview3);
       Calendar calendar1= Calendar.getInstance();
        int h = calendar1.get(Calendar.HOUR_OF_DAY);
        int m = calendar1.get(Calendar.MINUTE);
        hours= String.valueOf(h);
        mins= String.valueOf(m);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(h);
            timePicker.setMinute(m);
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , ''yy", Locale.ENGLISH);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
        date = dateFormat.format(calendar.getTime());
        day = dayFormat.format(calendar.getTime());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    hours=String.valueOf(hourOfDay);
                    mins=String.valueOf(minute);
                }
            });
        }
        button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CharSequence input1 = title.getText();
               c=onTimeSet(Integer.parseInt(hours),Integer.parseInt(mins));
               MyDayActivity myDayActivity=(MyDayActivity) getActivity();
               if(Integer.parseInt(mins)<10)
                   mins="0"+mins;
               if(Integer.parseInt(hours)<10)
                   hours="0"+hours;
               myDayActivity.fragment1(input1.toString() ,hours+":"+mins , date ,day,c);
           }
       });

       return view;
    }

    public Calendar onTimeSet(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        return c;
    }
}
