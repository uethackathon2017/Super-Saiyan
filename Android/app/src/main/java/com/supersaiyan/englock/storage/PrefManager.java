package com.supersaiyan.englock.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.supersaiyan.englock.R;

import java.util.Calendar;

/**
 * Created by thanh_000 on 3/10/2017.
 */
public class PrefManager {
    private static PrefManager INSTANCE;

    public static PrefManager getInstance() {
        return INSTANCE;
    }

    public static void initialization(Context context) {
        INSTANCE = new PrefManager(context);
    }

    private static final String EXTRA_ACTIVE = "Active";
    private static final String EXTRA_IS_FIRST_TIME_LAUNCH = "IsFirstTimeLanch";
    private static final String EXTRA_USE_PASSWORD = "UsePassword";
    private static final String EXTRA_PASSWORD = "Password";
    private static final String EXTRA_TITLE = "Title";
    private static final String EXTRA_IMAGE_BACKGROUND_INDEX = "ImageBackgroundIndex";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private PrefManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public boolean isFirtTimeLaunch() {
        boolean result = preferences.getBoolean(EXTRA_IS_FIRST_TIME_LAUNCH, true);
        if (result) {
            editor.putBoolean(EXTRA_IS_FIRST_TIME_LAUNCH, false);
        }
        return result;
    }


}
