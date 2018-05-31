package com.example.forhad.weatherapp.activity.wetherlist_activity;

import android.util.Log;

import com.example.forhad.weatherapp.http.ApiClient;
import com.example.forhad.weatherapp.http.ApiInterafce;
import com.example.forhad.weatherapp.http.apimodel.WeatherList;
import com.example.forhad.weatherapp.http.apimodel.WeatherResponse;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bpn on 12/7/17.
 */

public class GetWeatherIntractorImpl implements WeatherListMVP.GetWeatherhIntractor {


    @Override
    public void getWeatherIntractor(final OnFinishedListener onFinishedListener, String latitude, String longitude) {
        /** Create handle for the RetrofitInstance interface*/
        ApiInterafce apiService =  ApiClient.getClient().create(ApiInterafce.class);

        /** Call the method with parameter in the interface to get the notice data*/



        String appId = "e384f9ac095b2109c751d95296f8ea76";
        String units = "metric";

        Call<WeatherResponse> call = apiService.getWeather(latitude+"", longitude + "", 50 + "", units, appId);


        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                onFinishedListener.onFinished(response.body().getList());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Log error here since request failed
                onFinishedListener.onFailure(t);


            }
        });
    }
}
