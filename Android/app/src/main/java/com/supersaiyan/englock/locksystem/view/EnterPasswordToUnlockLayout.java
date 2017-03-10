package com.supersaiyan.englock.locksystem.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dev.thanhnamitit.studylock.R;
import com.dev.thanhnamitit.studylock.locksystem.listener.OnAnswerListener;
import com.dev.thanhnamitit.studylock.storage.PrefManager;
import com.nineoldandroids.animation.Animator;

public class EnterPasswordToUnlockLayout extends LinearLayout implements View.OnClickListener {

    private View layoutContainRing;
    private View[] vRing;
    private View[] btnToUnlock;

    private String result; // ket qua

    private OnAnswerListener onAnswerListener;
    private OnCancelClickListener onCancelClickListener;

    private StringBuffer answer = new StringBuffer(); // nguoi dung nhap

    public EnterPasswordToUnlockLayout(Context context, OnAnswerListener listener, OnCancelClickListener onCancelClickListener) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_enter_passcode_to_unlock,this);
        PrefManager prefManager =  PrefManager.getInstance();
        result = prefManager.getPassword();
        this.onAnswerListener = listener;
        this.onCancelClickListener = onCancelClickListener;
        init();
    }



    public void init() {
        layoutContainRing = findViewById(R.id.tb_contain_ring);
        setUpEnterPasswordToLockLayout();
        findViewById(R.id.tv_clear).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });

        findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClickListener.onCancel();
            }
        });
    }


    private void setUpEnterPasswordToLockLayout() {
        int[] arrBtnId = new int[]{R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4, R.id.bt_5, R.id.bt_6, R.id.bt_7, R.id.bt_8, R.id.bt_9};
        btnToUnlock = new View[arrBtnId.length];
        for (int i = 0; i < arrBtnId.length; i++) {
            btnToUnlock[i] = findViewById(arrBtnId[i]);
            btnToUnlock[i].setOnClickListener(this);
        }
        int[] arrRingId = new int[]{R.id.v_ring1, R.id.v_ring2, R.id.v_ring3, R.id.v_ring4};
        vRing = new View[arrRingId.length];

        for (int i = 0; i < arrRingId.length; i++) {
            vRing[i] = findViewById(arrRingId[i]);
        }
    }


    @Override
    public void onClick(View view) {
        try {
            Button btn = (Button) view;
            answer.append(btn.getText());
            vRing[answer.length() - 1].setBackgroundResource(R.drawable.dra_shape_circle_full);
            if (answer.length() >= result.length()) {
                setClickableForBtnToUnlock(false);
                if (answer.toString().equals(result)) {
                    onAnswerListener.onAnswerRight();
                } else {

                    YoYo.with(Techniques.Tada).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            resetData();
                            setClickableForBtnToUnlock(true);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).duration(700).playOn(layoutContainRing);
                }
            }
        } catch (Exception e) {
            Log.i("TAG", answer.toString());
            //  throw e;
        }

    }

    private void setClickableForBtnToUnlock(boolean value) {
        for (View view : btnToUnlock) {
            view.setClickable(value);
        }
    }

    private void resetData() {
        if (answer.length()==0) return;
        answer.delete(0,answer.length());
        for (View v : vRing) {
            v.setBackgroundResource(R.drawable.dra_shape_circle_stroke);
        }
    }


    interface OnCancelClickListener {
        void onCancel();
    }
}
