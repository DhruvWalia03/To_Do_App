package com.example.todoapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class Create_Task extends BottomSheetDialogFragment
{
    Button button;
    private EditText title;
    TimePicker timePicker;
    private BottomSheetListener mListener;
    Calendar c;
    private String date,day,hours,mins;
    boolean isUpdate =false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_create__task , container,false);
        title = view.findViewById(R.id.nameoftask);
        timePicker = view.findViewById(R.id.time_picker);
        button = view.findViewById(R.id.buttonview3);
        final Bundle bundle = getArguments();
        if(bundle!=null)
        {
            isUpdate = true;
            String task = bundle.getString("name");
            title.setText(task);
            String time = bundle.getString("time");
            setTimePickerUpdate(time.substring(0,2),time.substring(3,5));
            mListener.cancelAlarm();
            if(task.length()>0) {
                button.setTextColor(Color.WHITE);
                button.setEnabled(true);
            }
        }
        else setTimePickerInsert();

        title.addTextChangedListener(new TextWatcher() {
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

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , ''yy", Locale.ENGLISH);
        date = dateFormat.format(calendar.getTime());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
        day = dayFormat.format(calendar.getTime());
        if(day.equals("Thu"))
            day+="RS";


        button.setOnClickListener(v -> {
            String input = title.getText().toString();
            c = onTimeSet(Integer.parseInt(hours),Integer.parseInt(mins));
            if(Integer.parseInt(mins)<10)
                mins="0"+mins;
            if(Integer.parseInt(hours)<10)
                hours="0"+hours;
            if(isUpdate && bundle!=null)
                mListener.fragment_update(bundle.getString("id"),input,hours+":"+mins , date ,day ,c);
            else
                mListener.fragment_insert(input,hours+":"+mins , date ,day,c);
            dismiss();
        });
       return view;
    }

    private void setTimePickerUpdate(String h_ours, String m_in) {
        hours= (h_ours);
        mins= (m_in);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(Integer.parseInt(h_ours));
            timePicker.setMinute(Integer.parseInt(m_in));
        }
    }

    private void setTimePickerInsert() {
        Calendar calendar1= Calendar.getInstance();
        int h = calendar1.get(Calendar.HOUR_OF_DAY);
        int m = calendar1.get(Calendar.MINUTE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(h);
            timePicker.setMinute(m);
        }
    }

    public Calendar onTimeSet(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        return c;
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

    public interface BottomSheetListener {
        void fragment_insert(String input1, String s, String date, String text, Calendar c);
        void cancelAlarm();
        void fragment_update(String id, String input1, String s, String date, String day, Calendar c);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement BottomSheetListener");
        }
    }
}
