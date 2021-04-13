package com.kiscode.wanandroid.http;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2021/4/12 22:33
 */

public class RetrofitManager {
    private static final String BASEURL = "https://www.wanandroid.com";

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createHttpClient())
            .build();

    private static OkHttpClient createHttpClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addInterceptor(null)
                .build();
        return httpClient;
    }

    public static Retrofit getInstance() {
        return retrofit;
    }
}
