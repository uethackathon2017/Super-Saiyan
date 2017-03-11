package com.supersaiyan.englock.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.activity.FindWordByLocationActivity;
import com.supersaiyan.englock.databinding.ItemLocalWordBinding;
import com.supersaiyan.englock.model.Word;

import java.util.ArrayList;

/**
 * Created by thanh_000 on 3/11/2017.
 */

public class LocationWordAdapter extends RecyclerView.Adapter<LocationWordAdapter.Holder> {
    private ArrayList<Word> words;
    private FindWordByLocationActivity activity;

    public LocationWordAdapter(ArrayList<Word> words, FindWordByLocationActivity activity) {
        this.words = words;
        this.activity = activity;
    }

    @Override
    public LocationWordAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLocalWordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_local_word, null, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(LocationWordAdapter.Holder holder, int position) {
        holder.binding.setData(words.get(position));
        holder.binding.setActivity(activity);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemLocalWordBinding binding;

        public Holder(ItemLocalWordBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }


}
