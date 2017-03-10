package com.supersaiyan.englock;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DataBindingAdatpers {
    @BindingAdapter("android:glideUrl")
    public static void setImageUrl(ImageView imageView, String imgUrl) {
        Log.i("He", imgUrl);
        Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
    }


}
