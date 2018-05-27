package com.example.forhad.weatherapp.alarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.forhad.weatherapp.R;
import com.example.forhad.weatherapp.activity.MainActivity;
import com.example.forhad.weatherapp.model.WeatherList;
import com.example.forhad.weatherapp.retrofit.ApiClient;
import com.example.forhad.weatherapp.retrofit.ApiInterafce;
import com.example.forhad.weatherapp.utils.TrackGPS;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mac on 2/29/16.
 */
public class AlarmService extends IntentService {

 static public Ringtone ringtone=null;
    Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);


    double latitude,longitude;
    String title,time,option,data,id;

    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {


        getLocation();


    }
    void getLocation() {

        TrackGPS trackGPS = new TrackGPS(getApplicationContext());

        if (trackGPS.canGetLocation()) {
            latitude = trackGPS.getLatitude();
            longitude = trackGPS.getLongitude();
            Log.e("latitude", latitude + "");
            Log.e("longitude", longitude + "");


            if (((int) latitude != 0 || (int) latitude != 0)) {
                getCurrentWeather();
            }


        } else {

            trackGPS.showSettingsAlert();
        }


    }


    public void getCurrentWeather() {


        ApiInterafce apiService =
                ApiClient.getClient().create(ApiInterafce.class);


        String appId = "e384f9ac095b2109c751d95296f8ea76";
        String units = "metric";

        Call<WeatherList> call = apiService.getCurrentWeather(latitude + "",longitude + "", units, appId);


        call.enqueue(new Callback<WeatherList>() {
            @Override
            public void onResponse(Call<WeatherList> call, Response<WeatherList> response) {


                try {





                    customNotification(response.body().getMain().getTemp());


                } catch (Exception e) {

                    e.printStackTrace();


                }


            }

            @Override
            public void onFailure(Call<WeatherList> call, Throwable t) {
                // Log error here since request failed
                t.printStackTrace();


            }
        });


    }

    public void customNotification(double temp) {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.custom_notification);


        Log.e("notificationTemp",temp+"");
        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Temparature")
                .setOngoing(false)
                .setAutoCancel(true)
                .setContentIntent(pIntent)

                .setContent(remoteViews);
        remoteViews.setImageViewResource(R.id.appIcon, R.drawable.circle);
        remoteViews.setTextViewText(R.id.temp, getTempdataInCalcious(temp));


        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(007, builder.build());

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getTempdataInCalcious(double tempData) {
        String temp = "";

        DecimalFormat df = new DecimalFormat("#");

        temp = df.format(tempData);

        temp = temp + ((char) 0x00B0) + "C";

        temp = "Current temparature: " + temp;
        return temp;
    }

    @Override
    public void onDestroy() {


    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


    @Override
    public boolean onUnbind(Intent intent) {

        return super.onUnbind(intent);
    }
}
