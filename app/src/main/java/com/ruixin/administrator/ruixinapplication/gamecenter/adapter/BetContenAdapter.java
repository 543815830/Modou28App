package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetContentDb;
import com.ruixin.administrator.ruixinapplication.home.databean.HPR;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 * 适配新闻公告的适配器
 */

public class BetContenAdapter extends BaseAdapter {
    private Context mContext;
    List<BetContentDb.DataBean> list;
    String gameType;
    public BetContenAdapter(Context mContext,  List<BetContentDb.DataBean> list,String gameType) {
       this.mContext=mContext;
this.list=list;
this.gameType=gameType;
    }

    @Override
    public int getCount() {
      //  Log.e("tag",""+mDatas.size());
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        Log.e("tag","getItem"+i);
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
            convertView=View.inflate(mContext, R.layout.item_bet_content,null);
            mHashMap.put(i, convertView);
            viewHolder.bet_number=  convertView.findViewById(R.id.bet_number);
            viewHolder.tv_cb=  convertView.findViewById(R.id.tv_cb);
            viewHolder.bet_coins=  convertView.findViewById(R.id.bet_coins);
            viewHolder.stand_peilv=  convertView.findViewById(R.id.stand_peilv);
            viewHolder.open_peilv=  convertView.findViewById(R.id.open_peilv);
            viewHolder.open_coins=  convertView.findViewById(R.id.open_coins);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }


            viewHolder.bet_number.setText("投注号码："+list.get(i).getTzhm());
        if(gameType.equals("xn")){
            viewHolder.tv_cb.setText("投注虚拟币：");
        }else{
            viewHolder.tv_cb.setText("投注金币：");
        }
            viewHolder.bet_coins.setText(""+list.get(i).getTzhm());
            viewHolder.stand_peilv.setText(""+list.get(i).getBzpl());
            viewHolder.open_peilv.setText(""+list.get(i).getKjpl());
            viewHolder.bet_coins.setText(""+list.get(i).getTzsl());
            viewHolder.open_coins.setText("+"+list.get(i).getHdsl());


        return convertView;
    }
    static  class ViewHolder{
        TextView bet_number;
        TextView tv_cb;
        TextView bet_coins;
        TextView stand_peilv;
        TextView open_peilv;
        TextView open_coins;

    }
}
