package com.example.spacex.view.ui.main;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.spacex.R;
import com.example.spacex.data.local.pref.PreferenceHelper;
import com.example.spacex.data.model.others.Rocket;
import com.example.spacex.remote.ApiHelperInterface;
import com.example.spacex.util.DetailsTransition;
import com.example.spacex.view.adapter.FooterView;
import com.example.spacex.view.ui.rocketdetails.RocketDetailsFragment;
import com.example.spacex.view.ui.rocketlist.RocketListFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    PreferenceHelper preferenceHelper;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private Toolbar mTopToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Is new User", preferenceHelper.getIsNew().toString());
        if(savedInstanceState == null){
            RocketListFragment rocketListFragment = new RocketListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, rocketListFragment, RocketListFragment.TAG).commit();
        }

        mTopToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mTopToolbar);

        // Verify is User is firs time User
        if (preferenceHelper.getIsNew()){

            // Create Alert using Builder
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                    .setTitle("Welcome! Welcome!")
                    .setTextGravity(Gravity.CENTER_HORIZONTAL)
                    .setFooterView(new FooterView(this, preferenceHelper))
                    .setMessage("Seems this is the first time you are using our app. We just want to welcome you aboard\n\nEnjoy!")
                    .addButton("Continue Using App", -1, Color.parseColor("#FFC107"), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, (dialog, which) -> {
                        Toast.makeText(this, "Welcome and Please enjoy our app", Toast.LENGTH_LONG).show();
                        preferenceHelper.setIsNew(false);
                        dialog.dismiss();
                    });

//            findViewById(R.id.dialog_btn).setOnClickListener(view -> {
//                EditText edit = findViewById(R.id.dialog_edtxt);
//                String nickname_text = edit.getText().toString();
//
//                Toast.makeText(this, "Welcome " + nickname_text + ", Please be kind to reach out to us anytime", Toast.LENGTH_SHORT).show();
//                preferenceHelper.setIsNew(true);
//            });

            builder.show();
        }
    }

    public void show(Rocket rocket) {

        com.facebook.drawee.view.SimpleDraweeView image = findViewById(R.id.transit_img);
        RocketDetailsFragment rocketFragment = RocketDetailsFragment.forRocket(rocket);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rocketFragment.setSharedElementEnterTransition(new DetailsTransition());
            rocketFragment.setEnterTransition(new Fade());
            rocketFragment.setExitTransition(new Fade());
            rocketFragment.setSharedElementReturnTransition(new DetailsTransition());

            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("rocket")
                    .addSharedElement(image,"shared_image")
                    .add(R.id.fragment_container,
                            rocketFragment, null).commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("rocket")
                    .add(R.id.fragment_container,
                            rocketFragment, null).commit();
        }

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}