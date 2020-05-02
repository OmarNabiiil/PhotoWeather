package com.robusta.photoweather.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.robusta.photoweather.PhotoWeatherRepository;
import com.robusta.photoweather.entities.ApiResponse;
import com.robusta.photoweather.entities.WeatherApiObject;
import com.robusta.photoweather.entities.WeatherPhoto;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    PhotoWeatherRepository photoWeatherRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        photoWeatherRepository = PhotoWeatherRepository.getRepository(application);
    }

    public MutableLiveData<ApiResponse<WeatherApiObject>> getWeatherInfo(double lon, double lat) {
        return photoWeatherRepository.getWeatherInfo(lon, lat);
    }

    public void insertWeatherInfo(WeatherPhoto weatherPhoto) {
        photoWeatherRepository.insertWeatherPhoto(weatherPhoto);
    }
}
