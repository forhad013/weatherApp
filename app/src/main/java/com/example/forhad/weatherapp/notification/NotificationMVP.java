package com.example.forhad.weatherapp.notification;

import android.content.Context;

import com.example.forhad.weatherapp.activity.wetherlist_activity.WeatherListMVP;
import com.example.forhad.weatherapp.http.apimodel.WeatherList;

import java.util.ArrayList;

public interface NotificationMVP {

    interface Presenter{


        void getLocation(Context context);

        void getCurrentLocationWeather(String latitude,String longitude);

        void showNotification(WeatherList weatherList);
    }



    interface GetNotificationIntractor {



        interface OnFinishedListener {
            void onFinished(WeatherList weatherList);
            void onFailure(Throwable t);
        }

        void getWeatherIntractor(OnFinishedListener onFinishedListener , String latitude , String longitude);
    }
}
