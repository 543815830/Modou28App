package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ProfitDb2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class ProfitNameAdapter extends BaseAdapter {
    private Context mContext;
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    List<String> list=new ArrayList<>();
    public ProfitNameAdapter(Context context, List<String> list ){
        this.mContext=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size()+1;
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
            convertView=LayoutInflater.from(mContext).inflate(R.layout.item_profit_lv2,null);
            viewHolder. ll_profit_item=convertView.findViewById(R.id.ll_profit_item);
            viewHolder.tv_pgame_name=convertView.findViewById(R.id.tv_pgame_name);
            mHashMap.put(i, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
     if(i==0){
         viewHolder.ll_profit_item.setBackgroundColor(Color.parseColor("#F3F3F3"));
         viewHolder.tv_pgame_name.setText("游戏名称");
         viewHolder.tv_pgame_name.setTextSize(14);
         viewHolder.tv_pgame_name.setTextColor(Color.parseColor("#959595"));
     }else{
         viewHolder.tv_pgame_name.setText(list.get(i-1));
     }

        return convertView;
    }

    static  class ViewHolder{
        LinearLayout ll_profit_item;
        TextView tv_pgame_name;


    }
}

