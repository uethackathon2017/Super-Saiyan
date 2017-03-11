package com.supersaiyan.englock.util;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;


public class AnimUtil {

	public static void postAnimation(final View childLayout, int delay, final int duration) {
		int visibility = childLayout.getVisibility();
		if (visibility != View.VISIBLE) {
			return;
		}
		childLayout.setVisibility(View.INVISIBLE);
		childLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				childLayout.setVisibility(View.VISIBLE);
				AnimationSet animationSet = new AnimationSet(true);
				animationSet.setDuration(duration);
				animationSet.setInterpolator(new OvershootInterpolator(0.8f));
				int pivotXType = Animation.RELATIVE_TO_SELF;
				animationSet.addAnimation(new TranslateAnimation(pivotXType, -1, pivotXType, 0, pivotXType, 0, pivotXType, 0));
				animationSet.addAnimation(new AlphaAnimation(0, 1));
				childLayout.startAnimation(animationSet);
			}
		}, delay);
	}

	public static void postAnimationBottom(final View childLayout, int delay, final int duration) {
		int visibility = childLayout.getVisibility();
		if (visibility != View.VISIBLE) {
			return;
		}
		childLayout.setVisibility(View.INVISIBLE);
		childLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				childLayout.setVisibility(View.VISIBLE);
				AnimationSet animationSet = new AnimationSet(true);
				animationSet.setDuration(duration);
				animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
				int pivotXType = Animation.RELATIVE_TO_SELF;
				animationSet.addAnimation(new TranslateAnimation(pivotXType, 0, pivotXType, 0, pivotXType, 1, pivotXType, 0));
				animationSet.addAnimation(new AlphaAnimation(0, 1));
				childLayout.startAnimation(animationSet);
			}
		}, delay);
	}
}
