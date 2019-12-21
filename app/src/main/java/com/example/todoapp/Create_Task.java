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

import androidx.annotation.RequiresApi;
//import android.app.Fragment;

public class Create_Task extends Fragment
{
    private EditText desc , title;
    private Button button;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
       View view= inflater.inflate(R.layout.fragment_create__task , container,false);
       title =view.findViewById(R.id.nameoftask1);
       desc= view.findViewById(R.id.title1);
       button=view.findViewById(R.id.buttonview3);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               CharSequence input1 = title.getText();
               CharSequence input2 = desc.getText();
               MyDayActivity myDayActivity=(MyDayActivity) getActivity();
               myDayActivity.fragment1(input1.toString() ,input2.toString() , "21 Jan" ,"Sat");
               }
       });

       return view;
    }
}
