package com.supersaiyan.englock.view.cardswipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.supersaiyan.englock.R;

public class ChooseTopicLayout extends BaseCardLayout {
    private static final String TAG = "BaseCardLayout";

    public ChooseTopicLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void findViews() {
        View.inflate(getContext(), R.layout.screen_feedback, this);
    }


    @Override
    protected void setViewsContent() {

    }

}
