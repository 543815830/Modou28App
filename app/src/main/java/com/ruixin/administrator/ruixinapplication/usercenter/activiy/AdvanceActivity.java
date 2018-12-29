package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.popwindow.PopMenu4;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.AdvanceAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.AdvanceDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 闯关奖励activity
 */
public class AdvanceActivity extends Activity {
    AdvanceAdapter adapter;
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.record_type)
    TextView recordType;
    @BindView(R.id.lv_advance)
    PullToRefreshListView mPullRefreshlistView;
    ListView lvAdvance;
    @BindView(R.id.commit)
    Button commit;
    PopMenu4 popmenu;
    boolean tag = true;
    boolean has = false;
    boolean first = true;
    boolean first2 = true;
    @BindView(R.id.ll_advance)
    LinearLayout ll_advance;
    @BindView(R.id.rl_advance)
    RelativeLayout rlAdvance;
    private String the_first_id;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private HashMap<String, String> geprarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    int page = 1;
    String userId = "", userToken = "";
    public static int RESULT_CODE = 56;
    private int mtPage;//总页数
    List<AdvanceDb.DataBean> list = new ArrayList<>();
    List<AdvanceDb.ListBean> typelist = new ArrayList<>();
    String tbChk;
    String tbNum;
    StringBuilder sb;
    StringBuilder nb;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        geprarams.put("usersid", userId);
        geprarams.put("usertoken", userToken);
        ButterKnife.bind(this);
        initStatus();
        initView();
    }

    private void initView() {
        tvTitle.setText("闯关奖励");
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(ll_advance, null);
        lvAdvance = mPullRefreshlistView.getRefreshableView();
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new MyAdvanceAsyncTask().execute();
        Intent intent = new Intent();
        setResult(RESULT_CODE, intent);
        mPullRefreshlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //得到当前刷新的时间
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                //设置更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                list.clear();
                new MyAdvanceAsyncTask().execute();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });


        recordType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(popmenu!=null){
                    popmenu.SetPL(new PopMenu4.Setname() {
                        @Override
                        public void setParams(String name) {
                            Log.e("name", name);
                            prarams.clear();
                            prarams.put("usersid", userId);
                            prarams.put("usertoken", userToken);
                            if(!name.equals("")){
                                prarams.put("gamename", name);
                            }
                            list.clear();
                            first=true;
                            new MyAdvanceAsyncTask().execute();
                        }
                    });
                }

            }
        });

    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick({R.id.back_arrow, R.id.record_type, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.record_type:
                if (first2) {
                    typelist.add(0, new AdvanceDb.ListBean(null, "全部游戏"));
                    first2 = false;
                }
                showPop();
                break;
            case R.id.commit:
                adapter.SetPL(new AdvanceAdapter.SetParams() {
                    @Override
                    public void setParams(List<Integer> mposition) {
                        for (int i = 0; i < mposition.size(); i++) {
                            Log.e("i", "" + mposition.get(i));
                            initData(mposition);

                        }
                    }
                });
                break;
        }
    }

    private void initData(List<Integer> mposition) {
        sb = new StringBuilder();
        for (int j = 0; j < mposition.size(); j++) {
            sb.append(list.get(mposition.get(j)).getGamename());
            sb.append('=');
            sb.append("on");
            sb.append(",");
            Log.e("sb", "" + sb);
        }
        if (sb.length() > 0) {
            Log.e("sb.length()", "" + sb.length());
            sb.deleteCharAt(sb.length() - 1);
        }
        nb = new StringBuilder();
        for (int j = 0; j < mposition.size(); j++) {
            nb.append(list.get(mposition.get(j)).getGamename());
            nb.append('=');
            nb.append(list.get(mposition.get(j)).getJlmoney());
            nb.append("|");
            nb.append(list.get(mposition.get(j)).getCgnum());
            nb.append(",");
        }
        if (nb.length() > 0) {
            Log.e("nb.length()", "" + nb.length());
            nb.deleteCharAt(nb.length() - 1);
        }
        tbChk = sb.toString();
        tbNum = nb.toString();
        geprarams.put("tbchk", tbChk);
        geprarams.put("tbnum", tbNum);
        new GetRewardAsyncTask().execute();
    }

    private void showPop() {
        if (popmenu == null) {
            popmenu = new PopMenu4(AdvanceActivity.this, typelist, recordType);
        }
        //监听窗口的焦点事件，点击窗口外面则取消显示
        popmenu.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    popmenu.dismiss();

                }
            }
        });
        //设置默认获取焦点
        popmenu.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        // mymodePop.showAsDropDown(tvStartMode, 0, 0);
        popmenu.showAtLocation(rlAdvance, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //如果窗口存在，则更新
        popmenu.update();

    }

    /*获取活动列表*/
    private class MyAdvanceAsyncTask extends AsyncTask<String, Integer, String> {
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
            String result = AgentApi.dopost3(URL.getInstance().AdvanceRecord_URL, prarams);
            Log.e("result", "result=" + result);
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
                        AdvanceDb advanceDb = gson.fromJson(s, AdvanceDb.class);
                        typelist = advanceDb.getList();
                        if (advanceDb.getData() != null && advanceDb.getData().size() > 0) {
                            list.addAll(advanceDb.getData());
                            mPullRefreshlistView.onRefreshComplete();
                            adapter = new AdvanceAdapter(AdvanceActivity.this, list);
                            lvAdvance.setAdapter(adapter);
                            mPullRefreshlistView.onRefreshComplete();
                            mLoadingAndRetryManager.showContent();
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getGetprize() == 1) {
                                    has = true;
                                }
                            }
                            Log.e("tag", "" + has);
                            if (has) {
                                commit.setEnabled(true);
                            } else {
                                commit.setEnabled(false);
                                commit.setText("不可领取");
                            }

                        } else {
                            mLoadingAndRetryManager.showEmpty();
                            Toast.makeText(AdvanceActivity.this, "数据为空", Toast.LENGTH_SHORT).show();
                        }
                    } else if (entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyAdvanceAsyncTask());
                    }else if(entry.getStatus()==-97||entry.getStatus()==-99){
                        Unlogin.doLogin(AdvanceActivity.this);
                    }else {
                        Toast.makeText(AdvanceActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(AdvanceActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(AdvanceActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
            mPullRefreshlistView.onRefreshComplete();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetRewardAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().AdvanceReward_URL, geprarams);
            Log.e("数据返回", "" + result);
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
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        user = gson.fromJson(s, User.class);
                        Toast.makeText(AdvanceActivity.this, "领取成功！", Toast.LENGTH_SHORT).show();
                       /* Intent intent = new Intent();
                        setResult(RESULT_CODE, intent);*/
                        EventBus.getDefault().post(new MessageEvent("3"));
                        has = false;
                        list.clear();
                        new MyAdvanceAsyncTask().execute();
                    } else if (entry.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(geprarams,s,new GetRewardAsyncTask());
                    } else if (entry.getStatus() == -99 || entry.getStatus() == -97) {
                        Unlogin.doLogin(AdvanceActivity.this);
                    } else {
                        Toast.makeText(AdvanceActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(AdvanceActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AdvanceActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
