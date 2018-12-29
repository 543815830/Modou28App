package com.ruixin.administrator.ruixinapplication.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.LuckyWheelActivity;
import com.ruixin.administrator.ruixinapplication.home.databean.HomeDb;
import com.ruixin.administrator.ruixinapplication.home.databean.JumpMethod;
import com.ruixin.administrator.ruixinapplication.home.databean.LMessageEvent;
import com.ruixin.administrator.ruixinapplication.home.fragment.HPrizeFragment;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.home.fragment.HActSpeFragment;
import com.ruixin.administrator.ruixinapplication.home.fragment.HAdEFragment;
import com.ruixin.administrator.ruixinapplication.home.fragment.HHomeFragment;
import com.ruixin.administrator.ruixinapplication.home.fragment.HPressReleaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 首页界面
 */

public class HomeFragment extends BaseFragment{
    private Banner banner;
    private ViewPager home_vp;
    private List<String> imageArray;
    private View view;
    private List<Fragment> newsList = new ArrayList<Fragment>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private OneFmAdapter adapter;
    List<HomeDb.DataBean.ImgBean> imgBeanList = new ArrayList<>();
    private TabLayout tabLayout;
private String[]tabs=new String[]{"首页","广告体验","奖品兑换","活动专场","新闻公告"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            //初始化view
            view = View.inflate(mContext, R.layout.fragment_home, null);
            banner = view.findViewById(R.id.banner);
            tabLayout = view.findViewById(R.id.tayLayout);
            home_vp = view.findViewById(R.id.home_view);
            //初始化viewpager
            initView();
        }
        /*
         * 底部导航栏切换后 由于没有销毁顶部设置导致如果没有重新设置view
         * 导致底部切换后切回顶部页面数据会消失等bug
                * 以下设置每次重新创建view即可
                */
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    @Override
    protected View initView() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(home_vp,false);
        for (int i = 0; i <tabs.length; i++) {

            tabLayout.addTab(tabLayout.newTab());
        }
        newsList.add(new HHomeFragment());
        newsList.add(new HAdEFragment());
        newsList.add(new HPrizeFragment());
        newsList.add(new HActSpeFragment());
        newsList.add(new HPressReleaseFragment());
        adapter = new OneFmAdapter(getActivity().getSupportFragmentManager(), newsList);
        home_vp.setAdapter(adapter);
        home_vp.setOffscreenPageLimit(tabs.length);
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.getTabAt(i).setText(tabs[i]);
        }
        return view;
    }


    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(LMessageEvent enevnt) {

        imgBeanList = enevnt.imgBeanList;
        initBanner();

    }

    /*设置banner数据*/
    private void initBanner() {
        Log.e("tag", "initBanner()");
        if (imgBeanList != null && imgBeanList.size() > 0) {
            imageArray = new ArrayList<>();
            for (int i = 0; i < imgBeanList.size(); i++) {
                String path = RuiXinApplication.getInstance().getUrl() + imgBeanList.get(i).getImgname();
                imageArray.add(path);
                Log.e("tag", "imageArray" + imageArray);

            }
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(imageArray);
            //设置banner动画效果
            //banner.setBannerAnimation(Transformer.);
        /*//设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(imageTitle);*/
            //设置轮播时间
            banner.setDelayTime(1500);

            //banner设置方法全部调用完毕时最后调用
            banner.start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    JumpMethod.insideJump(imgBeanList.get(position).getImgsrc(), mContext);
                }
            });
        }
    }

private class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Log.e("tag", "displayImage()");
        //Glide 加载图片简单用法
        Glide.with(context)
                .load((String) path)
                //  .placeholder(R.drawable.banner) //占位图
                //  .error(_round)  //出错的占位图
                .dontAnimate()
                .into(imageView);
    }

}

    @SuppressLint("ResourceAsColor")
    protected void initStatus() {
        //Toast.makeText(mContext, "initStatus1", Toast.LENGTH_SHORT).show();
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION//隐藏虚拟按键(导航栏)
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            // window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);// SDK21
            //  window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //   window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        try {
            super.onViewStateRestored(savedInstanceState);
        } catch (Exception e) {
            savedInstanceState = null;
        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            //  initStatus();
        }
    }
}
