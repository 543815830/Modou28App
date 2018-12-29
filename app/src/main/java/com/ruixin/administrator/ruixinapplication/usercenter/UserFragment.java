package com.ruixin.administrator.ruixinapplication.usercenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.activity.CaptureActivity;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.AccountSafetyActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.CommitScanLoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.InsideBankActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.MoreFunctionActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.OnlinePayActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.PromoteActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.SetActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.SetPwdActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.SmashEggActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.UpdateInfoActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.UserInfoActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.UserStateActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.WelfareCenterActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CommonUtil;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.ListDataSave;
import com.ruixin.administrator.ruixinapplication.utils.SharedPrefUtility;
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

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 个人中心界面
 */

public class UserFragment extends BaseFragment implements View.OnClickListener {
    private TextView user_name;//用户名
    private TextView user_tv_id;//id号
    private ImageView user_head;//用户头像
    String imagepath;//头像地址
    private TextView user_balance;//账户余额
    private TextView user_coin;//银行金币
    private TextView user_total;//我的积分
    private TextView user_suffer;//我的经验
    private TextView user_tv_state;//账号动态

    RadioButton rb_scan_code;//扫一扫
    RadioButton rb_smash_eggs;//积分砸蛋
    RadioButton rb_inside_bank;//站内银行
    RadioButton rb_online_pay;//在线支付

    RelativeLayout user_info;//个人信息
    RelativeLayout rl_account_safety;//账户安全
    RelativeLayout rl_promote;//推广下线
    RelativeLayout rl_welfare_center;//福利中心
    RelativeLayout rl_more_function;//更多功能
    RelativeLayout rl_set;//设置
    RelativeLayout rl_user_fm;//头部


