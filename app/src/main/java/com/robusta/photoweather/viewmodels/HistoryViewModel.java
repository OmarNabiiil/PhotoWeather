package com.robusta.photoweather.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.robusta.photoweather.PhotoWeatherRepository;
import com.robusta.photoweather.entities.WeatherPhoto;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    PhotoWeatherRepository photoWeatherRepository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        photoWeatherRepository = PhotoWeatherRepository.getRepository(application);
    }

    public LiveData<List<WeatherPhoto>> getAllHistory() {
        return photoWeatherRepository.getAllItemsInWeatherPhotosHistory();
    }
}
