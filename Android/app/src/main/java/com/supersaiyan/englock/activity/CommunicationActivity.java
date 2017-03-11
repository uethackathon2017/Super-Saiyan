package com.supersaiyan.englock.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ActivityCommunicationBinding;

/**
 * Created by thanh_000 on 3/11/2017.
 */

public class CommunicationActivity extends AppCompatActivity {
    private ActivityCommunicationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_communication, null, false);
        binding.setActivity(this);
        setContentView(binding.getRoot());
    }

    public void onRoom1Click() {
        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra("data", "room1");
        startActivity(intent);
    }

    public void onRoom2Click() {

    }

    public void onRoom3Click() {

    }

    public void onRoom4Click() {

    }

    public void onRoom5Click() {

    }
}
