package com.ruixin.administrator.ruixinapplication.home.databean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.LuckyWheelActivity;
import com.ruixin.administrator.ruixinapplication.home.webview.BannerWebActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;

/**
 * Created by 李丽 on 2018/9/7.
 */

public class JumpMethod {


   public static void insideJump(String src,Context context) {
       String  userId = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
       String  userToken = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
       if (userId == null || userToken == null||userId.equals("")||userToken.equals("")) {
           Intent intent = new Intent(context, LoginActivity.class);
           intent.putExtra("where", "3");
           context.startActivity(intent);
       }else{
           if(src.equals("inside:round")){
               context. startActivity(new Intent(context, LuckyWheelActivity.class));
           }else{
               Intent intent=new Intent(context,BannerWebActivity.class);
               intent.putExtra("src",src);
               context. startActivity(intent);
           }
       }

    }
}
