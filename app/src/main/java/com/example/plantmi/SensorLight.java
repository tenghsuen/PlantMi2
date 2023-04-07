package com.example.plantmi;

import java.io.Serializable;

public class SensorLight implements Serializable {
    private String type;
    private String value;

    public SensorLight(){
    }

    public String getType(){return type;}
    public String getValue(){return value;}

}
