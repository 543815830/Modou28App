package com.ruixin.administrator.ruixinapplication.usercenter.activiy;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseActivity;
import com.ruixin.administrator.ruixinapplication.MainActivity;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.domain.EntryDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.PwdCard;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown2;
import com.ruixin.administrator.ruixinapplication.utils.CustomProgressDialogWechat;
import com.ruixin.administrator.ruixinapplication.utils.ExToast;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.PhoneFormatCheckUtils;
import com.ruixin.administrator.ruixinapplication.utils.SharedPrefUtility;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.roundImageView)
    ImageView roundImageView;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.ver_line)
    View verLine;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.verified_login)
    TextView verifiedLogin;
    @BindView(R.id.wechat_login)
    ImageView wechatLogin;
    @BindView(R.id.qq_login)
    ImageView qqLogin;
    @BindView(R.id.cancel_login)
    TextView cancelLogin;
    @BindView(R.id.iv_eye)
    ImageView ivEye;
    @BindView(R.id.ll_eye)
    LinearLayout llEye;
    private Handler mHandler;
    private String tbUserAccount;
    private String tbUserPwd;
    private String logintype;
    private String imagpath;
    private String type;
    private String code;
    private String imagepath;
    private String logintext;
    private RelativeLayout ll_pwd;
    private RelativeLayout rl_code;
    private EditText rg_verification_code;
    private TextView rg_get_code;
    private Context context;
    private static final String APP_ID = "101502050";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    //手机登录的携带参数
    private HashMap<String, String> prarams = new HashMap<String, String>();
    //验证码获取验证码携带的参数
    private HashMap<String, String> sms_verification_code_prarams_maps = new HashMap<String, String>();
    private HashMap<String, String> call_back_prarams = new HashMap<String, String>();
    private HashMap<String, String> qq_prarams = new HashMap<String, String>();
    private HashMap<String, String> pwdcard_prarams = new HashMap<String, String>();
    private CustomProgressDialogWechat progressDialog_Result = null;
    public static int RESULT_CODE = 1;
    String where;
    private long lastBackPress;
    CountDown2 timer;
    Intent intent;
    String openID = null;
    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //传入参数APPID和全局Context上下文
        where = getIntent().getStringExtra("where");
        //  mTencent = Tencent.createInstance(APP_ID, getApplicationContext());
        initStatus();
        initView();

    }

    /**
     * 设置edittext,设置下次打开时拥有此次的记忆
     */
    private void parameterSettings() {
        account.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    tbUserAccount = v.getText().toString();
                    if (tbUserAccount.trim().equals("")) {//判断EditText是否为空
                       Toast.makeText(LoginActivity.this,
                                "输入不能为空，请重新输入！", Toast.LENGTH_SHORT).show();
                        account.setText(null);

                    } else {
                        while (true) {
                            return true;
                        }
                    }
                }
                return true;
            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER | event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    tbUserPwd = v.getText().toString();
                    if (tbUserPwd.trim().equals("")) {
                       Toast.makeText(LoginActivity.this,
                                "输入不能为空，请重新输入！", Toast.LENGTH_SHORT).show();
                        password.setText(null);
                    } else {
                        while (true) {

                            return true;
                        }
                    }
                }
                return true;
            }
        });
    }

    /*读取账号密码*/
    private void readParameter() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                Activity.MODE_PRIVATE);
        tbUserAccount = sharedPreferences.getString("tbUserAccount", "");
        tbUserPwd = sharedPreferences.getString("tbUserPwd", "");
        imagepath = sharedPreferences.getString("user_head", "");
        if (tbUserAccount == "" | tbUserPwd == "") {
            parameterSettings();
        } else {

            account.setText(tbUserAccount);
            password.setText(tbUserPwd);

        }
        if (imagepath == "") {
            roundImageView.setImageResource(R.drawable.iv_user);
        } else {
            boolean contains = imagepath.contains("http");
            if (contains) {
                imagpath = imagepath;
            } else {
                imagpath = RuiXinApplication.getInstance().getUrl() + imagepath;
            }
            Glide.with(LoginActivity.this)
                    .load(imagpath)
                    .bitmapTransform(new CropCircleTransformation(LoginActivity.this))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                    .skipMemoryCache(true)//跳过内存缓存
                    //.placeholder() //占位图
                   // .error()  //出错的占位图
                    .into(roundImageView);
        }
    }

    /*保存账号密码*/
    private void saveParameter() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tbUserAccount", account.getText().toString());
        editor.putString("tbUserPwd", password.getText().toString());
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings = getSharedPreferences("Selector", 0);
        Boolean user_first = settings.getBoolean("FIRST", true);
        if (user_first) {
            settings.edit().putBoolean("FIRST", false).commit();
            parameterSettings();
        } else {
            readParameter();
            parameterSettings();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveParameter();
    }

    /*初始化视图*/
    private void initView() {
        ll_pwd = findViewById(R.id.ll_pwd);
        rl_code = findViewById(R.id.rl_verficationcode);
        rl_code.setVisibility(View.GONE);
        rg_verification_code = findViewById(R.id.rg_verification_code);
        rg_get_code = findViewById(R.id.rg_get_code);
        context = getApplicationContext();
        password. setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setTransformationMethod(new AsteriskPasswordTransformationMethod());
    }


    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return '●'; // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }
    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusBarColor));
        }
    }

  /*  @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            Log.e("onWindowFocusChanged", "onWindowFocusChanged");
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN//Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION//隐藏虚拟按键(导航栏)
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE//
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
      *//*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);// SDK21
        }*//*
    }
*/

    @OnClick({R.id.ll_eye,R.id.rg_get_code, R.id.login, R.id.register, R.id.forget_password, R.id.verified_login, R.id.wechat_login, R.id.qq_login, R.id.cancel_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_eye:
              if(password.getInputType()==InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                  ivEye.setImageResource(R.mipmap.denglu_bi);
                 password. setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                  password.setTransformationMethod(new AsteriskPasswordTransformationMethod());

              }else{
                  ivEye.setImageResource(R.mipmap.denglu_zheng);
                  password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//对用户可见的密码
                  password.setTransformationMethod(null);
              }
                break;
            case R.id.rg_get_code:
                if (rg_get_code.getText().toString().equals("获取验证码") || rg_get_code.getText().toString().equals("重新获取")) {
                    tbUserAccount = account.getText().toString().trim();
                    boolean judge = PhoneFormatCheckUtils.isMobile(tbUserAccount);

                    if (TextUtils.isEmpty(tbUserAccount)) {
                       Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (judge) {
                            sms_verification_code_prarams_maps.put("number", tbUserAccount);
                            type = "login";
                            sms_verification_code_prarams_maps.put("type", type);
                            if (timer == null) {
                                timer = new CountDown2(60 * 1000, 1000, rg_get_code, null);
                            } else {
                                timer.setMillisInFuture(60 * 1000);
                            }
                            timer.start();
                            new GetSmsCodeAsyncTask().execute();
                        } else {
                           Toast.makeText(this, "手机号不合法", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.login:
                logintext = verifiedLogin.getText().toString().trim();
                if (logintext.equals("验证手机登录")) {
                    login();
                } else if (logintext.equals("账号密码登录")) {
                    codelogin();
                }
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.forget_password://忘记密码
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.verified_login:
                textChange();
                break;
            case R.id.wechat_login:
                startActivity(new Intent(LoginActivity.this, PhoneVersionActivity.class));
                break;
            case R.id.qq_login:
             /*   mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(LoginActivity.this, "all", mIUiListener);
                mUserInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken()); //获取用户信息
                mUserInfo.getUserInfo(mIUiListener);*/
                mobQlogin();
                break;
            case R.id.cancel_login:
                finish();
                break;
        }
    }

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
//执行耗时操作
            startProgressDialog_Result("正在登录");
        }
    };

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
                openID = platform.getDb().getUserId();
                accessToken = platform.getDb().getToken();
                Log.e("sss", platform.getDb().getUserId());
                Log.e("sss", platform.getDb().getToken());
                call_back_prarams.put("openid", openID);
                call_back_prarams.put("token", accessToken);
                new Thread() {
                    public void run() {
                        Looper.prepare();
                        new Handler().post(runnable);//在子线程中直接去new 一个handler
                        Looper.loop();//这种情况下，Runnable对象是运行在子线程中的，可以进行联网操作，但是不能更新UI
                    }
                }.start();
                new CallBackAsyncTask().execute();
                //Toast.makeText(LoginActivity.this,"登录成功了",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.i("sss", throwable.toString());
               Toast.makeText(context, "登录失败了", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.i("sss", "onCancel");
               Toast.makeText(context, "登录取消了", Toast.LENGTH_SHORT).show();

            }
        });
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(false);
//        plat.isClientValid()
        //获取用户资料
        plat.showUser(null);
    }

    private void startProgressDialog_Result(String str) {
        if (progressDialog_Result == null) {
            progressDialog_Result = CustomProgressDialogWechat.createDialog(this);
        }
        progressDialog_Result.setMessage(str);
        progressDialog_Result.show();
    }

    private void stopProgressDialog_Result() {
        if (progressDialog_Result != null) {
            progressDialog_Result.dismiss();
            progressDialog_Result = null;
        }
    }

    private void textChange() {
        logintext = verifiedLogin.getText().toString().trim();
        if (logintext.equals("验证手机登录")) {
            ll_pwd.setVisibility(View.GONE);
            rl_code.setVisibility(View.VISIBLE);
            verifiedLogin.setText("账号密码登录");
        } else if (logintext.equals("账号密码登录")) {
            ll_pwd.setVisibility(View.VISIBLE);
            rl_code.setVisibility(View.GONE);
            verifiedLogin.setText("验证手机登录");
        }
    }

    /*手机验证登录*/
    private void codelogin() {
        tbUserAccount = account.getText().toString().trim();
        code = rg_verification_code.getText().toString().trim();
        if (isValid()) {
            prarams.put("code", code);
            startProgressDialog_Result("正在登录...");
            new SmsCodeAsyncTask().execute();
        }
    }

    public boolean isValid() {
        if (tbUserAccount.equals("")) {
           Toast.makeText(this, "请输入手机号或者邮箱账号!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (code.equals("")) {
           Toast.makeText(this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 从服务器获取验证码
     *
     * @return
     * @throws Exception
     */
    //网络请求 异步框架,获取验证码
    @SuppressLint("StaticFieldLeak")
    private class GetSmsCodeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost(URL.getInstance().AJAX_MOBILE_APP, sms_verification_code_prarams_maps);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取验证码消息返回", "消息返回结果result" + result);
            if (result.length() > 0) {

                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                Log.e("获取验证码消息返回", "entry" + entry);
                if (entry.getStatus() == 1) {
                   Toast.makeText(context, "获取验证码成功", Toast.LENGTH_SHORT).show();
                } else {
                   Toast.makeText(context, entry.getMsg(), Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(context, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /*验证验证码*/
    @SuppressLint("StaticFieldLeak")
    private class SmsCodeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost2(URL.getInstance().CODE_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("验证验证码消息返回", "消息返回结果result" + result);
            if (result.length() > 0) {
                // int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                // if (index == 0) {
                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                Log.e("status", "" + entry.getStatus());
                if (entry.getStatus() == 1) {
                    //验证成功，然后登录
                    logintype = "2";
                    prarams.put("mobile", tbUserAccount);
                    prarams.put("logintype", logintype);
                    Log.e("pa", "" + prarams);
                    new LoginAsyncTask().execute();
                } else {
                    stopProgressDialog_Result();
                   Toast.makeText(context, entry.getMsg(), Toast.LENGTH_SHORT).show();
                }
                //}else if(index!=-1){
                //Toast.makeText(context, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                //}
            } else {
               Toast.makeText(context, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void login() {
        if (isUserNameAndPwdValid()) {
            tbUserAccount = account.getText().toString().trim();
            // boolean judge = PhoneFormatCheckUtils.isMobile(tbUserAccount);
            boolean judge = true;
            if (judge) {
                tbUserPwd = password.getText().toString().trim();
                logintype = "1";
                prarams.put("logintype", logintype);
                prarams.put("tbUserAccount", tbUserAccount);
                prarams.put("tbUserPwd", tbUserPwd);
                startProgressDialog_Result("正在登录");
                new LoginAsyncTask().execute();
            } else {
               Toast.makeText(this, "手机号不合法", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 判断用户名和密码是否有效
     *
     * @return
     */
    public boolean isUserNameAndPwdValid() {
        // 用户名和密码不得为空
        if (account.getText().toString().trim().equals("")) {
           Toast.makeText(this, getString(R.string.accountName_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.getText().toString().trim().equals("")) {
           Toast.makeText(this, getString(R.string.password_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*验证登录账号密码登录*/
    @SuppressLint("StaticFieldLeak")
    private class LoginAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost(URL.getInstance().LOGIN_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("登录的消息返回", "消息返回结果result" + result);
            if (result.length() > 4) {
                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                if (entry.getStatus() == 1) {
                    if (where.equals("1")) {
                        stopProgressDialog_Result();
                        Gson gson1 = new Gson();
                        User user = gson1.fromJson(result, User.class);
                        SharedPrefUtility.saveData(LoginActivity.this, user);
                        Intent intent = new Intent();
                        intent.putExtra("result", result);
                        setResult(RESULT_CODE, intent);
                        finish();
                    } else if (where.equals("5")) {
                        stopProgressDialog_Result();
                        Gson gson1 = new Gson();
                        User user = gson1.fromJson(result, User.class);
                        SharedPrefUtility.saveData(LoginActivity.this, user);
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("where", "4");
                        removeALLActivity();
                        startActivity(intent);
                        finish();
                    } else {
                        stopProgressDialog_Result();
                        Gson gson1 = new Gson();
                        User user = gson1.fromJson(result, User.class);
                        SharedPrefUtility.saveData(LoginActivity.this, user);
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("where", "3");
                        removeALLActivity();
                        startActivity(intent);

                        finish();
                    }
                } else if (entry.getStatus() == 99) {
                    /*抗攻击*/
                    Unlogin.doAtk(prarams,result,new LoginAsyncTask());
                    stopProgressDialog_Result();
                } else if (entry.getStatus() == -12) {
                    PwdCard pwdCard = gson.fromJson(result, PwdCard.class);
                    String carddata = pwdCard.getData().getChaeckcard_data();
                    Log.e("carddata", "" + carddata);
                    String y = carddata.substring(0, 1);
                    String x = carddata.substring(1, 2);
                    pwdcard_prarams.put("y", y);
                    pwdcard_prarams.put("x", x);
                    String userid = String.valueOf(pwdCard.getData().getUsersid());
                    pwdcard_prarams.put("usersid", userid);
                    String number = String.valueOf(pwdCard.getData().getChaeckcard_data());
                    stopProgressDialog_Result();
                    //Toast.makeText(context, "您绑定了密保卡，需要密保卡二次登录", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_login, null);
// 设置我们自己定义的布局文件作为弹出框的Content
                    builder.setView(view);
//这个位置十分重要，只有位于这个位置逻辑才是正确的
                    final AlertDialog dialog = builder.show();
                    TextView card_number = view.findViewById(R.id.card_number);
                    final EditText card_acount = view.findViewById(R.id.card_account);
                    card_number.setText(number);
                    final Button commit = view.findViewById(R.id.commit);
                    final Button cancel = view.findViewById(R.id.cancel);
                    commit.setSelected(true);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancel.setSelected(true);
                            commit.setSelected(false);
                            //关闭对话框
                            dialog.dismiss();
                        }
                    });
                    commit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            commit.setSelected(true);
                            cancel.setSelected(false);
                            //立即启动+关闭对话框
                            String codetxt = card_acount.getText().toString();
                            pwdcard_prarams.put("codetxt", codetxt);
                            //写相关的服务代码
                            new PwdcardLoginAsyncTask().execute();
                            dialog.dismiss();
                        }
                    });
                } else if (entry.getStatus() == -8) {
                    stopProgressDialog_Result();
                   Toast.makeText(context, "您尚未注册账号！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }else {
                    stopProgressDialog_Result();
                    Toast.makeText(context, entry.getMsg(), Toast.LENGTH_SHORT).show();
                }
            } else {
                stopProgressDialog_Result();
               Toast.makeText(context, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object result) {
           Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
            JSONObject obj = (JSONObject) result;
            try {

                try {
                    openID = obj.getString("openid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                accessToken = obj.getString("access_token");
                call_back_prarams.put("openid", openID);
                call_back_prarams.put("token", accessToken);
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e("tag", "登录成功" + response.toString());
                        if (response == null) {
                            return;
                        }
                        try {
                            //JSONObject jo = (JSONObject) response;
                            startProgressDialog_Result("正在登录");
                            new CallBackAsyncTask().execute();
                            //Toast.makeText(context, "登录成功", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e("tag", "登录失败" + uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e("tag", "登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
           Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
           Toast.makeText(context, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        } else if (requestCode == 1 && resultCode == PhoneVersionActivity.RESULT_CODE) {
            startProgressDialog_Result("正在登录");
            new CallBackAsyncTask().execute();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    String url = URL.getInstance().CallBack_URL + "?act=login";

    //网络请求 异步框架,qq登录回调
    @SuppressLint("StaticFieldLeak")
    private class CallBackAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost3(url, call_back_prarams);
            Log.e("回调参数传递", "call_back_prarams=" + call_back_prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("回调消息返回", "消息返回结果result" + result);
            if (result.length() > 0) {
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    try {
                        JSONObject jsb = new JSONObject(result);
                        int status = jsb.optInt("status");
                        String msg=null;
                        if (status == 1) {
                            Gson gson = new Gson();
                            EntryDb entry = gson.fromJson(result, EntryDb.class);
                            Log.e("getStatus", "getStatus1");
                           Toast.makeText(context, "回调成功", Toast.LENGTH_SHORT).show();
                            String qcuid = entry.getData().getQcuid();
                            String qcpass = entry.getData().getQcpass();
                            qq_prarams.put("logintype", "3");
                            qq_prarams.put("qcuid", qcuid);
                            qq_prarams.put("qcpass", qcpass);
                            new QQLoginAsyncTask().execute();
                        } else if (status == 2) {//跳过验证的
                            Log.e("getStatus", "getStatus2");
                            if (where.equals("1")) {
                               Toast.makeText(context, "QQ登录成功", Toast.LENGTH_SHORT).show();
                                stopProgressDialog_Result();
                                Gson gson1 = new Gson();
                                User user = gson1.fromJson(result, User.class);
                                SharedPrefUtility.saveData(LoginActivity.this, user);
                              /*  RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                                RuiXinApplication.getInstance().setUserId(user.getData().getId());*/
                                Intent intent = new Intent();
                                intent.putExtra("result", result);
                                setResult(RESULT_CODE, intent);
                                finish();
                            } else if (where.equals("5")) {
                                stopProgressDialog_Result();
                                Gson gson1 = new Gson();
                                User user = gson1.fromJson(result, User.class);
                                SharedPrefUtility.saveData(LoginActivity.this, user);
                              /*  RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                                RuiXinApplication.getInstance().setUserId(user.getData().getId());*/
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("where", "4");
                                removeALLActivity();
                                startActivity(intent);
                                //
                                finish();
                            } else {
                                stopProgressDialog_Result();
                                Gson gson1 = new Gson();
                                User user = gson1.fromJson(result, User.class);
                                SharedPrefUtility.saveData(LoginActivity.this, user);
                              /*  RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                                RuiXinApplication.getInstance().setUserId(user.getData().getId());*/
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("where", "3");
                                removeALLActivity();
                                startActivity(intent);
                            }
                           /*Toast.makeText(context, "QQ登录成功", Toast.LENGTH_SHORT).show();
                            stopProgressDialog_Result();
                            Gson gson1 = new Gson();
                            User user = gson1.fromJson(result, User.class);
                            SharedPrefUtility.saveData(LoginActivity.this, user);
                            RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                            RuiXinApplication.getInstance().setUserId(user.getData().getId());
                            Intent intent = new Intent();
                            intent.putExtra("result", result);
                            setResult(RESULT_CODE, intent);
                            finish();*/
                        } else if (status == 0) {//如果qq没绑定就验证手机号，验证通过后台自动为该手机号绑定这个qq或微信，如果验证不通过，手机号未注册就跳转到注册界面？
                          /*  Log.e("getStatus", "getStatus2");
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setMessage("您还没有绑定qq,请先登录到个人中心界面绑定qq")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            intent = new Intent(LoginActivity.this, PhoneVersionActivity.class);
                                            intent.putExtra("openid", openID);
                                            intent.putExtra("token", accessToken);
                                            startActivityForResult(intent, 1);
                                            // startActivity(new Intent(LoginActivity.this,PhoneVersionActivity.class));
                                        }
                                    })
                                    .show();*/
                          msg= jsb.optString("msg");
                             Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            intent = new Intent(LoginActivity.this, PhoneVersionActivity.class);
                            intent.putExtra("openid", openID);
                            intent.putExtra("token", accessToken);
                            startActivityForResult(intent, 1);
                            stopProgressDialog_Result();
                        } else {
                            msg= jsb.optString("msg");
                            stopProgressDialog_Result();
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (index != -1) {
                   Toast.makeText(context, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            } else {
                stopProgressDialog_Result();
               Toast.makeText(context, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //回调成功，然后用密码UID和进行登录
    @SuppressLint("StaticFieldLeak")
    private class QQLoginAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost3(URL.getInstance().QQLogin_URL, qq_prarams);
            Log.e("回调参数传递", "qq_prarams=" + qq_prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("qqd登录消息返回", "消息返回结果result" + result);
            if (result.length() > 0) {
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(result, Entry.class);
                    if (entry.getStatus() == 1) {
                        if (where.equals("1")) {
                           Toast.makeText(context, "QQ登录成功", Toast.LENGTH_SHORT).show();
                            stopProgressDialog_Result();
                            Gson gson1 = new Gson();
                            User user = gson1.fromJson(result, User.class);
                            SharedPrefUtility.saveData(LoginActivity.this, user);
                          /*  RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                            RuiXinApplication.getInstance().setUserId(user.getData().getId());*/
                            Intent intent = new Intent();
                            intent.putExtra("result", result);
                            setResult(RESULT_CODE, intent);
                            finish();
                        } else if (where.equals("5")) {
                            stopProgressDialog_Result();
                            Gson gson1 = new Gson();
                            User user = gson1.fromJson(result, User.class);
                            SharedPrefUtility.saveData(LoginActivity.this, user);
                          /*  RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                            RuiXinApplication.getInstance().setUserId(user.getData().getId());*/
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("where", "4");
                            removeALLActivity();
                            startActivity(intent);
                            //
                            finish();
                        } else {
                            stopProgressDialog_Result();
                            Gson gson1 = new Gson();
                            User user = gson1.fromJson(result, User.class);
                            SharedPrefUtility.saveData(LoginActivity.this, user);
                           /* RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                            RuiXinApplication.getInstance().setUserId(user.getData().getId());*/
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("where", "3");
                            removeALLActivity();
                            startActivity(intent);
                        }
                        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else if (entry.getStatus() == -12) {
                        PwdCard pwdCard = gson.fromJson(result, PwdCard.class);
                        String carddata = pwdCard.getData().getChaeckcard_data();
                        Log.e("carddata", "" + carddata);
                        String y = carddata.substring(0, 1);
                        Log.e("y", "" + y);
                        String x = carddata.substring(1, 2);

                        Log.e("x", "" + x);
                        // String y = pwdCard.getData().getY();
                        pwdcard_prarams.put("y", y);
                        // String x = String.valueOf(pwdCard.getData().getX());
                        pwdcard_prarams.put("x", x);
                        String userid = String.valueOf(pwdCard.getData().getUsersid());
                        pwdcard_prarams.put("usersid", userid);
                        String number = String.valueOf(pwdCard.getData().getChaeckcard_data());
                        stopProgressDialog_Result();
                        //Toast.makeText(context, "您绑定了密保卡，需要密保卡二次登录", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_login, null);
                        // 设置我们自己定义的布局文件作为弹出框的Content
                        builder.setView(view);
                        //这个位置十分重要，只有位于这个位置逻辑才是正确的
                        final AlertDialog dialog = builder.show();
                        TextView card_number = view.findViewById(R.id.card_number);
                        final EditText card_acount = view.findViewById(R.id.card_account);
                        card_number.setText(number);
                        final Button commit = view.findViewById(R.id.commit);
                        final Button cancel = view.findViewById(R.id.cancel);
                        commit.setSelected(true);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cancel.setSelected(true);
                                commit.setSelected(false);
                                //关闭对话框
                                dialog.dismiss();
                            }
                        });
                        commit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commit.setSelected(true);
                                cancel.setSelected(false);
                                //立即启动+关闭对话框
                                String codetxt = card_acount.getText().toString();
                                pwdcard_prarams.put("codetxt", codetxt);
                                //写相关的服务代码
                                new PwdcardLoginAsyncTask().execute();
                                dialog.dismiss();
                            }
                        });
                    } else if (entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(qq_prarams,result,new QQLoginAsyncTask());
                    }else{
                        Toast.makeText(context, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(context, "访问出错了，请稍后！", Toast.LENGTH_SHORT).show();
                }
            } else {
                stopProgressDialog_Result();
               Toast.makeText(context, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class PwdcardLoginAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost3(URL.getInstance().LPwdCard_URL, pwdcard_prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("密保卡登录", "消息返回结果result" + result);
            if (result != null) {
                try {
                    JSONObject jsb = new JSONObject(result);
                    int status = jsb.optInt("status");
                    if (status == 1) {
                        if (where.equals("1")) {
                            //Toast.makeText(context, "QQ登录成功", Toast.LENGTH_SHORT).show();
                            stopProgressDialog_Result();
                            Gson gson1 = new Gson();
                            User user = gson1.fromJson(result, User.class);
                            SharedPrefUtility.saveData(LoginActivity.this, user);
                           /* RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                            RuiXinApplication.getInstance().setUserId(user.getData().getId());*/
                            Intent intent = new Intent();
                            intent.putExtra("result", result);
                            setResult(RESULT_CODE, intent);
                            finish();
                        } else if (where.equals("5")) {
                            stopProgressDialog_Result();
                            Gson gson1 = new Gson();
                            User user = gson1.fromJson(result, User.class);
                            SharedPrefUtility.saveData(LoginActivity.this, user);
                          /*  RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                            RuiXinApplication.getInstance().setUserId(user.getData().getId());*/
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("where", "4");
                            removeALLActivity();
                            startActivity(intent);
                            //
                            finish();
                        } else {
                            stopProgressDialog_Result();
                            Gson gson1 = new Gson();
                            User user = gson1.fromJson(result, User.class);
                            SharedPrefUtility.saveData(LoginActivity.this, user);
                            /*RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                            RuiXinApplication.getInstance().setUserId(user.getData().getId());*/
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("where", "3");
                            removeALLActivity();
                            startActivity(intent);
                        }
                        //startActivity(new Int
                    }  else if (status == -4) {
                       Toast.makeText(context, "您开启了短信验证登录功能，仅支持短信验证快捷登录！", Toast.LENGTH_SHORT).show();
                        stopProgressDialog_Result();
                    } else if (status == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(pwdcard_prarams,result,new PwdcardLoginAsyncTask());
                    }else {
                        Toast.makeText(context, "密保卡验证错误，请重新登录", Toast.LENGTH_SHORT).show();
                        stopProgressDialog_Result();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
               Toast.makeText(context, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

   /* @Override
    public void onBackPressed() {

            Log.e("tag","onBackPressed2");
            if (System.currentTimeMillis() - lastBackPress < 1000) {
                super.onBackPressed();
            } else {
                Log.e("tag","onBackPressed3");
                lastBackPress = System.currentTimeMillis();
               Toast.makeText(LoginActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }*/

}


