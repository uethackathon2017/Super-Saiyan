package com.supersaiyan.englock.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.supersaiyan.englock.BR;
import com.supersaiyan.englock.R;
import com.supersaiyan.englock.api.Config;

import java.io.Serializable;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class Topic extends BaseObservable implements Comparable {
    private String iconUrl;
    private String topicName;
    private String nameToShow;
    private int createByUser;

    @Bindable
    private int state = 0;

    @Bindable
    private int seclected;

    public Topic() {
    }

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getCreateByUser() {
        return createByUser;
    }

    public void setCreateByUser(int createByUser) {
        this.createByUser = createByUser;
    }

    public int getSeclected() {
        return seclected;
    }

    public void setSeclected(int seclected) {
        this.seclected = seclected;
        notifyPropertyChanged(BR.seclected);
    }

    public void changeSelectState() {
        if (seclected == 0) {
            seclected = 1;
        } else {
            seclected = 0;
        }
        notifyPropertyChanged(BR.seclected);
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNameToShow() {
        return nameToShow;
    }

    public void setNameToShow(String nameToShow) {
        this.nameToShow = nameToShow;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyPropertyChanged(BR.state);
    }

    @Override
    public int compareTo(Object o) {
        return createByUser - ((Topic) o).createByUser;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Topic) obj).topicName.equals(topicName);
    }
}