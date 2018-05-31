
package com.example.forhad.weatherapp.http.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;




public class Coord {

    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lon")
    @Expose
    public Double lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
    public void Coord(){

    }
}
