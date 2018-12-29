package com.ruixin.administrator.ruixinapplication.gamecenter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.ProfitAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.ProfitNameAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GaCBarDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ProfitDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ProfitDb2;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.popwindow.DatePop;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.GetGameResult;
import com.ruixin.administrator.ruixinapplication.utils.SetHight;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 盈利统计界面
 */

public class ProfitActivity extends Activity {
    LinearLayout ll_center_bar;
    LinearLayout ll_head_result;
    LinearLayout ll_head_result1;
    LinearLayout ll_head_result4;
    LinearLayout ll_head_result5;
    LinearLayout ll_head_result6;
    TextView be_tv1;
    TextView lottery_result_tv1;
    TextView lottery_result_tv2;
    TextView lottery_result_tv3;
    TextView tv_result1;
    TextView tv_result2;
    TextView tv_result3;
    TextView tv_result4;
    TextView tv_result41;
    TextView tv_result42;
    TextView tv_result5;
    TextView tv_result51;
    TextView tv_result12;
    TextView tv_result13;
    TextView tv_result14;
    LinearLayout ll_head_result7;
    TextView tv_result71;
    TextView tv_result72;
    TextView tv_result73;
    TextView tv_result74;
    TextView tv_result75;
    ImageView iv_1;
    ImageView iv_2;
    ImageView iv_3;
    TextView lottery_result_tv4;
    TextView be_tv2;
    TextView count_down_time;
    TextView tv_chane_coins;
    TextView tv_my_coins;
    TextView today_part;
    TextView today_win_loss;
    TextView today_win_rate;
    CountDown timer;
    private TextView tv_title;
    LinearLayout back_arrow;
    LinearLayout ll_game_profit;
    HorizontalScrollView hs_profit;
    String userId;
    String userToken;
    String EgameName;
    String gameType;
    String result;
    private ListView profit_lv_name;
    private ListView profit_lv;
    ProfitAdapter adapter;
    ProfitNameAdapter nameAdapter;
    List<String> list = new ArrayList<>();
    TextView tv_select_time;
    TextView tv_query_time;
    List<String> list1 = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    List<ProfitDb.DataBean.GamedataBean> gamedata = new ArrayList<>();
    List<ProfitDb2.DataBean.GamedataBean> gamedata2 = new ArrayList<>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    DatePop datePop;
    String date;
    int gameSystem;
    String no="";
    String Url;
    boolean first=true;
    boolean isopend=false;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> prarams1 = new HashMap<String, String>();
    private HashMap<String, String> gaprarams = new HashMap<String, String>();
    boolean isvisible=true;
    Runnable thread = new Runnable() {
        @Override
        public void run() {// TODO Auto-generated method stub
            prarams1.put("usersid", userId);
            prarams1.put(" usertoken", userToken);
            Url = URL.getInstance().CheckGame_URL + "?game=" + EgameName + "&no=" + no;
            new CheckGameInfoAsyncTask().execute();
            if (!isopend) {
                if (isvisible) {
                    handler.postDelayed(this, 5000);
                }
            }
        }
    };
    @SuppressLint("HandlerLeak")
    public Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            first=false;

