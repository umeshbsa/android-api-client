package com.app.apiclient;

import android.app.Application;

public class App extends Application {

    private static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static App getInstance() {
        return INSTANCE;
    }
}
