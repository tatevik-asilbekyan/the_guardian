package com.example.nf.features.newsfeed.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.transition.TransitionManager;

import com.example.nf.R;
import com.example.nf.data.entity.Content;
import com.example.nf.features.newsfeed.adapters.FeedAdapter;
import com.example.nf.features.newsfeed.adapters.PinAdapter;
import com.example.nf.features.newsfeed.contract.FeedContract;
import com.example.nf.features.newsfeed.contract.FeedContract.OnFeedInteractionListener;
import com.example.nf.helpers.InfiniteScrollProvider;
import com.example.nf.helpers.UIHelper;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;
import static androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL;
import static com.example.nf.features.newsfeed.contract.FeedContract.GRID;
import static com.example.nf.features.newsfeed.contract.FeedContract.HORIZONTAL_LIST;
import static com.example.nf.features.newsfeed.contract.FeedContract.VERTICAL_LIST;

public class FeedFragment extends Fragment implements FeedContract.OnFeedItemClickListener {

    private static final int SPAN_COUNT = 2;

    private OnFeedInteractionListener listener;
    private RecyclerView feedRV;
    private RecyclerView pinRV;
    private ProgressBar loading;
    private ConstraintLayout feedRoot;
    private LinearLayout empty;
    private FeedAdapter feedAdapter;
    private PinAdapter pinAdapter;

    public FeedFragment() {
    }

   static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFeedInteractionListener) {
            listener = (OnFeedInteractionListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pinRV = view.findViewById(R.id.pinRV);
        feedRV = view.findViewById(R.id.feedRV);
        feedRoot = view.findViewById(R.id.feedRoot);
        loading = view.findViewById(R.id.loading);
        empty = view.findViewById(R.id.empty);

        pinAdapter = new PinAdapter(this);
        feedAdapter = new FeedAdapter(this);

        pinRV.setLayoutManager(createLayoutManager(HORIZONTAL_LIST));
        pinRV.setAdapter(pinAdapter);

        feedRV.setLayoutManager(createLayoutManager(VERTICAL_LIST));
        feedRV.setAdapter(feedAdapter);

        InfiniteScrollProvider provider = new InfiniteScrollProvider();
        provider.attach(feedRV, () -> listener.onFeedViewScrolled());

        listener.onViewReady();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onFeedItemClicked(@NonNull Content content) {
        FeedItemDetailFragment.newInstance(content).show(getChildFragmentManager(), "FeedItemDetail");
        listener.onFeedDetailOpened();
    }

    void showFeed(List<Content> data) {
        feedAdapter.addMoreDataSource(data);
        empty.setVisibility(feedAdapter.getItemCount() == 0 ? VISIBLE : GONE);
    }

    void showPinnedFeed(List<Content> data) {
        if (pinRV.getVisibility() == GONE) {
            pinRV.setVisibility(VISIBLE);
        }
        pinAdapter.addDataSource(data);
    }

    void showFeedAs(@FeedContract.ListType int type) {
        TransitionManager.beginDelayedTransition(feedRoot);
        feedRV.setLayoutManager(createLayoutManager(type));

        InfiniteScrollProvider provider = new InfiniteScrollProvider();
        provider.attach(feedRV, () -> listener.onFeedViewScrolled());
    }

    void showLoading() {
        TransitionManager.beginDelayedTransition(feedRoot);
        loading.setVisibility(VISIBLE);
    }

    void hideLoading() {
        TransitionManager.beginDelayedTransition(feedRoot);
        loading.setVisibility(GONE);
    }

    void showError(int res) {
        UIHelper.showSnackBar(feedRoot, res);
    }

    void dispatchUpdates(List<Content> data) {
        feedAdapter.addDataSource(data);
    }

    private RecyclerView.LayoutManager createLayoutManager(@FeedContract.ListType int type) {
        RecyclerView.LayoutManager manager = null;
        switch (type) {
            case VERTICAL_LIST:
                manager = new LinearLayoutManager(getContext());
                break;
            case HORIZONTAL_LIST:
                manager = new LinearLayoutManager(getContext(), HORIZONTAL, false);
                break;
            case GRID:
                manager = new StaggeredGridLayoutManager(SPAN_COUNT, VERTICAL);
                break;
        }

        return manager;
    }
}