            if(msg.what ==5){
                if(isvisible){
                if (gameSystem == 1) {
                   handler.postDelayed(new Runnable() {
                        public void run() {
                            new GameInfoAsyncTask().execute();
                        }

                    }, 2500);
                } else if (gameSystem == 2) {
                   handler.postDelayed(new Runnable() {
                        public void run() {
                            if (!isopend) {
                                handler.post(thread);
                            }


                        }

                    }, 30000);

                }
            }

            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userToken = intent.getStringExtra("userToken");
        EgameName = intent.getStringExtra("EgameName");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        gaprarams.put("usersid", userId);
        gaprarams.put("usertoken", userToken);
        gaprarams.put("gamename", EgameName);
        initStatus();
        initView();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isvisible=false;
        handler.removeCallbacks(thread);
        handler.removeMessages(5);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isvisible=true;
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
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText("盈利统计");
        be_tv1=findViewById(R.id.be_tv1);
        count_down_time=findViewById(R.id.count_down_time);
        be_tv2=findViewById(R.id.be_tv2);
        ll_head_result=findViewById(R.id.ll_head_result);
        ll_head_result1=findViewById(R.id.ll_head_result1);
        ll_head_result4=findViewById(R.id.ll_head_result4);
        ll_head_result5=findViewById(R.id.ll_head_result5);
        ll_head_result6=findViewById(R.id.ll_head_result6);
        lottery_result_tv1=findViewById(R.id.lottery_result_tv1);
        lottery_result_tv2=findViewById(R.id.lottery_result_tv2);
        lottery_result_tv3=findViewById(R.id.lottery_result_tv3);
        lottery_result_tv4=findViewById(R.id.lottery_result_tv4);
        tv_result1=findViewById(R.id.tv_result1);
        iv_1=findViewById(R.id.iv_1);
        iv_2=findViewById(R.id.iv_2);
        iv_3=findViewById(R.id.iv_3);
        tv_result2=findViewById(R.id.tv_result2);
        tv_result3=findViewById(R.id.tv_result3);
        tv_result4=findViewById(R.id.tv_result4);
        tv_result41=findViewById(R.id.tv_result41);
        tv_result42=findViewById(R.id.tv_result42);
        tv_result5=findViewById(R.id.tv_result5);
        tv_result51=findViewById(R.id.tv_result51);
        tv_result12=findViewById(R.id.tv_result12);
        tv_result13=findViewById(R.id.tv_result13);
        tv_result14=findViewById(R.id.tv_result14);
        ll_head_result7 =findViewById(R.id.ll_head_result7);
        tv_result71 =findViewById(R.id.tv_result71);
        tv_result72 =findViewById(R.id.tv_result72);
        tv_result73 = findViewById(R.id.tv_result73);
        tv_result74 =findViewById(R.id.tv_result74);
        tv_result75 = findViewById(R.id.tv_result75);
        ll_center_bar = findViewById(R.id.ll_center_bar);
        ll_center_bar.setFocusable(true);
        ll_center_bar.setFocusableInTouchMode(true);
        ll_center_bar.requestFocus();
        tv_chane_coins = findViewById(R.id.tv_chane_coins);
        tv_my_coins = findViewById(R.id.tv_my_coins);
        today_part = findViewById(R.id.today_part);
        today_win_loss = findViewById(R.id.today_win_loss);
        today_win_rate = findViewById(R.id.today_win_rate);
        tv_select_time = findViewById(R.id.tv_select_time);
        ll_game_profit = findViewById(R.id.ll_game_profit);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(ll_game_profit, null);
        tv_select_time.setOnClickListener(new MyOnclickListener());
        date = tv_select_time.getText().toString();
        tv_select_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                date = tv_select_time.getText().toString();
                if (date.equals("最近七天")) {
                    prarams.put("date", "7");
                } else {
                    prarams.put("date", date);
                }
               // new GameInfoAsyncTask().execute();
                new GameProfitListAsyncTask().execute();
            }
        });
        profit_lv_name = findViewById(R.id.profit_lv_name);
        profit_lv = findViewById(R.id.profit_lv);
        new GameProfitListAsyncTask().execute();
        new GameInfoAsyncTask().execute();
    }

    private class MyOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_select_time:
                    if(list1.size()>0){
                        if(!list1.get(0).equals("最近七天")){
                            list1.add(0,"最近七天");
                        }
                        datePop = new DatePop(ProfitActivity.this, DisplayUtil.dp2px(ProfitActivity.this, 100), DisplayUtil.dp2px(ProfitActivity.this, 200), list1, new MyOnclickListener(), tv_select_time);
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
                        datePop.showAsDropDown(tv_select_time, 0, 0);
                        //如果窗口存在，则更新
                        datePop.update();
                    }else{
                       Toast.makeText(ProfitActivity.this, "您近期没有盈利日记", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GameProfitListAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().GameProfit_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取盈利列表", "消息返回结果result" + result);
            if (result != null) {
                int status = 0;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                    Gson gson = new Gson();
                   // if (date.equals("最近七天")) {
                        ProfitDb profitDb = gson.fromJson(result, ProfitDb.class);
                        list1 = profitDb.getData().getTime_list();
                        list2 = profitDb.getData().getGamedatalist();
                        gamedata = profitDb.getData().getGamedata();
                       list= profitDb.getData().getGamedatalist();//游戏名称的列表
                        adapter = new ProfitAdapter(ProfitActivity.this, list2, gamedata);
                        profit_lv.setAdapter(adapter);
                    nameAdapter=new ProfitNameAdapter(ProfitActivity.this,list);
                    profit_lv_name.setAdapter(nameAdapter);
                    SetHight.setListViewHeightBasedOnChildren(profit_lv_name);
                    SetHight.setListViewHeightBasedOnChildren(profit_lv);
                    mLoadingAndRetryManager.showContent();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(ProfitActivity.this);
                } else if (status < 0) {
                   Toast.makeText(ProfitActivity.this, "请求失败，获取信息失败", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(prarams,result,   new GameProfitListAsyncTask());
                }
            } else {
               Toast.makeText(ProfitActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class GameInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(first){
                mLoadingAndRetryManager.showLoading();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result= AgentApi.dopost3(URL.getInstance().GameCenBar_URL,gaprarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取游戏名称列表","消息返回结果result"+result);
            if(result!=null) {
                int status = 0;
                try {
                    JSONObject re=new JSONObject(result);
                    status=re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(status==1) {
                    Gson gson=new Gson();
                    GaCBarDb gaCBarDb=gson.fromJson(result,GaCBarDb.class);
                    gameSystem=gaCBarDb.getData().getGamesystem();
                    if(gaCBarDb.getData().getNearno()!=null){
                        no=gaCBarDb.getData().getNearno();
                        be_tv1.setText("距离"+ GetGameResult.getGno(gaCBarDb.getData().getNearno())+"期截止还有");
                        timer=new CountDown(gaCBarDb.getData().getRestsecond()*1000,1000,count_down_time,handler);
                        timer.start();
                    }else{
                        be_tv1.setText("距离本期截止还有");
                        timer=new CountDown(gaCBarDb.getData().getGametime()*1000,1000,count_down_time,handler);
                        timer.start();
                    }
                    if (gaCBarDb.getData().getLatestno()!=null){
                        be_tv2.setText(GetGameResult.getGno(gaCBarDb.getData().getLatestno())+"期开奖结果");
                    }else{
                        be_tv2.setText("近期");
                    }
                    gameType=gaCBarDb.getData().getType();
                    if(gaCBarDb.getData().getLatestno()!=null&&gaCBarDb.getData().getLatestresult()!=null){
                        if(gameType.equals("28")||gameType.equals("16")||gameType.equals("22")){
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.VISIBLE);
                            lottery_result_tv1.setText(""+gaCBarDb.getData().getLatestresult().get(0));
                            lottery_result_tv2.setText(""+gaCBarDb.getData().getLatestresult().get(1));
                            lottery_result_tv3.setText(""+gaCBarDb.getData().getLatestresult().get(2));
                            lottery_result_tv4.setText(""+gaCBarDb.getData().getLatestresult().get(3));
                        }else if(gameType.equals("ssc")){
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.VISIBLE);
                            tv_result71.setText("" + gaCBarDb.getData().getLatestresult().get(0));
                            tv_result72.setText("" + gaCBarDb.getData().getLatestresult().get(1));
                            tv_result73.setText("" + gaCBarDb.getData().getLatestresult().get(2));
                            tv_result74.setText("" + gaCBarDb.getData().getLatestresult().get(3));
                            tv_result75.setText("" + gaCBarDb.getData().getLatestresult().get(4));
                        }else if(gameType.equals("bjl")){
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            tv_result2.setText(GetGameResult.getbjlrs(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                        }else if(gameType.equals("lh")){
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            tv_result2.setText(GetGameResult.getlhrs(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                        }else if(gameType.equals("xync")||gameType.equals("pkww")){
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.VISIBLE);
                            StringBuilder res = new StringBuilder();
                            for(int j=0;j<gaCBarDb.getData().getLatestresult().size();j++){
                                res.append(gaCBarDb.getData().getLatestresult().get(j));
                                res.append(',');
                            }
                            if(res.length()>0){
                                res.deleteCharAt(res.length()-1);
                            }
                            tv_result3.setText(res.toString());
                        }else if(gameType.equals("11")||gameType.equals("gy")||gameType.equals("xn")){
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.VISIBLE);
                            StringBuilder res = new StringBuilder();
                            for(int j=0;j<gaCBarDb.getData().getLatestresult().size()-1;j++){
                                res.append(gaCBarDb.getData().getLatestresult().get(j));
                                res.append('+');
                            }
                            if(res.length()>0){
                                res.deleteCharAt(res.length()-1);
                            }
                            tv_result5.setText(new StringBuilder().append(res).append("=").toString());
                            tv_result51.setText(gaCBarDb.getData().getLatestresult().get(gaCBarDb.getData().getLatestresult().size()-1));
                        }else if(gameType.equals("36")){
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.VISIBLE);
                            StringBuilder res = new StringBuilder();
                            for(int j=0;j<gaCBarDb.getData().getLatestresult().size()-1;j++){
                                res.append(gaCBarDb.getData().getLatestresult().get(j));
                                res.append('+');
                            }
                            if(res.length()>0){
                                res.deleteCharAt(res.length()-1);
                            }
                            int num1= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0));
                            int num2= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1));
                            int num3= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2));
                            tv_result5.setText(new StringBuilder().append(res).append("=").toString());
                            tv_result51.setText(GetGameResult.get36rs(num1,num2,num3));
                        }else if(gameType.equals("10")||gameType.equals("xs")){
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            tv_result2.setText(gaCBarDb.getData().getLatestresult().get(0));
                        }if(gameType.equals("tbww")){
                            ll_head_result4.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.VISIBLE);
                            iv_1.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                            iv_2.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1))));
                            iv_3.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2))));
                        }if(gameType.equals("ww")){
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.VISIBLE);
                            int num1= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0));
                            int num2= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1));
                            int num3= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2));
                            int num4= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(3));
                            tv_result4.setText("" + num1 + "+" + num2 + "+" + num3+"=");
                            tv_result41.setText(""+ num4);
                            tv_result42.setText(GetGameResult.getwwrs(num1,num2,num3,num4));

                        }if(gameType.equals("dw")){
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.VISIBLE);
                            int num4= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(3));
                            tv_result41.setText("="+gaCBarDb.getData().getLatestresult().get(3));
                            tv_result42.setText(GetGameResult.getdwrs(num4));
                            tv_result4.setText(gaCBarDb.getData().getLatestresult().get(0)+"+"+gaCBarDb.getData().getLatestresult().get(1)+"+"+gaCBarDb.getData().getLatestresult().get(2));
                        }
                    }else{
                        ll_head_result6.setVisibility(View.GONE);
                        ll_head_result5.setVisibility(View.GONE);
                        tv_result3.setVisibility(View.GONE);
                        ll_head_result7.setVisibility(View.GONE);
                        ll_head_result1.setVisibility(View.GONE);
                        ll_head_result.setVisibility(View.GONE);
                        ll_head_result4.setVisibility(View.GONE);
                        tv_result2.setVisibility(View.VISIBLE);
                        tv_result2.setText("尚未开奖");
                    }

                    if (gameType.equals("xn")) {
                        tv_chane_coins.setText("虚拟币");
                        tv_my_coins.setText(String.valueOf(gaCBarDb.getData().getMytoday().getXnb()));
                    }else{
                        tv_my_coins.setText(String.valueOf(gaCBarDb.getData().getMytoday().getPoints()));
                    }
                    today_part.setText(new StringBuilder().append(gaCBarDb.getData().getMytoday().getPlaynum()).append("期").toString());
                    today_win_loss.setText(""+gaCBarDb.getData().getMytoday().getProfit());
                    today_win_rate.setText(new StringBuilder().append(gaCBarDb.getData().getMytoday().getWinpercent()).append("%").toString());
                    mLoadingAndRetryManager.showContent();
                }else if(status==-97||status==-99){
                    Unlogin.doLogin(ProfitActivity.this);
                }else if (status==99) {
                        /*抗攻击*/
                    Unlogin.doAtk(gaprarams,result,   new GameInfoAsyncTask());
                }
            }else{
               Toast.makeText(ProfitActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class CheckGameInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result= AgentApi.dopost3(Url,prarams1);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取是否开奖的结果","消息返回结果result"+result);
            if(result!=null) {
                if(result.equals("1")){
                    isopend=true;
                    handler.removeCallbacks(thread);
                    handler.removeMessages(5);
                    new GameInfoAsyncTask().execute();
                    isopend = false;
                }

            }else{
               Toast.makeText(ProfitActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
                mLoadingAndRetryManager.showContent();
            }
        }
    }
}
