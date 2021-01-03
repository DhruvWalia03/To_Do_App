package com.example.todoapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Create_imp extends BottomSheetDialogFragment {

    Button button;
    EditText text;
    TimePicker timePicker;
    DatePicker datePicker;
    private BottomSheetListener mListener;
    Calendar c;
    String hours,mins,day, date;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create_imp, container, false);

        button=root.findViewById(R.id.buttonview6);
        datePicker=root.findViewById(R.id.datepicker);
        timePicker=root.findViewById(R.id.time__picker);
        text=root.findViewById(R.id.nameoftask2);
        Calendar calendar1= Calendar.getInstance();
        int h = calendar1.get(Calendar.HOUR_OF_DAY);
        int m = calendar1.get(Calendar.MINUTE);
        int y = calendar1.get(Calendar.YEAR);
        int mo = calendar1.get(Calendar.MONTH);
        int dom = calendar1.get(Calendar.DAY_OF_MONTH);
        hours= String.valueOf(h);
        mins=String.valueOf(m);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(h);
            timePicker.setMinute(m);
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    hours=String.valueOf(hourOfDay);
                    mins=String.valueOf(minute);

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
                String input1 = text.getText().toString();
                if(input1.isEmpty())
                {
                    text.setError("Cannot Be Empty");
                    text.requestFocus();
                    return;
                }
                if(Integer.parseInt(mins)<10)
                    mins="0"+mins;
                if(Integer.parseInt(hours)<10)
                    hours="0"+hours;
                c=onTimeSet(Integer.parseInt(hours),Integer.parseInt(mins));
                mListener.fragment1(input1 ,hours+":"+mins , date ,day,c);
                dismiss();
            }
        });

        return root;
    }

    public Calendar onTimeSet(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        return c;
    }
    public interface BottomSheetListener {
        void fragment1(String input1, String s, String date, String text, Calendar c);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BottomSheetDialog(requireContext(),getTheme());
    }

}

