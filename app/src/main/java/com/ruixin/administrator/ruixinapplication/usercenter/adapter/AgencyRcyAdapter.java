package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.Agency;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 */

public class AgencyRcyAdapter extends RecyclerView.Adapter<AgencyRcyAdapter.ViewHolder> {
    private Context mContext;
    private List<Agency.DataBean> list;
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    public AgencyRcyAdapter(Context context,List<Agency.DataBean> list){
        this.mContext=context;
        this.list=list;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agency_pay,null); //解决条目显示不全
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ic_agency.setImageResource(R.mipmap.ic_agency);
        holder.tv_agency.setText(list.get(position).getName());
        Log.e("position",""+position+"---"+list.get(position).getName());
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
        private ImageView ic_agency;
        private TextView tv_agency;
        public ViewHolder(View itemView) {

            super(itemView);
            Log.e("tag","ViewHolder");
            ic_agency=  itemView.findViewById(R.id.ic_gency);
            tv_agency=  itemView.findViewById(R.id.tv_agency);
            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"data=="+mDatas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v,list.get(getLayoutPosition()).getQq());
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
