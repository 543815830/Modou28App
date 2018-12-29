package com.ruixin.administrator.ruixinapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by 李丽 on 2018/11/23.
 */

public class Unlogin {
    public static void doLogin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("is_login", "false");
        editor.commit();
        EventBus.getDefault().post(new MessageEvent("7"));
        Toast.makeText(context, "账号已经过期请重新登录", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(context,LoginActivity.class);
        intent.putExtra("where","2");
       context. startActivity(intent);
    }
    public static void doAtk(HashMap<String, String> prarams, String s, AsyncTask<String, Integer, String> asyncTask){
        Gson gson=new Gson();
         /*抗攻击*/
        ATK atK = gson.fromJson(s, ATK.class);
        String vaild_str = atK.getVaild_str();
        Log.e("tag", "" + vaild_str);
        String vaildd_md5 = FormatUtils.md5(vaild_str);
        Log.e("tag", "" + vaildd_md5);
        prarams.put("vaild_str", vaildd_md5);
        asyncTask.execute();
    }


}
