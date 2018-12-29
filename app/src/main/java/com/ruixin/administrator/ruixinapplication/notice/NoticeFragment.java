package com.ruixin.administrator.ruixinapplication.notice;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.FragmentBackListener;
import com.ruixin.administrator.ruixinapplication.MainActivity;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.UserFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.InsideMailBoxActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.SmsContentActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MailBoxAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MailBox;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.BackHandlerHelper;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 消息通知的界面
 */

public class NoticeFragment extends BaseFragment implements FragmentBackListener {
    private View view;
    Button btn_login;
    RelativeLayout rl_notice;
    RelativeLayout rl_title;
    TextView all_select;
    TextView tv_notice;
    TextView complete;
    LinearLayout ll_fm_notice1;
    LinearLayout ll_delete;
    LinearLayout ll_bottom;
    LinearLayout set_read;
    ListView notice_lv;
    NoticeAdapter adapter;
    private int TO_TAG = 1;
    private long lastBackPress;
    String userId = "", userToken = "";
    boolean Tag = true;
    int time = 1;//判断是第一次进来数据为空还是上拉加载数据为空
    boolean first = true;
    private String the_first_id;//最新id
    private String the_last_id;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    List<NoticeDb.DataBean> list = new ArrayList<>();
    List<NoticeDb.DataBean> removelist = new ArrayList<>();
    private PullToRefreshListView mPullRefreshlistView;
    List<Integer> position = new ArrayList<>();
    int alone = 1;
    int removeid;
    StringBuilder sb;
    int height = 0;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                TO_TAG = 2;
                all_select.setVisibility(View.VISIBLE);
                complete.setVisibility(View.VISIBLE);
                ll_bottom.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected View initView() {
        // initStatus();
        if (view == null) {
            view = View.inflate(mContext, R.layout.fm_notice, null);
            ll_fm_notice1 = view.findViewById(R.id.ll_fm_notice);
            btn_login = view.findViewById(R.id.btn_login);
            tv_notice = view.findViewById(R.id.tv_notice);
            getStatusBarHeight();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_notice.getLayoutParams();
            layoutParams.setMargins(0, height, 0, 0);
            tv_notice.setLayoutParams(layoutParams);
            btn_login.setOnClickListener(new MyONclick());
            rl_notice = view.findViewById(R.id.rl_notice);
            rl_title = view.findViewById(R.id.rl_title);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rl_title.getLayoutParams();
            Log.e("tag", "lp------" + lp);
            Log.e("tag", "height22222------" + height);
            lp.setMargins(0, height, 0, 0);
            rl_title.setLayoutParams(lp);
            all_select = view.findViewById(R.id.all_select);
            all_select.setOnClickListener(new MyONclick());
            complete = view.findViewById(R.id.complete);
            complete.setOnClickListener(new MyONclick());
            set_read = view.findViewById(R.id.set_read);
            set_read.setOnClickListener(new MyONclick());
            ll_bottom = view.findViewById(R.id.ll_bottom);
            ll_delete = view.findViewById(R.id.ll_delete);
            ll_delete.setOnClickListener(new MyONclick());
            mPullRefreshlistView = view.findViewById(R.id.notice_lv);
            notice_lv = mPullRefreshlistView.getRefreshableView();
            mLoadingAndRetryManager = LoadingAndRetryManager.generate(notice_lv, null);
            userId = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
            userToken = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
            if (sharedPreferences.getString("is_login", "").equals("true")) {
                if (userToken == null || userId == null || userToken.equals("") || userId.equals("")) {
                    ll_fm_notice1.setVisibility(View.VISIBLE);
                } else {
                    ll_fm_notice1.setVisibility(View.GONE);

                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    new MyNoticeAsyncTask().execute();
                    mPullRefreshlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                        //下拉刷新
                        @Override
                        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                            //得到当前刷新的时间
                            String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
                                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                            //设置更新时间
                            refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                            Tag = false;
                            prarams.clear();
                            prarams.put("usersid", userId);
                            prarams.put("usertoken", userToken);
                            prarams.put("firstid", "" + the_first_id);
                            new MyNoticeAsyncTask().execute();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mPullRefreshlistView.onRefreshComplete();
                                }
                            }, 500);
                        }

