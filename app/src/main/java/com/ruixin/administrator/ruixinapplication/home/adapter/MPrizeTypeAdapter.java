package com.ruixin.administrator.ruixinapplication.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.home.databean.HPR;
import com.ruixin.administrator.ruixinapplication.home.databean.PrizeTypeDb;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MailBoxAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 * 适配新闻公告的适配器
 */

public class MPrizeTypeAdapter extends BaseAdapter {
    private Context mContext;
    List<PrizeTypeDb.DataBean.PrizetypeBean>typeList;
    public MPrizeTypeAdapter(Context mContext, List<PrizeTypeDb.DataBean.PrizetypeBean>typeList) {
       this.mContext=mContext;
       this.typeList=typeList;

    }

    @Override
    public int getCount() {
      //  Log.e("tag",""+mDatas.size());
        return typeList.size();
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

            viewHolder.tv_type.setText(typeList.get(i).getName());

        if(typeList.get(i).isFlag()){
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
                for (int j= 0; j <typeList.size(); j++) {
                    if(j==i){
                        typeList.get(j).setFlag(true);
                    }else{
                        typeList.get(j).setFlag(false);
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
        public  void OnItemClick(View view,int i);
    }
    OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
