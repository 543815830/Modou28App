package com.ruixin.administrator.ruixinapplication.gamecenter.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.ProfitActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GaCBarDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.GetGameResult;
import com.ruixin.administrator.ruixinapplication.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 走势图界面
 */
public class GameMapWebview extends Activity implements View.OnClickListener {
    LinearLayout ll_game_map;
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
    private String game_name;
    private String userId;
    private String userToken;
    WebView mWebView;
    private String url;
    private String ui;
    String EgameName;
    String gameType;
    String result;
    int gameSystem;
    String no="";
    String Url;
    boolean isopend=false;
    boolean first=true;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> gaprarams = new HashMap<String, String>();
    boolean isvisible=true;
    private ProgressBar progressBar;
    @SuppressLint("HandlerLeak")
    public Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            first=false;
            if(isvisible){
            if(msg.what ==5){
                if (gameSystem == 1) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            new GameInfoAsyncTask().execute();
                        }

                    }, 2500);
                } else if (gameSystem == 2) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            isopend = false;
                            if (!isopend) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {// TODO Auto-generated method stub
                                        prarams.put("usersid", userId);
                                        prarams.put(" usertoken", userToken);
                                        Url = URL.getInstance().CheckGame_URL + "?game=" + EgameName + "&no=" + no;
                                        new CheckGameInfoAsyncTask().execute();
                                        handler.postDelayed(this, 5000);
                                    }
                                });
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
        setContentView(R.layout.activity_game_map_webview);
        Intent intent = getIntent();
        // 取其中的值
        userId = intent.getStringExtra("userId");
        Log.e("userId",""+userId);
        userToken = intent.getStringExtra("userToken");
        Log.e("userToken",""+userToken);
        game_name = intent.getStringExtra("gameName");
        gameType = intent.getStringExtra("gameType");
        EgameName = intent.getStringExtra("EgameName");
        gaprarams.put("usersid", userId);
        gaprarams.put("usertoken", userToken);
        gaprarams.put("gamename", EgameName);
        Log.e("geme",""+game_name);
        initStatus();
        initView();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isvisible=false;
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
        be_tv1=findViewById(R.id.be_tv1);
        count_down_time=findViewById(R.id.count_down_time);
        be_tv2=findViewById(R.id.be_tv2);
        ll_game_map=findViewById(R.id.ll_game_map);
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
        tv_title=findViewById(R.id.tv_title);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(this);
        tv_title.setText(""+game_name);
        mWebView = findViewById(R.id.game_map_web);

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(ll_game_map, null);
        new GameInfoAsyncTask().execute();
        //声明WebSettings子类
        progressBar=findViewById(R.id.progress_bar);
        WebSettings webSettings = mWebView.getSettings();
        //ww , 28 ,16 , 11 , 10 , dw , 22 , gy , xs , lh
        if(gameType.equals("28")||gameType.equals("ww")||gameType.equals("16")||gameType.equals("11")||gameType.equals("10")||gameType.equals("dw")||gameType.equals("22")||gameType.equals("gy")||gameType.equals("xs")||gameType.equals("lh")||gameType.equals("xn"))
        {
            url= URL.getInstance().GameMap_URL;
        }else if(gameType.equals("36")){
            url= URL.getInstance().GameMap1_URL;
        }else if(gameType.equals("pkww")){
            url= URL.getInstance().GameMap2_URL;
        }else if(gameType.equals("ssc")){
            url= URL.getInstance().GameMap3_URL;
        }else if(gameType.equals("xync")){
            url= URL.getInstance().GameMap4_URL;
        }else if(gameType.equals("tbww")){
            url= URL.getInstance().GameMap5_URL;
        }else if(gameType.equals("bjl")){
            url= URL.getInstance().GameMap6_URL;
        }
        url=url+"?gamename="+EgameName;
        ui = "usersid=" + userId + "&usertoken=" + userToken+"&gamename="+EgameName;
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //如果不设置WebViewClient，请求会跳转系统浏览器
       // mLoadingAndRetryManager.showLoading();
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
// Activity和Webview根据加载程度决定进度条的进度大小
// 当加载到100%的时候 进度条自动消失
//WebViewProgressActivity.this.setTitle("Loading...");
//WebViewProgressActivity.this.setProgress(progress * 100);
                Log.e("TAG", "progress:" + progress);
                if(progress==100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
        });
        // 设置web视图客户端
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.postUrl(url,ui.getBytes());

    }
    public class MyWebViewClient extends WebViewClient {
        public boolean shouldOverviewUrlLoading(WebView view, String url) {
            Log.e("TAG", "url:" + url);
            Log.e("TAG", "ui:" + ui);
            view.postUrl(url,ui.getBytes());
            return true;
        }

    }
    @Override
    public void onClick(View view) {
        finish();
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
                    if(gaCBarDb.getData().getLatestno()!=null&&gaCBarDb.getData().getLatestresult()!=null&&gaCBarDb.getData().getLatestresult().size()>0){
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
                            ll_head_result5.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            tv_result2.setText(GetGameResult.getbjlrs(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                        }else if(gameType.equals("lh")){
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            tv_result2.setText(GetGameResult.getlhrs(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                        }else if(gameType.equals("xync")||gameType.equals("pkww")){
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            int num1= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0));
                            int num2= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1));
                            int num3= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2));
                            tv_result5.setText(new StringBuilder().append(res).append("=").toString());
                            tv_result51.setText(GetGameResult.get36rs(num1,num2,num3));
                        }else if(gameType.equals("10")||gameType.equals("xs")){
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
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
                            tv_result4.setText(""+num1+"+"+num2+"+"+num3);
                            tv_result41 .setText("="+num4);
                            tv_result42.setText(GetGameResult.getwwrs(num1,num2,num3,num4));

                        }if(gameType.equals("dw")){
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.VISIBLE);
                            int num4= Integer.parseInt(gaCBarDb.getData().getLatestresult().get(3));
                            tv_result41.setText(""+gaCBarDb.getData().getLatestresult().get(3));
                            tv_result42.setText(GetGameResult.getdwrs(num4));
                            tv_result4.setText(gaCBarDb.getData().getLatestresult().get(0)+"+"+gaCBarDb.getData().getLatestresult().get(1)+"+"+gaCBarDb.getData().getLatestresult().get(2)+"=");
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
                   Toast.makeText(GameMapWebview.this, "账号已经过期请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(GameMapWebview.this,LoginActivity.class);
                    intent.putExtra("where","2");
                    startActivity(intent);
                }else if (status==99) {
                        /*抗攻击*/
                    Gson gson=new Gson();
                    ATK atK = gson.fromJson(result, ATK.class);
                    String vaild_str = atK.getVaild_str();
                    Log.e("tag", "" + vaild_str);
                    String vaildd_md5 = FormatUtils.md5(vaild_str);
                    Log.e("tag", "" + vaildd_md5);
                    gaprarams.put("vaild_str", vaildd_md5);
                    new GameInfoAsyncTask().execute();
                }
            }else{
               Toast.makeText(GameMapWebview.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class CheckGameInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result= AgentApi.dopost3(Url,prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取是否开奖的结果","消息返回结果result"+result);
            if(result!=null) {
                if(result.equals("1")){
                    isopend=true;
                    new GameInfoAsyncTask().execute();
                }

            }else{
               Toast.makeText(GameMapWebview.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
                mLoadingAndRetryManager.showContent();
            }
        }
    }
}
