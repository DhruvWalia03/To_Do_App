package com.example.todoapp;

public class task_to_be_done {

    private String name , desc, date ,day;

    public task_to_be_done(String name, String desc, String date ,String day) {
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.day =day;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public String getDay() { return day; }


}
