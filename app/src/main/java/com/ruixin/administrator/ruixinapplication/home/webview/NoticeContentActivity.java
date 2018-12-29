package com.ruixin.administrator.ruixinapplication.home.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.utils.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rmiri.skeleton.SkeletonGroup;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 详情公告界面
 */
public class NoticeContentActivity extends Activity implements View.OnClickListener {
    WebView mWebView;

    @BindView(R.id.skeletonGroup)
    SkeletonGroup skeletonGroup;
    @BindView(R.id.ll_skeleton)
    LinearLayout llSkeleton;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String id;
    private String url;
    LinearLayout back_arrow;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_content);
        ButterKnife.bind(this);
        initView();
        initStatus();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        tvTitle.setText("详情公告");
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(this);
        Intent intent = getIntent();
        // 取其中的值
        id = intent.getStringExtra("id");
        Log.e("id", "id" + id);
        mWebView = findViewById(R.id.notice_web);
        progressBar = findViewById(R.id.progress_bar);
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();
        skeletonGroup.setAutoPlay(true);
        skeletonGroup.setShowSkeleton(true);
        skeletonGroup.startAnimation();
        // skeletonGroup.finishAnimation();
        skeletonGroup.setSkeletonListener(new SkeletonGroup.SkeletonListener() {
            @Override
            public void onStartAnimation() {

            }

            @Override
            public void onFinishAnimation() {
                llSkeleton.setVisibility(View.GONE);
                skeletonGroup.setVisibility(View.GONE);
            }
        });
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

//支持插件
        // webSettings.setPluginsEnabled(true);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebView.clearCache(true);
        mWebView.clearHistory();
//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        //  webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setAppCacheEnabled(true);
        ; //设置可以访问文件
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        url = URL.getInstance().NewsDetial_URL + "?id=" + id;
        Log.e("url", "url" + url);

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                Log.e("TAG", "progress:" + progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                    llSkeleton.setVisibility(View.GONE);
                    skeletonGroup.setVisibility(View.GONE);
                    skeletonGroup.finishAnimation();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
        });
        mWebView.loadUrl(url);

    }

    @Override
    public void onClick(View view) {
        finish();
    }

    protected void onDestroy() {

        if (mWebView != null) {
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearCache(true);
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        super.onDestroy();
    }
}
