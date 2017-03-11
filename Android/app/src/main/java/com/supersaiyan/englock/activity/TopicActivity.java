package com.supersaiyan.englock.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ActivityTopicBinding;
import com.supersaiyan.englock.model.Topic;
import com.supersaiyan.englock.storage.DatabaseManager;
import com.supersaiyan.englock.view.adapter.TopicAdapter;
import com.supersaiyan.englock.view.customview.GridSpacingID;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class TopicActivity extends AppCompatActivity {
    private static final String TAG = "TopicActivity";

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
        getSupportActionBar().setTitle(R.string.choice_topic);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setSubtitleTextColor(Color.WHITE);
    }

    public void setUpRecyclerView() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.addItemDecoration(new GridSpacingID(2, getResources().getDimensionPixelSize(R.dimen._4sdp), true));
        binding.recyclerView.setAdapter(new TopicAdapter(this));
    }

    public void onTopicClick(final Topic topic, View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_topic_click);
        if (topic.getSeclected() == 1) {
            popupMenu.getMenu().getItem(0).setTitle(R.string.unselect);
        }
        if (topic.getCreateByUser() == 0) {
            popupMenu.getMenu().getItem(2).setVisible(false);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_choose_topic:
                        if (topic.getSeclected() == 1) {
                            topic.setSeclected(0);
                        } else {
                            topic.setSeclected(1);
                        }
                        DatabaseManager.getInstance().updateTopicSelect(topic.getTopicName(), topic.getSeclected());
                        break;
                    case R.id.action_list_word:
                        listWord(topic);
                        break;
                    case R.id.action_delete:

                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void listWord(Topic topic) {
        Intent intent = new Intent(TopicActivity.this, TopicDetailActivity.class);
        intent.putExtra("Name", topic.getTopicName());
        intent.putExtra("CountryName", topic.getTopicName());
        startActivity(intent);
    }

}
