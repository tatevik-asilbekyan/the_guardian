package com.example.nf.api;

import com.example.nf.data.entity.ContentResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/search")
    Single<ContentResponse> getContent(@Query("api-key") String key, @Query("show-fields") String fields,
                                       @Query("page") int page, @Query("pageSize") int pageSize);
}
