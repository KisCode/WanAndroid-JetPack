package com.kiscode.wanandroid.model.datasourece;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.kiscode.wanandroid.http.RetrofitManager;
import com.kiscode.wanandroid.model.ArticleModel;
import com.kiscode.wanandroid.model.ListModel;
import com.kiscode.wanandroid.model.RetrunModel;
import com.kiscode.wanandroid.model.api.ApiService;

import io.reactivex.functions.Consumer;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2021/4/13 8:20
 **/
public class ArticleDataSource extends PageKeyedDataSource<Integer, ArticleModel> {
    private static final String TAG = "ArticleDataSource";
    private static final int PAGE_INDEX_INITAL = 0;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ArticleModel> callback) {
        Log.i(TAG, "loadInitial:" + PAGE_INDEX_INITAL);
        RetrofitManager.getInstance().create(ApiService.class)
                .getArticleList(PAGE_INDEX_INITAL)
                .subscribe(new Consumer<RetrunModel<ListModel<ArticleModel>>>() {
                    @Override
                    public void accept(RetrunModel<ListModel<ArticleModel>> listModelRetrunModel) throws Exception {
                        callback.onResult(listModelRetrunModel.getData().getDatas(), null, PAGE_INDEX_INITAL + 1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ArticleModel> callback) {
        Log.i(TAG, "loadBefore:" + params.key);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ArticleModel> callback) {
        Log.i(TAG, "loadAfter:" + params.key);
        RetrofitManager.getInstance().create(ApiService.class)
                .getArticleList(params.key)
                .subscribe(new Consumer<RetrunModel<ListModel<ArticleModel>>>() {
                    @Override
                    public void accept(RetrunModel<ListModel<ArticleModel>> listModelRetrunModel) throws Exception {
                        callback.onResult(listModelRetrunModel.getData().getDatas(), params.key + 1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}