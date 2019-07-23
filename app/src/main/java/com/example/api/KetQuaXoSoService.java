package com.example.api;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.constant.Constant;
import com.example.model.JsonKQSX;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KetQuaXoSoService {
    private static KetQuaXoSoService apiService;
    private Retrofit retrofit;
    private KetQuaXoSoService(String baseUrl){
        initClient(baseUrl);
    }
    private void initClient(@NonNull String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            return;
        }
        if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        Log.e("URL ",baseUrl);
    }
    public static void init(@NonNull String baseUrl){
        if (apiService==null){
            apiService= new KetQuaXoSoService(baseUrl);
        }
    }
    public static KetQuaXoSoService getInstance(){
        return apiService;
    }
    public void getKetQua(Callback<JsonKQSX> callback,String url){
        if (retrofit!=null){
            Call<JsonKQSX> getKetQua=retrofit.create(ApiKetQuaXoSo.class).getKetQua(url);

            getKetQua.enqueue(callback);
            Log.e("URL ",retrofit.baseUrl().toString());
        }
    }
}
