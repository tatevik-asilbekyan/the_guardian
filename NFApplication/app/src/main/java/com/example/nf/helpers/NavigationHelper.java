package com.example.nf.helpers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.nf.features.newsfeed.ui.FeedActivity;

public final class NavigationHelper {

    private NavigationHelper() {
    }

    public static void goToMainPage(@NonNull Context context) {
        context.startActivity(new Intent(context, FeedActivity.class));
    }

    public static Intent mainPageIntent(@NonNull Context context) {
        return new Intent(context, FeedActivity.class);
    }
}
