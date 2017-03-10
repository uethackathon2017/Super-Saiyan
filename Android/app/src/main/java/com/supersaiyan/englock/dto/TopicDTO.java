package com.supersaiyan.englock.dto;

import com.google.gson.annotations.SerializedName;
import com.supersaiyan.englock.api.Config;
import com.supersaiyan.englock.model.Topic;
import com.supersaiyan.englock.model.Word;

import java.util.ArrayList;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class TopicDTO {
    @SerializedName("name")
    private String name;

    @SerializedName("country_name")
    private String countryName;

    @SerializedName("icon_url")
    private String iconUrl;

    @SerializedName("words")
    private ArrayList<Word> words;

    public TopicDTO() {
    }

    @Override
    public String toString() {
        return "Topic{" +
                "name='" + name + '\'' +
                ", countryName='" + countryName + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", words=" + words +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public Topic getTopic() {
        Topic topic = new Topic();
        topic.setTopicName(name);
        topic.setNameToShow(countryName);
        topic.setSeclected(0);
        topic.setIconUrl(Config.BASE_URL + "/" + iconUrl);
        return topic;
    }

}
