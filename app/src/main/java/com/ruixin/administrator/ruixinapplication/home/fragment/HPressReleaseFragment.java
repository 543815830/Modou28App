package com.ruixin.administrator.ruixinapplication.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.home.adapter.PRListAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.home.databean.HPR;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.myprogessbar.OnLoadingAndRetryListener;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.home.webview.NoticeContentActivity;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/16.
 * 邮箱：543815830@qq.com
 * 首页的新闻公告页
 */

public class HPressReleaseFragment extends BaseFragment {
    private View view;
    private ListView pr_lv;
    protected PRListAdapter adapter;
    private PullToRefreshListView mPullRefreshlistView;
    LinearLayoutManager manager;
    private int mtPage;//总页数
    boolean tag=true;
    boolean first=true;
    int page=1;
    private String the_first_id;
    int Position=1;
    List<HPR.DataBean> list=new ArrayList<>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
   /* private String[]datas=new String[]{
            "关于趣玩28游戏","关于28用户须知","关于卡奖问题","禁止一人多号","...."
    };*/
   /*loading*/
   LoadingAndRetryManager mLoadingAndRetryManager;
    @Override
    protected View initView() {

        if(view==null){
            view=View.inflate(mContext, R.layout.fm_press_release,null);
            mPullRefreshlistView = (PullToRefreshListView) view.findViewById(R.id.pr_lv);
            pr_lv = mPullRefreshlistView.getRefreshableView();
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
                    prarams.put("firstid",""+the_first_id);
                    new MyHprAsyncTask().execute();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullRefreshlistView.onRefreshComplete();
                        }
                    }, 500);
                }
                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    //得到当前刷新的时间
                    String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
                            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                    //设置更新时间
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                    tag=true;
                    if(page<mtPage){
                        prarams.clear();
                        ++page;
                        prarams.put("page",""+page);
                        new MyHprAsyncTask().execute();
                    }else{
                       Toast.makeText(mContext, "已经加载到最后页了", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPullRefreshlistView.onRefreshComplete();
                            }
                        }, 500);
                    }
                }
            });

        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //Log.d("TAG", mTagName + " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);
        Log.e("TAG", " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            Log.e("TAG", " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);
            if(first){
                mLoadingAndRetryManager = LoadingAndRetryManager.generate(pr_lv, null);
                new MyHprAsyncTask().execute();
                first=false;
            }

        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    /*获取新闻公告*/
    @SuppressLint("StaticFieldLeak")
    private class MyHprAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(first){
                mLoadingAndRetryManager.showLoading();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().News_URL,prarams);
            Log.e("result","result="+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!(s==null||s.equals(""))){
                int index = s.indexOf("{");
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        HPR hpr = gson.fromJson(s, HPR.class);
                            if(hpr.getData()!=null&&hpr.getData().size()>0){
                                if(tag){
                                    //加载
                                    mtPage= Integer.parseInt(hpr.getTotalpage());
                                    the_first_id=hpr.getFirstid();
                                    list.addAll(hpr.getData());
                                    int n=list.size()-hpr.getData().size();
                                   pr_lv.smoothScrollToPosition(n);
                                    mPullRefreshlistView.onRefreshComplete();
                                    //list.s((page-1)*3);
                                }else{
                                    //刷新
                                       the_first_id=hpr.getFirstid();
                                        Position=0;
                                       for(int i=0;i<list.size();i++){
                                           if(list.get(i).getTop().equals("1")){
                                               ++Position;
                                           }
                                       }
                                        for(int i=Position;i<hpr.getData().size()+Position;i++){
                                           for(int j=0;j<hpr.getData().size();j++){
                                               list.add(i,hpr.getData().get(j));
                                               mPullRefreshlistView.onRefreshComplete();
                                           }

                                        }

                                }

                            adapter = new PRListAdapter(mContext, list);
                                adapter.notifyDataSetChanged();
                                mPullRefreshlistView.onRefreshComplete();
                            pr_lv.setAdapter(adapter);
                            pr_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Log.e("onItemClick","onItemClick="+i);
                                    Intent intent = new Intent();
                                    //设置传递的参数
                                    intent.putExtra("id", list.get(i-1).getId());
                                    //从Activity MainActivity跳转到Activity OActivity
                                    intent.setClass(mContext, NoticeContentActivity.class);
                                    startActivity(intent);
                                }
                            });
                            mLoadingAndRetryManager.showContent();
                        } else {
                                if(tag){
                                   Toast.makeText(mContext, "数据为空", Toast.LENGTH_SHORT).show();
                                }else{
                                   Toast.makeText(mContext, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                                }
                        }
                    }else if(entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyHprAsyncTask());
                    }
                }else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
        }else{
                mLoadingAndRetryManager.showEmpty();
               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
