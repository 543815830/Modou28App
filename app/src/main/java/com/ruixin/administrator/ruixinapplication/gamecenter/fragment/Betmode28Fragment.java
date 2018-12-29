package com.ruixin.administrator.ruixinapplication.gamecenter.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.GameNumberAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.LastBetDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessageEvent2;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessagenextEvent;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MyBetpointsDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MybetModeDB;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.PeilvDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.domain.GetNumber;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李丽 on 2018/6/14.
 * 28等类型的投注模式
 */

public class Betmode28Fragment extends BaseFragment {
    private View view;
    ListView game_number_lv;
   int[]array;
    double[]rate;
    double[]rate2;
    String mode="";
    int[]money;
    int[]money1;
    String gameType;
    String gameName;
    String userId;
    String userToken;
    String result;
    GameNumberAdapter numberAdapter;
    List<Integer> list = new ArrayList<>();
    Map map=new HashMap();
    String where;
    String no;
    TextView tv_hasbet;
    String tbChk;
    String tbNum;
    String mulp="1";
    String totalPints1;
    StringBuilder sb;
    StringBuilder nb;
    Double mp1;
    TextView tv_peiv;
    int time=1;
    private List <Integer>mposition=new ArrayList<>();
    private List <String>moneyList=new ArrayList<>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    List<Double> odds=new ArrayList<>();
    List<Integer> mybetpoints=new ArrayList<Integer>();
    Activity activity = getActivity();
    MybetModeDB.DataBean.ModeljsonBean modelist=new MybetModeDB.DataBean.ModeljsonBean();
    public volatile boolean exit = false;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==1){
                Message msg1 = new Message();
                msg1.what =1;
                BetModeActivity.handler.sendMessage(msg1);
            }else if(msg.what==2){
                traversal2();
            }else if(msg.what==3){
                BetModeActivity.SetPL(new BetModeActivity.SetList() {
                    @Override
                    public void setParams(MybetModeDB.DataBean.ModeljsonBean list1) {
                        modelist=list1;
                        traversal3();
                    }
                });
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
            view=View.inflate(mContext, R.layout.fm_28,null);
            game_number_lv = view.findViewById(R.id.game_number_lv);
            tv_peiv = view.findViewById(R.id.tv_peiv);
            tv_hasbet = view.findViewById(R.id.tv_hasbet);
            map=GetNumber.getArray2(gameType, (HashMap) map);
            Log.e("tag",""+map);
            array= (int[]) map.get("array");
            money= (int[]) map.get("money");
            money1= (int[]) map.get("money");
            if(where.equals("1")){
               no= (String)getArguments().get("no");
               prarams.put("no", no);
               tv_peiv.setText("赔率");
                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        if(!exit){
                            // TODO Auto-generated method stub
                            new PeilvAsyncTask().execute();
                            handler.postDelayed(this, 5000);
                        }
                    }
                });

            }else{
                tv_peiv.setText("标赔");
                tv_hasbet.setVisibility(View.GONE);
            }
            rate= (double[]) map.get("rate");
            numberAdapter = new GameNumberAdapter(mContext, where,array,rate,money,mode,gameType,handler,mybetpoints);
            game_number_lv.setAdapter(numberAdapter);
            SetHight.setListViewHeightBasedOnChildren(game_number_lv);
            if(where.equals("1")){
                new MybetpointsAsyncTask().execute();
            }

        }
        return view;
    }
    //接收进入下期消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(MessagenextEvent enevnt) {
         no = enevnt.no;
        prarams.put("no", no);
        new MybetpointsAsyncTask().execute();


    }
    //接收mode消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(MessageEvent enevnt) {
        mode = enevnt.result;
            mulp="1";
            map=GetNumber.getArray2(gameType, (HashMap) map);
            money= (int[]) map.get("money");
           money1= (int[]) map.get("money");
        traversal5();
       /* numberAdapter = new GameNumberAdapter(mContext, array,rate,money,mode,gameType,handler);*//*

           // game_number_lv.setAdapter(numberAdapter);*/
            Message msg1 = new Message();
            msg1.what =1;
            BetModeActivity.handler.sendMessage(msg1);


    }
    //接收倍数消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus2(MessageEvent2 enevnt) {
        mulp = enevnt.mulp;
        if(mulp.equals("反选")){
            for (int i=0;i<game_number_lv.getCount();i++){
                View view = game_number_lv.getChildAt(i);
                EditText text=view.findViewById(R.id.et_points);
                if (text.getText().toString().trim().equals("")||text.getText().toString().equals("0")){
                    text.setText(""+money[i]);
                }else{
                    text.setText("");
                }
            }

        }else if(mulp.equals("清除")){
            traversal2();

        }else if(mulp.equals("梭哈")){
            int mycoin=BetModeActivity.mycoin;
            if(totalPints1!=null){
                int points= Integer.parseInt(totalPints1);
                if(points!=0){
                    mp1= (double)(mycoin/points);
                    for (int i=0;i<game_number_lv.getCount();i++){
                        View view = game_number_lv.getChildAt(i);
                        EditText text=view.findViewById(R.id.et_points);
                        if(!(text.getText().toString().equals("")||text.getText().toString().equals("0"))){
                            int mo= Integer.parseInt(text.getText().toString());
                            text.setText(new StringBuilder().append((int) Math.floor(mo * mp1)).toString());
                        }
                    }
                }

            }



        }else if(mulp.equals("上期投注")){
            new LastBetAsyncTask().execute();

        }else{
            mp1=  Double.parseDouble(mulp);
            for (int i=0;i<game_number_lv.getCount();i++){
                View view = game_number_lv.getChildAt(i);
                EditText text=view.findViewById(R.id.et_points);
                if(!text.getText().toString().equals("")){
                    int mo= Integer.parseInt(text.getText().toString());
                    text.setText(new StringBuilder().append((int) Math.floor(mo * mp1)).toString());
                }

            }
            Log.e("tag4",""+mulp);
        }


    }
    public void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }
    public interface  SetParams{
        void setParams(String tbChk,String tbNum,String Totalpoints);
    }
    private SetParams setParams;
    public void SetP(final SetParams setParams){
        this.setParams=setParams;
        if(setParams!=null){
            traversal();
            setParams.setParams(tbChk,tbNum,totalPints1);
        }
    }

