package com.example.forhad.weatherapp.alarm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AlarmBrodcastReceiver extends WakefulBroadcastReceiver {

    String titleForReciever;


    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        @Override
        public void onReceive(final Context context, Intent intent) {
            //this will update the UI with message
            String text = intent.getStringExtra("reminder_id");
            titleForReciever = intent.getStringExtra("reminder_title");



                        ComponentName comp = new ComponentName(context.getPackageName(),
                                AlarmService.class.getName());
                        startWakefulService(context, (intent.setComponent(comp)));
                        setResultCode(Activity.RESULT_OK);





                }
            }





