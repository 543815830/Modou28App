package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.webview.PayWebview;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * 微信充值
 */

public class WechatVoucherFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    EditText coins;
    String usersid = "", money = "", payway = "wx",userToken="";
    Button commit;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_alipay_voucher,null);
            coins = view.findViewById(R.id.coins);
            commit = view.findViewById(R.id.commit);
            commit.setOnClickListener(this);
        }
        return view;
    }
    @Override
    public void onClick(View view) {
        usersid = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
        userToken = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
        money = coins.getText().toString();
        if(isValid()){
        Intent intent;
        intent=new Intent(mContext,PayWebview.class);
        intent.putExtra("usersid",usersid);
        intent.putExtra("money",money);
        intent.putExtra("payway",payway);
        intent.putExtra("userToken",userToken);
        startActivity(intent);
        EventBus.getDefault().post(new MessageEvent("3"));
        }
    }
    public boolean isValid() {
        int coin= Integer.parseInt(money);
        if (coin<10) {
            Toast.makeText(mContext, "输入金额最低10元!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (coin>50000) {
            Toast.makeText(mContext, "输入金额最高50000元!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
