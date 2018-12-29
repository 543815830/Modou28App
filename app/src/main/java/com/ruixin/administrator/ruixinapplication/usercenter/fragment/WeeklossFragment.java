package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.LRebate;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by 李丽 on 2018/12/13.
 * 周亏损
 */

public class WeeklossFragment extends BaseFragment {
    private View view;
    TextView loss_total_coin;
    TextView week_total_coin;
    TextView shengyu_loss_coin;
    TextView today_total_coin;
    TextView loss_fanli_lilv;
    TextView tv_moren;
    TextView tv_0_1;
    TextView tv_1_2;
    TextView tv_2_3;
    TextView tv_3_4;
    TextView tv_4_5;
    LinearLayout ll_fanli;
    Button commit;
    String userId,userToken;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    int shengyu;
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_week_loss,null);
            loss_total_coin=view.findViewById(R.id.loss_total_coin);
            week_total_coin=view.findViewById(R.id.week_total_coin);
            shengyu_loss_coin=view.findViewById(R.id.shengyu_loss_coin);
            today_total_coin=view.findViewById(R.id.today_total_coin);
            loss_fanli_lilv=view.findViewById(R.id.loss_fanli_lilv);
            commit=view.findViewById(R.id.commit);
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    new GetRebateAsyncTask1().execute();
                }
            });
            ll_fanli=view.findViewById(R.id.ll_fanli);
            tv_moren=view.findViewById(R.id.tv_moren);
            tv_0_1=view.findViewById(R.id.tv_0_1);
            tv_1_2=view.findViewById(R.id.tv_1_2);
            tv_2_3=view.findViewById(R.id.tv_2_3);
            tv_3_4=view.findViewById(R.id.tv_3_4);
            tv_4_5=view.findViewById(R.id.tv_4_5);
            userId=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            userToken=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            new RebateAsyncTask().execute();
        }
        return view;
    }
    private class RebateAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().LRebate_URL, prarams);
            Log.e("亏损返利的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    LRebate lrebate = gson.fromJson(s, LRebate.class);
                    if (lrebate.getStatus() == 1) {
                        Log.e("kuisun返利的数据返回", "" + s);
                        loss_total_coin.setText(""+lrebate.getData().getQryl());//上周亏损
                        week_total_coin.setText(""+Math.round(Float.parseFloat(lrebate.getData().getQrzfl())));//本周共可领取
                        shengyu_loss_coin.setText(""+lrebate.getData().getSypoints());//剩余
                        today_total_coin.setText(""+lrebate.getData().getQrfl());//今天
                        shengyu=Integer.parseInt(lrebate.getData().getSypoints())-Integer.parseInt(lrebate.getData().getQrfl());
                        tv_moren.setText(lrebate.getData().getSystem().getQrfl()+"%");
                        tv_0_1.setText(lrebate.getData().getGroup().getWeek().get(0).getPercent()+"%");
                        tv_1_2.setText(lrebate.getData().getGroup().getWeek().get(1).getPercent()+"%");
                        tv_2_3.setText(lrebate.getData().getGroup().getWeek().get(2).getPercent()+"%");
                        tv_3_4.setText(lrebate.getData().getGroup().getWeek().get(3).getPercent()+"%");
                        tv_4_5.setText(lrebate.getData().getGroup().getWeek().get(4).getPercent()+"%");
                        loss_fanli_lilv.setText(new StringBuilder().append("        注：28网站每周七日亏损返利系统，您可在这领取您上周亏损金额的，").append(lrebate.getData().getQrflbl()).append("%上周亏损返利按7天天平均领取。请及时每天领取，逾期将无法领取。").toString());
if(lrebate.getData().getQrfled()!=null&&lrebate.getData().getQrfled().equals("1")){
    today_total_coin.setText("今天已经领取！请明天再来！");
    today_total_coin.setTextSize(14f);
    today_total_coin.setTextColor(Color.parseColor("#ff0000"));
    commit.setText("已领取");
    commit.setEnabled(false);
}

                    } else if (lrebate.getStatus() == 99) {

                        Unlogin.doAtk(prarams, s, new RebateAsyncTask());
                    } else if (lrebate.getStatus() <= 0) {
                        Toast.makeText(mContext, "获取信息失败", Toast.LENGTH_SHORT).show();
                    } else if (lrebate.getStatus() == -99 || lrebate.getStatus() == -97) {
                        Unlogin.doLogin(mContext);
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetRebateAsyncTask1 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().LwRebate_URL, prarams);
            Log.e("下线亏损领取的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        today_total_coin.setText("今天已经领取！请明天再来！");
                        today_total_coin.setTextSize(14f);
                        today_total_coin.setTextColor(Color.parseColor("#ff0000"));
                        shengyu_loss_coin.setText(""+shengyu);
                        commit.setText("已领取");
                        commit.setEnabled(false);
                        EventBus.getDefault().post(new MessageEvent("3"));
                    } else if (entry.getStatus() == 99) {
                        Unlogin.doAtk(prarams,s,new GetRebateAsyncTask1());
                    }  else if (entry.getStatus() == -99 || entry.getStatus() == -97) {
                        Unlogin.doLogin(mContext);
                    }else if (entry.getStatus() <= 0) {
                        Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
