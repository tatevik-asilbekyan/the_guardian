package com.example.nf.features.newsfeed.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.nf.R;
import com.example.nf.app.base.BaseActivity;
import com.example.nf.app.base.BasePresenter;
import com.example.nf.app.factory.PresenterFactory;
import com.example.nf.data.entity.Content;
import com.example.nf.features.newsfeed.contract.FeedContract;
import com.example.nf.helpers.PreferenceHelper;
import com.example.nf.helpers.UIHelper;

import java.util.List;

import static com.example.nf.features.newsfeed.contract.FeedContract.GRID;
import static com.example.nf.features.newsfeed.contract.FeedContract.VERTICAL_LIST;

public class FeedActivity extends BaseActivity implements FeedContract.View, FeedContract.OnFeedInteractionListener {

    private FeedContract.Presenter presenter;
    private FeedFragment feedFragment;
    private Toolbar toolbar;
    private boolean isShowingGrid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setIcon(isShowingGrid ? R.drawable.ic_list : R.drawable.ic_grid);
        feedFragment.showFeedAs(isShowingGrid ? GRID : VERTICAL_LIST);
        isShowingGrid = !isShowingGrid;

        return true;
    }

    @Override
    protected BasePresenter getPresenter() {
        if (presenter == null) {
            presenter = PresenterFactory.createFeedPresenter();
        }
        return presenter;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void showFeed(List<Content> content) {
        feedFragment.showFeed(content);
    }

    @Override
    public void onFeedViewScrolled() {
        presenter.onFeedViewScrolled();
    }

    @Override
    public void onViewReady() {
        presenter.onViewReady();
    }

    @Override
    public boolean isNetworkAvailable() {
        return UIHelper.isNetworkAvailable(this);
    }

    @Override
    public void onSaveItemClicked(@NonNull Content content) {
        presenter.onSaveItemClicked(content);
    }

    @Override
    public void onPinItemClicked(@NonNull Content content) {
        presenter.onPinItemClicked(content);
    }

    @Override
    public void showPinnedFeed(List<Content> content) {
        feedFragment.showPinnedFeed(content);
    }

    @Override
    public void onFeedDetailOpened() {
        toolbar.getMenu().getItem(0).setVisible(false);
    }

    @Override
    public void onFeedDetailClosed() {
        toolbar.getMenu().getItem(0).setVisible(true);
    }

    @Override
    public void showLoading() {
        feedFragment.showLoading();
    }

    @Override
    public void hideLoading() {
        feedFragment.hideLoading();
    }

    @Override
    public void showError(int res) {
        feedFragment.showError(res);
    }

    @Override
    public void dispatchUpdates(List<Content> content) {
        feedFragment.dispatchUpdates(content);
    }

    @Override
    public void saveLastContent(String content) {
        PreferenceHelper.saveLastContent(this, content);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        feedFragment = FeedFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.content_feed, feedFragment).commit();
    }
}
