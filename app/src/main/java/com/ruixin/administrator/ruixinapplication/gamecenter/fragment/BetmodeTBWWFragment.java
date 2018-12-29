package com.ruixin.administrator.ruixinapplication.gamecenter.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.TWGameNumberAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.LastBetDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessageEvent2;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessageModeEvent;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessagemybetEvent;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessagenextEvent;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MoneyDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MyBetpointsDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MybetModeDB;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.PeilvDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.domain.GetNumber;
import com.ruixin.administrator.ruixinapplication.popwindow.MyBetModePop;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CustomViewPager;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.SetHight;
import com.ruixin.administrator.ruixinapplication.utils.URL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李丽 on 2018/6/14.
 * 骰宝等类型的投注模式
 */

public class BetmodeTBWWFragment extends BaseFragment{
    private View view;
    private CustomViewPager fm_dw_vp;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
   TWFragment fmtw1=new TWFragment();
    private int position;
    String where;
    String no;
    String gameType;
    String gameName;
    String userId;
    String userToken;
    String result;
    int []array;
    List<Double> odds=new ArrayList<>();
    boolean exit=false;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    MybetModeDB.DataBean.ModeljsonBean modelist;
    List<String> MoneyPoints=new ArrayList<>();
    private TabLayout tabLayout;
    String mulp="1";
    Double mp1;
    private List <MoneyDb> moneyList2= new ArrayList<>();
    private String[]tabs=new String[]{"双面","短牌","长牌","三军","全骰"};
    List<Integer> mybetpoints=new ArrayList<Integer>();
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==1){
                Message msg1 = new Message();
                msg1.what =1;
                BetModeActivity.handler.sendMessage(msg1);
            }else if(msg.what ==2){
                modelist=null;
                EventBus.getDefault().post(new MessageModeEvent(modelist));//清除
            }else if(msg.what ==3){
                BetModeActivity.SetPL(new BetModeActivity.SetList() {
                    @Override
                    public void setParams(MybetModeDB.DataBean.ModeljsonBean list1) {
                        modelist=list1;
                        MoneyPoints= modelist.getMoneyPoints();
                        EventBus.getDefault().post(new MessageModeEvent(modelist));
                    }
                });

            }
        }
    };
    Runnable thread = new Runnable() {
        @Override
        public void run() {// TODO Auto-generated method stub


            new PeilvAsyncTask().execute();
            if(!exit){
                handler.postDelayed(this,5000);
            }
        }
    };
    @Override
    protected View initView() {
        if (view == null) {
            EventBus.getDefault().register(this);
            gameType = (String)getArguments().get("gameType");
            gameName = (String)getArguments().get("gameName");
            userId = (String)getArguments().get("userId");
            userToken = (String)getArguments().get("userToken");
            where = (String)getArguments().get("where");
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            prarams.put("gamename", gameName);
            //初始化view
            view=View.inflate(mContext, R.layout.fm_tbww,null);
            tabLayout = view. findViewById(R.id.tayLayout);
            fm_dw_vp =  view.findViewById(R.id.fm_dw_vp);
            initStatus();
            initVp();
            if (where.equals("1")){
                no= (String)getArguments().get("no");
                prarams.put("no", no);

                handler.postDelayed(thread,5000);

                new MybetpointsAsyncTask().execute();
            }
        }
        return view;
    }

    protected View initVp() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(fm_dw_vp, false);
        for (int i = 0; i < tabs.length; i++) {
            fmtw1 = new TWFragment();
            Bundle bundle = new Bundle();
            if (where.equals("1")){
                no= (String)getArguments().get("no");
                bundle.putString("no", no);
                prarams.put("no", no);
            }
            bundle.putString("where", where);
            bundle.putString("gameType", gameType);
            bundle.putString("gameName", gameName);
            bundle.putString("userId", userId);
            bundle.putString("userToken", userToken);
            bundle.putString("poision", String.valueOf(i));
            fmtw1.setArguments(bundle);
            newsList.add(fmtw1);
            tabLayout.addTab(tabLayout.newTab());
        }
        //设置viewpager适配器
        adapter = new OneFmAdapter(getActivity().getSupportFragmentManager(),newsList);
        fm_dw_vp.setAdapter(adapter);
      //  fm_dw_vp.setOffscreenPageLimit(tabs.length);
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.getTabAt(i).setText(tabs[i]);
        }
        return  view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(MessagenextEvent enevnt) {
        no = enevnt.no;
        prarams.put("no", no);
        mybetpoints.clear();
        new MybetpointsAsyncTask().execute();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus2(MessageEvent2 enevnt) {
        //    MoneyPoints2=GetNumber.getMoneyPoints(gameType);
        mulp = enevnt.mulp;
        if(mulp.equals("反选")){
            Toast.makeText(mContext,"暂不支持反选",Toast.LENGTH_SHORT).show();
        }else if(mulp.equals("清除")){
            if( RuiXinApplication.getInstance().getMoneyList()!=null){
                RuiXinApplication.getInstance().getMoneyList().clear();
            }
            EventBus.getDefault().post(new MessageEvent("notify"));
            Message msg = new Message();
            msg.what =1;
            handler.sendMessage(msg);


        }else if(mulp.equals("梭哈")){
            MoneyPoints.clear();
            //  MoneyPoints.addAll( MoneyPoints2);
            int mycoin= BetModeActivity.mycoin;
            int sum=0;
            if(  RuiXinApplication.getInstance().getMoneyList()!=null){
                for(int j = 0; j<RuiXinApplication.getInstance().getMoneyList().size(); j++){
                    String d=  RuiXinApplication.getInstance().getMoneyList().get(j).getMoney();
                    sum +=Integer.parseInt(d);
                }
            }
            if(sum!=0){
                mp1= (double)(mycoin/sum);
                if( RuiXinApplication.getInstance().getMoneyList()!=null){
                    for(int j = 0; j< RuiXinApplication.getInstance().getMoneyList().size(); j++){
                        if(j< RuiXinApplication.getInstance().getMoneyList().size()){
                            int money = Integer.parseInt(RuiXinApplication.getInstance().getMoneyList().get(j).getMoney());
                            RuiXinApplication.getInstance().getMoneyList().get(j).setMoney(String.valueOf((int) Math.floor(money * mp1)));
                            Log.e("tag", "MessageEnventBus"+mp1);
                        }

                    }
                    EventBus.getDefault().post(new MessageEvent("notify"));
                    Message msg = new Message();
                    msg.what =1;
                    handler.sendMessage(msg);

                }
            }


        }else if(mulp.equals("上期投注")){
            if( RuiXinApplication.getInstance().getMoneyList()!=null){
                RuiXinApplication.getInstance().getMoneyList().clear();
            }
            new LastBetAsyncTask().execute();
        }else{
            mp1=  Double.parseDouble(mulp);
            if( RuiXinApplication.getInstance().getMoneyList()!=null){
                for(int j = 0; j< RuiXinApplication.getInstance().getMoneyList().size(); j++){
                    if(j<RuiXinApplication.getInstance().getMoneyList().size()){
                        int money = Integer.parseInt(RuiXinApplication.getInstance().getMoneyList().get(j).getMoney());
                        RuiXinApplication.getInstance().getMoneyList().get(j).setMoney(String.valueOf((int) Math.floor(money * mp1)));
                        Log.e("tag", "MessageEnventBus"+mp1);
                    }

                }
                EventBus.getDefault().post(new MessageEvent("notify"));
                Message msg = new Message();
                msg.what =1;
                handler.sendMessage(msg);
            }
        }


    }
    //接收我的模式的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus3(MessageModeEvent enevnt) {
        if( RuiXinApplication.getInstance().getMoneyList()!=null){
            RuiXinApplication.getInstance().getMoneyList().clear();
        }
        // MoneyPoints.clear();
        if(enevnt.modelist!=null) {
            for (int i = 0; i < enevnt.modelist.getMoneyPoints().size(); i++) {
                if (enevnt.modelist.getMoneyPoints().get(i).equals("")) {
                    moneyList2.add(new MoneyDb(i, "0"));
                } else {
                    moneyList2.add(new MoneyDb(i, enevnt.modelist.getMoneyPoints().get(i)));
                }

                RuiXinApplication.getInstance().setMoneyList(moneyList2);
            }
            EventBus.getDefault().post(new MessageEvent("notify"));
            Message msg = new Message();
            msg.what =1;
            handler.sendMessage(msg);
        }

    }
    private class LastBetAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().GameLastBet_URL,prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取上期投注", "消息返回结果result" + result);
            if (result != null) {
                int status = 0;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                    Gson gson=new Gson();
                    LastBetDb lastBetDb=gson.fromJson(result,LastBetDb.class);
                    List<String> moneyPoints=new ArrayList<>();
                    if(lastBetDb.getMoneyPoints()!=null||lastBetDb.getMoneyPoints().size()>0){
                        for(int i=0;i<lastBetDb.getMoneyPoints().size();i++){
                            if(lastBetDb.getMoneyPoints().get(i).equals("")){
                                moneyList2.add(new MoneyDb(i,"0"));
                            }else{
                                moneyList2.add(new MoneyDb(i,lastBetDb.getMoneyPoints().get(i)));
                            }

                            RuiXinApplication.getInstance().setMoneyList(moneyList2);
                        }

                        EventBus.getDefault().post(new MessageEvent("notify"));
                        Message msg = new Message();
                        msg.what =1;
                        handler.sendMessage(msg);
                    }
                } else if (status == -97 || status == -99) {
                    Toast.makeText(mContext, "账号已经过期请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("where", "2");
                    startActivity(intent);
                }else if (status == 99) {
                        /*抗攻击*/
                    Gson gson = new Gson();
                    ATK atK = gson.fromJson(result, ATK.class);
                    String vaild_str = atK.getVaild_str();
                    Log.e("tag", "" + vaild_str);
                    String vaildd_md5 = FormatUtils.md5(vaild_str);
                    Log.e("tag", "" + vaildd_md5);
                    prarams.put("vaild_str", vaildd_md5);
                    new LastBetAsyncTask().execute();
                }
            } else {
                Toast.makeText(mContext, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void traversal4() {
        if(!exit){
            Fragment view =adapter.currentFragment;
            ListView game_number_lv=view.getView().findViewById(R.id.game_number_lv);
            position=   fm_dw_vp.getCurrentItem();
            for (int i = 0; i < game_number_lv.getCount(); i++) {
                View view1 = game_number_lv.getChildAt(i);
                TextView tv_peilv=view1.findViewById(R.id.tv_peilv);
                HashMap map=new HashMap();
                array= (int[]) GetNumber.getArray3(gameType,position,map).get("array");
                if(i<array.length){
                    tv_peilv.setText(""+odds.get(array[i]));
                }
            }
        }
    }
    private class PeilvAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().GameTrueLvBet_URL,prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取真实peilv", "消息返回结果result" + result);
            if (result != null) {
                int status = 0;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                    Gson gson=new Gson();
                    PeilvDb peilvDb=gson.fromJson(result,PeilvDb.class);
                    odds = peilvDb.getData().getOdds();
                    traversal4();
                } else if (status == -97 || status == -99) {
                    Toast.makeText(mContext, "账号已经过期请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("where", "2");
                    startActivity(intent);
                }else if (status == 99) {
                        /*抗攻击*/
                    Gson gson = new Gson();
                    ATK atK = gson.fromJson(result, ATK.class);
                    String vaild_str = atK.getVaild_str();
                    Log.e("tag", "" + vaild_str);
                    String vaildd_md5 = FormatUtils.md5(vaild_str);
                    Log.e("tag", "" + vaildd_md5);
                    prarams.put("vaild_str", vaildd_md5);
                    new PeilvAsyncTask().execute();
                }
            } else {
                Toast.makeText(mContext, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class MybetpointsAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().GameTrueMyBet_URL,prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取已投金币数据", "消息返回结果result" + result);
            if (result != null) {
                int status = 0;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                    Gson gson=new Gson();
                    MyBetpointsDb myBetpointsDb=gson.fromJson(result,MyBetpointsDb.class);
                    RuiXinApplication.getInstance().setMybetPoints(myBetpointsDb.getData());
                    EventBus.getDefault().post(new MessagemybetEvent(myBetpointsDb.getData()));

                } else if (status == -97 || status == -99) {
                    Toast.makeText(mContext, "账号已经过期请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("where", "2");
                    startActivity(intent);
                }else if (status == 99) {
                        /*抗攻击*/
                    Gson gson = new Gson();
                    ATK atK = gson.fromJson(result, ATK.class);
                    String vaild_str = atK.getVaild_str();
                    Log.e("tag", "" + vaild_str);
                    String vaildd_md5 = FormatUtils.md5(vaild_str);
                    Log.e("tag", "" + vaildd_md5);
                    prarams.put("vaild_str", vaildd_md5);
                    new MybetpointsAsyncTask().execute();
                }
            } else {
                Toast.makeText(mContext, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        exit=true;
        handler.removeCallbacks(thread);
        EventBus.getDefault().unregister(this);
    }
}
