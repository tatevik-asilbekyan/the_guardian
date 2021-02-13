package com.example.nf.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nf.api.ApiService;
import com.example.nf.data.entity.Content;
import com.example.nf.data.entity.ContentResponse;
import com.example.nf.data.service.DataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Repository implements DataSource {

    public interface onDataModifyListener{
        void onModify();
    }

    private static final String BASE_URL = "https://content.guardianapis.com/";
    private static final String API_KEY = "test";
    private static final String FIELDS = "thumbnail,body";
    private static final String RESULT_OK = "ok";

    private ApiService service;
    private Realm realm;

    public Repository() {
        init();
    }

    private void init() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        service = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(logging).build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);

        realm = Realm.getDefaultInstance();
    }

    @Override
    public Single<List<Content>> getRemoteContent(int page, int pageSize) {
        return service.getContent(API_KEY, FIELDS, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map((Function<ContentResponse, List<Content>>) contentResponse -> contentResponse.response.status.contentEquals(RESULT_OK) ?
                        contentResponse.response.results : Collections.emptyList())
                .onErrorReturn(throwable -> Collections.emptyList());
    }

    @Override
    public Flowable<List<Content>> getOfflineContent() {
        return realm.where(Content.class)
                .equalTo("saved", true)
                .findAllAsync()
                .asFlowable()
                .filter(RealmResults::isLoaded)
                .map(contents -> contents);
    }

    @Override
    public Flowable<List<Content>> getPinedContent() {
        return realm.where(Content.class)
                .equalTo("pinned", true)
                .findAllAsync()
                .asFlowable()
                .filter(RealmResults::isLoaded)
                .map(contents -> contents);
    }

    @Override
    public void saveContent(@NonNull Content content, @Nullable onDataModifyListener listener) {
        realm.executeTransaction(realm -> {
            if (listener != null) {
                listener.onModify();
            }
            realm.insertOrUpdate(content);
        });
    }
}
