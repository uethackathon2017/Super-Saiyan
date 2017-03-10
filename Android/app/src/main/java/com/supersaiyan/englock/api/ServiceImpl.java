package com.supersaiyan.englock.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceImpl {
    private static ServiceImpl instance;

    public static ServiceImpl getInstance() {
        if (instance == null) {
            instance = new ServiceImpl();
        }
        return instance;
    }

    private Service service;

    private ServiceImpl() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(Service.class);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Service getService() {
        return service;
    }

}