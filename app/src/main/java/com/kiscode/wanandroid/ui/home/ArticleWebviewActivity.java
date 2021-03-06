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

        //??????WebSettings??????
        WebSettings webSettings = webView.getSettings();
        //??????????????????????????????Javascript????????????webview??????????????????Javascript
        webSettings.setJavaScriptEnabled(true);
        //????????????????????????????????????
        webSettings.setUseWideViewPort(true); //????????????????????????webview?????????
        webSettings.setLoadWithOverviewMode(true); // ????????????????????????

        //????????????
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);  //????????????????????????????????????false?????????WebView????????????
        webSettings.setDisplayZoomControls(false); //???????????????????????????

        //??????????????????
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //??????webview?????????
        webSettings.setAllowFileAccess(true); //????????????????????????
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //????????????JS???????????????
        webSettings.setLoadsImagesAutomatically(true); //????????????????????????
        webSettings.setDefaultTextEncodingName("utf-8");//??????????????????
    }
}