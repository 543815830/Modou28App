package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.AgencyVoucherFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.AlipayVoucherFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.WechatVoucherFragment;
import com.ruixin.administrator.ruixinapplication.utils.IndicatorLineUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 站内银行界面
 */
public class OnlinePayActivity extends FragmentActivity {
    TextView tv_title;//标题
    LinearLayout back_arrow;//返回
    private ViewPager payment_vp;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
    String userId, userToken;
    public static int RESULT_CODE = 50;
    private TabLayout tabLayout;
    private String[] tabs = new String[]{"支付宝", "微信", "代理充值"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pay);
        initStatus();
        userId = getIntent().getStringExtra("userId");
        userId = getIntent().getStringExtra("userToken");
        if (userId.equals("")) {
            Toast.makeText(OnlinePayActivity.this, "您尚未登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OnlinePayActivity.this, LoginActivity.class);
            intent.putExtra("where", "2");
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
        tv_title.setText("在线充值");
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new MyOnClick());
        tabLayout = findViewById(R.id.tayLayout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(tabLayout, 30, 30);
            }
        });
        payment_vp = findViewById(R.id.payment_vp);
        initVp();
    }

    private void initVp() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(payment_vp, false);
        for (int i = 0; i < tabs.length; i++) {

            tabLayout.addTab(tabLayout.newTab());
        }
        newsList.add(new AlipayVoucherFragment());
        newsList.add(new WechatVoucherFragment());
        newsList.add(new AgencyVoucherFragment());
        //设置viewpager适配器
        adapter = new OneFmAdapter(getSupportFragmentManager(), newsList);
        payment_vp.setAdapter(adapter);
        payment_vp.setOffscreenPageLimit(tabs.length);
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
