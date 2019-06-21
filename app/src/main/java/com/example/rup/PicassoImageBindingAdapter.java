package com.example.rup;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;



public class PicassoImageBindingAdapter {

    @BindingAdapter("app:imageUrl")
    public static void setImageResource(ImageView view, String url){
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        if(url!=null)
        {
            RupApplication.getInstance().getApplicationComponent().picasso()
                    .load(url)
                    .into(view);
        }


    }
}
