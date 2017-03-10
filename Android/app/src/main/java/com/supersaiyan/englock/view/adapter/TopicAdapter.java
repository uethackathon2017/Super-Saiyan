package com.supersaiyan.englock.view.adapter;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.activity.TopicActivity;
import com.supersaiyan.englock.databinding.ItemTopicBinding;
import com.supersaiyan.englock.manager.TopicManager;
import com.supersaiyan.englock.model.Topic;

import java.util.ArrayList;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.Holder> {
    private TopicActivity activity;
    private ArrayList<Topic> topics;

    public TopicAdapter(TopicActivity activity) {
        topics = TopicManager.getInstance().getTopics();
        this.activity = activity;
    }

    @Override
    public TopicAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTopicBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_topic, null, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(TopicAdapter.Holder holder, int position) {
        holder.binding.setData(topics.get(position));
        holder.binding.setActivity(activity);
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemTopicBinding binding;

        public Holder(ItemTopicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
