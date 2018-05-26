package com.example.forhad.weatherapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.forhad.weatherapp.R;

public class SplashActivity extends AppCompatActivity {
    protected boolean _active = true;
    protected int _splashTime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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

//                    startActivity(new Intent(SplashActivity.this,
//                            DirChooserSample.class));

                    startActivity(new Intent(SplashActivity.this,
                            MainActivity.class));
                    finish();

                }
            };
        };


        // if(shouldOpen()) {
        splashTread.start();
        //  }

    }
}
