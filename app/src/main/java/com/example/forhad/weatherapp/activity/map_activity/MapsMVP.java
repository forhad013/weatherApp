package com.example.forhad.weatherapp.activity.map_activity;

public interface MapsMVP  {


    interface View{


        void setCityName();
        void setTemperature();
        void setMaxTemp();
        void setMinTemp();
        void setWeatherStatus();
        void setWind();
        void setHumadity();
    }

    interface Presenter{

        void setData();
        void onDestroy();
    }
}
