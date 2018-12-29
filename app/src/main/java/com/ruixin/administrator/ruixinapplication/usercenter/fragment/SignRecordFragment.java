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
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.PromoteEarningsActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.SignRecordAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.SignRecord;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.myprogessbar.OnLoadingAndRetryListener;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.ExToast;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/17.
 * 邮箱：543815830@qq.com
 * 签到记录
 */

public class SignRecordFragment extends BaseFragment {
    private View view;
    private ListView lv_sign_record;
    private SignRecordAdapter adapter;
    private List<SignRecord.DataBean> list = new ArrayList<>();
    String userId = "", userToken = "";
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private PullToRefreshListView mPullRefreshlistView;
    private int mtPage;//总页数
    boolean tag = true;//判断上拉下拉的标志
    boolean first = true;//是否是第一次创建
    private String the_first_id;//最新id
    int page = 1;

    @Override
    protected View initView() {
        if (view == null) {
            view = View.inflate(mContext, R.layout.fm_sign_record, null);
            mPullRefreshlistView = view.findViewById(R.id.lv_sign_record);
            lv_sign_record = mPullRefreshlistView.getRefreshableView();
            userId = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
            userToken = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            mPullRefreshlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    //得到当前刷新的时间
                    String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
                            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                    //设置更新时间
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                    tag = false;
                    prarams.clear();
                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    prarams.put("firstid", "" + the_first_id);
                    new SignRecordAsyncTask().execute();
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
                        new SignRecordAsyncTask().execute();
                    } else {
                        Toast.makeText(mContext, "已经加载到最后页了", Toast.LENGTH_SHORT).show();
                        //解决刷新失效问题
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

    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (first) {
                mLoadingAndRetryManager = LoadingAndRetryManager.generate(lv_sign_record, null);
                new SignRecordAsyncTask().execute();
                first = false;
            }

        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @SuppressLint("StaticFieldLeak")
    private class SignRecordAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (first) {
                mLoadingAndRetryManager.showLoading();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().SignRecord_URL, prarams);
            Log.e("我的签到记录的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    SignRecord signRecord = gson.fromJson(s, SignRecord.class);
                    if (signRecord.getStatus() == 1) {
                        if (signRecord.getData() != null && signRecord.getData().size() > 0) {
                            if (tag) {
                                mtPage = Integer.parseInt(signRecord.getTotalpage());
                                list.addAll(signRecord.getData());
                                Log.e("result", "list=" + list);
                            } else {
                                for (int i = 0; i < signRecord.getData().size(); i++) {
                                    list.add(i, signRecord.getData().get(i));
                                }
                            }
                            the_first_id = list.get(0).getId();
                            adapter = new SignRecordAdapter(mContext, list);
                            Log.e("tag", "" + list);
                            lv_sign_record.setAdapter(adapter);
                            mPullRefreshlistView.onRefreshComplete();
                            if (tag) {
                                // int n = (page - 1) * 3;
                                int n = list.size() - signRecord.getData().size();
                                lv_sign_record.setSelection(n + 1);
                            } else {
                                lv_sign_record.setSelection(0);
                            }

                            mLoadingAndRetryManager.showContent();
                        } else {
                            if (tag) {
                                mLoadingAndRetryManager.showEmpty();
                            } else {
                                Toast.makeText(mContext, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (signRecord.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams, s, new SignRecordAsyncTask());
                    } else if (signRecord.getStatus() == -97 || signRecord.getStatus() == -99) {
                        Unlogin.doLogin(mContext);
                    } else {
                        Toast.makeText(mContext, signRecord.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
