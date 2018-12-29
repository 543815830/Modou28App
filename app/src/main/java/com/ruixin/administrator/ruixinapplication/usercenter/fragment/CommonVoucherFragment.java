package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.UpdatePasswordActivity;
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
 * 普通充值
 */

public class CommonVoucherFragment extends BaseFragment {
    private View view;
    private RadioGroup rg_use_card_type;
    RadioButton rb_use_card;
    RadioButton rb_query_card;
    EditText card_account;
    EditText card_password;
    Button commit;
    User user;
    String userId="", tbCardNO="" ,tbCardPwd="",tbMode="1",userToken="";
    private HashMap<String, String> prarams = new HashMap<String, String>();
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_common_voucher,null);
            card_account=view.findViewById(R.id.card_account);
            card_password=view.findViewById(R.id.card_password);
            commit=view.findViewById(R.id.commit);
            commit.setOnClickListener(new MyOnclick());
            userId=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            userToken=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
            rg_use_card_type=view.findViewById(R.id.rg_use_card_type);
            rb_use_card=view.findViewById(R.id.rb_use_card);
            rb_query_card=view.findViewById(R.id.rb_query_card);
            setListener();

        }
        return view;
    }
    private void setListener() {
        rg_use_card_type.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中
        rg_use_card_type.check(R.id.rb_use_card);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_use_card://点卡使用
                    tbMode = "1";
                    break;
                case R.id.rb_query_card://查询点卡
                    tbMode = "2";
                    break;
            }
        }
    }

    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            tbCardNO=card_account.getText().toString();
            tbCardPwd=card_password.getText().toString();
            prarams.put("usersid",userId);
            prarams.put("usertoken",userToken);
            prarams.put("tbCardNO",tbCardNO);
            prarams.put("tbCardPwd",tbCardPwd);
            prarams.put("tbMode",tbMode);
            if(tbMode.equals("1")){
                new UseCardAsyncTask().execute();
            }else{
                new QueryCardAsyncTask().execute();
            }

        }
    }
    @SuppressLint("StaticFieldLeak")
    private class UseCardAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().CardUse_URL,prarams);
            Log.e("点卡的数据返回",""+result);
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
                        Log.e("普通充值的数据返回","充值成功");
                        Toast.makeText(mContext, user.getMsg(), Toast.LENGTH_SHORT).show();
                        //发送消息
                       // EventBus.getDefault().post(new MessageEvent(s));
                        EventBus.getDefault().post(new MessageEvent("3"));
                    }else if(user.getStatus()==-97||user.getStatus()==-99){
                        Unlogin.doLogin(mContext);
                    }else if (user.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new UseCardAsyncTask());
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
    @SuppressLint("StaticFieldLeak")
    private class QueryCardAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().CardUse_URL,prarams);
            Log.e("点卡的数据返回",""+result);
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
                       Toast.makeText(mContext, user.getMsg(), Toast.LENGTH_SHORT).show();
                    }else if(user.getStatus()==-97||user.getStatus()==-99){
                        Unlogin.doLogin(mContext);
                    }else if (user.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new QueryCardAsyncTask());
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
}
