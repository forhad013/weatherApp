package com.example.forhad.weatherapp.activity.splash_activity;

import com.example.forhad.weatherapp.notification.NotificationMVP;

public interface SplashActivityMVP {

    interface Presenter{

        void runTimer();
        void destroy();
    }

    interface View{
        void goToNextScreen();
    }
}
