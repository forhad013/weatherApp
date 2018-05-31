package com.example.forhad.weatherapp.activity.splash_activity;

import android.content.Context;

import com.example.forhad.weatherapp.App;

import permissions.dispatcher.NeedsPermission;

public class SplashPresenterImpl implements SplashActivityMVP.Presenter{


    public SplashPresenterImpl(SplashActivityMVP.View view) {
        this.view = view;
    }

    SplashActivityMVP.View view;


    @NeedsPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
    @Override
    public void runTimer() {
          final boolean _active = true;
          final int _splashTime = 2000;

          final Context context = App.getAppContext();

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {
                    view.goToNextScreen();
                }
            };
        };


        splashTread.start();

    }

    @Override
    public void destroy() {
        view = null;
    }
}
