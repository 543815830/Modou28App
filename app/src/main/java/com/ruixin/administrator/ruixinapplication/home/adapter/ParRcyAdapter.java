package com.ruixin.administrator.ruixinapplication.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.home.databean.Partner;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/17.
 * 邮箱：543815830@qq.com
 * 合作伙伴的适配器
 */

public class ParRcyAdapter extends RecyclerView.Adapter<ParRcyAdapter.ViewHolder>{
    private Context mContext;
    private List<Partner> list;
    public ParRcyAdapter(Context mContext, List<Partner> datas) {
        this.mContext=mContext;
        this.list=datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("tag","onCreateViewHolder");
        View itemview=View.inflate(mContext, R.layout.item_partner,null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("tag","onBindViewHolder");
        holder.partner_ic.setImageResource(list.get(position).getImg());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView partner_ic;


        public ViewHolder(View itemView) {

            super(itemView);
            partner_ic=  itemView.findViewById(R.id.ic_partner);

            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"data=="+mDatas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if(onItemClickListener!=null){
                       // onItemClickListener.OnItemClick(v,datas.get(getLayoutPosition()));
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
