package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.ConversionActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.BindBankFragent;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.GoldDepositFragment;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CustomViewPager;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.IndicatorLineUtil;
import com.ruixin.administrator.ruixinapplication.utils.SharedPrefUtility;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 金币提现界面
 */
public class GoldDepositActivity extends FragmentActivity {
    int height = 0;
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.user_balance)
    TextView userBalance;
    @BindView(R.id.tayLayout)
    TabLayout tayLayout;
    @BindView(R.id.gold_vp)
    CustomViewPager goldVp;
    @BindView(R.id.rl_title_bar)
    RelativeLayout rlTitleBar;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
    private String[]tabs=new String[]{"我要提现","绑定收款账户"};
    String userId = "", userToken = "",userSecques,userMail;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    String result;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_deposit);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        initStatus();
        initView();
        EventBus.getDefault().register(this);
    }
    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(MessageEvent enevnt) {
        if(enevnt.result.equals("3")) {
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            new UserInfoAsyncTask().execute();
        }

    }
    @SuppressLint("StaticFieldLeak")
    private class UserInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().Info_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("回调消息返回", "消息返回结果result" + result);
            if (result != null) {
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    try {
                        JSONObject jsb = new JSONObject(result);
                        int status = jsb.optInt("status");
                        String msg=jsb.optString("msg");
                        if (status == 1) {
                            Gson gson = new Gson();
                            user = gson.fromJson(result, User.class);
                            SharedPrefUtility.saveData(GoldDepositActivity.this, user);
                            userBalance.setText(FormatUtils.formatString(String.valueOf((int)Math.floor(Double.parseDouble(user.getData().getPoints())/1000))));
                        } else if (status == -97 || status == -99) {
                            Unlogin.doLogin(GoldDepositActivity.this);
                        } else if (status == 99) {
                            /*抗攻击*/
                            Unlogin.doAtk(prarams,result,new UserInfoAsyncTask());
                        } else{
                            Toast.makeText(GoldDepositActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (index != -1) {
                    Toast.makeText(GoldDepositActivity.this, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(GoldDepositActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        tvTitle.setText("金币提现");
        SharedPreferences sharedPreferences =getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        userMail = sharedPreferences.getString("user_mail", "");
        userBalance.setText(FormatUtils.formatString(String.valueOf((int)Math.floor(Double.parseDouble(sharedPreferences.getString("user_balance", ""))/1000))));
        userSecques =getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_secques", "");
        if (userSecques.equals("")) {
            Toast.makeText(GoldDepositActivity.this, "资料不全请先补全资料！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(GoldDepositActivity.this, SetPwdActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("userToken", userToken);
            startActivity(intent);
            finish();
        }else if (userMail.equals("")) {
            Toast.makeText(GoldDepositActivity.this, "资料不全请先补全资料！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(GoldDepositActivity.this, MailVersionActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("userToken", userToken);
            startActivity(intent);
            finish();
        }
        tayLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(tayLayout, 45, 45);
            }
        });
        //注册
      //  EventBus.getDefault().register(this);
        initVp();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initVp() {
        tayLayout.setTabMode(TabLayout.MODE_FIXED);
        tayLayout.setupWithViewPager(goldVp, false);
        for (int i = 0; i < tabs.length; i++) {

            tayLayout.addTab(tayLayout.newTab());
        }
        newsList.add(new GoldDepositFragment());
        newsList.add(new BindBankFragent());
        //设置viewpager适配器
        adapter = new OneFmAdapter(getSupportFragmentManager(), newsList);
        goldVp.setAdapter(adapter);
        for (int i = 0; i < tabs.length; i++) {
            tayLayout.getTabAt(i).setText(tabs[i]);
        }
    }

    private void initStatus() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(option);
        getStatusBarHeight();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rlTitleBar.getLayoutParams();
        layoutParams.setMargins(0, height, 0, 0);
    }

    private void getStatusBarHeight() {

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }
}
