package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.AutoBetInfoDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ScameDb;
import com.ruixin.administrator.ruixinapplication.home.databean.PrizeTypeDb;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 * 适配新闻公告的适配器
 */

public class SchemeAdapter extends BaseAdapter {
    private Context mContext;
    List<ScameDb> schmelist;
    public SchemeAdapter(Context mContext,  List<ScameDb> schmelist) {
       this.mContext=mContext;
       this.schmelist=schmelist;

    }

    @Override
    public int getCount() {
      //  Log.e("tag",""+mDatas.size());
        return schmelist.size();
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
            convertView=View.inflate(mContext, R.layout.item_prize_type,null);
            mHashMap.put(i, convertView);
            viewHolder.rl_item=  convertView.findViewById(R.id.rl_item);
            viewHolder.tv_type=  convertView.findViewById(R.id.tv_type);
            viewHolder.iv_check=  convertView.findViewById(R.id.iv_check);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }

            viewHolder.tv_type.setText(schmelist.get(i).getName());
     //   schmelist.get(0).setFlag(true);
        if(schmelist.get(i).isFlag()){
            viewHolder.tv_type.setTextColor(Color.parseColor("#339ef9"));
            viewHolder.iv_check.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tv_type.setTextColor(Color.parseColor("#696969"));
            viewHolder.iv_check.setVisibility(View.GONE);
        }
    viewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(onItemClickListener!=null){
                for (int j= 0; j <schmelist.size(); j++) {
                    if(j==i){
                        schmelist.get(j).setFlag(true);
                    }else{
                        schmelist.get(j).setFlag(false);
                    }

                    notifyDataSetChanged();
                }
                onItemClickListener.OnItemClick(view,i);
            }
        }
    });
        return convertView;
    }
    static  class ViewHolder{
        RelativeLayout rl_item;
        TextView tv_type;
        ImageView iv_check;
    }
    public  interface OnItemClickListener{
        public  void OnItemClick(View view, int id);
    }
    OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