    private String userToken;
    User user;//用户属性类
    private View view;
    public static int REQUEST_CODE = 1;
    //打开扫描界面请求码
    private static final int Qr_REQUEST_CODE = 0x01;
    //扫描成功返回码
    private static final int RESULT_OK = 0xA1;
    String result;
    int To_UP_REQUEST_CODE = 21;//跳转请求码
    int To_UP_REQUEST_CODE2 = 22;//跳转请求码
    Intent intent;
    String userId = "";
    String userBalance = "";
    String userCoin = "";
    String userSecques = "";
    String unreadMsgnum;
    private HashMap<String, String> prarams = new HashMap<>();
    private static final String APP_ID = "101502050";//官方获取的APPID
    private HashMap<String, String> qr_code_prarams = new HashMap<>();
    int height = 0;
    SharedPreferences sp;
    String scanResult;
    String randnumber;
    long timeStamp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册
        EventBus.getDefault().register(this);
    }

    @Override
    protected View initView() {
        if (view == null) {
            view = View.inflate(mContext, R.layout.fm_user, null);
            initStatus();
            rl_user_fm = view.findViewById(R.id.rl_user_fm);
            getStatusBarHeight();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)rl_user_fm.getLayoutParams();
            layoutParams.setMargins(0,height,0,0);
            rl_user_fm.setLayoutParams(layoutParams);
            user_tv_id = view.findViewById(R.id.user_tv_id);//id
            user_tv_state = view.findViewById(R.id.user_tv_state);//账号动态
            user_head = view.findViewById(R.id.user_head);//用户头像
            user_name = view.findViewById(R.id.user_name);//用户名
            user_balance = view.findViewById(R.id.user_balance);//余额
            user_coin = view.findViewById(R.id.user_coin);//银行金币
            user_total = view.findViewById(R.id.user_total);//用户积分
            user_suffer = view.findViewById(R.id.user_suffer);//用户经验

            rb_scan_code = view.findViewById(R.id.rb_scan_code);//手机绑定
            rb_smash_eggs = view.findViewById(R.id.rb_smash_eggs);//积分砸蛋
            rb_inside_bank = view.findViewById(R.id.rb_inside_bank);//站内银行
            rb_online_pay = view.findViewById(R.id.rb_online_pay);//在线支付

            user_info = view.findViewById(R.id.user_info);//个人信息
            rl_account_safety = view.findViewById(R.id.rl_account_safety);//账户安全
            rl_promote = view.findViewById(R.id.rl_promote);//推广下线
            rl_welfare_center = view.findViewById(R.id.rl_welfare_center);//福利中心
            rl_more_function = view.findViewById(R.id.rl_more_function);//更多功能
            rl_set = view.findViewById(R.id.rl_set);//设置

            user_info.setOnClickListener(this);
            rl_account_safety.setOnClickListener(this);
            rl_promote.setOnClickListener(this);
            rl_welfare_center.setOnClickListener(this);
            rl_more_function.setOnClickListener(this);
            rl_set.setOnClickListener(this);


            user_tv_state.setOnClickListener(this);
            user_name.setOnClickListener(this);
            user_head.setOnClickListener(this);
            rb_scan_code.setOnClickListener(this);
            rb_smash_eggs.setOnClickListener(this);
            rb_inside_bank.setOnClickListener(this);
            rb_online_pay.setOnClickListener(this);
            sp=  getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
            userToken =sp.getString("user_token","");
        }
        if (!userToken.equals("")) {
            new GetmenuInfoAsyncTask().execute();
            readParameter();
        }
        return view;
    }
    private void getStatusBarHeight(){

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("tag",height+"height---------");

    }
   /* *//* 标题栏与状态栏颜色一致用这种*//*
   *//*设置状态栏*/
    @SuppressLint("ResourceAsColor")
    protected void initStatus() {
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                View decorView =window.getDecorView();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION//隐藏虚拟按键(导航栏)
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(Color.TRANSPARENT);// SDK21
                }
            }


    }

    /*读取账号密码*/
    private void readParameter() {
        Log.e("tag", "readParameter");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        if (sharedPreferences.getString("is_login", "").equals("true")) {
            user_tv_id.setText(sharedPreferences.getString("user_tv_id", ""));
            user_name.setText(sharedPreferences.getString("user_name", ""));
            boolean contains = sharedPreferences.getString("user_head", "").contains("http");
            Log.e("tag", "contains-----" + contains);
            if (contains) {
                imagepath = sharedPreferences.getString("user_head", "");
            } else {
                imagepath = RuiXinApplication.getInstance().getUrl() + sharedPreferences.getString("user_head", "");
            }
            Log.e("tag", "imagepath-----" + imagepath);
            Glide.with(mContext)
                    .load(imagepath)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                    .skipMemoryCache(true)//跳过内存缓存
                    .placeholder(R.drawable.iv_user) //占位图
                    .error(R.drawable.iv_user)  //出错的占位图
                    .into(user_head);
            user_balance.setText(FormatUtils.formatString(sharedPreferences.getString("user_balance", "")));
            user_coin.setText(FormatUtils.formatString(sharedPreferences.getString("user_coin", "")));
            Log.e("tag", "user_total-----" + sharedPreferences.getString("user_total", ""));
            user_total.setText(FormatUtils.formatString(sharedPreferences.getString("user_total", "")));
            user_suffer.setText(FormatUtils.formatString(sharedPreferences.getString("user_suffer", "")));
        } else {
            user_tv_id.setText("");
            Glide.with(mContext)
                    .load(R.drawable.iv_user)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .placeholder(R.drawable.iv_user) //占位图
                    .error(R.drawable.iv_user)  //出错的占位图
                    .into(user_head);
            user_name.setText("登录");
            user_balance.setText("￥");
            user_coin.setText("/金币");
            user_total.setText("/积分");
            user_suffer.setText("/经验");
        }

    }

    @Override
    public void onClick(View view) {
        userId = user_tv_id.getText().toString();
        userBalance = user_balance.getText().toString();
        userCoin = user_coin.getText().toString();
        userToken = sp.getString("user_token","");
        if (userId.equals("")) {
           Toast.makeText(mContext, "您尚未登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.putExtra("where", "3");
            startActivity(intent);
        } else {
            switch (view.getId()) {
                case R.id.user_tv_state://账号动态
                    intent = new Intent(mContext, UserStateActivity.class);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    break;
                case R.id.user_head:
                    //登录状态下
                    To_UP_REQUEST_CODE = 8;
                    intent = new Intent(mContext, UpdateInfoActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    startActivityForResult(intent, To_UP_REQUEST_CODE);
                    break;
                case R.id.user_name:
                    if (user_name.getText().toString().equals("登录")) {
                        intent = new Intent(mContext, LoginActivity.class);
                        intent.putExtra("where", "1");
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                    break;
                case R.id.rb_scan_code://扫一扫
                    if (userId.equals("")) {
                       Toast.makeText(mContext, "您的手机尚未登录，请先登录！", Toast.LENGTH_SHORT).show();
                    } else {
                        //打开二维码扫描界面
                        if (CommonUtil.isCameraCanUse()) {
                            Intent intent = new Intent(mContext, CaptureActivity.class);
                            startActivityForResult(intent, Qr_REQUEST_CODE);
                        } else {
                           Toast.makeText(mContext, "请打开此应用的摄像头权限！", Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
                case R.id.rb_smash_eggs://积分砸蛋
                    intent = new Intent(mContext, SmashEggActivity.class);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("userId", userId);
                   // To_UP_REQUEST_CODE = 900;
                   // startActivityForResult(intent, To_UP_REQUEST_CODE);
                    startActivity(intent);
                    break;
                case R.id.rb_inside_bank://站内银行
                    userSecques = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_secques", "");
                    if (userSecques.equals("")) {
                       Toast.makeText(mContext, "您尚未设置密保问题", Toast.LENGTH_SHORT).show();
                        intent=new Intent(mContext, SetPwdActivity.class);
                        intent.putExtra("userId",userId);
                        intent.putExtra("userToken",userToken);
                        startActivity(intent);
                    } else {
                        intent = new Intent(mContext, InsideBankActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("userBalance", userBalance);
                        intent.putExtra("userCoin", userCoin);
                        intent.putExtra("userToken", userToken);
                      //  To_UP_REQUEST_CODE = 100;
                        //startActivityForResult(intent, To_UP_REQUEST_CODE);
                        startActivity(intent);
                    }

                    break;
                case R.id.rb_online_pay://在线充值
                    intent = new Intent(mContext, OnlinePayActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                   //To_UP_REQUEST_CODE = 50;
                   // startActivityForResult(intent, To_UP_REQUEST_CODE);
                    startActivity(intent);
                    // startActivity(new Intent(mContext, RedbagActivity.class));
                    break;

                case R.id.user_info://个人信息
                    intent = new Intent(mContext, UserInfoActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                   //startActivityForResult(intent, To_UP_REQUEST_CODE);
                    startActivity(intent);
                    // startActivity(new Intent(mContext, RedbagActivity.class));
                    break;
                case R.id.rl_account_safety://账户安全
                    intent = new Intent(mContext, AccountSafetyActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    startActivity(intent);
                   // startActivityForResult(intent, To_UP_REQUEST_CODE);
                    break;
                case R.id.rl_promote://推广下线
                    intent = new Intent(mContext, PromoteActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    startActivity(intent);
                   // startActivityForResult(intent, To_UP_REQUEST_CODE);
                    break;
                case R.id.rl_welfare_center://福利中心
                    intent = new Intent(mContext, WelfareCenterActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                 //   startActivityForResult(intent, To_UP_REQUEST_CODE);
                    startActivity(intent);
                    break;
                case R.id.rl_more_function://更多功能
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
                    unreadMsgnum=sharedPreferences.getString("user_msgnum", "");
                    intent = new Intent(mContext,MoreFunctionActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    Log.e("tag","unreadMsgnum--"+unreadMsgnum);
                    intent.putExtra("unreadMsgnum", unreadMsgnum);
                  //  startActivityForResult(intent, To_UP_REQUEST_CODE);
                    startActivity(intent);
                    break;
                case R.id.rl_set://设置
                    intent = new Intent(mContext,SetActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    startActivityForResult(intent, To_UP_REQUEST_CODE2);
                    break;

            }
        }
    }
    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(MessageEvent enevnt) {
      /*  notenum= Integer.parseInt(enevnt.result);
        Log.e("notenum", "" + notenum);
        remind(btn_msg);*/
        if(enevnt.result.equals("3")){
            userId = user_tv_id.getText().toString();
            prarams.put("usersid", userId);
            userToken = sp.getString("user_token","");
            if(!userToken.equals("")){
                prarams.put("usertoken", userToken);
                new UserInfoAsyncTask().execute();
            }else{
                readParameter();
            }
        }else if(enevnt.result.equals("7")){
            readParameter();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }
    @SuppressLint("SetTextI18n")
    @Override
    /*回调*/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == LoginActivity.RESULT_CODE) {
            //登录成功回调信息
            result = data.getStringExtra("result");
            Log.e("登录成功onActivityResult", "" + result);
            Gson gson = new Gson();
            user = gson.fromJson(result, User.class);
            SharedPrefUtility.saveData(mContext, user);
            user_tv_id.setText(user.getData().getId());
            user_name.setText(user.getData().getName());
            Log.e("tag", "usertoken-----" + getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token",""));
            Log.e("tag", "usertoken-----" + getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id",""));
            boolean contains = user.getData().getHead().contains("http");
            Log.e("tag", "contains-----" + contains);
            if (contains) {
                imagepath = user.getData().getHead();
            } else {
                imagepath = RuiXinApplication.getInstance().getUrl() + user.getData().getHead();
            }
            Log.e("tag", "u-----" + imagepath);
            Glide.with(mContext)
                    .load(imagepath)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .skipMemoryCache(true)//跳过内存缓存
                 //   .placeholder() //占位图
                   // .error()  //出错的占位图
                    .into(user_head);
            user_balance.setText(FormatUtils.formatString(user.getData().getPoints()));
            user_coin.setText(FormatUtils.formatString(user.getData().getBack()));
            user_total.setText(FormatUtils.formatString(user.getData().getExperience()));
            user_suffer.setText(FormatUtils.formatString(user.getData().getMaxexperience()));

        } else if (resultCode == CaptureActivity.RESULT_CODE_QR_SCAN) {
            Bundle bundle = data.getExtras();
            Log.e("tag", "" + bundle);
            scanResult = bundle.getString("qr_scan_result");
           randnumber=scanResult.substring(0,8);
            String os=scanResult.substring(11,scanResult.length());
            if (!scanResult.equals("")) {
                intent=new Intent(mContext, CommitScanLoginActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                intent.putExtra("randnumber", randnumber);
                intent.putExtra("os", os);
                startActivity(intent);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UserInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().Info_URL, prarams);
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
                        if (status == 1) {
                            Gson gson = new Gson();
                            user = gson.fromJson(result, User.class);
                            SharedPrefUtility.saveData(mContext, user);
                            RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                            RuiXinApplication.getInstance().setUserId(user.getData().getId());
                            user_tv_id.setText(user.getData().getId());
                            user_name.setText(user.getData().getName());
                            boolean contains = user.getData().getHead().contains("http");
                            Log.e("tag", "contains-----" + contains);
                            if (contains) {
                                imagepath = user.getData().getHead();
                            } else {
                                imagepath = RuiXinApplication.getInstance().getUrl() + user.getData().getHead();
                            }
                            Log.e("tag", "u-----" + imagepath);
                            Glide.with(mContext)
                                    .load(imagepath)
                                    .bitmapTransform(new CropCircleTransformation(mContext))
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                                    .skipMemoryCache(true)//跳过内存缓存
                                    // .load("http://b337.photo.store.qq.com/psb?/V10FcMmY1Ttz2o/7.fo01qLQ*SI59*E2Wq.j82HuPfes*efgiyEi7mrJdk!/b/dLHI5cioAQAA&bo=VQOAAgAAAAABB*Q!&rf=viewer_4")
                                   // .placeholder() //占位图
                                   // .error()  //出错的占位图
                                    .into(user_head);
                            user_balance.setText(FormatUtils.formatString(user.getData().getPoints()));
                            user_coin.setText(FormatUtils.formatString(user.getData().getBack()));
                            user_total.setText(FormatUtils.formatString(user.getData().getExperience()));
                            user_suffer.setText(FormatUtils.formatString(user.getData().getMaxexperience()));
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
                            prarams.put("vaild_str", vaildd_md5);
                            new UserInfoAsyncTask().execute();
                        } else if (status <= 0) {
                           Toast.makeText(mContext, "获取信息错误", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (index != -1) {
                   Toast.makeText(mContext, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
         //   initStatus();
            userId=getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            if (userId != null) {
                if((System.currentTimeMillis()-timeStamp)/1000>60){

                    new GetmenuInfoAsyncTask().execute();}
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetmenuInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            timeStamp = System.currentTimeMillis();
            // 请求数据
            result = AgentApi.doGet(URL.getInstance().menu);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取隐藏菜单", "消息返回结果result" + result);
            if (result != null) {
                   ListDataSave.setDataList(mContext,"menu",result);
                Gson gson = new Gson();
                List<List<String>> list=new ArrayList<>();
               list=gson.fromJson(result, new TypeToken<List<List<String>>>() {
               }.getType());
               /* RelativeLayout user_info;//个人信息
                RelativeLayout rl_account_safety;//账户安全
                RelativeLayout rl_promote;//推广下线
                RelativeLayout rl_welfare_center;//福利中心
                RelativeLayout rl_more_function;//更多功能
                RelativeLayout rl_set;//设置*/
               if(list.get(0).size()==4){
                   user_info.setVisibility(View.GONE);
               }else{
                   user_info.setVisibility(View.VISIBLE);
               }
                if(list.get(1).size()==5){
                    rl_account_safety.setVisibility(View.GONE);
                }else{
                    rl_account_safety.setVisibility(View.VISIBLE);
                }

                if(list.get(2).size()==2){
                    rl_promote.setVisibility(View.GONE);
                }else{
                    rl_promote.setVisibility(View.VISIBLE);
                }
                if(list.get(3).size()==6){
                    rl_welfare_center.setVisibility(View.GONE);
                }else{
                    rl_welfare_center.setVisibility(View.VISIBLE);
                }
                if(list.get(4).size()==6){
                    rl_more_function.setVisibility(View.GONE);
                }else{
                    rl_more_function.setVisibility(View.VISIBLE);
                }
              /*  if(list.get().contains("推广下线")&&list.contains("推广收益")){
                    rl_promote.setVisibility(View.GONE);
                }else{
                    rl_promote.setVisibility(View.VISIBLE);
                }*/
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
