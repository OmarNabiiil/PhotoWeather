package com.robusta.photoweather.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.robusta.photoweather.daos.PhotosDao;
import com.robusta.photoweather.entities.WeatherPhoto;

@Database(entities = {WeatherPhoto.class}, version = 2, exportSchema = true)
public abstract class PhotoWeatherRoomDatabase extends RoomDatabase {

    public abstract PhotosDao photosDao();
    private static PhotoWeatherRoomDatabase INSTANCE;

    public static PhotoWeatherRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhotoWeatherRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PhotoWeatherRoomDatabase.class, "eav_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
