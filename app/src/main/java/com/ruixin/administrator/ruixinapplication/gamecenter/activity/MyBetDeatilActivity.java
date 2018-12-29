package com.ruixin.administrator.ruixinapplication.gamecenter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.gamecenter.GameCenterFragment;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GameName1;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MybetDetailDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.GetGameResult;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 投注详情界面
 */
public class MyBetDeatilActivity extends Activity implements View.OnClickListener {
    LinearLayout ll_game_detial;
    LinearLayout close_arrow;
    LinearLayout ll_game_result3;
    ImageView iv_1;
    ImageView iv_2;
    ImageView iv_3;
private TextView tv_title;
private TextView tv_no;
private TextView tv_end_time;
private TextView tv_game_result;
private TextView tv_content;
private TextView tv_bet_points;
private TextView tv_get_points;
private TextView tv_kui_lv;
private TextView tv_yingkui_bili;
    private LinearLayout back_arrow;
    Button save;
    String EgameName;
    String userId;
    String userToken;
    String gameId;
    String gameType;
    String tbModelName;
    String where;
    private HashMap<String, String> dprarams = new HashMap<String, String>();
    private HashMap<String, String> sdprarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bet_deatil);
        Intent intent=getIntent();
        userId=intent.getStringExtra("usersid");
        Log.e("userId","userId"+userId);
        userToken=intent.getStringExtra("usertoken");
        EgameName= intent.getStringExtra("gamename");
        gameId=intent.getStringExtra("no");
        gameType=intent.getStringExtra("gameType");
        where=intent.getStringExtra("where");
        dprarams.put("usersid",userId);
        dprarams.put("usertoken",userToken);
        dprarams.put("gamename",EgameName);
        dprarams.put("no",gameId);
        sdprarams.put("usersid",userId);
        sdprarams.put("usertoken",userToken);
        sdprarams.put("gamename",EgameName);
        sdprarams.put("no",gameId);

        Log.e("userId","userId"+userToken);
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
        ll_game_detial=findViewById(R.id.ll_game_detial);
        ll_game_result3=findViewById(R.id.ll_game_result3);
        close_arrow=findViewById(R.id.close_arrow);
        iv_1=findViewById(R.id.iv_1);
        iv_2=findViewById(R.id.iv_2);
        iv_3=findViewById(R.id.iv_3);
        close_arrow.setOnClickListener(this);
        save=findViewById(R.id.save);
        if (where.equals("1")){
            save.setVisibility(View.GONE);
        }else {
            save.setVisibility(View.VISIBLE);
            save.setOnClickListener(this);
        }
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(ll_game_detial, null);
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("投注详情");
        tv_no=findViewById(R.id.tv_no);
        tv_end_time=findViewById(R.id.tv_end_time);
        tv_game_result=findViewById(R.id.tv_game_result);
        tv_content=findViewById(R.id.tv_content);
        tv_content.setOnClickListener(this);
        tv_bet_points=findViewById(R.id.tv_bet_points);
        tv_get_points=findViewById(R.id.tv_get_points);
        tv_kui_lv=findViewById(R.id.tv_kui_lv);
        tv_yingkui_bili=findViewById(R.id.tv_yingkui_bili);
        new BetDetailAsyncTask().execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_arrow:
                finish();
                break;
            case R.id.save:
                alert_edit();
                break;
            case R.id.tv_content:
                Intent intent=new Intent(MyBetDeatilActivity.this,BetContentActivity.class);
                intent.putExtra("no",gameId);
                intent.putExtra("userId",userId);
                intent.putExtra("userToken",userToken);
                intent.putExtra("gameType",gameType);
                intent.putExtra("gamename",EgameName);
                 startActivity(intent);
                break;


        }

    }

    public void alert_edit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
