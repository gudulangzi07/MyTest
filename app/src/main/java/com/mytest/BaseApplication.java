package com.mytest;

import android.app.Application;

import com.mytest.utils.CrashHandler;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        CrashHandler.getInstance().init(this);
    }
}
