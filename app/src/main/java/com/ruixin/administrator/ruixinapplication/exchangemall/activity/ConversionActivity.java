package com.ruixin.administrator.ruixinapplication.exchangemall.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.exchangemall.domain.CPrizeDb;
import com.ruixin.administrator.ruixinapplication.exchangemall.webview.CashNoticeWeb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.popwindow.ExtraNoticePop;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.MailVersionActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.SetPwdActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.UpdateInfoActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CountDown2;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.SharedPrefUtility;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

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
 * 兑换详情界面
 */
public class ConversionActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_addres)
    RelativeLayout rlAddres;
    @BindView(R.id.extra_fei)
    TextView extraFei;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.et_num)
    EditText etNum;
    ExtraNoticePop noticePop;
    @BindView(R.id.ll_conversion)
    LinearLayout llConversion;
    String userId;
    String userToken;
    String id;
    String url;
    @BindView(R.id.rl_cash_notice)
    RelativeLayout rlCashNotice;
    @BindView(R.id.tv_cuser_name)
    TextView tvCuserName;
    @BindView(R.id.tv_cuser_phone)
    TextView tvCuserPhone;
    @BindView(R.id.tv_cuser_qq)
    TextView tvCuserQq;
    @BindView(R.id.tv_cuser_mail)
    TextView tvCuserMail;
    @BindView(R.id.tv_cprize_name)
    TextView tvCprizeName;
    @BindView(R.id.tv_cprize_price)
    TextView tvCprizePrice;
    @BindView(R.id.tv_phone_version)
    TextView tvPhoneVersion;
    @BindView(R.id.et_version_code)
    EditText etVersionCode;
    @BindView(R.id.ll_product)
    LinearLayout llProduct;
    @BindView(R.id.iv_prize)
    ImageView ivPrize;
    List<String> list = new ArrayList<>();
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.iv_sub)
    ImageView ivSub;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.true_coin)
    TextView trueCoin;
    @BindView(R.id.et_answer)
    EditText etAnswer;
    @BindView(R.id.tv_pop_up)
    TextView tvPopUp;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.rl_conver)
    RelativeLayout rlConver;
    @BindView(R.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R.id.et_beizhu)
    EditText etBeizhu;
    @BindView(R.id.tv_mibao)
    TextView tvMibao;
    @BindView(R.id.rl_version_code)
    RelativeLayout rlVersionCode;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> sms_verification_code_prarams_maps = new HashMap<String, String>();
    String userName;
    String userPhone;
    String userQq;
    String userMail;
    String userQuest;
    String code;
    String type;
    String tbExchangedCount;
    String tbUserSecAns;
    String tbExchangedDescription;
    CPrizeDb cPrizeDb;
    String lsts;//●游戏流水手续费:您最近1日游戏流水：0,可免额外手续费兑奖0金币   您最近1日已兑奖：0 可免额外手续费兑奖0金币,本次兑奖105,000金币,超出部分：105,000按2%收取手续费：2,100金币//最近游戏流水天数/最近兑奖天数
    String sumls;//游戏流水
    String lscoin;//可免除额外手续费兑奖金额sumls/lsbs
    String sumtx;//最近已兑奖金额
    String sscoin;//可免除额外手续费兑奖金yxls/lsbs(已兑奖游戏流水/流水倍数）
    String lssxbl;//手续费2%
    int extra;//超出费用
    int shouxufei = 0;//手续费
    LoadingAndRetryManager mLoadingAndRetryManager;
    CountDown2 timer;
    int yz;
    int amouts;
    String shoptype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userToken = intent.getStringExtra("userToken");
        id = intent.getStringExtra("id");
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

    private void initView() {
        tvTitle.setText("确认兑换");
        readParameter();
        backArrow.setOnClickListener(new Myonclick());
        ivAdd.setOnClickListener(new Myonclick());
        ivSub.setOnClickListener(new Myonclick());
        extraFei.setOnClickListener(new Myonclick());
        tvGetCode.setOnClickListener(new Myonclick());
        llConversion.setOnClickListener(new Myonclick());
        rlCashNotice.setOnClickListener(new Myonclick());
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(rlConver, null);
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        prarams.put("id", id);
        new CPrizeDetailAsyncTask().execute();

        etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!etNum.getText().toString().equals("")) {
                    int num = Integer.parseInt(etNum.getText().toString());
                    tvSum.setText("×" + num);
                    if (cPrizeDb.getData().getLssxfopen().equals("1")) {
                        if (cPrizeDb.getData().getCssxfopen().equals("1")) {//兑换次数手续费
                            if (cPrizeDb.getData().getVipprice() * num - cPrizeDb.getData().getYxls() / cPrizeDb.getData().getLsbs() > 0) {
                                extra = (int) (cPrizeDb.getData().getVipprice() * num - cPrizeDb.getData().getYxls() / cPrizeDb.getData().getLsbs());
                                shouxufei = extra * cPrizeDb.getData().getLssxbl() / 100 + cPrizeDb.getData().getCssxf().getFee();
                            } else {
                                extra = 0;
                                shouxufei = cPrizeDb.getData().getCssxf().getFee();
                            }
                        } else {
                            if (cPrizeDb.getData().getVipprice() * num - cPrizeDb.getData().getYxls() / cPrizeDb.getData().getLsbs() > 0) {
                                extra = (int) (cPrizeDb.getData().getVipprice() * num - cPrizeDb.getData().getYxls() / cPrizeDb.getData().getLsbs());
                                shouxufei = extra * cPrizeDb.getData().getLssxbl() / 100;
                            } else {
                                extra = 0;
                                shouxufei = 0;
                            }
                        }

                    } else if (cPrizeDb.getData().getLssxfopen().equals("0") && cPrizeDb.getData().getCssxfopen().equals("1")) {
                        shouxufei = cPrizeDb.getData().getCssxf().getFee();
                    }

                    extraFei.setText("" + shouxufei);
                    int num1 = cPrizeDb.getData().getVipprice() * num;
                    int num2 = Integer.parseInt(extraFei.getText().toString());
                    int sum = num1 + num2;
                    trueCoin.setText(FormatUtils.formatString(String.valueOf(sum)));

                }

            }
        });
    }

    /*读取用户信息*/
    private void readParameter() {
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new UserInfoAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class UserInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            String result = AgentApi.dopost3(URL.getInstance().Info_URL, prarams);
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
                            User user = gson.fromJson(result, User.class);
                            SharedPrefUtility.saveData(ConversionActivity.this, user);
                            RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                            RuiXinApplication.getInstance().setUserId(user.getData().getId());
                            Log.e("tag", "readParameter");
                            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
                            userName = sharedPreferences.getString("user_name", "");
                            userQq = sharedPreferences.getString("qq", "");
                            userPhone = sharedPreferences.getString("user_phone", "");
                            userMail = sharedPreferences.getString("user_mail", "");
                            userQuest = sharedPreferences.getString("user_secques", "");
                            if ((userName.equals("") || userQq.equals("") || userPhone.equals("") || userName.equals(""))/*&&(userQuest.equals(""))&&(userMail.equals("")*/) {
                                // boolean a=/*(userName.equals("")||userQq.equals("")||userPhone.equals("")||userName.equals(""))&&(!userQuest.equals(""))*/userMail.!equals("");
                                boolean a = userMail.equals("");
                                Log.e("a", "" + a);
                                boolean b = true && true && false;
                                Log.e("b", "" + b);
                                Toast.makeText(ConversionActivity.this, "资料不全请先补全资料！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ConversionActivity.this, UpdateInfoActivity.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("userToken", userToken);
                                startActivityForResult(intent, 46);
                                finish();
                            } else if (userQuest.equals("")) {
                                Intent intent = new Intent(ConversionActivity.this, SetPwdActivity.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("userToken", userToken);
                                startActivityForResult(intent, 46);
                                finish();
                            } else if (userMail.equals("")) {
                                Log.e("userMail", "1111111111");
                                Intent intent = new Intent(ConversionActivity.this, MailVersionActivity.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("userToken", userToken);
                                startActivityForResult(intent, 46);
                                finish();
                            }
                            tvCuserName.setText("用户昵称：" + userName);
                            tvCuserPhone.setText("用户手机号：" + userPhone);
                            tvCuserQq.setText("QQ：" + userQq);
                            tvCuserMail.setText("邮箱：" + userMail);
                            etAnswer.setHint(userQuest);
                        } else if (status == -97 || status == -99) {
                            Unlogin.doLogin(ConversionActivity.this);
                        } else if (status == 99) {
                            /*抗攻击*/
                            Unlogin.doAtk(prarams, result, new UserInfoAsyncTask());
                        } else if (status <= 0) {
                            String msg = jsb.optString("msg");
                            Toast.makeText(ConversionActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (index != -1) {
                    Toast.makeText(ConversionActivity.this, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ConversionActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class Myonclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back_arrow:
                    finish();
                    break;
                case R.id.extra_fei:
                    showPop();
                    break;
                case R.id.iv_add:
                    int num = Integer.parseInt(etNum.getText().toString());
                    ++num;
                    etNum.setText("" + num);
                    break;
                case R.id.iv_sub:
                    int num1 = Integer.parseInt(etNum.getText().toString());
                    if (num1 > 0) {
                        --num1;
                    }
                    etNum.setText("" + num1);
                    break;
                case R.id.tv_get_code:
                    if (tvGetCode.getText().toString().equals("获取验证码") || tvGetCode.getText().toString().equals("重新获取")) {


                        sms_verification_code_prarams_maps.put("usersid", userId);
                        sms_verification_code_prarams_maps.put("usertoken", userToken);
                        type = "buyprize";
                        sms_verification_code_prarams_maps.put("type", type);
                        Log.e("tag", "sms_verification_code_prarams_maps" + sms_verification_code_prarams_maps);
                        if (timer == null) {
                            timer = new CountDown2(60 * 1000, 1000, tvGetCode, null);
                        } else {
                            timer.setMillisInFuture(60 * 1000);
                        }
                        timer.start();
                        new GetSmsCodeAsyncTask().execute();
                    }
                    break;
                case R.id.ll_conversion:
                    conversion();
                   /* startActivity(new Intent(ConversionActivity.this, ConversionSucessActivity.class));*/
                    break;
                case R.id.rl_cash_notice:
                    startActivity(new Intent(ConversionActivity.this, CashNoticeWeb.class));
                    break;
            }
        }
    }

    private void conversion() {
        code = etVersionCode.getText().toString();
        tbExchangedCount = etNum.getText().toString();
        tbUserSecAns = etAnswer.getText().toString();
        tbExchangedDescription = etBeizhu.getText().toString();//备注留言
        if (isValid()) {
            if(shoptype.equals("2")||shoptype.equals("3")){
                if(Integer.parseInt(tbExchangedCount)>amouts){
                       Toast.makeText(ConversionActivity.this,"兑换数量过多，商品库存不足！",Toast.LENGTH_SHORT).show();
                }else{
                    if(yz==1){
                        prarams.put("code", code);
                        new SmsCodeAsyncTask().execute();
                    }else{
                        doConversion();
                    }
                }
            }else{
                if(yz==1){
                    prarams.put("code", code);
                    new SmsCodeAsyncTask().execute();
                }else{
                    doConversion();
                }
            }


        }

    }

    @SuppressLint("NewApi")
    public boolean isValid() {

        if (tbExchangedCount.equals("0")) {
            Toast.makeText(this, "兑换数量不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tbUserSecAns.equals("")) {
            Toast.makeText(this, "密保答案不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(yz==1){
            if (code.equals("")) {
                Toast.makeText(this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }



        return true;
    }

    private void showPop() {

        noticePop = new ExtraNoticePop(ConversionActivity.this, DisplayUtil.dp2px(ConversionActivity.this, 210), DisplayUtil.dp2px(ConversionActivity.this, 80), cPrizeDb, etNum);
        //监听窗口的焦点事件，点击窗口外面则取消显示
        noticePop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    noticePop.dismiss();
                }
            }
        });
        //设置默认获取焦点
        noticePop.setFocusable(true);
        int[] location = new int[2];
        tvPopUp.getLocationOnScreen(location);
        noticePop.showAtLocation(tvPopUp, Gravity.NO_GRAVITY, location[0], location[1] - noticePop.getHeight());//以某个控件的x和y的偏移量位置开始显示窗口
        // noticePop.showAsDropDown(extraFei,0,0);
        //如果窗口存在，则更新
        noticePop.update();
    }

    private class CPrizeDetailAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().ConversionDetail_URL, prarams);
            Log.e("result", "result=" + result);
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
                        Gson gson1 = new Gson();
                        cPrizeDb = gson.fromJson(s, CPrizeDb.class);
                         yz = cPrizeDb.getData().getYz();
                        shoptype=cPrizeDb.getData().getShoptype();
                         if(shoptype.equals("2")||shoptype.equals("3")){
                             amouts=cPrizeDb.getData().getAmouts();
                         }
                        if (yz == 0) {
                        rlVersionCode.setVisibility(View.GONE);
                        }else{
                            rlVersionCode.setVisibility(View.VISIBLE);
                        }
                        String path = "http://120.78.87.50/uppic/shoppic/" + cPrizeDb.getData().getImgsrc();
                        Glide.with(ConversionActivity.this)
                                .load(path)
                                // .load("http://b337.photo.store.qq.com/psb?/V10FcMmY1Ttz2o/7.fo01qLQ*SI59*E2Wq.j82HuPfes*efgiyEi7mrJdk!/b/dLHI5cioAQAA&bo=VQOAAgAAAAABB*Q!&rf=viewer_4")
                                //  .placeholder() //占位图
                                // .error()  //出错的占位图
                                .into(ivPrize);
                        tvCprizeName.setText(cPrizeDb.getData().getPrizename());
                        tvCprizePrice.setText(FormatUtils.formatString(String.valueOf(cPrizeDb.getData().getVipprice())));
                        if (cPrizeDb.getData().getLssxfopen().equals("1")) {//流水手续费
                            if (cPrizeDb.getData().getCssxfopen().equals("1")) {//兑换次数手续费
                                if (cPrizeDb.getData().getVipprice() - cPrizeDb.getData().getYxls() / cPrizeDb.getData().getLsbs() > 0) {
                                    extra = (int) (cPrizeDb.getData().getVipprice() - cPrizeDb.getData().getYxls() / cPrizeDb.getData().getLsbs());
                                    shouxufei = extra * cPrizeDb.getData().getLssxbl() / 100 + cPrizeDb.getData().getCssxf().getFee();
                                } else {
                                    extra = 0;
                                    shouxufei = 0 + cPrizeDb.getData().getCssxf().getFee();
                                }
                            } else {
                                if (cPrizeDb.getData().getVipprice() - cPrizeDb.getData().getYxls() / cPrizeDb.getData().getLsbs() > 0) {
                                    extra = (int) (cPrizeDb.getData().getVipprice() - cPrizeDb.getData().getYxls() / cPrizeDb.getData().getLsbs());
                                    shouxufei = extra * cPrizeDb.getData().getLssxbl() / 100;
                                } else {
                                    extra = 0;
                                    shouxufei = 0;
                                }
                            }

                        } else if (cPrizeDb.getData().getLssxfopen().equals("0") && cPrizeDb.getData().getCssxfopen().equals("1")) {//兑奖次数手续费
                            shouxufei = cPrizeDb.getData().getCssxf().getFee();
                        }
                        extraFei.setText("" + shouxufei);
                        int num1 = cPrizeDb.getData().getVipprice();
                        int num2 = Integer.parseInt(extraFei.getText().toString());
                        int sum = num1 + num2;
                        trueCoin.setText(FormatUtils.formatString(String.valueOf(sum)));
                    } else if (entry.getStatus() == 99) {
    /*抗攻击*/
                        Unlogin.doAtk(prarams, s, new CPrizeDetailAsyncTask());
                    } else if (entry.getStatus() == -99 || entry.getStatus() == -97) {
                        Unlogin.doLogin(ConversionActivity.this);
                    } else {
                        Toast.makeText(ConversionActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(ConversionActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                mLoadingAndRetryManager.showContent();
                Toast.makeText(ConversionActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 46 && resultCode == MailVersionActivity.RESULT_CODE) {
            readParameter();
            new CPrizeDetailAsyncTask().execute();
        } else if (requestCode == 46 && resultCode == UpdateInfoActivity.RESULT_CODE) {
            readParameter();
        } else if (requestCode == 46 && resultCode == SetPwdActivity.RESULT_CODE) {
            readParameter();
        }
    }

    /**
     * 从服务器获取验证码
     *
     * @return
     * @throws Exception
     */
    //网络请求 异步框架,获取验证码
    class GetSmsCodeAsyncTask extends AsyncTask<String, Integer, String> {
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
            if (result != null) {
                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                if (entry != null) {
                    Log.e("获取验证码消息返回", "entry" + entry);
                    if (entry.getStatus() == 1) {
                        Toast.makeText(ConversionActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                    } else if (entry.getStatus() == 99) {
/*抗攻击*/
                        Unlogin.doAtk(sms_verification_code_prarams_maps, result, new GetSmsCodeAsyncTask());
                    } else if (entry.getStatus() == -99 || entry.getStatus() == -97) {
                        Unlogin.doLogin(ConversionActivity.this);
                    } else {
                        Toast.makeText(ConversionActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(ConversionActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
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
            if (result != null) {
                Gson gson = new Gson();
                Entry entry = gson.fromJson(result, Entry.class);
                if (entry != null) {
                    if (entry.getStatus() == 1) {
                        //验证成功，然后修改
                        doConversion();
                    } else if (entry.getStatus() == 99) {
/*抗攻击*/
                        Unlogin.doAtk(prarams, result, new SmsCodeAsyncTask());
                    } else if (entry.getStatus() == -99 || entry.getStatus() == -97) {
                        Unlogin.doLogin(ConversionActivity.this);
                    } else {
                        Toast.makeText(ConversionActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void doConversion() {
        prarams.put("tbExchangedCount", tbExchangedCount);
        prarams.put("tbUserSecAns", tbUserSecAns);
        prarams.put("tbExchangedDescription", tbExchangedDescription);
        new ConversionAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class ConversionAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            // 请求数据
            String result = AgentApi.dopost(URL.getInstance().Conversion_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("兑奖成功返回", "消息返回结果result" + result);
            if (result != null) {
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(result, Entry.class);
                    if (entry != null) {
                        //6  兑奖成功,请等待客服审查！    5   兑奖成功，邮件发送失败,请点击重发，或与管理员联系   4  兑奖成功,请在绑定邮箱查收！   3  兑奖成功,请在站内信查收！2  您的首次兑奖审核正在进行中，如有疑问请联系管理员。   1   兑奖订单已提交，您的账号是首次兑奖，需等待客服审核。
                        if (entry.getStatus() == 1 || entry.getStatus() == 2 || entry.getStatus() == 3 || entry.getStatus() == 4 || entry.getStatus() == 5 || entry.getStatus() == 6) {
                            Intent intent = new Intent(ConversionActivity.this, ConversionSucessActivity.class);
                            intent.putExtra("msg", entry.getMsg());
                            startActivity(intent);
                            finish();
                        } else if (entry.getStatus() == 99) {
/*抗攻击*/
                            Unlogin.doAtk(prarams, result, new SmsCodeAsyncTask());
                        } else if (entry.getStatus() == -99 || entry.getStatus() == -97) {
                            Unlogin.doLogin(ConversionActivity.this);
                        } else {
                            Toast.makeText(ConversionActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (index != -1) {
                    Toast.makeText(ConversionActivity.this, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
