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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.exchangemall.domain.CoRecordDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.ConversionRecordAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
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
 * 兑换记录activity
 */
public class ConversionRecordActivity extends Activity {
    String userId;
    String userToken;
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.conversion_record_lv)
    PullToRefreshListView mPullRefreshlistView;
    ListView conversion_record_lv;
    private String the_first_id;
    private String the_last_id;
    boolean Tag = true;
    boolean first = true;
    int time = 1;//判断是第一次进来数据为空还是上拉加载数据为空
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> reprarams = new HashMap<String, String>();
    List<CoRecordDb.DataBean> list = new ArrayList<>();
    ConversionRecordAdapter adapter;
    int position;
    boolean change = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_record);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userToken = intent.getStringExtra("userToken");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        reprarams.put("usersid", userId);
        reprarams.put("usertoken", userToken);
        initStatus();
        initView();
    }

    private void initView() {
        tvTitle.setText("兑换记录");
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        conversion_record_lv = mPullRefreshlistView.getRefreshableView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(conversion_record_lv, null);
        new MyRecordAsyncTask().execute();
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
                //  prarams.put("firstid", "" + the_first_id);
                new MyRecordAsyncTask().execute();
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
                new MyRecordAsyncTask().execute();
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
    private class MyRecordAsyncTask extends AsyncTask<String, Integer, String> {
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
            String result = AgentApi.dopost3(URL.getInstance().ConversionRecord_URL, prarams);
            Log.e("我的兑换记录的数据返回", "" + result);
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
                    CoRecordDb coRecordDb = gson.fromJson(s, CoRecordDb.class);
                    if (coRecordDb.getStatus() == 1) {
                        if (coRecordDb.getData() != null && coRecordDb.getData().size() > 0) {
                            if (Tag) {
                                list.addAll(coRecordDb.getData());
                            } else {
                              list=coRecordDb.getData();
                              /*  for (int i = 0; i <coRecordDb.getData().size(); i++) {
                                    list.add(i,coRecordDb.getData().get(i));
                                }*/
                            }
                            the_first_id = list.get(0).getOrderid();
                            the_last_id = list.get((list.size() - 1)).getOrderid();
                            adapter = new ConversionRecordAdapter(ConversionRecordActivity.this, list);
                            conversion_record_lv.setAdapter(adapter);
                            mPullRefreshlistView.onRefreshComplete();
                            adapter.setOnStartOrEndClickListener(new ConversionRecordAdapter.OnStartOrEndClickListener() {
                                @Override
                                public void OnStartOrEndClick(View view, int i) {
                                    if (list.get(i).getState().equals("1")) {
                                        //已发货对应重发操作
                                        reprarams.put("id", list.get(i).getOrderid());
                                        new ReStartAsyncTask().execute();
                                    } else if (list.get(i).getState().equals("2")) {
                                        Toast.makeText(ConversionRecordActivity.this, "正在等待发货！", Toast.LENGTH_SHORT).show();
                                    } else if (list.get(i).getState().equals("3")) {
                                        Toast.makeText(ConversionRecordActivity.this, "已经拒绝发货！", Toast.LENGTH_SHORT).show();
                                    } else {
                                        reprarams.put("id", list.get(i).getOrderid());
                                        position = i;
                                        new CancelAsyncTask().execute();
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
                                    Toast.makeText(ConversionRecordActivity.this, "已经加载到底部了！", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                mLoadingAndRetryManager.showContent();
                                Toast.makeText(ConversionRecordActivity.this, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (coRecordDb.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams, s, new MyRecordAsyncTask());
                    } else if (coRecordDb.getStatus() == -97 || coRecordDb.getStatus() == -99) {
                        Unlogin.doLogin(ConversionRecordActivity.this);
                    } else {
                        finish();
                        Toast.makeText(ConversionRecordActivity.this, coRecordDb.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    mLoadingAndRetryManager.showContent();
                    Toast.makeText(ConversionRecordActivity.this, "服务器出错啦,请反馈给管理员哦！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ConversionRecordActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class CancelAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().CaConversionRecord_URL, reprarams);
            Log.e("我的取消的数据返回", "" + result);
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
                    }

                    if (re.equals("1")) {
                        Toast.makeText(ConversionRecordActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        conversion_record_lv.invalidate();
                        if (list.size() == 0) {
                            mLoadingAndRetryManager.showEmpty();
                        }
                    } else if (re.equals("99")) {
                        /*抗攻击*/
                        Unlogin.doAtk(reprarams, s, new CancelAsyncTask());
                    } else if (re.equals("-99") || re.equals("-97")) {
                        Unlogin.doLogin(ConversionRecordActivity.this);
                    } else {
                        msg = jsonObject.optString("msg");
                        Toast.makeText(ConversionRecordActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(ConversionRecordActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ConversionRecordActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class ReStartAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().ReConversionRecord_URL, reprarams);
            Log.e("我的重发的数据返回", "" + result);
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
                    }

                    if (re.equals("1")) {
                        Toast.makeText(ConversionRecordActivity.this, "重发成功，请在绑定邮箱查收", Toast.LENGTH_SHORT).show();
                        Tag = true;
                        list.clear();
                        prarams.clear();
                        prarams.put("usersid", userId);
                        prarams.put("usertoken", userToken);
                        new MyRecordAsyncTask().execute();
                    } else if (re.equals("99")) {
                        /*抗攻击*/
                        Unlogin.doAtk(reprarams, s, new ReStartAsyncTask());
                    } else if (re.equals("-99") || re.equals("-97")) {
                        Unlogin.doLogin(ConversionRecordActivity.this);
                    } else {
                        msg = jsonObject.optString("msg");
                        Toast.makeText(ConversionRecordActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(ConversionRecordActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ConversionRecordActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
