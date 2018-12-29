package com.ruixin.administrator.ruixinapplication.gamecenter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.LotteryView;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.BetMultipelAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.CheckNumberBetAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.GameNumberAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.MyBetModeAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetMode;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetMultipel;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetMultipelDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.DatabaseHelper;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GaCBarDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GameName1;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessageEvent2;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessagenextEvent;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MoneyDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MybetModeDB;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MymodeDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.domain.GetNumber;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.BetFragment1;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.BetFragment2;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.BetFragment3;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.Betmode28Fragment;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.BetmodeDWFragment;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.BetmodeSSCFragment;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.BetmodeTBWWFragment;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.popwindow.MyBetModePop;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown;
import com.ruixin.administrator.ruixinapplication.utils.CustomViewPager;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.GetGameResult;
import com.ruixin.administrator.ruixinapplication.utils.SetHight;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 投注模式+投注界面
 */
public class BetModeActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    public static int mycoin;
    public static String Totalpoints;
    LinearLayout ll_center_bar;
    LinearLayout ll_head_result;
    LinearLayout ll_head_result1;
    LinearLayout ll_head_result4;
    LinearLayout ll_head_result5;
    LinearLayout ll_head_result6;
    LinearLayout ll_mode_name;
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
    TextView   tv_tbid;
    static TextView tv_save;
    LinearLayout ll_save;
    CountDown timer;
    private TextView tv_title;
    LinearLayout back_arrow;
    String userId;
    String userToken;
    String EgameName;
    String gameName;
    static String gameType;
    String result;
    String mode;
    String mulp;
    static String where;
  /*  String tbChk;
    String tbNum;*/
    RelativeLayout rl_bet_mode;
    String coin;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> gaprarams = new HashMap<String, String>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> prarams1 = new HashMap<String, String>();
    RadioGroup rg_bet;
    RecyclerView rcy_bet_multiple;
    RadioButton v1;
    RadioButton v2;
    RadioButton v3;
    ListView my_bet_mode_lv;
    public static Betmode28Fragment fragment28;
    public static BetmodeDWFragment fragmentdw;
    public static BetmodeTBWWFragment fragmenttbww;
    public static BetmodeSSCFragment fragmentssc;
    private ViewPager bet_mode_vp;
    //private CustomViewPager game_number_vp;
    private FrameLayout game_number_vp;
    private OneFmAdapter adapter;
    private List<Fragment> newsList = new ArrayList<>();
    private List<Fragment> newsList2 = new ArrayList<>();
    private int position;
    private static int position2;
    private static List<BetMultipel> list1 = new ArrayList<BetMultipel>();
    BetMultipelAdapter adapter1;
    Button auto_coin;
    private TextView add_my_betmode;
    LinearLayout ll_bottom;
    LinearLayout ll_smode;
    EditText et_mode;
    Button clear_btn;
    static TextView total_points;
    ImageView tv_clear;
    public static int RESULT_CODE = 22;
    private DatabaseHelper dbHelper;
    ImageView  iv_arrow;
 static    List<MybetModeDB.DataBean.ModeljsonBean> list = new ArrayList<>();
    int gameSystem;
    String no1 = "";//最新的一期
    String betno;//传递过来的期号
    String Url;
  static int totalpoints=0;
    boolean first = true;
    boolean isopend = false;
    boolean isvisible = true;
    static String type = "add";
    MyBetModeAdapter mymodeadapter;
   static String tbChk;
 static    String tbNum;
    private AlertDialog mAlertDialog;
    String tag;
    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
               if (gameType.equals("ssc") || gameType.equals("pkww") || gameType.equals("xync")||gameType.equals("dw")||gameType.equals("tbww")) {
                    initParams();

                } else {
                    fragment28.SetP(new Betmode28Fragment.SetParams() {
                        @Override
                        public void setParams(String tbChk, String tbNum, String Totalpoints) {
                            total_points.setText(Totalpoints);
                        }
                    });
                }

            } else if (msg.what == 3) {
                type = "update";
            }
        }
    };

    public   static   List  removeDuplicate(List <MoneyDb>list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  ((list.get(j).getNumber())==(list.get(i).getNumber()))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }
    private static void initParams() {
        StringBuilder sb = new StringBuilder();
        StringBuilder nb = new StringBuilder();
        int sum=0;
        if( RuiXinApplication.getInstance().getMoneyList()!=null){
            removeDuplicate(RuiXinApplication.getInstance().getMoneyList());
        for(int j = 0; j< RuiXinApplication.getInstance().getMoneyList().size(); j++){
            nb.append(   RuiXinApplication.getInstance().getMoneyList().get(j).getNumber());
            nb.append('=');
            nb.append(   RuiXinApplication.getInstance().getMoneyList().get(j).getMoney());
            nb.append(",");
            sb.append(   RuiXinApplication.getInstance().getMoneyList().get(j).getNumber());
            sb.append('=');
            sb.append("on");
            sb.append(",");
            String d=   RuiXinApplication.getInstance().getMoneyList().get(j).getMoney();
            sum +=Integer.parseInt(d);
        }
        if(nb.length()>0){
            nb.deleteCharAt(nb.length()-1);
        }
        if(sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
        }
        totalpoints=sum;
        tbChk=sb.toString();
        tbNum=nb.toString();
        total_points.setText(""+totalpoints);
        }else{
            tbChk=null;
            tbNum=null;
            total_points.setText("0");
        }
    }

    Runnable thread = new Runnable() {
        @Override
        public void run() {// TODO Auto-generated method stub
            prarams1.put("usersid", userId);
            prarams1.put(" usertoken", userToken);
            Url = URL.getInstance().CheckGame_URL + "?game=" + EgameName + "&no=" + no1;
            new CheckGameInfoAsyncTask().execute();
            if (!isopend) {
                if (isvisible) {
                    mhandler.postDelayed(this, 5000);
                }
            }
        }
    };
    @SuppressLint("HandlerLeak")
    public Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

                if (msg.what == 5) {
                    first = false;
                    if (isvisible) {
                    if (where.equals("1")) {
                        Log.e("tag","tag----------"+ tag);
                        if(tag.equals("0")){
                            if((Long.parseLong(betno)<Long.parseLong(no1))||(Long.parseLong(betno)==Long.parseLong(no1))){
                                showReminderDialog(BetModeActivity.this);
                            }
                        }
                    }
                    if (gameSystem == 1) {
                        mhandler.postDelayed(new Runnable() {
                            public void run() {
                                new GameInfoAsyncTask().execute();
                            }

                        }, 2500);
                    } else if (gameSystem == 2) {
                        mhandler.postDelayed(new Runnable() {
                            public void run() {
                               // isopend = false;
                                if (!isopend) {
                                    mhandler.post(new Runnable() {
                                        @Override
                                        public void run() {// TODO Auto-generated method stub
                                            mhandler.post(thread);
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
    private void showReminderDialog(Context context) {
        Log.e("tag", "showReminderDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("本期已截止，是否要进入下期?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mAlertDialog != null){
                            mAlertDialog.dismiss();
                            mAlertDialog=null;
                        }
                    }
                })
                .setPositiveButton("进入下期", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     doNextno();
                        if (mAlertDialog != null){
                            mAlertDialog.dismiss();
                            mAlertDialog=null;
                        }
                    }
                });
        if (mAlertDialog == null){
            mAlertDialog = builder.create();
            mAlertDialog.show();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_mode);
        RuiXinApplication.getInstance().setMoneyList(null);
        Intent intent = getIntent();
        where = intent.getStringExtra("where");
        userId = intent.getStringExtra("userId");
        userToken = intent.getStringExtra("userToken");
        EgameName = intent.getStringExtra("EgameName");
        gameName = intent.getStringExtra("gameName");
        gameType = intent.getStringExtra("gameType");
        if (where.equals("1")) {
            betno = intent.getStringExtra("no");
            tag=intent.getStringExtra("tag");
        }
        initView();
        initStatus();
        //构建一个 MyDatabaseHelper 对象，通过构造函数将数据库名指定为 BookStore.db
        dbHelper = new DatabaseHelper(this, "FavorsGameStore.db", null, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isvisible = false;
        timer.cancel();
        mhandler.removeMessages(5);
        mhandler.removeCallbacks(thread);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isvisible = true;
    }

    private void initView() {
        rl_bet_mode = findViewById(R.id.rl_bet_mode);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(rl_bet_mode, null);
        tv_title = findViewById(R.id.tv_title);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText(gameName);
        be_tv1 = findViewById(R.id.be_tv1);
        count_down_time = findViewById(R.id.count_down_time);
        count_down_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (count_down_time.getText().toString().equals("正在开奖中，请稍后...")) {
                    if (where.equals("1")) {
                        if(Long.parseLong(betno)==Long.parseLong(no1)){
                            Log.e("tag_betno", "" + betno+"______________"+no1);
                            tv_save.setText("已截止");
                            ll_save.setClickable(false);
                        }

                    }
                }

            }
        });
        be_tv2 = findViewById(R.id.be_tv2);
        ll_head_result = findViewById(R.id.ll_head_result);
        ll_head_result1 = findViewById(R.id.ll_head_result1);
        ll_head_result4 = findViewById(R.id.ll_head_result4);
        ll_head_result5 = findViewById(R.id.ll_head_result5);
        ll_head_result6 = findViewById(R.id.ll_head_result6);
        lottery_result_tv1 = findViewById(R.id.lottery_result_tv1);
        lottery_result_tv2 = findViewById(R.id.lottery_result_tv2);
        lottery_result_tv3 = findViewById(R.id.lottery_result_tv3);
        lottery_result_tv4 = findViewById(R.id.lottery_result_tv4);
        ll_head_result7 =findViewById(R.id.ll_head_result7);
        tv_result71 =findViewById(R.id.tv_result71);
        tv_result72 =findViewById(R.id.tv_result72);
        tv_result73 = findViewById(R.id.tv_result73);
        tv_result74 =findViewById(R.id.tv_result74);
        tv_result75 = findViewById(R.id.tv_result75);
        tv_result1 = findViewById(R.id.tv_result1);
        iv_1 = findViewById(R.id.iv_1);
        iv_2 = findViewById(R.id.iv_2);
        iv_3 = findViewById(R.id.iv_3);
        tv_result2 = findViewById(R.id.tv_result2);
        tv_result3 = findViewById(R.id.tv_result3);
        tv_result4 = findViewById(R.id.tv_result4);
        tv_result41 = findViewById(R.id.tv_result41);
        tv_result42 = findViewById(R.id.tv_result42);
        tv_result5 = findViewById(R.id.tv_result5);
        tv_result51 = findViewById(R.id.tv_result51);
        tv_result12 = findViewById(R.id.tv_result12);
        tv_result13 = findViewById(R.id.tv_result13);
        tv_result14 = findViewById(R.id.tv_result14);
        ll_center_bar = findViewById(R.id.ll_center_bar);
        ll_mode_name = findViewById(R.id.ll_mode_name);
        ll_center_bar.setFocusable(true);
        ll_center_bar.setFocusableInTouchMode(true);
        ll_center_bar.requestFocus();
        tv_chane_coins = findViewById(R.id.tv_chane_coins);
        tv_my_coins = findViewById(R.id.tv_my_coins);
        today_part = findViewById(R.id.today_part);
        today_win_loss = findViewById(R.id.today_win_loss);
        today_win_rate = findViewById(R.id.today_win_rate);

        iv_arrow = findViewById(R.id.iv_arrow);
        et_mode = findViewById(R.id.et_mode);
        clear_btn = findViewById(R.id.clear_btn);
        ll_save = findViewById(R.id.ll_save);
        tv_save = findViewById(R.id.tv_save);
        if (where.equals("1")) {
            if(tag.equals("0")){
                tv_save.setText("投注");

            }else{
                tv_save.setText("已截止");
            }
            ll_mode_name.setVisibility(View.GONE);
        } else {
            tv_save.setText("保存");
            ll_mode_name.setVisibility(View.VISIBLE);
        }
        ll_save.setOnClickListener(new MyOnclick());
        ll_smode = findViewById(R.id.ll_smode);
        clear_btn.setSelected(false);
        et_mode.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                clear_btn.setSelected(true);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听
                Log.e("输入前确认执行该方法", "开始输入");

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                Log.e("输入结束执行该方法", "输入结束");

            }
        });
        clear_btn.setOnClickListener(new MyOnclick());
        game_number_vp = findViewById(R.id.game_number_vp);
        Bundle bundle = new Bundle();
        bundle.putString("gameType", gameType);
        bundle.putString("gameName", EgameName);
        bundle.putString("userId", userId);
        bundle.putString("userToken", userToken);
        bundle.putString("where", where);
        if (where.equals("1")) {
            bundle.putString("no", betno);
            Log.e("tag","no---------"+betno);
        }
        bundle.putString("mode", mode);
        if (gameType.equals("ww") || gameType.equals("lh") || gameType.equals("dw") || gameType.equals("tbww") || gameType.equals("ssc") || gameType.equals("xync") || gameType.equals("pkww") || gameType.equals("bjl")) {
            ll_smode.setVisibility(View.GONE);
        }
        if (gameType.equals("28") || gameType.equals("16") || gameType.equals("11") || gameType.equals("10") || gameType.equals("36") || gameType.equals("ww") || gameType.equals("xs") || gameType.equals("xn") || gameType.equals("22") || gameType.equals("lh") || gameType.equals("gy") || gameType.equals("bjl")) {
            FragmentManager fm = getSupportFragmentManager();
            //开启事务
            FragmentTransaction transaction = fm.beginTransaction();
            //替换
            fragment28 = new Betmode28Fragment();
            fragment28.setArguments(bundle);
            transaction.replace(R.id.game_number_vp, fragment28);
            //提交事务
            transaction.commit();
        } else if (gameType.equals("dw")) {
            FragmentManager fm = getSupportFragmentManager();
            //开启事务
            FragmentTransaction transaction = fm.beginTransaction();
            //替换
            fragmentdw = new BetmodeDWFragment();
            fragmentdw.setArguments(bundle);
            transaction.replace(R.id.game_number_vp, fragmentdw);
            //提交事务
            transaction.commit();
            //game_number_vp.setCurrentItem(1);
        } else if (gameType.equals("tbww")) {
            fragmenttbww = new BetmodeTBWWFragment();
            FragmentManager fm = getSupportFragmentManager();
            //开启事务
            FragmentTransaction transaction = fm.beginTransaction();
            //替换
            fragmenttbww.setArguments(bundle);
            transaction.replace(R.id.game_number_vp, fragmenttbww);
            //提交事务
            transaction.commit();
            //  game_number_vp.setCurrentItem(2);
        } else if (gameType.equals("pkww") || gameType.equals("xync") || gameType.equals("ssc")) {
            fragmentssc = new BetmodeSSCFragment();
            FragmentManager fm = getSupportFragmentManager();
            //开启事务
            FragmentTransaction transaction = fm.beginTransaction();
            //替换
            fragmentssc.setArguments(bundle);
            transaction.replace(R.id.game_number_vp, fragmentssc);
            //提交事务
            transaction.commit();
            //game_number_vp.setCurrentItem(2);
        }
        gaprarams.put("usersid", userId);
        gaprarams.put("usertoken", userToken);
        gaprarams.put("gamename", EgameName);
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        prarams.put("gamename", EgameName);

        new GameInfoAsyncTask().execute();
        rcy_bet_multiple = findViewById(R.id.rcy_bet_multiple);
        tv_tbid = findViewById(R.id.tv_tbid);
        bet_mode_vp = findViewById(R.id.vp_bet_mode);

        ll_bottom = findViewById(R.id.ll_bottom);
        add_my_betmode = findViewById(R.id.add_my_betmode);
        my_bet_mode_lv = findViewById(R.id.my_bet_mode_lv);
        tv_clear = findViewById(R.id.tv_clear);
        tv_clear.setOnClickListener(new MyOnclick());
        total_points = findViewById(R.id.total_points);
        add_my_betmode.setOnClickListener(new MyOnclick());
        initVp();
        rg_bet = findViewById(R.id.rg_bet);
        setListener();
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        auto_coin = findViewById(R.id.auto_coin);//自定义金额
        auto_coin.setOnClickListener(new MyOnclick());
        list1 = BetMultipelDb.getList();
        adapter1 = new BetMultipelAdapter(this, list1);
        rcy_bet_multiple.setAdapter(adapter1);
        rcy_bet_multiple.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        adapter1.setOnItemClickListener(new BetMultipelAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, String data) {
                mulp = data;
                if(mulp.equals("反选")){
                    if (gameType.equals("ssc") || gameType.equals("pkww") || gameType.equals("xync")||gameType.equals("dw")||gameType.equals("tbww")) {
                        Toast.makeText(BetModeActivity.this,"暂不支持反选",Toast.LENGTH_SHORT).show();

                    } else {
                        EventBus.getDefault().post(new MessageEvent2(mulp));//发送倍数
                    }
                }else{
                    EventBus.getDefault().post(new MessageEvent2(mulp));//发送倍数
                }

            }
        });
     //   mLoadingAndRetryManager.showContent();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    private void initVp() {
        Log.e("tag", "initView()");
        for (int i = 0; i < 3; i++) {
            bet_mode_vp.setCurrentItem(i);
        }
        newsList.add(new BetFragment1());
        newsList.add(new BetFragment2());
        newsList.add(new BetFragment3());
        Log.e("tag", "newsList()" + newsList);
        //设置viewpager适配器
        adapter = new OneFmAdapter(getSupportFragmentManager(), newsList);
        bet_mode_vp.setAdapter(adapter);
        //两个viewpager切换不重新加载
        bet_mode_vp.setOffscreenPageLimit(2);
        //设置默认
        bet_mode_vp.setCurrentItem(0);
        //设置viewpager监听事件
        bet_mode_vp.setOnPageChangeListener(this);
    }

    private void setListener() {
        Log.e("tag", "setListener()");
        rg_bet.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中
        rg_bet.check(R.id.v1);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.v1://选择模式1
                    position = 0;
                    break;
                case R.id.v2://选择模式2
                    position = 1;
                    break;
                case R.id.v3://选择模式3
                    position = 2;
                    break;
            }
            bet_mode_vp.setCurrentItem(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            rg_bet.check(R.id.v1);
        } else if (position == 1) {
            rg_bet.check(R.id.v2);
        } else if (position == 2) {
            rg_bet.check(R.id.v3);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.auto_coin:
                    final AlertDialog.Builder builder = new AlertDialog.Builder(BetModeActivity.this);
                    // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view1 = LayoutInflater.from(BetModeActivity.this).inflate(R.layout.dialog_auto_coin, null);
// 设置我们自己定义的布局文件作为弹出框的Content
                    builder.setView(view1);
//这个位置十分重要，只有位于这个位置逻辑才是正确的
                    final AlertDialog dialog = builder.show();
                    final EditText et_points = view1.findViewById(R.id.et_points);
                    final TextView tv1 = view1.findViewById(R.id.tv1);
                    tv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            et_points.setText(tv1.getText().toString());
                        }
                    });
                    final TextView tv2 = view1.findViewById(R.id.tv2);
                    tv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            et_points.setText(tv2.getText().toString());
                        }
                    });
                    final TextView tv3 = view1.findViewById(R.id.tv3);
                    tv3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            et_points.setText(tv3.getText().toString());
                        }
                    });
                    final TextView tv4 = view1.findViewById(R.id.tv4);
                    tv4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            et_points.setText(tv4.getText().toString());
                        }
                    });
                    final TextView tv5 = view1.findViewById(R.id.tv5);
                    tv5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            et_points.setText(tv5.getText().toString());
                        }
                    });
                    final TextView tv6 = view1.findViewById(R.id.tv6);
                    tv6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            et_points.setText(tv6.getText().toString());
                        }
                    });
                    Button commit = view1.findViewById(R.id.commit);
                    commit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            coin = et_points.getText().toString();
                            /*if( RuiXinApplication.getInstance().getMoneyList()!=null){
                                RuiXinApplication.getInstance().getMoneyList().clear();
                            }*/
                            if (coin.contains("K")) {
                                coin = String.valueOf(Integer.parseInt(coin.substring(0, coin.length() - 1)) * 1000);
                                Totalpoints = total_points.getText().toString();
                                if (!Totalpoints.equals("0")) {
                                    mulp = String.valueOf(Double.parseDouble(coin) / Double.parseDouble(Totalpoints));
                                } else {
                                    mulp = "1";
                                }
                                EventBus.getDefault().post(new MessageEvent2(mulp));
                            }else{
                                Totalpoints = total_points.getText().toString();
                                if (!Totalpoints.equals("0")) {
                                    mulp = String.valueOf(Double.parseDouble(coin) / Double.parseDouble(Totalpoints));
                                } else {
                                    mulp = "1";
                                }
                                EventBus.getDefault().post(new MessageEvent2(mulp));
                            }
                            dialog.dismiss();
                        }
                    });
                    break;
                case R.id.add_my_betmode:
                    if(add_my_betmode.getText().toString().equals("我的模式")){
                        new MymodeAsyncTask2().execute();

                    }else{
                        add_my_betmode.setText("我的模式");
                        my_bet_mode_lv.setVisibility(View.GONE);
                        iv_arrow.setImageResource(R.drawable.downarrow);
                    }
                    break;
                case R.id.clear_btn:
                    et_mode.setText("");
                    break;
                case R.id.tv_clear:
                    total_points.setText("0");
                    if( RuiXinApplication.getInstance().getMoneyList()!=null){
                        RuiXinApplication.getInstance().getMoneyList().clear();
                    }
                    if (gameType.equals("dw")) {
                        Message msg1 = new Message();
                        msg1.what = 2;
                        fragmentdw.handler.sendMessage(msg1);//清空listview
                    } else if (gameType.equals("tbww")) {
                        Message msg1 = new Message();
                        msg1.what = 2;
                        fragmenttbww.handler.sendMessage(msg1);//清空listview
                    } else if (gameType.equals("ssc") || gameType.equals("pkww") || gameType.equals("xync")) {
                        Message msg1 = new Message();
                        msg1.what = 2;
                        fragmentssc.handler.sendMessage(msg1);//清空listview
                    } else {
                        Message msg1 = new Message();
                        msg1.what = 2;
                        fragment28.handler.sendMessage(msg1);//清空listview
                    }

                    break;
                case R.id.ll_save:
                    performTask();
                    break;
            }
        }
    }
