package com.example.forhad.weatherapp.activity.wetherlist_activity;

import android.content.Context;

import com.example.forhad.weatherapp.http.apimodel.WeatherList;
import java.util.ArrayList;

/**
 * Created by bpn on 12/6/17.
 */

public interface WeatherListMVP {

    /**
     * Call when user interact with the view and other when view OnDestroy()
     * */
    interface presenter{

        void onDestroy();
        void getLocation(Context context);

        void requestDataFromServer(String latitude,String longitude);

        void listItemClicked(Context context, WeatherList weatherList);

        void setNotification(int hour, int minute , Context context);
    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetNoticeInteractorImpl class
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(ArrayList<WeatherList> noticeArrayList);

        void onResponseFailure(Throwable throwable);



    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface GetWeatherhIntractor {



        interface OnFinishedListener {
            void onFinished(ArrayList<WeatherList> weatherLists);
            void onFailure(Throwable t);
        }

        void getWeatherIntractor(OnFinishedListener onFinishedListener , String latitude , String longitude);
    }
}
