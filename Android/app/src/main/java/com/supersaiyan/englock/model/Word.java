package com.supersaiyan.englock.model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;
import com.supersaiyan.englock.api.Config;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class Word extends BaseObservable {
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
    private String image;

    public Word() {
    }

    @Override
    public String toString() {
        return "Word{" +
                "title='" + title + '\'' +
                ", def='" + def + '\'' +
                ", mean='" + mean + '\'' +
                ", trans='" + trans + '\'' +
                ", sample='" + sample + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDef() {
        return def;
    }

    public String getMean() {
        return mean;
    }

    public String getTrans() {
        return trans;
    }

    public String getSample() {
        return sample;
    }

    public String getImage() {
        return Config.BASE_URL + "/" + image;
    }

    public boolean equals(Word word) {
        return (this.title.equals(word.title) && (mean.equals(word.getMean()) && (trans.equals(word.getTrans()))));
    }
}
