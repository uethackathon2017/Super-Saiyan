package com.supersaiyan.englock.view.cardswipe;

import android.content.Context;
import android.util.AttributeSet;

import com.supersaiyan.englock.R;


public class MoreAppLayout extends BaseCardLayout {


    public MoreAppLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void findViews() {
        inflate(getContext(), R.layout.layout_train, this);
    }

    @Override
    protected void setViewsContent() {
    }

    @Override
    protected void setViewsListener() {
    }

}
