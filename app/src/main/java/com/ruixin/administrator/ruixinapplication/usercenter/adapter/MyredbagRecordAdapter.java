package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.AdvanceDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.RedbagrecordDb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 李丽 on 2018/7/6.
 */

public class MyredbagRecordAdapter extends BaseAdapter {
    Context mContext;
    List<RedbagrecordDb.DataBean>list;
    String type;
    SparseBooleanArray mCheckStates=new SparseBooleanArray();
    private List <Integer>mposition=new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private OnShareClickListener onShareClickListener;
    private OnBackClickListener onBackClickListener;
    public MyredbagRecordAdapter(Context context, List<RedbagrecordDb.DataBean>list,String type) {
        this.mContext=context;
        this.list=list;
        this.type=type;
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
            convertView=View.inflate(mContext, R.layout.item_redbag_record,null);
            mHashMap.put(i, convertView);
            viewHolder.ll_item=  convertView.findViewById(R.id.ll_item);
            viewHolder.tv_show_time=  convertView.findViewById(R.id.tv_show_time);
            viewHolder.bag_type=  convertView.findViewById(R.id.bag_type);
            viewHolder.iv_pin=  convertView.findViewById(R.id.iv_pin);
            viewHolder.tv_coins_total=  convertView.findViewById(R.id.tv_coins_total);
            viewHolder.tv_status_bag=  convertView.findViewById(R.id.tv_status_bag);
            viewHolder.tv_get_num_user=  convertView.findViewById(R.id.tv_get_num_user);
            viewHolder.tv_share_redbag=  convertView.findViewById(R.id.tv_share_redbag);
            viewHolder.tv_share_redbag2=  convertView.findViewById(R.id.tv_share_redbag2);
            viewHolder.tv_back=  convertView.findViewById(R.id.tv_back);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(list.get(i).getType()!=null){
        if(list.get(i).getType().equals("1")){
            viewHolder.bag_type.setText("拼手气红包");
            viewHolder.iv_pin.setVisibility(View.VISIBLE);
        }else{
            viewHolder.bag_type.setText("普通红包");
            viewHolder.iv_pin.setVisibility(View.GONE);
        } }
        viewHolder.tv_coins_total.setText("剩余金额："+list.get(i).getRest()+"/"+list.get(i).getPoints());
        if(list.get(i).getState()!=null){
            if(list.get(i).getState().equals("1")){
                viewHolder.tv_status_bag.setText("状态：领取中");
            }else if(list.get(i).getState().equals("2")){
                viewHolder.tv_status_bag.setText("状态：领取完毕");
            }else{
                viewHolder.tv_status_bag.setText("状态：已返还");
            }
        }

        viewHolder.tv_show_time.setText(list.get(i).getTime());

        viewHolder.tv_get_num_user.setText("领取人数："+list.get(i).getGet()+"/"+list.get(i).getSum());
        viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(view,i);
                }
            }
        });
        viewHolder.tv_share_redbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onShareClickListener!=null){
                    onShareClickListener.OnShareClick(view,i);
                }
            }
        });
        viewHolder.tv_share_redbag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onShareClickListener!=null){
                    onShareClickListener.OnShareClick(view,i);
                }
            }
        });
        if(type.equals("0")){
            viewHolder.tv_share_redbag2.setVisibility(View.GONE);
            viewHolder.tv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onBackClickListener!=null){
                        onBackClickListener.OnBackClick(view,i);
                    }
                }
            });
        }else{
            viewHolder.tv_back.setVisibility(View.GONE);
 viewHolder.tv_share_redbag.setVisibility(View.GONE);
 viewHolder.tv_share_redbag2.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
    static  class ViewHolder{
        LinearLayout ll_item;
        ImageView iv_pin;
        TextView tv_show_time;
        TextView bag_type;
        TextView tv_coins_total;
        TextView tv_status_bag;
        TextView tv_get_num_user;
        TextView tv_share_redbag;
        TextView tv_share_redbag2;
        TextView tv_back;

    }

    public  interface OnItemClickListener{
        public  void onItemClick(View view, int i);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public  interface OnShareClickListener{
        public  void OnShareClick(View view, int i);
    }
    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }
    public  interface OnBackClickListener{
        public  void OnBackClick(View view, int i);
    }
    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }
}
