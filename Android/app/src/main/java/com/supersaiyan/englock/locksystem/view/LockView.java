package com.supersaiyan.englock.locksystem.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.supersaiyan.englock.R;
import com.supersaiyan.englock.locksystem.listener.OnAnswerListener;
import com.supersaiyan.englock.model.UserConfig;


import java.util.HashMap;
import java.util.Locale;

public class LockView extends FrameLayout implements OnAnswerListener, EnterPasswordToUnlockLayout.OnCancelClickListener, TextToSpeech.OnInitListener, AnswerToUnlockLayout.OnQuestionClickListener {

    public static final int MODE_PREVIEW_BACKGROUND = 1;
    public static final int MODE_LOCK_SCREEN = 2;

    private ViewPager viewPager;
    private ImageView imgBackground;
    private TextToSpeech textToSpeech;
    private OnUnlockListener onUnlockListener;
    private int mode;

    private int numberPagers;

    private UserConfig userConfig = UserConfig.getInstance();

    public LockView(Context context, int backgroundIndex, int mode, OnUnlockListener onUnlockListener) {
        super(context);
        this.mode = mode;
        this.onUnlockListener = onUnlockListener;
        LayoutInflater.from(context).inflate(R.layout.layout_lock_parent, this);
        textToSpeech = new TextToSpeech(getContext(), this);
        setBackground(backgroundIndex);
        if (userConfig.isUsePassWord()) {
            numberPagers = 2;
        } else {
            numberPagers = 1;
        }
        setUpViewPager();
    }


    public void setBackground(int backgroundIndex) {

        imgBackground = (ImageView) findViewById(R.id.img_bg);
        Glide.with(getContext()).load(R.drawable.bkg_03)
                .centerCrop().into(imgBackground);
    }

    private void setUpViewPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyViewPagerAdapter());
        viewPager.setCurrentItem(numberPagers - 1, false);
    }

    @Override
    public void onAnswerRight() {
        unlock();
    }

    @Override
    public void onCancel() {
        viewPager.setCurrentItem(1);
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
    public void onClickQuestion(String text) {
        if (textToSpeech.isSpeaking()) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    public void unlock() {
        if (mode == MODE_PREVIEW_BACKGROUND) {
            onUnlockListener.onUnlock();
            return;
        }
        stopTTS();
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onUnlockListener.onUnlock();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.layout_main).startAnimation(alphaAnimation);
    }

    public void reset() {
        setUpViewPager();
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (getCount() == 1) {
                position = 1;
            }
            View view = null;
            switch (position) {
                case 0:
                    view = new EnterPasswordToUnlockLayout(getContext(), LockView.this, LockView.this);
                    break;
                case 1:
                    view = new AnswerToUnlockLayout(getContext(), LockView.this, LockView.this);
                    break;
            }
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return numberPagers;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    public interface OnUnlockListener {
        void onUnlock();
    }
}
