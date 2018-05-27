package com.example.forhad.weatherapp.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.forhad.weatherapp.R;
import com.example.forhad.weatherapp.model.WeatherList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

   TextView cityNameView,currentTempView, maxTempView,minTempView,windView,humadityView,statusView;
   String cityNameString,weatherStatus;
   double currentTempDouble, maxTempDouble,minTempDouble,windDouble,humadityDouble,lat,lon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cityNameView = findViewById(R.id.cityName);
        currentTempView = findViewById(R.id.currentTemp);
        maxTempView = findViewById(R.id.max);
        minTempView = findViewById(R.id.min);
        windView = findViewById(R.id.wind);
        humadityView = findViewById(R.id.humadity);
        statusView = findViewById(R.id.weatherStatus);




        cityNameString = getIntent().getStringExtra("name");
        weatherStatus = getIntent().getStringExtra("status");
        currentTempDouble = getIntent().getDoubleExtra("currentTemp",0.0);
        maxTempDouble = getIntent().getDoubleExtra("maxTemp",0.0);
        minTempDouble = getIntent().getDoubleExtra("minTemp",0.0);
        windDouble = getIntent().getDoubleExtra("wind",0.0);
        humadityDouble = getIntent().getFloatExtra("humadity",0);
        lat = getIntent().getDoubleExtra("lat",0.0);
        lon = getIntent().getDoubleExtra("lon",0.0);




        cityNameView.setText(cityNameString);
        statusView.setText(capFirstLetter(weatherStatus));


   //     double tempInDouble = weatherList.getMain().getTemp();




        currentTempView.setText(getTempdataInCalcious(currentTempDouble));


        maxTempView.setText("Max. Temp.: "+getTempdataInCalcious(maxTempDouble));
        minTempView.setText("Min. Temp.: "+getTempdataInCalcious(minTempDouble));
        windView.setText("Wind Speed: "+windDouble+"");
        humadityView.setText("Humadity: "+humadityDouble+"");
    }

    /*
    * method for formating degree celsius
    * */
    public String getTempdataInCalcious(double tempData){
        String temp="";

        DecimalFormat df = new DecimalFormat("#");

        temp = df.format(tempData);

        temp = temp + ((char) 0x00B0) + "C";

        return temp;
    }

    /*
    * method for captalize the first letter
    * */
    public String capFirstLetter(String text){
        StringBuilder sb = new StringBuilder(text);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13.0f));


    }
}
