package com.mercury.sophixdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by wang.zhonghao on 2017/11/17.
 */

public class MyApplication extends Application {

    private final String TAG = "SophixStubApplication";

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, this.getClass().getSimpleName());
        context = getApplicationContext();
    }



}
