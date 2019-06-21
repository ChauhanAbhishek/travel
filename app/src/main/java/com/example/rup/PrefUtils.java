package com.example.rup;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

public class PrefUtils {

    private SharedPreferences sharedPreferences;

    @Inject
    public PrefUtils(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
