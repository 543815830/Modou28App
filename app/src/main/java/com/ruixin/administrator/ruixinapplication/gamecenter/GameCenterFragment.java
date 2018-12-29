package com.ruixin.administrator.ruixinapplication.gamecenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.AutoBetActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.CheckNumberBetActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.DoublingBetActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.webview.GameHelpActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.GameNameActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.MyBetActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.MyBetDeatilActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.ProfitActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.GameResultAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.DatabaseHelper;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GaCBarDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GameHomeDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GameName1;
import com.ruixin.administrator.ruixinapplication.gamecenter.webview.GameMapWebview;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.BackHandlerHelper;
import com.ruixin.administrator.ruixinapplication.utils.CountDown;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;
import com.ruixin.administrator.ruixinapplication.popwindow.PopMenu;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.GetGameResult;
import com.ruixin.administrator.ruixinapplication.utils.SetHight;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 游戏中心界面
 */

public class GameCenterFragment extends BaseFragment {
    Button btn_login;
    RelativeLayout rl_titlebar2;
    LinearLayout ll_head_result;
    LinearLayout ll_head_result1;
    LinearLayout ll_head_result4;
    LinearLayout ll_head_result5;
    LinearLayout ll_head_result6;
    LinearLayout ll_head_result7;
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
    TextView tv_result71;
    TextView tv_result72;
    TextView tv_result73;
    TextView tv_result74;
    TextView tv_result75;
    ImageView iv_menu;
    ImageView iv_1;
    ImageView iv_2;
    ImageView iv_3;
    TextView be_tv1;
    TextView lottery_result_tv4;
    TextView be_tv2;
    TextView count_down_time;
    TextView tv_my_coins;
    TextView today_part;
    TextView today_win_loss;
    TextView today_win_rate;
    TextView tv_chane_coins;
    CountDown timer;
    LinearLayout ll_center_bar;
    String userId;
    String userToken;
    String gameName;
    String EgameName;
    String gameType;
    static int gameSystem;
    private View view;
    TextView tv_title;
    TextView tv_game_center;
    LinearLayout selectMenu;
    LinearLayout ll_game_center1;
    LinearLayout ll_game_center;
    LinearLayout back_arrow;
    FrameLayout fl_game_content;
    static ListView game_center_lv;
    List<GameHomeDb.DataBean.OpenhistoryBean> list = new ArrayList<>();
    List<GameHomeDb.DataBean.OpenhistoryBean> newlist = new ArrayList<>();
    GameResultAdapter adapter;
    PopMenu popMenu;
    String result;
    private HashMap<String, String> game_name_prarams = new HashMap<>();
    Intent intent;
    private PullToRefreshScrollView pullToRefreshScrollView;
    ScrollView mScrollView;
    int time = 1;//判断是第一次进来数据为空还是上拉加载数据为空
    boolean tag = true;
    boolean first = true;
    boolean isopend = false;
    boolean isVisible;
    int page = 1;
    static String nearno = "";
    String no = "";
    String no1 = "";
    boolean exit = false;
    private long lastBackPress;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> gaprarams = new HashMap<String, String>();
    private String the_first_id = "";
    private String the_last_id = "";
    List<GameName1.DataBean.GamelistBean> list1 = new ArrayList<GameName1.DataBean.GamelistBean>();//游戏列表名
    List<GameName1.DataBean.HotgamelistBean> list2 = new ArrayList<GameName1.DataBean.HotgamelistBean>();//热门游戏
    List<GameName1.DataBean.LatesgamelistBean> list3 = new ArrayList<GameName1.DataBean.LatesgamelistBean>();//最爱游戏
    List<GameName1.DataBean.LatesgamelistBean> list33 = new ArrayList<GameName1.DataBean.LatesgamelistBean>();//最爱游戏
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> prarams1 = new HashMap<String, String>();
    private DatabaseHelper dbHelper;
    Activity activity = getActivity();
    String Url;
    boolean ishidden = false;
    int height = 0;
    int select = 0;
    long timeStamp;
    boolean onresult = false;
    boolean back = false;
    List<Integer>array=new ArrayList<>();
    Runnable thread = new Runnable() {
        @Override
        public void run() {// TODO Auto-generated method stub
            prarams1.put("usersid", userId);
            prarams1.put(" usertoken", userToken);
            Url = URL.getInstance().CheckGame_URL + "?game=" + EgameName + "&no=" + no;
            new CheckGameInfoAsyncTask().execute();
            if (!isopend) {
                if (isVisible) {
                    handler.postDelayed(this, 5000);
                }
            }
        }
    };
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 5) {//正在开奖中发的信息
                if (isVisible) {//可见
                    traversal();
                    if (gameSystem == 1) {
                        handler.postDelayed(new Runnable() {
                            public void run() {
                              /*  if (!isopend) {//尚未开奖时
                                    handler.post(thread);
                                }*/
                                if (timer != null) {
                                    timer.cancel();
                                }
                                tag = true;
                                page = 1;
                                prarams.clear();
                                prarams.put("usersid", userId);
                                prarams.put(" usertoken", userToken);
                                prarams.put("gamename", EgameName);
                                gaprarams.clear();
                                gaprarams.put("usersid", userId);
                                gaprarams.put("usertoken", userToken);
                                gaprarams.put("gamename", EgameName);
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
            }//传来消息5
            else if (msg.what == 6) {
                handler.removeMessages(5);
                handler.removeCallbacks(thread);
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected View initView() {
        if (view == null) {
            view = View.inflate(mContext, R.layout.fm_game_center, null);
            ll_game_center1 = view.findViewById(R.id.ll_game_center1);
            btn_login = view.findViewById(R.id.btn_login);
            tv_game_center = view.findViewById(R.id.tv_game_center);
            rl_titlebar2 = view.findViewById(R.id.rl_titlebar2);
            getStatusBarHeight();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_game_center.getLayoutParams();
            layoutParams.setMargins(0, height, 0, 0);
            tv_game_center.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rl_titlebar2.getLayoutParams();
            lp.setMargins(0, height, 0, 0);
            rl_titlebar2.setLayoutParams(lp);
            btn_login.setOnClickListener(new MyOnclickListener());
            //获取数据库前六位并加入到list3里面去
            dbHelper = new DatabaseHelper(mContext, "FavorsGameStore.db", null, 1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor1 = db.rawQuery("SELECT gamechname ,gamename ,gametype,count( * ) AS count\n" +
                    "FROM Game\n" +
                    "GROUP BY gamechname,gamename,gametype\n" +
                    "ORDER BY count DESC\n" +
                    "LIMIT 6", null);
            while (cursor1.moveToNext()) {
                gameName = cursor1.getString(cursor1.getColumnIndex("gamechname"));
                gameType = cursor1.getString(cursor1.getColumnIndex("gametype"));
                EgameName = cursor1.getString(cursor1.getColumnIndex("gamename"));
                GameName1.DataBean.LatesgamelistBean st = new GameName1.DataBean.LatesgamelistBean(gameName, EgameName, gameType);//student_info存一个条目的数据
                list3.add(st);
            }
            be_tv1 = view.findViewById(R.id.be_tv1);
            count_down_time = view.findViewById(R.id.count_down_time);
            be_tv2 = view.findViewById(R.id.be_tv2);
            ll_head_result = view.findViewById(R.id.ll_head_result);
            ll_head_result1 = view.findViewById(R.id.ll_head_result1);
            ll_head_result4 = view.findViewById(R.id.ll_head_result4);
            ll_head_result5 = view.findViewById(R.id.ll_head_result5);
            ll_head_result6 = view.findViewById(R.id.ll_head_result6);
            ll_head_result7 = view.findViewById(R.id.ll_head_result7);
            lottery_result_tv1 = view.findViewById(R.id.lottery_result_tv1);
            lottery_result_tv2 = view.findViewById(R.id.lottery_result_tv2);
            lottery_result_tv3 = view.findViewById(R.id.lottery_result_tv3);
            lottery_result_tv4 = view.findViewById(R.id.lottery_result_tv4);
            tv_result1 = view.findViewById(R.id.tv_result1);
            iv_1 = view.findViewById(R.id.iv_1);
            iv_2 = view.findViewById(R.id.iv_2);
            iv_3 = view.findViewById(R.id.iv_3);
            tv_result2 = view.findViewById(R.id.tv_result2);
            tv_result3 = view.findViewById(R.id.tv_result3);
            tv_result4 = view.findViewById(R.id.tv_result4);
            tv_result41 = view.findViewById(R.id.tv_result41);
            tv_result42 = view.findViewById(R.id.tv_result42);
            tv_result5 = view.findViewById(R.id.tv_result5);
            tv_result51 = view.findViewById(R.id.tv_result51);
            tv_result12 = view.findViewById(R.id.tv_result12);
            tv_result13 = view.findViewById(R.id.tv_result13);
            tv_result14 = view.findViewById(R.id.tv_result14);
            tv_result71 = view.findViewById(R.id.tv_result71);
            tv_result72 = view.findViewById(R.id.tv_result72);
            tv_result73 = view.findViewById(R.id.tv_result73);
            tv_result74 = view.findViewById(R.id.tv_result74);
            tv_result75 = view.findViewById(R.id.tv_result75);
            // //设置当前焦点，防止打开Activity出现在ListView位置问题
            ll_center_bar = view.findViewById(R.id.ll_center_bar);
            ll_center_bar.setFocusable(true);
            ll_center_bar.setFocusableInTouchMode(true);
            ll_center_bar.requestFocus();
            tv_chane_coins = view.findViewById(R.id.tv_chane_coins);
            tv_my_coins = view.findViewById(R.id.tv_my_coins);
            today_part = view.findViewById(R.id.today_part);
            today_win_loss = view.findViewById(R.id.today_win_loss);
            today_win_rate = view.findViewById(R.id.today_win_rate);
            ll_game_center = view.findViewById(R.id.ll_game_center);
            mLoadingAndRetryManager = LoadingAndRetryManager.generate(ll_game_center, null);
            pullToRefreshScrollView = view.findViewById(R.id.pull_to_refresh_scrollview);
            pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);//both  可以上拉、可以下拉
            pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                    Log.e("userId", "onPullDownToRefresh");
                    // timer.cancel();
                    // timer = null;
                    //得到当前刷新的时间
                    String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
                            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                    //设置更新时间
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                    tag = false;
                    prarams.clear();
                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    prarams.put("gamename", EgameName);
                    prarams.put("firstid", "" + the_first_id);
                    Log.e("firstid", "firstid" + the_first_id);

                    new GameInfoAsyncTask().execute();


                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                    Log.e("userId", "onPullDownToRefresh");
                    tag = true;
                    ++page;
                    ++time;
                    prarams.clear();
                    prarams.put("usersid", userId);
                    prarams.put(" usertoken", userToken);
                    prarams.put("gamename", EgameName);
                    prarams.put("lastid", "" + the_last_id);

                    new GameInfoAsyncTask().execute();
                }

            });
            mScrollView = pullToRefreshScrollView.getRefreshableView();
            tv_title = view.findViewById(R.id.tv_title);
            back_arrow = view.findViewById(R.id.back_arrow);
            back_arrow.setVisibility(View.GONE);
            fl_game_content = view.findViewById(R.id.fl_game_content);
            selectMenu = view.findViewById(R.id.select_menu);
            iv_menu = view.findViewById(R.id.iv_menu);
            game_center_lv = view.findViewById(R.id.game_center_lv);
            tv_title.setOnClickListener(new MyOnclickListener());
            tv_title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    Message msg1 = new Message();
                    msg1.what = 6;
                    handler.sendMessage(msg1);

                }
            });
            selectMenu.setOnClickListener(new MyOnclickListener());
            userId = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
            userToken = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
            if (sharedPreferences.getString("is_login", "").equals("true")) {
                if (userId == null || userToken == null || userId.equals("") || userToken.equals("")) {
                    ll_game_center1.setVisibility(View.VISIBLE);
                } else {
                    ll_game_center1.setVisibility(View.GONE);

                    game_name_prarams.put("usersid", userId);
                    game_name_prarams.put("usertoken", userToken);
                    if (userId != null && userToken != null) {
                        new GameNameListAsyncTask().execute();
                    }
                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    gaprarams.put("usersid", userId);
                    gaprarams.put("usertoken", userToken);
                }
            } else {
                ll_game_center1.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    private void getStatusBarHeight() {

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("tag", height + "height---------");

    }

    public class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (userToken != null) {
                gameName = tv_title.getText().toString();
                userId = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
                userToken = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
            }
            switch (view.getId()) {
                case R.id.btn_login://登录
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("where", "3");
                    startActivity(intent);
                    break;
                case R.id.select_menu:
                    popMenu = null;

                    //自定义的单击事件
                    MyOnclickListener onClickLintener = new MyOnclickListener();
                    popMenu = new PopMenu(mContext, gameType, onClickLintener, DisplayUtil.dp2px(mContext, 170));
                    //监听窗口的焦点事件，点击窗口外面则取消显示
                    popMenu.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                popMenu.dismiss();
                            }
                        }
                    });

                    //设置默认获取焦点
                    popMenu.setFocusable(true);
                    //以某个控件的x和y的偏移量位置开始显示窗口
                    popMenu.showAsDropDown(iv_menu, 0, 0);
                    //如果窗口存在，则更新
                    popMenu.update();
                    break;
                case R.id.my_bet://我的投注
                    intent = new Intent(mContext, MyBetActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("gameName", gameName);
                    intent.putExtra("EgameName", EgameName);
                    //  intent.putExtra("gameType",gameType);
                    startActivity(intent);
                    popMenu.dismiss();
                    //  startActivity(new Intent(mContext, MyBetActivity.class));
                    break;
                case R.id.bet_mod://投注模式
                    intent = new Intent(mContext, BetModeActivity.class);
                    intent.putExtra("where", "0");
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("EgameName", EgameName);
                    intent.putExtra("gameName", gameName);
                    intent.putExtra("gameType", gameType);
                    startActivity(intent);
                    popMenu.dismiss();
                    // startActivity(new Intent(mContext, BetModeActivity.class));
                    break;
                case R.id.auto_bet://自动投注
                    intent = new Intent(mContext, AutoBetActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("EgameName", EgameName);
                    intent.putExtra("gameName", gameName);
                    intent.putExtra("gameType", gameType);
                    intent.putExtra("type", "add");
                    startActivity(intent);
                    popMenu.dismiss();
                    // startActivity(new Intent(mContext, AutoBetActivity.class));
                    break;
                case R.id.check_number_bet://对号投注
                    intent = new Intent(mContext, CheckNumberBetActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("EgameName", EgameName);
                    intent.putExtra("gameName", gameName);
                    intent.putExtra("gameType", gameType);
                    intent.putExtra("type", "add");
                    Log.e("Tag", "" + gameType);
                    startActivity(intent);
                    popMenu.dismiss();
                    // startActivity(new Intent(mContext, CheckNumberBetActivity.class));
                    break;
                case R.id.doubling_bet://翻倍投注
                    intent = new Intent(mContext, DoublingBetActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("EgameName", EgameName);
                    intent.putExtra("gameName", gameName);
                    intent.putExtra("gameType", gameType);
                    Log.e("Tag", "" + gameType);
                    intent.putExtra("type", "add");
                    startActivity(intent);
                    popMenu.dismiss();
                    // startActivity(new Intent(mContext, CheckNumberBetActivity.class));
                    break;
                case R.id.game_tendency://游戏走势
                    intent = new Intent(mContext, GameMapWebview.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("EgameName", EgameName);
                    intent.putExtra("gameName", gameName);
                    intent.putExtra("gameType", gameType);
                    startActivity(intent);
                    popMenu.dismiss();
                    //startActivity(new Intent(mContext, GameMapWebview.class));
                    break;
                case R.id.profit_total://盈利统计
                    intent = new Intent(mContext, ProfitActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("EgameName", EgameName);
                    intent.putExtra("gameType", gameType);
                    startActivity(intent);
                    popMenu.dismiss();
                    // startActivity(new Intent(mContext, ProfitActivity.class));
                    break;
                case R.id.game_help://游戏帮助
                    intent = new Intent(mContext, GameHelpActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("gameName", gameName);
                    intent.putExtra("EgameName", EgameName);
                    startActivity(intent);
                    popMenu.dismiss();
                    // startActivity(new Intent(mContext,GameHelpActivity.class));
                    break;
                case R.id.get_xnb://领取虚拟币
                    prarams1.clear();
                    prarams1.put("usersid", userId);
                    prarams1.put(" usertoken", userToken);
                   new GetXnbAsyncTask().execute();
                    popMenu.dismiss();
                    // startActivity(new Intent(mContext,GameHelpActivity.class));
                    break;
                case R.id.tv_title://选择游戏
                    if (timer != null) {
                        timer.cancel();
                    }
                    if((System.currentTimeMillis()-timeStamp)/1000>60){
                        select++;
                        new GameNameListAsyncTask().execute();
                    }else{
                        intent = new Intent(mContext, GameNameActivity.class);
                        startActivityForResult(intent, 22);

                    }


                    // startActivity(new Intent(mContext, GameNameActivity.class));
                    break;


            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == GameNameActivity.RESULT_CODE) {
            Log.e("Tag", "onActivityResult" + gameName);
            onresult = true;
            if (timer != null) {
                timer.cancel();
            }
            handler.removeMessages(5);
            handler.removeCallbacks(thread);
            no1 = no;
            time = 1;
            gameName = data.getStringExtra("gameName");
            EgameName = data.getStringExtra("EgameName");
            Log.e("Tag", "" + gameName);
            gameType = data.getStringExtra("gameType");
            Log.e("Tag", "" + gameType);
            tv_title.setText(gameName);
            first = true;
            tag = true;
            page = 1;
            prarams.clear();
            prarams.put("usersid", userId);
            prarams.put(" usertoken", userToken);
            prarams.put("gamename", EgameName);
            gaprarams.clear();
            gaprarams.put("usersid", userId);
            gaprarams.put("usertoken", userToken);
            gaprarams.put("gamename", EgameName);
            //  new GameBetListAsyncTask().execute();
            new GameInfoAsyncTask().execute();
        }
        if (requestCode == 22 && resultCode == BetModeActivity.RESULT_CODE) {
            if (timer != null) {
                timer.cancel();
            }
            onresult = true;
            handler.removeMessages(5);
            handler.removeCallbacks(thread);
            first = true;
            back = true;
            tag = true;
            page = 1;
            prarams.clear();
            prarams.put("usersid", userId);
            prarams.put(" usertoken", userToken);
            prarams.put("gamename", EgameName);
            gaprarams.clear();
            gaprarams.put("usersid", userId);
            gaprarams.put("usertoken", userToken);
            gaprarams.put("gamename", EgameName);
            new GameInfoAsyncTask().execute();
            EventBus.getDefault().post(new MessageEvent("3"));//向个人中心发消息
        }
        if (requestCode == 345 && resultCode == LoginActivity.RESULT_CODE) {
            Log.e("tag", "onresult");
            initView();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GameNameListAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           timeStamp = System.currentTimeMillis();

            Log.e("xxxxx",""+ timeStamp);

            if (first) {
                mLoadingAndRetryManager.showLoading();
                first = false;
            }
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().GameName_URL, game_name_prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取游戏名称列表", "消息返回结果result" + result);
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
                    GameName1 gameName1 = gson.fromJson(result, GameName1.class);
                    list1 = gameName1.getData().getGamelist();//游戏列表
                    RuiXinApplication.getInstance().setList(list1);
                    list2 = gameName1.getData().getHotgamelist();//最热游戏
                    RuiXinApplication.getInstance().setList1(list2);
                    Log.e("ega", "ega" + EgameName);
                    if (select == 0) {
                        if (list3.size() == 0 || list3 == null) {
                            tv_title.setText(list1.get(0).getGamechname());
                            EgameName = list1.get(0).getGamename();
                            gameName = list1.get(0).getGamechname();
                            gameType = list1.get(0).getGametype();
                        } else if (list3.size() > 0) {
                            for(int i=0;i<list3.size();i++){
                                for(int j=0;j<list1.size();j++){
                                    if(list3.get(i).getGamechname().equals(list1.get(j).getGamechname())){
                                       list33.add(list3.get(i));
                                    }
                                }
                            }
                            tv_title.setText(list33.get(0).getGamechname());
                            EgameName = list33.get(0).getGamename();
                            gameName = list33.get(0).getGamechname();
                            gameType = list33.get(0).getGametype();
                        }
                        prarams.put("gamename", EgameName);
                        gaprarams.put("gamename", EgameName);
                        new GameInfoAsyncTask().execute();
                    }else{
                        intent = new Intent(mContext, GameNameActivity.class);
                        startActivityForResult(intent, 22);
                    }
                    // new GameBetListAsyncTask().execute();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(mContext);
                } else if (status == 99) {
                        /*抗攻击*/
                    Gson gson = new Gson();
                    ATK atK = gson.fromJson(result, ATK.class);
                    String vaild_str = atK.getVaild_str();
                    Log.e("tag", "" + vaild_str);
                    String vaildd_md5 = FormatUtils.md5(vaild_str);
                    Log.e("tag", "" + vaildd_md5);
                    game_name_prarams.put("vaild_str", vaildd_md5);
                    new GameNameListAsyncTask().execute();
                }
            } else {
                Toast.makeText(mContext, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* 标题栏与状态栏颜色一致用这种*/
    /*设置状态栏*/
    @SuppressLint("ResourceAsColor")
    protected void initStatus() {
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_VISIBLE
            );//隐藏虚拟按键(导航栏));
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class GameBetListAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (back) {
                mLoadingAndRetryManager.showLoading();
                back = false;
            }
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().GameHome_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pullToRefreshScrollView.onRefreshComplete();

            Log.e("获取中心游戏开奖列表", "消息返回结果result" + result);
            if (!(result == null || result.equals(""))) {
                int status = 0;
                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {

                    GameHomeDb gameHomeDb = gson.fromJson(result, GameHomeDb.class);
                    Log.e("result", "gameHomeDb.getData().getOpenhistory().size()=" + gameHomeDb.getData().getOpenhistory().size());
                    if (gameHomeDb.getData().getOpenhistory().size() > 0) {
                        if (tag) {
                            if (page == 1) {
                                list = gameHomeDb.getData().getOpenhistory();
                            } else {
                                list.addAll(gameHomeDb.getData().getOpenhistory());
                            }

                        } else {
                            if (list.size() > 4) {
                                for (int j = 0; j < 4; j++) {
                                    newlist.add(list.get(j));
                                }
                            }
                            list.removeAll(newlist);
                            for (int i = 0; i < gameHomeDb.getData().getOpenhistory().size(); i++) {
                                list.add(i, gameHomeDb.getData().getOpenhistory().get(i));
                            }
                            Log.e("result", "list=" + list);
                            adapter.notifyDataSetChanged();
                        }
                        if (list.size() > 4 && list.size() > 0) {
                            the_first_id = list.get(4).getNo();
                        } else {
                            the_first_id = list.get(0).getNo();
                        }
                        the_last_id = list.get(list.size() - 1).getNo();
                        adapter = new GameResultAdapter(mContext, list, gameType);
                        Log.e("userId", "" + userId);
                        game_center_lv.setAdapter(adapter);
                        SetHight.setListViewHeightBasedOnChildren(game_center_lv);
                        adapter.setBetClickListener(new GameResultAdapter.BetClickListener() {
                            @Override
                            public void OnBetlick(View v, int i, String tag) {
                                Log.e("tag", "OnBetlick");
                                if (list.get(i).getIsopened().equals("0") || list.get(i).getIsopened().equals("3")) {
                                    intent = new Intent(mContext, BetModeActivity.class);
                                    intent.putExtra("where", "1");
                                    intent.putExtra("userId", userId);
                                    intent.putExtra("userToken", userToken);
                                    intent.putExtra("EgameName", EgameName);
                                    intent.putExtra("gameName", gameName);
                                    intent.putExtra("gameType", gameType);
                                    intent.putExtra("no", list.get(i).getNo());
                                    intent.putExtra("tag", tag);
                                    startActivityForResult(intent, 22);
                                    Log.e("tag", "OnBetlick");
                                } else {
                                    Intent intent = new Intent(mContext, MyBetDeatilActivity.class);
                                    intent.putExtra("usersid", userId);
                                    intent.putExtra("gameType", gameType);
                                    intent.putExtra("usertoken", userToken);
                                    intent.putExtra("gamename", EgameName);
                                    intent.putExtra("no", list.get(i).getNo());
                                    intent.putExtra("where", "1");
                                    startActivity(intent);
                                    Log.e("tag", "OnBetlick");
                                }
                            }
                        });
                        pullToRefreshScrollView.onRefreshComplete();
                        mLoadingAndRetryManager.showContent();
                    } else {
                        if (tag) {
                            if (time == 1) {
                                mLoadingAndRetryManager.showEmpty();
                            } else {
                                mLoadingAndRetryManager.showContent();
                                Toast.makeText(mContext, "已经加载到底部了！", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                    mLoadingAndRetryManager.showContent();

                } else if (status == -97 || status == -99) {
                    Toast.makeText(mContext, "账号已经过期请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("where", "2");
                    startActivity(intent);
                } else if (status < 0) {
                    Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                } else if (status == 99) {
                        /*抗攻击*/
                    ATK atK = gson.fromJson(result, ATK.class);
                    String vaild_str = atK.getVaild_str();
                    String vaildd_md5 = FormatUtils.md5(vaild_str);
                    prarams.put("vaild_str", vaildd_md5);
                    new GameBetListAsyncTask().execute();
                } else {
                    Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    mLoadingAndRetryManager.showContent();
                }
            } else {
                Toast.makeText(mContext, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
                mLoadingAndRetryManager.showContent();
            }
        }
    }

    private class GameInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (first) {
                mLoadingAndRetryManager.showLoading();
                first = false;
            }
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().GameCenBar_URL, gaprarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取游戏头部信息", "消息返回结果result" + result);
            onresult = false;
            if (result != null) {
                int status = 0;
                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                    new GameBetListAsyncTask().execute();
                    GaCBarDb gaCBarDb = gson.fromJson(result, GaCBarDb.class);
                    if (gaCBarDb.getData().getNearno() != null) {
                        gameSystem = gaCBarDb.getData().getGamesystem();
                        nearno = GetGameResult.getGno(gaCBarDb.getData().getNearno());
                        no = gaCBarDb.getData().getNearno();
                        be_tv1.setText("距离" + GetGameResult.getGno(gaCBarDb.getData().getNearno()) + "期截止还有");
                        if (timer == null) {
                            timer = new CountDown(gaCBarDb.getData().getRestsecond() * 1000, 1000, count_down_time, handler);
                        } else {
                            Log.e("timer", "-------");
                            timer.setMillisInFuture(gaCBarDb.getData().getRestsecond() * 1000);
                        }
                        timer.start();
                    } else {
                        be_tv1.setText("距离本期截止还有");
                        if (timer == null) {
                            timer = new CountDown(gaCBarDb.getData().getGametime() * 1000, 1000, count_down_time, handler);
                        } else {
                            timer.setMillisInFuture(gaCBarDb.getData().getGametime() * 1000);
                        }
                        timer.start();
                    }
                    if (gaCBarDb.getData().getLatestno() != null) {
                        be_tv2.setText(GetGameResult.getGno(gaCBarDb.getData().getLatestno()) + "期开奖结果");
                    } else {
                        be_tv2.setText("近期");
                    }
                    gameType = gaCBarDb.getData().getType();
                    Log.e("Tag", "" + gameType);
                    if (gaCBarDb.getData().getLatestno() != null && gaCBarDb.getData().getLatestresult() != null && gaCBarDb.getData().getLatestresult().size() > 0) {
                        if (gameType.equals("28") || gameType.equals("16") || gameType.equals("22")) {

                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.VISIBLE);
                            if (gaCBarDb.getData().getLatestresult().size() >= 4) {
                                lottery_result_tv1.setText("" + gaCBarDb.getData().getLatestresult().get(0));
                                lottery_result_tv2.setText("" + gaCBarDb.getData().getLatestresult().get(1));
                                lottery_result_tv3.setText("" + gaCBarDb.getData().getLatestresult().get(2));
                                lottery_result_tv4.setText("" + gaCBarDb.getData().getLatestresult().get(3));
                            }
                        } else if (gameType.equals("ssc")) {

                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.VISIBLE);
                            if (gaCBarDb.getData().getLatestresult().size() >= 4) {
                                tv_result71.setText("" + gaCBarDb.getData().getLatestresult().get(0));
                                tv_result72.setText("" + gaCBarDb.getData().getLatestresult().get(1));
                                tv_result73.setText("" + gaCBarDb.getData().getLatestresult().get(2));
                                tv_result74.setText("" + gaCBarDb.getData().getLatestresult().get(3));
                                tv_result75.setText("" + gaCBarDb.getData().getLatestresult().get(4));
                            }
                        } else if (gameType.equals("bjl")) {

                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            if (gaCBarDb.getData().getLatestresult().size() >= 1) {
                                tv_result2.setText(GetGameResult.getbjlrs(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                            }
                        } else if (gameType.equals("lh")) {

                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            if (gaCBarDb.getData().getLatestresult().size() >= 1) {
                                tv_result2.setText(GetGameResult.getlhrs(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                            }
                        } else if (gameType.equals("xync") || gameType.equals("pkww")) {
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.VISIBLE);
                            StringBuilder res = new StringBuilder();
                            for (int j = 0; j < gaCBarDb.getData().getLatestresult().size(); j++) {
                                res.append(gaCBarDb.getData().getLatestresult().get(j));
                                res.append(',');
                            }
                            if (res.length() > 0) {
                                res.deleteCharAt(res.length() - 1);
                            }
                            tv_result3.setText(res.toString());
                        } else if (gameType.equals("11") || gameType.equals("gy") || gameType.equals("xn")) {
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.VISIBLE);
                            StringBuilder res = new StringBuilder();
                            for (int j = 0; j < gaCBarDb.getData().getLatestresult().size() - 1; j++) {
                                res.append(gaCBarDb.getData().getLatestresult().get(j));
                                res.append('+');
                            }
                            if (res.length() > 0) {
                                res.deleteCharAt(res.length() - 1);
                            }
                            tv_result5.setText(new StringBuilder().append(res).append("=").toString());
                            if (gaCBarDb.getData().getLatestresult().size() >= 1) {
                                tv_result51.setText(gaCBarDb.getData().getLatestresult().get(gaCBarDb.getData().getLatestresult().size() - 1));
                            }
                        } else if (gameType.equals("36")) {

                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.VISIBLE);
                            StringBuilder res = new StringBuilder();
                            for (int j = 0; j < gaCBarDb.getData().getLatestresult().size() - 1; j++) {
                                res.append(gaCBarDb.getData().getLatestresult().get(j));
                                res.append('+');
                            }
                            if (res.length() > 0) {
                                res.deleteCharAt(res.length() - 1);
                            }
                            if (gaCBarDb.getData().getLatestresult().size() >= 4) {
                                int num1 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0));
                                int num2 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1));
                                int num3 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2));
                                tv_result5.setText(new StringBuilder().append(res).append("=").toString());
                                tv_result51.setText(GetGameResult.get36rs(num1, num2, num3));
                            }
                        } else if (gameType.equals("10") || gameType.equals("xs")) {
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            if (gaCBarDb.getData().getLatestresult().size() >= 1) {
                                tv_result2.setText(gaCBarDb.getData().getLatestresult().get(0));
                            }
                        }
                        if (gameType.equals("tbww")) {
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.VISIBLE);
                            if (gaCBarDb.getData().getLatestresult().size() >= 3) {
                                iv_1.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                                iv_2.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1))));
                                iv_3.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2))));
                            }
                        }
                        if (gameType.equals("ww")) {
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.VISIBLE);
                            if (gaCBarDb.getData().getLatestresult().size() >= 4) {
                                int num1 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0));
                                int num2 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1));
                                int num3 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2));
                                int num4 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(3));
                                tv_result4.setText("" + num1 + "+" + num2 + "+" + num3 + "=");
                                tv_result41.setText("" + num4);
                                tv_result42.setText(GetGameResult.getwwrs(num1, num2, num3, num4));
                            }

                        }
                        if (gameType.equals("dw")) {
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.VISIBLE);
                            if (gaCBarDb.getData().getLatestresult().size() >= 4) {
                                int num4 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(3));
                                tv_result41.setText("" + gaCBarDb.getData().getLatestresult().get(3));
                                tv_result42.setText(GetGameResult.getdwrs(num4));
                                tv_result4.setText(gaCBarDb.getData().getLatestresult().get(0) + "+" + gaCBarDb.getData().getLatestresult().get(1) + "+" + gaCBarDb.getData().getLatestresult().get(2) + "=");
                            }
                        }
                    } else {
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
                    today_win_loss.setText("" + gaCBarDb.getData().getMytoday().getProfit());
                    today_win_rate.setText(new StringBuilder().append(gaCBarDb.getData().getMytoday().getWinpercent()).append("%").toString());
                    mLoadingAndRetryManager.showContent();
                } else if (status == -97 || status == -99) {
                    Toast.makeText(mContext, "账号已经过期请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("where", "2");
                    startActivity(intent);
                } else if (status == 99) {
                        /*抗攻击*/
                    ATK atK = gson.fromJson(result, ATK.class);
                    String vaild_str = atK.getVaild_str();
                    Log.e("tag", "" + vaild_str);
                    String vaildd_md5 = FormatUtils.md5(vaild_str);
                    Log.e("tag", "" + vaildd_md5);
                    gaprarams.put("vaild_str", vaildd_md5);
                    new GameInfoAsyncTask().execute();
                } else {
                    Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    mLoadingAndRetryManager.showContent();
                }
            } else {
                Toast.makeText(mContext, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
                mLoadingAndRetryManager.showContent();
            }
        }
    }

    @Override
    public boolean onBackPressed() {
        Log.e("tag", "onBackPressed1");
        if (BackHandlerHelper.handleBackPress(this)) {
            Log.e("tag", "onBackPressed2");
            if (System.currentTimeMillis() - lastBackPress < 1000) {
                super.onBackPressed();
            } else {
                Log.e("tag", "onBackPressed3");
                lastBackPress = System.currentTimeMillis();
                Toast.makeText(mContext, "再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    public void onDestroy() {
        Log.e("tag", "onDestroy");
        if (timer != null) {
            timer.cancel();
            timer = null;
            exit = true;
        }
        isVisible = false;
        handler.removeMessages(5);
        handler.removeCallbacks(thread);
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (userToken != null) {
            Log.e("tagi", "+onResume");
            if (!ishidden) {
                isVisible = true;
            } else {
                isVisible = false;
                handler.removeMessages(5);
                handler.removeCallbacks(thread);
            }
            if (!ishidden) {
                if (!onresult) {
                    if (count_down_time.getText().toString().equals("正在开奖中，请稍后...")) {
                        Message msg1 = new Message();
                        msg1.what = 5;
                        handler.sendMessage(msg1);
                        if (timer != null) {
                            timer.cancel();
                        }
                        handler.removeMessages(5);
                        handler.removeCallbacks(thread);
                        first = true;
                        tag = true;
                        page = 1;
                        prarams.clear();
                        prarams.put("usersid", userId);
                        prarams.put(" usertoken", userToken);
                        prarams.put("gamename", EgameName);
                        gaprarams.clear();
                        gaprarams.put("usersid", userId);
                        gaprarams.put("usertoken", userToken);
                        gaprarams.put("gamename", EgameName);
                        new GameInfoAsyncTask().execute();
                    }
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isVisible = false;
        handler.removeMessages(5);
        handler.removeCallbacks(thread);
    }

    private void traversal() {
        Log.e("tagi", "" + isVisible);
        if (isVisible) {
            Log.e("taga", "" + activity);
            if (game_center_lv.getCount() > 0 && game_center_lv != null) {
                for (int i = 0; i < game_center_lv.getCount(); i++) {
                    if (isVisible) {
                        View view = game_center_lv.getChildAt(i);
                        if (view != null) {
                            TextView tv_game_noid = view.findViewById(R.id.tv_game_noid);
                            Button btn_bet = view.findViewById(R.id.btn_bet);
                            if (tv_game_noid.getText().toString().equals(nearno)) {
                                btn_bet.setText("开奖中");
                                // btn_bet.setClickable(false);

                            }
                        }

                    }

                }
            }

        }
    }

    private class CheckGameInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(Url, prarams1);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取是否开奖的结果", "消息返回结果result" + result);
            if (result != null) {
                if (result.equals("1")) {
                    isopend = true;
                    handler.removeMessages(5);
                    handler.removeCallbacks(thread);
                    isopend = false;
                    if (timer != null) {
                        timer.cancel();
                    }
                    time = 1;
                    tag = true;
                    page = 1;
                    prarams.clear();
                    prarams.put("usersid", userId);
                    prarams.put(" usertoken", userToken);
                    prarams.put("gamename", EgameName);
                    gaprarams.clear();
                    gaprarams.put("usersid", userId);
                    gaprarams.put("usertoken", userToken);
                    gaprarams.put("gamename", EgameName);
                    // new GameBetListAsyncTask().execute();
                    new GameInfoAsyncTask().execute();

                } else {
                    isopend = false;
                }

            } else {
                Toast.makeText(mContext, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
                mLoadingAndRetryManager.showContent();
            }
        }
    }
    private class GetXnbAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().GXnb_URL, prarams1);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("领取虚拟币", "消息返回结果result" + result);
            if (result != null) {

                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                if(entry.getStatus()==1){
                    Toast.makeText(mContext, "领取成功", Toast.LENGTH_SHORT).show();
                    tv_my_coins.setText(entry.getXnb());
                }else{
                    Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("taghi", "onHiddenChanged " + hidden);
        if (!hidden) {
            // initStatus();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
            if (sharedPreferences.getString("is_login", "").equals("true")) {
                userId = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
                userToken = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
                if (userToken == null || userId == null || userToken.equals("") || userId.equals("")) {
                    ll_game_center1.setVisibility(View.VISIBLE);
                    ll_game_center.setVisibility(View.GONE);
                } else {
                    ll_game_center.setVisibility(View.VISIBLE);
                    ll_game_center1.setVisibility(View.GONE);
                    ishidden = false;
                    isVisible = true;
                    if (!first) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        time = 1;
                        tag = true;
                        list.clear();
                        page = 1;
                        prarams.clear();
                        prarams.put("usersid", userId);
                        prarams.put(" usertoken", userToken);
                        prarams.put("gamename", EgameName);
                        gaprarams.clear();
                        gaprarams.put("usersid", userId);
                        gaprarams.put("usertoken", userToken);
                        gaprarams.put("gamename", EgameName);
                        new GameInfoAsyncTask().execute();
                    }

                    Log.e("taghi", "onHiddenChanged " + hidden);
                }
            } else {
                ll_game_center1.setVisibility(View.VISIBLE);
                ll_game_center.setVisibility(View.GONE);
            }
        } else {
            ishidden = true;
            isVisible = false;
            handler.removeMessages(5);
            handler.removeCallbacks(thread);
        }

    }


}
