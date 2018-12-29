package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.rank.databean.RankDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MyFechDb;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/20.
 * 邮箱：543815830@qq.com
 *本月 排行榜的适配器
 */

public class DepositReoordAdapter extends BaseAdapter{
    private final Context mContext;
    List<MyFechDb.DataBean> myFechList;
    public DepositReoordAdapter(Context context, List<MyFechDb.DataBean> myFechList) {
        this.mContext=context;
this.myFechList=myFechList;
    }

    @Override
    public int getCount() {
        return myFechList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(position) == null) {
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext,R.layout.item_deposit_record,null);
            viewHolder. rl_account=convertView.findViewById(R.id.rl_account);
            viewHolder. tv_status=convertView.findViewById(R.id.tv_status);
            viewHolder. tv_time=convertView.findViewById(R.id.tv_time);
            viewHolder. tv_account_type=convertView.findViewById(R.id.tv_account_type);
            viewHolder. tv_account=convertView.findViewById(R.id.tv_account);
            viewHolder. tv_user_name=convertView.findViewById(R.id.tv_user_name);
            viewHolder. tv_fetch=convertView.findViewById(R.id.tv_fetch);
            mHashMap.put(position, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(position);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder. tv_fetch.setText("+"+myFechList.get(position).getRmb());
        viewHolder. tv_user_name.setText(myFechList.get(position).getRealname());

        viewHolder. tv_time.setText(myFechList.get(position).getTime());
        if(myFechList.get(position).getBank().contains("支付宝")||myFechList.get(position).getBank().contains("微信")){
            viewHolder. tv_account_type.setVisibility(View.GONE);
            viewHolder. tv_account.setText(myFechList.get(position).getBank());
        }else{
            viewHolder. tv_account_type.setText(myFechList.get(position).getBank());
            viewHolder. tv_account.setText(myFechList.get(position).getCard());
        }
        if(myFechList.get(position).getState().equals("0")){
            viewHolder. tv_status.setText("审核中");
        }else if(myFechList.get(position).getState().equals("1")){
            viewHolder. tv_status.setText("已到账");
        }else if(myFechList.get(position).getState().equals("2")){
            viewHolder. tv_status.setText("已拒绝");
        }
        return convertView;
    }
    static  class ViewHolder{
        RelativeLayout rl_account;
        TextView tv_status;
        TextView tv_time;
        TextView tv_account_type;
        TextView tv_account;
        TextView tv_user_name;
        TextView tv_fetch;
    }
}
