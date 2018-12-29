package com.ruixin.administrator.ruixinapplication.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.ProductDetailActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MybetModeDB;
import com.ruixin.administrator.ruixinapplication.home.adapter.HotPrizeRcyAdapter;
import com.ruixin.administrator.ruixinapplication.home.databean.HotPrize;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.popwindow.MyBetModePop;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/21.
 * 邮箱：543815830@qq.com
 * 热门奖品的界面
 */

public class HPrizeMoreFragment extends BaseFragment {
    private View view;
    LinearLayout ll_hot_prize;
    private RecyclerView hot_prize_ry;
    private HotPrizeRcyAdapter adapter;
    boolean first=true;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    String id;
    List<HotPrize.DataBean.HotprizeBean> list=new ArrayList<>();
    LinearLayout ll_quexing;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==1){
                id=HPrizeFragment.id;
                if(!id.equals("")){
                    prarams.put("id",id);
                    Log.e("tagid",""+id);

                }else{
                    prarams.clear();
                }
                new MyPrizeAsyncTask().execute();
            }
        }
    };
    @Override
    protected View initView() {
        if(view==null){
            id=(String)getArguments().get("pid");
            Log.e("tagpid",""+id);
            if(!id.equals("")){
                prarams.put("id",id);
            }
            view=View.inflate(mContext, R.layout.fm_hot_prize,null);
            ll_hot_prize=view.findViewById(R.id.ll_hot_prize);
            ll_quexing=view.findViewById(R.id.ll_quexing);
            hot_prize_ry=view.findViewById(R.id.hot_prize_ry);

        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
         if(first){
             mLoadingAndRetryManager = LoadingAndRetryManager.generate(hot_prize_ry, null);
             new MyPrizeAsyncTask().execute();
             first=false;
         }

        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    /*获取热门奖品*/
    private class MyPrizeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().PrizeType_Prize_URL,prarams);
            Log.e("result","result="+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.length()>0){
                Log.e("tag","s结果长度"+s.length());
                int index = s.indexOf("{");
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                      list.clear();
                        HotPrize hotPrize = gson.fromJson(s, HotPrize.class);
                        list = hotPrize.getData().getNewprize();
                        if (list != null && list.size() > 0) {
                            ll_quexing.setVisibility(View.GONE);
                            adapter = new HotPrizeRcyAdapter(mContext, list);
                            hot_prize_ry.setAdapter(adapter);
                            adapter.setOnItemClickListener(new HotPrizeRcyAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(View view, String data) {
                                    Intent intent=new Intent(mContext, ProductDetailActivity.class);
                                    intent.putExtra("id",data);
                                    startActivity(intent);
                                }
                            });
                            adapter.setOnbtnClickListener(new HotPrizeRcyAdapter.OnbtnClickListener() {
                                @Override
                                public void OnbtnClick(View view, String data) {
                                    Intent intent=new Intent(mContext, ProductDetailActivity.class);
                                    intent.putExtra("id",data);
                                    startActivity(intent);
                                }
                            });
                            //设置GridView效果
                            hot_prize_ry.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
                            mLoadingAndRetryManager.showContent();
                        } else {
                            mLoadingAndRetryManager.showContent();
                            ll_quexing.setVisibility(View.VISIBLE);
                        }
                    }else if(entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyPrizeAsyncTask());
                    }
                }else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else {

               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
