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
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessageMoneyEvent;
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

public class SsCFragment extends BaseFragment {
    private View view;
    ListView game_number_lv;
    Map map=new HashMap();
    int []array;
    double[] rate;
    int[]money;
    String gameType;
    int position;
    SSCGameNumberAdapter numberAdapter;
    String mulp="1";
    Double mp1;
    String gameName;
    String userId;
    String userToken;
    String result;
    TextView tv_peiv;
    TextView tv_hasbet;
    String where;
    String no;
    int time=1;
    public volatile boolean exit = false;
    List<Double> odds=new ArrayList<>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    MybetModeDB.DataBean.ModeljsonBean modelist=new MybetModeDB.DataBean.ModeljsonBean();
    List<Integer> mybetpoints=new ArrayList<Integer>();
    List<String> MoneyPoints2=new ArrayList<>();
    List<String> MoneyPoints=new ArrayList<>();
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==1) {
                Message msg1 = new Message();
                msg1.what = 1;
                BetModeActivity.fragmentssc.handler.sendMessage(msg1);
            }
        }
    };
    @Override
    protected View initView() {
        if(view==null){
            EventBus.getDefault().register(this);
            where = (String)getArguments().get("where");
            gameType = (String)getArguments().get("gameType");
            gameType = (String)getArguments().get("gameType");
            gameName = (String)getArguments().get("gameName");
            userId = (String)getArguments().get("userId");
            userToken = (String)getArguments().get("userToken");
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            prarams.put("gamename", gameName);
            position= Integer.parseInt( (String)getArguments().get("poision")) ;
            Log.e("tag55555555555", "initView"+position);
            view=View.inflate(mContext, R.layout.fm_dw1,null);
            game_number_lv = view.findViewById(R.id.game_number_lv);
            tv_peiv = view.findViewById(R.id.tv_peiv);
            tv_hasbet = view.findViewById(R.id.tv_hasbet);
            map= GetNumber.getArray3(gameType,position,(HashMap) map);
            array= (int[]) map.get("array");
            money= (int[]) map.get("money");
            if(where.equals("1")){
                no= (String)getArguments().get("no");
                prarams.put("no", no);
                tv_peiv.setText("赔率");
            }else{
                tv_hasbet.setVisibility(View.GONE);
                tv_peiv.setText("标赔");

            }
            rate= (double[]) map.get("rate");
            mybetpoints=RuiXinApplication.getInstance().getMybetPoints();
                if(numberAdapter==null){
                    numberAdapter = new SSCGameNumberAdapter(mContext,where,gameType,array,rate,money,handler,MoneyPoints,mybetpoints);
                }

                game_number_lv.setAdapter(numberAdapter);
                game_number_lv.setTag(position);
                SetHight.setListViewHeightBasedOnChildren(game_number_lv);



        }
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(MessageEvent enevnt) {
        if(enevnt.result.equals("notify")){
            Log.e("tag", "MessageEnventBus"+enevnt.result);
            if(numberAdapter==null){
                numberAdapter = new SSCGameNumberAdapter(mContext,where,gameType,array,rate,money,handler,MoneyPoints,mybetpoints);
                game_number_lv.setAdapter(numberAdapter);
            }else{
                numberAdapter.notifyDataSetChanged();
            }


            //   numberAdapter.notifyDataSetChanged();
        }
    }
    //接收我的投注金币的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus4(MessagemybetEvent enevnt) {
        Log.e("tag", "MessagemybetEvent-----");
      //进入下一期的消息
        if( RuiXinApplication.getInstance().getMoneyList()!=null){
            RuiXinApplication.getInstance().getMoneyList().clear();
        }
        if(mybetpoints!=null){
            mybetpoints.clear();
            mybetpoints.addAll(enevnt.mybetpoints);
            numberAdapter.notifyDataSetChanged();
        }else{
            mybetpoints=enevnt.mybetpoints;
            numberAdapter = new SSCGameNumberAdapter(mContext,where,gameType,array,rate,money,handler,MoneyPoints,mybetpoints);
            game_number_lv.setAdapter(numberAdapter);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("tag5555555555", "onDestroyView"+position);
        exit=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("tag5555555555", "onDestroy"+position);
        EventBus.getDefault().unregister(this);
    }
}
