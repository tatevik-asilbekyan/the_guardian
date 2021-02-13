package com.example.nf.helpers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class InfiniteScrollProvider {

    private static final int THRESHOLD = 5;

    private RecyclerView recyclerView;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading = false;
    private int lastVisibleItem;
    private int totalItemCount;
    private int previousItemCount = 0;

    public void attach(RecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener) {
        this.recyclerView = recyclerView;
        this.onLoadMoreListener = onLoadMoreListener;
        setInfiniteScroll(recyclerView.getLayoutManager());
    }

    private void setInfiniteScroll(final RecyclerView.LayoutManager layoutManager) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                totalItemCount = layoutManager.getItemCount();
                if (previousItemCount > totalItemCount) {
                    previousItemCount = totalItemCount - THRESHOLD;
                }

                if (layoutManager instanceof GridLayoutManager){
                    lastVisibleItem = ((GridLayoutManager)layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof LinearLayoutManager){
                    lastVisibleItem = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager){
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int spanCount = staggeredGridLayoutManager.getSpanCount();
                    int[] ids = new int[spanCount];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(ids);
                    int max = ids[0];
                    for (int i = 1; i < ids.length; i++) {
                        if (ids[1] > max){
                            max = ids[1];
                        }
                    }
                    lastVisibleItem = max;
                }
                if (totalItemCount > THRESHOLD) {
                    if (previousItemCount <= totalItemCount && isLoading) {
                        isLoading = false;
                    }
                    if (!isLoading && (lastVisibleItem > (totalItemCount - THRESHOLD)) && totalItemCount > previousItemCount) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                        previousItemCount = totalItemCount;
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
