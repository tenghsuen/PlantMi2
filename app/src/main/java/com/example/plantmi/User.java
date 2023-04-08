package com.example.plantmi;

import java.sql.Time;

public class User {

    public String user;
    public String username;
    public Time time;

    public User() {
    }

    public User(String user) {
        this.user = user;
        this.username = "Plant Pan";
    }

    public void setTime(Time time) {
        this.time = time;
    }
}