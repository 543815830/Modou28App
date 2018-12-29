package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseActivity;
import com.ruixin.administrator.ruixinapplication.MainActivity;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.home.HomeFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown2;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.PhoneFormatCheckUtils;
import com.ruixin.administrator.ruixinapplication.utils.SharedPrefUtility;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 注册界面
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.rg_phone_number)
    EditText rgPhoneNumber;
    @BindView(R.id.rg_verification_code)
    EditText rgVerificationCode;
    @BindView(R.id.rg_get_code)
    TextView rgGetCode;
    @BindView(R.id.rg_nickname)
    EditText rgNickname;
    @BindView(R.id.rg_password)
    EditText rgPassword;
    @BindView(R.id.rg_pwd_again)
    EditText rgPwdAgain;
    @BindView(R.id.rg_register)
    Button rgRegister;
    @BindView(R.id.back_home)
    TextView backHome;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private String userNumber;
    // 客户端输入的验证
    private String valicationCode;
    private String serverValicationCode;
    private String type;
    // 服务器端获取的验证码
    private String nickName;
    private String passWord;
    private String pwAgain;
    private Context context;
    //获取短信验证码参数
    private HashMap<String, String> sms_verification_code_prarams_maps = new HashMap<String, String>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private Message msg = new Message();
    Intent intent;
    CountDown2 timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initStatus();
        initView();
    }

    private void initView() {
        context = getApplicationContext();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusBarColor));
        }
    }

    @OnClick({R.id.rg_get_code, R.id.rg_register, R.id.tv_login,R.id.back_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //基本使用
            //点击获取验证码
            case R.id.rg_get_code:
                if(rgGetCode.getText().toString().equals("获取验证码")||rgGetCode.getText().toString().equals("重新获取")){


                // 判断手机号是否输入
                userNumber = rgPhoneNumber.getText().toString().trim();
                boolean judge = PhoneFormatCheckUtils.isMobile(userNumber);
                if (TextUtils.isEmpty(userNumber)) {
                   Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.e("judge", "" + judge);
                    if (judge) {
                        sms_verification_code_prarams_maps.put("number", userNumber);
                        Log.e("tag", "userNumber" + userNumber);
                        type = "reg";
                        sms_verification_code_prarams_maps.put("type", type);
                        Log.e("tag", "sms_verification_code_prarams_maps" + sms_verification_code_prarams_maps);
                        if (timer == null) {
                            timer = new CountDown2(60 * 1000, 1000, rgGetCode, null);
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
            case R.id.rg_register:
                register();
                break;
                case R.id.tv_login:
                    /*removeALLActivity();
                    Intent  intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.putExtra("where","3");
                    startActivity(intent);*/
                    finish();
                break;
            case R.id.back_home:
                Intent  intent2=new Intent(RegisterActivity.this,MainActivity.class);
                intent2.putExtra("where","2");
              //  removeALLActivity();
                startActivity(intent2);
                finish();
                break;

        }
    }


    /**
     * 注册
     */
    public void register() {
        // 1.首先判断输入的值是否有效
        // 2.然后判断输入的验证码是否有效（防止没有点击获取验证码自己填的错误验证码)
        // 3.最后注册
        userNumber = rgPhoneNumber.getText().toString().trim();
        valicationCode = rgVerificationCode.getText().toString().trim();
        nickName = rgNickname.getText().toString().trim();
        passWord = rgPassword.getText().toString().trim();
        pwAgain = rgPwdAgain.getText().toString().trim();
        if (isValid()) {
            prarams.put("code", valicationCode);
            new SmsCodeAsyncTask().execute();
        }
    }

    /**
     * 注册之前判断数据是否为空
     */
    @SuppressLint("NewApi")
    public boolean isValid() {
        if (userNumber.equals("")) {
           Toast.makeText(this, "请输入手机号!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (valicationCode.equals("")) {
           Toast.makeText(this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nickName.equals("")) {
           Toast.makeText(this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passWord.equals("")) {
           Toast.makeText(this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passWord.length() < 6) {
           Toast.makeText(this, "密码至少6位!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Objects.equals(pwAgain, passWord)) {
           Toast.makeText(this, "确认密码错误！", Toast.LENGTH_SHORT).show();
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
                    Log.e("获取验证码消息返回", "entry" + entry);
                    if (entry.getStatus() == 1) {
                       Toast.makeText(context, "获取验证码成功", Toast.LENGTH_SHORT).show();
                    } else if (entry.getStatus() == 99) {
                            /*抗攻击*/
                        Unlogin.doAtk(prarams,result,new GetSmsCodeAsyncTask());
                    } else{
                        Toast.makeText(context, entry.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }
            } else {
               Toast.makeText(context, "加载数据为空", Toast.LENGTH_SHORT).show();
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
                        //验证成功，然后注册
                        prarams.put("mobile", userNumber);
                        prarams.put("tbUserNick", nickName);
                        prarams.put("tbUserPwd", passWord);
                        prarams.put("code", valicationCode);
                        new RegisterAsyncTask().execute();
                    } else if (entry.getStatus() == 99) {
                /*抗攻击*/
                Unlogin.doAtk(prarams,result,new SmsCodeAsyncTask());
                    } else {
                       Toast.makeText(context, "验证码错误！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    /*注册的异步请求*/
    @SuppressLint("StaticFieldLeak")
    private class RegisterAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            // 请求数据
            String result = AgentApi.dopost(URL.getInstance().REGISTER_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("注册消息返回", "消息返回结果result" + result);
            if (result != null) {
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(result, Entry.class);
                    if (entry != null) {
                        if (entry.getStatus() == 1) {
                            Gson gson1 = new Gson();
                            User user = gson1.fromJson(result, User.class);
                            SharedPrefUtility.saveData(RegisterActivity.this, user);
                            intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("where", "3");
                            removeALLActivity();
                            startActivity(intent);
                            finish();
                          //  startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }  else if (entry.getStatus() == 99) {
                            /*抗攻击*/
                            Unlogin.doAtk(prarams,result,new RegisterAsyncTask());
                        } else {
                            Toast.makeText(context, entry.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (index != -1) {
                   Toast.makeText(context, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
