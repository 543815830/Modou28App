package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.GoldDepositDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MyCardDb;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/20.
 * 邮箱：543815830@qq.com
 *本月 排行榜的适配器
 */

public class MybindBnakdAdapter extends BaseAdapter{
    private final Context mContext;
    List<MyCardDb.DataBean> cardList ;
    private OnDelClickListener onDelClickListener;
    private OnUpClickListener onUpClickListener;
    public MybindBnakdAdapter(Context context, List<MyCardDb.DataBean> cardList) {
        this.mContext=context;
this.cardList=cardList;
    }

    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(position) == null) {
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext,R.layout.item_my_bank,null);
            viewHolder.tv_account_type=convertView.findViewById(R.id.tv_account_type);
            viewHolder.tv_user_name=convertView.findViewById(R.id.tv_user_name);
            viewHolder.tv_account_number=convertView.findViewById(R.id.tv_account_number);
            viewHolder.rl_account_number=convertView.findViewById(R.id.rl_account_number);
            viewHolder.tv_clear=convertView.findViewById(R.id.tv_clear);
            viewHolder.tv_update=convertView.findViewById(R.id.tv_update);
            mHashMap.put(position, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(position);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_account_type.setText(cardList.get(position).getTypename());
        viewHolder.tv_user_name.setText(cardList.get(position).getRealname());
        if(cardList.get(position).getTypename().equals("支付宝")||cardList.get(position).getTypename().equals("微信支付")){
            viewHolder.rl_account_number.setVisibility(View.GONE);
        }else{
            viewHolder.rl_account_number.setVisibility(View.VISIBLE);
            viewHolder.tv_account_number.setText(cardList.get(position).getCardname());
        }
        viewHolder.tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onUpClickListener!=null){
                    onUpClickListener.OnUpClick(view,cardList.get(position).getId(), position);
                }
            }
        });
        viewHolder.tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onDelClickListener!=null){
                    onDelClickListener.OnDelClick(view,cardList.get(position).getId(), position);
                }
            }
        });

        return convertView;
    }
    static  class ViewHolder{
        RelativeLayout rl_account_number;
        TextView tv_account_type;
        TextView tv_user_name;
        TextView tv_account_number;
        TextView tv_clear;
        TextView tv_update;
    }

    public  interface OnDelClickListener{
        public  void OnDelClick(View view, String data,int i);
    }
    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        this.onDelClickListener = onDelClickListener;
    }
    public  interface OnUpClickListener{
        public  void OnUpClick(View view, String data,int i);
    }
    public void setOnUpClickListener(OnUpClickListener onUpClickListener) {
        this.onUpClickListener = onUpClickListener;
    }
}
