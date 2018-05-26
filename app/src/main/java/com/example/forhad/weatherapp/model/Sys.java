
package com.example.forhad.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


public class Sys {

    @SerializedName("country")
    @Expose
    public String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public void Sys(){

    }
}
