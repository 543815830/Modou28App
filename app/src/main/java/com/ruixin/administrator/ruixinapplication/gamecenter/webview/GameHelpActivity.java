package com.ruixin.administrator.ruixinapplication.gamecenter.webview;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.utils.URL;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 游戏帮助界面
 */
public class GameHelpActivity extends Activity implements View.OnClickListener {
    private TextView tv_title;
    private LinearLayout back_arrow;
    private String game_name;
    private String Egame_name;
    private String userId;
    private String userToken;
    WebView mWebView;
    private String url;
    private String ui;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_help);
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

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(this);
        Intent intent = getIntent();
        // 取其中的值
        userId = intent.getStringExtra("userId");
        Log.e("userId", "" + userId);
        userToken = intent.getStringExtra("userToken");
        Log.e("userToken", "" + userToken);
        game_name = intent.getStringExtra("gameName");
        Egame_name = intent.getStringExtra("EgameName");
        Log.e("geme", "" + game_name);
        tv_title.setText("" + game_name);
        mWebView = findViewById(R.id.game_help_web);
       // mLoadingAndRetryManager = LoadingAndRetryManager.generate(mWebView, null);
        progressBar=findViewById(R.id.progress_bar);
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();
        url = URL.getInstance().GameHelp_URL;
        ui = "usersid=" + userId + "&usertoken=" + userToken + "&gamename=" + Egame_name;
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //如果不设置WebViewClient，请求会跳转系统浏览器
        //mLoadingAndRetryManager.showLoading();
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
// Activity和Webview根据加载程度决定进度条的进度大小
// 当加载到100%的时候 进度条自动消失
//WebViewProgressActivity.this.setTitle("Loading...");
//WebViewProgressActivity.this.setProgress(progress * 100);
                Log.e("TAG", "progress:" + progress);
                if(progress==100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
        });
        mWebView.postUrl(url, ui.getBytes());
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
