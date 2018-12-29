package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.ruixin.administrator.ruixinapplication.usercenter.databean.Rebate;
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
 * 首充返利activity
 */
public class FirstRebateActivity extends Activity {
    TextView tv_title;//标题
    LinearLayout back_arrow;//返回
    public static int RESULT_CODE = 500;
    String userId = "", userToken = "";
    User user;
    @BindView(R.id.fanli)
    TextView fanli;
    @BindView(R.id.first_pay)
    TextView firstPay;
    @BindView(R.id.commit)
    Button commit;
    @BindView(R.id.shouchong_lilv)
    TextView shouchongLilv;
    private HashMap<String, String> prarams = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_rebate);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        initStatus();
        if (userId.equals("")) {
           Toast.makeText(FirstRebateActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(FirstRebateActivity.this,LoginActivity.class);
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
        tv_title.setText("首充返利");
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new MyOnClick());
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new RebateAsyncTask().execute();
    }

    @OnClick(R.id.commit)
    public void onViewClicked() {
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new GetRebateAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetRebateAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().GRebate_URL, prarams);
            Log.e("领取首充返利的数据返回", "" + result);
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
                        user = gson.fromJson(s, User.class);
                        Log.e("领取的数据返回", "领取成功");
                        Toast.makeText(FirstRebateActivity.this, "领取成功！", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new MessageEvent("3"));
                        fanli.setText("您昨日的首充返利已领取！");
                        fanli.setTextSize(12f);
                        fanli.setTextColor(Color.parseColor("#ff0000"));
                        commit.setText("已领取");
                        commit.setClickable(false);
                        commit.setEnabled(false);
                    }else if (entry.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new GetRebateAsyncTask());
                    } else if(entry.getStatus()==-99||entry.getStatus()==-97){
                        Unlogin.doLogin(FirstRebateActivity.this);
                    }else {
                        Toast.makeText(FirstRebateActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(FirstRebateActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(FirstRebateActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class RebateAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().Rebate_URL, prarams);
            Log.e("首充返利的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                    Gson gson = new Gson();
                    Rebate rebate = gson.fromJson(s, Rebate.class);
                    if (rebate.getStatus() == 1) {
                        Log.e("首充返利的数据返回", "" + s);
                        if (rebate.getData().getIsgeted().equals("0")) {
                            if (rebate.getData().getFlpoints() == null) {
                                fanli.setText("0");
                            } else {
                                fanli.setText(rebate.getData().getFlpoints());
                            }
                            if (rebate.getData().getPoints() == null) {
                                firstPay.setText("0");
                            } else {
                                firstPay.setText(rebate.getData().getPoints());
                            }
                            shouchongLilv.setText(new StringBuilder().append("            魔豆28首充返利盛大来袭，所有会员在魔豆28每日首充，即可获得首次充值金额的").append(rebate.getData().getScflbl()).append("%返利").toString());

                        } else   if (rebate.getData().getIsgeted().equals("1")){
                            fanli.setText("您昨日的首充返利已领取！");
                            fanli.setTextSize(12f);
                            fanli.setTextColor(Color.parseColor("#ff0000"));
                            if (rebate.getData().getPoints() == null) {
                                firstPay.setText("0");
                            } else {
                                firstPay.setText(rebate.getData().getPoints());
                            }
                            shouchongLilv.setText(new StringBuilder().append("            魔豆28首充返利盛大来袭，所有会员在魔豆28每日首充，即可获得首次充值金额的").append(rebate.getData().getScflbl()).append("%返利").toString());
                            commit.setText("已领取");
                            commit.setClickable(false);
                            commit.setEnabled(false);
                        }
                        //发送消息
                    } else if (rebate.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new RebateAsyncTask());
                    }else if(rebate.getStatus()==-99||rebate.getStatus()==-97){
                        Unlogin.doLogin(FirstRebateActivity.this);
                    }else{
                        Toast.makeText(FirstRebateActivity.this, rebate.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
               Toast.makeText(FirstRebateActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
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
