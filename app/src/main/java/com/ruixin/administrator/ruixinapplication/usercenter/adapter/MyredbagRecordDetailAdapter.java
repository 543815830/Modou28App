package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MybagDetailDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.RedbagrecordDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 李丽 on 2018/7/6.
 */

public class MyredbagRecordDetailAdapter extends BaseAdapter {
    Context mContext;
    List<MybagDetailDb.DataBean.ListBean> list;
    SparseBooleanArray mCheckStates=new SparseBooleanArray();
    private List <Integer>mposition=new ArrayList<>();
    public MyredbagRecordDetailAdapter(Context context, List<MybagDetailDb.DataBean.ListBean> list) {
        this.mContext=context;
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
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(i) == null) {
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_get_users,null);
            mHashMap.put(i, convertView);
            viewHolder.tv_get_username=convertView.findViewById(R.id.tv_get_username);
            viewHolder.tv_get_time=  convertView.findViewById(R.id.tv_get_time);
            viewHolder.tv_get_cions=  convertView.findViewById(R.id.tv_get_cions);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_get_username.setText(list.get(i).getName());
        viewHolder.tv_get_time.setText(list.get(i).getTime());
        viewHolder.tv_get_cions.setText(list.get(i).getPoints());
        return convertView;
    }
    static  class ViewHolder{
        TextView tv_get_username;
        TextView tv_get_time;
        TextView tv_get_cions;

    }
}
