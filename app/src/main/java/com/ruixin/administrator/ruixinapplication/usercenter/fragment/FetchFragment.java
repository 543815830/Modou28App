package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.RMessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * 取出
 */

public class FetchFragment extends BaseFragment {
    private View view;
    TextView tv_user_secques;
    EditText et_deposit_coin;
    EditText et_user_secans;
    Button commit;
    String userId="", userSecques="" ,tbAccessType="2",tbUserSecAns="",tbAccessG="",userToken="";
    User user;
    private HashMap<String, String> prarams = new HashMap<String, String>();

    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_fetch,null);
            tv_user_secques=view.findViewById(R.id.tv_user_secques);
            et_deposit_coin=view.findViewById(R.id.et_deposit_coin);
            et_user_secans=view.findViewById(R.id.et_user_secans);
            commit=view.findViewById(R.id.commit);
            commit.setOnClickListener(new MyOnclick());
            userId=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            userToken=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
            userSecques=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_secques","");
            tv_user_secques.setText(new StringBuilder().append("您的密保问题是：").append(userSecques).toString());
        }
        return view;
    }


    @SuppressLint("StaticFieldLeak")
    private class FetchAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().InsideBank_URL,prarams);
            Log.e("取出的数据返回",""+result);
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
                        Log.e("取出的数据返回","取出成功");
                        //发送消息
                        EventBus.getDefault().post(new RMessageEvent(s));
                        Toast.makeText(mContext, "操作成功", Toast.LENGTH_SHORT).show();
                    }else if(user.getStatus()==-97||user.getStatus()==-99){
                        Unlogin.doLogin(mContext);
                    }else if (user.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new FetchAsyncTask());
                    }else{
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
    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            tbAccessG=et_deposit_coin.getText().toString();
            tbUserSecAns=et_user_secans.getText().toString();
            if(isValid()){
            prarams.put("usersid",userId);
            prarams.put("usertoken",userToken);
            prarams.put("tbAccessType",tbAccessType);
            prarams.put("tbAccessG",tbAccessG);
            prarams.put("tbUserSecAns",tbUserSecAns);
            new FetchAsyncTask().execute();
            }
        }
    }
    public boolean isValid() {
        if (tbAccessG.equals("")) {
            Toast.makeText(mContext, "请输入存入数量!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tbUserSecAns.equals("")) {
            Toast.makeText(mContext, "请输入密保答案!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
