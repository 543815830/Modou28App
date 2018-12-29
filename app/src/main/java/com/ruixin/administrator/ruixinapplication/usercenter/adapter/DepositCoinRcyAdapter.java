package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.Agency;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 */

public class DepositCoinRcyAdapter extends RecyclerView.Adapter<DepositCoinRcyAdapter.ViewHolder> {
    private Context mContext;
    private int[]array;
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    public DepositCoinRcyAdapter(Context context,int[]array){
        this.mContext=context;
        this.array=array;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deposit_coins,null); //解决条目显示不全
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_coins.setText(""+array[position]);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return array.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
     //   private LinearLayout ll__pa_coins;
        private LinearLayout ll_coins;
        private TextView tv_coins;
        public ViewHolder(View itemView) {

            super(itemView);
            Log.e("tag","ViewHolder");
          /*  ll__pa_coins=itemView.findViewById(R.id.ll_pa_coins);
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            int  width = dm.widthPixels;
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) ll__pa_coins.getLayoutParams();
//获取当前控件的布局对象
            params.width=width/3;//设置当前控件布局的高度*/
           // ll__pa_coins.setLayoutParams(params);
            ll_coins=  itemView.findViewById(R.id.ll_coins);
            tv_coins=  itemView.findViewById(R.id.tv_coins);
            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"data=="+mDatas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, String.valueOf(array[getLayoutPosition()]));
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
