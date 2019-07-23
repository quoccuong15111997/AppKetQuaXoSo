package com.example.api;

import com.example.model.JsonKQSX;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiKetQuaXoSo {
    @GET("/")
    Call<JsonKQSX> getKetQua(@Query(value = "rss_url") String url);
}
