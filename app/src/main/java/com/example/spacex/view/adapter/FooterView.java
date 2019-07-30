package com.example.spacex.view.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.views.CFPushButton;
import com.example.spacex.R;
import com.example.spacex.data.local.pref.PreferenceHelper;

/**
 * Created by rahul on 11/07/17.
 */

public class FooterView extends RelativeLayout implements View.OnClickListener {

    CFPushButton tweet_btn;
    EditText editText;


    PreferenceHelper preferenceHelper;

    public FooterView(Context context, PreferenceHelper preferenceHelper) {
        this(context, null, 0);

        this.preferenceHelper = preferenceHelper;
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void setupSubviews() {
        editText = findViewById(R.id.dialog_edtxt);
        tweet_btn = findViewById(R.id.dialog_btn);
        tweet_btn.setOnClickListener(this);
    }

    private void init() {
        inflate(getContext(), R.layout.dialog_footer_view, this);
        setupSubviews();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_btn:{
                String nickname_text = editText.getText().toString();
                Toast.makeText(getContext(), "Welcome " + nickname_text + ", Please Enjoy!", Toast.LENGTH_SHORT).show();
                preferenceHelper.setIsNew(false);
                break;
            }
        }
    }

}