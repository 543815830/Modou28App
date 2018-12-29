package com.ruixin.administrator.ruixinapplication.exchangemall.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.exchangemall.domain.LuckyDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LuckyWheelActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cjcs)
    TextView tvCjcs;
    String userId, userToken;
    LuckyDb luckyDb;
    @BindView(R.id.ll_lucky)
    LinearLayout llLucky;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel);
        ButterKnife.bind(this);
        initStatus();
        initView();
    }

    private void initView() {
        tvTitle.setText("幸运大转盘");
        userId = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
        userToken = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");;
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(llLucky, null);
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new getResourcesAsyncTask().execute();
    }

    @OnClick(R.id.back_arrow)
    public void onViewClicked() {
        finish();
    }

    @SuppressLint("StaticFieldLeak")
    private class getResourcesAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().luckyRound_URL, prarams);
            Log.e("result", "getResourcesAsyncTaskresult=" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() > 0) {
                int index = s.indexOf("{");
                if (index == 0) {
                    mLoadingAndRetryManager.showContent();
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        luckyDb = gson.fromJson(s, LuckyDb.class);
                        tvCjcs.setText("您剩余的抽奖次数：" + luckyDb.getResult().getCjcs() + "次");
                    } else if (entry.getStatus() == 99) {
/*抗攻击*/
                        Unlogin.doAtk(prarams,s,   new  getResourcesAsyncTask());
                    }else if(entry.getStatus() ==-99||entry.getStatus() ==-97){
                        Unlogin.doLogin(LuckyWheelActivity.this);
                    } else {
                        Toast.makeText(LuckyWheelActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(LuckyWheelActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                // mLoadingAndRetryManager.showContent();
               Toast.makeText(LuckyWheelActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
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
}
