package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * 作者：Created by ${李丽} on 2018/4/13.
 * 邮箱：543815830@qq.com
 * 大量充值
 */

public class MaxVoucherFragment extends BaseFragment {
    private View view;
    EditText et_max_card;
    Button commit;
    User user;
    String userId="", pilcard="",userToken="";
    private HashMap<String, String> prarams = new HashMap<String, String>();
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_max_voucher,null);
            et_max_card=view.findViewById(R.id.et_max_card);
            userId=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            userToken=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
            commit=view.findViewById(R.id.commit);
            commit.setOnClickListener(new MyOnclick());
        }
        return view;
    }

    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            pilcard=et_max_card.getText().toString();
            prarams.put("usersid",userId);
            prarams.put("usertoken",userToken);
            prarams.put("pilcard",pilcard);
            new UseCardAsyncTask().execute();
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class UseCardAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().MaxCard_URL,prarams);
            Log.e("批量点卡的数据返回",""+result);
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
                    user=gson.fromJson(s,User.class);
                    if(user.getStatus()==1) {
                        Toast.makeText(mContext,user.getMsg(), Toast.LENGTH_SHORT).show();
                        //发送消息
                      //  EventBus.getDefault().post(new MessageEvent(s));
                        EventBus.getDefault().post(new MessageEvent("3"));
                    }else if(user.getStatus()==-97||user.getStatus()==-99){
                        Unlogin.doLogin(mContext);
                    }else if (user.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new UseCardAsyncTask());
                    }else {
                        Toast.makeText(mContext, user.getMsg(), Toast.LENGTH_SHORT).show();
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
