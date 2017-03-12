package com.supersaiyan.englock.api;

import com.google.gson.JsonObject;
import com.supersaiyan.englock.dto.TopicDTO;
import com.supersaiyan.englock.model.Word;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {
    @GET(value = "/el/api/topic/list")
    Call<ArrayList<TopicDTO>> getTopics();

    @POST(value = "/el/api/topic/suggestion")
    Call<TopicDTO> getSuggestionWord(@Body JsonObject request);

    @GET(value = "/el/api/oxford")
    Call<Word> getNewWord(@Query("word") String word);

}