//遍历listview
    private void traversal() {
        Log.e("tag","traversal");
        mposition.clear();
        moneyList.clear();
        for (int i=0;i<game_number_lv.getCount();i++){
            View view = game_number_lv.getChildAt(i);
            EditText text=view.findViewById(R.id.et_points);
            if(!(text.getText().toString().equals("")||text.getText().toString().equals("0"))){
                mposition.add(i);
                moneyList.add(text.getText().toString());
            }
        }
        sb = new StringBuilder();
        for(int j=0;j<mposition.size();j++){
            sb.append(array[mposition.get(j)]);
            sb.append('=');
            sb.append("on");
            sb.append(",");
            Log.e("sb",""+sb);
        }
        if(sb.length()>0){
            Log.e("sb.length()",""+sb.length());
            sb.deleteCharAt(sb.length()-1);
        }
        nb = new StringBuilder();
        for(int j=0;j<mposition.size();j++){
            nb.append(array[mposition.get(j)]);
            nb.append('=');
            nb.append(moneyList.get(j));
            nb.append(",");
        }
        if(nb.length()>0){
            Log.e("nb.length()",""+nb.length());
            nb.deleteCharAt(nb.length()-1);
        }
        int sum=0;
        for(String d:moneyList)
        {
            sum +=Integer.parseInt(d);
        }
        totalPints1= String.valueOf(sum);
        tbChk=sb.toString();
        tbNum=nb.toString();
        Log.e("tag3333",""+totalPints1);
    }
    private void traversal2() {
        Log.e("tag", "traversal2");
        mposition.clear();
        moneyList.clear();
        for (int i = 0; i < game_number_lv.getCount(); i++) {
            View view = game_number_lv.getChildAt(i);
            LinearLayout ll_game_number=view.findViewById(R.id.ll_game_number);
            EditText text = view.findViewById(R.id.et_points);
            text.setText("");
            ll_game_number.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }
    private void traversal3() {
        Log.e("tag", "traversal3");
        mposition.clear();
        moneyList.clear();
        for (int i = 0; i < game_number_lv.getCount(); i++) {
            View view = game_number_lv.getChildAt(i);
            LinearLayout ll_game_number=view.findViewById(R.id.ll_game_number);
            EditText text = view.findViewById(R.id.et_points);
            text.setText(modelist.getMoneyPoints().get(i));
            if (text.getText().toString().equals("0")||text.getText().toString().equals("")){
                ll_game_number.setBackgroundColor(Color.parseColor("#ffffff"));
            }else{
                ll_game_number.setBackgroundColor(Color.parseColor("#c5c5c5"));
            }
        }
    }
    private void traversal5() {
        for (int i = 0; i < game_number_lv.getCount(); i++) {
            View view = game_number_lv.getChildAt(i);
            LinearLayout ll_game_number=view.findViewById(R.id.ll_game_number);
            EditText text = view.findViewById(R.id.et_points);
          text.setText(GetNumber.getMoney(mode,array,money,i,gameType));
            if (text.getText().toString().equals("0")||text.getText().toString().equals("")){
                ll_game_number.setBackgroundColor(Color.parseColor("#ffffff"));
            }else{
                ll_game_number.setBackgroundColor(Color.parseColor("#c5c5c5"));
            }
        }
    }
    private void traversal4() {
        if(!exit){
        Log.e("tag", "traversal4");
        for (int i = 0; i < game_number_lv.getCount(); i++) {
            View view = game_number_lv.getChildAt(i);
            TextView tv_peilv=view.findViewById(R.id.tv_peilv);
            tv_peilv.setText(""+odds.get(i));
        }
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
                        moneyPoints=lastBetDb.getMoneyPoints();
                        Log.e("tag", "" + moneyPoints);
                        modelist.setMoneyPoints(moneyPoints);
                        traversal3();
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
                   mybetpoints.clear();
                    MyBetpointsDb myBetpointsDb=gson.fromJson(result,MyBetpointsDb.class);
                    mybetpoints.addAll(myBetpointsDb.getData());
                    numberAdapter.notifyDataSetChanged();
                   // traversal6();
                   // numberAdapter .notifyDataSetChanged();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        exit=true;
    }
}
