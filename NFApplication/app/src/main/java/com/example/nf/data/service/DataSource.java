package com.example.nf.data.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nf.data.Repository.onDataModifyListener;
import com.example.nf.data.entity.Content;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface DataSource {
    Single<List<Content>> getRemoteContent(int page, int pageSize);
    Flowable<List<Content>> getOfflineContent();
    Flowable<List<Content>> getPinedContent();
    void saveContent(@NonNull Content content, @Nullable onDataModifyListener listener);
}
