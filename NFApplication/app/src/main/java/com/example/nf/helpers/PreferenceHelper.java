package com.example.nf.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class PreferenceHelper {

    private static final String PREF_NAME = "theguardian";
    private static final String KEY_NAME = "content_id";

    private PreferenceHelper() {
    }

    public static void saveLastContent(@NonNull Context context, @Nullable String value) {
        final SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedpreferences.edit().putString(KEY_NAME, value).apply();
    }

    @Nullable
    public static String getLastContent(@NonNull Context context) {
        final SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString(KEY_NAME, null);
    }
}