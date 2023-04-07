package com.example.plantmi;

public class User {

    public String plantname;
    public String plantdesc;
    public String user;

    public User() {
    }

    public User(String user, String plantname, String plantdesc) {
        this.user = user;
        this.plantname = plantname;
        this.plantdesc = plantdesc;
    }

}