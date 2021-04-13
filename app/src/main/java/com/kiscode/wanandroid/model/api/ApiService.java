package com.kiscode.wanandroid.model.api;


import com.kiscode.wanandroid.model.ArticleModel;
import com.kiscode.wanandroid.model.ListModel;
import com.kiscode.wanandroid.model.RetrunModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2021/4/12 22:24
 */

public interface ApiService {


    /**
     * 首页文章
     *
     * @param pageIndex 分页页码 从0开始
     * @return
     */
    @GET("/article/list/{pageIndex}/json")
    Observable<RetrunModel<ListModel<ArticleModel>>> getArticleList(@Path("pageIndex") int pageIndex);
}
