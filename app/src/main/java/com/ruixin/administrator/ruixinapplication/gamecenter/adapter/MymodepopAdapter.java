package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MymodeDb;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class MymodepopAdapter extends BaseAdapter {
    private Context mContext;
    private List<MymodeDb.DataBean.ModellistBean>list;
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    public MymodepopAdapter(Context context, List<MymodeDb.DataBean.ModellistBean>list){
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(i) == null) {
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.item_multipel_lv,null);
            mHashMap.put(i, convertView);
            viewHolder.tv_multiple=  convertView.findViewById(R.id.tv_multiple);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }

            viewHolder.tv_multiple.setText(list.get(i).getModelname());


        return convertView;
    }

    static  class ViewHolder{

TextView tv_multiple;
    }
}

