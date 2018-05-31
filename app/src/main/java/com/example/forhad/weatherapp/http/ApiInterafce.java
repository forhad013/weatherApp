package com.example.forhad.weatherapp.http;


import com.example.forhad.weatherapp.http.apimodel.WeatherList;
import com.example.forhad.weatherapp.http.apimodel.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mac on 8/11/16.
 */
public interface ApiInterafce {


  //  http://api.openweathermap.org/data/2.5/find?lat=23.68&lon=90.35&cnt=50&appid=e384f9ac095b2109c751d95296f8ea76





    @GET("data/2.5/find")
    Call<WeatherResponse> getWeather(@Query("lat") String lat,
                                      @Query("lon") String lon,
                                      @Query("cnt") String cnt,
                                      @Query("units") String units,
                                      @Query("appid") String appid);

    @GET("data/2.5/weather")
    Call<WeatherList> getCurrentWeather(@Query("lat") String lat,
                                        @Query("lon") String lon,
                                        @Query("units") String units,
                                        @Query("appid") String appid);








}
