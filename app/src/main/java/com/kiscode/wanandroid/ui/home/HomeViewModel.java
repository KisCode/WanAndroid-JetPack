package com.kiscode.wanandroid.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.kiscode.wanandroid.model.ArticleModel;
import com.kiscode.wanandroid.model.datasourece.ArticleDataSourceFactory;

public class HomeViewModel extends ViewModel {

    private LiveData<PagedList<ArticleModel>> pagedListLiveData;
    private ArticleDataSourceFactory factory;

    public HomeViewModel() {
        factory =new ArticleDataSourceFactory();
        pagedListLiveData = new LivePagedListBuilder<>(factory, 20).build();
    }

    public LiveData<PagedList<ArticleModel>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}