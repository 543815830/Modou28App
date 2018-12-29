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
 */

public class OfflinelossFragment extends BaseFragment {
    private View view;
    TextView loss_total_coin;
    TextView fanli_loss;
    TextView loss_fanli_lilv;
    Button commit;
    String userId,userToken;
    User user;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_offline_loss,null);
            loss_total_coin=view.findViewById(R.id.loss_total_coin);
            fanli_loss=view.findViewById(R.id.fanli_loss);
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

                        if (lrebate.getData().getIsxxgeted().equals("0")) {
                            loss_total_coin.setText(""+lrebate.getData().getSumxxks());
                            fanli_loss.setText(""+lrebate.getData().getSumxxksfl());
                            loss_fanli_lilv.setText(new StringBuilder().append("        注：28网站最新推出下线亏损返利系统，通过您的下线连接注册的用户，当天亏损后，您可以于第二天领取亏损额的").append(lrebate.getData().getXxksbl()).append("%的返利。请及时领取，逾期将无法领取。").toString());
                        } else {
                            loss_total_coin.setText(""+lrebate.getData().getSumxxks());
                            fanli_loss.setText("您昨日亏损返利已经领取！请明天再来！");
                            fanli_loss.setTextSize(14f);
                            fanli_loss.setTextColor(Color.parseColor("#ff0000"));
                            loss_fanli_lilv.setText(new StringBuilder().append("        注：28网站最新推出下线亏损返利系统，通过您的下线连接注册的用户，当天亏损后，您可以于第二天领取亏损额的").append(lrebate.getData().getXxksbl()).append("%的返利。请及时领取，逾期将无法领取。").toString());
                            commit.setText("已领取");
                            commit.setEnabled(false);
                        }


                    } else if (lrebate.getStatus() == 99) {

                        Unlogin.doAtk(prarams, s, new RebateAsyncTask());
                    }  else if (lrebate.getStatus() == -99 || lrebate.getStatus() == -97) {
                        Unlogin.doLogin(mContext);
                    }else  {
                        Toast.makeText(mContext, lrebate.getMsg(), Toast.LENGTH_SHORT).show();
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
            String result = AgentApi.dopost3(URL.getInstance().LxRebate_URL, prarams);
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
                        user = gson.fromJson(s, User.class);
                        fanli_loss.setText("您昨日亏损返利已经领取！请明天再来！");
                        fanli_loss.setTextSize(14f);
                        fanli_loss.setTextColor(Color.parseColor("#ff0000"));
                        commit.setText("已领取");
                        commit.setEnabled(false);
                        EventBus.getDefault().post(new MessageEvent("3"));
                       // finish();
                        // saveParameter();
                    } else if (entry.getStatus() == 99) {
                       Unlogin.doAtk(prarams,s,new GetRebateAsyncTask1());
                    } else if (entry.getStatus() == -99 || entry.getStatus() == -97) {
                        Unlogin.doLogin(mContext);
                    }else {
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
