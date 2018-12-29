package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.ProductDetailActivity;
import com.ruixin.administrator.ruixinapplication.exchangemall.domain.CoRecordDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.RedbagDetailActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MyredbagRecordAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.RedbagrecordDb;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 李丽 on 2018/9/13.
 *
 */

public class MyredbagrecordFragment extends BaseFragment {
    private View view;
    PullToRefreshListView mPullRefreshlistView;
    ListView myredbag_record_lv;
    String type;
    String userId;
    String userToken;
    private String the_last_id;//最后一条的id
    private int mtPage;//总页数
    boolean tag = true;//判断上拉下拉的标志
    boolean first = true;//是否是第一次创建
    private String the_first_id;//最新id
    int page = 1;
    int position;
    String url;
    String result;
    String msg;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    MyredbagRecordAdapter adapter;
    List<RedbagrecordDb.DataBean> list = new ArrayList<>();

    @Override
    protected View initView() {
        if (view == null) {
            type = (String) getArguments().get("type");
            view = View.inflate(mContext, R.layout.fm_rebbag_record, null);
            mPullRefreshlistView = view.findViewById(R.id.myred_bag_record_lv);
            myredbag_record_lv = mPullRefreshlistView.getRefreshableView();
            mLoadingAndRetryManager = LoadingAndRetryManager.generate(myredbag_record_lv, null);
            myredbag_record_lv.setVerticalScrollBarEnabled(false);
            myredbag_record_lv.setFastScrollEnabled(false);
            userId = (String) getArguments().get("userid");
            userToken = (String) getArguments().get("usertoken");
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            if (type.equals("0")) {
                url = URL.getInstance().Mysendbag_URL;
            } else {
                url = URL.getInstance().Mygetbag_URL;
            }
            new MyRedbagRecordAsyncTask().execute();
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
                    if (type.equals("0")) {
                        url = URL.getInstance().Mysendbag_URL;
                    } else {
                        url = URL.getInstance().Mygetbag_URL;
                    }
                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    list.clear();
                   // prarams.put("firstid", "" + the_first_id);
                    new MyRedbagRecordAsyncTask().execute();
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
                      //  prarams.put("page", "" + page);
                        if (type.equals("0")) {
                            url = URL.getInstance().Mysendbag_URL+"?page="+page;
                        } else {
                            url = URL.getInstance().Mygetbag_URL+"?page="+page;
                        }
                        new MyRedbagRecordAsyncTask().execute();
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

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(first){
              //  mLoadingAndRetryManager = LoadingAndRetryManager.generate(myredbag_record_lv, null);
              //  new MyRedbagRecordAsyncTask().execute();
                first=false;
            }

        }
    }
*/

    @SuppressLint("StaticFieldLeak")
    private class MyRedbagRecordAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (first) {
                mLoadingAndRetryManager.showLoading();
                first = false;
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            result = AgentApi.dopost3(url, prarams);
            Log.e("我的红包记录的数据返回", "" + result);
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
                    RedbagrecordDb redbagrecordDb = gson.fromJson(s, RedbagrecordDb.class);
                    if (redbagrecordDb.getStatus() == 1) {
                        if (redbagrecordDb.getData() != null && redbagrecordDb.getData().size() > 0) {
                            mLoadingAndRetryManager.showContent();
                            if(tag){//加载
                                mtPage = Integer.parseInt(redbagrecordDb.getTotalpage());
                                list.addAll(redbagrecordDb.getData());
                            }else{
                                for(int i=0;i<redbagrecordDb.getData().size();i++){
                                    list.add(i,redbagrecordDb.getData().get(i));
                                }
                            }
                            mPullRefreshlistView.onRefreshComplete();
                            the_first_id=list.get(0).getId();
                            adapter = new MyredbagRecordAdapter(mContext, list,type);
                            myredbag_record_lv.setAdapter(adapter);
                          adapter.setOnItemClickListener(new MyredbagRecordAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick( View view, int i) {
                                    Intent intent = new Intent(mContext, RedbagDetailActivity.class);
                                    intent.putExtra("userid", userId);
                                    intent.putExtra("usertoken", userToken);
                                    intent.putExtra("hbid", list.get(i).getHbid());
                                    intent.putExtra("mybag", (Serializable) list.get(i));
                                    intent.putExtra("type", type);
                                    Log.e("tag",""+i);
                                    startActivityForResult(intent,78);
                                }
                            });
                            adapter.setOnShareClickListener(new MyredbagRecordAdapter.OnShareClickListener() {
                                @Override
                                public void OnShareClick(View view, int i){
                                    showShare(list.get(i).getHbid());
                                }
                            });
                            adapter.setOnBackClickListener(new MyredbagRecordAdapter.OnBackClickListener() {
                                @Override
                                public void OnBackClick(View view, int i) {
                                    //返还红包
                                    position=i;
                                    prarams.clear();
                                    prarams.put("usersid", userId);
                                    prarams.put("usertoken", userToken);
                                    prarams.put("hbid", list.get(i).getHbid());
                                    new BackbagAsyncTask().execute();
                                }
                            });
                        } else {
                            if(tag){
                                mLoadingAndRetryManager.showEmpty();
                               //Toast.makeText(mContext, "数据为空", Toast.LENGTH_SHORT).show();
                            }else{
                               Toast.makeText(mContext, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }
                           //
                        }
                    } else if (redbagrecordDb.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyRedbagRecordAsyncTask());
                    } else if (redbagrecordDb.getStatus() == -97 || redbagrecordDb.getStatus() == -99) {
                        Unlogin.doLogin(mContext);
                    } else{
                       Toast.makeText(mContext, redbagrecordDb.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    mLoadingAndRetryManager.showContent();
                   Toast.makeText(mContext, "服务器出错啦,请反馈给管理员哦！", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showShare(String data) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");
        // titleUrl QQ和QQ空间跳转链接
         oks.setTitleUrl(RuiXinApplication.getInstance().getUrl()+"RedPack/"+data);
        // text是分享文本，所有平台都需要这个字段
        String text = "【红包来啦】"+ RuiXinApplication.getInstance().getUrl()+"RedPack/"+data;
        //Log.e("tag")
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath(path);//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        // oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        // oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(mContext);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
               Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
               Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
               Toast.makeText(mContext, "分享取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取游戏列表
    private class BackbagAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().Mybackbag_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("返还红包", "返回结果result" + result);
            if (result != null) {
                int status = 0;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                    msg = re.optString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                   Toast.makeText(mContext, "返还成功！", Toast.LENGTH_SHORT).show();
                    list.get(position).setState("3");
                    adapter.notifyDataSetChanged();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(mContext);
                } else if (status == 99) {
                       /*抗攻击*/
                       Unlogin.doAtk(prarams,result,new BackbagAsyncTask());
                } else {
                   Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(mContext, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==78){
            prarams.clear();
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            list.clear();
            if (type.equals("0")) {
                url = URL.getInstance().Mysendbag_URL;
            } else {
                url = URL.getInstance().Mygetbag_URL;
            }
            new MyRedbagRecordAsyncTask().execute();
        }
    }
}
