package com.example.todoapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    int h,mo,dom,y,m;
    boolean isUpdate = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create_imp, container, false);
        button=root.findViewById(R.id.buttonview6);
        datePicker=root.findViewById(R.id.datepicker);
        timePicker=root.findViewById(R.id.time__picker);
        text=root.findViewById(R.id.nameoftask2);
        final Bundle bundle = getArguments();
        if(bundle!=null)
        {
            isUpdate = true;
            String task = bundle.getString("name");
            text.setText(task);
            String time = bundle.getString("time");
            setTimePickerUpdate(time.substring(0,2),time.substring(3,5));
            mListener.cancelAlarm();
            if(task.length()>0) {
                button.setTextColor(Color.WHITE);
                button.setEnabled(true);
            }
        }
        else setTimePickerInsert();

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    button.setEnabled(false);
                    button.setTextColor(Color.GRAY);
                } else {
                    if(!isUpdate) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            timePicker.setOnTimeChangedListener((view1, hourOfDay, minute) -> {
                                hours=String.valueOf(hourOfDay);
                                mins=String.valueOf(minute);
                                button.setEnabled(true);
                                button.setTextColor(Color.WHITE);
                            });
                        }
                    }
                    else {
                        button.setEnabled(true);
                        button.setTextColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            monthOfYear++;
            Calendar calendar= Calendar.getInstance();
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , ''yy", Locale.ENGLISH);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
            date = dateFormat.format(calendar.getTime());
            day = dayFormat.format(calendar.getTime());
        });

        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , ''yy", Locale.ENGLISH);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
        date = dateFormat.format(calendar1.getTime());
        day = dayFormat.format(calendar1.getTime());
        if(day.equals("THU"))
            day+="RS";

        button.setOnClickListener(v -> {
            String input = text.getText().toString();
            if(Integer.parseInt(mins)<10)
                mins="0"+mins;
            if(Integer.parseInt(hours)<10)
                hours="0"+hours;
            c=onTimeSet(Integer.parseInt(hours),Integer.parseInt(mins));
            if(isUpdate && bundle!=null)
                mListener.fragment_update(bundle.getString("id"),input,hours+":"+mins , date ,day ,c);
            else
                mListener.fragment_insert(input,hours+":"+mins , date ,day,c);
            dismiss();
        });

        return root;
    }

    private void setTimePickerUpdate(String h_o, String m_i) {
        Calendar calendar1= Calendar.getInstance();
        y = calendar1.get(Calendar.YEAR);
        mo = calendar1.get(Calendar.MONTH);
        dom = calendar1.get(Calendar.DAY_OF_MONTH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(Integer.parseInt(h_o));
            timePicker.setMinute(Integer.parseInt(m_i));
        }
        datePicker.updateDate(y,mo,dom);
    }

    private void setTimePickerInsert() {
        Calendar calendar1= Calendar.getInstance();
        h = calendar1.get(Calendar.HOUR_OF_DAY);
        m = calendar1.get(Calendar.MINUTE);
        y = calendar1.get(Calendar.YEAR);
        mo = calendar1.get(Calendar.MONTH);
        dom = calendar1.get(Calendar.DAY_OF_MONTH);
        hours= String.valueOf(h);
        mins=String.valueOf(m);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(h);
            timePicker.setMinute(m);
        }
        datePicker.updateDate(y,mo,dom);
    }

    public Calendar onTimeSet(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        return c;
    }
    public interface BottomSheetListener {
        void fragment_insert(String input1, String s, String date, String text, Calendar c);
        void fragment_update(String id, String input, String s, String date, String day, Calendar c);
        void cancelAlarm();
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

