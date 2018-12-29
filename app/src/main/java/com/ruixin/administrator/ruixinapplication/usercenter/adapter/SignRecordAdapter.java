package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.SignRecord;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/17.
 * 邮箱：543815830@qq.com
 */

public class SignRecordAdapter extends BaseAdapter {
    private Context mContext;
    private List<SignRecord.DataBean> list;
    public SignRecordAdapter(Context mContext, List<SignRecord.DataBean> list) {
        this.mContext=mContext;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(i) == null) {
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_sign_record,null);
            mHashMap.put(i, convertView);
            viewHolder.sign_time=  convertView.findViewById(R.id.sign_time);
            viewHolder.sign_coin=  convertView.findViewById(R.id.sign_coin);
            viewHolder.sign_suffer=  convertView.findViewById(R.id.sign_suffer);
            viewHolder.sign_balance=  convertView.findViewById(R.id.sign_balance);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sign_time.setText("时间:"+list.get(i).getTime());
        viewHolder.sign_coin.setText("+"+list.get(i).getPoints());
        viewHolder.sign_suffer.setText("+"+list.get(i).getExperience());
        viewHolder.sign_balance.setText(list.get(i).getYucoins());
        return convertView;
    }
    static  class ViewHolder{
        TextView sign_time;
        TextView sign_coin;
        TextView sign_suffer;
        TextView sign_balance;

    }
}
