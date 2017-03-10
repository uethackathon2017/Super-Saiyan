package com.supersaiyan.englock.api;

import com.supersaiyan.englock.dto.TopicDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET(value = "/el/api/topic/list")
    Call<ArrayList<TopicDTO>> getTopics();

}