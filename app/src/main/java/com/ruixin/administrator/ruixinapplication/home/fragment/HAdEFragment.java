package com.ruixin.administrator.ruixinapplication.home.fragment;

import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.home.adapter.AdERcyAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.home.databean.HAde;
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
 *  作者：Created by ${李丽} on 2018/3/16.
 * 邮箱：543815830@qq.com
 * 广告体验的fragment
 */

public class HAdEFragment extends BaseFragment {
    private View view;
    private RecyclerView ade_ry;
    private AdERcyAdapter adapter;
    boolean first=true;
    private static  List<HAde> list = new ArrayList<HAde>();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    @Override
    protected View initView() {
        // initData();
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_ad_e,null);
            ade_ry=view.findViewById(R.id.ade_ry);
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            Log.e("TAG", " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);
            if(first){
                mLoadingAndRetryManager = LoadingAndRetryManager.generate(ade_ry, null);
                new MyAdeAsyncTask().execute();
                first=false;
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    /*获取广告体验*/
   private class MyAdeAsyncTask extends AsyncTask<String, Integer, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().ADE_URL,prarams);
            Log.e("tag",""+prarams);
            Log.e("result","result="+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                int index = s.indexOf("{");
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        HAde hAde = gson.fromJson(s, HAde.class);
                        List<HAde.DataBean> list = hAde.getData();
                        if (list != null && list.size() > 0) {
                            adapter = new AdERcyAdapter(mContext, list);
                            ade_ry.setAdapter(adapter);
                            //设置GridView效果
                            ade_ry.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false));
                            mLoadingAndRetryManager.showContent();
                        } else {
                            mLoadingAndRetryManager.showEmpty();
                        }
                    }else if(entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyAdeAsyncTask());
                    }
                }else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else{
               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
