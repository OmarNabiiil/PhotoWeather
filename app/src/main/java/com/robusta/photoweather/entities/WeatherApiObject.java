package com.robusta.photoweather.entities;

import androidx.room.Embedded;
import androidx.room.Ignore;

import java.util.List;

public class WeatherApiObject {

    private String name;
    @Ignore
    private List<Weather> weather;
    @Embedded
    private WeatherCondition main;

    public WeatherApiObject(String name,  WeatherCondition main) {
        this.name = name;
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public WeatherCondition getMain() {
        return main;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public void setMain(WeatherCondition main) {
        this.main = main;
    }
}
