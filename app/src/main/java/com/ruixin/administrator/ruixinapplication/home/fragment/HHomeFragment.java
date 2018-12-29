package com.ruixin.administrator.ruixinapplication.home.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.ProductDetailActivity;
import com.ruixin.administrator.ruixinapplication.home.adapter.HActRcyAdapter;
import com.ruixin.administrator.ruixinapplication.home.adapter.HAdERcyAdapter;
import com.ruixin.administrator.ruixinapplication.home.adapter.HHotPrizeRcyAdapter;
import com.ruixin.administrator.ruixinapplication.home.adapter.PRRcyAdapter;
import com.ruixin.administrator.ruixinapplication.home.adapter.ParRcyAdapter;
import com.ruixin.administrator.ruixinapplication.home.databean.LMessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.home.databean.HomeDb;
import com.ruixin.administrator.ruixinapplication.home.databean.Partner;
import com.ruixin.administrator.ruixinapplication.home.databean.PartnerDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.home.webview.EventContentActivity;
import com.ruixin.administrator.ruixinapplication.home.webview.NoticeContentActivity;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：Created by ${李丽} on 2018/3/17.
 * 邮箱：543815830@qq.com
 * 首页下的首页
 */

public class HHomeFragment extends BaseFragment {
    private View view;
    private RecyclerView ry_hhome;
    private RecyclerView ry_hhome_ade;
    private RecyclerView ry_hhome_hotprize;
    private RecyclerView ry_hhome_pr;
    private RecyclerView ry_hhome_partner;
    private HActRcyAdapter adapter;
    private HAdERcyAdapter adapter1;
    private PRRcyAdapter adapter2;
    private HHotPrizeRcyAdapter adapter3;
    private ParRcyAdapter adapter4;
    private PullToRefreshScrollView pullToRefreshScrollView;
   ScrollView mScrollView;
    boolean first=true;
    String result;
    Gson gson;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    /*伙伴准备的数据*/
    List<HomeDb.DataBean.HdlistBean> list=new ArrayList<>();
    List<HomeDb.DataBean.NewsBean> list1=new ArrayList<>();
    List<HomeDb.DataBean.HotprizeBean> list2=new ArrayList<>();
    private static  List<Partner> list4 = new ArrayList<Partner>();
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_hhome,null);
            pullToRefreshScrollView = view.findViewById(R.id.pull_to_refresh_scrollview);
            pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                    //得到当前刷新的时间
                    String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
                            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                    //设置更新时间
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                    new HomeAsyncTask().execute();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pullToRefreshScrollView.onRefreshComplete();
                        }
                    }, 500);

                }

            });
            mScrollView = pullToRefreshScrollView.getRefreshableView();
            /*活动专场*/
            ry_hhome=view.findViewById(R.id.ry_h_home);
              /*游戏试玩*/
            ry_hhome_ade=view.findViewById(R.id.ry_h_home_ade);

            /*新闻公告*/
           ry_hhome_pr=view.findViewById(R.id.ry_h_home_pr);
             /*热门奖品*/
            ry_hhome_hotprize=view.findViewById(R.id.ry_h_home_hotprize);
             /*合作伙伴*/
            ry_hhome_partner=view.findViewById(R.id.ry_h_home_partner);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserCache",
                    Activity.MODE_PRIVATE);
            result = sharedPreferences.getString("index", "");
            Log.e("tag","result"+result);
            if(!(result==null||result.equals(""))){
                initData(result,gson);
            }
            new HomeAsyncTask().execute();
            list4 = PartnerDb.getList();
            adapter4=new ParRcyAdapter(mContext,list4);
            ry_hhome_partner.setAdapter(adapter4);
            //设置GridView效果
            ry_hhome_partner.setLayoutManager(new GridLayoutManager(mContext,2, GridLayoutManager.HORIZONTAL,false));
        }
        return view;
    }
   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //Log.d("TAG", mTagName + " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);
        Log.e("TAG", " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            Log.e("TAG", " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);
            new HomeAsyncTask().execute();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }*/
    /*首页的异步请求*/
    @SuppressLint("StaticFieldLeak")
    private class HomeAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
           result= AgentApi.dopost3(URL.getInstance().INDEX_URL,prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pullToRefreshScrollView.onRefreshComplete();
            Log.e("获取首页消息返回","消息返回结果result"+result);
            if(result!=null){
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                   gson=new Gson();
                    Entry entry = gson.fromJson(result, Entry.class);
                    if (entry.getStatus() == 1) {
                        SharedPreferences sharedPreferences =mContext.getSharedPreferences("UserCache", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("index", result);
                        editor.commit();
                        list.clear();
                        list1.clear();
                        list2.clear();
                        initData(result, gson);
                    }else if(entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,result,new HomeAsyncTask());
                    }else {//如果是错误的json数据则进行截取解析并上传到友盟
                        Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else{
               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
        }

    private void initData(String result, Gson gson) {
        Log.e("TAG", "initData=== " );
        gson=new Gson();
        HomeDb homeDb = gson.fromJson(result, HomeDb.class);
        List<HomeDb.DataBean.ImgBean>imgBeanList=homeDb.getData().getImg();
        EventBus.getDefault().post(new LMessageEvent(imgBeanList));
                    /*活动专场的数据*/
        list = homeDb.getData().getHdlist();
        if (list != null && list.size() > 0) {
            adapter = new HActRcyAdapter(mContext, list);
            ry_hhome.setAdapter(adapter);
            ry_hhome.setNestedScrollingEnabled(false);

            //竖直方向是listview的效果，false是不设置倒置
            ry_hhome.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            adapter.setOnItemClickListener(new HActRcyAdapter.OnItemClickListener() {
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
        } else {
           Toast.makeText(mContext, "活动专场数据为空", Toast.LENGTH_SHORT).show();
        }
                 /*新闻公告的数据*/
      list1 = homeDb.getData().getNews();
        if (list1 != null && list1.size() > 0) {
            adapter2 = new PRRcyAdapter(mContext, list1);
            ry_hhome_pr.setAdapter(adapter2);
            ry_hhome_pr.setNestedScrollingEnabled(false);
            //竖直方向是listview的效果，false是不设置倒置
            ry_hhome_pr.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            adapter2.setOnItemClickListener(new PRRcyAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, String data) {
                    Intent intent = new Intent();
                    //设置传递的参数
                    intent.putExtra("id", data);
                    //从Activity MainActivity跳转到Activity OActivity
                    intent.setClass(mContext, NoticeContentActivity.class);
                    startActivity(intent);
                }
            });
        } else {
           Toast.makeText(mContext, "新闻公告数据为空", Toast.LENGTH_SHORT).show();
        }
   /*游戏试玩的数据*/
        List<HomeDb.DataBean.GamelistBean> list3 = homeDb.getData().getGamelist();
        if (list3 != null && list3.size() > 0) {
            adapter1 = new HAdERcyAdapter(mContext, list3);
            ry_hhome_ade.setAdapter(adapter1);
            ry_hhome_ade.setNestedScrollingEnabled(false);
            //设置GridView效果
            ry_hhome_ade.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
        } else {
           Toast.makeText(mContext, "游戏试玩数据为空", Toast.LENGTH_SHORT).show();
        }
                    /*热门奖品的数据*/
       list2 = homeDb.getData().getHotprize();
        if (list2 != null && list2.size() > 0) {
            adapter3 = new HHotPrizeRcyAdapter(mContext, list2);
            ry_hhome_hotprize.setAdapter(adapter3);
            //设置GridView效果
            ry_hhome_hotprize.setNestedScrollingEnabled(false);
            ry_hhome_hotprize.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
            adapter3.setOnItemClickListener(new HHotPrizeRcyAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, String data) {
                    Intent intent=new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra("id",data);
                    startActivity(intent);
                }
            });
        } else {
           Toast.makeText(mContext, "热门奖品数据为空", Toast.LENGTH_SHORT).show();
        }
    }
}

