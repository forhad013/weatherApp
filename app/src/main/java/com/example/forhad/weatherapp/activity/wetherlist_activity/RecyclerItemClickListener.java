package com.example.forhad.weatherapp.activity.wetherlist_activity;

import com.example.forhad.weatherapp.http.apimodel.WeatherList;


public interface RecyclerItemClickListener {
    void onItemClick(WeatherList weatherList);
}