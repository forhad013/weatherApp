package com.example.forhad.weatherapp.utils;

import java.text.DecimalFormat;

public class UtilMethods {

    public String capFirstLetter(String text){
        StringBuilder sb = new StringBuilder(text);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    public String getTempdataInCalcious(double tempData){
        String temp="";

        DecimalFormat df = new DecimalFormat("#");

        temp = df.format(tempData);

        temp = temp + ((char) 0x00B0) + "C";

        return temp;
    }
}
