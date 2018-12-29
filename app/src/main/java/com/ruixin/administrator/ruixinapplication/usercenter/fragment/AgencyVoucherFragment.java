package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.UserFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.AgencyRcyAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.Agency;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.myprogessbar.OnLoadingAndRetryListener;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.usercenter.webview.AqWebview;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * 代理商支付
 */

public class AgencyVoucherFragment extends BaseFragment {
    private View view;
    private RecyclerView ry_agency_pay;
    private AgencyRcyAdapter adapter;
    List<Agency.DataBean> list;
    String usersid = "",userToken="";
    LoadingAndRetryManager mLoadingAndRetryManager;
    boolean first=true;
    private HashMap<String, String> prarams = new HashMap<String, String>();

    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_agency_voucher,null);
            ry_agency_pay=view.findViewById(R.id.ry_agency_pay);
            usersid = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
            userToken = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
            prarams.put("usersid",usersid);
            prarams.put("usertoken",userToken);

        }
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if(first){
                mLoadingAndRetryManager = LoadingAndRetryManager.generate(ry_agency_pay, null);
                new AgencyVoucherFragment.MyActAsyncTask().execute();
                first=false;
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    /*获取活动列表*/
    @SuppressLint("StaticFieldLeak")
    private class MyActAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().Agency_URL,prarams);
            Log.e("result","result="+result);
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
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        Agency agencyDb=gson.fromJson(s,Agency.class);
                        list=agencyDb.getData();

                        if(list!=null&&list.size()>0){

                            adapter=new AgencyRcyAdapter(mContext,list);
                            ry_agency_pay.setAdapter(adapter);
                            //设置GridView效果
                            ry_agency_pay.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));

                            adapter.setOnItemClickListener(new AgencyRcyAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(View view, String data) {
                                    Intent intent = new Intent();
                                    //设置传递的参数
                                    intent.putExtra("qq",data);
                                    intent.putExtra("userToken",userToken);
                                    //从Activity MainActivity跳转到Activity OActivity
                                    intent.setClass(mContext,AqWebview.class);
                                    startActivity(intent);
                                }
                            });
                            mLoadingAndRetryManager.showContent();//这句是让loading消失显示内容
                        }else{
                            mLoadingAndRetryManager.showEmpty();
                        }
                    }else if(entry.getStatus()==-97||entry.getStatus()==-99){
                        Unlogin.doLogin(mContext);
                    }else if (entry.getStatus()  ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyActAsyncTask());
                    }else{
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
}
