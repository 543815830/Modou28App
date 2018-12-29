package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.CommonVoucherFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.MaxVoucherFragment;
import com.ruixin.administrator.ruixinapplication.utils.IndicatorLineUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 点卡使用的界面
 */
public class UseCardActivity extends FragmentActivity{
    TextView tv_title;//标题
    LinearLayout back_arrow;
    String userId="", userToken="";
    private ViewPager card_vp;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
    User user;
    public static int RESULT_CODE = 200;
    private TabLayout tabLayout;
    private String[]tabs=new String[]{"普通充值","批量充值"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_card);
        userId=getIntent().getStringExtra("userId");
        userToken=getIntent().getStringExtra("userToken");
        initStatus();
        if(userId.equals("")){
           Toast.makeText(UseCardActivity.this,"您尚未登录",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(UseCardActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        }else{
            initView();
        }

    }

    private void initView() {
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("点卡使用");
        back_arrow=findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new MyOnClick());
        tabLayout = findViewById(R.id.tayLayout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(tabLayout, 45, 45);
            }
        });
        card_vp=findViewById(R.id.card_vp);

        //注册
      //  EventBus.getDefault().register(this);
        initVp();
    }

    private void initVp() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(card_vp, false);
        for (int i = 0; i < tabs.length; i++) {

            tabLayout.addTab(tabLayout.newTab());
        }
        newsList.add(new CommonVoucherFragment());
        newsList.add(new MaxVoucherFragment());
        //设置viewpager适配器
        adapter = new OneFmAdapter(getSupportFragmentManager(),newsList);
        card_vp.setAdapter(adapter);
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.getTabAt(i).setText(tabs[i]);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解注册
      //  EventBus.getDefault().unregister(this);
    }


    private class MyOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back_arrow:
                finish();
                break;
            }
        }
    }
}
