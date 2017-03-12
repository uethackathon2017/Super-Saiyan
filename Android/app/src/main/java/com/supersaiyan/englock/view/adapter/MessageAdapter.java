package com.supersaiyan.englock.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ItemChatBinding;
import com.supersaiyan.englock.model.Message;

import java.util.ArrayList;

/**
 * Created by thanh_000 on 3/12/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> {

    private ArrayList<Message> messages = new ArrayList<>();

    public MessageAdapter() {
        setHasStableIds(true);
    }

    @Override
    public MessageAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemChatBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_chat, null, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.Holder holder, int position) {
        holder.binding.setData(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return messages.get(position).hashCode();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemChatBinding binding;

        public Holder(ItemChatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
