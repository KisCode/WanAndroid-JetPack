package com.kiscode.wanandroid.model.datasourece;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.kiscode.wanandroid.model.ArticleModel;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2021/4/13 8:28
 **/
public class ArticleDataSourceFactory extends DataSource.Factory<Integer, ArticleModel> {
    public MutableLiveData<ArticleDataSource> articleDataSourceLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, ArticleModel> create() {
        ArticleDataSource dataSource = new ArticleDataSource();
        articleDataSourceLiveData.postValue(dataSource);
        return dataSource;
    }
}