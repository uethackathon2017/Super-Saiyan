package com.supersaiyan.englock.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ActivityWordDetailBinding;
import com.supersaiyan.englock.model.Word;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by thanh_000 on 3/11/2017.
 */

public class WordDetailActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private ActivityWordDetailBinding binding;
    private final int SPEECH_RECOGNITION_CODE = 1;
    private Word word;

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_word_detail, null, false);

        binding.btnSave.setVisibility(
                getIntent().getBooleanExtra("hasSave", false) ? View.VISIBLE : View.GONE
        );

        word = (Word) getIntent().getSerializableExtra("data");
        binding.setData(word);
        binding.setActivity(this);
        setContentView(binding.getRoot());
        textToSpeech = new TextToSpeech(this, this);
    }

    public void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.UK);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hãy cố để nói " + word.getTitle().toLowerCase());
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void speech() {
        if (textToSpeech.isSpeaking()) {
            return;
        }
        String utteranceId = this.hashCode() + "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(word.getTitle(), TextToSpeech.QUEUE_FLUSH, null, utteranceId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    checkResult(text);
                }
                break;
            }
        }
    }

    public void checkResult(String text) {
        if (text.toLowerCase().equals(word.getTitle().toLowerCase())) {
            Snackbar.make(binding.getRoot(), "Bạn đã phát âm đúng, thật tuyệt vời!!!", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(binding.getRoot(), "Bạn vừa nói " + text + "???", Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            textToSpeech.setSpeechRate(0.7f);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "This Language is not supported");
            }
        } else {
            Log.e("error", "Initilization Failed!");
        }
    }

    public void stopTTS() {
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTTS();
    }
}
