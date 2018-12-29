package com.ruixin.administrator.ruixinapplication.usercenter.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ruixin.administrator.ruixinapplication.MainActivity;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.utils.ExToast;
import com.ruixin.administrator.ruixinapplication.utils.URL;

import java.util.HashMap;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 活动详情界面
 */
public class QueryPwdcardWebview extends Activity implements View.OnClickListener {
    WebView mWebView;
    TextView tv_title;
    private String url;
    private LinearLayout back_arrow;
    String ui;
    String usersid = "", userToken = "";
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_content1);
        String is_card = getIntent().getStringExtra("is_card");
        if (is_card.equals("0")) {
           Toast.makeText(QueryPwdcardWebview.this, "你尚未绑定密保卡，无法查看！", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            initView();
            initStatus();
        }
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
        tv_title = findViewById(R.id.tv_title);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(this);
        Intent intent = getIntent();
        // 取其中的值
        usersid = intent.getStringExtra("userId");
        userToken = intent.getStringExtra("userToken");
        prarams.put("usersid", usersid);
        prarams.put("usertoken", userToken);
        ui = "usersid=" + usersid + "&usertoken=" + userToken;
        tv_title.setText("密保卡");
        mWebView = findViewById(R.id.envent_web);
      //  mLoadingAndRetryManager = LoadingAndRetryManager.generate(mWebView, null);
        progressBar=findViewById(R.id.progress_bar);
      //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
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
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        url = URL.getInstance().QPwdCard_URL;
        Log.e("TAG", "url:" + url);
       // mLoadingAndRetryManager.showLoading();
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
       // mWebView.setWebChromeClient(new MywebChromeClient()); //在加载网页前加上这句就可以了
        mWebView.postUrl(url, ui.getBytes());

    }


    class MywebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            // 弹窗处理
            AlertDialog.Builder b2 = new AlertDialog.Builder(QueryPwdcardWebview.this)
                    .setTitle(R.string.app_name).setMessage(message)
                    .setPositiveButton("ok", new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    });


            b2.setCancelable(false);
            b2.create();
            b2.show();


            return true;
        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }

    public void destroyWebView() {
        if (mWebView != null) {
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            mWebView.freeMemory();
            mWebView.pauseTimers();
            mWebView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }
    }
}
