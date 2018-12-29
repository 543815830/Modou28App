package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.ConversionActivity;
import com.ruixin.administrator.ruixinapplication.popwindow.ExtraNoticePop;
import com.ruixin.administrator.ruixinapplication.popwindow.ExtraNoticePop2;
import com.ruixin.administrator.ruixinapplication.popwindow.MyFechPop;
import com.ruixin.administrator.ruixinapplication.popwindow.MyFechPop2;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.DepositRecordActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.DepositSucessActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.GoldDepositActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.MailVersionActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.SetPwdActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.UpdateInfoActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.UserInfoActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.DepositCoinRcyAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.GoldDepositDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 李丽 on 2018/11/26.
 * 金币提现
 */

public class GoldDepositFragment extends BaseFragment {
    private View view;
    LinearLayout ll_fm_deposit;
    LinearLayout ll_shoufei;
    TextView deposit_record;
    TextView tv_user_secques;
    TextView tv_shoufei;
    TextView deduct_coin;
    EditText et_deposit_coins;
    EditText et_tbusernick;
    TextView et_fech;
    RecyclerView rcy_coin_deposit;
    DepositCoinRcyAdapter adapter;
    int[] array = new int[]{50, 100, 500, 1000, 5000, 10000};
    String userId, userToken, userSecques, money, tbUserNick, bankname;
    Button deposit;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    String url,url2;
    MyFechPop2 pop;
    GoldDepositDb goldDepositDb;
    List<GoldDepositDb.DataBean.CardlistBean> fechlist = new ArrayList<>();
    ExtraNoticePop2 pop2;
   String truePrice;//提现数额
    int extra;
    String shouxufei;
    String time_shouxufei;
    int deduct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected View initView() {
        if (view == null) {
            view = View.inflate(mContext, R.layout.fm_gold_deposit, null);
            ll_fm_deposit = view.findViewById(R.id.ll_fm_deposit);
            rcy_coin_deposit = view.findViewById(R.id.rcy_coin_deposit);
            deposit_record = view.findViewById(R.id.deposit_record);
            tv_user_secques = view.findViewById(R.id.tv_user_secques);
            et_deposit_coins = view.findViewById(R.id.et_deposit_coins);
            et_fech = view.findViewById(R.id.et_fech);
            ll_shoufei = view.findViewById(R.id.ll_shoufei);
            tv_shoufei = view.findViewById(R.id.tv_shoufei);
            et_tbusernick = view.findViewById(R.id.et_tbusernick);
            deduct_coin = view.findViewById(R.id.deduct_coin);
            deduct_coin.setText("0");
            et_deposit_coins.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                   initShouxufei(goldDepositDb);
                    if(et_deposit_coins.getText().toString().equals("")){
                        deduct =Integer.parseInt(tv_shoufei.getText().toString());
                    }else{
                        deduct = (int)(Double.parseDouble(et_deposit_coins.getText().toString()) * 1000 + Double.parseDouble(tv_shoufei.getText().toString()));
                    }
                    deduct_coin.setText(""+deduct);
                }
            });
            deposit = view.findViewById(R.id.deposit);
            deposit_record.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            deposit_record.setOnClickListener(new MyOnclick());
            tv_shoufei.setOnClickListener(new MyOnclick());

            deposit.setOnClickListener(new MyOnclick());
            adapter = new DepositCoinRcyAdapter(mContext, array);
            adapter.setOnItemClickListener(new DepositCoinRcyAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, String data) {
                    et_deposit_coins.setText(data);
                }
            });

            rcy_coin_deposit.setAdapter(adapter);
            rcy_coin_deposit.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
            userId = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
            userToken = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
            userSecques = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_secques", "");
            tv_user_secques.setText(new StringBuilder().append("密保问题：").append(userSecques).toString());
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            url = URL.getInstance().deposit_info;
            new GoldDepositeAsyncTask().execute();
            et_fech.setOnClickListener(new MyOnclick());
        }
        return view;
    }

    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.deposit_record:
                    Intent intent = new Intent(mContext, DepositRecordActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    startActivity(intent);
                    break;
                case R.id.deposit:
                    money = et_deposit_coins.getText().toString().trim();
                    tbUserNick = et_tbusernick.getText().toString().trim();
                    bankname = et_fech.getText().toString().trim();
                    if(pop!=null){
                        pop.SetPL(new MyFechPop2.SetPid() {
                            @Override
                            public void setParams(int i) {
                                prarams.put("accid",fechlist.get(i).getId());
                            }
                        });
                    }

                    if (isValid()) {
                        prarams.put("money", money);
                        prarams.put("tbUserNick", tbUserNick);
                        prarams.put("bankname", bankname);
                        url2 = URL.getInstance().deposit_info + "?act=jbtx";
                        new CGoldDepositeAsyncTask().execute();
                    }
                    break;
                case R.id.et_fech:
                    if (fechlist != null && fechlist.size() > 0) {
                        pop=null;
                        showPop();
                    }else{
                        Toast.makeText(mContext, "请尚未绑定收款账号!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.tv_shoufei:
                        showPop2();
                    break;
            }
        }
    }

    private void showPop2() {
        pop2 = new ExtraNoticePop2(mContext, DisplayUtil.dp2px(mContext, 210), DisplayUtil.dp2px(mContext, 80),goldDepositDb,et_deposit_coins);
        //监听窗口的焦点事件，点击窗口外面则取消显示
        pop2.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    pop2.dismiss();
                }
            }
        });
        //设置默认获取焦点
        pop2.setFocusable(true);
        int[] location = new int[2];
        ll_shoufei.getLocationOnScreen(location);
        pop2.showAtLocation(ll_shoufei, Gravity.NO_GRAVITY, location[0], location[1] - pop2.getHeight());//以某个控件的x和y的偏移量位置开始显示窗口
        // noticePop.showAsDropDown(extraFei,0,0);
        //如果窗口存在，则更新
        pop2.update();
    }

    public boolean isValid() {
        if (money.equals("")) {
            Toast.makeText(mContext, "请输入提现金额!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tbUserNick.equals("")) {
            Toast.makeText(mContext, "请输入密保问题!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bankname.equals("")) {
            Toast.makeText(mContext, "请选择收款方式!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showPop() {
        if (pop == null) {
            pop = new MyFechPop2(mContext, fechlist, et_fech);
        }

        //监听窗口的焦点事件，点击窗口外面则取消显示
        pop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    pop.dismiss();
                }
            }
        });
        //设置默认获取焦点
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        // mytrusteePop.showAsDropDown(ll_choose_scheme,0,0);
        pop.showAtLocation(ll_fm_deposit, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //如果窗口存在，则更新
        pop.update();
    }


    @SuppressLint("StaticFieldLeak")
    private class GoldDepositeAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(url, prarams);
            Log.e("金币提现的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    goldDepositDb = gson.fromJson(s, GoldDepositDb.class);
                    if (goldDepositDb.getStatus() == 1) {
                        fechlist = goldDepositDb.getData().getCardlist();
                        Log.e("fechlist", "-----" + fechlist.size());
                        initShouxufei(goldDepositDb);

                    } else if (goldDepositDb.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams, s, new GoldDepositeAsyncTask());
                        //  new MyTrusteeAsyncTask().execute();
                    } else if (goldDepositDb.getStatus() == -97 || goldDepositDb.getStatus() == -99) {
                        Unlogin.doLogin(mContext);
                    } else if (goldDepositDb.getStatus() == -2) {

                        Toast.makeText(mContext, "资料不全请先补全资料！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, MailVersionActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("userToken", userToken);
                        startActivity(intent);
                    }else if (goldDepositDb.getStatus() == -3) {

                        Toast.makeText(mContext, "资料不全请先补全资料！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, SetPwdActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("userToken", userToken);
                        startActivity(intent);
                    } else {
                        Entry entry = gson.fromJson(s, Entry.class);
                        Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void initShouxufei(GoldDepositDb goldDepositDb) {
        Log.e("tag","initShouxufei");
        if(et_deposit_coins.getText().toString().equals("")){
            truePrice="0";
        }else{
            truePrice= String.valueOf(Double.parseDouble(et_deposit_coins.getText().toString())*1000);
        }
        Log.e("tag","initShouxufei"+truePrice);
        if(goldDepositDb.getData().getLssxf().getState().equals("1")){
            if(goldDepositDb.getData().getCssxf().getState().equals("1")){//兑换次数手续费
                if(!truePrice.equals("0")){
                    if(Double.parseDouble(truePrice)-goldDepositDb.getData().getLssxf().getDetail().getYxls()/goldDepositDb.getData().getLssxf().getDetail().getLsbs()>0){
                        Log.e("tag","大于0");
                        extra= (int) (Double.parseDouble(truePrice)-goldDepositDb.getData().getLssxf().getDetail().getYxls()/goldDepositDb.getData().getLssxf().getDetail().getLsbs());
                        shouxufei= String.valueOf(extra*goldDepositDb.getData().getLssxf().getDetail().getLssxbl()/100);
                    }else{
                        extra=0;
                        shouxufei="0";
                    }
                }else{
                    extra=0;
                    shouxufei="0";
                }

                time_shouxufei= String.valueOf(goldDepositDb.getData().getCssxf().getDetail().getFee());
                int totalfei=Integer.parseInt(shouxufei)+(int)(Double.parseDouble(time_shouxufei));
                Log.e("tag","initShouxufei"+totalfei);
               tv_shoufei.setText(""+totalfei);
            }else{
                if(!truePrice.equals("0")){
                if(Double.parseDouble(truePrice)-goldDepositDb.getData().getLssxf().getDetail().getYxls()/goldDepositDb.getData().getLssxf().getDetail().getLsbs()>0){
                    Log.e("tag","大于0");
                    extra= (int) (Double.parseDouble(truePrice)-goldDepositDb.getData().getLssxf().getDetail().getYxls()/goldDepositDb.getData().getLssxf().getDetail().getLsbs());
                    shouxufei= String.valueOf(extra*goldDepositDb.getData().getLssxf().getDetail().getLssxbl()/100);
                }else{
                    extra=0;
                    shouxufei="0";
                }
                    tv_shoufei.setText(shouxufei);
                }else{
                    tv_shoufei.setText("0");
                }

            }


        }else if(goldDepositDb.getData().getLssxf().getState().equals("0")&&goldDepositDb.getData().getCssxf().getState().equals("1")){
            shouxufei=  String.valueOf(goldDepositDb.getData().getCssxf().getDetail().getFee());
            tv_shoufei.setText(shouxufei);
        }else{
            tv_shoufei.setText("0");
        }



    }

    private class CGoldDepositeAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(url2, prarams);
            Log.e("金币提现的返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    GoldDepositDb goldDepositDb = gson.fromJson(s, GoldDepositDb.class);
                    if (goldDepositDb.getStatus() == 1) {
                      //  Toast.makeText(mContext, "提现申请成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new MessageEvent("3"));

                        Intent intent = new Intent(mContext, DepositSucessActivity.class);
                        intent.putExtra("msg", entry.getMsg());
                      //  intent.putExtra("userToken", userToken);
                        startActivity(intent);
                    } else if (goldDepositDb.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams, s, new GoldDepositeAsyncTask());
                        //  new MyTrusteeAsyncTask().execute();
                    } else if (goldDepositDb.getStatus() == -97 || goldDepositDb.getStatus() == -99) {
                        Unlogin.doLogin(mContext);
                    } else {
                        Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("isVisibleToUser", "" + isVisibleToUser);
        if(isVisibleToUser==true){
            if(!(userId==null&&userToken==null)){
                new GoldDepositeAsyncTask().execute();
            }

        }
    }

}
