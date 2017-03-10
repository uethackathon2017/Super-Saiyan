package com.supersaiyan.englock.dto;

import android.util.Log;


import com.supersaiyan.englock.api.ServiceImpl;
import com.supersaiyan.englock.model.Topic;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicDTOManager {
    private static final String TAG = "TopicDTOManager";

    public TopicDTOManager() {
    }

    public void getTopicsAsync(final OnTopicLoadListener listener) {
        ServiceImpl.getInstance().getService().getTopics().enqueue(new Callback<ArrayList<TopicDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<TopicDTO>> call, Response<ArrayList<TopicDTO>> response) {
                if (response.code() != 200) {
                    Log.d(TAG, "response.code() != 200");
                    listener.onTopicLoadFailure();
                } else {
                    listener.onTopicLoadSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TopicDTO>> call, Throwable t) {
                t.printStackTrace();
                listener.onTopicLoadFailure();
            }
        });
    }

    public interface OnTopicLoadListener {
        void onTopicLoadSuccess(ArrayList<TopicDTO> topics);

        void onTopicLoadFailure();
    }

}