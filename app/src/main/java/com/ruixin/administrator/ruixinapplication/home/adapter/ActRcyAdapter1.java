package com.ruixin.administrator.ruixinapplication.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.home.databean.HactDb;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/17.
 * 邮箱：543815830@qq.com
 * 活动专场的适配器
 */

public class ActRcyAdapter1 extends BaseAdapter {
    private Context mContext;
    private  List<HactDb.DataBean> list;
    public ActRcyAdapter1(Context mContext, List<HactDb.DataBean>datas) {
        this.mContext=mContext;
        this.list=datas;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(position) == null) {
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_act_spe,null);
            mHashMap.put(position, convertView);
            viewHolder.act_ic=  convertView.findViewById(R.id.ic_act);
            viewHolder. act_title=  convertView.findViewById(R.id.title_act);
            viewHolder.act_time=convertView.findViewById(R.id.time_act);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(position);
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String path= RuiXinApplication.getInstance().getUrl()+list.get(position).getImage();
        Log.e("tag","u-----"+path);
        Glide.with(mContext)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                // .load("http://b337.photo.store.qq.com/psb?/V10FcMmY1Ttz2o/7.fo01qLQ*SI59*E2Wq.j82HuPfes*efgiyEi7mrJdk!/b/dLHI5cioAQAA&bo=VQOAAgAAAAABB*Q!&rf=viewer_4")
              //  .placeholder() //占位图
              //  .error()  //出错的占位图
                .into(viewHolder.act_ic);
        viewHolder.act_title.setText(list.get(position).getTitle());
        viewHolder.act_time.setText(new StringBuilder().append("活动时间:").append(list.get(position).getStrtime()).append("——").append(list.get(position).getEndtime()).toString());
        viewHolder.act_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClick", "mLlDelete---setOnClickListener" );
                if(onItemClickListener!=null){
                    onItemClickListener.OnItemClick(view,list.get(position).getId());
                }
            }
        }
        );
        return convertView;
    }

   static class ViewHolder {

        private ImageView act_ic;
        private TextView act_title;
        private TextView act_time;

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
