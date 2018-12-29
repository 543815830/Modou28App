package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.DepositFragment;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown2;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.PhoneFormatCheckUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.HashMap;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 忘记密码界面
 */
public class ForgetPasswordActivity extends Activity implements View.OnClickListener {
    private LinearLayout arrow_back;
    TextView tv_title;//标题
    private Button btn_next;
    private TextView get_code;
    private TextView phone_number;
    private EditText et_code;
    private String userNumber;
    private String code;
    private Context context;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    String type;
    //获取短信验证码参数
    private HashMap<String, String> sms_verification_code_prarams_maps = new HashMap<String, String>();
    CountDown2 timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initStatus();
        initView();
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("忘记密码");
        arrow_back = findViewById(R.id.back_arrow);
        btn_next = findViewById(R.id.next);
        get_code = findViewById(R.id.get_code);
        et_code = findViewById(R.id.verification_code);
        phone_number = findViewById(R.id.phone_number);
        arrow_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        get_code.setOnClickListener(this);
        context = getApplicationContext();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.get_code:
                if(get_code.getText().toString().equals("获取验证码")||get_code.getText().toString().equals("重新获取")){
                    // 判断手机号是否输入
                    userNumber = phone_number.getText().toString().trim();
                    boolean judge = PhoneFormatCheckUtils.isMobile(userNumber);
                    if (TextUtils.isEmpty(userNumber)) {
                       Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (judge) {
                            sms_verification_code_prarams_maps.put("number", userNumber);
                            type = "findpwd";
                            sms_verification_code_prarams_maps.put("type", type);
                            if (timer == null) {
                                timer = new CountDown2(60 * 1000, 1000, get_code, null);
                            }else{
                                timer.setMillisInFuture(60 * 1000);
                            }
                            timer.start();
                            new GetSmsCodeAsyncTask().execute();
                        } else {
                           Toast.makeText(this, "手机号不合法", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
            case R.id.next:
                goNext();
                break;
        }
    }

    private void goNext() {
        userNumber = phone_number.getText().toString().trim();
        code = et_code.getText().toString().trim();
        if (isValid()) {
            prarams.put("code", code);
            new SmsCodeAsyncTask().execute();
        }

    }

    @SuppressLint("NewApi")
    public boolean isValid() {
        if (userNumber.equals("")) {
           Toast.makeText(this, "请输入手机号!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (et_code.equals("")) {
           Toast.makeText(this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 从服务器获取验证码
     *
     * @return
     * @throws Exception
     */
    //网络请求 异步框架,获取验证码
    @SuppressLint("StaticFieldLeak")
    private class GetSmsCodeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost(URL.getInstance().AJAX_MOBILE_APP, sms_verification_code_prarams_maps);
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
                       Toast.makeText(context, "获取验证码成功", Toast.LENGTH_SHORT).show();
                    }else if (entry.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(sms_verification_code_prarams_maps,result,new GetSmsCodeAsyncTask());
                    }else if (entry.getStatus()==-97||entry.getStatus()==-99) {
                        /*抗攻击*/
                        Unlogin.doLogin(ForgetPasswordActivity.this);
                    } else {
                       Toast.makeText(context, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
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
                        userNumber = phone_number.getText().toString().trim();
//生成一个Intent对象
                        Intent intent = new Intent();
//设置传递的参数
                        intent.putExtra("number", userNumber);
//从Activity MainActivity跳转到Activity OActivity
                        intent.setClass(ForgetPasswordActivity.this, NPwdActivity.class);
                        startActivity(intent);
                        finish();
                    }else if (entry.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,result,new SmsCodeAsyncTask());
                    }else if (entry.getStatus()==-97||entry.getStatus()==-99) {
                        /*抗攻击*/
                        Unlogin.doLogin(ForgetPasswordActivity.this);
                    } else {
                        Toast.makeText(context, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
