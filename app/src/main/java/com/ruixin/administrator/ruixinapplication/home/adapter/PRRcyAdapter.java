package com.ruixin.administrator.ruixinapplication.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.home.databean.HomeDb;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/21.
 * 邮箱：543815830@qq.com
 * 首页下的新闻公告的适配器
 */

public class PRRcyAdapter extends RecyclerView.Adapter<PRRcyAdapter.ViewHolder>{
    private Context mContext;
    private List<HomeDb.DataBean.NewsBean> mDatas;

    public PRRcyAdapter(Context mContext, List<HomeDb.DataBean.NewsBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("tag","onCreateViewHolder");
        View itemview=  LayoutInflater.from(mContext).inflate(R.layout.item_press_release,parent,false);//解决子view全屏问题
       // View itemview=View.inflate(mContext, R.layout.item_press_release,null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position%2==0){
            holder.ll_pr.setBackgroundColor(Color.parseColor("#f2f8ff"));
        }
       // mDatas.get(position).getTop().equals("1")
        if(mDatas.get(position).getTop().equals("1")){
            String mTag="[顶]";
            String builder= mTag + mDatas.get(position).getTitle();
            // Log.e("builder",""+builder);
            //设置新的颜色
            SpannableString mBuilder = new SpannableString(builder);
            mBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), 0, mTag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Log.e("tag",builder);
            holder.pr_title.setText(mBuilder);
        }else{
            holder.pr_title.setText(mDatas.get(position).getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout ll_pr;
        private TextView pr_title;
        public ViewHolder(View itemView) {
            super(itemView);
            Log.e("tag","ViewHolder");
            ll_pr=  itemView.findViewById(R.id.ll_pr);
            pr_title=  itemView.findViewById(R.id.pr_tv);

            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"data=="+mDatas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if(onItemClickListener!=null){
                         onItemClickListener.OnItemClick(v,mDatas.get(getLayoutPosition()).getId());
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
    private PRRcyAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(PRRcyAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
