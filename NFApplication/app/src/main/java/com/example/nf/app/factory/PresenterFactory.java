package com.example.nf.app.factory;

import com.example.nf.features.newsfeed.contract.FeedContract;
import com.example.nf.features.newsfeed.presenter.FeedPresenter;

public final class PresenterFactory {

    private PresenterFactory() {}

    public static FeedContract.Presenter createFeedPresenter() {
        return new FeedPresenter();
    }
}
