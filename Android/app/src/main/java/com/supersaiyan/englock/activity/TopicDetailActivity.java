package com.supersaiyan.englock.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.api.ServiceImpl;
import com.supersaiyan.englock.databinding.ActivityTopicBinding;
import com.supersaiyan.englock.model.Word;
import com.supersaiyan.englock.storage.DatabaseManager;
import com.supersaiyan.englock.view.adapter.WordAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class TopicDetailActivity extends AppCompatActivity {
    private static final String TAG = "TopicDetailActivity";

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
        getSupportActionBar().setTitle(getIntent().getStringExtra("CountryName"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setSubtitleTextColor(Color.WHITE);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setUpRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new WordAdapter(DatabaseManager.getInstance().getListWordOfTopic(getIntent().getStringExtra("Name")), this));
    }

    public void onWordClick(View view, Word word, boolean hasSave) {
        Intent intent = new Intent(this, WordDetailActivity.class);
        intent.putExtra("data", word);
        intent.putExtra("hasSave", hasSave);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_word, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_word:
                AddWordDialog dialog = new AddWordDialog(this);
                dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_Dialog_Animate;
                dialog.show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class AddWordDialog extends Dialog {
        private EditText edtWord;
        private ProgressBar pgbLoading;

        public AddWordDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_add_word);
            getWindow().setLayout(-1, -2);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            initializeComponents();
        }

        void initializeComponents() {
            edtWord = (EditText) findViewById(R.id.edt_word);
            pgbLoading = (ProgressBar) findViewById(R.id.pgb_loading);

            findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String word = edtWord.getText().toString();
                    if (word.isEmpty()) {
                        Toast.makeText(getContext(), "Vui lòng nhập từ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    pgbLoading.setVisibility(View.VISIBLE);

                    getNewWord(word, new OnNewWordLoadListener() {
                        @Override
                        public void onNewWordLoadSuccess(Word word) {
                            Log.d(TAG, word.toString());
                            pgbLoading.setVisibility(View.GONE);
                            dismiss();
                            onWordClick(null, word, true);
                        }

                        @Override
                        public void onNewWorLoadFailure() {
                            Toast.makeText(getContext(), "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                            pgbLoading.setVisibility(View.GONE);
                        }
                    });
                }
            });
        }

        private void getNewWord(String word, final OnNewWordLoadListener listener) {
            ServiceImpl.getInstance().getService().getNewWord(word).enqueue(new Callback<Word>() {
                @Override
                public void onResponse(Call<Word> call, Response<Word> response) {
                    if (response.code() != 200) {
                        listener.onNewWorLoadFailure();
                    } else {
                        listener.onNewWordLoadSuccess(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Word> call, Throwable t) {
                    t.printStackTrace();
                    listener.onNewWorLoadFailure();
                }
            });
        }
    }

    private interface OnNewWordLoadListener {
        void onNewWordLoadSuccess(Word word);

        void onNewWorLoadFailure();
    }

}