package com.ruixin.administrator.ruixinapplication.gamecenter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.MyBetAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GaCBarDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MyBetDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown;
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
 * 我的投注界面
 */
public class MyBetActivity extends Activity implements View.OnClickListener {
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
    private LinearLayout back_arrow;
    private TextView total_bet;
    private TextView tv_hd;
    private TextView tv_yingkui;
    private EditText et_search_start;
    private EditText et_search_end;
    private Button commit_query;
    ListView my_bet_lv;
    List<MyBetDb.DataBean.MybetlistBean> list=new ArrayList<>();
    MyBetAdapter adapter;
    String gameName;
    String EgameName;
    String userId;
    String userToken;
    String minNo;
    String maxNo;
    String gameType;
    private PullToRefreshScrollView pullToRefreshScrollView;
    ScrollView mScrollView;
    private int mtPage;//总页数
    int time = 1;//判断是第一次进来数据为空还是上拉加载数据为空
    boolean tag=true;
    boolean first=true;
    boolean query=false;
    LinearLayout ll_my_bet;
    int page=1;
    String url;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private String the_first_id;
    private String the_last_id;
    private HashMap<String, String> gaprarams = new HashMap<String, String>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> prarams1 = new HashMap<String, String>();
    int gameSystem;
    String no="";
    String Url;
    boolean isopend=false;
    boolean isvisible=true;
    String result;
    String fixted;
    String fixted2;
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

