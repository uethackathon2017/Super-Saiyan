package com.supersaiyan.englock.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public boolean actived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_main, null, false);
        binding.setActived(actived);
        binding.getRoot();
        setContentView(binding.getRoot());
    }

    public void activeClick() {

    }
}
