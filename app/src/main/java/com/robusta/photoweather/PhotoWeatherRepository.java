package com.robusta.photoweather;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.robusta.photoweather.daos.PhotosDao;
import com.robusta.photoweather.entities.ApiResponse;
import com.robusta.photoweather.entities.WeatherApiObject;
import com.robusta.photoweather.entities.WeatherPhoto;
import com.robusta.photoweather.helpers.Config;
import com.robusta.photoweather.model.local.PhotoWeatherRoomDatabase;
import com.robusta.photoweather.model.network.ApiClient;
import com.robusta.photoweather.model.network.PhotoWeatherService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoWeatherRepository {

    private PhotosDao photosDao;
    private PhotoWeatherService webClient;
    private static PhotoWeatherRepository INSTANCE;

    public static PhotoWeatherRepository getRepository(Application application) {
        if (INSTANCE == null) {
            synchronized (PhotoWeatherRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PhotoWeatherRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    public PhotoWeatherRepository(Application application) {
        webClient = ApiClient.getInstance();
        PhotoWeatherRoomDatabase photoWeatherRoomDatabase = PhotoWeatherRoomDatabase.getDatabase(application);
        photosDao = photoWeatherRoomDatabase.photosDao();
    }

    public MutableLiveData<ApiResponse<WeatherApiObject>> getWeatherInfo(double lon, double lat) {
        final MutableLiveData<ApiResponse<WeatherApiObject>> data = new MutableLiveData<>();

        Call<WeatherApiObject> call = webClient.getWeatherInfo(lat, lon, Config.API_KEY);
        call.enqueue(new Callback<WeatherApiObject>() {
            @Override
            public void onResponse(Call<WeatherApiObject> call, Response<WeatherApiObject> response) {
                WeatherApiObject weatherApiObjectApiResponse = response.body();

                data.postValue(new ApiResponse<>(response.code(), false, weatherApiObjectApiResponse));
            }

            @Override
            public void onFailure(Call<WeatherApiObject> call, Throwable t) {
                data.postValue(new ApiResponse<>(408, true, null));
            }
        });
        return data;
    }

    public LiveData<List<WeatherPhoto>> getAllItemsInWeatherPhotosHistory() {
        return photosDao.getAllItemsInWeatherPhotosHistory();
    }

    public void insertWeatherPhoto(WeatherPhoto weatherPhoto) {
        new insertWeatherPhotoAsyncTask(photosDao).execute(weatherPhoto);
    }

    private static class insertWeatherPhotoAsyncTask extends AsyncTask<WeatherPhoto, Void, Void> {

        private PhotosDao mAsyncPhotosDao;

        insertWeatherPhotoAsyncTask(PhotosDao dao) {
            mAsyncPhotosDao = dao;
        }

        @Override
        protected Void doInBackground(final WeatherPhoto... params) {
            mAsyncPhotosDao.insertWeatherPhoto(params[0]);
            return null;
        }
    }
}
