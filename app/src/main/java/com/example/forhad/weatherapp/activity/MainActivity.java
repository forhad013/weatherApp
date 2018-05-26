package com.example.forhad.weatherapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.forhad.weatherapp.R;
import com.example.forhad.weatherapp.adapter.WeatherListAdapter;
import com.example.forhad.weatherapp.model.WeatherList;
import com.example.forhad.weatherapp.model.WeatherResponse;
import com.example.forhad.weatherapp.retrofit.ApiClient;
import com.example.forhad.weatherapp.retrofit.ApiInterafce;
import com.example.forhad.weatherapp.utils.TrackGPS;

import org.parceler.Parcels;

import java.util.ArrayList;

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

    ListView list;
    WeatherListAdapter weatherListAdapter;

    ArrayList<WeatherList> weatherModelArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);

        weatherModelArraylist = new ArrayList<>();




        MainActivityPermissionsDispatcher.getLocationWithPermissionCheck(MainActivity.this);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);



                i.putExtra("name",  weatherModelArraylist.get(position).name);
                i.putExtra("status",  weatherModelArraylist.get(position).getWeather().get(0).getDescription());
                i.putExtra("currentTemp",  weatherModelArraylist.get(position).getMain().temp);
                i.putExtra("maxTemp",weatherModelArraylist.get(position).getMain().tempMax);
                i.putExtra("minTemp", weatherModelArraylist.get(position).getMain().tempMin);
                i.putExtra("wind", weatherModelArraylist.get(position).getWind().getSpeed());
                i.putExtra("humadity", weatherModelArraylist.get(position).getMain().humidity);
                i.putExtra("lat", weatherModelArraylist.get(position).getCoord().lat);
                i.putExtra("lon", weatherModelArraylist.get(position).getCoord().lon);

                startActivity(i);


            }
        });
    }

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
            }


        }else {

            trackGPS.showSettingsAlert();
        }



    }

    @OnShowRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)
    void showRationaleForCamera(final PermissionRequest request) {
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
    void showDeniedForCamera() {
        Toast.makeText(getApplicationContext(), "Permission dined", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(android.Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForCamera() {
        Toast.makeText(getApplicationContext(), "never ask", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    public void getWeatherData() {


        ApiInterafce apiService =
                ApiClient.getClient().create(ApiInterafce.class);


        String appId = "e384f9ac095b2109c751d95296f8ea76";
        String units = "metric";

        Call<WeatherResponse> call = apiService.getWeather(37.42 + "", -122.08 + "", 50 + "",units, appId);


        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {


                try {


                    String msg = response.body().getMessage();


                    Log.e("msg", msg);

                    weatherModelArraylist = response.body().getList();

                    weatherListAdapter = new WeatherListAdapter(getApplicationContext(),weatherModelArraylist);
                    list.setAdapter(weatherListAdapter);


                    Log.e("size", weatherModelArraylist.get(0).getName()+"");
                    Log.e(" e", weatherModelArraylist.get(0).getMain().getTemp()+"");

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
