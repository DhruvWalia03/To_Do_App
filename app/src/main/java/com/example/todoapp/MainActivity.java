package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text1 =(TextView) findViewById(R.id.activity);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyDay();
            }
        });
        TextView text2 =(TextView) findViewById(R.id.activity1);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImportant();
            }
        });
        TextView text3 =(TextView) findViewById(R.id.activity2);
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewList();
            }
        });
    }

    private void openNewList() {
        Intent i3= new Intent(this , NewListActivity.class);
        startActivity(i3);
    }

    private void openImportant() {
        Intent i2= new Intent(this , Important.class);
        startActivity(i2);
    }

    public void openMyDay() {
        Intent i1 =new Intent(this, MyDayActivity.class);
        startActivity(i1);
    }
}
