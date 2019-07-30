package com.example.spacex.view.adapter;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

public class CustomBindingAdapter {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("imgUrl")
    public static void bindImageUrl(SimpleDraweeView draweeView, String imgUrl) {
        String new_url = imgUrl.replaceAll("^\"|\"$", "");
        Uri imgUri = Uri.parse(new_url);
        draweeView.setImageURI(imgUri);
    }

    @BindingAdapter("backgroundText")
    public static void bindTextColor(TextView view, Boolean success) {

        if (success == null){
            view.setTextColor(Color.parseColor("#ADD8E6"));
            view.setText("No Launch Status yet");
        }
        else if(success && success != null) {
            view.setTextColor(Color.parseColor("#39C16C"));
            view.setText("Successful Launch");
        }
        else {
            view.setTextColor(Color.parseColor("#FF9494"));
            view.setText("Failed Launch");
        }
    }
}