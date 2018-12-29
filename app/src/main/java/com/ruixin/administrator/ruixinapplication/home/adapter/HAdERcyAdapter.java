package com.ruixin.administrator.ruixinapplication.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.home.databean.HomeDb;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/19.
 * 邮箱：543815830@qq.com
 * 广告体验的适配器
 */

public class HAdERcyAdapter extends RecyclerView.Adapter<HAdERcyAdapter.ViewHolder>{
    private Context mContext;
    private List<HomeDb.DataBean.GamelistBean>list;
    public HAdERcyAdapter(Context context, List<HomeDb.DataBean.GamelistBean> list) {
        this.mContext=context;
        this.list=list;
        Log.e("tag","ades"+list);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // View itemView=View.inflate(mContext, R.layout.item_ade,null);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ade,null); //解决条目显示不全
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("tag","onBindViewHolder");
        String path= RuiXinApplication.getInstance().getUrl()+list.get(position).getImg();
        Log.e("tag","u-----"+path);
        Glide.with(mContext)
                .load(path)
                // .load("http://b337.photo.store.qq.com/psb?/V10FcMmY1Ttz2o/7.fo01qLQ*SI59*E2Wq.j82HuPfes*efgiyEi7mrJdk!/b/dLHI5cioAQAA&bo=VQOAAgAAAAABB*Q!&rf=viewer_4")
                //.placeholder() //占位图
               // .error()  //出错的占位图
                .into(holder.hade_ic);
       holder.htitle_ade.setText(list.get(position).getTilte());
        holder.hade_website.setText("官网");
        holder.hade_v.setVisibility(View.VISIBLE);
        holder.hade_gift_bag.setText("礼包");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView hade_ic;

        private TextView htitle_ade;
        private TextView hade_website;
        private TextView hade_gift_bag;
        private View hade_v;
        public ViewHolder(View itemView) {

            super(itemView);
            hade_ic=  itemView.findViewById(R.id.ic_ade);
            htitle_ade=  itemView.findViewById(R.id.title_ade);
            hade_website=  itemView.findViewById(R.id.ade_website);
            hade_gift_bag=  itemView.findViewById(R.id.ade_gift_bag);
            hade_v=itemView.findViewById(R.id.ade_v);
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
