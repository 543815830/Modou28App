package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.usercenter.UserFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MBMessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.RMessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.BadgeView;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.ListDataSave;
import com.ruixin.administrator.ruixinapplication.utils.SharedPrefUtility;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 更多功能界面
 */
public class MoreFunctionActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_card)
    RelativeLayout rlCard;
    @BindView(R.id.rl_conversion)
    RelativeLayout rlConversion;
    @BindView(R.id.rl_trustee)
    RelativeLayout rlTrustee;
    @BindView(R.id.rl_red_bag)
    RelativeLayout rlRedBag;
    @BindView(R.id.tv_mail_box)
    TextView tvMailBox;
   @BindView(R.id.tv_mail_boxnumber)
    TextView tv_mail_boxnumber;
    @BindView(R.id.rl_inside_mailbox)
    RelativeLayout rlInsideMailbox;
    @BindView(R.id.rl_domain_name)
    RelativeLayout rlDomainName;
    Intent intent;
    String userId;
    String userToken;
  int unreadMsgnum;
    String result;
    User user;
    private HashMap<String, String> prarams = new HashMap<>();
    List<String> list=new ArrayList<>();
    BadgeView badge1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_function);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new UserInfoAsyncTask().execute();

        tvTitle.setText("更多功能");
        list=  ListDataSave.getDataList(MoreFunctionActivity.this,"menu").get(4);
        if(list.contains("点卡使用")){
            rlCard.setVisibility(View.GONE);
        } if(list.contains("兑换记录")){
            rlConversion.setVisibility(View.GONE);
        }if(list.contains("托管方案")){
            rlTrustee.setVisibility(View.GONE);
        } if(list.contains("站内红包")){
            rlRedBag.setVisibility(View.GONE);
        } if(list.contains("站内信箱")){
            rlInsideMailbox.setVisibility(View.GONE);
        } if(list.contains("专属域名")){
            rlDomainName.setVisibility(View.GONE);
        }
        initStatus();
        //注册
        EventBus.getDefault().register(this);
    }
    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(MBMessageEvent enevnt) {
        String result = enevnt.result;
        Log.e("tag", "result" + result);
        if(enevnt.result.equals("1")){
          if(unreadMsgnum>0){
              unreadMsgnum--;
              remind(tv_mail_boxnumber);
              saveParameter();
          }else{
              remind(tv_mail_boxnumber);
              saveParameter();
          }

        }

    }

    private void remind(View view) { //BadgeView的具体使用
        if(badge1==null){
            badge1 = new BadgeView(this, view);// 创建一个BadgeView对象，view为你需要显示提醒的控件
        }
        badge1.setText(""+unreadMsgnum); // 需要显示的提醒类容
      //  badge1.setText("120"+unreadMsgnum); // 需要显示的提醒类容
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setTextColor(Color.WHITE); // 文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
        //badge1.setBackgroundResource(R.mipmap.icon_message_png); //设置背景图片
        badge1.setTextSize(12); // 文本大小
        badge1.setBadgeMargin(10);
      //  badge1.setBadgeMargin(10); // 水平和竖直方向的间距
        // badge1.toggle(); //显示效果，如果已经显示，则影藏，如果影藏，则显示
        if(unreadMsgnum>0){
            badge1.show();// 只有显示
        }else{
            badge1.hide();
        }

        // badge1.hide();//影藏显示
    }
    @SuppressLint("StaticFieldLeak")
    private class UserInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().Info_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("回调消息返回", "消息返回结果result" + result);
            if (result != null) {
                int index = result.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    try {
                        JSONObject jsb = new JSONObject(result);
                        int status = jsb.optInt("status");
                        if (status == 1) {
                            Gson gson = new Gson();
                            user = gson.fromJson(result, User.class);
                            SharedPrefUtility.saveData(MoreFunctionActivity.this, user);
                            RuiXinApplication.getInstance().setUserToken(user.getData().getUsertoken());
                            RuiXinApplication.getInstance().setUserId(user.getData().getId());
                            unreadMsgnum= user.getData().getMsgnum();
                           remind(tv_mail_boxnumber);
                        } else if (status == -97 || status == -99) {
                            Unlogin.doLogin(MoreFunctionActivity.this);
                        } else if (status == 99) {
                            /*抗攻击*/
                            Unlogin.doAtk(prarams,result,new UserInfoAsyncTask());
                        } else if (status <= 0) {
                            Toast.makeText(MoreFunctionActivity.this, "获取信息错误", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (index != -1) {
                    Toast.makeText(MoreFunctionActivity.this, "返回错误数据格式", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MoreFunctionActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void saveParameter() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_msgnum", String.valueOf(unreadMsgnum));
        editor.apply();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }
    @OnClick({R.id.back_arrow, R.id.rl_card, R.id.rl_conversion, R.id.rl_trustee, R.id.rl_red_bag, R.id.rl_inside_mailbox, R.id.rl_domain_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.rl_card://点卡使用
                intent = new Intent(MoreFunctionActivity.this, UseCardActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                break;
            case R.id.rl_conversion://兑换记录
                intent = new Intent(MoreFunctionActivity.this, ConversionRecordActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                // startActivity(new Intent(mContext, TrusteeActivity.class));
                break;
            case R.id.rl_trustee://托管方案
                intent = new Intent(MoreFunctionActivity.this, TrusteeActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                // startActivity(new Intent(mContext, TrusteeActivity.class));
                break;
            case R.id.rl_red_bag://站内红包
                intent = new Intent(MoreFunctionActivity.this, InsideBagActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                //startActivity(new Intent(mContext, InsideBagActivity.class));
                break;
            case R.id.rl_inside_mailbox://站内信箱
                intent = new Intent(MoreFunctionActivity.this, InsideMailBoxActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                //startActivity(new Intent(mContext, InsideMailBoxActivity.class));
                break;
            case R.id.rl_domain_name://专属域名
                intent = new Intent(MoreFunctionActivity.this, DomainNameActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userToken", userToken);
                startActivity(intent);
                //startActivity(new Intent(mContext, InsideMailBoxActivity.class));
                break;
        }
    }
}
