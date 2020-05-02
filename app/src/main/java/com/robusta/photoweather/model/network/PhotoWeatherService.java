package com.robusta.photoweather.model.network;

import com.robusta.photoweather.entities.ApiResponse;
import com.robusta.photoweather.entities.WeatherApiObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PhotoWeatherService {

    @GET("weather")
    Call<WeatherApiObject> getWeatherInfo(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String api_key);
}
