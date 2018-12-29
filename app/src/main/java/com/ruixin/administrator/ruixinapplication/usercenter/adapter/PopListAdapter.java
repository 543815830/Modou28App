package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/11.
 * 邮箱：543815830@qq.com
 */
public class PopListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> list ;
    public PopListAdapter(Context mContext,   List<String> list ) {
        this.mContext=mContext;
        Log.e("tag",""+list);
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
            convertView=View.inflate(mContext, R.layout.item_pop_list,null);
            mHashMap.put(i, convertView);
            viewHolder.pop_tv_pwd_que=  convertView.findViewById(R.id.pop_tv_pwd_que);
            viewHolder.ll_pop_item=  convertView.findViewById(R.id.ll_pop_item);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
       viewHolder.pop_tv_pwd_que.setText(list.get(i));
        Log.e("tag",""+list.get(i));
        return convertView;
    }

    static  class ViewHolder{
        LinearLayout ll_pop_item;
        TextView pop_tv_pwd_que;

    }
}
