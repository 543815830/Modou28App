package com.ruixin.administrator.ruixinapplication.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.home.databean.HomeDb;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/21.
 * 邮箱：543815830@qq.com
 * 首页下的热门奖品的适配器
 */

public class HHotPrizeRcyAdapter extends RecyclerView.Adapter<HHotPrizeRcyAdapter.ViewHolder>{
    private Context mContext;
    private   List<HomeDb.DataBean.HotprizeBean>list;
    public HHotPrizeRcyAdapter(Context context, List<HomeDb.DataBean.HotprizeBean> list) {
        this.mContext=context;
        this.list=list;
        Log.e("tag","ades"+list);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=View.inflate(mContext, R.layout.item_hotprize2,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("tag","onBindViewHolder");
       // holder.hot_prize_ic.setImageResource(list.get(position).getImg());
        String path= RuiXinApplication.getInstance().getUrl()+list.get(position).getImgsrc();
        Log.e("tag","u-----"+path);
        Glide.with(mContext)
                .load(path)
                // .load("http://b337.photo.store.qq.com/psb?/V10FcMmY1Ttz2o/7.fo01qLQ*SI59*E2Wq.j82HuPfes*efgiyEi7mrJdk!/b/dLHI5cioAQAA&bo=VQOAAgAAAAABB*Q!&rf=viewer_4")
                //.placeholder() //占位图
              //  .error()  //出错的占位图
                .into(holder.hhot_prize_ic);
        holder.htitle_hot_prize.setText(list.get(position).getName());
       /* String mTag="兑换价：";
        String builder= mTag + FormatUtils.formatString(list.get(position).getPoints());
        // Log.e("builder",""+builder);
        //设置新的颜色
        SpannableString mBuilder = new SpannableString(builder);
        mBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#454545")), 0, mTag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), mTag.length(), builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        holder.hprice_hot_prize.setText(""+list.get(position).getPoints());
        holder.hnumber_hot_prize.setText("已兑"+list.get(position).getConvertnum()+"笔");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView hhot_prize_ic;

        private TextView htitle_hot_prize;

        private TextView hprice_hot_prize;
        private TextView hnumber_hot_prize;
        public ViewHolder(View itemView) {

            super(itemView);
            hhot_prize_ic=  itemView.findViewById(R.id.ic_hot_prize);
            htitle_hot_prize=  itemView.findViewById(R.id.title_hot_prize);
            hprice_hot_prize=itemView.findViewById(R.id.price_hot_prize);
            hnumber_hot_prize=itemView.findViewById(R.id.price_hot_number);
          /*  conversion.setClickable(false);
            conversion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("tag",""+getLayoutPosition());
                    if(onbtnClickListener!=null){
                        onbtnClickListener.OnbtnClick(view,list.get(getLayoutPosition()).getId());
                    }
                }
            });*/
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
    public  interface OnbtnClickListener{
        public  void OnbtnClick(View view, String data);
    }
    private OnbtnClickListener onbtnClickListener;

    public void setOnbtnClickListener(OnbtnClickListener onbtnClickListener ) {
        this.onbtnClickListener = onbtnClickListener;
    }
}
