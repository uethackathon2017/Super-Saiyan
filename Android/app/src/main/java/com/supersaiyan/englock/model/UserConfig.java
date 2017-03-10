package com.supersaiyan.englock.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.preference.PreferenceManager;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.BR;

/**
 * Created by thanh_000 on 3/11/2017.
 */

public class UserConfig extends BaseObservable {

    public static void initialization(Context context) {
        INSTANCE = new UserConfig(context);
    }

    private static UserConfig INSTANCE;

    public static UserConfig getInstance() {
        return INSTANCE;
    }

    private static final String EXTRA_ACTIVE = "ExtraActive";
    private static final String EXTRA_USE_PASSWORD = "ExtraUsePassword";
    private static final String EXTRA_PASSWORD = "Password";
    private static final String EXTRA_TITLE = "ExtraLockTitle";
    private static final String EXTRA_NUMBER_ANSWER = "ExtraNumberAnswer";
    private static final String EXTRA_IMAGE_BACKGROUND_INDEX = "ImageBackgroundIndex";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Bindable
    private boolean activeLock;
    @Bindable
    private String lockTitle;
    @Bindable
    private boolean usePassWord;
    @Bindable
    private String passWord;
    @Bindable
    private int numberAnswer;

    private UserConfig(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        loadData(context);
    }

    private void loadData(Context context) {
        activeLock = preferences.getBoolean(EXTRA_ACTIVE, false);
        lockTitle = preferences.getString(EXTRA_TITLE, context.getString(R.string.app_name));
        usePassWord = preferences.getBoolean(EXTRA_USE_PASSWORD, false);
        passWord = preferences.getString(EXTRA_PASSWORD, "1111");
        numberAnswer = preferences.getInt(EXTRA_NUMBER_ANSWER, 2);
    }

    public boolean isActiveLock() {
        return activeLock;
    }

    public void setActiveLock(boolean activeLock) {
        this.activeLock = activeLock;
        notifyPropertyChanged(BR.activeLock);
        editor.putBoolean(EXTRA_ACTIVE, activeLock).apply();
    }

    public String getLockTitle() {
        return lockTitle;
    }

    public void setLockTitle(String lockTitle) {
        this.lockTitle = lockTitle;
        notifyPropertyChanged(BR.lockTitle);
        editor.putString(EXTRA_TITLE, lockTitle).apply();
    }

    public boolean isUsePassWord() {
        return usePassWord;
    }

    public void setUsePassWord(boolean usePassWord) {
        this.usePassWord = usePassWord;
        notifyPropertyChanged(BR.usePassWord);
        editor.putBoolean(EXTRA_USE_PASSWORD, usePassWord).apply();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
        notifyPropertyChanged(BR.passWord);
        editor.putString(EXTRA_PASSWORD, passWord).apply();
    }

    public int getNumberAnswer() {
        return numberAnswer;
    }

    public void setNumberAnswer(int numberAnswer) {
        this.numberAnswer = numberAnswer;
        notifyPropertyChanged(BR.numberAnswer);
        editor.putInt(EXTRA_NUMBER_ANSWER, numberAnswer).apply();
    }
}
