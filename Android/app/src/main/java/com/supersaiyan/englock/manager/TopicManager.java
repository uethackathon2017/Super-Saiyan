package com.supersaiyan.englock.manager;

import com.supersaiyan.englock.App;
import com.supersaiyan.englock.R;
import com.supersaiyan.englock.listener.OnAddTopicListener;
import com.supersaiyan.englock.model.Topic;
import com.supersaiyan.englock.storage.DatabaseManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class TopicManager {
    private static final TopicManager INSTANCE = new TopicManager();

    public static TopicManager getInstance() {
        return INSTANCE;
    }

    private ArrayList<Topic> topics;

    public TopicManager() {
        topics = DatabaseManager.getInstance().getListTopic();
        int a = 1;
//        Topic addTopic = new Topic("");
//        addTopic.setNameToShow(App.getAppLicationContext().getString(R.string.add_topic));
//        addTopic.setIconId(R.drawable.ic_add_topic);
//        addTopic.setSeclected(2);
//        addTopic.setCreateByUser(2);
//        topics.add(addTopic);
//        Collections.sort(topics);
    }

    public boolean checkTopicExist(String topicName) {
        for (Topic topic : topics) {
            if (topic.getNameToShow().toLowerCase().equals(topicName)) return true;
        }
        return false;
    }

    public void addTopic(String topicName, OnAddTopicListener listener) {
        if (checkTopicExist(topicName.toLowerCase())) {
            listener.errorWithMessage(R.string.error_topic_exists);
        } else {
            Topic topic = new Topic(Calendar.getInstance().getTimeInMillis() + "");
            topic.setNameToShow(topicName);
            topic.setCreateByUser(1);
            DatabaseManager.getInstance().createNewTopic(topic);
            topics.add(topics.size() - 1, topic);
            listener.onSuccess();

        }
    }
    public ArrayList<Topic> getTopics() {
        return topics;
    }
}
