package com.example.nf.app.base;

public interface BaseView<T> {
    void showLoading();
    void hideLoading();
    boolean isNetworkAvailable();
}
