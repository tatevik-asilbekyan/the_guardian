package com.example.nf.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.google.android.material.snackbar.Snackbar.LENGTH_LONG;

public final class UIHelper {

    private UIHelper() {
    }

    public static void asyncLoadImage(@NonNull ImageView target, @Nullable String url, int radius) {
        Glide.with(target.getContext())
                .load(url)
                .transform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP))
                .into(target);
    }

    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    public static void showSnackBar(@NonNull View view, @StringRes int resId) {
        Snackbar.make(view, resId, LENGTH_LONG).show();
    }
}