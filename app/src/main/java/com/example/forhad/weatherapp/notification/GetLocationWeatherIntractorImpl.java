package com.example.forhad.weatherapp.notification;

import com.example.forhad.weatherapp.activity.wetherlist_activity.WeatherListMVP;
import com.example.forhad.weatherapp.http.ApiClient;
import com.example.forhad.weatherapp.http.ApiInterafce;
import com.example.forhad.weatherapp.http.apimodel.WeatherList;
import com.example.forhad.weatherapp.http.apimodel.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bpn on 12/7/17.
 */

public class GetLocationWeatherIntractorImpl implements NotificationMVP.GetNotificationIntractor {


    @Override
    public void getWeatherIntractor(final OnFinishedListener onFinishedListener, String latitude, String longitude) {
        ApiInterafce apiService =
                ApiClient.getClient().create(ApiInterafce.class);


        String appId = "e384f9ac095b2109c751d95296f8ea76";
        String units = "metric";

        Call<WeatherList> call = apiService.getCurrentWeather(latitude + "",longitude + "", units, appId);


        call.enqueue(new Callback<WeatherList>() {
            @Override
            public void onResponse(Call<WeatherList> call, Response<WeatherList> response) {


                try {


                    onFinishedListener.onFinished(response.body());


                } catch (Exception e) {
                    onFinishedListener.onFailure(e);
                    e.printStackTrace();


                }


            }

            @Override
            public void onFailure(Call<WeatherList> call, Throwable t) {
                // Log error here since request failed
                t.printStackTrace();
                onFinishedListener.onFailure(t);


            }
        });
    }
}
