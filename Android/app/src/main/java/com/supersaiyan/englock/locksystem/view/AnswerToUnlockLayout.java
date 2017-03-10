package com.supersaiyan.englock.locksystem.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.LayoutAnswerToUnlockBinding;
import com.supersaiyan.englock.locksystem.listener.OnAnswerListener;
import com.supersaiyan.englock.model.UserConfig;
import com.supersaiyan.englock.model.Word;
import com.supersaiyan.englock.storage.DatabaseManager;
import com.supersaiyan.englock.storage.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class AnswerToUnlockLayout extends FrameLayout implements View.OnClickListener {

    private static final int ANIMATION_DURATION = 500;
    private static final Random RD = new Random();

    private TextView tvQuestion, tvTrans, tvTopicName;
    private Button answer1, answer2;
    private View viewContainQuestion;
    private OnAnswerListener answerListener;
    private OnQuestionClickListener questionClickListener;
    private ArrayList<Word> words;
    private String topicName;
    private int rightAnswerIndex;

    private UserConfig userConfig = UserConfig.getInstance();

    private LayoutAnswerToUnlockBinding binding;

    public AnswerToUnlockLayout(Context context, OnAnswerListener answerListener, OnQuestionClickListener questionClickListener) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_answer_to_unlock, null, false);
        addView(binding.getRoot(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        this.answerListener = answerListener;
        this.questionClickListener = questionClickListener;
        init();
    }

    public void init() {
        pickWords();
        setUpAnserToUnLockLayout();
    }

    private void pickWords() {
        HashMap<String, ArrayList<Word>> result = DatabaseManager.getInstance().getWordToLockScreen();
        Set<String> keySet = result.keySet();
        topicName = keySet.toArray()[0].toString();
        words = result.get(topicName);
    }

    private void setUpAnserToUnLockLayout() {
        answer1 = (Button) findViewById(R.id.tv_answer1);
        answer1.setOnClickListener(this);
        answer2 = (Button) findViewById(R.id.tv_answer2);
        answer2.setOnClickListener(this);
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        tvQuestion.setOnClickListener(this);
        tvTrans = (TextView) findViewById(R.id.tv_transliteration);
        viewContainQuestion = findViewById(R.id.view_contain_question);
        tvTopicName = (TextView) findViewById(R.id.tv_topic_name);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(userConfig.getLockTitle());

        setDataForView();


    }

    private void setDataForView() {
        tvQuestion.setText(words.get(rightAnswerIndex).getTitle());
        tvTrans.setText(words.get(rightAnswerIndex).getTrans());
        answer1.setText(words.get(0).getMean());
        answer2.setText(words.get(1).getMean());
        tvTopicName.setText(topicName);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_answer1:
                checkAnswer(0);
                break;
            case R.id.tv_answer2:
                checkAnswer(1);
                break;
            case R.id.tv_question:
                questionClickListener.onClickQuestion(tvQuestion.getText().toString().trim());
                break;
        }
    }

    public void setClickAbleForAnswerBtn(boolean value) {
        answer1.setClickable(value);
        answer2.setClickable(value);
    }

    public void checkAnswer(final int answer) {
        setClickAbleForAnswerBtn(false);
        if (answer == rightAnswerIndex) {

            answerListener.onAnswerRight();
        } else {
            pickWords();
            YoYo.with(Techniques.Swing).duration(ANIMATION_DURATION * 2).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    changeQuestion();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).playOn(viewContainQuestion);
        }
    }

    private void changeQuestion() {
        YoYo.with(Techniques.FadeOutUp).duration(ANIMATION_DURATION).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setDataForView();
                YoYo.with(Techniques.FadeInDown).duration(ANIMATION_DURATION).playOn(tvQuestion);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).playOn(tvQuestion);

        if (!tvTrans.getText().toString().isEmpty()) {
            YoYo.with(Techniques.ZoomOut).duration(ANIMATION_DURATION).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    YoYo.with(Techniques.ZoomIn).duration(ANIMATION_DURATION).playOn(tvTrans);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).playOn(tvTrans);
        }
        YoYo.with(Techniques.FadeOutLeft).duration(ANIMATION_DURATION).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                YoYo.with(Techniques.FadeInLeft).duration(ANIMATION_DURATION).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        answer1.setClickable(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(answer1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).playOn(answer1);

        YoYo.with(Techniques.FadeOutRight).duration(ANIMATION_DURATION).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                YoYo.with(Techniques.FadeInRight).duration(ANIMATION_DURATION).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        answer2.setClickable(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(answer2);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).playOn(answer2);

        YoYo.with(Techniques.FadeOutDown).duration(ANIMATION_DURATION).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                YoYo.with(Techniques.FadeInUp).duration(ANIMATION_DURATION).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(tvTopicName);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).playOn(tvTopicName);
    }


    public interface OnQuestionClickListener {
        void onClickQuestion(String text);
    }

}
