
package com.example.forhad.weatherapp.http.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



import java.util.ArrayList;

public class WeatherResponse {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("cod")
    @Expose
    public String cod;
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("list")
    @Expose
    public ArrayList<WeatherList> list = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<WeatherList> getList() {
        return list;
    }

    public void setList(ArrayList<WeatherList> list) {
        this.list = list;
    }

}
