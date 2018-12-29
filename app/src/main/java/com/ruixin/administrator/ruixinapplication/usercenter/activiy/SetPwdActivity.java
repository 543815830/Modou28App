package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.PopListAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.PwdQue;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 设置密保界面
 */
public class SetPwdActivity extends Activity implements View.OnClickListener {
    TextView tv_title;//标题
    LinearLayout back_arrow;//返回
    ImageView iv_down_arrow;//向下箭头
    @BindView(R.id.pwd_que)
    TextView pwdQue;
    @BindView(R.id.pwd_ans)
    EditText pwdAns;
    @BindView(R.id.save)
    Button save;
    String que;
    List<String> list = new ArrayList<>();
    String userId = "", UserSecQues = "", answer, userToken = "";
    ArrayAdapter<String> adapter;
    @BindView(R.id.iv_down_arrow)
    ImageView ivDownArrow;
    @BindView(R.id.ll_set_pwd)
    LinearLayout llSetPwd;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private PopupWindow popupWindow;
    private ListView listview;
    PopListAdapter popListAdapter;
    public static int RESULT_CODE = 47;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pwd);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        initStatus();
        if (userId.equals("")) {
           Toast.makeText(SetPwdActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SetPwdActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        } else {
            if (userToken.equals("")) {
               Toast.makeText(SetPwdActivity.this, "您的账号不正确请重新登录！", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                initView();
            }

        }
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("密保设置");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new PwdQueAsyncTask().execute();
        back_arrow = findViewById(R.id.back_arrow);
        iv_down_arrow = findViewById(R.id.iv_down_arrow);
        back_arrow.setOnClickListener(this);
        //iv_down_arrow.setOnClickListener(this);
        llSetPwd.setOnClickListener(this);
        listview=new ListView(this);
        listview.setBackgroundColor(Color.parseColor("#ffffff"));
        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.ll_set_pwd:
                if(popupWindow==null){
                    popupWindow=new PopupWindow(this);
                    Log.e("tag", "popupWindow==");
                    popupWindow.setWidth(llSetPwd.getWidth());
                    popupWindow.setHeight(600);
                    popupWindow.setContentView(listview);
                    popupWindow.setFocusable(true);
                }
                popupWindow.showAsDropDown(llSetPwd,0,0);
                popListAdapter=new PopListAdapter(this,list);
                listview.setAdapter(popListAdapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        que = list.get(i);
                        pwdQue.setText(que);
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.save:
                UserSecQues = pwdQue.getText().toString();
                answer = pwdAns.getText().toString();
                prarams.put("UserSecQues", UserSecQues);
                prarams.put("answer", answer);
                prarams.put("usertoken", userToken);
                new SavePwdAsyncTask().execute();
                break;
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class PwdQueAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().PwdQue_URL, prarams);
            Log.e("密保问题的数据返回", "" + result);
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
                    PwdQue pwdQue = gson.fromJson(s, PwdQue.class);
                    if (pwdQue.getStatus() == 1) {
                        Log.e("密保问题的数据返回", "" + s);
                        list = pwdQue.getData();

                    }else if(pwdQue.getStatus()==-97||pwdQue.getStatus()==-99){
                        Unlogin.doLogin(SetPwdActivity.this);
                    }else if (pwdQue.getStatus() ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new PwdQueAsyncTask());
                    } else {
                       Toast.makeText(SetPwdActivity.this, pwdQue.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(SetPwdActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(SetPwdActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @SuppressLint("StaticFieldLeak")
    private class SavePwdAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().SPwdQue_URL, prarams);
            Log.e("绑定密保问题的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                int status = -1;
                String ques = "";
                String msg = "";
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    try {
                        JSONObject jsb = new JSONObject(s);
                        status = jsb.optInt("status");
                        ques = jsb.optString("data");
                        msg = jsb.optString("msg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status == 1) {
                       Toast.makeText(SetPwdActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                                Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (ques != null) {
                            editor.putString("user_secques", ques);  //用户密保问题
                        } else {
                            editor.putString("user_secques", "");
                        }
                        editor.apply();
                        EventBus.getDefault().post(new MessageEvent("3"));
                        finish();
                    } else if(status==-97||status==-99){
                        Unlogin.doLogin(SetPwdActivity.this);
                    }else if (status ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new SavePwdAsyncTask());
                    }else{
                        Toast.makeText(SetPwdActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(SetPwdActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(SetPwdActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
