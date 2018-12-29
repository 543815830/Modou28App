package com.ruixin.administrator.ruixinapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import android.widget.Toast;

import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;


/**
 * Created by 李丽 on 2018/9/4.
 */

public class ShowToast {
    public  void ShowToast(int status,Context context){
        if(status==-97||status==-99){
           Toast.makeText(context, "账号已经过期请重新登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context,LoginActivity.class);
            intent.putExtra("where","2");
           context.startActivity(intent);
        }
    }
}