// 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);
//这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        final EditText et_bet_mode = view.findViewById(R.id.et_bet_mode);
        et_bet_mode.setText(gameId+"期模式");
        final Button commit = view.findViewById(R.id.commit);
        final Button cancel = view.findViewById(R.id.cancel_btn);
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
                tbModelName = et_bet_mode.getText().toString();
                sdprarams.put("tbModelName", tbModelName);
                //写相关的服务代码
                new SaveBetModeAsyncTask().execute();
                dialog.dismiss();
            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    private class BetDetailAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result= AgentApi.dopost3(URL.getInstance().MyBetDetail_URL,dprarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取游戏列表","消息返回结果result"+result);
            if(result!=null) {
                int status = 0;
                try {
                    JSONObject re=new JSONObject(result);
                    status=re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(status==1) {
                   Gson gson=new Gson();
                    MybetDetailDb mybetDetailDb=gson.fromJson(result,MybetDetailDb.class);
                    tv_no.setText(new StringBuilder().append("期号：").append(mybetDetailDb.getData().getNo()).toString());
                    tv_end_time.setText(new StringBuilder().append("截止时间：").append(mybetDetailDb.getData().getKjtime()).toString());
                    if(gameType.equals("28")||gameType.equals("16")||gameType.equals("11")||gameType.equals("22")||gameType.equals("gy")||gameType.equals("xn")){
                        tv_game_result.setVisibility(View.VISIBLE);
                        StringBuilder res = new StringBuilder();
                        for(int j=0;j<mybetDetailDb.getData().getResult().size()-1;j++){
                            res.append(mybetDetailDb.getData().getResult().get(j));
                            res.append('+');
                        }
                        if(res.length()>0){
                            res.deleteCharAt(res.length()-1);
                        }
                        tv_game_result.setText(new StringBuilder().append("开奖结果：").append(new StringBuilder().append(res).append("=").append(mybetDetailDb.getData().getResult().get(mybetDetailDb.getData().getResult().size() - 1)).toString()).toString());
                    }if(gameType.equals("36")){
                        tv_game_result.setVisibility(View.VISIBLE);
                        StringBuilder res = new StringBuilder();
                        for(int j=0;j<mybetDetailDb.getData().getResult().size()-1;j++){
                            res.append(mybetDetailDb.getData().getResult().get(j));
                            res.append('+');
                        }
                        if(res.length()>0){
                            res.deleteCharAt(res.length()-1);
                        }
                        if(mybetDetailDb.getData().getResult().size()>1){
                            int num1= Integer.parseInt(mybetDetailDb.getData().getResult().get(0));
                            int num2= Integer.parseInt(mybetDetailDb.getData().getResult().get(1));
                            int num3= Integer.parseInt(mybetDetailDb.getData().getResult().get(2));
                            tv_game_result.setText(new StringBuilder().append("开奖结果：").append(new StringBuilder().append(res).append("=").append(GetGameResult.get36rs(num1,num2,num3)).toString()).toString());
                        }

                    }if(gameType.equals("10")||gameType.equals("xs")){
                        tv_game_result.setVisibility(View.VISIBLE);
                        tv_game_result.setText(new StringBuilder().append("开奖结果：").append(mybetDetailDb.getData().getResult().get(0)).toString());
                    }if(gameType.equals("tbww")){
                        Log.e("tag","tbww");
                       ll_game_result3.setVisibility(View.VISIBLE);
                       iv_1.setImageResource(GetGameResult.getTbww(Integer.parseInt(mybetDetailDb.getData().getResult().get(0))));
                        Log.e("tag","tbww"+mybetDetailDb.getData().getResult().get(0));
                       iv_2.setImageResource(GetGameResult.getTbww(Integer.parseInt(mybetDetailDb.getData().getResult().get(1))));
                        Log.e("tag","tbww"+mybetDetailDb.getData().getResult().get(1));
                       iv_3.setImageResource(GetGameResult.getTbww(Integer.parseInt(mybetDetailDb.getData().getResult().get(2))));
                    }if(gameType.equals("ww")){
                        tv_game_result.setVisibility(View.VISIBLE);
                        int num1= Integer.parseInt(mybetDetailDb.getData().getResult().get(0));
                        int num2= Integer.parseInt(mybetDetailDb.getData().getResult().get(1));
                        int num3= Integer.parseInt(mybetDetailDb.getData().getResult().get(2));
                        int num4= Integer.parseInt(mybetDetailDb.getData().getResult().get(3));
                        tv_game_result.setText(new StringBuilder().append("游戏结果：").append(num1).append("+").append(num2).append("+").append(num3).append("=").append(num4).append("  ").append(GetGameResult.getwwrs(num1, num2, num3, num4)).toString());
                    }if(gameType.equals("dw")){
                        tv_game_result.setVisibility(View.VISIBLE);
                        int num1= Integer.parseInt(mybetDetailDb.getData().getResult().get(0));
                        int num2= Integer.parseInt(mybetDetailDb.getData().getResult().get(1));
                        int num3= Integer.parseInt(mybetDetailDb.getData().getResult().get(2));
                        int num4= Integer.parseInt(mybetDetailDb.getData().getResult().get(3));
                        tv_game_result.setText(new StringBuilder().append("游戏结果：").append(num1).append("+").append(num2).append("+").append(num3).append("=").append(num4).append("  ").append(GetGameResult.getdwrs(num4)).toString());
                    }if(gameType.equals("xync")||gameType.equals("pkww")||gameType.equals("ssc")){
                        tv_game_result.setVisibility(View.VISIBLE);
                        StringBuilder res = new StringBuilder();
                        for(int j=0;j<mybetDetailDb.getData().getResult().size();j++){
                            res.append(mybetDetailDb.getData().getResult().get(j));
                            res.append(',');
                        }
                        if(res.length()>0){
                            res.deleteCharAt(res.length()-1);
                        }
                      tv_game_result.setText(new StringBuilder().append("游戏结果：").append(res).toString());
                    }if(gameType.equals("bjl")){
                        tv_game_result.setVisibility(View.VISIBLE);
                        tv_game_result.setText(new StringBuilder().append("开奖结果：").append(GetGameResult.getbjlrs(Integer.parseInt(mybetDetailDb.getData().getResult().get(0)))).toString());
                    }if(gameType.equals("lh")){
                        tv_game_result.setVisibility(View.VISIBLE);
                        tv_game_result.setText(new StringBuilder().append("开奖结果：").append(GetGameResult.getlhrs(Integer.parseInt(mybetDetailDb.getData().getResult().get(0)))).toString());
                    }

                    tv_content.setText(new StringBuilder().append("投注内容：").append(mybetDetailDb.getData().getTznr()).toString());
                    if(gameType.equals("xn")){
                        tv_bet_points.setText(new StringBuilder().append("投注虚拟币：").append(mybetDetailDb.getData().getPoints()).toString());
                        tv_get_points.setText(new StringBuilder().append("获得虚拟币：").append(mybetDetailDb.getData().getHdpoints()).toString());
                    }else{
                        tv_bet_points.setText(new StringBuilder().append("投注金币：").append(mybetDetailDb.getData().getPoints()).toString());
                        tv_get_points.setText(new StringBuilder().append("获得金币：").append(mybetDetailDb.getData().getHdpoints()).toString());
                    }

                    tv_kui_lv.setText(new StringBuilder().append("投注赔率：").append(mybetDetailDb.getData().getZjpl()).toString());
                    tv_yingkui_bili.setText(new StringBuilder().append(new StringBuilder().append("盈亏比例：").append(mybetDetailDb.getData().getYkbl()).toString()).append("%").toString());
                    mLoadingAndRetryManager.showContent();
                }else if(status==-97||status==-99){
                    Unlogin.doLogin(MyBetDeatilActivity.this);
                }else if (status==99) {
                        /*抗攻击*/
                    Unlogin.doAtk(dprarams,result,   new BetDetailAsyncTask());
                }else if(status==0){
                   Toast.makeText(MyBetDeatilActivity.this, "该期不存在或已被删除！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else{
               Toast.makeText(MyBetDeatilActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class SaveBetModeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result= AgentApi.dopost3(URL.getInstance().SaveBetMode_URL,sdprarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("保存模式返回","消息返回结果result"+result);
            if(result!=null) {
                int status = 0;
                try {
                    JSONObject re=new JSONObject(result);
                    status=re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(status==1) {
                   Toast.makeText(MyBetDeatilActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                    mLoadingAndRetryManager.showContent();
                }else if(status==-97||status==-99){
                    Unlogin.doLogin(MyBetDeatilActivity.this);
                }else if (status==99) {
                        /*抗攻击*/
                    Unlogin.doAtk(sdprarams,result,   new SaveBetModeAsyncTask());
                }else if(status==0){
                   Toast.makeText(MyBetDeatilActivity.this, "该期不存在或已被删除！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else{
               Toast.makeText(MyBetDeatilActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
