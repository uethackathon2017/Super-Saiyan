package com.supersaiyan.englock.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.JsonObject;
import com.supersaiyan.englock.R;
import com.supersaiyan.englock.api.ServiceImpl;
import com.supersaiyan.englock.databinding.ActivityFindWordBinding;
import com.supersaiyan.englock.dto.TopicDTO;
import com.supersaiyan.englock.model.Word;
import com.supersaiyan.englock.view.adapter.LocationWordAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindWordByLocationActivity extends AppCompatActivity {
    private ActivityFindWordBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Đang lấy dữ liệu", "Vui lòng chờ");
        getFetchData(new OnSuggestionWordLoadListener() {
            @Override
            public void onSuccess(final TopicDTO data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        setUpRecyclerView(data.getWords());
                    }
                });
            }

            @Override
            public void onFailure() {
                finish();
            }
        });
    }


    public void initToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Đề xuất từ vựng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setSubtitleTextColor(Color.WHITE);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setUpRecyclerView(ArrayList<Word> words) {
        for (Word word : words) {
            word.setIconUrl(word.getIconUrl());
        }
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_find_word, null, false);
        setContentView(binding.getRoot());
        initToolbar();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new LocationWordAdapter(words, this));
    }

    private void getFetchData(final OnSuggestionWordLoadListener listener) {
        JsonObject request = new JsonObject();
        request.addProperty("latitude", 21.0380572);
        request.addProperty("longitude", 105.7827);

        ServiceImpl.getInstance().getService().getSuggestionWord(request).enqueue(new Callback<TopicDTO>() {
            @Override
            public void onResponse(Call<TopicDTO> call, Response<TopicDTO> response) {
                if (response.code() != 200) {
                    return;
                }

                TopicDTO data = response.body();
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(Call<TopicDTO> call, Throwable t) {
                t.printStackTrace();
                listener.onFailure();
            }
        });
    }

    public void onWordClick(View view, Word word) {
        Intent intent = new Intent(this, WordDetailActivity.class);
        intent.putExtra("data", word);
        startActivity(intent);
    }

    private interface OnSuggestionWordLoadListener {
        void onSuccess(TopicDTO data);

        void onFailure();
    }

}