package com.example.forhad.weatherapp.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.forhad.weatherapp.App;
import com.example.forhad.weatherapp.R;
import com.example.forhad.weatherapp.activity.wetherlist_activity.WeatherListActivity;
import com.example.forhad.weatherapp.activity.wetherlist_activity.WeatherListMVP;
import com.example.forhad.weatherapp.http.apimodel.WeatherList;
import com.example.forhad.weatherapp.utils.TrackGPS;
import com.example.forhad.weatherapp.utils.UtilMethods;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by bpn on 12/7/17.
 */

public class NotificationPresenterImpl implements NotificationMVP.Presenter, NotificationMVP.GetNotificationIntractor.OnFinishedListener {



    private NotificationMVP.GetNotificationIntractor getNotificationIntractor;


    public NotificationPresenterImpl(NotificationMVP.GetNotificationIntractor getNotificationIntractor) {
        this.getNotificationIntractor = getNotificationIntractor;
    }



    @Override
    public void getLocation(Context context) {


        TrackGPS trackGPS = new TrackGPS(context);


        double latitude,longitude;

        if (trackGPS.canGetLocation()) {
            latitude = trackGPS.getLatitude();
            longitude = trackGPS.getLongitude();

            if (((int) latitude != 0 || (int) latitude != 0)) {



                getCurrentLocationWeather(latitude+"",longitude+"");


            }else{
                getLocation(context);
            }


        } else {

            trackGPS.showSettingsAlert();
        }

    }


    @Override
    public void onFinished(WeatherList weatherList) {
        showNotification(weatherList);
    }

    @Override
    public void onFailure(Throwable t) {

    }




    @Override
    public void getCurrentLocationWeather(String latitude,String longitude) {

        getNotificationIntractor.getWeatherIntractor(this,latitude,longitude);

    }

    @Override
    public void showNotification(WeatherList weatherList) {

        Context context = App.getAppContext();

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.custom_notification);


        UtilMethods utilMethods = new UtilMethods();

        double temp = weatherList.main.temp;



        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(App.getAppContext(), WeatherListActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        String messageToShow = "Current temperature: " +utilMethods.getTempdataInCalcious(temp);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.getAppContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Temparature")
                .setOngoing(false)
                .setAutoCancel(true)
                .setContentIntent(pIntent)

                .setContent(remoteViews);
        remoteViews.setImageViewResource(R.id.appIcon, R.drawable.circle);
        remoteViews.setTextViewText(R.id.temp, messageToShow);


        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(007, builder.build());

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
