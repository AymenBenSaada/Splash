package com.example.macbook.splash.Interfaces;

import android.util.Log;

import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static final String BASE_URL = "https://edonecserver.azurewebsites.net/";
    private static final String BASE_URL_1 = "http://192.168.100.66:51165";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) //bind gson to create for customization
                    .build();
        }
        return retrofit;
    }
}
