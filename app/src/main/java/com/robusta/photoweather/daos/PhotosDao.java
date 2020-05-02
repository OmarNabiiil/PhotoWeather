package com.robusta.photoweather.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.robusta.photoweather.entities.WeatherPhoto;

import java.util.List;

@Dao
public abstract class PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertWeatherPhoto(WeatherPhoto weatherPhoto);

    @Query("SELECT * FROM weather_photos")
    public abstract LiveData<List<WeatherPhoto>> getAllItemsInWeatherPhotosHistory();

}
