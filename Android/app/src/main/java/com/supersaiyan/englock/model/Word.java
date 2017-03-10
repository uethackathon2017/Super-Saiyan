package com.supersaiyan.englock.model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;
import com.supersaiyan.englock.api.Config;

import java.io.Serializable;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class Word extends BaseObservable implements Serializable {
    @SerializedName("title")
    private String title;

    @SerializedName("def")
    private String def;

    @SerializedName("mean")
    private String mean;

    @SerializedName("trans")
    private String trans;

    @SerializedName("sample")
    private String sample;

    @SerializedName("image")
    private String iconUrl;

    public Word() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = Config.BASE_URL + "/" + iconUrl;
    }

    public boolean equals(Word word) {
        return (this.title.equals(word.title) && (mean.equals(word.getMean()) && (trans.equals(word.getTrans()))));
    }
}
