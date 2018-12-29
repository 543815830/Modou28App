package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.Salary;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 工资领取界面
 */
public class GetSalaryActivity extends Activity {
    TextView tv_title;//标题
    LinearLayout back_arrow;//返回
    public static int RESULT_CODE = 400;
    String userId = "", userToken = "";
    @BindView(R.id.current_salary)
    TextView currentSalary;
    @BindView(R.id.commit)
    Button commit;
    @BindView(R.id.gongzi_lilv)
    TextView gongziLilv;
    @BindView(R.id.is_jiru)
    TextView isJiru;
    String tsg;
    User user;
    private HashMap<String, String> prarams = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_salary);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        initStatus();
        if (userId.equals("")) {
           Toast.makeText(GetSalaryActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(GetSalaryActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        } else {
            initView();
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
        tv_title.setText("工资领取");
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new MyOnClick());
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new SalaryAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class SalaryAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().Salary_URL, prarams);
            Log.e("工资领取的数据返回", "" + result);
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
                    Salary salary = gson.fromJson(s, Salary.class);
                    if (salary.getStatus() == 1) {
                        //发送消息
                        currentSalary.setText(salary.getData().getGongzi());
                        gongziLilv.setText(new StringBuilder().append("            可领取的工资额为您投注额的").append(salary.getData().getGongzi_num()).append("分之一！").toString());
                        if (salary.getData().getGongzi_auto().equals("1")) {
                            tsg = "不";
                        } else {
                            tsg = "";
                        }
                        isJiru.setText(new StringBuilder().append("            自动投注").append(tsg).append("计入工资；").toString());

                    }else if (salary.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new SalaryAsyncTask());
                    } else if(salary.getStatus()==-99||salary.getStatus()==-97){
                        Unlogin.doLogin(GetSalaryActivity.this);
                    }else{
                        Toast.makeText(GetSalaryActivity.this, salary.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(GetSalaryActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(GetSalaryActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @OnClick(R.id.commit)
    public void onViewClicked() {
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new GetSalaryAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetSalaryAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().GSalary_URL, prarams);
            Log.e("工资领取的数据返回", "" + result);
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
                        User user=gson.fromJson(s, User.class);
                        EventBus.getDefault().post(new MessageEvent("3"));
                        currentSalary.setText(user.getData().getGongzi());
                        Toast.makeText(GetSalaryActivity.this, "领取成功", Toast.LENGTH_SHORT).show();
                    }else if (entry.getStatus() ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new GetSalaryAsyncTask());
                    }else if(entry.getStatus()==-99||entry.getStatus()==-97){
                        Unlogin.doLogin(GetSalaryActivity.this);
                    } else {
                        Toast.makeText(GetSalaryActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(GetSalaryActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(GetSalaryActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class MyOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back_arrow:
                    finish();
                    break;
            }
        }
    }
}
