package com.robusta.photoweather.model.network;

import com.robusta.photoweather.helpers.Config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final OkHttpClient client;

    private static PhotoWeatherService sInstance;

    private static final Object sLock = new Object();

    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    public static PhotoWeatherService getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = getRetrofitInstance().create(PhotoWeatherService.class);
            }
            return sInstance;
        }
    }

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
