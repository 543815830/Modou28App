package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MybindBnakdAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.GoldDepositDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MyCardDb;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.BindBankFragent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 我的收款账号界面
 */
public class MyBindbankActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_my_bank)
    ListView lv_my_bank;
    MybindBnakdAdapter adapter;
    String userId,userToken;
    String url;
    String url2;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    List<MyCardDb.DataBean> cardList=new ArrayList<>();
    boolean first=true;
    int position;
    LoadingAndRetryManager mLoadingAndRetryManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bindbank);
        ButterKnife.bind(this);
        initStatus();
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        tvTitle.setText("我的收款账号");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        url= URL.getInstance().deposit_info+"?act=mycard";
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(lv_my_bank, null);
        new GoldDepositeAsyncTask().execute();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    private class GoldDepositeAsyncTask extends AsyncTask<String, Integer, String> {
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
            String result= AgentApi.dopost3(url,prarams);
            Log.e("金币提现的数据返回",""+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    mLoadingAndRetryManager.showContent();
                    Gson gson=new Gson();
                  MyCardDb myCardDb=gson.fromJson(s,MyCardDb.class);
                    if(myCardDb.getStatus()==1) {
                        cardList=myCardDb.getData();
                        Log.e("cardList",""+cardList);
                            if(myCardDb.getData()!=null&&myCardDb.getData().size()>0){
                                adapter=new MybindBnakdAdapter(MyBindbankActivity.this,cardList);
                                lv_my_bank.setAdapter(adapter);
                                adapter.setOnUpClickListener(new MybindBnakdAdapter.OnUpClickListener() {
                                    @Override
                                    public void OnUpClick(View view, String data, int i) {
                                        Intent intent = new Intent(MyBindbankActivity.this,UpdateMybankActivity.class);
                                        intent.putExtra("userId", userId);
                                        intent.putExtra("userToken", userToken);
                                        intent.putExtra("id", cardList.get(i).getId());
                                        intent.putExtra("typename", cardList.get(i).getTypename());
                                        intent.putExtra("cardname", cardList.get(i).getCardname());
                                        intent.putExtra("realname", cardList.get(i).getRealname());
                                        startActivityForResult(intent,11);
                                    }
                                });
                                adapter.setOnDelClickListener(new MybindBnakdAdapter.OnDelClickListener() {
                                    @Override
                                    public void OnDelClick(View view, String data, int i) {
                                        url2= URL.getInstance().deposit_info+"?act=del";
                                        prarams.put("id", data);
                                        position=i;
                                        new DGoldDepositeAsyncTask().execute();

                                    }
                                });

                            } else{
                                mLoadingAndRetryManager.showEmpty();
                            Toast.makeText(MyBindbankActivity.this, "您尚未绑定收款账号！", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    else if (myCardDb.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,   new GoldDepositeAsyncTask());
                        //  new MyTrusteeAsyncTask().execute();
                    } else if(myCardDb.getStatus()==-97||myCardDb.getStatus()==-99){
                       Unlogin.doLogin(MyBindbankActivity.this);
                    }else{
                        Entry entry=gson.fromJson(s,Entry.class);
                        Toast.makeText(MyBindbankActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(MyBindbankActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(MyBindbankActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }
    private class DGoldDepositeAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(url2,prarams);
            Log.e("删除的数据返回",""+result);
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
                  MyCardDb myCardDb=gson.fromJson(s,MyCardDb.class);
                    if(myCardDb.getStatus()==1) {
                        Toast.makeText(MyBindbankActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        cardList.remove(position);
                        adapter.notifyDataSetChanged();
                        if(cardList.size()==0){
                            mLoadingAndRetryManager.showEmpty();
                        }

                    }
                    else if (myCardDb.getStatus()==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,   new GoldDepositeAsyncTask());
                        //  new MyTrusteeAsyncTask().execute();
                    } else if(myCardDb.getStatus()==-97||myCardDb.getStatus()==-99){
                       Unlogin.doLogin(MyBindbankActivity.this);
                    }else{
                        Entry entry=gson.fromJson(s,Entry.class);
                        Toast.makeText(MyBindbankActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(MyBindbankActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(MyBindbankActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==UpdateMybankActivity.Result_code){
            new GoldDepositeAsyncTask().execute();
        }
    }
}
