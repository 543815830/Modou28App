package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.LRebate;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by 李丽 on 2018/12/13.
 * 日亏损
 */

public class DatelossFragment extends BaseFragment {
    private View view;
    TextView day_loss_fanli;
    TextView day_fanli_lilv;
    Button commit1;
    String userId,userToken;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_date_loss,null);
            day_loss_fanli=view.findViewById(R.id.day_loss_fanli);
            commit1=view.findViewById(R.id.commit1);
            commit1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    new GetRebateAsyncTask1().execute();
                }
            });
            day_fanli_lilv=view.findViewById(R.id.day_fanli_lilv);
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
                        if (lrebate.getData().getIsgeted().equals("0")) {
                            day_loss_fanli.setText(lrebate.getData().getFanli());
                            day_fanli_lilv.setText(new StringBuilder().append("        注：28网站最新亏损返利系统，您可在此领取您昨日亏损金额的").append(lrebate.getData().getKsflbl()).append("%。请及时领取，逾期将无法领取。").toString());
                        } else {
                            day_loss_fanli.setText("您昨日亏损返利已经领取！请明天再来！");
                            day_loss_fanli.setTextSize(14f);
                            day_loss_fanli.setTextColor(Color.parseColor("#ff0000"));
                            day_fanli_lilv.setText(new StringBuilder().append("        注：28网站最新亏损返利系统，您可在此领取您昨日亏损金额的").append(lrebate.getData().getKsflbl()).append("%。请及时领取，逾期将无法领取。").toString());
                            commit1.setText("已领取");
                            commit1.setEnabled(false);
                            commit1.setClickable(false);
                        }


                    } else if (lrebate.getStatus() == 99) {

                        Unlogin.doAtk(prarams, s, new RebateAsyncTask());
                    } else if (lrebate.getStatus() == -99 || lrebate.getStatus() == -97) {
                        Unlogin.doLogin(mContext);
                    }else {
                        Toast.makeText(mContext, "获取信息失败", Toast.LENGTH_SHORT).show();
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
            String result = AgentApi.dopost3(URL.getInstance().LdRebate_URL, prarams);
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
                        day_loss_fanli.setText("您昨日亏损返利已经领取！请明天再来！");
                        day_loss_fanli.setTextSize(14f);
                        day_loss_fanli.setTextColor(Color.parseColor("#ff0000"));
                        commit1.setText("已领取");
                        commit1.setEnabled(false);
                        commit1.setClickable(false);
                        EventBus.getDefault().post(new MessageEvent("3"));
                    } else if (entry.getStatus() == 99) {
                        Unlogin.doAtk(prarams,s,new GetRebateAsyncTask1());
                    }  else if (entry.getStatus() == -99 || entry.getStatus() == -97) {
                        Unlogin.doLogin(mContext);
                    }else  {
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
