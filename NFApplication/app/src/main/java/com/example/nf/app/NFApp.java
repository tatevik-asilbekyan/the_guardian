package com.example.nf.app;

import android.app.Application;

import com.example.nf.helpers.Configurator;

public class NFApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final Configurator config = new Configurator(this);
        config.init();
    }
}
