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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.DatelossFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.OfflinelossFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.WeeklossFragment;
import com.ruixin.administrator.ruixinapplication.utils.IndicatorLineUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * 亏损返利界面
 */
public class LossRebateActivity extends FragmentActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tayLayout)
    TabLayout tayLayout;
    @BindView(R.id.loss_vp)
    ViewPager lossVp;
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
    private String[] tabs = new String[]{"日亏损返利", "周亏损返利", "下线亏损返利"};
    private HashMap<String, String> prarams = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);
        initStatus();
        ButterKnife.bind(this);
        tvTitle.setText("亏损返利");
        tayLayout.setTabMode(TabLayout.MODE_FIXED);
        tayLayout.setupWithViewPager(lossVp, false);
        for (int i = 0; i < tabs.length; i++) {
            tayLayout.addTab(tayLayout.newTab());
        }
        tayLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(tayLayout, 15, 15);
            }
        });
        newsList.add(new DatelossFragment());
        newsList.add(new WeeklossFragment());
        newsList.add(new OfflinelossFragment());

        //设置viewpager适配器
        adapter = new OneFmAdapter(getSupportFragmentManager(), newsList);
        lossVp.setAdapter(adapter);
        lossVp.setOffscreenPageLimit(2);
        for (int i = 0; i < tabs.length; i++) {
            tayLayout.getTabAt(i).setText(tabs[i]);
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

    @OnClick(R.id.back_arrow)
    public void onViewClicked() {
        finish();
    }

}
