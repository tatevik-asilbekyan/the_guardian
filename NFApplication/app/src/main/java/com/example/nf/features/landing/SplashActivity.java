package com.example.nf.features.landing;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nf.R;
import com.example.nf.helpers.NavigationHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            NavigationHelper.goToMainPage(SplashActivity.this);
            finish();
            }, 1500);
    }
}
