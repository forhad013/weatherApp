package com.example.forhad.weatherapp.activity.wetherlist_activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.forhad.weatherapp.activity.map_activity.MapsActivity;
import com.example.forhad.weatherapp.http.apimodel.WeatherList;
import com.example.forhad.weatherapp.notification.NotificationBrodcastReceiver;
import com.example.forhad.weatherapp.utils.TrackGPS;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by bpn on 12/7/17.
 */

public class MainPresenterImpl implements WeatherListMVP.presenter, WeatherListMVP.GetWeatherhIntractor.OnFinishedListener {

    private WeatherListMVP.MainView mainView;
    private WeatherListMVP.GetWeatherhIntractor getWeatehrIntractor;

    public MainPresenterImpl(WeatherListMVP.MainView mainView, WeatherListMVP.GetWeatherhIntractor getWeatehrIntractor) {
        this.mainView = mainView;
        this.getWeatehrIntractor = getWeatehrIntractor;
    }

    @Override
    public void onDestroy() {

        mainView = null;

    }

    @Override
    public void getLocation(Context context) {

        mainView.showProgress();
        TrackGPS trackGPS = new TrackGPS(context);


        double latitude,longitude;

        if (trackGPS.canGetLocation()) {
            latitude = trackGPS.getLatitude();
            longitude = trackGPS.getLongitude();

            if (((int) latitude != 0 || (int) latitude != 0)) {

                Log.e("la",latitude+"");

                requestDataFromServer(latitude+"",longitude+"");


            }else{
                getLocation(context);
            }


        } else {

            trackGPS.showSettingsAlert();
        }

    }


    @Override
    public void requestDataFromServer( String latitude,String longitude) {
        getWeatehrIntractor.getWeatherIntractor(this, latitude, longitude);
    }

    @Override
    public void listItemClicked(Context context,WeatherList weatherList) {
        Intent i = new Intent(context, MapsActivity.class);


        i.putExtra("name", weatherList.name);
        i.putExtra("status", weatherList.getWeather().get(0).getDescription());
        i.putExtra("currentTemp", weatherList.getMain().temp);
        i.putExtra("maxTemp", weatherList.getMain().tempMax);
        i.putExtra("minTemp", weatherList.getMain().tempMin);
        i.putExtra("wind", weatherList.getWind().getSpeed());
        i.putExtra("humadity", weatherList.getMain().humidity);
        i.putExtra("lat", weatherList.getCoord().lat);
        i.putExtra("lon", weatherList.getCoord().lon);

        context.startActivity(i);
    }

    @Override
    public void setNotification(int hour, int minute , Context context) {


         PendingIntent pendingIntent;
         AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();


        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Intent myIntent = new Intent(context, NotificationBrodcastReceiver.class);


        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 1000 * 60 * 60*24, pendingIntent);

    }



    @Override
    public void onFinished(ArrayList<WeatherList> weatherLists) {
        if(mainView != null){
            mainView.setDataToRecyclerView(weatherLists);
            mainView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(mainView != null){
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }
}
