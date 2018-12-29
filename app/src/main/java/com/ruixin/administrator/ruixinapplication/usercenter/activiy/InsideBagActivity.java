package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.Bag;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 站内红包界面
 */

public class InsideBagActivity extends Activity {
    TextView tv_title;//标题
    LinearLayout back_arrow;
    TextView tv_bag_type;//红包类型
    String pin = "每个人抽到的金额随机，改为普通红包";
    String pu = "收到固定金额，改为拼手气红包";
    String mTag = "改为普通红包";
    String pTag = "改为拼手气红包";
    public static int RESULT_CODE = 700;
    String userId = "", userToken = "";
    @BindView(R.id.shoucufei)
    TextView shoucufei;
    @BindView(R.id.xiane)
    TextView xiane;
    @BindView(R.id.commit)
    Button commit;
    User user;
    String type, points, sum;
    @BindView(R.id.bag_account)
    EditText bagAccount;
    @BindView(R.id.bag_number)
    EditText bagNumber;
    @BindView(R.id.total_deduct_money)
    TextView totalDeductMoney;
    @BindView(R.id.select_more)
    LinearLayout selectMore;
    @BindView(R.id.ll_pwd)
    LinearLayout llPwd;
    @BindView(R.id.tv_bag_type)
    TextView tvBagType;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    double feilv;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_bag);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        initStatus();
        if (userId.equals("")) {
           Toast.makeText(InsideBagActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(InsideBagActivity.this, LoginActivity.class);
            intent.putExtra("where", "2");
            startActivity(intent);
        } else {
            initView();
        }
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("站内红包");
        tv_bag_type = findViewById(R.id.tv_bag_type);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new MyOnclick());
        selectMore.setOnClickListener(new MyOnclick());
        SpannableString mBuilder = new SpannableString(pin);
        mBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), 11, 11 + mTag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_bag_type.setText(mBuilder);
        tv_bag_type.setOnClickListener(new MyOnclick());;
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new BagAsyncTask().execute();
        bagAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!bagAccount.getText().toString().equals("")) {
                    double account = Double.parseDouble((bagAccount.getText().toString()));
                    double money = feilv * account + account;
                    DecimalFormat df = new DecimalFormat("0.00");
                    totalDeductMoney.setText("" + df.format(money));
                }

            }
        });
    }

    @OnClick(R.id.commit)
    public void onViewClicked() {
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        if (tv_bag_type.getText().toString().equals(pin)) {
            type = "1";
        } else {
            type = "2";
        }
        points = bagAccount.getText().toString();
        sum = bagNumber.getText().toString();
        prarams.put("points", points);
        prarams.put("type", type);
        prarams.put("sum", sum);
        new SendBagAsyncTask().execute();
    }

    /*动态显示扣费总金额*/
    public void initData() {

        bagAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!bagAccount.getText().toString().equals("")) {
                        double account = Double.parseDouble((bagAccount.getText().toString()));
                        double money = feilv * account + account;
                        DecimalFormat df = new DecimalFormat("0.00");
                        totalDeductMoney.setText("" + df.format(money));
                    } else {
                        totalDeductMoney.setText("0");
                    }
                }
            }
        });
    }

    private class BagAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().IBag_URL, prarams);
            Log.e("红包数据返回", "" + result);
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
                    Bag bag = gson.fromJson(s, Bag.class);
                    if (bag.getStatus() == 1) {
                        shoucufei.setText(new StringBuilder().append(bag.getData().getWeb_hbsx()).append("%").toString());
                        xiane.setText(bag.getData().getWeb_hbxe());
                        feilv = Double.parseDouble(bag.getData().getWeb_hbsx()) / 100;
                    } else if (bag.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new BagAsyncTask());
                    }else if (bag.getStatus() == -97 || bag.getStatus() == -99) {
                        Unlogin.doLogin(InsideBagActivity.this);
                    } else {
                       Toast.makeText(InsideBagActivity.this, bag.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(InsideBagActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(InsideBagActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @SuppressLint("StaticFieldLeak")
    private class SendBagAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().Send_Bag_URL, prarams);
            Log.e("发送红包的数据返回", "" + result);
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
                        EventBus.getDefault().post(new MessageEvent("3"));
                        intent=new Intent(InsideBagActivity.this,MyRedBagActivity.class);
                        intent.putExtra("userid",userId);
                        intent.putExtra("usertoken",userToken);
                        startActivity(intent);
                    } else if (entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new SendBagAsyncTask());
                    } else if (entry.getStatus() == -97 || entry.getStatus() == -99) {
                        Unlogin.doLogin(InsideBagActivity.this);
                    }else{
                        Toast.makeText(InsideBagActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(InsideBagActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(InsideBagActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
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
    public class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back_arrow://返回
                    finish();
                    break;
                case R.id.select_more://更多
                    intent=new Intent(InsideBagActivity.this,MyRedBagActivity.class);
                    intent.putExtra("userid",userId);
                    intent.putExtra("usertoken",userToken);
                    startActivity(intent);
                    break;
                case R.id.my_redbag://我的红包
                    intent=new Intent(InsideBagActivity.this,MyRedBagActivity.class);
                intent.putExtra("userid",userId);
                intent.putExtra("usertoken",userToken);
                startActivity(intent);
                    break;
                case R.id.tv_bag_type:
                    //为拼手气红包时
                    if (tv_bag_type.getText().toString().equals(pin)) {
                        SpannableString mBuilder = new SpannableString(pu);
                        mBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), 7, 7 + pTag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_bag_type.setText(mBuilder);
                    } else {
                        //普通红包
                        SpannableString mBuilder = new SpannableString(pin);
                        mBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), 11, 11 + mTag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_bag_type.setText(mBuilder);
                    }
                    break;
            }
        }
    }
}
