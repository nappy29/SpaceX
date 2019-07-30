package com.example.spacex.data.local.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.spacex.di.PreferenceInfo;

import javax.inject.Inject;

public class AppPreferenceHelper implements PreferenceHelper {

    private final SharedPreferences mPrefs;

    private static final String PREF_KEY_IS_NEW_USER = "PREF_KEY_IS_NEW_USER";

    @Inject
    public AppPreferenceHelper(Context context, @PreferenceInfo String prefFileName){
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void setIsNew(Boolean isNew) {

        mPrefs.edit().putBoolean(PREF_KEY_IS_NEW_USER,isNew).apply();
    }

    @Override
    public Boolean getIsNew() {
        return mPrefs.getBoolean(PREF_KEY_IS_NEW_USER, true);
    }
}
