package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.TrusteeDetAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.TrusteeDetailDb;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.SetHight;
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
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 自动托管详情界面
 */
public class TrusteeDetActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_start_no)
    TextView etStartNo;
    @BindView(R.id.et_end_no)
    TextView etEndNo;
    @BindView(R.id.et_points_up)
    TextView etPointsUp;
    @BindView(R.id.et_points_down)
    TextView etPointsDown;
    @BindView(R.id.start_mode)
    TextView startMode;
    @BindView(R.id.tv_start_mode)
    TextView tvStartMode;
    @BindView(R.id.trustee_bet_lv)
    ListView trusteeBetLv;
    String userId;
    String userToken;
    String gameType;

    String id;
    @BindView(R.id.et_trustee_name)
    TextView etTrusteeName;
    @BindView(R.id.tv_up)
    TextView tvUp;
    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.fl_game_content)
    FrameLayout flGameContent;
    @BindView(R.id.ll_auto_bet)
    LinearLayout llAutoBet;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> sprarams = new HashMap<String, String>();
    List<TrusteeDetailDb.DataBean.ModellistBean> list = new ArrayList<>();
    TrusteeDetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trustee_det);
        ButterKnife.bind(this);
        initStatus();
        tvTitle.setText("托管详情");
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userToken = intent.getStringExtra("userToken");
        gameType = intent.getStringExtra("gameType");
        if(gameType.equals("xn")){
            tvUp.setText("虚拟币上限");
            tvDown.setText("虚拟币下限");
        }
        id = intent.getStringExtra("id");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        prarams.put("id", id);
        sprarams.put("usersid", userId);
        sprarams.put("usertoken", userToken);
        sprarams.put("id", id);
        new TrusteeDetAsyncTask().execute();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick({R.id.back_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class TrusteeDetAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().TrusteeDetail_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                String re = null;
                String msg = null;
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //第一层解析
                    if (jsonObject != null) {
                        re = jsonObject.optString("status");
                        msg = jsonObject.optString("msg");
                    }

                    if (re.equals("1")) {
                        Gson gson = new Gson();
                        TrusteeDetailDb detailDb = gson.fromJson(s, TrusteeDetailDb.class);
                        etStartNo.setText(detailDb.getData().getStartno());
                        etEndNo.setText(detailDb.getData().getEndno());
                        etTrusteeName.setText(detailDb.getData().getName());
                        etPointsUp.setText(detailDb.getData().getMaxpoint());
                        etPointsDown.setText(detailDb.getData().getMinpoint());
                        tvStartMode.setText(detailDb.getData().getStartmodel());
                        list = detailDb.getData().getModellist();
                        adapter = new TrusteeDetAdapter(TrusteeDetActivity.this, list);
                        trusteeBetLv.setAdapter(adapter);
                        SetHight.setListViewHeightBasedOnChildren(trusteeBetLv);
                    } else if (re.equals("99")) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams, s, new TrusteeDetAsyncTask());
                        // new TrusteeDetAsyncTask().execute();
                    } else if (re.equals("-99") || re.equals("-97")) {
                        Unlogin.doLogin(TrusteeDetActivity.this);
                    }else{
                        finish();
                        Toast.makeText(TrusteeDetActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(TrusteeDetActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TrusteeDetActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
