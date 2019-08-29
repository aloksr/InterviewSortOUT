package com.example.interviewsort.activity;

import android.app.Application;

import com.example.interviewsort.R;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by alok on 18/6/19.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the AdMob app
        MobileAds.initialize(this,getString(R.string.appid));
    }
}