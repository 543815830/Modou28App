package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetMode;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetMultipel;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.AgencyRcyAdapter;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class BetMultipelAdapter extends RecyclerView.Adapter<BetMultipelAdapter.ViewHolder> {
    private Context mContext;
    private List<BetMultipel> list;
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    public BetMultipelAdapter(Context context, List<BetMultipel> list){
        this.mContext=context;
        this.list=list;

    }
    @Override
    public BetMultipelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bet_multipel,null); //解决条目显示不全
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(list.get(position).getmultipel().equals("反选")||list.get(position).getmultipel().equals("清除")||list.get(position).getmultipel().equals("梭哈")||list.get(position).getmultipel().equals("上期投注")){
            holder.tv_bet_multipel.setText(list.get(position).getmultipel());
        }else{
            holder.tv_bet_multipel.setText(list.get(position).getmultipel()+"倍");
        }

    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll_mulp;
        private TextView tv_bet_multipel;
        public ViewHolder(View itemView) {

            super(itemView);
            Log.e("tag","ViewHolder");
            ll_mulp=  itemView.findViewById(R.id.ll_mulp);
            tv_bet_multipel=  itemView.findViewById(R.id.tv_bet_multipel);
            //设置点击事件
            ll_mulp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"data=="+mDatas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v,list.get(getLayoutPosition()).getmultipel());
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

