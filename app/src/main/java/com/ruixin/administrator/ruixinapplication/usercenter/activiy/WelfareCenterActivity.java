package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.utils.ListDataSave;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 作者：Created by ${李丽} on 2018/3/16.
 * 邮箱：543815830@qq.com
 * 福利中心的界面
 */
public class WelfareCenterActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_advance_reward)
    RelativeLayout rlAdvanceReward;
    @BindView(R.id.rl_sign_desk)
    RelativeLayout rlSignDesk;
    @BindView(R.id.rl_loss_rebate)
    RelativeLayout rlLossRebate;
    @BindView(R.id.rl_first_rebate)
    RelativeLayout rlFirstRebate;
    @BindView(R.id.rl_get_salary)
    RelativeLayout rlGetSalary;
    @BindView(R.id.rl_get_relief)
    RelativeLayout rlGetRelief;
    Intent intent;
    String userId;
    String userToken;
    List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_center);
        ButterKnife.bind(this);
        userId=getIntent().getStringExtra("userId");
        userToken=getIntent().getStringExtra("userToken");
        tvTitle.setText("福利中心");
        list=  ListDataSave.getDataList(WelfareCenterActivity.this,"menu").get(3);
        if(list.contains("闯关奖励")){
            rlAdvanceReward.setVisibility(View.GONE);
        } if(list.contains("签到中心")){
            rlSignDesk.setVisibility(View.GONE);
        } if(list.contains("亏损返利")){
            rlLossRebate.setVisibility(View.GONE);
        } if(list.contains("首充返利")){
            rlFirstRebate.setVisibility(View.GONE);
        } if(list.contains("工资领取")){
            rlGetSalary.setVisibility(View.GONE);
        } if(list.contains("救济领取")){
            rlGetRelief.setVisibility(View.GONE);
        }
        initStatus();
    }
    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick({R.id.back_arrow, R.id.rl_advance_reward, R.id.rl_sign_desk, R.id.rl_loss_rebate, R.id.rl_first_rebate, R.id.rl_get_salary, R.id.rl_get_relief})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.rl_advance_reward:
                intent = new Intent(WelfareCenterActivity.this, AdvanceActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
            case R.id.rl_sign_desk:
                intent = new Intent(WelfareCenterActivity.this, SignDeskActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
            case R.id.rl_loss_rebate:
                intent = new Intent(WelfareCenterActivity.this, LossRebateActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
            case R.id.rl_first_rebate:
                intent = new Intent(WelfareCenterActivity.this, FirstRebateActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
            case R.id.rl_get_salary:
                intent = new Intent(WelfareCenterActivity.this, GetSalaryActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
            case R.id.rl_get_relief:
                intent = new Intent(WelfareCenterActivity.this, GetReliefActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
        }
    }
}
