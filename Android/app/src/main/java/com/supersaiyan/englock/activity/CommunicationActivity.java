package com.supersaiyan.englock.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
        openRoom("room1");
    }

    public void onRoom2Click() {
        openRoom("room2");
    }

    public void onRoom3Click() {
        openRoom("room3");
    }

    public void onRoom4Click() {
        openRoom("room4");
    }

    public void openRoom(String roomName) {
        if (binding.edtName.getText().toString().trim().equals("")) {
            Snackbar.make(binding.getRoot(), "Vui lòng nhập tên.", Snackbar.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra("data", roomName);
        intent.putExtra("name", binding.edtName.getText().toString().trim());
        startActivity(intent);
    }

}
