package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetMode;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.AgencyRcyAdapter;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class BetModeAdapter extends RecyclerView.Adapter<BetModeAdapter.ViewHolder> {
    private Context mContext;
    private  List<BetMode> list;
    boolean first=true;
    SparseBooleanArray mCheckStates=new SparseBooleanArray();
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    public BetModeAdapter(Context context,List<BetMode> list){
        this.mContext=context;
        this.list=list;

    }
    @Override
    public BetModeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bet_mode,null); //解决条目显示不全
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_bet_mode.setText(list.get(position).getModename());

       // holder.tv_select_number.setText(list.get(position).getName());*/
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
        private LinearLayout ll_pa_mode;
        private LinearLayout ll_mode;
        private TextView tv_bet_mode;
        private TextView tv_select_number;
        public ViewHolder(final View itemView) {

            super(itemView);
            Log.e("tag","ViewHolder");
            ll_pa_mode=itemView.findViewById(R.id.ll_pa_mode);
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
          int  width = dm.widthPixels;
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) ll_pa_mode.getLayoutParams();
//获取当前控件的布局对象
            params.width=width/4;//设置当前控件布局的高度
            ll_pa_mode.setLayoutParams(params);
            ll_mode=itemView.findViewById(R.id.ll_mode);
            tv_bet_mode=  itemView.findViewById(R.id.tv_bet_mode);
            //设置点击事件
             ll_mode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"data=="+mDatas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v,list.get(getLayoutPosition()).getModename());
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

