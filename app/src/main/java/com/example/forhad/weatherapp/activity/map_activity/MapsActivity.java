package com.example.forhad.weatherapp.activity.map_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.forhad.weatherapp.R;
import com.example.forhad.weatherapp.utils.UtilMethods;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, MapsMVP.View {

    private GoogleMap mMap;
    @BindView(R.id.cityName)
    TextView cityNameView;

    @BindView(R.id.currentTemp)
    TextView currentTempView;

    @BindView(R.id.max)
    TextView maxTempView;

    @BindView(R.id.min)
    TextView minTempView;

    @BindView(R.id.wind)
    TextView windView;

    @BindView(R.id.humadity)
    TextView humadityView;

    @BindView(R.id.weatherStatus)
    TextView statusView;



    String cityNameString, weatherStatus;
    double currentTempDouble, maxTempDouble, minTempDouble, windDouble, humadityDouble, lat, lon;


    UtilMethods utilMethods;

    MapsMVP.Presenter presenter;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
      //  ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        utilMethods = new UtilMethods();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);






        cityNameString = getIntent().getStringExtra("name");
        weatherStatus = getIntent().getStringExtra("status");
        currentTempDouble = getIntent().getDoubleExtra("currentTemp", 0.0);
        maxTempDouble = getIntent().getDoubleExtra("maxTemp", 0.0);
        minTempDouble = getIntent().getDoubleExtra("minTemp", 0.0);
        windDouble = getIntent().getDoubleExtra("wind", 0.0);
        humadityDouble = getIntent().getFloatExtra("humadity", 0);
        lat = getIntent().getDoubleExtra("lat", 0.0);
        lon = getIntent().getDoubleExtra("lon", 0.0);


        presenter = new MapPresenterImpl(this);
        presenter.setData();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
       unbinder.unbind();
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

    @Override
    public void setCityName() {
        cityNameView.setText(cityNameString);
    }

    @Override
    public void setTemperature() {
        currentTempView.setText(utilMethods.getTempdataInCalcious(currentTempDouble));
    }

    @Override
    public void setMaxTemp() {
        maxTempView.setText("Max. Temp.: " + utilMethods.getTempdataInCalcious(maxTempDouble));
    }

    @Override
    public void setMinTemp() {
        minTempView.setText("Min. Temp.: " + utilMethods.getTempdataInCalcious(minTempDouble));
    }

    @Override
    public void setWeatherStatus() {
        statusView.setText(utilMethods.capFirstLetter(weatherStatus));
    }

    @Override
    public void setWind() {
        windView.setText("Wind Speed: " + windDouble + "");
    }

    @Override
    public void setHumadity() {

        humadityView.setText("Humadity: " + humadityDouble + "");
    }
}
