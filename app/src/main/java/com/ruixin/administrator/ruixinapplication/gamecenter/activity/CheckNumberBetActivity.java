package com.ruixin.administrator.ruixinapplication.gamecenter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
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
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.CheckNumberBetAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ChecknumberInfo;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.DatabaseHelper;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GaCBarDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MymodeDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ScameDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.domain.GetNumber;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
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
 * 对号投注界面
 */
public class CheckNumberBetActivity extends Activity {
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
    TextView tv_clear;
    TextView tv_bet;
    TextView today_win_rate;
    CountDown timer;
    TextView tv_up;
    TextView tv_down;
    EditText etStartNo;
    EditText etEndNo;
    EditText etTrusteeName;
    EditText etCoinsUp;
    EditText etCoinsDown;
    private TextView tv_title;
    private TextView tv_warning;
    LinearLayout back_arrow;
    String userId;
    String userToken;
    String EgameName;
    String gameName;
    String gameType;
    String startno;
    String endno;
    String trustee_name;
    String min;
    String max;
    String result;
    RelativeLayout rl_check_number;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private ListView check_number_lv;
    CheckNumberBetAdapter adapter;
    int gameSystem;
    String type;
    String no = "";
    String Url;
    String url;
    boolean first = true;
    boolean isopend = false;
    boolean isvisible = true;
    private DatabaseHelper dbHelper;
    List<MymodeDb.DataBean.ModellistBean> aulist = new ArrayList<>();//模式的列表
    List<ChecknumberInfo.DataBean.CaselistBean> schmelist = new ArrayList<>();//托管方案的列表
    List<ScameDb> caselist=new ArrayList<>();
    List<ChecknumberInfo.DataBean.CaselistBean.ModelBean> schmemodellist = new ArrayList<>();//托管方案模式的列表
    public int[] array;
    String fixted;
    String pid="0";
    String info;
    private HashMap<String, String> gaprarams = new HashMap<String, String>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> prarams1 = new HashMap<String, String>();
    private TabLayout tabLayout;
    private String[] tabs = new String[]{"添加新方案", "修改现有方案"};
    RadioGroup rg_check;
    RadioButton rb_add_scheme;
    RadioButton rb_update_scheme;
    LinearLayout ll_choose_scheme;
    ChecknumberInfo checknumberInfo;
    MytrusteePop mytrusteePop;
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
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            first = false;

