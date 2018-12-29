package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.usercenter.webview.QueryPwdcardWebview;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 设置密保卡界面
 */
public class SetPwdCardActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bind_pwdcard)
    Button bindPwdcard;
    @BindView(R.id.query_pwdcard)
    Button queryPwdcard;
    @BindView(R.id.unbind_pwdcard)
    Button unbindPwdcard;
   String userId="",is_card="",userToken="";
    String ui;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pwd_card);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        is_card = getIntent().getStringExtra("is_card");
        Log.e("is_card", "" + is_card);
        initStatus();
        if (userId.equals("")) {
           Toast.makeText(SetPwdCardActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SetPwdCardActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        } else {
            initView();
        }
    }

    private void initView() {
        tvTitle.setText("密保卡");
        if (is_card.equals("0")){
            bindPwdcard.setEnabled(true);
            bindPwdcard.setPressed(true);
            queryPwdcard.setVisibility(View.GONE);
            unbindPwdcard.setVisibility(View.GONE);
        }else if(is_card.equals("1")){
            bindPwdcard.setEnabled(false);
            bindPwdcard.setPressed(false);
            queryPwdcard.setVisibility(View.VISIBLE);
            unbindPwdcard.setVisibility(View.VISIBLE);
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
    @OnClick({R.id.back_arrow,R.id.bind_pwdcard, R.id.query_pwdcard, R.id.unbind_pwdcard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.bind_pwdcard:
                if (is_card.equals("1")){
                  Toast.makeText(this,"您已绑定密保卡",Toast.LENGTH_SHORT).show();
                }else{
                    prarams.put("usertoken",userToken);
                 prarams.put("usersid",userId);
                 new BindPwdCardAsyncTask().execute();
                }
                break;
            case R.id.query_pwdcard:
                Intent intent=new Intent(SetPwdCardActivity.this, QueryPwdcardWebview.class);
                intent.putExtra("userId",userId);
                intent.putExtra("is_card",is_card);
                intent.putExtra("userToken",userToken);
                startActivity(intent);
                break;
            case R.id.unbind_pwdcard:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("温馨提示")//设置对话框的标题
                        .setMessage("确定要解绑密保卡吗？")//设置对话框的内容
                        //设置对话框的按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                prarams.put("usersid",userId);
                                prarams.put("usertoken",userToken);
                                     new UnBindPwdCardAsyncTask().execute();
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                break;
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class BindPwdCardAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().PwdCard_URL, prarams);
            Log.e("绑定密保卡的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                int status = -1;
                String msg=null;
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    try {
                        JSONObject jsb=new JSONObject(s);
                        status=jsb.optInt("status");
                        is_card=jsb.optString("data");
                        msg=jsb.optString("msg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status == 1) {
                       Toast.makeText(SetPwdCardActivity.this, "绑定成功！", Toast.LENGTH_SHORT).show();
                        is_card="1";
                        bindPwdcard.setEnabled(false);
                        bindPwdcard.setPressed(false);
                        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                                Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_iscard",is_card);  //用户密保问题
                        editor.apply();
                       String url = URL.getInstance().QPwdCard_URL;
                        ui = "usersid=" + userId + "&usertoken=" + userToken;
                        AlertDialog.Builder builder = new AlertDialog.Builder(SetPwdCardActivity.this);
                        View view = getLayoutInflater().inflate(R.layout.carpwd_web, null);
                        WebView webView = (WebView) view.findViewById(R.id.envent_web1);
                        WebSettings webSettings1 = webView.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
                        webSettings1.setJavaScriptEnabled(true);
//支持插件
                        // webSettings.setPluginsEnabled(true);
//设置自适应屏幕，两者合用
                        webSettings1.setUseWideViewPort(true); //将图片调整到适合webview的大小
                        webSettings1.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
                        webSettings1.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
                        webSettings1.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
                        webSettings1.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
                        webSettings1.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
                        webSettings1.setAllowFileAccess(true); //设置可以访问文件
                        webSettings1.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
                        webSettings1.setLoadsImagesAutomatically(true); //支持自动加载图片
                        webSettings1.setDefaultTextEncodingName("utf-8");//设置编码格式
                        //webView.setWebChromeClient(new QueryPwdcardWebview.MywebChromeClient());
                        webView.postUrl(url, ui.getBytes());
                        builder.setView(view);
                        builder.show();
                       /* Intent intent=new Intent(SetPwdCardActivity.this, QueryPwdcardWebview.class);
                        intent.putExtra("userId",userId);
                        intent.putExtra("is_card",is_card);
                        intent.putExtra("userToken",userToken);
                        startActivity(intent);*/

                    } else if(status==-97||status==-99){
                        Unlogin.doLogin(SetPwdCardActivity.this);
                    }else if (status==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new BindPwdCardAsyncTask());
                    }else {
                        Toast.makeText(SetPwdCardActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(SetPwdCardActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(SetPwdCardActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class UnBindPwdCardAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().UbPwdCard_URL, prarams);
            Log.e("解绑密保卡的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                int status = -1;
                String msg = null;
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    try {
                        JSONObject jsb=new JSONObject(s);
                        status=jsb.optInt("status");
                        is_card=jsb.optString("data");
                        msg=jsb.optString("msg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status == 1) {
                       Toast.makeText(SetPwdCardActivity.this, "解绑成功！", Toast.LENGTH_SHORT).show();
                        is_card="0";
                        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_iscard",is_card);  //用户密保问题
                        editor.apply();
                        finish();

                    } else if(status==-97||status==-99){
                        Unlogin.doLogin(SetPwdCardActivity.this);
                    }else if (status==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new UnBindPwdCardAsyncTask());
                    }else {
                        Toast.makeText(SetPwdCardActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(SetPwdCardActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(SetPwdCardActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
