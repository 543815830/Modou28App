package com.ruixin.administrator.ruixinapplication.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.home.databean.GlideRoundImage;
import com.ruixin.administrator.ruixinapplication.home.databean.HomeDb;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/17.
 * 邮箱：543815830@qq.com
 * 首页下的活动专场的适配器
 */

public class HActRcyAdapter extends RecyclerView.Adapter<HActRcyAdapter.ViewHolder>{
    private Context mContext;
    private   List<HomeDb.DataBean.HdlistBean> list;
    public HActRcyAdapter(Context mContext, List<HomeDb.DataBean.HdlistBean> datas) {
        this.mContext=mContext;
        this.list=datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("tag","onCreateViewHolder");
        View itemview=View.inflate(mContext, R.layout.item_hact_spe,null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("tag","onBindViewHolder");
        //holder.act_ic.setImageResource(R.drawable.event_img3);
       // holder.act_title.setText("你亏损趣玩给你返利");

        String path= RuiXinApplication.getInstance().getUrl()+list.get(position).getImage();
        Log.e("tag","u-----"+path);
        Glide.with(mContext)
                .load(path)
                .transform(new CenterCrop(mContext), new GlideRoundImage(mContext))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
              //  .placeholder(R.drawable.event_img3) //占位图
               // .error(R.drawable.event_img3)  //出错的占位图
                .into(holder.hact_ic);
        holder.hact_title.setText(list.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView hact_ic;

        private TextView hact_title;


        public ViewHolder(View itemView) {

            super(itemView);
            hact_ic=  itemView.findViewById(R.id.ic_act);
            hact_title=  itemView.findViewById(R.id.tv_hact_spe);

            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"data=="+mDatas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v,list.get(getLayoutPosition()).getId());
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
