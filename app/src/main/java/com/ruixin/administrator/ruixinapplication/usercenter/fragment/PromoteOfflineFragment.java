package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.OfflineAward;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.HashMap;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * 推广下线
 */

public class PromoteOfflineFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private TextView offline_address;
    private TextView award_by_manual;
    private TextView award_by_card;
    private TextView award_by_loss;
    private TextView  tv_award_lv1;
    private TextView  tv_award_lv2;
    private TextView  tv_award_lv3;
    private TextView  tv_award_lv4;
    private TextView  tv_award_lv5;
    private TextView  tv_award_lv6;
private Button btn_copy;
    String userId="",userToken="";
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_promote_offline,null);
            offline_address=view.findViewById(R.id.offline_address);
            award_by_manual=view.findViewById(R.id.award_by_manual);
            award_by_card=view.findViewById(R.id.award_by_card);
            award_by_loss=view.findViewById(R.id.award_by_loss);
            tv_award_lv1=view.findViewById(R.id.tv_award_lv1);
            tv_award_lv2=view.findViewById(R.id.tv_award_lv2);
            tv_award_lv3=view.findViewById(R.id.tv_award_lv3);
            tv_award_lv4=view.findViewById(R.id.tv_award_lv4);
            tv_award_lv5=view.findViewById(R.id.tv_award_lv5);
            tv_award_lv6=view.findViewById(R.id.tv_award_lv6);
            btn_copy=view.findViewById(R.id.btn_copy);
            btn_copy.setOnClickListener(this);
            userId=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            userToken=getActivity(). getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
            prarams.put("usersid",userId);
            prarams.put("usertoken",userToken);
            new POfflineAsyncTask().execute();
        }
        return view;
    }
//复制地址的实现
    @Override
    public void onClick(View view) {
        ClipboardManager clip = (ClipboardManager) getActivity().getSystemService(mContext.CLIPBOARD_SERVICE);
        clip.getText(); // 粘贴
        clip.setText(offline_address.getText().toString()); // 复制
       Toast.makeText(mContext, "推广地址已复制到剪贴板",Toast.LENGTH_SHORT).show();
    }

    private class POfflineAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().PromoteOffline_URL,prarams);
            Log.e("推广下线的数据返回",""+result);
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
                    OfflineAward offlineDb=gson.fromJson(s,OfflineAward.class);
                    if(offlineDb.getStatus()==1) {
                           offline_address.setText(new StringBuilder().append("    ").append(offlineDb.getData().getTjurl()).toString());

                        tv_award_lv1.setText(offlineDb.getData().getTgv1());
                        tv_award_lv2.setText(offlineDb.getData().getTgv2());
                        tv_award_lv3.setText(offlineDb.getData().getTgv3());
                        tv_award_lv4.setText(offlineDb.getData().getTgv4());
                        tv_award_lv5.setText(offlineDb.getData().getTgv5());
                        tv_award_lv6.setText(offlineDb.getData().getTgv6());
                        award_by_manual.setText(new StringBuilder().append("下线手动投注，上线获得").append(offlineDb.getData().getWeb_tzfl()).append("%奖励").toString());
                        award_by_card.setText(new StringBuilder().append("下线使用广告体验卡，上线获得").append(offlineDb.getData().getWeb_chongzhi_fanli()).append("%奖励").toString());
                        award_by_loss.setText(new StringBuilder().append("下线亏损，上线第二天亏损返利领取").append(offlineDb.getData().getXxksbl()).append("%奖励").toString());
                        String s1=award_by_manual.getText().toString();
                        String s2=award_by_card.getText().toString();
                        String s3=award_by_loss.getText().toString();
                        String s1Tag=offlineDb.getData().getWeb_tzfl();
                        String s2Tag=offlineDb.getData().getWeb_chongzhi_fanli();
                        String s3Tag=offlineDb.getData().getXxksbl();
                        SpannableString mBuilder1 = new SpannableString(s1);
                        //设置字体变色
                        mBuilder1.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), 11, 11+s1Tag.length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        award_by_manual.setText(mBuilder1);
                        SpannableString mBuilder2 = new SpannableString(s2);
                        mBuilder2.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), 14, 14+s2Tag.length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        award_by_card.setText(mBuilder2);
                        SpannableString mBuilder3 = new SpannableString(s3);
                        mBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), 16, 16+s3Tag.length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        award_by_loss.setText(mBuilder3);
                    }else if(offlineDb.getStatus()==-97||offlineDb.getStatus()==-99){
                        Unlogin.doLogin(mContext);
                    }else if (offlineDb.getStatus() ==99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,new POfflineAsyncTask());
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
