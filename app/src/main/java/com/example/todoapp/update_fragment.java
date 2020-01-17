package com.example.todoapp;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
//import android.app.Fragment;

public class update_fragment extends Fragment
{
    private Button button1, button2;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_update_fragment , container,false);
        button1=view.findViewById(R.id.buttonview4);
        button2=view.findViewById(R.id.buttonview5);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDayActivity myDayActivity;
                myDayActivity = (MyDayActivity) getActivity();
                myDayActivity.deleteData1();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDayActivity myDayActivity=(MyDayActivity) getActivity();
                myDayActivity.Cancel();
            }
        });


        return view;
    }
}
