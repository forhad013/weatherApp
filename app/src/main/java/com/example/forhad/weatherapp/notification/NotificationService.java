package com.example.forhad.weatherapp.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.forhad.weatherapp.R;
import com.example.forhad.weatherapp.activity.wetherlist_activity.GetWeatherIntractorImpl;
import com.example.forhad.weatherapp.activity.wetherlist_activity.MainPresenterImpl;
import com.example.forhad.weatherapp.activity.wetherlist_activity.WeatherListActivity;
import com.example.forhad.weatherapp.http.apimodel.WeatherList;
import com.example.forhad.weatherapp.http.ApiClient;
import com.example.forhad.weatherapp.http.ApiInterafce;
import com.example.forhad.weatherapp.utils.TrackGPS;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mac on 2/29/16.
 */
public class NotificationService extends IntentService {

 static public Ringtone ringtone=null;
    Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);


    double latitude,longitude;

    NotificationMVP.Presenter presenter;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {




        presenter = new NotificationPresenterImpl(new GetLocationWeatherIntractorImpl());

        presenter.getLocation(getApplicationContext());


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
