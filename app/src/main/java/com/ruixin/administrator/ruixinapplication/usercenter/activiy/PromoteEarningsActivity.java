package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
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
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MyEarningsAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.PromoteEarings;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.AgencyVoucherFragment;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * 推广收益界面
 */
public class PromoteEarningsActivity extends Activity {
    TextView tv_title;//标题
    LinearLayout back_arrow;//返回
    private ListView lv_pm_earnings;
    private MyEarningsAdapter adapter;
    private List<PromoteEarings.DataBean> list=new ArrayList<>();
    String userId="",userToken="";
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private PullToRefreshListView mPullRefreshlistView;
    private int mtPage;//总页数
    boolean tag=true;
    boolean first=true;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private String the_first_id;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_earnings);
        initStatus();
        userId=getIntent().getStringExtra("userId");
        userToken=getIntent().getStringExtra("userToken");
        initStatus();
        if(userId.equals("")){
           Toast.makeText(PromoteEarningsActivity.this,"您尚未登录",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(PromoteEarningsActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        }else{
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
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("推广收益");
        back_arrow = findViewById(R.id.back_arrow);
        mPullRefreshlistView=findViewById(R.id.lv_pm_eranings);
        lv_pm_earnings=mPullRefreshlistView.getRefreshableView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(lv_pm_earnings, null);
        prarams.put("usersid",userId);
        prarams.put("usertoken",userToken);
        new MyEarningsAsyncTask().execute();
        back_arrow.setOnClickListener(new MyOnClick());
        mPullRefreshlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //得到当前刷新的时间
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                //设置更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                tag=false;
                prarams.clear();
                prarams.put("usersid",userId);
                prarams.put("usertoken",userToken);
                prarams.put("firstid",""+the_first_id);
                new MyEarningsAsyncTask().execute();
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
                    prarams.clear();
                    prarams.put("usersid",userId);
                    prarams.put("usertoken",userToken);
                    prarams.put("page",""+page);
                    new MyEarningsAsyncTask().execute();
                }else{
                   Toast.makeText(PromoteEarningsActivity.this, "已经加载到最后页了", Toast.LENGTH_SHORT).show();
                    //解决刷新失效问题
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullRefreshlistView.onRefreshComplete();
                        }
                    }, 500);
                    //mPullRefreshlistView.onRefreshComplete();
                }
            }
        });
    }

    private class MyOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back_arrow:
                    finish();
                    break;
            }
        }
    }
    private class MyEarningsAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(first){
                mLoadingAndRetryManager.showLoading();
                first=false;
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().PromoteEarnings_URL,prarams);
            Log.e("推广收益的数据返回",""+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null){
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson=new Gson();
                    PromoteEarings offlineDb=gson.fromJson(s,PromoteEarings.class);
                    if(offlineDb.getStatus()==1) {
                        if (offlineDb.getData() != null && offlineDb.getData().size() > 0) {
                            if(tag){
                                mtPage = Integer.parseInt(offlineDb.getTotalpage());
                                list.addAll(offlineDb.getData());
                                Log.e("result","list="+list);
                            }else{
                                    for(int i=0;i<offlineDb.getData().size();i++){
                                        list.add(i,offlineDb.getData().get(i));
                                    }
                            }
                            the_first_id=list.get(0).getTjlogid();
                            adapter=new MyEarningsAdapter(PromoteEarningsActivity.this,list);
                            Log.e("tag", "" + list);
                            lv_pm_earnings.setAdapter(adapter);
                            mPullRefreshlistView.onRefreshComplete();
                            if (tag) {
                                int n = (page - 1) * 3;
                                lv_pm_earnings.setSelection(n + 1);
                            } else {
                                lv_pm_earnings.setSelection(0);
                            }
                            mLoadingAndRetryManager.showContent();
                        } else {
                            if(tag){
                              mLoadingAndRetryManager.showEmpty();
                            }else{
                               Toast.makeText(PromoteEarningsActivity.this, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else if (offlineDb.getStatus()  ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyEarningsAsyncTask());
                    }else if(offlineDb.getStatus()==-97||offlineDb.getStatus()==-99){
                        Unlogin.doLogin(PromoteEarningsActivity.this);
                }else{
                        Toast.makeText(PromoteEarningsActivity.this, offlineDb.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(PromoteEarningsActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else{
               Toast.makeText(PromoteEarningsActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
