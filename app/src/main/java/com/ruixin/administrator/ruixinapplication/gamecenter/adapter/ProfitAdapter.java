package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ProfitDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class ProfitAdapter extends BaseAdapter {
    private Context mContext;
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    List<String> list=new ArrayList<String>();
    List<ProfitDb.DataBean.GamedataBean> gamedata=new ArrayList<>();
    public ProfitAdapter(Context context, List<String> list,List<ProfitDb.DataBean.GamedataBean> gamedata){
        this.mContext=context;
        this.list=list;
        this.gamedata=gamedata;

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
            convertView=LayoutInflater.from(mContext).inflate(R.layout.item_profit_lv,null);
            viewHolder.ll_profit_item=convertView.findViewById(R.id.ll_profit_item);
            viewHolder.ll_tv=convertView.findViewById(R.id.ll_tv);
            viewHolder.hs_item=convertView.findViewById(R.id.hs_item);
           // viewHolder.tv_pgame_name=convertView.findViewById(R.id.tv_pgame_name);
            viewHolder.tv_1=convertView.findViewById(R.id.tv_1);
            viewHolder.tv_2=convertView.findViewById(R.id.tv_2);
            viewHolder.tv_3=convertView.findViewById(R.id.tv_3);
            viewHolder.tv_4=convertView.findViewById(R.id.tv_4);
            viewHolder.tv_5=convertView.findViewById(R.id.tv_5);
            viewHolder.tv_6=convertView.findViewById(R.id.tv_6);
            viewHolder.tv_7=convertView.findViewById(R.id.tv_7);
            viewHolder.tv_8=convertView.findViewById(R.id.tv_8);
            viewHolder.tv_sum=convertView.findViewById(R.id.tv_sum);
            viewHolder.tv_profit=convertView.findViewById(R.id.tv_profit);
            mHashMap.put(i, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(gamedata.size()>1){
       if(i==0){

           viewHolder.ll_profit_item.setBackgroundColor(Color.parseColor("#F3F3F3"));
           viewHolder.ll_tv.setBackgroundColor(Color.parseColor("#F3F3F3"));
           viewHolder.hs_item.setVisibility(View.VISIBLE);
           viewHolder.tv_1.setText("今天");
           viewHolder.tv_1.setTextSize(14);
           viewHolder.tv_1.setTextColor(Color.parseColor("#959595"));
           viewHolder.tv_2.setText("昨天");
           viewHolder.tv_2.setTextSize(14);
           viewHolder.tv_2.setTextColor(Color.parseColor("#959595"));
           viewHolder.tv_3.setText("前天");
           viewHolder.tv_3.setTextSize(14);
           viewHolder.tv_3.setTextColor(Color.parseColor("#959595"));
           viewHolder.tv_4.setText("大前天");
           viewHolder.tv_4.setTextSize(14);
           viewHolder.tv_4.setTextColor(Color.parseColor("#959595"));
           viewHolder.tv_5.setText(""+gamedata.get(4).getDate());
           viewHolder.tv_5.setTextSize(14);
           viewHolder.tv_5.setTextColor(Color.parseColor("#959595"));
           viewHolder.tv_6.setText(""+gamedata.get(5).getDate());
           viewHolder.tv_6.setTextSize(14);
           viewHolder.tv_6.setTextColor(Color.parseColor("#959595"));
           viewHolder.tv_7.setText(""+gamedata.get(6).getDate());
           viewHolder.tv_7.setTextSize(14);
           viewHolder.tv_7.setTextColor(Color.parseColor("#959595"));
           viewHolder.tv_8.setText(""+gamedata.get(7).getDate());
           viewHolder.tv_8.setTextSize(14);
           viewHolder.tv_8.setTextColor(Color.parseColor("#959595"));
           viewHolder.tv_sum.setText("合计");
           viewHolder.tv_sum.setTextSize(14);
           viewHolder.tv_sum.setTextColor(Color.parseColor("#959595"));
       }else{
           viewHolder.hs_item.setVisibility(View.VISIBLE);
        //   viewHolder.tv_pgame_name.setText(list.get(i-1));
           viewHolder.tv_1.setText(""+gamedata.get(0).getData().get(i-1));
           viewHolder.tv_2.setText(""+gamedata.get(1).getData().get(i-1));
           viewHolder.tv_3.setText(""+gamedata.get(2).getData().get(i-1));
           viewHolder.tv_4.setText(""+gamedata.get(3).getData().get(i-1));
           viewHolder.tv_5.setText(""+gamedata.get(4).getData().get(i-1));
           viewHolder.tv_6.setText(""+gamedata.get(5).getData().get(i-1));
           viewHolder.tv_7.setText(""+gamedata.get(6).getData().get(i-1));
           viewHolder.tv_8.setText(""+gamedata.get(7).getData().get(i-1));
           viewHolder.tv_sum.setText(""+gamedata.get(8).getData().get(i-1));
       }
        }else{
            viewHolder.hs_item.setVisibility(View.GONE);
            viewHolder.tv_profit.setVisibility(View.VISIBLE);
            if(i==0){
                viewHolder.tv_profit.setBackgroundColor(Color.parseColor("#F3F3F3"));
                viewHolder.tv_profit.setText(gamedata.get(0).getDate());
                viewHolder.tv_profit.setTextSize(14);
                viewHolder.tv_profit.setTextColor(Color.parseColor("#959595"));
            }else{
                viewHolder.tv_profit.setText(""+gamedata.get(0).getData().get(i-1));
            }
        }
        return convertView;
    }

    static  class ViewHolder{
        LinearLayout ll_profit_item;
        LinearLayout ll_tv;
        HorizontalScrollView hs_item;
       // TextView tv_pgame_name;
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        TextView tv_5;
        TextView tv_6;
        TextView tv_7;
        TextView tv_8;
        TextView tv_sum;
        TextView tv_profit;
    }
}

