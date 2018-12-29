package com.ruixin.administrator.ruixinapplication.rank.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.rank.databean.RankDb;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/20.
 * 邮箱：543815830@qq.com
 * 本周排行榜的适配器
 */

public class WRankingListAdapter extends BaseAdapter{
    private final Context mContext;
    private final  List<RankDb.DataBean.YingliYestBean> list;
    public WRankingListAdapter(Context context, List<RankDb.DataBean.YingliYestBean> datas) {
        this.mContext=context;
        this.list=datas;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(position) == null) {
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext,R.layout.item_ranking_list,null);
            mHashMap.put(position, convertView);
            viewHolder.item_rank=  convertView.findViewById(R.id.item_rank);
            viewHolder.item_game_player=  convertView.findViewById(R.id.item_game_player);
            viewHolder.item_gold_coin=  convertView.findViewById(R.id.item_gold_coin);
            viewHolder.ll_rank_list=convertView.findViewById(R.id.ll_rank_list);
            convertView.setTag(viewHolder);
        }else{
            convertView = (View) mHashMap.get(position);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(position==0){

            viewHolder.item_rank.setBackgroundResource(R.drawable.rank_no1);
            viewHolder.item_rank.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.item_game_player.setTextColor(Color.parseColor("#ff423d"));
            viewHolder.item_gold_coin.setTextColor(Color.parseColor("#ff423d"));
        }
        if(position==1){
            viewHolder.item_rank.setBackgroundResource(R.drawable.rank_no2);
            viewHolder.item_rank.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.item_game_player.setTextColor(Color.parseColor("#ffbd3d"));
            viewHolder.item_gold_coin.setTextColor(Color.parseColor("#ffbd3d"));
        }
        if(position==2){
            viewHolder.item_rank.setBackgroundResource(R.drawable.rank_no3);
            viewHolder.item_rank.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.item_game_player.setTextColor(Color.parseColor("#5fd1c5"));
            viewHolder.item_gold_coin.setTextColor(Color.parseColor("#5fd1c5"));
        }
        if (position%2==0){
            viewHolder.ll_rank_list.setBackgroundColor(Color.parseColor("#f2f8ff"));
        }
        viewHolder.item_rank.setText(new StringBuilder().append(position+1).toString());
        viewHolder.item_game_player.setText(list.get(position).getName());
        viewHolder.item_gold_coin.setText(FormatUtils.formatString(list.get(position).getShouyi()));
        return convertView;
    }
    static  class ViewHolder{
        LinearLayout ll_rank_list;
        TextView item_rank;
        TextView item_game_player;
        TextView item_gold_coin;
    }
}
