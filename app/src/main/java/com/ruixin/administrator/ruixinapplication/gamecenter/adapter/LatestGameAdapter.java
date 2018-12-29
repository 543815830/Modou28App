package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GameName1;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.AgencyRcyAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.Agency;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/24.
 * 邮箱：543815830@qq.com
 */
public class LatestGameAdapter extends RecyclerView.Adapter<LatestGameAdapter.ViewHolder> {
    private Context mContext;
    private List<GameName1.DataBean.LatesgamelistBean> list;
    public LatestGameAdapter(Context context,List<GameName1.DataBean.LatesgamelistBean> list){
        this.mContext=context;
        this.list=list;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_latest_name,null); //解决条目显示不全
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
       holder.tv_latest_hot_name.setText(list.get(position).getGamechname());
       holder.tv_latest_hot_name.setTag(position);
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
        private TextView tv_latest_hot_name;
        public ViewHolder(View itemView) {

            super(itemView);
            tv_latest_hot_name=  itemView.findViewById(R.id.tv_latest_hot_name);
            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"data=="+mDatas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if(onItemClickListener!=null){
                       onItemClickListener.OnItemClick(v,list.get(getLayoutPosition()).getGamechname(),list.get(getLayoutPosition()).getGamename(),list.get(getLayoutPosition()).getGametype());
                    }
                }
            });

        }
    }
    /*
     * */
    public  interface OnItemClickListener{
        public  void OnItemClick(View view, String LgameName,String LEgameName,String LgameType);
    }
    private LatestGameAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(LatestGameAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
