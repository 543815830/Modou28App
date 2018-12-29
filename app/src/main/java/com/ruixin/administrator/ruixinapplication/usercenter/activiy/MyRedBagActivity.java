package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.CommonVoucherFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.DepositFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.FetchFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.MaxVoucherFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.MyredbagrecordFragment;
import com.ruixin.administrator.ruixinapplication.utils.IndicatorLineUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 我的红包界面
 */
public class MyRedBagActivity extends FragmentActivity{
    String userId;
    String userToken;
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.my_red_bag_vp)
    ViewPager myRedBagVp;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
    MyredbagrecordFragment mrbFragment;
    private TabLayout tabLayout;
    private String[]tabs=new String[]{"我发出的","我收到的"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_red_bag);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userid");
        userToken = getIntent().getStringExtra("usertoken");
        initStatus();
        initView();
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
        tvTitle.setText("我的红包");
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tabLayout = findViewById(R.id.tayLayout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(tabLayout, 45, 45);
            }
        });
        initVp();
    }
    private void initVp() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(myRedBagVp, false);
        for (int i = 0; i < tabs.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("type",""+i);
            bundle.putString("userid",userId);
            bundle.putString("usertoken",userToken);
            mrbFragment=new MyredbagrecordFragment();
            mrbFragment.setArguments(bundle);
            newsList.add(mrbFragment);
            tabLayout.addTab(tabLayout.newTab());
        }
        //设置viewpager适配器
        adapter = new OneFmAdapter(getSupportFragmentManager(), newsList);
        myRedBagVp.setAdapter(adapter);
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.getTabAt(i).setText(tabs[i]);
        }
    }
    }


