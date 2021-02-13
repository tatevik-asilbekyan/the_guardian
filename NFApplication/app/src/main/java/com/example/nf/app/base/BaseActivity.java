package com.example.nf.app.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    protected abstract P getPresenter();

    @LayoutRes
    protected abstract int getLayoutResource();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        if (getPresenter() != null) {
            getPresenter().onCreate(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().onDestroy();
        }
        super.onDestroy();
    }
}
