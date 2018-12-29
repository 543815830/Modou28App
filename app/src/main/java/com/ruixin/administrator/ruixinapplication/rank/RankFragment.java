package com.ruixin.administrator.ruixinapplication.rank;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.rank.fragment.DateRankFragment;
import com.ruixin.administrator.ruixinapplication.rank.fragment.MonthRankFragment;
import com.ruixin.administrator.ruixinapplication.rank.fragment.WeekRankFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.OnlinePayActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 排行榜的界面
 */

public class RankFragment extends BaseFragment {
    private View view;
    private TextView tvRank;
    private ViewPager rank_vp;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
    int height = 0;
    private TabLayout tabLayout;
    private String[] tabs = new String[]{"今日排行", "昨日排行", "本周排行"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            //初始化view
            view = View.inflate(mContext, R.layout.fragment_rank, null);
            tvRank = view.findViewById(R.id.tv_rank);
            getStatusBarHeight();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvRank.getLayoutParams();
            layoutParams.setMargins(0, height, 0, 0);
            tvRank.setLayoutParams(layoutParams);
            tabLayout = view.findViewById(R.id.tayLayout);
            rank_vp = view.findViewById(R.id.rank_vp);
            initView();
        }
        return view;

    }

    private void getStatusBarHeight() {

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("tag", height + "");

    }

    @SuppressLint("ResourceAsColor")
    protected void initStatus() {
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_VISIBLE);//隐藏虚拟按键(导航栏));
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }

    }

    @Override
    protected View initView() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(rank_vp, false);
        for (int i = 0; i < tabs.length; i++) {

            tabLayout.addTab(tabLayout.newTab());
        }
        newsList.add(new DateRankFragment());
        newsList.add(new WeekRankFragment());
        newsList.add(new MonthRankFragment());

        //设置viewpager适配器
        adapter = new OneFmAdapter(getActivity().getSupportFragmentManager(), newsList);
        rank_vp.setAdapter(adapter);
        rank_vp.setOffscreenPageLimit(2);
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.getTabAt(i).setText(tabs[i]);
        }
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            //  initStatus();
        }
    }
}
