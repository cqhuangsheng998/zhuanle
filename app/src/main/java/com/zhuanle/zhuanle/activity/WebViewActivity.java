package com.zhuanle.zhuanle.activity;



import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhuanle.zhuanle.R;
import com.zhuanle.zhuanle.databinding.ActivityWebviewBinding;
import com.zhuanle.zhuanle.help.AppGlobals;
import com.zhuanle.zhuanle.utils.CommonUtils;
import com.zhuanle.zhuanle.utils.StatusBar;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class WebViewActivity extends AppCompatActivity {
    private   TextView webviewText;
    private     FrameLayout framentlayout;
    private   ProgressBar webViewProgressBar;
    private   Toolbar  toolbar;
    private WebView mWebView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //启用沉浸式布局，白底黑字
        StatusBar.fitSystemBar(new WeakReference<>(this).get());
        super.onCreate(savedInstanceState);
        ActivityWebviewBinding viewDataBinding = DataBindingUtil.setContentView(new WeakReference<>(this).get(),
                R.layout.activity_webview);
        toolbar=   viewDataBinding.webviewToolbar;
        setSupportActionBar(toolbar);
        String titel = getIntent().getStringExtra("titel");
        if(!CommonUtils.isStringEmpty(titel))
            getSupportActionBar().setTitle(titel);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        framentlayout=viewDataBinding.webviewFramentlayout;
        webViewProgressBar=  viewDataBinding.webViewProgressBar;
        webviewText=  viewDataBinding.webviewText;
        String url = getIntent().getStringExtra("url");

        if (url != null && url.startsWith("http")) {
            mWebView = new WebView(AppGlobals.getApplication());
            framentlayout.addView(mWebView);
            WebSettings webSettings =   mWebView.getSettings();
            webSettings.setDomStorageEnabled(true);//主要是这句
            webSettings.setJavaScriptEnabled(true);//启用js
            webSettings.setBlockNetworkImage(false);//解决图片不显示
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setLoadsImagesAutomatically(true);
            //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });

            mWebView.loadUrl(url);

            //监听网页的加载进度    此处有内存泄漏 .ondesty必须设为null
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView webView, int position) {
                    if (position == 100) {  //必须在ui县城里，否则很久才消失哦
                        runOnUiThread(() -> {
                            webViewProgressBar.setVisibility(View.GONE);


                        });
                    }
                }
            });

        } else {
            //显示文字
            webviewText.setVisibility(View.VISIBLE);
            webViewProgressBar.setVisibility(View.GONE);
            framentlayout.setVisibility(View.GONE);
            webviewText.setText(url);
        }

    }

    @Override
    public void onDestroy() {
        try {
            if (mWebView != null) {
                mWebView.stopLoading();
                mWebView.setWebChromeClient(null);
                mWebView.setWebViewClient(null);
                mWebView.getSettings().setJavaScriptEnabled(false);
                CookieSyncManager.getInstance().stopSync();
                mWebView.removeAllViewsInLayout();
                mWebView.removeAllViews();
                framentlayout.removeView(mWebView);
                mWebView.destroy();
                mWebView = null;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            super.onDestroy();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


