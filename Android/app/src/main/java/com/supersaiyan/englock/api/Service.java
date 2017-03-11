package com.supersaiyan.englock.api;

import com.google.gson.JsonObject;
import com.supersaiyan.englock.dto.TopicDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {
    @GET(value = "/el/api/topic/list")
    Call<ArrayList<TopicDTO>> getTopics();

    @POST(value = "/el/api/topic/suggestion")
    Call<TopicDTO> getSuggestionWord(@Body JsonObject request);

}