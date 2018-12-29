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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.Relief;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 救济领取界面
 */
public class GetReliefActivity extends Activity {
    TextView tv_title;//标题
    LinearLayout back_arrow;//返回
    String userId = "", userToken = "";
    @BindView(R.id.can_get_relief_0)
    TextView canGetRelief0;
    @BindView(R.id.get_relief_condition_0)
    TextView getReliefCondition0;
    @BindView(R.id.get_relief_fre_0)
    TextView getReliefFre0;
    @BindView(R.id.can_get_relief_1)
    TextView canGetRelief1;
    @BindView(R.id.get_relief_condition_1)
    TextView getReliefCondition1;
    @BindView(R.id.get_relief_fre_1)
    TextView getReliefFre1;
    @BindView(R.id.can_get_relief_2)
    TextView canGetRelief2;
    @BindView(R.id.get_relief_condition_2)
    TextView getReliefCondition2;
    @BindView(R.id.get_relief_fre_2)
    TextView getReliefFre2;
    @BindView(R.id.can_get_relief_3)
    TextView canGetRelief3;
    @BindView(R.id.get_relief_condition_3)
    TextView getReliefCondition3;
    @BindView(R.id.get_relief_fre_3)
    TextView getReliefFre3;
    @BindView(R.id.can_get_relief_4)
    TextView canGetRelief4;
    @BindView(R.id.get_relief_condition_4)
    TextView getReliefCondition4;
    @BindView(R.id.get_relief_fre_4)
    TextView getReliefFre4;
    @BindView(R.id.can_get_relief_5)
    TextView canGetRelief5;
    @BindView(R.id.get_relief_condition_5)
    TextView getReliefCondition5;
    @BindView(R.id.get_relief_fre_5)
    TextView getReliefFre5;
    @BindView(R.id.can_get_relief_6)
    TextView canGetRelief6;
    @BindView(R.id.get_relief_condition_6)
    TextView getReliefCondition6;
    @BindView(R.id.get_relief_fre_6)
    TextView getReliefFre6;
    @BindView(R.id.commit)
    Button commit;
    User user;
    public static int RESULT_CODE = 300;
    private HashMap<String, String> prarams = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_relief);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        initStatus();
        if (userId.equals("")) {
           Toast.makeText(GetReliefActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(GetReliefActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        } else {
            initView();
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

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("救济领取");
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new MyOnClick());
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new ReliefAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class ReliefAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().Relief_URL, prarams);
            Log.e("救济领取的数据返回", "" + result);
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
                    Relief relief = gson.fromJson(s, Relief.class);
                    if (relief.getStatus() == 1) {
                        //发送消息
                        canGetRelief0.setText(relief.getData().getWeb_jjv0());
                        canGetRelief1.setText(relief.getData().getWeb_jjv1());
                        canGetRelief2.setText(relief.getData().getWeb_jjv2());
                        canGetRelief3.setText(relief.getData().getWeb_jjv3());
                        canGetRelief4.setText(relief.getData().getWeb_jjv4());
                        canGetRelief5.setText(relief.getData().getWeb_jjv5());
                        canGetRelief6.setText(relief.getData().getWeb_jjv6());
                        getReliefCondition0.setText(new StringBuilder().append("余额<").append(relief.getData().getWeb_jjv0()).toString());
                        getReliefCondition1.setText(new StringBuilder().append("余额<").append(relief.getData().getWeb_jjv1()).toString());
                        getReliefCondition2.setText(new StringBuilder().append("余额<").append(relief.getData().getWeb_jjv2()).toString());
                        getReliefCondition3.setText(new StringBuilder().append("余额<").append(relief.getData().getWeb_jjv3()).toString());
                        getReliefCondition4.setText(new StringBuilder().append("余额<").append(relief.getData().getWeb_jjv4()).toString());
                        getReliefCondition5.setText(new StringBuilder().append("余额<").append(relief.getData().getWeb_jjv5()).toString());
                        getReliefCondition6.setText(new StringBuilder().append("余额<").append(relief.getData().getWeb_jjv6()).toString());
                        getReliefFre0.setText(relief.getData().getJiujitime());
                        getReliefFre1.setText(relief.getData().getJiujitime());
                        getReliefFre2.setText(relief.getData().getJiujitime());
                        getReliefFre3.setText(relief.getData().getJiujitime());
                        getReliefFre4.setText(relief.getData().getJiujitime());
                        getReliefFre5.setText(relief.getData().getJiujitime());
                        getReliefFre6.setText(relief.getData().getJiujitime());
                    }else if (relief.getStatus()  ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new ReliefAsyncTask());
                    }else if(relief.getStatus()==-99||relief.getStatus()==-97){
                        Unlogin.doLogin(GetReliefActivity.this);
                    } else {
                        Toast.makeText(GetReliefActivity.this, relief.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(GetReliefActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(GetReliefActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @OnClick(R.id.commit)
    public void onViewClicked() {
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new GetReliefAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetReliefAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().GRelief_URL, prarams);
            Log.e("领取救济的数据返回", "" + result);
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
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        EventBus.getDefault().post(new MessageEvent("3"));
                        Toast.makeText(GetReliefActivity.this, "领取成功！", Toast.LENGTH_SHORT).show();
                    }else if (entry.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new GetReliefAsyncTask());
                    }else if(entry.getStatus()==-99||entry.getStatus()==-97){
                        Unlogin.doLogin(GetReliefActivity.this);
                    }else {
                        Toast.makeText(GetReliefActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(GetReliefActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(GetReliefActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class MyOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back_arrow:
                    finish();
                    break;
            }
        }
    }
}
