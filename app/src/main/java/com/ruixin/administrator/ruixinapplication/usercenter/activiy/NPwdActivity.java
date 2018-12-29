package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 下一步修改密码界面
 */
public class NPwdActivity extends Activity implements View.OnClickListener {
    TextView tv_title;//标题
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    private String userNumber;
    private EditText pwd_et;
    private EditText agpwd_et;
    private Button update_pwd;
    private String password;
    private String apassword;
    private String Type;
    private HashMap<String, String> prarams = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npwd);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        // 取其中的值
        userNumber = intent.getStringExtra("number");
        initStatus();
        initView();
    }

    private void initView() {

        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("忘记密码");
        pwd_et = findViewById(R.id.pwd_et);
        agpwd_et = findViewById(R.id.agpwd_et);
        update_pwd = findViewById(R.id.update_pwd);
        update_pwd.setOnClickListener(this);
        backArrow.setOnClickListener(this);
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @SuppressLint("NewApi")
    public boolean isValid() {
        if (password.equals("")) {
            Toast.makeText(this, "请输入密码!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (apassword.equals("")) {
            Toast.makeText(this, "请确认密码!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Objects.equals(apassword, password)) {
            Toast.makeText(this, "确认密码错误！", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.update_pwd:
                update();
                break;
        }

    }

    private void update() {
        password = pwd_et.getText().toString().trim();
        apassword = agpwd_et.getText().toString().trim();
        if (isValid()) {
            prarams.put("mobile", userNumber);
            prarams.put("password", password);
            new UpdateAsynctask().execute();
        }
    }



    /**
     * 从服务器获取验证码
     *
     * @return
     * @throws Exception
     */
    //网络请求 异步框架,获取验证码
    @SuppressLint("StaticFieldLeak")
    private class UpdateAsynctask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost(URL.getInstance().PWD_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("修改密码消息返回", "消息返回结果result" + result);
            if (result != null) {
                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                if (entry != null) {
                    if (entry.getStatus() == 1) {
                        finish();
                        Intent intent = new Intent(NPwdActivity.this, LoginActivity.class);
                        intent.putExtra("where", "2");
                        startActivity(intent);
                    } else if (entry.getStatus() == 99) {
                        /*抗攻击*/
                        ATK atK = gson.fromJson(result, ATK.class);
                        String vaild_str = atK.getVaild_str();
                        Log.e("tag", "" + vaild_str);
                        String vaildd_md5 = FormatUtils.md5(vaild_str);
                        Log.e("tag", "" + vaildd_md5);
                        prarams.put("vaild_str", vaildd_md5);
                        new UpdateAsynctask().execute();
                    } else {
                        Toast.makeText(NPwdActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
