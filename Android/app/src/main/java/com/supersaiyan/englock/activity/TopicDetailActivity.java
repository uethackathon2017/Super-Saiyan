package com.supersaiyan.englock.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ActivityTopicBinding;
import com.supersaiyan.englock.model.Word;
import com.supersaiyan.englock.storage.DatabaseManager;
import com.supersaiyan.englock.view.adapter.WordAdapter;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class TopicDetailActivity extends AppCompatActivity {

    private ActivityTopicBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_topic, null, false);
        setContentView(binding.getRoot());
        initToolbar();
        setUpRecyclerView();
    }

    public void initToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("CountryName"));
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

    public void setUpRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new WordAdapter(DatabaseManager.getInstance().getListWordOfTopic(getIntent().getStringExtra("Name")), this));
    }

    public void onWordClick(View view, Word word) {
        Intent intent = new Intent(this, WordDetailActivity.class);
        intent.putExtra("data", word);
        startActivity(intent);
    }
}
