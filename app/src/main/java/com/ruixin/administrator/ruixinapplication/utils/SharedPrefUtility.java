package com.ruixin.administrator.ruixinapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;

/**
 * 作者：Created by ${李丽} on 2018/5/9.
 * 邮箱：543815830@qq.com
 *   保存用户信息
 */
public class SharedPrefUtility {
    public static void saveData(Context context, User user) {
        Log.e("saveData","saveData");
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("is_login", "true");
        if(user.getData().getId()!=null)  {
            editor.putString("user_tv_id", user.getData().getId());  //用户id
        }else{
            editor.putString("user_tv_id", "");
        }
            editor.putString("user_loginsj", ""+user.getData().getLogin_sj());//手機短信驗證
            editor.putString("user_qcallow", ""+user.getData().getQc_allow());//手機短信驗證
            editor.putString("user_qcclose", ""+user.getData().getQc_close());//手機短信驗證
            editor.putString("user_msgnum", ""+user.getData().getMsgnum());
            editor.putString("user_notnum", ""+user.getData().getNotenum());

        if(user.getData().getUsertoken()!=null)  {
            editor.putString("user_token", user.getData().getUsertoken());  //用户token
        }else{
            editor.putString("user_token", "");
        }
        if(user.getData().getName()!=null)  {
            editor.putString("user_name",user.getData().getName()); //用户昵称
        }else{
            editor.putString("user_name", "");
        }
        if(user.getData().getHead()!=null)  {
            editor.putString("user_head", user.getData().getHead()); //用户头像地址
        }else{
            editor.putString("user_head", "");
        }
        if(user.getData().getIs_bdCard()!=null)  {
            editor.putString("user_iscard", user.getData().getIs_bdCard()); //用户是否绑定密保卡
        }else{
            editor.putString("user_iscard", "");
        }
        if(user.getData().getPoints()!=null)  {
            editor.putString("user_balance", user.getData().getPoints()); //账户余额
        }else{
            editor.putString("user_balance", "");
        }
        if(user.getData().getBack()!=null)  {
            editor.putString("user_coin", user.getData().getBack());   //银行金币
        }else{
            editor.putString("user_coin", "");
        }
        if(user.getData().getExperience()!=null)  {
            editor.putString("user_total", user.getData().getExperience());  //用户积分
            Log.e("tag",""+ user.getData().getExperience());
        }else{
            editor.putString("user_total", "");
        }
        if(user.getData().getEggjf()!=null)  {
            editor.putString("user_eggjf", user.getData().getEggjf());  //消耗积分
        }else{
            editor.putString("user_eggjf", "");
        }
        if(user.getData().getMaxexperience()!=null)  {
            editor.putString("user_suffer", user.getData().getMaxexperience());  //用户经验
        }else{
            editor.putString("user_suffer", "");
        }
        if(user.getData().getSecques()!=null)  {
            editor.putString("user_secques", user.getData().getSecques());  //用户密保问题
        }else{
            editor.putString("user_secques", "");
        }
        if(user.getData().getQq()!=null)  {
            editor.putString("qq",user.getData().getQq().toString() );  //用户qq
        }else{
            editor.putString("qq", "");
        }
        if(user.getData().getSex()!=null)  {
            editor.putString("user_sex", user.getData().getSex() );   //用户性别
        }else{
            editor.putString("user_sex", "");
        }
        if(user.getData().getBirthday()!=null)  {
            editor.putString("user_birthday", user.getData().getBirthday().toString());//用户生日
        }else{
            editor.putString("user_birthday", "");
        }
        if(user.getData().getSjNum()!=null)  {
            editor.putString("user_phone", user.getData().getSjNum() );//用户手机号
        }else{
            editor.putString("user_phone", "");
        }
        if(user.getData().getEmail()!=null)  {
            editor.putString("user_mail", user.getData().getEmail() );//用户邮箱
        }else{
            editor.putString("user_mail", "");
        }
        if(user.getData().getAlipay()!=null)  {
            editor.putString("user_alipy", user.getData().getAlipay().toString() );//用户支付宝账号
        }else{
            editor.putString("user_alipy", "");
        }
        editor.commit();
    }

    public static void removeData(Context context){

        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
