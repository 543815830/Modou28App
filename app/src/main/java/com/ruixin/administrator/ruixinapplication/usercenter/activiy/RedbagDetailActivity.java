package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MyredbagRecordAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MyredbagRecordDetailAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MybagDetailDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.RedbagrecordDb;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.MyredbagrecordFragment;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.SetHight;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * 红包详情界面
 */
public class RedbagDetailActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_share_redbag)
    TextView tvShareRedbag;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_coins_total)
    TextView tvCoinsTotal;
    @BindView(R.id.tv_red_status)
    TextView tvRedStatus;
    @BindView(R.id.tv_get_users)
    TextView tvGetUsers;
    @BindView(R.id.tv_time_red)
    TextView tvTimeRed;
    @BindView(R.id.lv_getred)
    ListView lvGetred;
    MyredbagRecordDetailAdapter adapter;
    @BindView(R.id.mscroll)
    ScrollView mscroll;
    String userId, userToken, hbid,type;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    @BindView(R.id.ll_bagdetail)
    LinearLayout llBagdetail;
    String result, msg;
    RedbagrecordDb.DataBean mybag;
    List<MybagDetailDb.DataBean.ListBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redbag_detail);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userid");
        userToken = getIntent().getStringExtra("usertoken");
        hbid = getIntent().getStringExtra("hbid");
        type = getIntent().getStringExtra("type");
        mybag = (RedbagrecordDb.DataBean) getIntent().getSerializableExtra("mybag");
        initStatus();
        initView();
    }

    private void initView() {
        tvTitle.setText("红包详情");
        if(type.equals("0")){
            tvBack.setVisibility(View.VISIBLE);
        }else{
            tvBack.setVisibility(View.GONE);
        }
        tvCoinsTotal.setText(mybag.getRest() + "/" + mybag.getPoints());
        if(mybag.getState()!=null){
            if (mybag.getState().equals("1")) {
                tvRedStatus.setText("状态：领取中");
            } else if (mybag.getState().equals("2")) {
                tvRedStatus.setText("状态：领取完毕");
            } else if (mybag.getState().equals("3")) {
                tvRedStatus.setText("状态：已返还");
            }
        }else{
            tvRedStatus.setText(mybag.getState());
        }

        tvGetUsers.setText("领取人数:" + mybag.getGet() + "/" + mybag.getSum());
        tvTimeRed.setText(mybag.getTime());
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(lvGetred, null);
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        prarams.put("hbid", hbid);
        new MyRedbagDetailAsyncTask().execute();

    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick({R.id.back_arrow, R.id.tv_share_redbag, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.tv_share_redbag:
                showShare(hbid);
                break;
            case R.id.tv_back:
                new BackbagAsyncTask().execute();
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class MyRedbagDetailAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            result = AgentApi.dopost3(URL.getInstance().Mydetailbag_URL, prarams);
            Log.e("我的红包详情的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    mLoadingAndRetryManager.showContent();
                    Gson gson = new Gson();
                    MybagDetailDb mybagDetailDb = gson.fromJson(s, MybagDetailDb.class);
                    if (mybagDetailDb.getStatus() == 1) {
                        if (mybagDetailDb.getData().getList() != null && mybagDetailDb.getData().getList().size() > 0) {
                            mLoadingAndRetryManager.showContent();
                            list = mybagDetailDb.getData().getList();
                            adapter = new MyredbagRecordDetailAdapter(RedbagDetailActivity.this, list);
                            lvGetred.setAdapter(adapter);
                            lvGetred.setFocusable(false);
                            SetHight.setListViewHeightBasedOnChildren(lvGetred);
                        } else {
                            mLoadingAndRetryManager.showEmpty();
                        }
                    } else if (mybagDetailDb.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new MyRedbagDetailAsyncTask());
                    } else if (mybagDetailDb.getStatus() == -97 || mybagDetailDb.getStatus() == -99) {
                        Unlogin.doLogin(RedbagDetailActivity.this);
                    } else {
                       Toast.makeText(RedbagDetailActivity.this, mybagDetailDb.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    mLoadingAndRetryManager.showContent();
                   Toast.makeText(RedbagDetailActivity.this, "服务器出错啦,请反馈给管理员哦！", Toast.LENGTH_SHORT).show();
                }
            } else {
                mLoadingAndRetryManager.showEmpty();
               Toast.makeText(RedbagDetailActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
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
        // oks.setTitleUrl("http://sharesdk.cn");
        oks.setTitleUrl(RuiXinApplication.getInstance().getUrl()+"RedPack/"+data);
        // text是分享文本，所有平台都需要这个字段
        String text = "【红包来啦】"+ RuiXinApplication.getInstance().getUrl()+"RedPack/"+data;
        Log.e("text",""+text);
        //Log.e("tag")
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath(path);//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        // oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        // oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(RedbagDetailActivity.this);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
               Toast.makeText(RedbagDetailActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
               Toast.makeText(RedbagDetailActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
               Toast.makeText(RedbagDetailActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
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
                   Toast.makeText(RedbagDetailActivity.this, "返还成功！", Toast.LENGTH_SHORT).show();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(RedbagDetailActivity.this);
                } else if (status == 99) {
                       /*抗攻击*/
                       Unlogin.doAtk(prarams,result,new BackbagAsyncTask());
                } else {
                   Toast.makeText(RedbagDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(RedbagDetailActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
