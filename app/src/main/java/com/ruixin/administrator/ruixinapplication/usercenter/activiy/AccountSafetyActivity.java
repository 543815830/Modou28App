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
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.ListDataSave;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountSafetyActivity extends Activity {
    Intent intent;
    String userId;
    String userToken;
    String result;
    String userSecques;
    User user;
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sms_version)
    RelativeLayout smsVersion;
    @BindView(R.id.qq_version)
    RelativeLayout qqVersion;
    @BindView(R.id.update_user_pwd)
    RelativeLayout updateUserPwd;
    @BindView(R.id.set_pw_que)
    RelativeLayout setPwQue;
    @BindView(R.id.set_pwd_card)
    RelativeLayout setPwdCard;
    List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safety);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        tvTitle.setText("账户安全");
        list=  ListDataSave.getDataList(AccountSafetyActivity.this,"menu").get(1);
        if(list.contains("手机绑定")){
            smsVersion.setVisibility(View.GONE);
        } if(list.contains("QQ登录")){
            qqVersion.setVisibility(View.GONE);
        } if(list.contains("密码修改")){
            updateUserPwd.setVisibility(View.GONE);
        } if(list.contains("密保设置")){
            setPwQue.setVisibility(View.GONE);
        } if(list.contains("密保卡设置")){
            setPwdCard.setVisibility(View.GONE);
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

    @OnClick({R.id.back_arrow, R.id.sms_version, R.id.qq_version, R.id.update_user_pwd, R.id.set_pw_que, R.id.set_pwd_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.sms_version:
                intent=new Intent(AccountSafetyActivity.this, SmsVersionActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userToken",userToken);
                startActivity(intent);
                break;
            case R.id.qq_version:
                intent=new Intent(AccountSafetyActivity.this, QqVersionActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userToken",userToken);
                startActivity(intent);
                break;
            case R.id.update_user_pwd:
                intent=new Intent(AccountSafetyActivity.this,UpdatePasswordActivity.class);
                userSecques=getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_secques","");
                intent.putExtra("userSecques",userSecques);
                intent.putExtra("userToken",userToken);
                intent.putExtra("userId",userId);
                startActivity(intent);
                break;
            case R.id.set_pw_que:
                intent=new Intent(AccountSafetyActivity.this, SetPwdActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userToken",userToken);
                startActivity(intent);
                break;
            case R.id.set_pwd_card:
                intent=new Intent(AccountSafetyActivity.this, SetPwdCardActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userToken",userToken);
                String is_card=getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_iscard","");
                Log.e("is_card", "" + is_card);
                intent.putExtra("is_card",is_card);
                startActivity(intent);
                break;
        }
    }
}
