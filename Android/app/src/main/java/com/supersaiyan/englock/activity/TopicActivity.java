package com.supersaiyan.englock.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ActivityTopicBinding;
import com.supersaiyan.englock.manager.TopicManager;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class TopicActivity extends AppCompatActivity {

    private ActivityTopicBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_topic, null, false);
        setContentView(binding.getRoot());
        initToolbar();

        TopicManager manager = TopicManager.getInstance();
    }

    public void initToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.choice_topic);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setSubtitleTextColor(Color.WHITE);
    }
}
