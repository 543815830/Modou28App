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
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.MyOfflineFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.PromoteOfflineFragment;
import com.ruixin.administrator.ruixinapplication.utils.IndicatorLineUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * 推广下线界面
 */
public class PromoteOfflineActivity extends FragmentActivity{
    TextView tv_title;//标题
    LinearLayout back_arrow;//返回
    private ViewPager offline_vp;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
    String userId="",userToken="";
    private TabLayout tabLayout;
    private String[]tabs=new String[]{"推广下线","我的下线"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_offline);
        userId=getIntent().getStringExtra("userId");
        userToken=getIntent().getStringExtra("userToken");
        initStatus();
        if(userId.equals("")){
           Toast.makeText(PromoteOfflineActivity.this,"您尚未登录",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(PromoteOfflineActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        }else{
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
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("推广下线");
        back_arrow=findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new MyOnClick());
        tabLayout = findViewById(R.id.tayLayout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(tabLayout, 45, 45);
            }
        });
        offline_vp=findViewById(R.id.offline_vp);
        initVp();
    }
    private void initVp() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(offline_vp, false);
        for (int i = 0; i < tabs.length; i++) {

            tabLayout.addTab(tabLayout.newTab());
        }
        newsList.add(new PromoteOfflineFragment());
        newsList.add(new MyOfflineFragment());
        //设置viewpager适配器
        adapter = new OneFmAdapter(getSupportFragmentManager(),newsList);
        offline_vp.setAdapter(adapter);
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.getTabAt(i).setText(tabs[i]);
        }
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
