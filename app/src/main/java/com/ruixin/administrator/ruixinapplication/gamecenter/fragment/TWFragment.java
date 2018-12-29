package com.ruixin.administrator.ruixinapplication.gamecenter.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.DWGameNumberAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.SSCGameNumberAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.TWGameNumberAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.LastBetDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessageEvent2;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessageModeEvent;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessagemybetEvent;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessagenextEvent;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MyBetpointsDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MybetModeDB;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.PeilvDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.domain.GetNumber;
import com.ruixin.administrator.ruixinapplication.popwindow.MyBetModePop;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
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
 */

public class TWFragment extends BaseFragment {
    private View view;
    ListView game_number_lv;
    int []array;
    double[] rate;
    int[]money;
    TWGameNumberAdapter numberAdapter;
    String mulp="1";
    Double mp1;
    String totalPints1;
    private List <Integer>mposition=new ArrayList<>();
    private List <String>moneyList=new ArrayList<>();
    StringBuilder sb;
    StringBuilder nb;
    String tbChk;
    String tbNum;
    String gameType;
    String gameName;
    String userId;
    String userToken;
    String result;
    int position;
    TextView tv_peiv;
    String where;
    String no;
    int time=1;
    public volatile boolean exit = false;
    List<Double> odds=new ArrayList<>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    Map map=new HashMap();
    MybetModeDB.DataBean.ModeljsonBean modelist=new MybetModeDB.DataBean.ModeljsonBean();
    List<String> MoneyPoints=new ArrayList<>();
    List<String> MoneyPoints2=new ArrayList<>();
    TextView tv_hasbet;
    List<Integer> mybetpoints=new ArrayList<Integer>();
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==1){
                Message msg1 = new Message();
                msg1.what =1;
                BetModeActivity.fragmenttbww.handler.sendMessage(msg1);
            }
        }
    };
    @Override
    protected View initView() {
        if(view==null){
            EventBus.getDefault().register(this);
            where = (String)getArguments().get("where");
            gameType = (String)getArguments().get("gameType");
            gameName = (String)getArguments().get("gameName");
            userId = (String)getArguments().get("userId");
            userToken = (String)getArguments().get("userToken");
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            prarams.put("gamename", gameName);
            position= Integer.parseInt( (String)getArguments().get("poision")) ;
            view=View.inflate(mContext, R.layout.fm_dw1,null);
            game_number_lv = view.findViewById(R.id.game_number_lv);
            tv_peiv = view.findViewById(R.id.tv_peiv);
            tv_hasbet = view.findViewById(R.id.tv_hasbet);
            map= GetNumber.getArray3(gameType,position,(HashMap) map);
            MoneyPoints2=GetNumber.getMoneyPoints(gameType);
            array= (int[]) map.get("array");
            money= (int[]) map.get("money");
            if(where.equals("1")){
                no= (String)getArguments().get("no");
                prarams.put("no", no);
                tv_peiv.setText("赔率");
            }else{
                tv_peiv.setText("标赔");
                tv_hasbet.setVisibility(View.GONE);
            }
            rate= (double[]) map.get("rate");
            mybetpoints=RuiXinApplication.getInstance().getMybetPoints();
                if(numberAdapter==null){
                    numberAdapter = new TWGameNumberAdapter(mContext,where,array,rate,money,handler,position,MoneyPoints,mybetpoints);
                }
                game_number_lv.setAdapter(numberAdapter);
                SetHight.setListViewHeightBasedOnChildren(game_number_lv);
        }
        return view;
    }
    //接收我的投注金币的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus4(MessagemybetEvent enevnt) {

        if( RuiXinApplication.getInstance().getMoneyList()!=null){
            RuiXinApplication.getInstance().getMoneyList().clear();
        }
        if(mybetpoints!=null){
            mybetpoints.clear();
            mybetpoints.addAll(enevnt.mybetpoints);
            numberAdapter.notifyDataSetChanged();
        }else{
            mybetpoints=enevnt.mybetpoints;
            numberAdapter = new TWGameNumberAdapter(mContext,where,array,rate,money,handler,position,MoneyPoints,mybetpoints);
            game_number_lv.setAdapter(numberAdapter);
        }



    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(MessageEvent enevnt) {

        if(enevnt.result.equals("notify")){
            Log.e("tag", "MessageEnventBus"+enevnt.result);
            if(numberAdapter==null){
                numberAdapter = new TWGameNumberAdapter(mContext,where,array,rate,money,handler,position,MoneyPoints,mybetpoints);
                game_number_lv.setAdapter(numberAdapter);
            }else{
                numberAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        exit=true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("tag5555555555", "onDestroy"+position);
        EventBus.getDefault().unregister(this);
    }
}
