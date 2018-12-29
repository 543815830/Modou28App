package com.ruixin.administrator.ruixinapplication.home.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.MainActivity;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.home.adapter.ActRcyAdapter1;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.home.databean.HactDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.myprogessbar.OnLoadingAndRetryListener;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.home.webview.EventContentActivity;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/16.
 * 邮箱：543815830@qq.com
 * 活动专场的fragment
 */

public class HActSpeFragment extends BaseFragment {
    LinearLayout ll_act;
    private View view;
    private ListView ry_act;
    private PullToRefreshListView mPullRefreshlistView;
    private ActRcyAdapter1 adapter;
    private int mtPage;//总页数
    boolean tag=true;
    boolean first=true;
    int page=1;
   private String the_first_id;
    List<HactDb.DataBean> list=new ArrayList<HactDb.DataBean>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    int Position;
    @Override
    protected View initView() {
        if(view==null){
            Log.e("TAG", " initView");
            view=View.inflate(mContext,R.layout.fm_act_spe,null);
            ll_act=view.findViewById(R.id.ll_act);

            mPullRefreshlistView = view.findViewById(R.id.mpulltorefresh);
            ry_act = mPullRefreshlistView.getRefreshableView();
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
                    new MyActAsyncTask().execute();

                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    tag=true;
                    if(page<mtPage){
                        prarams.clear();
                       ++page;
                        prarams.put("page",""+page);
                        new MyActAsyncTask().execute();
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

            if (isVisibleToUser) {
                Log.e("TAG", " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);
                if(first){
                    mLoadingAndRetryManager = LoadingAndRetryManager.generate(ll_act, null);
                new MyActAsyncTask().execute();
                first=false;
                }
            }
        Log.e("TAG", " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);

        super.setUserVisibleHint(isVisibleToUser);
    }

    /*获取活动列表*/
    private class MyActAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(first){
                mLoadingAndRetryManager.showLoading();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e("TAG", " MyActAsyncTask ");
            String result= AgentApi.dopost3(URL.getInstance().ACT_URL,prarams);
            Log.e("result","result="+result);
            try {
                //停留时间
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Call onRefreshComplete when the list has been refreshed.
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        HactDb hactDb = gson.fromJson(s, HactDb.class);
                        if (hactDb.getData() != null && hactDb.getData().size() > 0) {
                            if(tag){
                                mtPage = Integer.parseInt(hactDb.getTotalpage());
                                the_first_id=hactDb.getFirstid();
                            list.addAll(hactDb.getData());
                                Log.e("result","list="+list);
                            mPullRefreshlistView.onRefreshComplete();
                        }else{
                                the_first_id=hactDb.getFirstid();
                                Position=0;
                                for(int i=0;i<list.size();i++){
                                    if(list.get(i).getTop().equals("1")){
                                        ++Position;
                                    }
                                }
                                for(int i=Position;i<hactDb.getData().size()+Position;i++){
                                    for(int j=0;j<hactDb.getData().size();j++){
                                        list.add(i,hactDb.getData().get(j));
                                        mPullRefreshlistView.onRefreshComplete();
                                    }

                                }
                                Log.e("the_first_id1","the_first_id1="+the_first_id);

                        }

                            Log.e("the_first_id","the_first_id="+the_first_id);
                            adapter = new ActRcyAdapter1(mContext, list);
                            Log.e("tag", "" + list);
                            mPullRefreshlistView.onRefreshComplete();
                            //adapter.notifyDataSetChanged();
                            ry_act.setAdapter(adapter);
                           if (tag) {
                                int n = list.size()-hactDb.getData().size();
                                ry_act.setSelection(n);
                            } else {
                                ry_act.setSelection(0);
                            }
                            //竖直方向是listview的效果，false是不设置倒置
                            //  ry_act.setLayoutManager(manager);
                            adapter.setOnItemClickListener(new ActRcyAdapter1.OnItemClickListener() {
                                @Override
                                public void OnItemClick(View view, String data) {
                                    Intent intent = new Intent();
                                    //设置传递的参数
                                    intent.putExtra("id", data);
                                    //从Activity MainActivity跳转到Activity OActivity
                                    intent.setClass(mContext, EventContentActivity.class);
                                    startActivity(intent);
                                }
                            });
                            mLoadingAndRetryManager.showContent();//这句是让loading消失显示内容
                        } else {
                            if(!tag){
                               Toast.makeText(mContext, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }else{
                                mLoadingAndRetryManager.showEmpty();
                               Toast.makeText(mContext, "数据为空", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else if (entry.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,   new MyActAsyncTask());
                    }else if(entry.getStatus()==-99||entry.getStatus()==-97){
                        Unlogin.doLogin(mContext);
                    }else{
                        Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {

               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
            mPullRefreshlistView.onRefreshComplete();
        }
    }

}
