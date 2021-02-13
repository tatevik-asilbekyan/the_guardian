package com.example.nf.features.newsfeed.contract;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.example.nf.app.base.BasePresenter;
import com.example.nf.app.base.BaseView;
import com.example.nf.data.entity.Content;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public interface FeedContract {

    int VERTICAL_LIST = 0;
    int HORIZONTAL_LIST = 1;
    int GRID = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({VERTICAL_LIST, HORIZONTAL_LIST, GRID})
    @interface ListType {}

    interface OnFeedItemClickListener {
        void onFeedItemClicked(@NonNull Content content);
    }

    interface OnFeedInteractionListener {
        void onViewReady();
        void onFeedViewScrolled();
        void onSaveItemClicked(@NonNull Content content);
        void onPinItemClicked(@NonNull Content content);
        void onFeedDetailOpened();
        void onFeedDetailClosed();
    }

    interface View extends BaseView<Presenter> {
        void showFeed(List<Content> content);
        void showPinnedFeed(List<Content> content);
        void dispatchUpdates(List<Content> content);
        void showError(@StringRes int res);
        void saveLastContent(String content);
    }

    interface Presenter extends BasePresenter<View> {
        void onViewReady();
        void onFeedViewScrolled();
        void onSaveItemClicked(@NonNull Content content);
        void onPinItemClicked(@NonNull Content content);
    }
}
