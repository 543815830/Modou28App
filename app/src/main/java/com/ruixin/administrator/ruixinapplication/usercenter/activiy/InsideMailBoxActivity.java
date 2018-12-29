package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MailBoxAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MBMessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MailBox;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
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
 * 站内信箱界面
 */
public class InsideMailBoxActivity extends Activity {
    private int TO_TAG = 1;
    TextView tv_title;//标题
    LinearLayout back_arrow;//返回
    Button bt_delete;//删除
    int tag = 1;
    String userId = "", userToken = "";
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private ListView lv_inside_mailbox;
    private MailBoxAdapter adapter;
    private List<MailBox.DataBean> list = new ArrayList<>();
    private List<MailBox.DataBean> removelist = new ArrayList<>();
    private PullToRefreshListView mPullRefreshlistView;
    boolean Tag = true;
    boolean first = true;
    public static int RESULT_CODE = 88;
    int time = 1;//判断是第一次进来数据为空还是上拉加载数据为空
    int T = 1;//判断是否多选删除后的重新初始化
    private String the_first_id;//最新id
    private String the_last_id;//最后一条id
    int removeid = 0;
    List<Integer> position = new ArrayList<>();
    LoadingAndRetryManager mLoadingAndRetryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_mail_box);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        initStatus();
        if (userId.equals("")) {
           Toast.makeText(InsideMailBoxActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(InsideMailBoxActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        } else {
            initView();
        }
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("站内信箱");
        back_arrow = findViewById(R.id.back_arrow);
        bt_delete = findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(new MyOnClick());
        back_arrow.setOnClickListener(new MyOnClick());
        mPullRefreshlistView = findViewById(R.id.lv_inside_mailbox);
        lv_inside_mailbox = mPullRefreshlistView.getRefreshableView();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(lv_inside_mailbox, null);
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new MyMailAsyncTask().execute();
        mPullRefreshlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //得到当前刷新的时间
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                //设置更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                Tag = false;
                prarams.clear();
                prarams.put("usersid", userId);
                prarams.put("usertoken", userToken);
                prarams.put("firstid", "" + the_first_id);
                new MyMailAsyncTask().execute();
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
                new MyMailAsyncTask().execute();
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

    @SuppressLint("StaticFieldLeak")
    private class MyMailAsyncTask extends AsyncTask<String, Integer, String> {
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
            String result = AgentApi.dopost3(URL.getInstance().MailBox_URL, prarams);
            Log.e("我的站内信箱的数据返回", "" + result);
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
                    MailBox mailDb = gson.fromJson(s, MailBox.class);
                    if (mailDb.getStatus() == 1) {
                        if (mailDb.getData() != null && mailDb.getData().size() > 0) {
                            if (Tag) {
                                list.addAll(mailDb.getData());
                            } else {
                                for (int i = 0; i < mailDb.getData().size(); i++) {
                                    list.add(i, mailDb.getData().get(i));
                                }
                            }
                            the_first_id = list.get(0).getSmsid();
                            the_last_id = list.get((list.size() - 1)).getSmsid();

                            adapter = new MailBoxAdapter(InsideMailBoxActivity.this, list, TO_TAG, prarams, position);
                            Log.e("tag", "" + list);
                            lv_inside_mailbox.setAdapter(adapter);
                            mPullRefreshlistView.onRefreshComplete();
                            if (Tag) {
                                int n = list.size() - mailDb.getData().size();
                                lv_inside_mailbox.setSelection(n);
                            } else {
                                lv_inside_mailbox.setSelection(0);
                            }
                            adapter.setOnItemClickListener(new MailBoxAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(View view, String data, int i) {
                                    if(list.get(i).getLook().equals("0")){
                                        EventBus.getDefault().post(new MBMessageEvent("1"));
                                    }
                                    Intent intent = new Intent();
                                    //设置传递的参数
                                    intent.putExtra("usersid", userId);
                                    intent.putExtra("Smsid", data);
                                    intent.putExtra("userToken", userToken);
                                    //从Activity MainActivity跳转到Activity OActivity
                                    intent.setClass(InsideMailBoxActivity.this, SmsContentActivity.class);
                                    startActivity(intent);
                                    list.get(i).setLook("1");
                                    adapter.notifyDataSetChanged();

                                }
                            });
                            adapter.setOnDelClickListener(new MailBoxAdapter.OnDelClickListener() {
                                @Override
                                public void OnDelClick(View view, String data, int i) {
                                    prarams.clear();
                                    prarams.put("usersid", userId);
                                    prarams.put("chkID", data);
                                    prarams.put("usertoken", userToken);
                                    position.add(i);
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
                                   Toast.makeText(InsideMailBoxActivity.this, "已经加载到底部了！", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                mLoadingAndRetryManager.showContent();
                               Toast.makeText(InsideMailBoxActivity.this, "已刷新为最新数据", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else if (mailDb.getStatus() ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyMailAsyncTask());
                    }else if(mailDb.getStatus()==-97||mailDb.getStatus()==-99){
                        Unlogin.doLogin(InsideMailBoxActivity.this);
                    } else{
                        finish();
                       Toast.makeText(InsideMailBoxActivity.this, mailDb.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(InsideMailBoxActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(InsideMailBoxActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class MyOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back_arrow:
                    finish();
                    break;
                case R.id.bt_delete:
                    if(list.size()>0){
                        if (bt_delete.getText().toString().equals("多选")) {
                            bt_delete.setText("删除");
                            TO_TAG = 2;
                            adapter = new MailBoxAdapter(InsideMailBoxActivity.this, list, TO_TAG, prarams, position);
                            adapter.setOnItemClickListener(new MailBoxAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(View view, String data, int i) {
                                    Log.e("tag","OnItemClick");
                                    if(list.get(i).getLook().equals("0")){
                                        EventBus.getDefault().post(new MBMessageEvent("1"));
                                    }
                                    Intent intent = new Intent();
                                    //设置传递的参数
                                    intent.putExtra("usersid", userId);
                                    intent.putExtra("Smsid", data);
                                    intent.putExtra("userToken", userToken);
                                    //从Activity MainActivity跳转到Activity OActivity
                                    intent.setClass(InsideMailBoxActivity.this, SmsContentActivity.class);
                                    startActivity(intent);
                                    list.get(i).setLook("1");
                                    adapter.notifyDataSetChanged();

                                }
                            });
                            lv_inside_mailbox.setAdapter(adapter);
                        } else {
                            if(prarams.get("chkID")==null||prarams.get("chkID").equals("")){
                                Toast.makeText(InsideMailBoxActivity.this, "请至少选择一项！", Toast.LENGTH_SHORT).show();
                            }else{
                                AlertDialog dialog = new AlertDialog.Builder(InsideMailBoxActivity.this)
                                        .setTitle("温馨提示")//设置对话框的标题
                                        .setMessage("确定要删除吗？")//设置对话框的内容
                                        //设置对话框的按钮
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                new DeleteMailAsyncTask().execute();
                                                dialog.dismiss();

                                            }
                                        }).create();
                                dialog.show();
                            }
                        }

                    }else{
                        Toast.makeText(InsideMailBoxActivity.this, "站内信箱为空！", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteMailAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().DelMailBox_URL, prarams);
            Log.e("我的删除的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                String re = null;
                String msg=null;
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    removeDuplicate(position);
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
                        //多选删除啊
                       Toast.makeText(InsideMailBoxActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        if (bt_delete.getText().toString().equals("删除")) {
                            for (int i = 0; i < position.size(); i++) {
                                MailBox.DataBean removeid = list.get(position.get(i));
                                removelist.add(removeid);
                            }
                            Log.e("removelist", "" + removelist);
                            list.removeAll(removelist);
                            TO_TAG = 1;
                            position.clear();
                            bt_delete.setText("多选");
                            adapter = new MailBoxAdapter(InsideMailBoxActivity.this, list, TO_TAG, prarams, position);
                            lv_inside_mailbox.setAdapter(adapter);
                            adapter.setOnItemClickListener(new MailBoxAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(View view, String data, int i) {
                                    if(list.get(i).getLook().equals("0")){
                                        EventBus.getDefault().post(new MBMessageEvent("1"));
                                    }
                                    Intent intent = new Intent();
                                    //设置传递的参数
                                    intent.putExtra("usersid", userId);
                                    intent.putExtra("Smsid", data);
                                    intent.putExtra("userToken", userToken);
                                    //从Activity MainActivity跳转到Activity OActivity
                                    intent.setClass(InsideMailBoxActivity.this, SmsContentActivity.class);
                                    startActivity(intent);
                                    list.get(i).setLook("1");
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            adapter.setOnDelClickListener(new MailBoxAdapter.OnDelClickListener() {
                                @Override
                                public void OnDelClick(View view, String data, int i) {
                                    prarams.clear();
                                    prarams.put("usersid", userId);
                                    prarams.put("chkID", data);
                                    prarams.put("usertoken", userToken);
                                    position.add(i);
                                    new DeleteMailAsyncTask().execute();
                                }
                            });
                        } else {
                            // 单选删除

                            Log.e("position", "" + position);
                            for (int i = 0; i < position.size(); i++) {
                                Log.e("position", "" + position.get(i));
                                removeid = position.get(i);
                                list.remove(removeid);
                            }
                            adapter.notifyDataSetChanged();
                            position.clear();
                            lv_inside_mailbox.invalidate();
                        }
                        if (list.size() <= 0) {
                            mLoadingAndRetryManager.showEmpty();
                        }

                    }else if (re.equals("99")) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new DeleteMailAsyncTask());
                    }else if(re.equals("-99")||re.equals("-97")){
                        Unlogin.doLogin(InsideMailBoxActivity.this);
                    }else{
                        Toast.makeText(InsideMailBoxActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(InsideMailBoxActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(InsideMailBoxActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public static void removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        System.out.println(list);
    }
    /*监听返回键*/

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (TO_TAG == 1) {
                finish();
            } else {
                bt_delete.setText("多选");
                TO_TAG = 1;
                adapter = new MailBoxAdapter(InsideMailBoxActivity.this, list, TO_TAG, prarams, position);
                lv_inside_mailbox.setAdapter(adapter);
                adapter.setOnItemClickListener(new MailBoxAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, String data, int i) {
                        if(list.get(i).getLook().equals("0")){
                            EventBus.getDefault().post(new MBMessageEvent("1"));
                        }
                        Intent intent = new Intent();
                        //设置传递的参数
                        intent.putExtra("usersid", userId);
                        intent.putExtra("Smsid", data);
                        intent.putExtra("userToken", userToken);
                        //从Activity MainActivity跳转到Activity OActivity
                        intent.setClass(InsideMailBoxActivity.this, SmsContentActivity.class);
                        startActivity(intent);
                        list.get(i).setLook("1");
                        adapter.notifyDataSetChanged();
                    }
                });
                adapter.setOnDelClickListener(new MailBoxAdapter.OnDelClickListener() {
                    @Override
                    public void OnDelClick(View view, String data, int i) {
                        prarams.clear();
                        prarams.put("usersid", userId);
                        prarams.put("chkID", data);
                        prarams.put("usertoken", userToken);
                        position.add(i);
                        new DeleteMailAsyncTask().execute();
                    }
                });
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
