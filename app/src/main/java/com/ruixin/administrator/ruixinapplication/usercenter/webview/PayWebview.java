package com.ruixin.administrator.ruixinapplication.usercenter.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 活动详情界面
 */
public class PayWebview extends Activity implements View.OnClickListener {
    WebView mWebView;
    TextView tv_title;
private String url;
private LinearLayout back_arrow;
    String usersid = "", money = "", payway = "",userToken="";
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    LinearLayout ll_web;
    private ProgressBar progressBar;
    String ui;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_content1);
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
        tv_title=findViewById(R.id.tv_title);
        ll_web=findViewById(R.id.ll_web);
        back_arrow=findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(this);

        Intent intent=getIntent();
        // 取其中的值
        usersid= intent.getStringExtra("usersid");
        payway=intent.getStringExtra("payway");
        money=intent.getStringExtra("money");
        userToken=intent.getStringExtra("userToken");
     //   mLoadingAndRetryManager = LoadingAndRetryManager.generate(ll_web, null);
        prarams.put("usersid", usersid);
        prarams.put("money", money);
        prarams.put("payway", payway);
        prarams.put("usertoken", userToken);
 ui="usersid="+usersid+"&"+"payway="+payway+"&"+"money="+money+"&"+"usertoken="+userToken;
        if(payway.equals("zfb")){
            tv_title.setText("支付宝支付");
        }else{
            tv_title.setText("微信支付");
        }
        mWebView=findViewById(R.id.envent_web);
        //声明WebSettings子类
        progressBar=findViewById(R.id.progress_bar);
        WebSettings webSettings = mWebView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

//支持插件
        // webSettings.setPluginsEnabled(true);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setDomStorageEnabled(true);//对H5支持
//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setBlockNetworkImage(false);//
        webSettings.setBlockNetworkLoads(false);//
//其他细节操作
       // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setAppCacheEnabled(true);
        ; //设置可以访问文件
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        if(payway.equals("zfb")){
            url= URL.getInstance().PayAlipy_URL;
        }else{
            url= URL.getInstance().PayWe_URL;
        }
new getresultAsyncTask().execute();
        Log.e("url","url"+url);
        Log.e("prarams","url"+ui);
       // mLoadingAndRetryManager.showLoading();
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                Log.e("TAG", "progress:" + progress);
                if(progress==100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
        });


       mWebView.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               view.loadUrl(url);
                return true;
            }
        });


     /* //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
       mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                Log.d("TAG", "url:"+url);
                view.postUrl(url,ui.getBytes());
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }



            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
               Toast.makeText(PayWebview.this, "网页加载完毕", Toast.LENGTH_SHORT).show();
            }

        });*/



    }

    // mWebView.loadUrl(url);

    /*获取轮播图*/
    @SuppressLint("StaticFieldLeak")
  private   class getresultAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(url, prarams);
            Log.e("轮播图返回结果result", "result=" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                Log.e("tag",""+s);
                mWebView.loadUrl("http://120.78.87.50"+s);
            }

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
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearCache(true);
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }
}
