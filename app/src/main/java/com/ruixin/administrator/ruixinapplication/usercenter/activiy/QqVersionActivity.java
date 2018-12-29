package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * QQ验证界面
 */
public class QqVersionActivity extends Activity {
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.commit)
    Button commit;
    String userId = "",userToken="", qc_allow,qc_close;
    @BindView(R.id.cb_open)
    CheckBox cbOpen;
    @BindView(R.id.cb_jump)
    CheckBox cbJump;
    private HashMap<String, String> prarams = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_version);
        ButterKnife.bind(this);
        initStatus();
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        if (userId.equals("")) {
           Toast.makeText(QqVersionActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(QqVersionActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        } else {
            initView();
        }
    }

    private void initView() {
        tvTitle.setText("QQ验证");
       qc_allow=getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_qcallow", "");
       qc_close=getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_qcclose", "");
       if(qc_allow.equals("0")){
           cbOpen.setChecked(false);

       }else{
           cbOpen.setChecked(true);

       }
        if(qc_close.equals("0")){
            cbJump.setChecked(false);

        }else{
            cbJump.setChecked(true);
        }
        cbOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                 qc_allow="1";
                }else{
                    qc_allow="0";
                }
            }
        });
        cbJump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    qc_close="1";
                }else{
                   qc_close="0";
                }
            }
        });
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick({R.id.back_arrow, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.commit:
                prarams.put("usersid", userId);
                prarams.put("usertoken", userToken);
                prarams.put("qc_allow", qc_allow);
                prarams.put("qc_close", qc_close);
                new IsOpenAsyncTask().execute();
                break;
        }
    }

    private class IsOpenAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().QqVersion_URL, prarams);
            Log.e("是否开启QQ验证的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                int status = -1;
                String msg="设置失败";
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    try {
                        JSONObject jsb = new JSONObject(s);
                        status = jsb.optInt("status");
                        msg = jsb.optString("msg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status == 1) {
                       Toast.makeText(QqVersionActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences =getSharedPreferences("UserInfo",
                                Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_qcallow", ""+qc_allow);//手機短信驗證
                        editor.putString("user_qcclose", ""+qc_close);//手機短信驗證
                        editor.apply();
                        finish();
                    }else if(status==-97||status==-99){
                        Unlogin.doLogin(QqVersionActivity.this);
                    }else if (status ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new IsOpenAsyncTask());
                    } else {
                        Toast.makeText(QqVersionActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(QqVersionActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(QqVersionActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
