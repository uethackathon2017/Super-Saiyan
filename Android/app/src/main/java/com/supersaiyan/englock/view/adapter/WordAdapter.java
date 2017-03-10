package com.supersaiyan.englock.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.activity.TopicDetailActivity;
import com.supersaiyan.englock.databinding.ItemWordBinding;
import com.supersaiyan.englock.model.Word;

import java.util.ArrayList;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.Holder> {
    private ArrayList<Word> words = new ArrayList<>();
    private TopicDetailActivity activity;
    public WordAdapter(ArrayList<Word> words, TopicDetailActivity activity) {
        this.words = words;
        this.activity = activity;
    }

    @Override
    public WordAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemWordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_word, null, false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(WordAdapter.Holder holder, int position) {
        holder.binding.setData(words.get(position));
        holder.binding.setActivity(activity);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemWordBinding binding;

        public Holder(ItemWordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
