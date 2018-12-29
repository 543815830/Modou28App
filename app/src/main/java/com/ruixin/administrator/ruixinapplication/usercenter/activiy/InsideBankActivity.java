package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.RMessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.DepositFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.FetchFragment;
import com.ruixin.administrator.ruixinapplication.utils.IndicatorLineUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 站内银行界面
 */
public class InsideBankActivity extends FragmentActivity{
    TextView tv_title;//标题
    LinearLayout back_arrow;//返回
    TextView inside_user_balance;//账户余额
    TextView inside_bank_balance;//银行余额
    private ViewPager inside_bank_vp;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
    public static int RESULT_CODE = 50;
    String userId = "", userBalance = "", userCoin = "", userSecques = "", userToken = "";
    User user;
    private TabLayout tabLayout;
    private String[]tabs=new String[]{"存入","取出"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_bank);
        initStatus();
        userId = getIntent().getStringExtra("userId");
        userBalance = getIntent().getStringExtra("userBalance");
        userCoin = getIntent().getStringExtra("userCoin");
        userToken = getIntent().getStringExtra("userToken");
        Log.e("tag", "userId" + userId);
        if (userId.equals("")) {
           Toast.makeText(InsideBankActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(InsideBankActivity.this,LoginActivity.class);
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
        tv_title.setText("站内银行");
        inside_user_balance = findViewById(R.id.inside_user_balance);
        inside_user_balance.setText(userBalance);
        inside_bank_balance = findViewById(R.id.inside_bank_balance);
        inside_bank_balance.setText(userCoin);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new MyOnClick());
        tabLayout = findViewById(R.id.tayLayout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(tabLayout, 45, 45);
            }
        });
        inside_bank_vp = findViewById(R.id.inside_bank_vp);
        //注册
        EventBus.getDefault().register(this);
        initVp();
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(RMessageEvent enevnt) {
        String result = enevnt.result;
        if(!enevnt.result.equals("3")){
            Gson gson = new Gson();
            user = gson.fromJson(result, User.class);
            inside_user_balance.setText(user.getData().getPoints());
            inside_bank_balance.setText(user.getData().getBack());
            saveParameter();
        }
        EventBus.getDefault().post(new MessageEvent("3"));

    }

    private void saveParameter() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (user.getData().getPoints() != null) {
            editor.putString("user_balance", user.getData().getPoints()); //账户余额
        } else {
            editor.putString("user_balance", "");
        }
        if (user.getData().getBack() != null) {
            editor.putString("user_coin", user.getData().getBack());   //银行金币
        } else {
            editor.putString("user_coin", "");
        }
        editor.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }
    private void initVp() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(inside_bank_vp, false);
        for (int i = 0; i < tabs.length; i++) {

            tabLayout.addTab(tabLayout.newTab());
        }
        newsList.add(new DepositFragment());
        newsList.add(new FetchFragment());
        //设置viewpager适配器
        adapter = new OneFmAdapter(getSupportFragmentManager(), newsList);
        inside_bank_vp.setAdapter(adapter);
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.getTabAt(i).setText(tabs[i]);
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