            if(msg.what ==5){
                if(isvisible){
                if (gameSystem == 1) {
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            first=false;
                            new GameInfoAsyncTask().execute();
                        }

                    }, 2500);
                } else if (gameSystem == 2) {
                   handler.postDelayed(new Runnable() {
                        public void run() {
                            first=false;
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
        setContentView(R.layout.activity_my_bet);
        Intent intent=getIntent();
        userId=intent.getStringExtra("userId");
        userToken=intent.getStringExtra("userToken");
        gameName=intent.getStringExtra("gameName");
        EgameName=intent.getStringExtra("EgameName");
      //  gameType=intent.getStringExtra("gameType");
        prarams.put("usersid",userId);
        prarams.put("usertoken",userToken);
        prarams.put("gamename",EgameName);
        gaprarams.put("usersid", userId);
        gaprarams.put("usertoken", userToken);
        gaprarams.put("gamename", EgameName);
        initStatus();
        initView();
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(gameName);
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
        total_bet = findViewById(R.id.total_bet);
        tv_hd = findViewById(R.id.tv_hd);
        et_search_start = findViewById(R.id.et_search_start);
        et_search_end = findViewById(R.id.et_search_end);
        commit_query = findViewById(R.id.commit_query);
        commit_query.setOnClickListener(this);
        ll_my_bet=findViewById(R.id.ll_my_bet);
        tv_yingkui = findViewById(R.id.tv_yingkui);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(this);
        pullToRefreshScrollView =findViewById(R.id.pull_to_refresh_scrollview);
        mScrollView = pullToRefreshScrollView.getRefreshableView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(ll_my_bet, null);
        new GameInfoAsyncTask().execute();
 //设置刷新的模式
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);//both  可以上拉、可以下拉
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>(){

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                Log.e("userId","onPullDownToRefresh");
                //得到当前刷新的时间
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                //设置更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                tag=false;
                if(!query){

                    prarams.clear();
                    prarams.put("usersid",userId);
                    prarams.put("usertoken",userToken);
                    prarams.put("gamename",EgameName);
                    prarams.put("firstid",""+the_first_id);
                    new GameBetListAsyncTask().execute();
                }else{
                    prarams.clear();
                    prarams.put("usersid",userId);
                    prarams.put("usertoken",userToken);
                    prarams.put("gamename",EgameName);
                    prarams.put("minNO",""+ minNo);
                    prarams.put("maxNO",""+maxNo);
                    prarams.put("firstid",""+the_first_id);
                    new GameBetListAsyncTask().execute();
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                Log.e("userId","onPullUpToRefresh");
                tag=true;
                ++time;
                if(!query){
                    prarams.clear();
                    prarams.put("usersid",userId);
                    prarams.put(" usertoken",userToken);
                    prarams.put("gamename",EgameName);
                    prarams.put("lastid",the_last_id);
                    new GameBetListAsyncTask().execute();
                }else{
                    prarams.clear();
                    prarams.put("usersid",userId);
                    prarams.put("usertoken",userToken);
                    prarams.put("gamename",EgameName);
                    prarams.put("minNO",""+ minNo);
                    prarams.put("maxNO",""+maxNo);
                    prarams.put("lastid",the_last_id);
                    new GameBetListAsyncTask().execute();
                }


            }
        });
        my_bet_lv=findViewById(R.id.my_bet_lv);
        new GameBetListAsyncTask().execute();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_arrow:
                finish();
                break;
            case R.id.commit_query:
                minNo=et_search_start.getText().toString();
                maxNo=et_search_end.getText().toString();
                if(minNo.equals("")||maxNo.equals("")){
                   Toast.makeText(MyBetActivity.this, "查询期数不能为空", Toast.LENGTH_SHORT).show();
                }else if(minNo.equals("0")||maxNo.equals("0")){
                    Toast.makeText(MyBetActivity.this, "查询期数不能为0", Toast.LENGTH_SHORT).show();
                }else{
                    tag=true;
                    query=true;
                    page=1;
                    prarams.clear();
                    prarams.put("usersid",userId);
                    prarams.put("usertoken",userToken);
                    prarams.put("gamename",EgameName);
                    if(fixted2!=null){
                        minNo=fixted2+minNo;
                    }
                    if(fixted!=null){
                        maxNo=fixted+maxNo;
                    }
                    prarams.put("minNO",""+ minNo);
                    prarams.put("maxNO",""+maxNo);
                    list.clear();
                    new GameBetListAsyncTask().execute();
                }

                break;
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class GameBetListAsyncTask extends AsyncTask<String, Integer, String> {
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
            result= AgentApi.dopost3(URL.getInstance().MyBet_URL,prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pullToRefreshScrollView.onRefreshComplete();
            Log.e("获取投注列表","消息返回结果result"+result);
            if(result!=null) {
                int status = 0;
                try {
                    JSONObject re=new JSONObject(result);
                    status=re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(status==1) {
                    //Toast.makeText(mContext, "网页登录失败！", Toast.LENGTH_SHORT).show();
                    Gson gson=new Gson();
                    MyBetDb myBetDb=gson.fromJson(result,MyBetDb.class);
                    if(myBetDb.getData().getMybetlist().size()>0){
                        total_bet.setText(myBetDb.getData().getBetpoints());
                        tv_hd.setText(myBetDb.getData().getSumpoints());
                        int kui=Integer.parseInt(myBetDb.getData().getSumpoints())-Integer.parseInt(myBetDb.getData().getBetpoints());
                        tv_yingkui.setText(new StringBuilder().append(kui).toString());
                        gameType=myBetDb.getData().getGameinfo().getType();
                        if(myBetDb.getData().getMybetlist().size()>0){
                            if(tag){
                                list.addAll(myBetDb.getData().getMybetlist());
                                Log.e("result","list="+list);
                                pullToRefreshScrollView.onRefreshComplete();
                            }else{
                                for(int i=0;i<myBetDb.getData().getMybetlist().size();i++){
                                    list.add(i,myBetDb.getData().getMybetlist().get(i));
                                    pullToRefreshScrollView.onRefreshComplete();
                                }

                            }
                            the_last_id=list.get(list.size()-1).getId();
                            the_first_id=list.get(0).getId();
                          if(first){
                              et_search_end.setText(GetGameResult.getGno(myBetDb.getData().getMaxno()));
                              et_search_start.setText(GetGameResult.getGno(myBetDb.getData().getMinno()));
                              first=false;
                          }
if(myBetDb.getData().getMaxno().length()>7){
 fixted=myBetDb.getData().getMaxno().substring(0,myBetDb.getData().getMaxno().length()-7);
}
if(myBetDb.getData().getMinno().length()>7){
                                fixted2=myBetDb.getData().getMaxno().substring(0,myBetDb.getData().getMinno().length()-7);
                            }
                            adapter=new MyBetAdapter(MyBetActivity.this,list,gameType);
                            my_bet_lv.setAdapter(adapter);
                            SetHight.setListViewHeightBasedOnChildren(my_bet_lv);
                            my_bet_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent=new Intent(MyBetActivity.this,MyBetDeatilActivity.class);
                                    intent.putExtra("usersid",userId);
                                    intent.putExtra("gameType",gameType);
                                    intent.putExtra("usertoken",userToken);
                                    intent.putExtra("gamename",EgameName);
                                    intent.putExtra("no",list.get(i).getId());
                                    intent.putExtra("where","2");
                                    startActivity(intent);
                                }
                            });
                            mLoadingAndRetryManager.showContent();
                        }
                    }else{
                        if (tag) {
                            if (time == 1) {
                                if(!query){
                                    mLoadingAndRetryManager.showContent();
                                   Toast.makeText(MyBetActivity.this, "没有投注记录！", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                   Toast.makeText(MyBetActivity.this, "这个期间没有投注记录！", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                mLoadingAndRetryManager.showContent();
                               Toast.makeText(MyBetActivity.this, "已经加载到底部了！", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                           Toast.makeText(MyBetActivity.this, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                        }
                    }


                }else if(status==-97||status==-99){
                   Toast.makeText(MyBetActivity.this, "账号已经过期请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MyBetActivity.this,LoginActivity.class);
                    intent.putExtra("where","2");
                    startActivity(intent);
                }else if(status==-2){
                    if(!query) {
                        Toast.makeText(MyBetActivity.this, "没有投注记录", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(MyBetActivity.this, "这个期间没有投注记录！", Toast.LENGTH_SHORT).show();
                    }
                }else if(status<0){
                   Toast.makeText(MyBetActivity.this, "请求失败，获取信息失败", Toast.LENGTH_SHORT).show();
                    finish();

                }else if (status==99) {
                        /*抗攻击*/
                    Gson gson=new Gson();
                    ATK atK = gson.fromJson(result, ATK.class);
                    String vaild_str = atK.getVaild_str();
                    String vaildd_md5 = FormatUtils.md5(vaild_str);
                    prarams.put("vaild_str", vaildd_md5);
                    new GameBetListAsyncTask().execute();
                }
            }else{
               Toast.makeText(MyBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
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
            Log.e("获取游戏头部信息列表","消息返回结果result"+result);
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
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
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
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
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
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
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
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
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
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
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
                            int num1= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0));
                            int num2= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1));
                            int num3= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2));
                            tv_result5.setText(new StringBuilder().append(res).append("=").toString());
                            tv_result51.setText(GetGameResult.get36rs(num1,num2,num3));
                        }else if(gameType.equals("10")||gameType.equals("xs")){
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            tv_result2.setText(gaCBarDb.getData().getLatestresult().get(0));
                        }if(gameType.equals("tbww")){
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.VISIBLE);
                            iv_1.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                            iv_2.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1))));
                            iv_3.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2))));
                        }if(gameType.equals("ww")){
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.VISIBLE);
                            int num1= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0));
                            int num2= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1));
                            int num3= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2));
                            int num4= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(3));
                            tv_result4.setText(""+num1+"+"+num2+"+"+num3+"=");
                            tv_result41 .setText(""+num4);
                            tv_result42.setText(GetGameResult.getwwrs(num1,num2,num3,num4));

                        }if(gameType.equals("dw")){
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.VISIBLE);
                            int num4= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(3));
                            tv_result41.setText(""+gaCBarDb.getData().getLatestresult().get(3));
                            tv_result42.setText(GetGameResult.getdwrs(num4));
                            tv_result4.setText(gaCBarDb.getData().getLatestresult().get(0)+"+"+gaCBarDb.getData().getLatestresult().get(1)+"+"+gaCBarDb.getData().getLatestresult().get(2)+"=");
                        }
                    }else{
                        ll_head_result7.setVisibility(View.GONE);
                        ll_head_result6.setVisibility(View.GONE);
                        ll_head_result5.setVisibility(View.GONE);
                        tv_result3.setVisibility(View.GONE);
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
                    Unlogin.doLogin(MyBetActivity.this);
                }else if (status==99) {
                        /*抗攻击*/
                    Unlogin.doAtk(gaprarams,result,   new GameInfoAsyncTask());
                }
            }else{
               Toast.makeText(MyBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
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
               Toast.makeText(MyBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
                mLoadingAndRetryManager.showContent();
            }
        }
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
}
