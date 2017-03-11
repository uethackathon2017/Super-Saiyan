package com.supersaiyan.englock.view.cardswipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.supersaiyan.englock.R;

public class MiniGameLayout extends BaseCardLayout {

    public MiniGameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void findViews() {
        View.inflate(getContext(), R.layout.screen_feedback, this);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setViewsContent() {
    }

    @Override
    protected void setViewsListener() {
    }

}