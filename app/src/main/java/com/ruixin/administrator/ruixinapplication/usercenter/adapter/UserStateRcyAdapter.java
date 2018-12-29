package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.StateDb;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/17.
 * 邮箱：543815830@qq.com
 * 活动专场的适配器
 */

public class UserStateRcyAdapter extends RecyclerView.Adapter<UserStateRcyAdapter.ViewHolder>{
    private Context mContext;
    List<StateDb.DataBean> list;
    public UserStateRcyAdapter(Context mContext, List<StateDb.DataBean> list) {
        Log.e("tag","UserStateRcyAdapter");
        this.mContext=mContext;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("tag","onCreateViewHolder");
        View itemview=View.inflate(mContext, R.layout.item_user_state,null);
        //View itemview=View.inflate(mContext, R.layout.item_user_state,null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("tag","onBindViewHolder");
       //holder.act_ic.setImageResource(list.get(position).getImage());
        holder.st_time.setText(new StringBuilder().append("时间:").append(list.get(position).getTime()).toString());
        holder.st_suffer.setText(new StringBuilder().append("经验:").append("+"+list.get(position).getExperience().toString()).toString());
        holder.st_coin.setText(new StringBuilder().append("金额:").append("+"+list.get(position).getPoints().toString()).toString());
        holder.st_balance.setText(new StringBuilder().append("余额:").append(list.get(position).getYucoins().toString()).toString());
    }

    @Override
    public int getItemCount() {
        Log.e("tag","getItemCount");
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView st_time;
        private TextView st_suffer;
        private TextView st_coin;
        private TextView st_balance;

        public ViewHolder(View itemView) {

            super(itemView);
            Log.e("tag","ViewHolder");
            st_time=  itemView.findViewById(R.id.st_time);
            st_suffer=  itemView.findViewById(R.id.st_suffer);
            st_coin=  itemView.findViewById(R.id.st_coin);
            st_balance=  itemView.findViewById(R.id.st_balance);
            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"data=="+mDatas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if(onItemClickListener!=null){
                      Log.e("tag","我被点击了");
                    }
                }
            });

        }
    }
    /*
    * */
    public  interface OnItemClickListener{
        public  void OnItemClick(View view, String data);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
