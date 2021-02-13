package com.example.nf.app.base;

public interface BasePresenter<V> {
    void onCreate(V view);
    void onDestroy();
}
