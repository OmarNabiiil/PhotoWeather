package com.robusta.photoweather.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_photos")
public class WeatherPhoto {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private byte[] image;

    @Embedded
    private WeatherApiObject weatherInfo;

    public WeatherPhoto(byte[] image, WeatherApiObject weatherInfo) {
        this.image = image;
        this.weatherInfo = weatherInfo;
    }

    public int getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public WeatherApiObject getWeatherInfo() {
        return weatherInfo;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setWeatherInfo(WeatherApiObject weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public void setId(int id) {
        this.id = id;
    }
}
