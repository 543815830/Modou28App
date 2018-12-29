package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MailContent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 信箱内容界面
 */
public class SmsContentActivity extends Activity implements View.OnClickListener {
    TextView tv_title;//标题
    LinearLayout back_arrow;//返回
    @BindView(R.id.send_user)
    TextView sendUser;
    @BindView(R.id.send_time)
    TextView sendTime;
    @BindView(R.id.sms_content)
    TextView smsContent;
    String usersid, smsid, userToken;
    @BindView(R.id.ll_sms_content)
    LinearLayout llSmsContent;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_content);
        ButterKnife.bind(this);
        usersid = getIntent().getStringExtra("usersid");
        smsid = getIntent().getStringExtra("Smsid");
        userToken = getIntent().getStringExtra("userToken");
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
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("信箱内容");
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(this);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(llSmsContent, null);
        prarams.put("usersid", usersid);
        prarams.put("usertoken", userToken);
        new SmsAsyncTask().execute();
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @SuppressLint("StaticFieldLeak")
    private class SmsAsyncTask extends AsyncTask<String, Integer, String> {
        String url = URL.getInstance().MailContent_URL + "&id=" + smsid;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(url, prarams);
            Log.e("信箱详情的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    MailContent mailContent = gson.fromJson(s, MailContent.class);
                    if (mailContent.getstatus() == 1) {
                        Log.e("信箱内容的数据返回", "" + s);
                        sendUser.setText(mailContent.getData().getUsersid());
                        sendTime.setText(mailContent.getData().getTime());
                        smsContent.setText("        " + mailContent.getData().getMag());
                        mLoadingAndRetryManager.showContent();
                    } else if (mailContent.getstatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new SmsAsyncTask());
                    }else if (mailContent.getstatus()==-99||mailContent.getstatus()==-97) {
                        /*抗攻击*/
                        Unlogin.doLogin(SmsContentActivity.this);
                    }else{
                        mLoadingAndRetryManager.showContent();
                        Toast.makeText(SmsContentActivity.this, mailContent.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    mLoadingAndRetryManager.showContent();
                   Toast.makeText(SmsContentActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                mLoadingAndRetryManager.showContent();
               Toast.makeText(SmsContentActivity.this, "系统正在维护，请稍后！", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
