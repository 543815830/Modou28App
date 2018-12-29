package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.popwindow.DatePop2;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.Domain;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;
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
import butterknife.OnClick;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 开通域名界面
 */
public class OpenDomainNameActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_domain_name)
    EditText etDomainName;
    @BindView(R.id.tv_domain)
    TextView tvDomain;
    @BindView(R.id.rb_south_server)
    RadioButton rbSouthServer;
    @BindView(R.id.rb_east_server)
    RadioButton rbEastServer;
    @BindView(R.id.rb_north_server)
    RadioButton rbNorthServer;
    @BindView(R.id.rg_server)
    RadioGroup rgServer;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_down_arrow)
    ImageView ivDownArrow;
    @BindView(R.id.clear_coins)
    TextView clearCoins;
    @BindView(R.id.commit)
    Button commit;
    String userId = "", time = "1", region = "gz", rr = "", userToken = "", path;
    String coins;
    ArrayAdapter<String> adapter;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.tv_south_time)
    TextView tvSouthTime;
    @BindView(R.id.tv_east_time)
    TextView tvEastTime;
    @BindView(R.id.tv_north_time)
    TextView tvNorthTime;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    List<String> list = new ArrayList<>();
    public static int RESULT_CODE = 20;
    DatePop2 datePop;
    List<String> list1 = new ArrayList<>();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_domain_name);
        ButterKnife.bind(this);
        initStatus();
        initView();
    }

    private void initView() {
        tvTitle.setText("开通域名");
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new DomainAsyncTask().execute();
        tvTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String sTime = tvTime.getText().toString();
                if (sTime.equals("一个月")) {
                    time = "1";
                } else if (sTime.equals("二个月")) {
                    time = "2";
                } else if (sTime.equals("三个月")) {
                    time = "3";
                } else if (sTime.equals("四个月")) {
                    time = "4";
                } else if (sTime.equals("五个月")) {
                    time = "5";
                } else if (sTime.equals("六个月")) {
                    time = "6";
                } else if (sTime.equals("七个月")) {
                    time = "7";
                } else if (sTime.equals("八个月")) {
                    time = "8";
                } else if (sTime.equals("九个月")) {
                    time = "9";
                } else if (sTime.equals("十个月")) {
                    time = "10";
                } else if (sTime.equals("十一个月")) {
                    time = "11";
                } else if (sTime.equals("十二个月")) {
                    time = "12";
                }
                int clearcoins = Integer.parseInt(coins) * Integer.parseInt(time);
                clearCoins.setText(FormatUtils.formatString(String.valueOf(clearcoins)));
            }
        });
        setListener();
        Intent intent = new Intent();
        setResult(RESULT_CODE, intent);
    }

    private void setListener() {
        rgServer.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中
        rgServer.check(R.id.rb_south_server);
    }

    @OnClick(R.id.back_arrow)
    public void onViewClicked() {
        finish();
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_south_server://华南服务器
                    region = "gz";
                    break;
                case R.id.rb_east_server://华东服务器
                    region = "sh";
                    break;
                case R.id.rb_north_server://华北服务器
                    region = "bj";
                    break;
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

    @OnClick({R.id.commit, R.id.tv_domain, R.id.iv_down_arrow, R.id.tv_time, R.id.ll_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commit:
                prarams.put("usersid", userId);
                prarams.put("usertoken", userToken);
                rr = etDomainName.getText().toString();
                path = tvDomain.getText().toString();
                prarams.put("path", path);
                prarams.put("rr", rr);
                prarams.put("region", region);
                String sTime = tvTime.getText().toString();
                if (sTime.equals("一个月")) {
                    time = "1";
                } else if (sTime.equals("二个月")) {
                    time = "2";
                } else if (sTime.equals("三个月")) {
                    time = "3";
                } else if (sTime.equals("四个月")) {
                    time = "4";
                } else if (sTime.equals("五个月")) {
                    time = "5";
                } else if (sTime.equals("六个月")) {
                    time = "6";
                } else if (sTime.equals("七个月")) {
                    time = "7";
                } else if (sTime.equals("八个月")) {
                    time = "8";
                } else if (sTime.equals("九个月")) {
                    time = "9";
                } else if (sTime.equals("十个月")) {
                    time = "10";
                } else if (sTime.equals("十一个月")) {
                    time = "11";
                } else if (sTime.equals("十二个月")) {
                    time = "12";
                }
                prarams.put("time", time);
                new OpDomainAsyncTask().execute();
                break;
            case R.id.tv_domain:
                Showalert2();
                break;
            case R.id.iv_down_arrow:
                Log.e("tag", "点击箭头了");
                Showalert();
                break;
            case R.id.tv_time:
                Log.e("tag", "点击箭头了");
                Showalert();
                break;
            case R.id.ll_time:
                Log.e("tag", "点击箭头了");
                Showalert();
                break;
        }
    }

    private void Showalert() {
       /* adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"一个月", "二个月", "三个月", "四个月", "五个月", "六个月", "七个月", "八个月", "九个月", "十个月", "十一个月", "十二个月"});
        AlertDialog dialog1 = new AlertDialog.Builder(this)
              *//*  .setIcon(R.mipmap.wen)
                .setTitle("请选择")*//*
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       Toast.makeText(OpenDomainNameActivity.this, adapter.getItem(which), Toast.LENGTH_SHORT).show();
                        String time = adapter.getItem(which);
                        tvTime.setText(time);
                        dialog.dismiss();
                    }
                }).create();
        dialog1.show();*/
        list1.clear();
        list1.add("一个月");
        list1.add("二个月");
        list1.add("三个月");
        list1.add("四个月");
        list1.add("五个月");
        list1.add("六个月");
        list1.add("七个月");
        list1.add("八个月");
        list1.add("九个月");
        list1.add("十个月");
        list1.add("十一个月");
        list1.add("十二个月");
        datePop = new DatePop2(this, DisplayUtil.dp2px(this, 170), DisplayUtil.dp2px(this, 170), list1, null, tvTime);
        //监听窗口的焦点事件，点击窗口外面则取消显示
        datePop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    datePop.dismiss();
                }
            }
        });

        //设置默认获取焦点
        datePop.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        datePop.showAsDropDown(tvTime, 0, 0);
        //如果窗口存在，则更新
        datePop.update();
    }

    private void Showalert2() {
        datePop = new DatePop2(this, DisplayUtil.dp2px(this, 170), DisplayUtil.dp2px(this, 170), list, null, tvDomain);
        //监听窗口的焦点事件，点击窗口外面则取消显示
        datePop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    datePop.dismiss();
                }
            }
        });

        //设置默认获取焦点
        datePop.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        datePop.showAsDropDown(tvDomain, 0, 0);
        //如果窗口存在，则更新
        datePop.update();
    }

    @SuppressLint("StaticFieldLeak")
    private class DomainAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().Dmain_URL, prarams);
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
                    Domain domain = gson.fromJson(s, Domain.class);
                    if (domain.getStatus() == 1) {
                        Log.e("域名数据返回", "" + s);
                        list = domain.getData().getYml();
                        coins = domain.getData().getWeb_zsxl();
                        clearCoins.setText(FormatUtils.formatString(domain.getData().getWeb_zsxl()));
                        tvDomain.setText(list.get(0));
                    } else if (domain.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new DomainAsyncTask());
                    } else if (domain.getStatus() == -97 || domain.getStatus() == -99) {
                        Unlogin.doLogin(OpenDomainNameActivity.this);
                    }else{
                        Toast.makeText(OpenDomainNameActivity.this, domain.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(OpenDomainNameActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OpenDomainNameActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @SuppressLint("StaticFieldLeak")
    private class OpDomainAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().OpenDmain_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    JSONObject jsb = null;
                    try {
                        jsb = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int status = jsb.optInt("status");
                    String msg=jsb.optString("msg");
                    if (status == 1) {
                        EventBus.getDefault().post(new MessageEvent("3"));
                        finish();
                    }else if (status == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new OpDomainAsyncTask());
                    } else if (status == -97 || status == -99) {
                        Unlogin.doLogin(OpenDomainNameActivity.this);
                    }else{
                        Toast.makeText(OpenDomainNameActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(OpenDomainNameActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OpenDomainNameActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }


   /* @SuppressLint("HandlerLeak")
    private void RunHandler() {
        // 实时更新UI
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                PingNetEntity pingNetEntity = new PingNetEntity("101.226.103.106", 3, 3, new StringBuffer());
                pingNetEntity = PingNet.ping(pingNetEntity);
                Log.e("testPing", pingNetEntity.getIp());
                Log.e("testPing", "time=" + pingNetEntity.getPingTime());
                Log.e("testPing", pingNetEntity.isResult() + "");
                //华东
                if (pingNetEntity.isResult()) {
                    String time = pingNetEntity.getPingTime();
                    String sub = time.substring(0, time.length() - 3);
                    if (Double.parseDouble(sub) < 100) {
                        tvEastTime.setTextColor(Color.parseColor("#37af2b"));
                    } else if (Double.parseDouble(sub) > 100 && Double.parseDouble(sub) < 200) {
                        tvEastTime.setTextColor(Color.parseColor("#c9912f"));
                    } else {
                        tvEastTime.setTextColor(Color.parseColor("#cd3a48"));
                    }
                    tvEastTime.setText(pingNetEntity.getPingTime());
                } else {
                    tvEastTime.setText("连接服务器超时");
                    //Toast.makeText(OpenDomainNameActivity.this, "连接服务器超时", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Runnable mRunnable = new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        mHandler.sendMessage(mHandler.obtainMessage());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(mRunnable).start();
    }*/
  /*  @SuppressLint("HandlerLeak")
    private void RunHandler2() {
        // 实时更新UI
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                PingNetEntity pingNetEntity2 = new PingNetEntity("111.13.101.208", 3, 3, new StringBuffer());
                pingNetEntity2 = PingNet.ping(pingNetEntity2);
                Log.e("testPing", pingNetEntity2.isResult() + "");
                //华南
                if (pingNetEntity2.isResult()) {
                    String time2 = pingNetEntity2.getPingTime();
                    String sub2 = time2.substring(0, time2.length() - 3);
                    if (Double.parseDouble(sub2) < 100) {
                        tvSouthTime.setTextColor(Color.parseColor("#37af2b"));
                    } else if (Double.parseDouble(sub2) > 100 && Double.parseDouble(sub2) < 200) {
                        tvSouthTime.setTextColor(Color.parseColor("#c9912f"));
                    } else {
                        tvSouthTime.setTextColor(Color.parseColor("#cd3a48"));
                    }
                    tvSouthTime.setText(pingNetEntity2.getPingTime());
                } else {
                    tvSouthTime.setText("连接服务器超时");
                    //Toast.makeText(OpenDomainNameActivity.this, "连接服务器超时", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Runnable mRunnable = new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        mHandler.sendMessage(mHandler.obtainMessage());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(mRunnable).start();
    }
    @SuppressLint("HandlerLeak")
    private void RunHandler3() {
        // 实时更新UI
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                PingNetEntity pingNetEntity3 = new PingNetEntity("110.75.115.70", 3, 3, new StringBuffer());
                pingNetEntity3 = PingNet.ping(pingNetEntity3);
                Log.e("testPing", pingNetEntity3.isResult() + "");
                //华北
                if (pingNetEntity3.isResult()) {
                    String time = pingNetEntity3.getPingTime();
                    String sub = time.substring(0, time.length() - 3);
                    if (Double.parseDouble(sub) < 100) {
                        tvNorthTime.setTextColor(Color.parseColor("#37af2b"));
                    } else if (Double.parseDouble(sub) > 100 && Double.parseDouble(sub) < 200) {
                        tvNorthTime.setTextColor(Color.parseColor("#c9912f"));
                    } else {
                        tvNorthTime.setTextColor(Color.parseColor("#cd3a48"));
                    }
                    tvNorthTime.setText(pingNetEntity3.getPingTime());
                } else {
                    tvNorthTime.setText("连接服务器超时");
                    //Toast.makeText(OpenDomainNameActivity.this, "连接服务器超时", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Runnable mRunnable = new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(4000);
                        mHandler.sendMessage(mHandler.obtainMessage());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(mRunnable).start();
    }*/
}
