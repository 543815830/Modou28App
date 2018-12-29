package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.PromoteEarings;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * 推广收益适配
 */

public class MyEarningsAdapter extends BaseAdapter {
    private Context mContext;
    private List <PromoteEarings.DataBean>list;
    public MyEarningsAdapter(Context mContext, List <PromoteEarings.DataBean>list) {
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
            convertView=View.inflate(mContext, R.layout.item_lv_myearnings,null);
            mHashMap.put(i, convertView);
            viewHolder.register_time=  convertView.findViewById(R.id.register_time);
            viewHolder.offline_id=  convertView.findViewById(R.id.offline_id);
            viewHolder.earnings_type=  convertView.findViewById(R.id.earnings_type);
            viewHolder.award=  convertView.findViewById(R.id.award);
            viewHolder.last_login_time=  convertView.findViewById(R.id.last_login_time);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.register_time.setText(new StringBuilder().append("注册时间：").append(list.get(i).getRegtime()).toString());
        viewHolder.offline_id.setText(list.get(i).getId());
        viewHolder.earnings_type.setText(list.get(i).getType());
        viewHolder.award.setText(list.get(i).getPoints());
        viewHolder.last_login_time.setText(new StringBuilder().append("最后登录：").append(list.get(i).getTime()).toString());
        return convertView;
    }
    static  class ViewHolder{
        TextView register_time;
        TextView offline_id;
        TextView earnings_type;
        TextView award;

        TextView last_login_time;

    }
}
