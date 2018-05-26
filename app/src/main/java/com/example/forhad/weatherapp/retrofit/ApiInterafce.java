package com.example.forhad.weatherapp.retrofit;


import com.example.forhad.weatherapp.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mac on 8/11/16.
 */
public interface ApiInterafce {


  //  http://api.openweathermap.org/data/2.5/find?lat=23.68&lon=90.35&cnt=50&appid=e384f9ac095b2109c751d95296f8ea76


        //topicsList
//    @GET("api/v1.2/home")
//    Call<SplashResponse> getSplashData();



    @GET("data/2.5/find")
    Call<WeatherResponse> getWeather(@Query("lat") String lat,
                                      @Query("lon") String lon,
                                      @Query("cnt") String cnt,
                                      @Query("units") String units,
                                      @Query("appid") String appid);

    @GET("data/2.5/weather")
    Call<WeatherResponse> getCurrentWeather(@Query("lat") String lat,
                                      @Query("lon") String lon);







//    @GET("api/v1.2/categorybrands")
//    Call<CategoryBrandResponse> getCategoryBrand();
//
//    @GET("api/v1.2/productdetails/{id}")
//    Call<ProductDetailsResponse> getProductDetails(@Path("id") int id);
//
//
//    @GET("api/v1.2/autosuggestion/{term}")
//    Call<AutoCompleteResponse> getAutoComplete(@Path("term") String term);

}
