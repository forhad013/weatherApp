package com.example.forhad.weatherapp.activity.map_activity;

public class MapPresenterImpl implements MapsMVP.Presenter{

    public MapPresenterImpl(MapsMVP.View view) {
        this.view = view;
    }

    MapsMVP.View view;

    @Override
    public void setData() {

        view.setCityName();
        view.setHumadity();
        view.setMaxTemp();
        view.setMinTemp();
        view.setTemperature();
        view.setWeatherStatus();
        view.setWind();

    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
