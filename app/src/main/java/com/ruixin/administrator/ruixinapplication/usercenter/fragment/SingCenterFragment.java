package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.PromoteEarningsActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.SignState;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.Signed;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.SignCalendar;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/17.
 * 邮箱：543815830@qq.com
 * 签到中心
 */

public class SingCenterFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    LinearLayout ll_sign_center;
    LinearLayout ll_sign;
    LinearLayout ll_sign_days;
    private SignCalendar calendar;
    private String date;
    TextView last_month;
    TextView next_month;
    TextView current_time;
    TextView signed_days;
    TextView award_guize_0;
    TextView award_guize_1;
    TextView award_guize_2;
    TextView award_guize_3;
    TextView award_guize_4;
    TextView award_guize_5;
    TextView award_guize_6;
    List<String> list = new ArrayList<String>();
    private int years;
    private String months;
    private Button btn_sign;
    String userId="",userToken="";
    Boolean isSigned;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    SimpleDateFormat formatter;
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_sign_center,null);
            ll_sign_center=view.findViewById(R.id.ll_sign_center);
            mLoadingAndRetryManager = LoadingAndRetryManager.generate(ll_sign_center, null);
            ll_sign=view.findViewById(R.id.ll_sign);
            ll_sign.setOnClickListener(this);
            ll_sign_days=view.findViewById(R.id.ll_sign_days);
            current_time=view.findViewById(R.id.current_time);
            signed_days=view.findViewById(R.id.signed_days);
            last_month=view.findViewById(R.id.last_month);
           next_month=view.findViewById(R.id.next_month);
            award_guize_0=view.findViewById(R.id.award_guize_0);
            award_guize_1=view.findViewById(R.id.award_guize_1);
            award_guize_2=view.findViewById(R.id.award_guize_2);
            award_guize_3=view.findViewById(R.id.award_guize_3);
            award_guize_4=view.findViewById(R.id.award_guize_4);
            award_guize_5=view.findViewById(R.id.award_guize_5);
            award_guize_6=view.findViewById(R.id.award_guize_6);
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            final Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
            date = formatter.format(curDate);
            current_time.setText(date);//当前日期显示
            calendar = view.findViewById(R.id.sign_main);
            userId=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            userToken=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
            prarams.put("usersid",userId);
            prarams.put("usertoken",userToken);
            new SignSdateAsyncTask().execute();
            //当今日日历点击是签到
            calendar.setOnCalendarClickListener(new SignCalendar.OnCalendarClickListener() {
                @Override
                public void onCalendarClick(int row, int col, String dateFormat) {
                    if(dateFormat.equals(date)){
                        if(isSigned==false){
                       calendar.addMark(dateFormat,0);
                      new SignAsyncTask().execute();
                        }
                    }
                }
            });
            last_month.setOnClickListener(this);
            next_month.setOnClickListener(this);
          /*  btn_sign.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    List<String> list = new ArrayList<String>();
                    list.add("2018-04-10");
                    list.add(date);
                    calendar.addMarks(list, 0);
                }
            });*/
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.last_month:
                calendar.lastMonth();
                Log.e("当前日历时间",""+calendar.getCalendarYear());
                Log.e("当前日历时间",""+calendar.getCalendarMonth());
                current_time.setText(new StringBuilder().append(calendar.getCalendarYear()).append("/").append(calendar.getCalendarMonth()).toString());
                break;
            case R.id.next_month:
                calendar.nextMonth();
               // current_time.setText(formatter.format(calendar.getCalendarMonth()));
                current_time.setText(new StringBuilder().append(calendar.getCalendarYear()).append("/").append(calendar.getCalendarMonth()).toString());
                break;
            case R.id.ll_sign:
                calendar.addMark(date,0);
                new SignAsyncTask().execute();
                ll_sign.setVisibility(View.GONE);
                ll_sign_days.setVisibility(View.VISIBLE);
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SignAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().Sign_URL,prarams);
            Log.e("我的签到的数据返回",""+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null){
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson=new Gson();
                    Signed signed=gson.fromJson(s,Signed.class);
                    if(signed.getStatus()==1) {
                        ll_sign.setVisibility(View.GONE);
                        ll_sign_days.setVisibility(View.VISIBLE);
                       signed_days.setText(new StringBuilder().append("您已连续签到").append(signed.getData().getUser().getLxqd()).append("天，累计获得签到奖励").append(signed.getData().getLjqdhd()).append("金币。").toString());

                        String s1=signed_days.getText().toString();
                        String s2="您已连续签到天，累计获得签到奖励";
                        String s1Tag=signed.getData().getUser().getLxqd();
                        String s2Tag=signed.getData().getLjqdhd();
                        SpannableString mBuilder1 = new SpannableString(s1);
                        //设置字体变色
                        mBuilder1.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), 6, 6+s1Tag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mBuilder1.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), s2.length()+s1Tag.length(), s2.length()+s1Tag.length()+s2Tag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        signed_days.setText(mBuilder1);
                        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("UserInfo",
                                Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_balance", signed.getData().getUser().getPoints()); //账户余额
                            editor.putString("user_suffer", signed.getData().getUser().getMaxexperience());  //用户经验
                        editor.apply();
                        EventBus.getDefault().post(new MessageEvent("3"));
                        }else if (signed.getStatus() ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new SignAsyncTask());
                    }else if(signed.getStatus()==-97||signed.getStatus()==-99){
                            Unlogin.doLogin(mContext);
                    }else{
                        Toast.makeText(mContext, signed.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else{
               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class SignSdateAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }
        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().SignState_URL,prarams);
            Log.e("我的签到中心的数据返回",""+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null){
                mLoadingAndRetryManager.showContent();
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson=new Gson();
                    SignState signState=gson.fromJson(s,SignState.class);
                    if(signState.getStatus()==1) {
                        if(!signState.getData().toString().equals("")){
                          //  signed_days.setText(new StringBuilder().append("您已连续签到").append(signState.getData().getLxqd()).append("天，累计获得签到奖励").append(signState.getData().getLjqdhd()).toString());
                            signed_days.setText(new StringBuilder().append("您已连续签到").append(signState.getData().getLxqd()).append("天，累计获得签到奖励").append(signState.getData().getLjqdhd()).append("金币。").toString());
                            String s1=signed_days.getText().toString();
                            String s2="您已连续签到天，累计获得签到奖励";

                            String s1Tag=signState.getData().getLxqd();
                            String s2Tag=signState.getData().getLjqdhd();
                            SpannableString mBuilder1 = new SpannableString(s1);
                            //设置字体变色
                            mBuilder1.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), 6, 6+s1Tag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mBuilder1.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), s2.length()+s1Tag.length(), s2.length()+s1Tag.length()+s2Tag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            signed_days.setText(mBuilder1);
                            award_guize_0.setText(new StringBuilder().append(signState.getData().getQd().getV0js()).append("+").append(signState.getData().getQd().getV0ts()).append("×天数").toString());
                            award_guize_1.setText(new StringBuilder().append(signState.getData().getQd().getV1js()).append("+").append(signState.getData().getQd().getV1ts()).append("×天数").toString());
                            award_guize_2.setText(new StringBuilder().append(signState.getData().getQd().getV2js()).append("+").append(signState.getData().getQd().getV2ts()).append("×天数").toString());
                            award_guize_3.setText(new StringBuilder().append(signState.getData().getQd().getV3js()).append("+").append(signState.getData().getQd().getV3ts()).append("×天数").toString());
                            award_guize_4.setText(new StringBuilder().append(signState.getData().getQd().getV4js()).append("+").append(signState.getData().getQd().getV4ts()).append("×天数").toString());
                            award_guize_5.setText(new StringBuilder().append(signState.getData().getQd().getV5js()).append("+").append(signState.getData().getQd().getV5ts()).append("×天数").toString());
                            award_guize_6.setText(new StringBuilder().append(signState.getData().getQd().getV6js()).append("+").append(signState.getData().getQd().getV6ts()).append("×天数").toString());
                             for(int i=0;i<signState.getData().getQdarr().size();i++){
                                 list.add(signState.getData().getQdarr().get(i));
                                 calendar.addMarks(list, 0);
                             }
                            if(signState.getData().getTodayqd().equals("1")){
                                isSigned=true;
                                ll_sign.setVisibility(View.GONE);
                                ll_sign_days.setVisibility(View.VISIBLE);
                            }else{
                                isSigned=false;
                                ll_sign.setVisibility(View.VISIBLE);
                                ll_sign_days.setVisibility(View.GONE);
                            }
                        }else{
                           Toast.makeText(mContext, "数据为空", Toast.LENGTH_SHORT).show();
                        }
                    }else if (signState.getStatus() ==99) {
                        Unlogin.doAtk(prarams,s,new SignSdateAsyncTask());
                        /*抗攻击*/
                    }else if(signState.getStatus()==-97||signState.getStatus()==-99){
                        Unlogin.doLogin(mContext);
                    }else{
                        Toast.makeText(mContext, signState.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else{
               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
