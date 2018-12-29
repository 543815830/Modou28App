package com.ruixin.administrator.ruixinapplication.gamecenter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.AutoBetAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.AutoBetInfoDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.DatabaseHelper;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GaCBarDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MymodeDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ScameDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.popwindow.MymodePop;
import com.ruixin.administrator.ruixinapplication.popwindow.MytrusteePop;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.TrusteeActivity;
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
 * 自动投注界面
 */

public class AutoBetActivity extends Activity {
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
    TextView tvWarning;
    EditText etStartNo;
    EditText etEndNo;
    EditText etTrusteeName;
    EditText etPointsUp;
    EditText etPointsDown;
    TextView startMode;
    TextView tvStartMode;
    TextView tv_up;
    TextView tv_down;
    TextView tvStartModeid;
    RelativeLayout rl_mode;
    ImageView ivDownArrow;
    ListView autoBetLv;
    Button start;
    private TextView tv_title;
    LinearLayout back_arrow;
    String userId;
    String userToken;
    String EgameName;
    String gameName;
    String gameType;
    String result;
    String tbMaxG;
    String tbMinG;
    String tbStartNO;
    String tbEndNO;
    String trustee_name;
    String tbID;
    int gameSystem;
    LoadingAndRetryManager mLoadingAndRetryManager;
    LoadingAndRetryManager mLoadingAndRetryManager2;
    LinearLayout ll_choose_scheme;
    LinearLayout ll_auto_bet;
    LinearLayout ll_auto_info;
    RadioGroup rg_auto;
    RadioButton rb_add_scheme;
    RadioButton rb_update_scheme;
    private HashMap<String, String> gaprarams = new HashMap<String, String>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> prarams1 = new HashMap<String, String>();
    AutoBetAdapter adapter;
    AutoBetInfoDb autoBetInfoDb;
    List<MymodeDb.DataBean.ModellistBean> aulist = new ArrayList<>();//模式的列表
    List<AutoBetInfoDb.DataBean.CaselistBean> schmelist = new ArrayList<>();//托管方案的列表
    List<ScameDb> caselist=new ArrayList<>();
    List<AutoBetInfoDb.DataBean.CaselistBean.ModelBean> schmemodellist = new ArrayList<>();//托管方案模式的列表
    MymodePop mymodePop;
    String no = "";
    String Url;
    boolean first = true;
    boolean isopend = false;
    boolean isvisible = true;
    String type;
    MytrusteePop mytrusteePop;
    String url;
    String pid;
    String info;
    String fixted;
    private DatabaseHelper dbHelper;
    Runnable thread = new Runnable() {
        @Override
        public void run() {// TODO Auto-generated method stub
            prarams.clear();
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
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            first = false;
            if (isvisible) {
                if (msg.what == 5) {
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
                                    handler.post(thread);
                                }


                            }

                        }, 30000);

                    }

                } else if (msg.what == 4) {
                    jumpMode();
                }
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userToken = intent.getStringExtra("userToken");
        EgameName = intent.getStringExtra("EgameName");
        gameName = intent.getStringExtra("gameName");
        gameType = intent.getStringExtra("gameType");
        type = intent.getStringExtra("type");
        if (type.equals("update")) {
            pid = intent.getStringExtra("id");
        }
        gaprarams.put("usersid", userId);
        gaprarams.put("usertoken", userToken);
        gaprarams.put("gamename", EgameName);
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        prarams.put("gamename", EgameName);
        initStatus();
        initView();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    private void jumpMode() {
        Intent intent;
        intent = new Intent(AutoBetActivity.this, BetModeActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("where", "3");
        intent.putExtra("userToken", userToken);
        intent.putExtra("EgameName", EgameName);
        intent.putExtra("gameName", gameName);
        intent.putExtra("gameType", gameType);
        startActivityForResult(intent, 89);
    }

    private void initView() {
        setContentView(R.layout.activity_auto_bet);
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
        ll_head_result7 = findViewById(R.id.ll_head_result7);
        tv_result71 = findViewById(R.id.tv_result71);
        tv_result72 = findViewById(R.id.tv_result72);
        tv_result73 = findViewById(R.id.tv_result73);
        tv_result74 = findViewById(R.id.tv_result74);
        tv_result75 = findViewById(R.id.tv_result75);
        ll_center_bar = findViewById(R.id.ll_center_bar);
        rl_mode = findViewById(R.id.rl_mode);
        ll_center_bar.setFocusable(true);
        ll_center_bar.setFocusableInTouchMode(true);
        ll_center_bar.requestFocus();

        tv_chane_coins = findViewById(R.id.tv_chane_coins);
        tv_my_coins = findViewById(R.id.tv_my_coins);
        today_part = findViewById(R.id.today_part);
        today_win_loss = findViewById(R.id.today_win_loss);
        today_win_rate = findViewById(R.id.today_win_rate);
        ll_auto_bet = findViewById(R.id.ll_auto_bet);
        ll_auto_info = findViewById(R.id.ll_auto_info);
        ll_choose_scheme = findViewById(R.id.ll_choose_scheme);
        ll_choose_scheme.setOnClickListener(new MyOnclick());
        tvWarning = findViewById(R.id.tv_warning);
        rg_auto = findViewById(R.id.rg_auto);
        rb_add_scheme = findViewById(R.id.rb_add_scheme);
        rb_update_scheme = findViewById(R.id.rb_update_scheme);
        etStartNo = findViewById(R.id.et_start_no);
        etEndNo = findViewById(R.id.et_end_no);
        etTrusteeName = findViewById(R.id.et_trustee_name);
        tv_up= findViewById(R.id.tv_up);
        tv_down= findViewById(R.id.tv_down);
        if(gameType.equals("xn")){
            tv_up.setText("虚拟币上限");
            tv_down.setText("虚拟币下限");
        }
        etPointsUp = findViewById(R.id.et_points_up);
        etPointsDown = findViewById(R.id.et_points_down);
        tvStartMode = findViewById(R.id.tv_start_mode);
        tvStartModeid = findViewById(R.id.tv_start_mode_id);
        ivDownArrow = findViewById(R.id.iv_down_arrow);
        rl_mode.setOnClickListener(new MyOnclick());
        autoBetLv = findViewById(R.id.auto_bet_lv);
        start = findViewById(R.id.start);
        start.setOnClickListener(new MyOnclick());
        tvWarning.setSelected(true);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(ll_auto_bet, null);
        mLoadingAndRetryManager2 = LoadingAndRetryManager.generate(ll_auto_info, null);
        new GameInfoAsyncTask().execute();
        new AutoBetAsyncTask().execute();
        dbHelper = new DatabaseHelper(this, "FavorsGameStore.db", null, 1);
    }

    private void setListener() {
        rg_auto.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中
        if (type.equals("add")) {
            rg_auto.check(R.id.rb_add_scheme);
        } else {
            rg_auto.check(R.id.rb_update_scheme);
        }

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_add_scheme://添加新方案
                    ll_choose_scheme.setVisibility(View.GONE);
                    type = "add";
                    start.setText("开始自动投注");
                    initData(info);
                    break;
                case R.id.rb_update_scheme://修改现有方案
                    start.setText("确定修改方案");
                    type = "update";
                    initData(info);
                    break;
            }
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
            Log.e("获取游戏名称列表", "消息返回结果result" + result);
            mLoadingAndRetryManager.showContent();
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
                    GaCBarDb gaCBarDb = gson.fromJson(result, GaCBarDb.class);
                    gameSystem = gaCBarDb.getData().getGamesystem();

                    if (gaCBarDb.getData().getNearno() != null && gaCBarDb.getData().getNearno().length() > 0) {
                        no = gaCBarDb.getData().getNearno();
                        be_tv1.setText("距离" + GetGameResult.getGno(gaCBarDb.getData().getNearno()) + "期截止还有");
                        long startno = Long.parseLong(GetGameResult.getGno(gaCBarDb.getData().getNearno())) + 1;
                        long endno = Long.parseLong(GetGameResult.getGno(gaCBarDb.getData().getNearno())) + 3001;
                        if (type.equals("add")) {
                            tbStartNO = String.valueOf(startno);
                            etStartNo.setText(tbStartNO);
                            tbEndNO = String.valueOf(endno);
                            etEndNo.setText(tbEndNO);
                        }

                        timer = new CountDown(gaCBarDb.getData().getRestsecond() * 1000, 1000, count_down_time, handler);
                        // timer=new CountDown(5*60*1000,1000,count_down_time);
                        timer.start();

                    } else {
                        be_tv1.setText("距离本期截止还有");
                        timer = new CountDown(gaCBarDb.getData().getGametime() * 1000, 1000, count_down_time, handler);
                        timer.start();
                        etStartNo.setText("");
                        etEndNo.setText("");
                    }


                    if (gaCBarDb.getData().getLatestno() != null) {
                        be_tv2.setText(GetGameResult.getGno(gaCBarDb.getData().getLatestno()) + "期开奖结果");
                    } else {
                        be_tv2.setText("近期");
                    }
                    gameType = gaCBarDb.getData().getType();
                    if (gaCBarDb.getData().getLatestno() != null && gaCBarDb.getData().getLatestresult() != null) {
                        if (gameType.equals("28") || gameType.equals("16") || gameType.equals("22")) {
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.VISIBLE);
                            tv_result2.setText(GetGameResult.getlhrs(Integer.parseInt(gaCBarDb.getData().getLatestresult().get(0))));
                        } else if (gameType.equals("xync") || gameType.equals("pkww")) {
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            tv_result51.setText(gaCBarDb.getData().getLatestresult().get(gaCBarDb.getData().getLatestresult().size() - 1));
                        } else if (gameType.equals("36")) {
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            tv_result4.setText("" + num1 + "+" + num2 + "+" + num3 + "=");
                            tv_result41.setText("" + num4);
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
                            tv_result41.setText("=" + gaCBarDb.getData().getLatestresult().get(3));
                            tv_result42.setText(GetGameResult.getdwrs(num4));
                            tv_result4.setText(gaCBarDb.getData().getLatestresult().get(0) + "+" + gaCBarDb.getData().getLatestresult().get(1) + "+" + gaCBarDb.getData().getLatestresult().get(2));
                        }
                    } else {
                        ll_head_result6.setVisibility(View.GONE);
                        ll_head_result5.setVisibility(View.GONE);
                        tv_result3.setVisibility(View.GONE);
                        ll_head_result1.setVisibility(View.GONE);
                        ll_head_result7.setVisibility(View.GONE);
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
                } else if (entry.getStatus() == 99) {
/*抗攻击*/
                    Unlogin.doAtk(prarams,result,   new GameInfoAsyncTask());
                }else if(entry.getStatus() ==-99||entry.getStatus() ==-97){
                    Unlogin.doLogin(AutoBetActivity.this);
                } else {
                    Toast.makeText(AutoBetActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    mLoadingAndRetryManager.showContent();
                }
            } else {
                Toast.makeText(AutoBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AutoBetAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager2.showLoading();

        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().AutoBetInfo_URL, gaprarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取模式列表", "消息返回结果result" + result);
            mLoadingAndRetryManager2.showContent();
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
                    initData(result);
                    info = result;
                    setListener();
                }else if (status == -97 || status == -99) {
                    Unlogin.doLogin(AutoBetActivity.this);
                } else if (status == 99) {
                    Unlogin.doAtk(gaprarams,result,   new AutoBetAsyncTask());
                } else {
                    Toast.makeText(AutoBetActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AutoBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initData(String result) {
        if (result != null) {
            Gson gson = new Gson();
            autoBetInfoDb = gson.fromJson(result, AutoBetInfoDb.class);
            aulist = autoBetInfoDb.getData().getModellist();
            schmelist = autoBetInfoDb.getData().getCaselist();

            if (type.equals("add")) {
                etTrusteeName.setText("");
                if (autoBetInfoDb.getData().getCurno() != null) {
                    long curno = Long.parseLong(GetGameResult.getGno(autoBetInfoDb.getData().getCurno())) + 1;
                    long endno = Long.parseLong(GetGameResult.getGno(autoBetInfoDb.getData().getCurno())) + 3001;
                    etStartNo.setText("" + curno);
                    etEndNo.setText("" + endno);
                    if (autoBetInfoDb.getData().getCurno().length() > 7) {
                        fixted = autoBetInfoDb.getData().getCurno().substring(0, autoBetInfoDb.getData().getCurno().length() - 7);
                    }
                } else {
                    etStartNo.setText("");
                    etEndNo.setText("");
                }


                if (aulist != null && aulist.size() > 0) {
                    tvStartMode.setText(autoBetInfoDb.getData().getModellist().get(0).getModelname());
                    tvStartModeid.setText(autoBetInfoDb.getData().getModellist().get(0).getModelid());
                    adapter = new AutoBetAdapter(AutoBetActivity.this, aulist, schmemodellist, type, handler);
                    autoBetLv.setAdapter(adapter);
                    SetHight.setListViewHeightBasedOnChildren(autoBetLv);
                } else {
                    tvStartMode.setText("");
                    tvStartModeid.setText("");
                    //Toast.makeText(AutoBetActivity.this, "暂时没有数据，请添加模式！", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (schmelist != null && schmelist.size() > 0) {
                    ll_choose_scheme.setVisibility(View.VISIBLE);
                    if (pid == null) {
                        pid = schmelist.get(0).getId();
                        etTrusteeName.setText(schmelist.get(0).getName());
                        schmemodellist = schmelist.get(0).getModel();
                        etStartNo.setText(schmelist.get(0).getStartno());
                        etEndNo.setText(schmelist.get(0).getEndno());
                        etPointsUp.setText(schmelist.get(0).getMaxpoint());
                        etPointsDown.setText(schmelist.get(0).getMinpoint());
                        tvStartMode.setText(schmelist.get(0).getModelname());
                        tvStartModeid.setText(schmelist.get(0).getStartmodel());
                    } else {
                        for (int i = 0; i < schmelist.size(); i++) {
                            if (schmelist.get(i).getId().equals(pid)) {
                                etTrusteeName.setText(schmelist.get(i).getName());
                                schmemodellist = schmelist.get(i).getModel();
                                etStartNo.setText(schmelist.get(i).getStartno());
                                etEndNo.setText(schmelist.get(i).getEndno());
                                if (schmelist.get(i).getStartno().length() > 7) {
                                    fixted = schmelist.get(i).getStartno().substring(0, schmelist.get(i).getStartno().length() - 7);
                                }
                                etPointsUp.setText(schmelist.get(i).getMaxpoint());
                                etPointsDown.setText(schmelist.get(i).getMinpoint());
                                tvStartMode.setText(schmelist.get(i).getModelname());
                                tvStartModeid.setText(schmelist.get(i).getStartmodel());
                            }
                        }
                    }

                    adapter = new AutoBetAdapter(AutoBetActivity.this, aulist, schmemodellist, type, handler);
                    autoBetLv.setAdapter(adapter);
                    SetHight.setListViewHeightBasedOnChildren(autoBetLv);
                } else {
                    tvStartMode.setText("");
                    tvStartModeid.setText("");
                    ll_choose_scheme.setVisibility(View.GONE);
                    type = "add";
                    rg_auto.check(R.id.rb_add_scheme);
                    start.setText("开始自动投注");
                    Toast.makeText(AutoBetActivity.this, "您还没有方案可进行修改", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_mode:
                    if (aulist != null && aulist.size() > 0) {

                        mymodePop = new MymodePop(AutoBetActivity.this, aulist, null, tvStartMode, tvStartModeid, handler);
                        //监听窗口的焦点事件，点击窗口外面则取消显示
                        mymodePop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (!hasFocus) {
                                    mymodePop.dismiss();
                                }
                            }
                        });

                        //设置默认获取焦点
                        mymodePop.setFocusable(true);
                        //以某个控件的x和y的偏移量位置开始显示窗口
                        // mymodePop.showAsDropDown(tvStartMode, 0, 0);
                        mymodePop.showAtLocation(ll_auto_bet, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                        //如果窗口存在，则更新
                        mymodePop.update();
                    } else {
                        Toast.makeText(AutoBetActivity.this, "尚未添加模式,请先添加模式！", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(AutoBetActivity.this);
                        builder.setMessage("尚未添加模式,请先添加模式！？")//设置消息
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//确定按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent;
                                        intent = new Intent(AutoBetActivity.this, BetModeActivity.class);
                                        intent.putExtra("userId", userId);
                                        intent.putExtra("where", "3");
                                        intent.putExtra("userToken", userToken);
                                        intent.putExtra("EgameName", EgameName);
                                        intent.putExtra("gameName", gameName);
                                        intent.putExtra("gameType", gameType);
                                        startActivityForResult(intent, 89);
                                    }
                                })
                                .setNegativeButton("取消", null)//取消按钮：点击事件为null，点击取消时，对话框就自动关闭
                                .show();
                    }
                    break;
                case R.id.start:
                    tbMaxG = etPointsUp.getText().toString();
                    tbMinG = etPointsDown.getText().toString();
                    tbID = tvStartModeid.getText().toString();
                    trustee_name = etTrusteeName.getText().toString().trim();
                    tbStartNO = etStartNo.getText().toString();
                    tbEndNO = etEndNo.getText().toString();
                    if (fixted != null) {
                        tbStartNO = fixted + tbStartNO;
                        tbEndNO = fixted + tbEndNO;
                    }
                    if (isValid()) {


                        prarams.put("tbMaxG", tbMaxG);
                        prarams.put("tbMinG", tbMinG);

                        prarams.put("tbStartNO", tbStartNO);
                        prarams.put("tbEndNO", tbEndNO);
                        prarams.put("tbID", tbID);
                        prarams.put("name", trustee_name);
                        if (trustee_name.equals("") || trustee_name == null) {
                            Toast.makeText(AutoBetActivity.this, "请填写方案名称！", Toast.LENGTH_SHORT).show();
                        } else {

                            if (aulist != null && aulist.size() > 0) {
                                adapter.setOnnotifyClickListener(new AutoBetAdapter.OnnotifyClickListener() {
                                    @Override
                                    public void OnNotifyClick(String sID, String sWinModel, String sLossModel) {
                                        prarams.put("sID", sID);
                                        Log.e("tag", sID);
                                        prarams.put("sWinModel", sWinModel);
                                        prarams.put("sLossModel", sLossModel);
                                        if (type.equals("add")) {
                                            url = URL.getInstance().AutoBetStart_URL;
                                            new StartAndUpAutoBetAsyncTask().execute();
                                        } else {
                                            prarams.put("autoid", pid);
                                            url = URL.getInstance().AutoBetUpdate_URL;
                                            new StartAndUpAutoBetAsyncTask().execute();
                                        }

                                    }
                                });
                            } else {
                                Toast.makeText(AutoBetActivity.this, "尚未添加模式！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    break;

                case R.id.ll_choose_scheme:
                    caselist.clear();
                    for(int i=0;i<schmelist.size();i++){
                        caselist.add(i,new ScameDb(schmelist.get(i).getId(),schmelist.get(i).getName()));
                    }
                    if (caselist != null || caselist.size() > 0) {
                        showPop();
                        etTrusteeName.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                if (type.equals("update")) {
                                    mytrusteePop.SetPL(new MytrusteePop.SetPid() {
                                        @Override
                                        public void setParams(int i) {
                                            pid = schmelist.get(i).getId();
                                            schmemodellist = schmelist.get(i).getModel();
                                            etStartNo.setText(schmelist.get(i).getStartno());
                                            etEndNo.setText(schmelist.get(i).getEndno());
                                            etPointsUp.setText(schmelist.get(i).getMaxpoint());
                                            etPointsDown.setText(schmelist.get(i).getMinpoint());
                                            tvStartMode.setText(schmelist.get(i).getModelname());
                                            tvStartModeid.setText(schmelist.get(i).getStartmodel());
                                            adapter = new AutoBetAdapter(AutoBetActivity.this, aulist, schmemodellist, type, handler);
                                            autoBetLv.setAdapter(adapter);
                                            SetHight.setListViewHeightBasedOnChildren(autoBetLv);
                                        }
                                    });
                                }

                            }
                        });
                    } else {
                        Toast.makeText(AutoBetActivity.this, "暂时没有现有方案，请先添加方案！", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }


    public boolean isValid() {
        if (trustee_name.equals("")) {
            Toast.makeText(this, "请输入方案名称!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tbStartNO.equals("")) {
            Toast.makeText(this, "请输入开始期号!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tbEndNO.equals("")) {
            Toast.makeText(this, "请输入结束期数!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tbMaxG.equals("")) {
            Toast.makeText(this, "请输入金币上限!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tbMinG.equals("")) {
            Toast.makeText(this, "请输入金币下限!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tbID.equals("")) {
            Toast.makeText(this, "请选择开始模式!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showPop() {

        if (mytrusteePop == null) {
            mytrusteePop = new MytrusteePop(AutoBetActivity.this, caselist, etTrusteeName);
        }

        //监听窗口的焦点事件，点击窗口外面则取消显示
        mytrusteePop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mytrusteePop.dismiss();
                }
            }
        });
        //设置默认获取焦点
        mytrusteePop.setFocusable(true);
        mytrusteePop.setOutsideTouchable(true);
        // mytrusteePop.showAsDropDown(ll_choose_scheme,0,0);
        mytrusteePop.showAtLocation(ll_auto_bet, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //如果窗口存在，则更新
        mytrusteePop.update();
        //mytrusteePop.setOnDismissListener(new poponDismissListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 89 && resultCode == BetModeActivity.RESULT_CODE) {
            new GameInfoAsyncTask().execute();
            new AutoBetAsyncTask().execute();
        }
    }

    private class StartAndUpAutoBetAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(url, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("开始投注", "消息返回结果result" + result);
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
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    //开始组装第一条数据
                    values.put("gamename", EgameName);
                    values.put("gamechname", gameName);
                    values.put("gametype", gameType);
                    //插入第一条数据
                    db.insert("Game", null, values);
                    Intent intent = new Intent(AutoBetActivity.this, TrusteeActivity.class);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("userId", userId);
                    intent.putExtra("gameType", gameType);
                    startActivity(intent);
                    finish();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(AutoBetActivity.this);
                } else if (status == 99) {
                    Unlogin.doAtk(gaprarams,result,   new StartAndUpAutoBetAsyncTask());
                } else {
                    if (!(msg == null || msg.equals(""))) {
                        Toast.makeText(AutoBetActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                }
            } else {
                Toast.makeText(AutoBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
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
                    handler.removeCallbacks(thread);
                    handler.removeMessages(5);
                    new GameInfoAsyncTask().execute();
                    isopend = false;
                }

            } else {
                Toast.makeText(AutoBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
                mLoadingAndRetryManager.showContent();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isvisible = false;
        handler.removeCallbacks(thread);
        handler.removeMessages(5);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isvisible = true;
    }

}
