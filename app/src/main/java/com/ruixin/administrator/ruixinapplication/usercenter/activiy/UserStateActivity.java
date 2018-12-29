package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.UserStateRcyAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.StateDb;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Created by ${李丽} on 2018/3/16.
 * 邮箱：543815830@qq.com
 * 账号动态的界面
 */
public class UserStateActivity extends Activity {
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private RecyclerView ry_user_state;
    private UserStateRcyAdapter adapter;
    List<StateDb.DataBean> list = new ArrayList<>();
    private PullToRefreshRecyclerView mPullRefreshRecyclerView;
    LinearLayoutManager manager;
    private int mtPage;//总页数
    boolean tag = true;
    boolean first = true;
    int page = 1;
    private String the_first_id;
    String userId = "", userToken = "";
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_state);
        ButterKnife.bind(this);
        initStatus();
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        Log.e("tag", "userId" + userId);
        if (userId.equals("")) {
            Toast.makeText(UserStateActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserStateActivity.this, LoginActivity.class);
            intent.putExtra("where", "2");
            startActivity(intent);
        } else {
            initView();

        }
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
        tvTitle.setText("账号动态");
        manager = new LinearLayoutManager(UserStateActivity.this, LinearLayoutManager.VERTICAL, false);
        backArrow.setOnClickListener(new MyOnclick());
        mPullRefreshRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.ry_user_state);
        ry_user_state = mPullRefreshRecyclerView.getRefreshableView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(ry_user_state, null);
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new MyStateAsyncTask().execute();
        // mPullRefreshRecyclerView.setHasPullUpFriction(false); // 设置没有上拉阻力
        mPullRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                //得到当前刷新的时间
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                //设置更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                tag = false;
                prarams.clear();
                prarams.put("usersid", userId);
                prarams.put("usertoken", userToken);
                prarams.put("firstid", "" + the_first_id);
                new MyStateAsyncTask().execute();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshRecyclerView.onRefreshComplete();
                    }
                }, 500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                tag = true;
                if (page < mtPage) {
                    ++page;
                    Log.e("tag", "page" + page);
                    prarams.clear();
                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    prarams.put("page", "" + page);
                    new MyStateAsyncTask().execute();
                } else {
                    Toast.makeText(UserStateActivity.this, "已经加载到最后页了", Toast.LENGTH_SHORT).show();
                    mPullRefreshRecyclerView.onRefreshComplete();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class MyStateAsyncTask extends AsyncTask<String, Integer, String> {
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
            String result = AgentApi.dopost3(URL.getInstance().UserState_URL, prarams);
            Log.e("账号动态", "result" + result);
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
                    StateDb stateDb = gson.fromJson(s, StateDb.class);
                    if (stateDb.getStatus() == 1) {
                        if (stateDb.getData() != null && stateDb.getData().size() > 0) {
                            if (tag) {
                                mtPage = Integer.parseInt(stateDb.getTotalpage());
                                Log.e("tag", "mtPage" + mtPage);
                                list.addAll(stateDb.getData());
                                Log.e("tag", "list" + list);
                                mPullRefreshRecyclerView.onRefreshComplete();
                                // int n = (page - 1) * 3;
                                int n = list.size() - stateDb.getData().size();
                                MoveToPosition(manager, ry_user_state, n);
                            } else {
                                for (int i = 0; i < stateDb.getData().size(); i++) {
                                    list.add(i, stateDb.getData().get(i));
                                    mPullRefreshRecyclerView.onRefreshComplete();
                                }
                            }
                            the_first_id = list.get(0).getId();
                            adapter = new UserStateRcyAdapter(UserStateActivity.this, list);
                            Log.e("tag", "adapter" + adapter);
                            ry_user_state.setLayoutManager(manager);
                            ry_user_state.setAdapter(adapter);
                            mLoadingAndRetryManager.showContent();
                        } else {
                            if (tag) {
                                mLoadingAndRetryManager.showEmpty();
                            } else {
                                Toast.makeText(UserStateActivity.this, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }else if (stateDb.getStatus() == -97 || stateDb.getStatus() == -99) {
                        Unlogin.doLogin(UserStateActivity.this);
                    } else if (stateDb.getStatus() == 99) {
                        Unlogin.doAtk(prarams,s,  new MyStateAsyncTask());
                      //  new MyStateAsyncTask().execute();
                    }else{
                        finish();
                        Toast.makeText(UserStateActivity.this, stateDb.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(UserStateActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(UserStateActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back_arrow:
                    finish();
                    break;
            }
        }
    }

    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        Log.e("tag", "" + firstItem);
        int lastItem = manager.findLastVisibleItemPosition();
        Log.e("tag", "" + lastItem);
        Log.e("tag", "" + n);
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }
}
