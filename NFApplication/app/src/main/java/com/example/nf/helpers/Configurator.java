package com.example.nf.helpers;

import android.content.Context;

import androidx.lifecycle.ProcessLifecycleOwner;

import io.realm.Realm;

public class Configurator {

    private Context context;

    public Configurator(Context context) {
        this.context = context;
    }

    public void init() {
        Realm.init(context);

        ProcessLifecycleOwner.get().getLifecycle().addObserver(new BackgroundListener());
    }
}
