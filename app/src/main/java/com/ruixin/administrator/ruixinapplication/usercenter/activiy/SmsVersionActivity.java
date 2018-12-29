package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 手机绑定界面
 */
public class SmsVersionActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rb_open)
    RadioButton rbOpen;
    @BindView(R.id.rb_close)
    RadioButton rbClose;
    @BindView(R.id.commit)
    Button commit;
    String userId = "",suerToken="",sms="1";
    @BindView(R.id.rg_isopen)
    RadioGroup rgIsopen;
    private HashMap<String, String> prarams = new HashMap<String, String>();
String userlogin_sj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_version);
        ButterKnife.bind(this);
        initStatus();
        userId = getIntent().getStringExtra("userId");
        suerToken = getIntent().getStringExtra("userToken");
        if (userId.equals("")) {
           Toast.makeText(SmsVersionActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SmsVersionActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        } else {
            initView();
        }
    }

    private void initView() {
        tvTitle.setText("手机绑定");
        userlogin_sj =getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_loginsj", "");
        setListener();
    }
    private void setListener() {
        rgIsopen.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中
        if(userlogin_sj.equals("0")){
            rgIsopen.check(R.id.rb_open);
        }else{
            rgIsopen.check(R.id.rb_close);
        }

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_open://开启
                    sms = "1";
                    break;
                case R.id.rb_close://关闭
                    sms = "0";
                    break;
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

    @OnClick({R.id.back_arrow, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.commit:
                prarams.put("usersid",userId);
                prarams.put("usertoken",suerToken);
                prarams.put("sms",sms);
                new IsOpenAsyncTask().execute();
                break;
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class IsOpenAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().SmsVersion_URL, prarams);
            Log.e("是否开启短信验证的数据返回", "" + result);
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
                        msg=jsb.optString("msg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status == 1) {
                       Toast.makeText(SmsVersionActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences =getSharedPreferences("UserInfo",
                                Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_loginsj", ""+sms);//手機短信驗證
                        editor.apply();
                       // EventBus.getDefault().post(new MessageEvent("3"));
                        finish();

                    }else if(status==-97||status==-99){
                        Unlogin.doLogin(SmsVersionActivity.this);
                    }else if (status ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new IsOpenAsyncTask());
                      //  AsyncTask<String, Integer, String> execute = new IsOpenAsyncTask().execute();
                    } else {
                       Toast.makeText(SmsVersionActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(SmsVersionActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(SmsVersionActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
