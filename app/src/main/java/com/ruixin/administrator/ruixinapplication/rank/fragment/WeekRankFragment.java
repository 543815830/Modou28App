package com.ruixin.administrator.ruixinapplication.rank.fragment;

import android.annotation.SuppressLint;
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
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.rank.adapter.WRankingListAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.domain.EntryDb;
import com.ruixin.administrator.ruixinapplication.rank.databean.RankDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.myprogessbar.OnLoadingAndRetryListener;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 本周排行榜
 */

@SuppressLint("ValidFragment")
public class WeekRankFragment extends BaseFragment {
    private ListView mListView;
    private WRankingListAdapter adapter;
    private View view;
    boolean first=true;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    private PullToRefreshListView mPullRefreshlistView;
    @Override
    protected View initView() {
        if(view==null){
         view=View.inflate(mContext, R.layout.fragment_do_rank,null);
            mPullRefreshlistView = (PullToRefreshListView) view.findViewById(R.id.fd_ranking_list);
            mListView = mPullRefreshlistView.getRefreshableView();
            mPullRefreshlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                    //得到当前刷新的时间
                    String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
                            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                    //设置更新时间
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                    new MyRankAsyncTask().execute();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullRefreshlistView.onRefreshComplete();
                        }
                    }, 500);
                }
            });

        }
       // initData();
        return view;

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if(first){
                mLoadingAndRetryManager = LoadingAndRetryManager.generate(mListView,null);
                new MyRankAsyncTask().execute();
                first=false;
            }

        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    /*获取排行榜*/
    private class MyRankAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(first){
                mLoadingAndRetryManager.showLoading();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost(URL.getInstance().Rank_URL,prarams);
            Log.e("result","result="+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            mPullRefreshlistView.onRefreshComplete();
            super.onPostExecute(s);
            if(s.length()>0){
                int index = s.indexOf("{");
                if (index == 0) {
                    Gson gson = new Gson();
                    EntryDb entry = gson.fromJson(s, EntryDb.class);
                    if (entry.getStatus() == 1) {
                        RankDb rankDb = gson.fromJson(s, RankDb.class);
                        List<RankDb.DataBean.YingliYestBean> list = rankDb.getData().getYingli_yest();
                        if (list != null && list.size() > 0) {
                            adapter = new WRankingListAdapter(mContext, list);
                            mListView.setAdapter(adapter);
                            mLoadingAndRetryManager.showContent();
                        } else {
                            mLoadingAndRetryManager.showEmpty();

                        }
                    }else if(entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyRankAsyncTask());
                    }else {//如果是错误的json数据则进行截取解析并上传到友盟
                        Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
        }else{
                mLoadingAndRetryManager.showContent();
               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