                if (msg.what == 5) {
                    if (isvisible) {
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
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {// TODO Auto-generated method stub
                                          handler.post(thread);
                                        }
                                    });
                                }


                            }

                        }, 30000);

                    }
                }
                } else if (msg.what == 6) {
                    handler.removeMessages(5);
                } else if (msg.what == 4) {
                    jumpMode();
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
        Log.e("Tag", "" + gameType);
        gaprarams.put("usersid", userId);
        gaprarams.put("usertoken", userToken);
        gaprarams.put("gamename", EgameName);
        initStatus();
        intView();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    private void intView() {
        setContentView(R.layout.activity_check_number_bet);
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
        ll_center_bar.setFocusable(true);
        ll_center_bar.setFocusableInTouchMode(true);
        ll_center_bar.requestFocus();
        tv_chane_coins = findViewById(R.id.tv_chane_coins);
        tv_my_coins = findViewById(R.id.tv_my_coins);
        today_part = findViewById(R.id.today_part);
        today_win_loss = findViewById(R.id.today_win_loss);
        today_win_rate = findViewById(R.id.today_win_rate);
        rl_check_number = findViewById(R.id.rl_check_number);
        etStartNo = findViewById(R.id.et_start_no);
        etEndNo = findViewById(R.id.et_end_no);
        etTrusteeName = findViewById(R.id.et_trustee_name);
        etCoinsUp = findViewById(R.id.et_coins_up);
        tv_up = findViewById(R.id.tv_up);
        tv_down = findViewById(R.id.tv_down);
        if(gameType.equals("xn")){
            tv_up.setText("虚拟币上限");
            tv_down.setText("虚拟币下限");
        }
        etCoinsDown = findViewById(R.id.et_coins_down);
        tv_clear = findViewById(R.id.tv_clear);
        rg_check = findViewById(R.id.rg_check);
        rb_add_scheme = findViewById(R.id.rb_add_scheme);
        rb_update_scheme = findViewById(R.id.rb_update_scheme);
        ll_choose_scheme = findViewById(R.id.ll_choose_scheme);
        ll_choose_scheme.setOnClickListener(new MyOnclick());
        tv_bet = findViewById(R.id.tv_bet);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(rl_check_number, null);
        tv_warning = findViewById(R.id.tv_warning);
        tv_warning.setSelected(true);
        array = null;
        dbHelper = new DatabaseHelper(this, "FavorsGameStore.db", null, 1);
        array = GetNumber.getArray(gameType, array);
        check_number_lv = findViewById(R.id.check_number_lv);
        new GameInfoAsyncTask().execute();
        new MymodeAsyncTask().execute();
        tv_bet.setOnClickListener(new MyOnclick());
        tv_clear.setOnClickListener(new MyOnclick());
setListener();
    }
    private void setListener() {
        rg_check.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中
        if (type.equals("add")) {
            rg_check.check(R.id.rb_add_scheme);
        } else {
            rg_check.check(R.id.rb_update_scheme);
        }

    }
    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_add_scheme://添加新方案
                    ll_choose_scheme.setVisibility(View.GONE);
                    type = "add";
                    tv_bet.setText("开始托管");
                    initData(info);
                    break;
                case R.id.rb_update_scheme://修改现有方案
                    tv_bet.setText("确定修改方案");
                    type = "update";
                    initData(info);
                    break;
            }
        }
    }
    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_bet:
                    adapter.SetParamsS(new CheckNumberBetAdapter.SetParams() {
                        @Override
                        public void getParams(String tbChk, String tbNum) {
                            prarams.put("tbchk",tbChk);
                            prarams.put("tbnum",tbNum);
                        }
                    });
                    trustee_name = etTrusteeName.getText().toString().trim();
                    startno = etStartNo.getText().toString().trim();
                    endno = etEndNo.getText().toString().trim();
                    if (fixted != null) {
                        startno = fixted + startno;
                        endno = fixted + endno;
                    }
                    min = etCoinsDown.getText().toString().trim();
                    max = etCoinsUp.getText().toString().trim();
                    if (isValid()) {
                        prarams.put("usersid", userId);
                        prarams.put("usertoken", userToken);
                        prarams.put("gamename", EgameName);
                        prarams.put("startno", startno);
                        prarams.put("endno", endno);
                        prarams.put("name", trustee_name);
                        prarams.put("min", min);
                        prarams.put("max", max);
                        if(type.equals("add")){
                            url=URL.getInstance().CheckNumberMode_URL;
                            new CheckNumbermodeAsyncTask().execute();
                        }else{
                            prarams.put("autoid", pid);
                            url=URL.getInstance().UCheckNumberMode_URL;
                            new CheckNumbermodeAsyncTask().execute();
                        }

                    }


                    break;
                case R.id.tv_clear:
                    traversal2();
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
                                            etCoinsUp.setText(schmelist.get(i).getMaxpoint());
                                            etCoinsDown.setText(schmelist.get(i).getMinpoint());
                                            adapter = new CheckNumberBetAdapter(CheckNumberBetActivity.this, array, aulist, gameType, handler,schmelist.get(i).getModel());
                                            check_number_lv.setAdapter(adapter);
                                            SetHight.setListViewHeightBasedOnChildren(check_number_lv);
                                        }
                                    });
                                }

                            }
                        });
                    } else {
                        Toast.makeText(CheckNumberBetActivity.this, "暂时没有现有方案，请先添加方案！", Toast.LENGTH_SHORT).show();
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
        if (startno.equals("")) {
            Toast.makeText(this, "请输入开始期号!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (endno.equals("")) {
            Toast.makeText(this, "请输入结束期数!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (max.equals("")) {
            Toast.makeText(this, "请输入金币上限!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (min.equals("")) {
            Toast.makeText(this, "请输入金币下限!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (aulist.size() <= 0) {
            Toast.makeText(this, "您尚未添加模式!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void jumpMode() {
        Intent intent;
        intent = new Intent(CheckNumberBetActivity.this, BetModeActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("where", "3");
        intent.putExtra("userToken", userToken);
        intent.putExtra("EgameName", EgameName);
        intent.putExtra("gameName", gameName);
        intent.putExtra("gameType", gameType);
        startActivityForResult(intent, 89);
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
                        no = gaCBarDb.getData().getNearno();
                        be_tv1.setText("距离" + GetGameResult.getGno(gaCBarDb.getData().getNearno()) + "期截止还有");
                        timer = new CountDown(gaCBarDb.getData().getRestsecond() * 1000, 1000, count_down_time, handler);
                        timer.start();
                    } else {
                        be_tv1.setText("距离本期截止还有");
                        timer = new CountDown(gaCBarDb.getData().getGametime() * 1000, 1000, count_down_time, handler);
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
                            ll_head_result4.setVisibility(View.GONE);
                            ll_head_result6.setVisibility(View.GONE);
                            tv_result2.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result1.setVisibility(View.GONE);
                            ll_head_result.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            tv_result3.setVisibility(View.GONE);
                            ll_head_result7.setVisibility(View.GONE);
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
                            ll_head_result7.setVisibility(View.GONE);
                            ll_head_result5.setVisibility(View.GONE);
                            tv_result3.setVisibility(View.GONE);
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
                    today_win_loss.setText("" + gaCBarDb.getData().getMytoday().getProfit());
                    today_win_rate.setText(new StringBuilder().append(gaCBarDb.getData().getMytoday().getWinpercent()).append("%").toString());
                    if (gaCBarDb.getData().getNearno() != null) {
                        long no = Long.parseLong(GetGameResult.getGno(gaCBarDb.getData().getNearno()));
                        long stat_no = no + 1;
                        long end_no = no + 3001;
                        if(type.equals("add")){
                            etStartNo.setText(new StringBuilder().append(stat_no).toString());
                            etEndNo.setText(new StringBuilder().append(end_no).toString());
                        }


                        if (gaCBarDb.getData().getNearno().length() > 7) {
                            fixted = gaCBarDb.getData().getNearno().substring(0, gaCBarDb.getData().getNearno().length() - 7);
                        }
                    }

                    mLoadingAndRetryManager.showContent();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(CheckNumberBetActivity.this);
                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(gaprarams,result,  new GameInfoAsyncTask());
                }
            } else {
                Toast.makeText(CheckNumberBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
//获取对号投注的数据信息
    private class MymodeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().MyMode_URL, gaprarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取对号投注信息数据", "消息返回结果result" + result);
            if (result != null) {
                mLoadingAndRetryManager.showContent();
                int status = 0;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                    if (array.length > 1) {
                        initData(result);
                        info = result;
                        setListener();
                    } else {
                        Toast.makeText(CheckNumberBetActivity.this, "该游戏暂时不支持对号投注", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                  /*  Gson gson = new Gson();
                    MymodeDb mymodeDb = gson.fromJson(result, MymodeDb.class);
                    list = mymodeDb.getData().getModellist();
                    if (array.length > 1) {
                        adapter = new CheckNumberBetAdapter(CheckNumberBetActivity.this, array, list, prarams, gameType, handler);
                    } else {
                        Toast.makeText(CheckNumberBetActivity.this, "该游戏暂时不支持对号投注", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    check_number_lv.setAdapter(adapter);
                    SetHight.setListViewHeightBasedOnChildren(check_number_lv);*/

                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(CheckNumberBetActivity.this);
                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(gaprarams,result,  new MymodeAsyncTask());
                }
            } else {
                Toast.makeText(CheckNumberBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void initData(String result) {
        if (result != null) {
            Gson gson = new Gson();
            checknumberInfo = gson.fromJson(result, ChecknumberInfo.class);
            aulist = checknumberInfo.getData().getModellist();
            schmelist = checknumberInfo.getData().getCaselist();
            if (type.equals("add")) {
                    etTrusteeName.setText("");
                    adapter = new CheckNumberBetAdapter(CheckNumberBetActivity.this, array, aulist, gameType, handler,null);
                    check_number_lv.setAdapter(adapter);
                    SetHight.setListViewHeightBasedOnChildren(check_number_lv);
            } else {
                if (schmelist != null && schmelist.size() > 0) {
                    ll_choose_scheme.setVisibility(View.VISIBLE);
                        for (int i = 0; i < schmelist.size(); i++) {
                            if (schmelist.get(i).getId().equals(pid)) {
                                etTrusteeName.setText(schmelist.get(i).getName());
                                schmemodellist = schmelist.get(i).getModel();
                                etStartNo.setText(schmelist.get(i).getStartno());
                                etEndNo.setText(schmelist.get(i).getEndno());
                                if (schmelist.get(i).getStartno().length() > 7) {
                                    fixted = schmelist.get(i).getStartno().substring(0, schmelist.get(i).getStartno().length() - 7);
                                }
                                etCoinsUp.setText(schmelist.get(i).getMaxpoint());
                                etCoinsDown.setText(schmelist.get(i).getMinpoint());
                                adapter = new CheckNumberBetAdapter(CheckNumberBetActivity.this, array, aulist, gameType, handler,schmelist.get(i).getModel());
                                check_number_lv.setAdapter(adapter);
                                SetHight.setListViewHeightBasedOnChildren(check_number_lv);
                            }else{
                                etTrusteeName.setText(schmelist.get(0).getName());
                                schmemodellist = schmelist.get(0).getModel();
                                etStartNo.setText(schmelist.get(0).getStartno());
                                etEndNo.setText(schmelist.get(0).getEndno());
                                if (schmelist.get(0).getStartno().length() > 7) {
                                    fixted = schmelist.get(0).getStartno().substring(0, schmelist.get(0).getStartno().length() - 7);
                                }
                                pid=schmelist.get(0).getId();
                                etCoinsUp.setText(schmelist.get(0).getMaxpoint());
                                etCoinsDown.setText(schmelist.get(0).getMinpoint());
                                adapter = new CheckNumberBetAdapter(CheckNumberBetActivity.this, array, aulist, gameType, handler,schmelist.get(0).getModel());
                                check_number_lv.setAdapter(adapter);
                                SetHight.setListViewHeightBasedOnChildren(check_number_lv);
                            }
                        }
                } else {
                    ll_choose_scheme.setVisibility(View.GONE);
                    type = "add";
                    rg_check.check(R.id.rb_add_scheme);
                    tv_bet.setText("开始托管");
                    Toast.makeText(CheckNumberBetActivity.this, "您还没有方案可进行修改", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private class CheckNumbermodeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(url, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mLoadingAndRetryManager.showContent();
            Log.e("确认添加", "消息返回结果result" + result);
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
                    Intent intent = new Intent(CheckNumberBetActivity.this, TrusteeActivity.class);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("userId", userId);
                    intent.putExtra("gameType", gameType);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(CheckNumberBetActivity.this, "添加成功", Toast.LENGTH_SHORT).show();

                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(CheckNumberBetActivity.this);
                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(prarams,result,   new  CheckNumbermodeAsyncTask());
                } else if (status == -2) {
                    Toast.makeText(CheckNumberBetActivity.this, "当前游戏尚不能添加对号投注", Toast.LENGTH_SHORT).show();
                    mLoadingAndRetryManager.showContent();
                } else if (status <= 0) {
                    Toast.makeText(CheckNumberBetActivity.this, msg, Toast.LENGTH_SHORT).show();
                    mLoadingAndRetryManager.showContent();
                }
            } else {
                Toast.makeText(CheckNumberBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void traversal2() {
        for (int i = 0; i < check_number_lv.getCount(); i++) {
            View view = check_number_lv.getChildAt(i);
            CheckBox cb_mode = view.findViewById(R.id.cb_mode);
            cb_mode.setChecked(false);
            prarams.clear();
        }
    }
    private void showPop() {

        if (mytrusteePop == null) {
            mytrusteePop = new MytrusteePop(CheckNumberBetActivity.this, caselist, etTrusteeName);
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
        mytrusteePop.showAtLocation(ll_choose_scheme, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //如果窗口存在，则更新
        mytrusteePop.update();
        //mytrusteePop.setOnDismissListener(new poponDismissListener());
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
                Toast.makeText(CheckNumberBetActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 89 && resultCode == BetModeActivity.RESULT_CODE) {
            new MymodeAsyncTask().execute();
        }
    }
}
