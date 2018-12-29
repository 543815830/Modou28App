package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.domain.EntryDb;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.RedbagAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.GetRedbagDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.GetRedbagDb2;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.SelfDialog;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 *幸运红包界面
 */
public class RedbagActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.user_head)
    ImageView userHead;
    @BindView(R.id.lv_red_bag)
    ListView lvRedBag;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    RedbagAdapter adapter;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_user_sendpoints)
    TextView tvUserSendpoints;
    private float lastY;
    String clip;
    private SelfDialog selfDialog;
    String userId, userToken;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    String url;
    String hbid;
    String path;
    boolean tag=true;//判断是直接领取还是领取完毕调用接口
List<GetRedbagDb2.DataBean.ListBean> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redbag);
        ButterKnife.bind(this);
        userId = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
        userToken = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");;
        if (userId == null || userToken == null||userId.equals("")||userToken.equals("")) {
            Intent intent = new Intent(RedbagActivity.this, LoginActivity.class);
            intent.putExtra("where", "2");
            startActivity(intent);
        } else {

            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            initStatus();
            initView();
            Intent intent = getIntent();
            clip = intent.getStringExtra("clip");
            if (clip != null) {
                Log.e("Alex", "clip:" + clip);
                url = RuiXinApplication.getInstance().getUrl()+"app/User_RedPackGet_App.php?hbid=" + clip;
                tag=true;
                new GetBagAsyncTask().execute();
            }
            String action = intent.getAction();
            if (Intent.ACTION_VIEW.equals(action)) {
                Uri uri = intent.getData();
                if (uri != null) {
                    String host = uri.getHost();
                    Log.e("Alex", "host:" + host);
                    String dataString = intent.getDataString();
                    //  String id = uri.getQueryParameter("id");
                    hbid = uri.getQuery();
                   /* if (hbid.contains("=")) {
                      int index=hbid.indexOf("=");
                      hbid=hbid.substring(index,hbid.length());
                       // hbid = hbid.replaceAll("/", "");
                    }*/
                    //  String path1 = uri.getEncodedPath();
                    //   String queryString = uri.getQuery();
                    Log.e("Alex", "host:" + host);
                    Log.e("Alex", "dataString:" + dataString);
                    //  Log.e("Alex", "id:" + id);
                    Log.e("Alex", "hbid:" + hbid);
                    //  Log.e("Alex", "path1:" + path1);
                    //  Log.e("Alex", "queryString:" + queryString);
                    url = RuiXinApplication.getInstance().getUrl()+"app/User_RedPackGet_App.php?" + hbid;
                    tag=true;
                    new GetBagAsyncTask().execute();
                }
            }

        }

    }

    private class GetBagAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(url, prarams);
            Log.e("获取红包数据返回", "" + result);
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
                    EntryDb entryDb = gson.fromJson(s, EntryDb.class);
                    if (entryDb.getStatus() == 2) {
                        GetRedbagDb getRedbagDb = gson.fromJson(s, GetRedbagDb.class);
                        selfDialog = new SelfDialog(RedbagActivity.this);
                        selfDialog.setMessage("" + getRedbagDb.getData().getPoints());
                        selfDialog.show();
                        selfDialog.setYesOnclickListener(new SelfDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                Log.e("tag", "onYesClick");
                                selfDialog.dismiss();
                                tag=false;
                                new GetBagAsyncTask().execute();
                                EventBus.getDefault().post(new MessageEvent("3"));
                            }
                        });

                    } else if (entryDb.getStatus() == 3) {
                        if(tag){
                           Toast.makeText(RedbagActivity.this, entryDb.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        GetRedbagDb2 getRedbagDb2 = gson.fromJson(s, GetRedbagDb2.class);
                        tvUsername.setText(""+getRedbagDb2.getData().getPackInfo().getSenderName());
                        tvUserSendpoints.setText(""+getRedbagDb2.getData().getPackInfo().getPoints());
                        if(!getRedbagDb2.getData().getPackInfo().getHead().contains("http")){
                            path=RuiXinApplication.getInstance().getUrl()+getRedbagDb2.getData().getPackInfo().getHead();
                        }else{
                            path=getRedbagDb2.getData().getPackInfo().getHead();
                        }

                        Log.e("tag","u-----"+path);
                        Glide.with(RedbagActivity.this)
                                .load(path)
                                .bitmapTransform(new CropCircleTransformation(RedbagActivity.this))
                                .placeholder(R.drawable.iv_user) //占位图
                                .error(R.drawable.iv_user)  //出错的占位图
                                .into(userHead);
                        if(getRedbagDb2.getData().getList()!=null&&getRedbagDb2.getData().getList().size()>0){
                            list=getRedbagDb2.getData().getList();
                            adapter = new RedbagAdapter(RedbagActivity.this,list, (String) getRedbagDb2.getData().getLuckmanID());
                            lvRedBag.setAdapter(adapter);
                        }

                    } else if (entryDb.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new GetBagAsyncTask());
                    } else if (entryDb.getStatus() == -97 || entryDb.getStatus() == -99) {
                        Unlogin.doLogin(RedbagActivity.this);
                    } else{
                       Toast.makeText(RedbagActivity.this, entryDb.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(RedbagActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(RedbagActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
backArrow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});
        lvRedBag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        lvRedBag.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        lvRedBag.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusredColor));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
