package com.ruixin.administrator.ruixinapplication.gamecenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.BetContenAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetContentDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BetContentActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_bet_no)
    TextView tvBetNo;
    @BindView(R.id.bet_content_lv)
    ListView betContentLv;
    BetContenAdapter adapter;
    String userId;
    String userToken;
    String gameId;
    String EgameName;
    String gameType;
    @BindView(R.id.ll_bet_content)
    LinearLayout llBetContent;
    private HashMap<String, String> dprarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    String result;
    List<BetContentDb.DataBean> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_content);
        ButterKnife.bind(this);
        initStatus();
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        Log.e("userId", "userId" + userId);
        userToken = intent.getStringExtra("userToken");
        gameType = intent.getStringExtra("gameType");
        EgameName = intent.getStringExtra("gamename");
        gameId = intent.getStringExtra("no");
        dprarams.put("usersid", userId);
        dprarams.put("usertoken", userToken);
        dprarams.put("gamename", EgameName);
        dprarams.put("no", gameId);
        initView();

    }

    private void initView() {
        tvTitle.setText("投注详情");
        tvBetNo.setText(gameId);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(llBetContent, null);
       new BetContentAsyncTask().execute();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick(R.id.back_arrow)
    public void onViewClicked() {
        finish();
    }

    private class BetContentAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().MyBetContentDetail_URL, dprarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mLoadingAndRetryManager.showContent();
            Log.e("投注内容详情返回", "消息返回结果result" + result);
            if (result != null) {
                int status = -22;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                    Gson gson=new Gson();
                    BetContentDb betContentDb=gson.fromJson(result,BetContentDb.class);
                    list=betContentDb.getData();
                    adapter = new BetContenAdapter(BetContentActivity.this,list,gameType);
                    betContentLv.setAdapter(adapter);
                    mLoadingAndRetryManager.showContent();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(BetContentActivity.this);
                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(dprarams,result,   new BetContentAsyncTask());
                } else if (status == 0) {
                    Toast.makeText(BetContentActivity.this, "该期不存在或已被删除！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(BetContentActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
