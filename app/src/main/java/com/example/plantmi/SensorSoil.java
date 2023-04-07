package com.example.plantmi;

import java.io.Serializable;

public class SensorSoil implements Serializable {
    private String type;
    private String value;

    public SensorSoil(){
    }

    public String getType(){return type;}
    public String getValue(){return value;}

}
