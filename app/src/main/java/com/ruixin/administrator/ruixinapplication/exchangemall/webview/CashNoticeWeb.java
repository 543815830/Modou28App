package com.ruixin.administrator.ruixinapplication.exchangemall.webview;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.utils.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rmiri.skeleton.SkeletonGroup;

public class CashNoticeWeb extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.cashnotice_web)
    WebView mWebView;
    LoadingAndRetryManager mLoadingAndRetryManager;
    @BindView(R.id.ll_skeleton)
    LinearLayout llSkeleton;
    @BindView(R.id.skeletonGroup)
    SkeletonGroup skeletonGroup;
    private ProgressBar progressBar;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_notice_web);
        ButterKnife.bind(this);
        initStatus();
        initView();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    private void initView() {
        tvTitle.setText("兑换须知");
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressBar = findViewById(R.id.progress_bar);
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        skeletonGroup.setAutoPlay(true);
        skeletonGroup.setShowSkeleton(true);
        skeletonGroup.startAnimation();
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
//支持插件
        // webSettings.setPluginsEnabled(true);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        mWebView.clearCache(true);
        mWebView.clearHistory();
        // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setDomStorageEnabled(true);//对h5支持
       /* webSettings.setBlockNetworkImage(false);//
        webSettings.setBlockNetworkLoads(false);//*/
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setAppCacheEnabled(true);
        ; //设置可以访问文件
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        ; //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //  mLoadingAndRetryManager = LoadingAndRetryManager.generate(mWebView, null);
        // mLoadingAndRetryManager.showLoading();
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
// Activity和Webview根据加载程度决定进度条的进度大小
// 当加载到100%的时候 进度条自动消失
//WebViewProgressActivity.this.setTitle("Loading...");
//WebViewProgressActivity.this.setProgress(progress * 100);
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
        // 设置web视图客户端
        url = URL.getInstance().ConversionNotice_URL;
        mWebView.loadUrl(url);
    }

    public class MyWebViewClient extends WebViewClient {
        /*  @Override
          public void onPageFinished(WebView view, String url) {
              super.onPageFinished(view, url);
          }

          @Override
          public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
              super.onReceivedSslError(view, handler, error);
              handler.proceed();
          }
  */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(URL.getInstance().ConversionNotice_URL);
            return true;
        }


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
