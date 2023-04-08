package com.example.plantmi;

import java.sql.Time;

public class Plant{

    public String plantname;
    public String plantdesc;
    public Time time;

    public Plant() {
    }

    public Plant(String plantname, String plantdesc) {
        this.plantname = plantname;
        this.plantdesc = plantdesc;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}