//点击投注或者保存的具体操作
    private void performTask() {
        if (gameType.equals("dw")||gameType.equals("ssc") || gameType.equals("pkww") || gameType.equals("xync")||gameType.equals("tbww")) {
                    prarams.put("tbChk", tbChk);
                    prarams.put("tbNum", tbNum);
                    if (where.equals("1")) {
                        prarams.put("no", betno);
                        new BetAsyncTask().execute();
                    } else {
                        String modename = et_mode.getText().toString();
                        String tbid = tv_tbid.getText().toString();
                        if (modename.equals("")) {
                            Toast.makeText(BetModeActivity.this, "模式名不能为空！", Toast.LENGTH_SHORT).show();
                        } else {

                            prarams.put("modelname", modename);
                            if (type.equals("add")) {
                                new SaveModeAsyncTask().execute();
                            } else {
                                prarams.put("tbID", tbid);
                                new USaveModeAsyncTask().execute();
                                type = "add";
                            }
                        }
                    }
        } else {
            fragment28.SetP(new Betmode28Fragment.SetParams() {
                @Override
                public void setParams(String tbChk, String tbNum, String Totalpoints) {
                    prarams.put("tbChk", tbChk);
                    prarams.put("tbNum", tbNum);
                    if (where.equals("1")) {
                        prarams.put("no", betno);
                        new BetAsyncTask().execute();
                    } else {
                        String modename = et_mode.getText().toString();
                        String tbid = tv_tbid.getText().toString();
                        if (modename.equals("")) {
                            Toast.makeText(BetModeActivity.this, "模式名不能为空！", Toast.LENGTH_SHORT).show();
                        } else {
                            prarams.put("modelname", modename);
                            if (type.equals("add")) {
                                new SaveModeAsyncTask().execute();
                            } else {
                                prarams.put("tbID", tbid);
                                new USaveModeAsyncTask().execute();
                                type = "add";
                            }
                        }
                    }

                }
            });
        }
        et_mode.setText("");
        tv_tbid.setText("");
    }
    //进入最新一期
    private void doNextno(){
        if (where.equals("1")) {
            Log.e("no", "no" + betno);
            if(Long.parseLong(betno)==Long.parseLong(no1)){
                betno = String.valueOf(Long.parseLong(betno)+1);
            }else  if(Long.parseLong(betno)<Long.parseLong(no1)){
                betno=no1;
            }

            Log.e("no", "no-------" + betno);
            tv_save.setText("投注");
            total_points.setText("0");
            ll_save.setClickable(true);
            ll_mode_name.setVisibility(View.GONE);
            EventBus.getDefault().post(new MessagenextEvent("进入下期",betno));
        }
    }

    private class GameInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (first) {
                mLoadingAndRetryManager.showLoading();
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
            mLoadingAndRetryManager.showContent();
            Log.e("获取游戏头部信息", "消息返回结果result" + result);
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
                    GaCBarDb gaCBarDb = gson.fromJson(result, GaCBarDb.class);
                    gameSystem = gaCBarDb.getData().getGamesystem();
                    if (gaCBarDb.getData().getNearno() != null) {
                        no1 = gaCBarDb.getData().getNearno();
                        be_tv1.setText("距离" + GetGameResult.getGno(gaCBarDb.getData().getNearno()) + "期截止还有");
                        timer = new CountDown(gaCBarDb.getData().getRestsecond() * 1000, 1000, count_down_time, mhandler);
                        timer.start();
                    } else {
                        be_tv1.setText("距离本期截止还有");
                        timer = new CountDown(gaCBarDb.getData().getGametime() * 1000, 1000, count_down_time, mhandler);
                        timer.start();
                    }
                    if (gaCBarDb.getData().getLatestno() != null) {
                        be_tv2.setText(GetGameResult.getGno(gaCBarDb.getData().getLatestno()) + "期开奖结果");
                    } else {
                        be_tv2.setText("近期");
                    }
                    gameType = gaCBarDb.getData().getType();
                    if (gaCBarDb.getData().getLatestno() != null && gaCBarDb.getData().getLatestresult() != null) {

                        if (gameType.equals("28") || gameType.equals("16") || gameType.equals("22")) {
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.VISIBLE);
                            lottery_result_tv1.setText("" + gaCBarDb.getData().getLatestresult().get(0));
                            lottery_result_tv2.setText("" + gaCBarDb.getData().getLatestresult().get(1));
                            lottery_result_tv3.setText("" + gaCBarDb.getData().getLatestresult().get(2));
                            lottery_result_tv4.setText("" + gaCBarDb.getData().getLatestresult().get(3));
                        } else if (gameType.equals("ssc")) {
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
                        } else if (gameType.equals("bjl")) {
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            tv_result2.setText(GetGameResult.getbjlrs(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                        } else if (gameType.equals("lh")) {
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            tv_result2.setText(GetGameResult.getlhrs(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                        } else if (gameType.equals("xync") || gameType.equals("pkww")) {
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            tv_result51.setText(gaCBarDb.getData().getLatestresult().get(gaCBarDb.getData().getLatestresult().size() - 1));
                        } else if (gameType.equals("36")) {
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            int num1 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0));
                            int num2 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1));
                            int num3 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2));
                            tv_result5.setText(new StringBuilder().append(res).append("=").toString());
                            tv_result51.setText(GetGameResult.get36rs(num1, num2, num3));
                        } else if (gameType.equals("10") || gameType.equals("xs")) {
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            tv_result2.setText(gaCBarDb.getData().getLatestresult().get(0));
                        }
                        if (gameType.equals("tbww")) {
                            ll_head_result4.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.VISIBLE);
                            iv_1.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                            iv_2.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1))));
                            iv_3.setImageResource(GetGameResult.getTbww(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2))));
                        }
                        if (gameType.equals("ww")) {
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.VISIBLE);
                            int num1 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0));
                            int num2 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(1));
                            int num3 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(2));
                            int num4 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(3));
                            tv_result4.setText("" + num1 + "+" + num2 + "+" + num3+"=");
                            tv_result41.setText(""+ num4);
                            tv_result42.setText(GetGameResult.getwwrs(num1, num2, num3, num4));

                        }
                        if (gameType.equals("dw")) {
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result4.setVisibility(View.VISIBLE);
                            int num4 = Integer.parseInt(gaCBarDb.getData().getLatestresult().get(3));
                            tv_result41.setText("" + gaCBarDb.getData().getLatestresult().get(3));
                            tv_result42.setText(GetGameResult.getdwrs(num4));
                            tv_result4.setText(gaCBarDb.getData().getLatestresult().get(0) + "+" + gaCBarDb.getData().getLatestresult().get(1) + "+" + gaCBarDb.getData().getLatestresult().get(2) + "=");
                        }
                    } else {
                        ll_head_result6.setVisibility(View.GONE);
                        ll_head_result7.setVisibility(View.GONE);
                        ll_head_result5.setVisibility(View.GONE);
                        tv_result3.setVisibility(View.GONE);
                        ll_head_result1.setVisibility(View.GONE);
                        ll_head_result.setVisibility(View.GONE);
                        ll_head_result4.setVisibility(View.GONE);
                        tv_result2.setVisibility(View.VISIBLE);
                        tv_result2.setText("尚未开奖");
                    }
                    mycoin = gaCBarDb.getData().getMytoday().getPoints();
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
                    Unlogin.doLogin(BetModeActivity.this);
                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(gaprarams,result,   new GameInfoAsyncTask());
                }
            } else {
                Toast.makeText(BetModeActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class MymodeAsyncTask2 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().MyBetMode_URL, gaprarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取我的模式称列表", "消息返回结果result" + result);
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
                    MybetModeDB mybetmodeDb = gson.fromJson(result, MybetModeDB.class);
                    list.clear();
                    list = mybetmodeDb.getData().getModeljson();
                    if (list != null && list.size() > 0) {
                        my_bet_mode_lv.setVisibility(View.VISIBLE);
                        mymodeadapter=new MyBetModeAdapter(BetModeActivity.this,list,userId,userToken,EgameName);
                        my_bet_mode_lv.setAdapter(mymodeadapter);
                        setListViewHeight(my_bet_mode_lv);
                        add_my_betmode.setText("添加模式");
                        iv_arrow.setImageResource(R.drawable.msup);
                        my_bet_mode_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Log.e("tag", "" + list.get(i));
                                RuiXinApplication.getInstance().setMoneyList(null);
                                position2=i;
                                if(gameType.equals("dw")){
                                    Message msg1 = new Message();
                                    msg1.what =3;
                                    BetModeActivity.fragmentdw.handler.sendMessage(msg1);
                                  my_bet_mode_lv.setVisibility(View.GONE);
                                    add_my_betmode.setText("我的模式");
                                    iv_arrow.setImageResource(R.drawable.downarrow);
                                }else if(gameType.equals("tbww")){
                                    Message msg1 = new Message();
                                    msg1.what =3;
                                    BetModeActivity.fragmenttbww.handler.sendMessage(msg1);
                                    my_bet_mode_lv.setVisibility(View.GONE);
                                    add_my_betmode.setText("我的模式");
                                    iv_arrow.setImageResource(R.drawable.downarrow);
                                }else if(gameType.equals("ssc")||gameType.equals("pkww")||gameType.equals("xync")){
                                    Message msg1 = new Message();
                                    msg1.what =3;
                                    BetModeActivity.fragmentssc.handler.sendMessage(msg1);
                                    my_bet_mode_lv.setVisibility(View.GONE);
                                    add_my_betmode.setText("我的模式");
                                    iv_arrow.setImageResource(R.drawable.downarrow);
                                }else{
                                    Message msg1 = new Message();
                                    msg1.what =3;
                                    BetModeActivity.fragment28.handler.sendMessage(msg1);
                                    my_bet_mode_lv.setVisibility(View.GONE);
                                    add_my_betmode.setText("我的模式");
                                    iv_arrow.setImageResource(R.drawable.downarrow);
                                }
                                et_mode.setText(list.get(i).getModelName());
                                tv_tbid.setText(""+list.get(i).getID());
                                totalpoints= Integer.parseInt(list.get(i).getModelPoints());
                                total_points.setText(list.get(i).getModelPoints());
                                Message msg1 = new Message();
                                msg1.what =3;
                                handler.sendMessage(msg1);
                                my_bet_mode_lv.setVisibility(View.GONE);
                                add_my_betmode.setText("我的模式");
                                iv_arrow.setImageResource(R.drawable.downarrow);
                            }
                        });
                    } else {
                        Toast.makeText(BetModeActivity.this, "暂时没有数据，请添加模式！", Toast.LENGTH_SHORT).show();
                    }

                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(BetModeActivity.this);
                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(gaprarams,result,   new MymodeAsyncTask2());
                }
            } else {
                Toast.makeText(BetModeActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SaveModeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //   mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().SaveMode_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("保存模式", "消息返回结果result" + result);
            if (result != null) {
                int status = 0;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                  //  mLoadingAndRetryManager.showContent();
                    Intent intent = new Intent();
                    setResult(RESULT_CODE, intent);
                    Toast.makeText(BetModeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(BetModeActivity.this);
                } else if (status == -7) {
                    Toast.makeText(BetModeActivity.this, "您超出了单期投注限额", Toast.LENGTH_SHORT).show();
                  //  mLoadingAndRetryManager.showContent();

                } else if (status == -6) {
                    Toast.makeText(BetModeActivity.this, "前该游戏暂时不允许编辑或添加模式 ", Toast.LENGTH_SHORT).show();
                   // mLoadingAndRetryManager.showContent();

                } else if (status == -8) {
                    Toast.makeText(BetModeActivity.this, "该模式名称已存在,请修改模式名称", Toast.LENGTH_SHORT).show();
                   // mLoadingAndRetryManager.showContent();

                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(gaprarams,result,   new SaveModeAsyncTask());
                }
            } else {
                Toast.makeText(BetModeActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
//修改模式
    private class USaveModeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().USaveMode_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("修改模式", "消息返回结果result" + result);
            if (result != null) {
                int status = 0;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                  //  mLoadingAndRetryManager.showContent();
                    Intent intent = new Intent();
                    setResult(RESULT_CODE, intent);
                    Toast.makeText(BetModeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(BetModeActivity.this);
                } else if (status == -7) {
                    Toast.makeText(BetModeActivity.this, "您超出了单期投注限额", Toast.LENGTH_SHORT).show();
                  //  mLoadingAndRetryManager.showContent();

                } else if (status == -6) {
                    Toast.makeText(BetModeActivity.this, "前该游戏暂时不允许编辑或添加模式 ", Toast.LENGTH_SHORT).show();
                   // mLoadingAndRetryManager.showContent();

                } else if (status == -8) {
                    Toast.makeText(BetModeActivity.this, "该模式名称已存在,请修改模式名称", Toast.LENGTH_SHORT).show();
                 //   mLoadingAndRetryManager.showContent();

                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(gaprarams,result,  new SaveModeAsyncTask());
                }
            } else {
                Toast.makeText(BetModeActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class BetAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //   mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().GameBet_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("投注", "消息返回结果result" + result);
            if (result != null) {
                int status = 0;
                String msg = null;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                    msg = re.optString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                   // mLoadingAndRetryManager.showContent();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    //开始组装第一条数据
                    values.put("gamename", EgameName);
                    values.put("gamechname", gameName);
                    values.put("gametype", gameType);
                    //插入第一条数据
                    db.insert("Game", null, values);
                    Intent intent = new Intent();
                    setResult(RESULT_CODE, intent);
                    finish();
                    Toast.makeText(BetModeActivity.this, "投注成功", Toast.LENGTH_SHORT).show();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(BetModeActivity.this);
                } else if (status <= 0) {
                    Toast.makeText(BetModeActivity.this, msg, Toast.LENGTH_SHORT).show();
                  //  mLoadingAndRetryManager.showContent();

                } else if (status == -5) {
                    Toast.makeText(BetModeActivity.this, "您超出了单期投注限额", Toast.LENGTH_SHORT).show();
                  //  mLoadingAndRetryManager.showContent();

                } else if (status == -6) {
                    Toast.makeText(BetModeActivity.this, "账户余额不足", Toast.LENGTH_SHORT).show();
                //    mLoadingAndRetryManager.showContent();

                } else if (status == -4) {
                    Toast.makeText(BetModeActivity.this, "该期已截止投注,请投注下期", Toast.LENGTH_SHORT).show();
                  //  mLoadingAndRetryManager.showContent();

                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(prarams,result,  new BetAsyncTask());
                }
            } else {
                Toast.makeText(BetModeActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
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
            Log.e("获取是否开奖的结果", "BetModeActivity消息返回结果result" + result);
            if (result != null) {
                if (result.equals("1")) {
                    isopend = true;
                    mhandler.removeCallbacks(thread);
                    mhandler.removeMessages(5);
                    isopend = false;
                    new GameInfoAsyncTask().execute();

                }

            } else {
                Toast.makeText(BetModeActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
              //  mLoadingAndRetryManager.showContent();
            }
        }
    }

    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); //得到ListView 添加的适配器
        if (listAdapter == null) {
            return;
        }
        View itemView = listAdapter.getView(0, null, listView); //获取其中的一项 //进行这一项的测量，为什么加这一步，具体分析可以参考 https://www.jianshu.com/p/dbd6afb2c890这篇文章
        itemView.measure(0, 0);
        int itemHeight = itemView.getMeasuredHeight(); //一项的高度
        int itemCount = listAdapter.getCount();//得到总的项数
        LinearLayout.LayoutParams layoutParams = null; //进行布局参数的设置
        if (itemCount <= 6) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * itemCount);
        } else if (itemCount > 6) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * 6);
        }
        listView.setLayoutParams(layoutParams);

    }

    public static interface  SetList{
        void setParams( MybetModeDB.DataBean.ModeljsonBean list1);
    }
    private static SetList setParams2;
    public static void SetPL( SetList setParams){
      setParams2=setParams;
        if(setParams2!=null){
            setParams2.setParams(list.get(position2));
        }
    }

}
