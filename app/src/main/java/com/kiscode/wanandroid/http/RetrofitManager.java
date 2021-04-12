package com.kiscode.wanandroid.http;


import retrofit2.Retrofit;
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
            .build();

    public static Retrofit getInstance() {
        return retrofit;
    }
}
