package com.example.nf.features.newsfeed.presenter;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.nf.R;
import com.example.nf.app.base.BaseMVPPresenter;
import com.example.nf.data.Repository;
import com.example.nf.data.entity.Content;
import com.example.nf.features.newsfeed.contract.FeedContract;
import com.example.nf.helpers.ContentDiffHelper;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public final class FeedPresenter extends BaseMVPPresenter<FeedContract.View> implements FeedContract.Presenter {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int NEW_ITEMS_PAGE_SIZE = 5;

    private static final int TASK_DELAY_MILLIS = 30 * 1000;

    private Repository repository;
    private int currentPage = 1;
    private String lastItemId;

    @Override
    public void onCreate(FeedContract.View view) {
        super.onCreate(view);

        repository = new Repository();

        startCheckingNewItemsDelayed();
    }

    @Override
    public void onViewReady() {
        getPinnedFeed();

        if (view.isNetworkAvailable()) {
            getOnlineContent();
        } else {
            getOfflineContent();
        }
    }

    @Override
    public void onFeedViewScrolled() {
        if (view.isNetworkAvailable()) {
            getOnlineContent();
        } else {
            view.showError(R.string.no_internet);
        }
    }

    @Override
    public void onSaveItemClicked(@NonNull Content content) {
        repository.saveContent(content, () -> content.saved = true);
    }

    @Override
    public void onPinItemClicked(@NonNull Content content) {
        repository.saveContent(content, () -> content.pinned = true);
    }

    private void getOnlineContent() {
        disposable.add(getFeed(currentPage, DEFAULT_PAGE_SIZE)
                .doOnSubscribe(disposable -> view.showLoading())
                .subscribe(contents -> {
                    if (currentPage == 1) {
                        lastItemId = contents.get(0).id;
                        view.saveLastContent(lastItemId);
                    }
                    ++currentPage;
                    view.showFeed(contents);
                    view.hideLoading();
                }, throwable -> {
                }));
    }

    private void getOfflineContent() {
        disposable.add(repository.getOfflineContent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contents -> view.showFeed(contents)));
    }

    private void getPinnedFeed() {
        disposable.add(repository.getPinedContent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contents -> {
                    if (contents.size() > 0) {
                        view.showPinnedFeed(contents);
                    }
                }));
    }

    private Single<List<Content>> getFeed(int page, int pageSize) {
        return repository.getRemoteContent(page, pageSize)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void startCheckingNewItemsDelayed() {
        new Handler().postDelayed(() -> {
            disposable.add(getFeed(1, NEW_ITEMS_PAGE_SIZE)
                    .subscribe((contents, throwable) -> {
                        if (contents.size() > 0) {
                            List<Content> result = ContentDiffHelper.checkIfNewItems(contents, lastItemId);
                            if (result != null) {
                                lastItemId = result.get(0).id;
                                view.saveLastContent(lastItemId);
                                view.dispatchUpdates(result);
                            }
                        }
                        startCheckingNewItemsDelayed();
                    }));
        }, TASK_DELAY_MILLIS);
    }
}
