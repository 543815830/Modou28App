package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ruixin.administrator.ruixinapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/20.
 * 邮箱：543815830@qq.com
 */

public class EggAdapter extends RecyclerView.Adapter<EggAdapter.ViewHolder>{
    Context mContext;
    List list=new ArrayList();

    public EggAdapter(Context mContext, List list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview=View.inflate(mContext, R.layout.item_egg,null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView egg;

        public ViewHolder(View itemView) {
            super(itemView);
            egg=itemView.findViewById(R.id.iv_egg);
            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"data=="+mDatas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                        if(onItemClickListener!=null){
                            int total= Integer.parseInt(mContext.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_total", ""));
                            if(total>50){
                                egg.setImageResource(R.mipmap.smash_egg);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        egg.setImageResource(R.mipmap.egg);
                                    }
                                },2000);
                            }
                            onItemClickListener.OnItemClick(v, String.valueOf(getItemId()));

                    }
                }
            });
        }
    }
    public  interface OnItemClickListener{
        public  void OnItemClick(View view, String data);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
