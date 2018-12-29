package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown2;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
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
 * 邮箱验证界面
 */
public class MailVersionActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mail_number)
    EditText mailNumber;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    @BindView(R.id.get_code)
    TextView getCode;
    @BindView(R.id.commit)
    Button commit;
    public static int RESULT_CODE = 46;
    String userid = "", email = "", action = "post", code = "", userToken = "";
    @BindView(R.id.ll_mail_version)
    LinearLayout llMailVersion;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    CountDown2 timer;
    String result;
    User user;
    LoadingAndRetryManager mLoadingAndRetryManager;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_version);
        ButterKnife.bind(this);
        initStatus();
        userid = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        if (userid.equals("")) {
           Toast.makeText(MailVersionActivity.this, "您尚未登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MailVersionActivity.this, LoginActivity.class);
            intent.putExtra("where", "2");
            startActivity(intent);
        } else {
            initView();
        }
    }

    private void initView() {
        tvTitle.setText("邮箱验证");
        prarams.put("usersid", userid);
        prarams.put("usertoken", userToken);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(llMailVersion, null);
        new UserInfoAsyncTask().execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class UserInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().Info_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("用户信息消息返回", "消息返回结果result" + result);
            if (result != null) {
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    try {
                        JSONObject jsb = new JSONObject(result);
                        int status = jsb.optInt("status");
                        String msg=jsb.optString("msg");
                        if (status == 1) {
                          //  mLoadingAndRetryManager.showContent();
                            Gson gson = new Gson();
                            user = gson.fromJson(result, User.class);
                            if (user.getData().getEmailyz().equals("1")) {
                               Toast.makeText(MailVersionActivity.this, "邮箱已经验证！", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                mLoadingAndRetryManager.showContent();
                            }

                        } else if (status == -97 || status == -99) {
                          //  mLoadingAndRetryManager.showContent();
                               Unlogin.doLogin(MailVersionActivity.this);
                        } else if (status == 99) {
                           // mLoadingAndRetryManager.showContent();
                            /*抗攻击*/
                            Unlogin.doAtk(prarams,result,new UserInfoAsyncTask());
                        } else{
                           Toast.makeText(MailVersionActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (index != -1) {
                   Toast.makeText(MailVersionActivity.this, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(MailVersionActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
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

    @OnClick({R.id.back_arrow, R.id.get_code, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.get_code:
                if (getCode.getText().toString().equals("获取验证码") || getCode.getText().toString().equals("重新获取")) {
                    email = mailNumber.getText().toString().trim();
                    prarams.put("usersid", userid);
                    prarams.put("email", email);
                    prarams.put("action", action);
                    prarams.put("usertoken", userToken);
                    if (timer == null) {
                        timer = new CountDown2(60 * 1000, 1000, getCode, null);
                    } else {
                        timer.setMillisInFuture(60 * 1000);
                    }
                    timer.start();
                    new GetSmsCodeAsyncTask().execute();
                }


                break;
            case R.id.commit:
                email = mailNumber.getText().toString().trim();
                code = verificationCode.getText().toString().trim();
                if(isValid()){
                    prarams.put("usersid", userid);
                    prarams.put("usertoken", userToken);
                    prarams.put("email", email);
                    prarams.put("code", code);
                    new SmsCodeAsyncTask().execute();
                }

                break;
        }
    }
    public boolean isValid() {

        if (email.equals("")) {
            Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (code.equals("")) {
            Toast.makeText(this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    @SuppressLint("StaticFieldLeak")
    private class GetSmsCodeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost3(URL.getInstance().Email_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取验证码消息返回", "消息返回结果result" + result);
            if (result != null) {
                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                if (entry != null) {
                    Log.e("获取验证码消息返回", "entry" + entry);
                    if (entry.getStatus() == 1) {
                       Toast.makeText(MailVersionActivity.this, "邮件发送成功,请注意查收!若收不到邮件，请联系客服。", Toast.LENGTH_SHORT).show();
                    }  else if (entry.getStatus() == -97 || entry.getStatus() == -99) {
                        Unlogin.doLogin(MailVersionActivity.this);
                    } else if (entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,result,new GetSmsCodeAsyncTask());
                    }else {
                        Toast.makeText(MailVersionActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SmsCodeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost3(URL.getInstance().SEmail_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取验证码消息返回", "消息返回结果result" + result);
            if (result != null) {
                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                if (entry != null) {
                    if (entry.getStatus() == 1) {
                       Toast.makeText(MailVersionActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                                Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_mail", email);
                        editor.apply();
                        Intent intent = new Intent();
                        intent.putExtra("result", result);
                        setResult(RESULT_CODE, intent);
                        finish();
                    }  else if (entry.getStatus() == -97 || entry.getStatus() == -99) {
                        Unlogin.doLogin(MailVersionActivity.this);
                    } else if (entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,result,new SmsCodeAsyncTask());
                    } else {
                       Toast.makeText(MailVersionActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
