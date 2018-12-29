package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

public class PromoteActivity extends Activity {
    Intent intent;
    String userId;
    String userToken;
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_promote_earnings)
    RelativeLayout rlPromoteEarnings;
    @BindView(R.id.rl_promote_offline)
    RelativeLayout rlPromoteOffline;
    List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        tvTitle.setText("推广下线");
        list=  ListDataSave.getDataList(PromoteActivity.this,"menu").get(2);
        Log.e("tag",""+list.size());
        if(list.contains("推广收益")){
            rlPromoteEarnings.setVisibility(View.GONE);
        } if(list.contains("推广下线")){
            rlPromoteOffline.setVisibility(View.GONE);
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

    @OnClick({R.id.back_arrow, R.id.rl_promote_earnings, R.id.rl_promote_offline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.rl_promote_earnings:
                intent = new Intent(PromoteActivity.this, PromoteEarningsActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
            case R.id.rl_promote_offline:
                intent = new Intent(PromoteActivity.this, PromoteOfflineActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
        }
    }
}
