package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown2;
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
 * 修改密码的界面
 */
public class UpdatePasswordActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.old_pwd)
    EditText oldPwd;
    @BindView(R.id.new_pwd)
    EditText newPwd;
    @BindView(R.id.ag_new_pwd)
    EditText agNewPwd;
    @BindView(R.id.tv_pwd_que)
    TextView tvPwdQue;
    @BindView(R.id.et_pwd_ans)
    EditText etPwdAns;
    @BindView(R.id.versioncode)
    EditText versioncode;
    @BindView(R.id.get_version_code)
    TextView getVersionCode;
    @BindView(R.id.commit)
    Button commit;
    String userSecques, userToken, userId, type;
    String old_pwd, new_pwd, ag_new_pwd, pwd_ans, code;
    //获取短信验证码参数
    private HashMap<String, String> sms_verification_code_prarams_maps = new HashMap<String, String>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    CountDown2 timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userSecques = getIntent().getStringExtra("userSecques");
        userToken = getIntent().getStringExtra("userToken");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        if (userId.equals("")) {
            Toast.makeText(this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
            intent.putExtra("where", "2");
            startActivity(intent);
        } else {
            initStatus();
            initView();
        }


    }

    private void initView() {
        tvTitle.setText("修改密码");
        if (userSecques.equals("")) {
            Toast.makeText(this, "您尚未设置密保问题，资料不全，请补全资料！", Toast.LENGTH_SHORT).show();
            finish();
        }
        tvPwdQue.setText(userSecques);
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick({R.id.back_arrow, R.id.get_version_code, R.id.commit, R.id.forget_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.get_version_code:
                if (getVersionCode.getText().toString().equals("获取验证码") || getVersionCode.getText().toString().equals("重新获取")) {
                    sms_verification_code_prarams_maps.put("usersid", userId);
                    sms_verification_code_prarams_maps.put("usertoken", userToken);
                    type = "editpwd";
                    sms_verification_code_prarams_maps.put("type", type);
                    Log.e("tag", "sms_verification_code_prarams_maps" + sms_verification_code_prarams_maps);
                    if (timer == null) {
                        timer = new CountDown2(60 * 1000, 1000, getVersionCode, null);
                    } else {
                        timer.setMillisInFuture(60 * 1000);
                    }
                    timer.start();
                    new GetSmsCodeAsyncTask().execute();
                }

                break;
            case R.id.commit:
                update();
                break;
            case R.id.forget_pwd:
                Intent intent = new Intent(UpdatePasswordActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void update() {
        old_pwd = oldPwd.getText().toString().trim();
        new_pwd = newPwd.getText().toString().trim();
        ag_new_pwd = agNewPwd.getText().toString().trim();
        pwd_ans = etPwdAns.getText().toString().trim();
        code = versioncode.getText().toString().trim();
        if (isValid()) {
            prarams.put("code", code);
            new SmsCodeAsyncTask().execute();
        }
    }

    @SuppressLint("NewApi")
    public boolean isValid() {

        if (old_pwd.equals("")) {
            Toast.makeText(this, "旧密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (new_pwd.equals("")) {
            Toast.makeText(this, "新密码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (new_pwd.length() < 6) {
            Toast.makeText(this, "登录密码不能少于6位数!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ag_new_pwd.equals("")) {
            Toast.makeText(this, "确认新密码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pwd_ans.equals("")) {
            Toast.makeText(this, "密保答案不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (code.equals("")) {
            Toast.makeText(this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Objects.equals(new_pwd, ag_new_pwd)) {
            Toast.makeText(this, "确认密码错误！", Toast.LENGTH_SHORT).show();
        }

        return true;
    }


    //网络请求 异步框架,获取验证码
    class GetSmsCodeAsyncTask extends AsyncTask<String, Integer, String> {
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
                        Toast.makeText(UpdatePasswordActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                    } else if (entry.getStatus() == -97 || entry.getStatus() == -99) {
                        Unlogin.doLogin(UpdatePasswordActivity.this);
                    } else if (entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(sms_verification_code_prarams_maps, result,  new GetSmsCodeAsyncTask());
                        // new LoginAsyncTask().execute();
                      //  new GetSmsCodeAsyncTask().execute();
                    } else {
                        Toast.makeText(UpdatePasswordActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(UpdatePasswordActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
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
                        //验证成功，然后修改
                        prarams.put("tbOldPwd", old_pwd);
                        prarams.put("tbNewPwd", new_pwd);
                        prarams.put("tbRePwd", ag_new_pwd);
                        prarams.put("answer", pwd_ans);
                        new UpdateAsyncTask().execute();
                    } else if (entry.getStatus() == -97 || entry.getStatus() == -99) {
                        Unlogin.doLogin(UpdatePasswordActivity.this);
                    } else if (entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams, result,  new SmsCodeAsyncTask());
                       // new SmsCodeAsyncTask().execute();
                    } else  {
                        Toast.makeText(UpdatePasswordActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            // 请求数据
            String result = AgentApi.dopost(URL.getInstance().Update_Pwd, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("修改密码返回", "消息返回结果result" + result);
            if (result != null) {
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(result, Entry.class);
                    if (entry != null) {
                        if (entry.getStatus() == 1) {
                            finish();
                            Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                            intent.putExtra("where", "2");
                            startActivity(intent);
                        } else if (entry.getStatus() == -97 || entry.getStatus() == -99) {
                            Unlogin.doLogin(UpdatePasswordActivity.this);
                        } else if (entry.getStatus() == 99) {
                            /*抗攻击*/
                            Unlogin.doAtk(prarams, result,   new UpdateAsyncTask());
                        }else{
                            versioncode.setText("");
                            Toast.makeText(UpdatePasswordActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (index != -1) {
                    Toast.makeText(UpdatePasswordActivity.this, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

