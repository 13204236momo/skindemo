package com.example.skindemo;

import android.app.Application;

import com.example.skin_library.SkinManager;

public class SkinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
