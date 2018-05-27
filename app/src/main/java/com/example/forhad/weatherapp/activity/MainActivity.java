package com.example.forhad.weatherapp.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.forhad.weatherapp.R;
import com.example.forhad.weatherapp.adapter.WeatherListAdapter;
import com.example.forhad.weatherapp.alarm.AlarmBrodcastReceiver;
import com.example.forhad.weatherapp.model.WeatherList;
import com.example.forhad.weatherapp.model.WeatherResponse;
import com.example.forhad.weatherapp.retrofit.ApiClient;
import com.example.forhad.weatherapp.retrofit.ApiInterafce;
import com.example.forhad.weatherapp.utils.TrackGPS;

import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    double latitude, longitude;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    ListView list;
    WeatherListAdapter weatherListAdapter;

    ArrayList<WeatherList> weatherModelArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);

        weatherModelArraylist = new ArrayList<>();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        MainActivityPermissionsDispatcher.getLocationWithPermissionCheck(MainActivity.this);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);


                i.putExtra("name", weatherModelArraylist.get(position).name);
                i.putExtra("status", weatherModelArraylist.get(position).getWeather().get(0).getDescription());
                i.putExtra("currentTemp", weatherModelArraylist.get(position).getMain().temp);
                i.putExtra("maxTemp", weatherModelArraylist.get(position).getMain().tempMax);
                i.putExtra("minTemp", weatherModelArraylist.get(position).getMain().tempMin);
                i.putExtra("wind", weatherModelArraylist.get(position).getWind().getSpeed());
                i.putExtra("humadity", weatherModelArraylist.get(position).getMain().humidity);
                i.putExtra("lat", weatherModelArraylist.get(position).getCoord().lat);
                i.putExtra("lon", weatherModelArraylist.get(position).getCoord().lon);

                startActivity(i);


            }
        });



         setAlarmHours(18,53);

    }




    /*
    * method for setting alarm for calling current temparature
    * */
    public void setAlarmHours(int hours,int minute){



        Calendar calendar = Calendar.getInstance();

        Log.e("val", hours + " " + minute);


        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent myIntent = new Intent(getApplicationContext(), AlarmBrodcastReceiver.class);


        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent, 0);


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), 1000 * 60  *60, pendingIntent);



    }

    /*
    * method for getting current location of the user
    * */
    @NeedsPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
    void getLocation() {

        TrackGPS trackGPS = new TrackGPS(getApplicationContext());

        if (trackGPS.canGetLocation()) {
            latitude = trackGPS.getLatitude();
            longitude = trackGPS.getLongitude();
            Log.e("latitude", latitude + "");
            Log.e("longitude", longitude + "");


            if (((int) latitude != 0 || (int) latitude != 0)) {
                getWeatherData();
            }else{
                MainActivityPermissionsDispatcher.getLocationWithPermissionCheck(MainActivity.this);
            }


        } else {

            trackGPS.showSettingsAlert();
        }


    }

    @OnShowRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)
    void showRationaleForLocation(final PermissionRequest request) {
        new AlertDialog.Builder(getApplicationContext())
                .setMessage(R.string.location_message)
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();

                    }
                })
                .show();
    }

    @OnPermissionDenied(android.Manifest.permission.ACCESS_FINE_LOCATION)
    void showDeniedForLocation() {
        Toast.makeText(getApplicationContext(), "Permission dined", Toast.LENGTH_SHORT).show();
        finish();
    }

    @OnNeverAskAgain(android.Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForLocation() {
        Toast.makeText(getApplicationContext(), "never ask", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }



    /*
    * method for calling api to get weather data and set the list with using adapter
    * */
    public void getWeatherData() {


        ApiInterafce apiService =
                ApiClient.getClient().create(ApiInterafce.class);


        String appId = "e384f9ac095b2109c751d95296f8ea76";
        String units = "metric";

        Call<WeatherResponse> call = apiService.getWeather(latitude+"", longitude + "", 50 + "", units, appId);


        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {


                try {


                    String msg = response.body().getMessage();

                    weatherModelArraylist = response.body().getList();


                    weatherListAdapter = new WeatherListAdapter(getApplicationContext(), weatherModelArraylist);
                    list.setAdapter(weatherListAdapter);




                } catch (Exception e) {

                    e.printStackTrace();


                }


            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Log error here since request failed
                t.printStackTrace();


            }
        });


    }



}
