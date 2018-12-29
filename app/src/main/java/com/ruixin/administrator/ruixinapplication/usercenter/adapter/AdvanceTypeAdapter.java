package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.home.databean.PrizeTypeDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.AdvanceDb;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 * 适配新闻公告的适配器
 */

public class AdvanceTypeAdapter extends BaseAdapter {
    private Context mContext;
    List<AdvanceDb.ListBean>typelist;
    public AdvanceTypeAdapter(Context mContext, List<AdvanceDb.ListBean>typelist) {
       this.mContext=mContext;
       this.typelist=typelist;

    }

    @Override
    public int getCount() {
      //  Log.e("tag",""+mDatas.size());
        return typelist.size();
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
            convertView=View.inflate(mContext, R.layout.item_advance_type,null);
            mHashMap.put(i, convertView);
            viewHolder.ll_item_adavace_pop=  convertView.findViewById(R.id.ll_item_adavace_pop);
            viewHolder.tv_advance_type=  convertView.findViewById(R.id.tv_advance_type);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_advance_type.setText(typelist.get(i).getGamechname());
         if(typelist.get(i).isFlag()){
             viewHolder.tv_advance_type.setTextColor(Color.parseColor("#339ef9"));
         }

        return convertView;
    }
    static  class ViewHolder{
        LinearLayout ll_item_adavace_pop;
        TextView tv_advance_type;

    }
    public  interface OnItemClickListener{
        public  void OnItemClick(View view, int i);
    }
    OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
