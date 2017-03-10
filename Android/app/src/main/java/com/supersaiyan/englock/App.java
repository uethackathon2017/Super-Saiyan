package com.supersaiyan.englock;

import android.app.Application;
import android.content.Context;

import com.supersaiyan.englock.model.UserConfig;
import com.supersaiyan.englock.storage.DatabaseManager;
import com.supersaiyan.englock.storage.PrefManager;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class App extends Application {
    private static Context appLicationContext;

    public static Context getAppLicationContext() {
        return appLicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appLicationContext = getApplicationContext();
        DatabaseManager.initialization(getApplicationContext());
        PrefManager.initialization(getApplicationContext());
        UserConfig.initialization(getApplicationContext());
    }
}
