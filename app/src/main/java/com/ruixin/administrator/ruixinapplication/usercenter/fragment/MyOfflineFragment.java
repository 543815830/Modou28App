package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MyofflineAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MyOffline;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.myprogessbar.OnLoadingAndRetryListener;
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
 * 我的下线的fragment
 */

public class MyOfflineFragment extends BaseFragment {
    private View view;
    private ListView lv_my_offline;
    private MyofflineAdapter adapter;
    private List<MyOffline.DataBean> list=new ArrayList<>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    String userId="",userToken="";
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private PullToRefreshListView mPullRefreshlistView;
    private int mtPage;//总页数
    boolean tag=true;
    boolean first=true;
    private String the_first_id;
    int page=1;
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_my_offline,null);
            userId=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            userToken=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
            prarams.put("usersid",userId);
            prarams.put("usertoken",userToken);
            mPullRefreshlistView=view.findViewById(R.id.lv_my_offline);
            lv_my_offline=mPullRefreshlistView.getRefreshableView();
            mPullRefreshlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    //得到当前刷新的时间
                    String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
                            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                    //设置更新时间
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                    tag=false;
                    prarams.clear();
                    prarams.put("usersid",userId);
                    prarams.put("usertoken",userToken);
                    prarams.put("firstid",""+the_first_id);
                    new MyOfflineAsyncTask().execute();
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
                        new MyOfflineAsyncTask().execute();
                    }else{
                       Toast.makeText(mContext, "已经加载到最后页了", Toast.LENGTH_SHORT).show();
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
        return view;
    }
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if(first){
                mLoadingAndRetryManager = LoadingAndRetryManager.generate(lv_my_offline, null);
                new MyOfflineAsyncTask().execute();
                first=false;
            }

        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    @SuppressLint("StaticFieldLeak")
    private class MyOfflineAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(first){
                mLoadingAndRetryManager.showLoading();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().MyOffline_URL,prarams);
            Log.e("我的下线的数据返回",""+result);
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
                   MyOffline offlineDb=gson.fromJson(s,MyOffline.class);

                    if(offlineDb.getStatus()==1) {
                            if (offlineDb.getData() != null && offlineDb.getData().size() > 0) {
                                if(tag){
                                    mtPage = Integer.parseInt(offlineDb.getTotalpage());
                                    list.addAll(offlineDb.getData());
                                    Log.e("result","list="+list);
                                   // mPullRefreshlistView.onRefreshComplete();
                                }else{
                                        for(int i=0;i<offlineDb.getData().size();i++){
                                            list.add(i,offlineDb.getData().get(i));
                                           // mPullRefreshlistView.onRefreshComplete();
                                        }

                                }
                                the_first_id=list.get(0).getId();
                                adapter = new MyofflineAdapter(mContext, list);
                                Log.e("tag", "" + list);
                                //mPullRefreshlistView.onRefreshComplete();
                                //adapter.notifyDataSetChanged();
                                lv_my_offline.setAdapter(adapter);
                                mPullRefreshlistView.onRefreshComplete();
                                if (tag) {
                                    int n = (page - 1) * 3;
                                    lv_my_offline.setSelection(n + 1);
                                } else {
                                    lv_my_offline.setSelection(0);
                                }
                            mLoadingAndRetryManager.showContent();
                        } else {
                            if(tag){
                                mLoadingAndRetryManager.showEmpty();
                            }else{
                               Toast.makeText(mContext, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else if (offlineDb.getStatus() ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyOfflineAsyncTask());
                    }else if(offlineDb.getStatus()==-97||offlineDb.getStatus()==-99){
                        Unlogin.doLogin(mContext);
                }else{
                        Toast.makeText(mContext, offlineDb.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else{
               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
        }

}
