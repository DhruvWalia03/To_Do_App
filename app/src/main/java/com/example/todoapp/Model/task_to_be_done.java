package com.example.todoapp.Model;

public class task_to_be_done {

    private String name,time,date,day;
    private int id;

    public task_to_be_done(String name, String time, String date ,String day) {
        this.name = name;
        this.time = time;
        this.date = date;
        this.day =day;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String desc) {
        this.time = desc;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
