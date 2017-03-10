package com.supersaiyan.englock.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ActivityMainBinding;

/**
 * Created by dinht_000 on 3/10/2017.
 */

public class TestActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public boolean actived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
    }

    public void activeClick() {

    }
}
