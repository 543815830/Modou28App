package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.DomainNameAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.DomainName;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 专属域名activity
 */
public class DomainNameActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_domain_name)
    TextView tvDomainName;
    @BindView(R.id.commit)
    Button commit;
    @BindView(R.id.lv_domain_name)
    PullToRefreshListView mPullRefreshlistView;
    ListView lvDomainName;
    String userId = "", time = "", userToken = "";
    DomainNameAdapter adapter;
    int To_UP_REQUEST_CODE;
    public static int RESULT_CODE = 30;
    ArrayAdapter<String> adapter1;
    private List<DomainName.DataBean.LogBean> list = new ArrayList<>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private int mtPage;//总页数
    boolean tag = true;
    boolean first = true;
    private String the_first_id;//最新id
    int page = 1;
    LoadingAndRetryManager mLoadingAndRetryManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domain_name);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        initStatus();
        if (userId.equals("")) {
           Toast.makeText(DomainNameActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(DomainNameActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        } else {
            initView();
        }
    }

    private void initView() {
        tvTitle.setText("专属域名");
        lvDomainName = mPullRefreshlistView.getRefreshableView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(lvDomainName, null);
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new DomainNameAsyncTask().execute();
        mPullRefreshlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.e("tag","onPullDownToRefresh");
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
                new DomainNameAsyncTask().execute();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshlistView.onRefreshComplete();
                    }
                }, 500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                tag = true;
                if (page < mtPage) {
                    ++page;
                    prarams.clear();
                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    prarams.put("page", "" + page);
                    new DomainNameAsyncTask().execute();
                } else {
                   Toast.makeText(DomainNameActivity.this, "已经加载到最后页了", Toast.LENGTH_SHORT).show();
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

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick({R.id.back_arrow, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.commit:
                if (commit.getText().toString().equals("立即开通")) {
                    Intent intent = new Intent(DomainNameActivity.this, OpenDomainNameActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    To_UP_REQUEST_CODE = 20;
                    startActivityForResult(intent, To_UP_REQUEST_CODE);
                } else if (commit.getText().toString().equals("立即续费")) {
                    adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"一个月", "二个月", "三个月", "四个月", "五个月", "六个月", "七个月", "八个月", "九个月", "十个月", "十一个月", "十二个月"});
                    AlertDialog dialog1 = new AlertDialog.Builder(this)
                            .setIcon(R.mipmap.wen)
                            .setTitle("请选择续费时间")
                            .setAdapter(adapter1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   Toast.makeText(DomainNameActivity.this, adapter1.getItem(which), Toast.LENGTH_SHORT).show();
                                    String sTime = adapter1.getItem(which);
                                    if (sTime.equals("一个月")) {
                                        time = "1";
                                    } else if (sTime.equals("二个月")) {
                                        time = "2";
                                    } else if (sTime.equals("三个月")) {
                                        time = "3";
                                    } else if (sTime.equals("四个月")) {
                                        time = "4";
                                    } else if (sTime.equals("五个月")) {
                                        time = "5";
                                    } else if (sTime.equals("六个月")) {
                                        time = "6";
                                    } else if (sTime.equals("七个月")) {
                                        time = "7";
                                    } else if (sTime.equals("八个月")) {
                                        time = "8";
                                    } else if (sTime.equals("九个月")) {
                                        time = "9";
                                    } else if (sTime.equals("十个月")) {
                                        time = "10";
                                    } else if (sTime.equals("十一个月")) {
                                        time = "11";
                                    } else if (sTime.equals("十二个月")) {
                                        time = "12";
                                    }
                                    prarams.put("time", time);
                                    new XDomainAsyncTask().execute();
                                    dialog.dismiss();
                                }
                            }).create();
                    dialog1.show();
                }
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DomainNameAsyncTask extends AsyncTask<String, Integer, String> {
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
            String result = AgentApi.dopost3(URL.getInstance().DmainName_URL, prarams);
            Log.e("域名数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mLoadingAndRetryManager.showContent();
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    JSONObject jsb = null;
                    try {
                        jsb = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int status = jsb.optInt("status");
                    String msg=jsb.optString("msg");
                    Gson gson = new Gson();
                    if (status == 1) {
                        DomainName domainName = gson.fromJson(s, DomainName.class);
                        if(domainName.getTotalpage()!=null){
                            mtPage = Integer.parseInt(domainName.getTotalpage());
                        }
                        if (domainName.getData().getType() == -1) {
                            Log.e("status", "" + domainName.getData().getType());
                            tvDomainName.setText("您尚未开通专属域名");
                            commit.setVisibility(View.VISIBLE);
                            commit.setText("立即开通");
                        } else if (domainName.getData().getType() == 0) {
                            tvDomainName.setText("系统正在为您搭建专属服务器，请稍后");
                            commit.setVisibility(View.GONE);
                        } else if (domainName.getData().getType() == 1) {
                            Log.e("type", "" + domainName.getData().getType());
                            tvDomainName.setText(new StringBuilder().append(domainName.getData().getZsxlym()).append(domainName.getData().getPath()).toString());
                            commit.setVisibility(View.VISIBLE);
                            commit.setText("立即续费");
                        }
                        if (domainName.getData().getLog() != null && domainName.getData().getLog().size() > 0) {
                            if (tag) {
                                list.addAll(domainName.getData().getLog());
                            } else {
                                for (int i = 0; i < domainName.getData().getLog().size(); i++) {
                                    list.add(i, domainName.getData().getLog().get(i));
                                }
                            }
                            the_first_id = list.get(0).getId();
                            adapter = new DomainNameAdapter(DomainNameActivity.this, list);
                            lvDomainName.setAdapter(adapter);
                            mPullRefreshlistView.onRefreshComplete();
                            if (tag) {
                                int n = list.size()-domainName.getData().getLog().size();
                                lvDomainName.setSelection(n);
                            } else {
                                lvDomainName.setSelection(0);
                            }
                        } else {
                            if (tag) {
                                mLoadingAndRetryManager.showEmpty();
                            } else {
                               Toast.makeText(DomainNameActivity.this, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else if (status ==99) {

                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new DomainNameAsyncTask());
                    }else if(status==-97||status==-99){
                        Unlogin.doLogin(DomainNameActivity.this);
                    }else{
                        Toast.makeText(DomainNameActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(DomainNameActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(DomainNameActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @SuppressLint("StaticFieldLeak")
    private class XDomainAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().XDmain_URL, prarams);
            Log.e("返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    JSONObject jsb = null;
                    try {
                        jsb = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int status = jsb.optInt("status");
                    String msg=jsb.optString("msg");
                    if (status == 1) {
                       Toast.makeText(DomainNameActivity.this, " 续费成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new MessageEvent("3"));
                        tag=false;
                        if(adapter!=null){
                            adapter.notifyDataSetChanged();
                        }
                        initView();
                    }else if (status ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new XDomainAsyncTask());
                    }else if(status==-97||status==-99){
                        Unlogin.doLogin(DomainNameActivity.this);
                    }else{
                        Toast.makeText(DomainNameActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(DomainNameActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(DomainNameActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == To_UP_REQUEST_CODE && resultCode == OpenDomainNameActivity.RESULT_CODE) {
            prarams.clear();
            tag=true;
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            new DomainNameAsyncTask().execute();
          //  initView();
        }
    }
}
