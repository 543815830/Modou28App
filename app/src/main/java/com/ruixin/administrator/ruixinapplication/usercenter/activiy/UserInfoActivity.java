package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.ListDataSave;
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
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 个人信息的界面
 */
public class UserInfoActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.update_user_info)
    RelativeLayout updateUserInfo;

    @BindView(R.id.mail_version)
    RelativeLayout mailVersion;
    @BindView(R.id.bind_qq)
    RelativeLayout bindQq;
    Intent intent;
    String userId;
    String userToken;
    String result;
    User user;
    @BindView(R.id.gold_deposit)
    RelativeLayout goldDeposit;
    private HashMap<String, String> call_back_prarams = new HashMap<>();
List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        tvTitle.setText("个人信息");
        list=  ListDataSave.getDataList(UserInfoActivity.this,"menu").get(0);
       if(list.contains("资料修改")){
            updateUserInfo.setVisibility(View.GONE);
        } if(list.contains("邮箱验证")){
            mailVersion.setVisibility(View.GONE);
        } if(list.contains("QQ绑定")){
            bindQq.setVisibility(View.GONE);
        } if(list.contains("金币提现")){
           goldDeposit.setVisibility(View.GONE);
        }
        initStatus();

    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick({R.id.back_arrow,R.id.gold_deposit, R.id.update_user_info, R.id.mail_version, R.id.bind_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.gold_deposit://金币提现
                intent = new Intent(UserInfoActivity.this, GoldDepositActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
            case R.id.update_user_info://修改信息
                intent = new Intent(UserInfoActivity.this, UpdateInfoActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;

            case R.id.mail_version:
                intent = new Intent(UserInfoActivity.this, MailVersionActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
            case R.id.bind_qq:
                mobQlogin();
                break;
        }
    }

    private void mobQlogin() {
        Platform plat = ShareSDK.getPlatform(QQ.NAME);
//        plat.i
        if (plat == null) {
            return;
        }

//判断指定平台是否已经完成授权
        if (plat.isAuthValid()) {
            Log.i("sss", "已完成授权");
            plat.removeAccount(true);
//            plat.isClientValid()
        }
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.i("sss", platform.getDb().exportData());
                PlatformDb platformDb = platform.getDb();
                String openID = platform.getDb().getUserId();
                String accessToken = platform.getDb().getToken();
                Log.e("sss", platform.getDb().getUserId());
                Log.e("sss", platform.getDb().getToken());
                call_back_prarams.put("usersid", userId);
                call_back_prarams.put("openid", openID);
                call_back_prarams.put("token", accessToken);
                //遍历Map
               /* Iterator ite =hashMap.entrySet().iterator();
                while (ite.hasNext()) {
                    Map.Entry entry = (Map.Entry)ite.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                }*/
                String qchead = (String) hashMap.get("figureurl_qq_1");
                String qchead2 = (String) hashMap.get("figureurl_qq_2");
                call_back_prarams.put("qchead1", qchead);
                call_back_prarams.put("qchead2", qchead2);
                // startProgressDialog_Result("正在登录");
                new CallBackAsyncTask().execute();

                //Toast.makeText(LoginActivity.this,"登录成功了",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.i("sss", throwable.toString());
                Toast.makeText(UserInfoActivity.this, "登录失败了", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.i("sss", "onCancel");
                Toast.makeText(UserInfoActivity.this, "登录取消了", Toast.LENGTH_SHORT).show();

            }
        });
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(false);
//        plat.isClientValid()
        //获取用户资料
        plat.showUser(null);
    }

    String url = URL.getInstance().CallBack_URL + "?act=bind";

    @SuppressLint("StaticFieldLeak")
    private class CallBackAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(url, call_back_prarams);
            Log.e("回调参数传递", "call_back_prarams=" + call_back_prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("回调消息返回", "消息返回结果result" + result);
            if (result != null) {
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    try {
                        JSONObject jsb = new JSONObject(result);
                        int status = jsb.optInt("status");
                       String msg = jsb.optString("msg");
                        if (status == 1) {
                            new AlertDialog.Builder(UserInfoActivity.this)
                                    .setMessage("绑定成功！")
                                    .setPositiveButton("确定", null)
                                    .show();
                            Gson gson = new Gson();
                            user = gson.fromJson(result, User.class);
                            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                                    Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_head", user.getData().getHead()); //用户头像地址
                            editor.putString("user_balance", user.getData().getPoints()); //账户余额
                            editor.apply();
                        } else if (status == 0) {
                            Log.e("getStatus", "getStatus2");
                            new AlertDialog.Builder(UserInfoActivity.this)
                                    .setMessage("您尚未登录，请先登录账号再绑定QQ！")
                                    .setPositiveButton("确定", null)
                                    .show();
                        } else if (status == -2) {
                            Log.e("getStatus", "getStatus2");
                            new AlertDialog.Builder(UserInfoActivity.this)
                                    .setMessage("您的账号已经绑定了qq,请不要重复绑定")
                                    .setPositiveButton("确定", null)
                                    .show();
                        } else if (status == -97 || status == -99) {
                            Unlogin.doLogin(UserInfoActivity.this);
                        } else if (status == 99) {
                            /*抗攻击*/
                            Unlogin.doAtk(call_back_prarams, result, new CallBackAsyncTask());
                            // new CallBackAsyncTask().execute();
                        }else{
                            Toast.makeText(UserInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (index != -1) {
                    Toast.makeText(UserInfoActivity.this, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(UserInfoActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
