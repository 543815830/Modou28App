package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.AutoBetActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.CheckNumberBetActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.DoublingBetActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.TrusteeAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.TrusteeDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
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
 * 托管界面
 */
public class TrusteeActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private PullToRefreshListView mPullRefreshlistView;
    ListView lv_trustee;
    TrusteeAdapter adapter;
    String userId;
    String userToken;
    String gameType;
    private String the_first_id;
    private String the_last_id;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> delprarams = new HashMap<String, String>();
    private HashMap<String, String> Sprarams = new HashMap<String, String>();
    private HashMap<String, String> Eprarams = new HashMap<String, String>();
    boolean Tag = true;
    boolean first = true;
    int time = 1;//判断是第一次进来数据为空还是上拉加载数据为空
    LoadingAndRetryManager mLoadingAndRetryManager;
    List<TrusteeDb.DataBean.AutolistBean> list=new ArrayList<>();
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trustee);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userToken = intent.getStringExtra("userToken");
        gameType = intent.getStringExtra("gameType");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        delprarams.put("usersid", userId);
        delprarams.put("usertoken", userToken);
        Sprarams.put("usersid", userId);
        Sprarams.put("usertoken", userToken);
        Eprarams.put("usersid", userId);
        Eprarams.put("usertoken", userToken);
        initStatus();
        initView();
    }

    private void initView() {
        tvTitle.setText("托管方案");
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mPullRefreshlistView = findViewById(R.id.game_trustee_lv);
        lv_trustee = mPullRefreshlistView.getRefreshableView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(lv_trustee, null);
        new MyTrusteeAsyncTask().execute();
        mPullRefreshlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //得到当前刷新的时间
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                //设置更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                Tag = false;
                prarams.clear();
                prarams.put("usersid", userId);
                prarams.put("usertoken", userToken);
                prarams.put("firstid", "" + the_first_id);
                new MyTrusteeAsyncTask().execute();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshlistView.onRefreshComplete();
                    }
                }, 500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Tag = true;
                ++time;
                prarams.clear();
                prarams.put("usersid", userId);
                prarams.put("usertoken", userToken);
                prarams.put("lastid", "" + the_last_id);
                new MyTrusteeAsyncTask().execute();
                //解决刷新失效问题
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshlistView.onRefreshComplete();
                    }
                }, 500);

            }
        });
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class MyTrusteeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (first) {
                mLoadingAndRetryManager.showLoading();
                first = false;
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().TrusteeInfo_URL, prarams);
            Log.e("我的托管方案的数据返回", "" + result);
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
                   TrusteeDb trusteeDb = gson.fromJson(s, TrusteeDb.class);
                    if (trusteeDb.getStatus() == 1) {
                        if (trusteeDb.getData().getAutolist() != null && trusteeDb.getData().getAutolist().size() > 0) {
                            if (Tag) {
                                list.addAll(trusteeDb.getData().getAutolist());
                                Log.e("result", "list=" + list);
                            } else {
                                Log.e("result", "list=" + list.size());
                                for (int i = 0; i <trusteeDb.getData().getAutolist().size(); i++) {
                                    list.add(i,trusteeDb.getData().getAutolist().get(i));
                                }
                            }
                            the_first_id = list.get(0).getId();
                            the_last_id = list.get((list.size() - 1)).getId();

                            adapter = new TrusteeAdapter(TrusteeActivity.this, list);
                            Log.e("tag", "" + list);
                           lv_trustee.setAdapter(adapter);
                            mPullRefreshlistView.onRefreshComplete();
                           adapter.setOnItemClickListener(new TrusteeAdapter.OnItemClickListener() {

                               @Override
                               public void OnItemClick(View view, int i) {
                                   if(list.get(i).getType().equals("1")){
                                       Intent intent=new Intent(TrusteeActivity.this,TrusteeDetActivity.class);
                                       intent.putExtra("userId",userId);
                                       intent.putExtra("userToken",userToken);
                                       intent.putExtra("gameType",list.get(i).getGametype());
                                       intent.putExtra("id",list.get(i).getId());
                                       startActivity(intent);
                                   }else if(list.get(i).getType().equals("2")){
                                       Intent intent=new Intent(TrusteeActivity.this,TrusteeDetActivity2.class);
                                       intent.putExtra("userId",userId);
                                       intent.putExtra("userToken",userToken);
                                       intent.putExtra("gameType",list.get(i).getGametype());
                                       intent.putExtra("id",list.get(i).getId());
                                       startActivity(intent);
                                   }else if(list.get(i).getType().equals("3")){
                                       Intent intent=new Intent(TrusteeActivity.this,TrusteeDetActivity3.class);
                                       intent.putExtra("userId",userId);
                                       intent.putExtra("userToken",userToken);
                                       intent.putExtra("gameType",list.get(i).getGametype());
                                       intent.putExtra("id",list.get(i).getId());
                                       startActivity(intent);
                                   }


                               }
                           });
                           adapter.setOnivClickListener(new TrusteeAdapter.OnClickListener() {
                               @Override
                               public void OnivClick(View view, int i) {
                                   if(list.get(i).getType().equals("1")){
                                       Intent intent=new Intent(TrusteeActivity.this,AutoBetActivity.class);
                                       intent.putExtra("userId",userId);
                                       intent.putExtra("userToken",userToken);
                                       intent.putExtra("id",list.get(i).getId());
                                       intent.putExtra("type","update");
                                       intent.putExtra("EgameName",list.get(i).getGame());
                                       intent.putExtra("gameName",list.get(i).getChgamename());
                                       intent.putExtra("gameType",list.get(i).getGametype());
                                       startActivity(intent);
                                       finish();
                                   } else if(list.get(i).getType().equals("2")){
                                       Intent intent=new Intent(TrusteeActivity.this, CheckNumberBetActivity.class);
                                       intent.putExtra("userId",userId);
                                       intent.putExtra("userToken",userToken);
                                       intent.putExtra("id",list.get(i).getId());
                                       intent.putExtra("type","update");
                                       intent.putExtra("EgameName",list.get(i).getGame());
                                       intent.putExtra("gameName",list.get(i).getChgamename());
                                       intent.putExtra("gameType",list.get(i).getGametype());
                                       startActivity(intent);
                                       finish();
                                   }else if(list.get(i).getType().equals("3")){
                                       Intent intent=new Intent(TrusteeActivity.this,DoublingBetActivity.class);
                                       intent.putExtra("userId",userId);
                                       intent.putExtra("userToken",userToken);
                                       intent.putExtra("id",list.get(i).getId());
                                       intent.putExtra("type","update");
                                       intent.putExtra("EgameName",list.get(i).getGame());
                                       intent.putExtra("gameName",list.get(i).getChgamename());
                                       intent.putExtra("gameType",list.get(i).getGametype());
                                       startActivity(intent);
                                      finish();
                                   }

                               }
                           });
                            adapter.setOnDelClickListener(new TrusteeAdapter.OnDelClickListener() {
                                                              @Override
                                                              public void OnDelClick(View view, int i) {
                                                                  delprarams.put("id",list.get(i).getId());
                                                                  position=i;
                                                                  new DeleteTrusteeAsyncTask().execute();

                                                              }
                                                          });
                            adapter.setOnStartOrEndClickListener(new TrusteeAdapter.OnStartOrEndClickListener() {
                                @Override
                                public void OnStartOrEndClick(View view, int i) {
                                    position=i;
                                    if(list.get(i).getState().equals("1")){
                                        Eprarams.put("id",list.get(i).getId());
                                        new EndTrusteeAsyncTask().execute();
                                    }else{
                                        Sprarams.put("id",list.get(i).getId());
                                        new StartTrusteeAsyncTask().execute();
                                    }
                                }
                            });
                                    mLoadingAndRetryManager.showContent();
                        } else {
                            if (Tag) {
                                if (time == 1) {
                                    mLoadingAndRetryManager.showEmpty();
                                } else {
                                    mLoadingAndRetryManager.showContent();
                                   Toast.makeText(TrusteeActivity.this, "已经加载到底部了！", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                mLoadingAndRetryManager.showContent();
                               Toast.makeText(TrusteeActivity.this, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else if (trusteeDb.getStatus() ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,   new MyTrusteeAsyncTask());
                      //  new MyTrusteeAsyncTask().execute();
                    } else if(trusteeDb.getStatus()==-97||trusteeDb.getStatus()==-99){
                        Unlogin.doLogin(TrusteeActivity.this);
                    }else {
                        finish();
                       Toast.makeText(TrusteeActivity.this, trusteeDb.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(TrusteeActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(TrusteeActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteTrusteeAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().TrusteeDel_URL, delprarams);
            Log.e("我的删除的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                String re = null;
                String msg = null;

                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //第一层解析
                    if (jsonObject != null) {
                        re = jsonObject.optString("status");
                        msg = jsonObject.optString("msg");
                    }

                    if (re.equals("1")) {
                       Toast.makeText(TrusteeActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        lv_trustee.invalidate();

                    }else if (re.equals("99")) {
                        /*抗攻击*/
                        Unlogin.doAtk(delprarams,s, new DeleteTrusteeAsyncTask());
                    //    new DeleteTrusteeAsyncTask().execute();
                    }else if(re.equals("-99")||re.equals("-97")){
                        Unlogin.doLogin(TrusteeActivity.this);
                    }else{
                        Toast.makeText(TrusteeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(TrusteeActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(TrusteeActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }

        }
    }
    @SuppressLint("StaticFieldLeak")
    private class EndTrusteeAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().TrusteeStop_URL, Eprarams);
            Log.e("我的停止的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                String re = null;
                String msg = null;
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //第一层解析
                    if (jsonObject != null) {

                        re = jsonObject.optString("status");
                        msg= jsonObject.optString("msg");
                    }

                    if (re.equals("1")) {
                       list.get(position).setState("0");
                       adapter.notifyDataSetChanged();
                    }else if (re.equals("99")) {
                        /*抗攻击*/
                        Unlogin.doAtk(Eprarams,s, new EndTrusteeAsyncTask());
                      //  new EndTrusteeAsyncTask().execute();
                    }else if(re.equals("-99")||re.equals("-97")){
                        Unlogin.doLogin(TrusteeActivity.this);
                      /* Toast.makeText(TrusteeActivity.this, "账号已经过期请重新登录", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(TrusteeActivity.this,LoginActivity.class);
                        intent.putExtra("where","2");
                        startActivity(intent);*/
                    }else{
                        Toast.makeText(TrusteeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(TrusteeActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(TrusteeActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }

        }
    }
    @SuppressLint("StaticFieldLeak")
    private class StartTrusteeAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().TrusteeStart_URL, Sprarams);
            Log.e("我的运行的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                String re = null;
                String msg = null;
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //第一层解析
                    if (jsonObject != null) {
                        re = jsonObject.optString("status");
                        msg = jsonObject.optString("msg");
                    }

                    if (re.equals("1")) {
                        list.get(position).setState("1");
                        adapter.notifyDataSetChanged();
                    }else if (re.equals("99")) {
                        /*抗攻击*/
                        Unlogin.doAtk(Eprarams,s, new EndTrusteeAsyncTask());
                      //  new EndTrusteeAsyncTask().execute();
                    }else if(re.equals("-99")||re.equals("-97")){
                        Unlogin.doLogin(TrusteeActivity.this);
                    }else{
                        Toast.makeText(TrusteeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(TrusteeActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(TrusteeActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
