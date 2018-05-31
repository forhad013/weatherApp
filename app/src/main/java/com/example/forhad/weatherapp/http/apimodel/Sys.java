
package com.example.forhad.weatherapp.http.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;




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
