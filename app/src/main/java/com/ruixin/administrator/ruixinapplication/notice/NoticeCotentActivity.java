package com.ruixin.administrator.ruixinapplication.notice;

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
import com.ruixin.administrator.ruixinapplication.home.webview.NoticeContentActivity;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.SmsContentActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MailContent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeCotentActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_notice_title)
    TextView tvNoticeTitle;
    @BindView(R.id.send_user)
    TextView sendUser;
    @BindView(R.id.send_time)
    TextView sendTime;
    @BindView(R.id.sms_content)
    TextView smsContent;
    @BindView(R.id.ll_sms_content)
    LinearLayout llSmsContent;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    String usersid, smsid, userToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_cotent);
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
        tvTitle.setText("通知详情");
        backArrow.setOnClickListener(this);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(llSmsContent, null);
        prarams.put("usersid", usersid);
        prarams.put("usertoken", userToken);
        prarams.put("id", smsid);
        new SmsAsyncTask().execute();
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @SuppressLint("StaticFieldLeak")
    private class SmsAsyncTask extends AsyncTask<String, Integer, String> {
        String url = URL.getInstance().NoticeContent_URL ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(url, prarams);
            Log.e("通知详情", "" + result);
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
                        Log.e("通知详情", "" + s);
                        tvNoticeTitle.setText(mailContent.getData().getTitle());
                        sendUser.setText(mailContent.getData().getUsersid());
                        sendTime.setText(mailContent.getData().getTime());
                        smsContent.setText("        " + mailContent.getData().getMag());
                        mLoadingAndRetryManager.showContent();
                    }else if (mailContent.getstatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new SmsAsyncTask());
                    }else if (mailContent.getstatus()==-99||mailContent.getstatus()==-97) {
                        /*抗攻击*/
                        Unlogin.doLogin(NoticeCotentActivity.this);
                    } else {
                        mLoadingAndRetryManager.showContent();
                        Toast.makeText(NoticeCotentActivity.this, mailContent.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    mLoadingAndRetryManager.showContent();
                   Toast.makeText(NoticeCotentActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                mLoadingAndRetryManager.showContent();
               Toast.makeText(NoticeCotentActivity.this, "系统正在维护，请稍后！", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