                        @Override
                        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                            Tag = true;
                            ++time;
                            prarams.clear();
                            prarams.put("usersid", userId);
                            prarams.put("usertoken", userToken);
                            prarams.put("lastid", "" + the_last_id);
                            new MyNoticeAsyncTask().execute();
                            //解决刷新失效问题
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mPullRefreshlistView.onRefreshComplete();
                                }
                            }, 500);

                        }
                    });
                }
            } else {
                ll_fm_notice1.setVisibility(View.VISIBLE);
                rl_notice.setVisibility(View.GONE);
            }
        }
        return view;
    }

    private void getStatusBarHeight() {

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("tag", height + "height---------");

    }
  /*  protected void initStatus() {
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_VISIBLE
            );//隐藏虚拟按键(导航栏)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }

    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).setBackListener(this);
            ((MainActivity) activity).setInterception(true);
        }
    }

    @Override
    public void onBackForward() {
        Log.e("tag", "" + TO_TAG);
        if (TO_TAG == 2) {
            all_select.setVisibility(View.GONE);
            complete.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.GONE);
            TO_TAG = 1;
            adapter = new NoticeAdapter(mContext, list, TO_TAG, handler);
            adapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, String data, int i) {
                    Intent intent = new Intent();
                    //设置传递的参数
                    intent.putExtra("usersid", userId);
                    intent.putExtra("Smsid", data);
                    intent.putExtra("userToken", userToken);
                    //从Activity MainActivity跳转到Activity OActivity
                    intent.setClass(mContext, NoticeCotentActivity.class);
                    startActivity(intent);
                    if (list.get(i).getLook().equals("0")) {
                        EventBus.getDefault().post(new MessageEvent("2"));
                    }
                    list.get(i).setLook("1");
                    adapter.notifyDataSetChanged();
                }
            });
            adapter.setOnDelClickListener(new NoticeAdapter.OnDelClickListener() {
                @Override
                public void OnDelClick(View view, String data, int i) {
                    alone = 1;
                    prarams.clear();
                    removeid = i;
                    prarams.put("usersid", userId);
                    prarams.put("chkID", data);
                    prarams.put("usertoken", userToken);
                    new DeleteMailAsyncTask().execute();
                }
            });
            notice_lv.setAdapter(adapter);
        } else {
            if (System.currentTimeMillis() - lastBackPress < 1000) {
                Log.e("tag", "onBackPressed32");
                getActivity().onBackPressed();//销毁自己
            } else {
                Log.e("tag", "onBackPressed3");
                lastBackPress = System.currentTimeMillis();
                Toast.makeText(mContext, "再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class MyNoticeAsyncTask extends AsyncTask<String, Integer, String> {
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
            String result = AgentApi.dopost3(URL.getInstance().Notice_URL, prarams);
            Log.e("我的消息的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mLoadingAndRetryManager.showContent();
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    NoticeDb noticeDb = gson.fromJson(s, NoticeDb.class);
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (noticeDb.getStatus() == 1) {
                        if (noticeDb.getData() != null && noticeDb.getData().size() > 0) {

                            if (Tag) {
                                list.addAll(noticeDb.getData());
                                Log.e("result", "list=" + list);
                            } else {
                                Log.e("result", "list=" + list.size());
                                for (int i = 0; i < noticeDb.getData().size(); i++) {
                                    list.add(i, noticeDb.getData().get(i));
                                }
                            }
                            the_first_id = list.get(0).getSmsid();
                            the_last_id = list.get((list.size() - 1)).getSmsid();

                            adapter = new NoticeAdapter(mContext, list, TO_TAG, handler);
                            Log.e("tag", "" + list);
                            notice_lv.setAdapter(adapter);
                            mPullRefreshlistView.onRefreshComplete();
                            adapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(View view, String data, int i) {
                                    Intent intent = new Intent();
                                    //设置传递的参数
                                    intent.putExtra("usersid", userId);
                                    intent.putExtra("Smsid", data);
                                    intent.putExtra("userToken", userToken);
                                    //从Activity MainActivity跳转到Activity OActivity
                                    intent.setClass(mContext, NoticeCotentActivity.class);
                                    startActivity(intent);
                                    if (list.get(i).getLook().equals("0")) {
                                        EventBus.getDefault().post(new MessageEvent("2"));
                                    }
                                    list.get(i).setLook("1");
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            adapter.setOnDelClickListener(new NoticeAdapter.OnDelClickListener() {
                                @Override
                                public void OnDelClick(View view, String data, int i) {
                                    alone = 1;
                                    prarams.clear();
                                    removeid = i;
                                    prarams.put("usersid", userId);
                                    prarams.put("chkID", data);
                                    prarams.put("usertoken", userToken);
                                    //position.add(i);
                                    new DeleteMailAsyncTask().execute();
                                }
                            });
                            mLoadingAndRetryManager.showContent();
                        } else {
                            if (Tag) {
                                if (time == 1) {
                                    mLoadingAndRetryManager.showEmpty();
                                } else {
                                    mLoadingAndRetryManager.showContent();
                                    Toast.makeText(mContext, "已经加载到底部了！", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                mLoadingAndRetryManager.showContent();
                                Toast.makeText(mContext, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (noticeDb.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyNoticeAsyncTask());
                    } else if (noticeDb.getStatus() == -97 || noticeDb.getStatus() == -99) {
                        Unlogin.doLogin(mContext);
                    } else {
                        mLoadingAndRetryManager.showContent();
                        Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteMailAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().DelNotice_URL, prarams);
            Log.e("我的删除的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                String re = null;
                String msg = null;
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //第一层解析
                    if (jsonObject != null) {
                        re = jsonObject.optString("status");
                        msg = jsonObject.optString("msg");
                    }

                    if (re.equals("1")) {
                        EventBus.getDefault().post(new MessageEvent("1"));
                        //多选删除啊
                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                        if (alone == 1) {
                            //単删
                            list.remove(removeid);
                            adapter.notifyDataSetChanged();
                        } else if (alone == 2) {
                            //多选删除
                            for (int i = 0; i < position.size(); i++) {
                                NoticeDb.DataBean removeid = list.get(position.get(i));
                                removelist.add(removeid);
                            }
                            Log.e("removelist", "" + removelist);
                            list.removeAll(removelist);
                            TO_TAG = 1;
                            all_select.setVisibility(View.GONE);
                            complete.setVisibility(View.GONE);
                            ll_bottom.setVisibility(View.GONE);
                            adapter = new NoticeAdapter(mContext, list, TO_TAG, handler);
                            notice_lv.setAdapter(adapter);
                            adapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(View view, String data, int i) {
                                    Intent intent = new Intent();
                                    //设置传递的参数
                                    intent.putExtra("usersid", userId);
                                    intent.putExtra("Smsid", data);
                                    intent.putExtra("userToken", userToken);
                                    //从Activity MainActivity跳转到Activity OActivity
                                    intent.setClass(mContext, NoticeCotentActivity.class);
                                    startActivity(intent);
                                    if (list.get(i).getLook().equals("0")) {
                                        EventBus.getDefault().post(new MessageEvent("2"));
                                    }
                                    list.get(i).setLook("1");
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            adapter.setOnDelClickListener(new NoticeAdapter.OnDelClickListener() {
                                @Override
                                public void OnDelClick(View view, String data, int i) {
                                    alone = 1;
                                    prarams.clear();
                                    removeid = i;
                                    prarams.put("usersid", userId);
                                    prarams.put("chkID", data);
                                    prarams.put("usertoken", userToken);
                                    //position.add(i);
                                    new DeleteMailAsyncTask().execute();
                                }
                            });

                        }
                        if (list.size() <= 0) {
                            mLoadingAndRetryManager.showEmpty();
                        }
                    } else if (re.equals("99")) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new DeleteMailAsyncTask());
                    } else if (re.equals("-99") || re.equals("-97")) {
                        Unlogin.doLogin(mContext);
                    }else{
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class MyONclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_login://登录
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("where", "5");
                    startActivity(intent);
                    break;
                case R.id.complete:
                    all_select.setVisibility(View.GONE);
                    complete.setVisibility(View.GONE);
                    ll_bottom.setVisibility(View.GONE);
                    TO_TAG = 1;
                    adapter = new NoticeAdapter(mContext, list, TO_TAG, handler);
                    adapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, String data, int i) {
                            Intent intent = new Intent();
                            //设置传递的参数
                            intent.putExtra("usersid", userId);
                            intent.putExtra("Smsid", data);
                            intent.putExtra("userToken", userToken);
                            //从Activity MainActivity跳转到Activity OActivity
                            intent.setClass(mContext, NoticeCotentActivity.class);
                            startActivity(intent);
                            if (list.get(i).getLook().equals("0")) {
                                EventBus.getDefault().post(new MessageEvent("2"));
                            }
                            list.get(i).setLook("1");
                            adapter.notifyDataSetChanged();
                        }
                    });
                    adapter.setOnDelClickListener(new NoticeAdapter.OnDelClickListener() {
                        @Override
                        public void OnDelClick(View view, String data, int i) {
                            alone = 1;
                            prarams.clear();
                            removeid = i;
                            prarams.put("usersid", userId);
                            prarams.put("chkID", data);
                            prarams.put("usertoken", userToken);
                            new DeleteMailAsyncTask().execute();
                        }
                    });
                    notice_lv.setAdapter(adapter);
                    break;
                case R.id.ll_delete:
                    alone = 2;
                    position.clear();
                    adapter.SetPL(new NoticeAdapter.SetParams() {
                        @Override
                        public void setParams(List<Integer> mposition) {
                            position = mposition;
                            initData(mposition);
                            new DeleteMailAsyncTask().execute();
                        }
                    });
                    break;
                case R.id.set_read:
                    alone = 2;
                    position.clear();
                    adapter.SetPL(new NoticeAdapter.SetParams() {
                        @Override
                        public void setParams(List<Integer> mposition) {
                            position = mposition;
                            Log.e("position", "" + position);
                            initData(mposition);
                            new SetReadAsyncTask().execute();
                        }
                    });
                    break;

                case R.id.all_select:
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setFlag(true);
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SetReadAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().RedNotice_URL, prarams);
            Log.e("我的已读的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                String re = null;
                String msg = null;
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //第一层解析
                    if (jsonObject != null) {
                        re = jsonObject.optString("status");
                        msg= jsonObject.optString("msg");
                    }

                    if (re.equals("1")) {
                        EventBus.getDefault().post(new MessageEvent("1"));
                        //多选删除啊
                        Toast.makeText(mContext, "设置成功", Toast.LENGTH_SHORT).show();
                        //多选删除
                        for (int i = 0; i < position.size(); i++) {
                            list.get(position.get(i)).setLook("1");
                        }
                        TO_TAG = 1;
                        all_select.setVisibility(View.GONE);
                        complete.setVisibility(View.GONE);
                        ll_bottom.setVisibility(View.GONE);
                        adapter = new NoticeAdapter(mContext, list, TO_TAG, handler);
                        notice_lv.setAdapter(adapter);
                        adapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
                            @Override
                            public void OnItemClick(View view, String data, int i) {
                                Intent intent = new Intent();
                                //设置传递的参数
                                intent.putExtra("usersid", userId);
                                intent.putExtra("Smsid", data);
                                intent.putExtra("userToken", userToken);
                                //从Activity MainActivity跳转到Activity OActivity
                                intent.setClass(mContext, NoticeCotentActivity.class);
                                startActivity(intent);
                                if (list.get(i).getLook().equals("0")) {
                                    EventBus.getDefault().post(new MessageEvent("2"));
                                }
                                list.get(i).setLook("1");
                                adapter.notifyDataSetChanged();
                            }
                        });
                        adapter.setOnDelClickListener(new NoticeAdapter.OnDelClickListener() {
                            @Override
                            public void OnDelClick(View view, String data, int i) {
                                alone = 1;
                                prarams.clear();
                                removeid = i;
                                prarams.put("usersid", userId);
                                prarams.put("chkID", data);
                                prarams.put("usertoken", userToken);
                                //position.add(i);
                                new DeleteMailAsyncTask().execute();
                            }
                        });


                    } else if (re.equals("99")) {
                        Unlogin.doAtk(prarams,s,new DeleteMailAsyncTask());
                    } else if (re.equals("-99") || re.equals("-97")) {
                        Unlogin.doLogin(mContext);
                    }else{
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void initData(List<Integer> mposition) {
        sb = new StringBuilder();
        for (int j = 0; j < mposition.size(); j++) {
            sb.append(list.get(mposition.get(j)).getSmsid());
            sb.append(',');
            Log.e("sb", "" + sb);
        }
        if (sb.length() > 0) {
            Log.e("sb.length()", "" + sb.length());
            sb.deleteCharAt(sb.length() - 1);
        }
        prarams.clear();
        prarams.put("usersid", userId);
        prarams.put("chkID", sb.toString());
        prarams.put("usertoken", userToken);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("tag", "" + hidden);
        if (!hidden) {
            //   initStatus();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
            if (sharedPreferences.getString("is_login", "").equals("true")) {
                userId = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
                userToken = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
                if (userToken == null || userId == null) {
                    ll_fm_notice1.setVisibility(View.VISIBLE);
                    rl_notice.setVisibility(View.GONE);
                }
            } else {
                ll_fm_notice1.setVisibility(View.VISIBLE);
                rl_notice.setVisibility(View.GONE);
            }
        }
    }


}

