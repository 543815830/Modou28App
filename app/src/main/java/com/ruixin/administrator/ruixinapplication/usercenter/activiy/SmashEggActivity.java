package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.EggAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.SignRecordFragment;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.SharedPrefUtility;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 砸金蛋界面
 */

public class SmashEggActivity extends Activity implements View.OnClickListener {
    LinearLayout back_arrow;//返回
    @BindView(R.id.egg_ry)
    RecyclerView eggRy;
    EggAdapter adapetr;
    List list = new ArrayList();
    String userId = "",userToken="";
    public static int RESULT_CODE = 50;
    @BindView(R.id.eggjf)
    TextView eggjf;
    private HashMap<String, String> prarams = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smash_egg);
        ButterKnife.bind(this);
        initStatus();
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");

        if (userId.equals("")) {
           Toast.makeText(SmashEggActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SmashEggActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        } else {
            initView();
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

    private void initView() {
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(this);
        String jf = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_eggjf", "");
      eggjf.setText(new StringBuilder().append("温馨提示：砸金蛋每次消耗").append(jf).append("积分").toString());
        for (int i = 0; i < 6; i++) {
            list.add(i);
        }
        adapetr = new EggAdapter(this, list);
        eggRy.setAdapter(adapetr);
        eggRy.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        adapetr.setOnItemClickListener(new EggAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, String data) {
                prarams.put("usersid", userId);
                prarams.put("usertoken", userToken);
                String position = data;
                Log.e("position", "position" + position);
                new SmashEggAsyncTask().execute();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class SmashEggAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost3(URL.getInstance().SmshEgg_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("砸金蛋消息返回", "消息返回结果result" + result);
            if (result != null) {
                try {
                    JSONObject jsb = new JSONObject(result);
                    int status=jsb.optInt("status");

                  if(status==-97||status==-99){
                      Unlogin.doLogin(SmashEggActivity.this);
                    }else if (status==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,result,new SmashEggAsyncTask());
                    }else if(status==1) {
                      String prize = jsb.optString("prize");
                      String points = jsb.optString("points");
                      String experience = jsb.optString("experience");
                     Toast.makeText(SmashEggActivity.this, prize, Toast.LENGTH_SHORT).show();
                      SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                              Activity.MODE_PRIVATE);
                      SharedPreferences.Editor editor = sharedPreferences.edit();
                      editor.putString("user_balance", points); //账户余额
                      editor.putString("user_total", experience);  //用户积分
                      editor.apply();
                      EventBus.getDefault().post(new MessageEvent("3"));
                  }else{
                      String prize = jsb.optString("prize");
                      Toast.makeText(SmashEggActivity.this, prize, Toast.LENGTH_SHORT).show();
                  }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
