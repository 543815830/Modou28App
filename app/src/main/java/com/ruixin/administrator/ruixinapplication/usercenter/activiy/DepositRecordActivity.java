package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.app.Activity;
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
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.DepositReoordAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MybindBnakdAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MyCardDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MyFechDb;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 提现记录activity
 */
public class DepositRecordActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    ListView lvDepsitRecord;
    DepositReoordAdapter adapter;
    @BindView(R.id.lv_depsit_record)
    PullToRefreshListView mPullRefreshlistView;
    String userId,userToken;
    String url;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    boolean tag=true;
    boolean first=true;
    private int mtPage;//总页数
    int page=1;
    List<MyFechDb.DataBean> myFechList=new ArrayList<>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_record);
        ButterKnife.bind(this);
        initStatus();
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        tvTitle.setText("提现记录");
        lvDepsitRecord=mPullRefreshlistView.getRefreshableView();
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        url= URL.getInstance().deposit_info+"?act=history";
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(lvDepsitRecord, null);
        new GoldDepositeAsyncTask().execute();
        mPullRefreshlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //得到当前刷新的时间
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                //设置更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                tag=false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshlistView.onRefreshComplete();
                    }
                }, 500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                tag=true;
                if(page<mtPage){
                    ++page;
                    prarams.put("page",""+page);
                    new GoldDepositeAsyncTask().execute();
                }else{
                    Toast.makeText(DepositRecordActivity.this, "已经加载到最后页了", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullRefreshlistView.onRefreshComplete();
                        }
                    }, 500);
                }

            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
    private class GoldDepositeAsyncTask extends AsyncTask<String, Integer, String> {
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
            String result= AgentApi.dopost3(url,prarams);
            Log.e("金币提现jilu 的数据返回",""+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mLoadingAndRetryManager.showContent();
            if(s!=null){
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    mLoadingAndRetryManager.showContent();
                    Gson gson=new Gson();
                    MyFechDb myFechDb=gson.fromJson(s,MyFechDb.class);
                    if(myFechDb.getStatus()==1) {
                        // cardList=myCardDb.getData();
                       if(myFechDb.getData()!=null&&myFechDb.getData().size()>0){
                           myFechList.addAll(myFechDb.getData());
                            mPullRefreshlistView.onRefreshComplete();
                            mtPage=myFechDb.getCount();
                           adapter=new DepositReoordAdapter(DepositRecordActivity.this,myFechList);
                           lvDepsitRecord.setAdapter(adapter);
                        }else{
                           mLoadingAndRetryManager.showEmpty();
                            Toast.makeText(DepositRecordActivity.this, "您尚未提现！", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else if (myFechDb.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,   new GoldDepositeAsyncTask());
                        //  new MyTrusteeAsyncTask().execute();
                    } else if(myFechDb.getStatus()==-97||myFechDb.getStatus()==-99){
                        Unlogin.doLogin(DepositRecordActivity.this);
                    }else{
                        Entry entry=gson.fromJson(s,Entry.class);
                        Toast.makeText(DepositRecordActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(DepositRecordActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(DepositRecordActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
