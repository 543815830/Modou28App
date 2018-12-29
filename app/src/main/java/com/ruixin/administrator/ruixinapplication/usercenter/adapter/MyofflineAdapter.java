package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MyOffline;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 */

public class MyofflineAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyOffline.DataBean> list;
    public MyofflineAdapter(Context mContext, List<MyOffline.DataBean> list) {
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
            convertView=View.inflate(mContext, R.layout.item_lv_myoffline,null);
            mHashMap.put(i, convertView);
            viewHolder.register_time=  convertView.findViewById(R.id.register_time);
            viewHolder.offline_id=  convertView.findViewById(R.id.offline_id);
            viewHolder.last_login_time=  convertView.findViewById(R.id.last_login_time);
            viewHolder.offline_level=  convertView.findViewById(R.id.offline_level);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.register_time.setText(new StringBuilder().append("注册时间：").append(list.get(i).getRegtime()).toString());
        viewHolder.offline_id.setText(list.get(i).getId());
        viewHolder.last_login_time.setText(new StringBuilder().append("最后登录：").append(list.get(i).getLogintime()).toString());
        viewHolder.offline_level.setText(list.get(i).getLevel());
        return convertView;
    }
    static  class ViewHolder{
        TextView register_time;
        TextView offline_id;
        TextView last_login_time;
        TextView offline_level;

    }
}
