package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.domain.EntryDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.PwdCard;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown2;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.SharedPrefUtility;
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
 * 第三方手机验证界面
 */
public class PhoneVersionActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    @BindView(R.id.get_code)
    TextView getCode;
    @BindView(R.id.commit)
    Button commit;
    String type;
    String code;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    CountDown2 timer;
    String openID;
    String accessToken;
    private HashMap<String, String> qq_prarams = new HashMap<String, String>();
    String phone;
    public static int RESULT_CODE = 11;
    private HashMap<String, String> call_back_prarams = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_version);
        ButterKnife.bind(this);
        initView();
        initStatus();
        openID=getIntent().getStringExtra("openid");
        accessToken=getIntent().getStringExtra("token");
        call_back_prarams.put("openid", openID);
        call_back_prarams.put("token", accessToken);
    }

    private void initView() {
        tvTitle.setText("手机验证");
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
                prarams.clear();
                if (getCode.getText().toString().equals("获取验证码")||getCode.getText().toString().equals("重新获取")){
                    phone=phoneNumber.getText().toString().trim();
                    prarams.put("number",phone);
                    type = "bindqq";
                    prarams.put("type",type);
                    if (timer == null) {
                        timer = new CountDown2(60 * 1000, 1000, getCode, null);
                    }else{
                        timer.setMillisInFuture(60 * 1000);
                    }
                    timer.start();
                    new GetSmsCodeAsyncTask().execute();
                }
                break;
            case R.id.commit:
                code=verificationCode.getText().toString().trim();
                prarams.put("code",code);
                new SmsCodeAsyncTask().execute();
                break;
        }
    }

    //网络请求 异步框架,获取验证码
    class GetSmsCodeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost(URL.getInstance().AJAX_MOBILE_APP, prarams);
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
                       Toast.makeText(PhoneVersionActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                    } else if(entry.getStatus()==-97||entry.getStatus()==-99){
                        Unlogin.doLogin(PhoneVersionActivity.this);
                    }else if (entry.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,result,new GetSmsCodeAsyncTask());
                    }else{
                       Toast.makeText(PhoneVersionActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
               Toast.makeText(PhoneVersionActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*验证验证码*/
    @SuppressLint("StaticFieldLeak")
    private class SmsCodeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost2(URL.getInstance().CODE_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("验证验证码消息返回", "消息返回结果result" + result);
            if (result != null) {
                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                if (entry != null) {
                    if (entry.getStatus() == 1) {
                        call_back_prarams.put("phone",phone);
                        new CallBackAsyncTask().execute();
                    } else if(entry.getStatus()==-97||entry.getStatus()==-99){
                        Unlogin.doLogin(PhoneVersionActivity.this);
                    }else if (entry.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,result,new SmsCodeAsyncTask());
                    }else{
                       Toast.makeText(PhoneVersionActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    String url=URL.getInstance().CallBack_URL+"?act=bind";
    //网络请求 异步框架,qq登录回调
    @SuppressLint("StaticFieldLeak")
    private class CallBackAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost3(url, call_back_prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("回调消息返回", "消息返回结果result" + result);
            if (result.length() > 0) {
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    try {
                        JSONObject jsb = new JSONObject(result);
                        int status = jsb.optInt("status");
                        String msg=jsb.optString("msg");
                        if (status == 1) {
                           Toast.makeText(PhoneVersionActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent();
                           intent.putExtra("phone",phone);
                           setResult(RESULT_CODE, intent);
                            finish();
                        }else {
                           Toast.makeText(PhoneVersionActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (index != -1) {
                   Toast.makeText(PhoneVersionActivity.this, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            } else
               Toast.makeText(PhoneVersionActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }


}
