package com.example.imable1;

public class User {
    String activity;
    //Timestamp time;
    String date;
    String time;
    public User(){}


    public User(String activity, String date,String time) {
        this.activity = activity;
        this.date=date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
