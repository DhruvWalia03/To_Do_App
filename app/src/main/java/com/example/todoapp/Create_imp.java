package com.example.todoapp;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Create_imp extends Fragment {

    Button button;
    EditText text;
    TimePicker timePicker;
    DatePicker datePicker;
    Calendar c;
    String hours,mins,day, date;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_imp, container, false);

        button.findViewById(R.id.buttonview6);
        datePicker.findViewById(R.id.datepicker);
        timePicker.findViewById(R.id.time__picker);
        text.findViewById(R.id.nameoftask2);
        Calendar calendar1= Calendar.getInstance();
        int h = calendar1.get(Calendar.HOUR_OF_DAY);
        int m = calendar1.get(Calendar.MINUTE);
        int y = calendar1.get(Calendar.YEAR);
        int mo = calendar1.get(Calendar.MONTH);
        int dom = calendar1.get(Calendar.DAY_OF_MONTH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(h);
            timePicker.setMinute(m);
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    hours=String.valueOf(hourOfDay);
                    mins=String.valueOf(minute);
                    if(Integer.parseInt(mins)<10)
                        mins="0"+mins;
                    if(Integer.parseInt(hours)<10)
                        hours="0"+hours;

                }
            });
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , ''yy", Locale.ENGLISH);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
        date = dateFormat.format(calendar1.getTime());
        day = dayFormat.format(calendar1.getTime());
        datePicker.updateDate(y,mo,dom);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                Calendar calendar= Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , ''yy", Locale.ENGLISH);
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
                date = dateFormat.format(calendar.getTime());
                day = dayFormat.format(calendar.getTime());
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                CharSequence input1 = text.getText();
                c=onTimeSet(Integer.parseInt(hours),Integer.parseInt(mins));
                Important important=(Important) getActivity();
                important.fragment1(input1.toString() ,hours+":"+mins , date ,day,c);
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

