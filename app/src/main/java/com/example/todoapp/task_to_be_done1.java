package com.example.todoapp;

public class task_to_be_done1 {
    private final String name;
    private final String desc;
    private final String date;
    private final String day ;

    public task_to_be_done1(String name, String desc, String date ,String day) {
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
