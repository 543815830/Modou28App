package com.ruixin.administrator.ruixinapplication.exchangemall.fragment;

import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.utils.URL;

/**
 * Created by 李丽 on 2018/6/29.
 */

public class IntroduceProductFM extends BaseFragment {
    private  View view;
    WebView mWebView;
    int position;
    String id;
    String userId;
    String userToken;
    private String url;
    private ProgressBar progressBar;
    @Override
    protected View initView() {
        if(view==null){
            position= Integer.parseInt( (String)getArguments().get("poision")) ;
            id= (String)getArguments().get("id");
            userId=  (String)getArguments().get("userId") ;
            userToken=(String)getArguments().get("usertoken");
            view=View.inflate(mContext, R.layout.fm_introduce_product,null);
            mWebView=view.findViewById(R.id.product_web);
            progressBar=view.findViewById(R.id.progress_bar);
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
            if(position==2){
                url=URL.getInstance().ConversionNotice_URL;
                mWebView.loadUrl(url);
            }else if(position==0){
                url=URL.getInstance().IntroducePrize_URL;
               String ui = "usersid=" + userId + "&usertoken=" + userToken+"&id="+id;
                Log.e("tag", "" + ui);
                mWebView.postUrl(url,ui.getBytes());
            }else if(position==1){
                url=URL.getInstance().PrizeSize_URL;
               String ui = "usersid=" + userId + "&usertoken=" + userToken+"&id="+id;
                Log.e("tag", "" + ui);
                mWebView.postUrl(url,ui.getBytes());
            }

        }
        return view;
    }
}
