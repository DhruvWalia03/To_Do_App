package com.example.todoapp;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class updateFragment2 extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View view= inflater.inflate(R.layout.fragment_update_fragment2 , container,false);
            Button button1 = view.findViewById(R.id.buttonview4);
            Button button2 = view.findViewById(R.id.buttonview5);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Important important;
                    important = (Important) getActivity();
                    important.deleteData1();
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Important important=(Important) getActivity();
                    important.Cancel();
                }
            });


            return view;
        }

    }
