package com.supersaiyan.englock.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ActivityWordDetailBinding;
import com.supersaiyan.englock.model.Word;

/**
 * Created by thanh_000 on 3/11/2017.
 */

public class WordDetailActivity extends AppCompatActivity {
    private ActivityWordDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_word_detail, null, false);

        Word word = (Word) getIntent().getSerializableExtra("data");
        binding.setData(word);
        setContentView(binding.getRoot());
    }
}
