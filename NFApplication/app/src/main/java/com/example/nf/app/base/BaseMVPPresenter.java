package com.example.nf.app.base;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseMVPPresenter<V extends BaseView> implements BasePresenter<V> {

    protected V view;
    protected CompositeDisposable disposable;

    @Override
    public void onCreate(V view) {
        this.view = view;
        disposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        disposable.clear();
    }
}