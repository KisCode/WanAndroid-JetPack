package com.kiscode.wanandroid.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.core.widget.ContentLoadingProgressBar;

import com.kiscode.wanandroid.R;
import com.kiscode.wanandroid.model.ArticleModel;

public class ArticleWebviewActivity extends AppCompatActivity {

    private static final String KEY_ARTICLE = "ARTICLE";
    private boolean isFavorite;
    private WebView webView;
    private ContentLoadingProgressBar pbar;
    private Toolbar toolbar;
    private ArticleModel articleModel;

    public static void start(Context context, ArticleModel articleModel) {
        Intent intent = new Intent(context, ArticleWebviewActivity.class);
        intent.putExtra(KEY_ARTICLE, articleModel);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_webview);
        initView();
        initData();
        initWebView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
        webView = null;
    }

    private void initData() {
        articleModel = (ArticleModel) getIntent().getSerializableExtra(KEY_ARTICLE);

        getSupportActionBar().setTitle(articleModel.getTitle());
    }

    private void initView() {
        pbar = findViewById(R.id.pabr_article);
        webView = findViewById(R.id.webview_article);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_favorite:
                Log.i("onOptionsItemSelected", "action_favorite");
                isFavorite = !isFavorite;
                setMenuItemCheck(isFavorite, item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setMenuItemCheck(boolean isCheck, MenuItem menuItem) {
        menuItem.setChecked(isCheck);
        MenuItemCompat.setIconTintList(
                menuItem, ColorStateList.valueOf(isCheck ? ContextCompat.getColor(this, R.color.red) : ContextCompat.getColor(this, R.color.white)));
    }

    private void initWebView() {
        Log.i("url", articleModel.getLink());
        webView.loadUrl(articleModel.getLink());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("urlShould", url);
                if (!url.startsWith("http")) {
                    return true;
                }

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbar.setVisibility(View.GONE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbar.setProgress(newProgress);
            }

        });

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);  //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }
}