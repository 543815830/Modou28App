package com.ruixin.administrator.ruixinapplication;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mob.MobSDK;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GameName1;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MoneyDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.utils.CrashHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/22.
 * 邮箱：543815830@qq.com
 * 总应用程序
 */

public class RuiXinApplication extends Application {
    public static RuiXinApplication instance;
    private RuiXinApplication OkHttpUtil;
    public static Typeface TypeFaceLanTing;
    public  String UserId;
    public  String UserToken;
   public List<GameName1.DataBean.GamelistBean> list;
   public List<GameName1.DataBean.HotgamelistBean> list1;
   public List<GameName1.DataBean.LatesgamelistBean> list2;
    public List <MoneyDb>moneyList;
    public List <Integer>mybetPoints;
    public  String Url;
    public static Context BASECONTEXT;
    private List<Activity> oList;//用于存放所有启动的Activity的集合

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("RuiXinApplication","onCreate");
        // instance= (RuiXinApplication) getApplicationContext();
         /*loading初始化*/
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
        /*全局设置字体样式*/
        TypeFaceLanTing = Typeface.createFromAsset(getAssets(), "font/LanTing.TTF");
        Field field = null;
        try {
            field = Typeface.class.getDeclaredField("MONOSPACE");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        try {
            field.set(null, TypeFaceLanTing);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BASECONTEXT=getBaseContext();
        long currentTime=System.currentTimeMillis();
        Log.e("tag----------c-------",""+currentTime);
        // CrashHandler.getInstance().init(getApplicationContext());
        MobSDK.init(this);//分享初始haul
        oList = new ArrayList<Activity>();
       // UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"");
    }

    public static RuiXinApplication getInstance() {
        if (instance == null) {
            instance = new RuiXinApplication();
        }
        return instance;
    }
    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String UserToken) {
        this.UserToken = UserToken;
    }
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }
    public String getUrl() {
        return "http://"+Url+"/";
    }

    public void setUrl(String Url) {
        Log.e("setUrl","setUrl");
        this.Url = Url;
    }
    public List<GameName1.DataBean.GamelistBean> getList() {
        return list;
    }
    public List<MoneyDb> getMoneyList() {
        return moneyList;
    }
    public List<Integer> getMybetPoints() {
        return mybetPoints;
    }

    public void setList(List<GameName1.DataBean.GamelistBean> list) {
        this.list = list;
    }
    public void setMoneyList(List<MoneyDb> moneyList) {
        Log.e("setMoneyList","setMoneyList");
        this.moneyList = moneyList;
    }
    public void setMybetPoints(List<Integer> mybetPoints) {
        this.mybetPoints = mybetPoints;
    }
    public List<GameName1.DataBean.HotgamelistBean> getList1() {
        return list1;
    }

    public void setList1(List<GameName1.DataBean.HotgamelistBean> list1) {
        this.list1= list1;
    }
    public List<GameName1.DataBean.LatesgamelistBean> getList2() {
        return list2;
    }

    public void setList2(List<GameName1.DataBean.LatesgamelistBean> list2) {
        this.list2= list2;
    }


    public void addActivity_(Activity activity) {
// 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
//判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        Log.e("removeALLActivity_", "removeALLActivity_=---------" );
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            Log.e("removeALLActivity_", "removeALLActivity_=---------" );
            activity.finish();
        }
    }
}
