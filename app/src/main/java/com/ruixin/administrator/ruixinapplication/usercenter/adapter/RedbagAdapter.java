package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.home.adapter.PRListAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.RedbagActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.GetRedbagDb2;
import com.ruixin.administrator.ruixinapplication.usercenter.utils.CenterImage;

import java.util.HashMap;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by 李丽 on 2018/8/2.
 */

public class RedbagAdapter extends BaseAdapter {
    Context mContext;
    List<GetRedbagDb2.DataBean.ListBean> list;
    String luckyId;
    String path;
    public RedbagAdapter(Context context,List<GetRedbagDb2.DataBean.ListBean> list,String id) {
        this.mContext=context;
        this.list=list;
        this.luckyId=id;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(i) == null) {
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_red_bag_list,null);
            mHashMap.put(i, convertView);
            viewHolder.ll_guanghuan=  convertView.findViewById(R.id.ll_guanghuan);
            viewHolder.touxiang=  convertView.findViewById(R.id.touxiang);
            viewHolder.tv_getuser_name=  convertView.findViewById(R.id.tv_getuser_name);
            viewHolder.tv_getuser_points=  convertView.findViewById(R.id.tv_getuser_points);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(luckyId!=null){
            if(list.get(i).getGetuid().equals(luckyId)){
                Log.e("tag","u-----"+luckyId);
                viewHolder.ll_guanghuan.setBackgroundResource(R.drawable.guanghuan);
            }
            Log.e("tag","u-----"+luckyId);
           // viewHolder.touxiang.setCenterImgShow(true);
        }
        viewHolder.tv_getuser_name.setText(list.get(i).getName());
        viewHolder.tv_getuser_points.setText(list.get(i).getPoints());
        if(!list.get(i).getHead().contains("http")){
            path= RuiXinApplication.getInstance().getUrl()+list.get(i).getHead();
        }else{
            path=list.get(i).getHead();
        }

        Log.e("tag","u-----"+path);
        Glide.with(mContext)
                .load(path)
                . bitmapTransform(new RoundedCornersTransformation(mContext, 10, 0, RoundedCornersTransformation.CornerType.ALL))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (viewHolder.touxiang == null) {
                            return false;
                        }
                        if (viewHolder.touxiang .getScaleType() != ImageView.ScaleType.FIT_XY) {
                            viewHolder.touxiang .setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = viewHolder.touxiang .getLayoutParams();
                        int vw = viewHolder.touxiang .getWidth();
                        //float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = (int) ((float)vw/(float) 1);
                        params.height = vh;
                        viewHolder.touxiang .setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(R.drawable.touxiang) //占位图
                .error(R.drawable.touxiang)  //出错的占位图
                .into(viewHolder.touxiang);
        return convertView;
    }
    static  class ViewHolder{
        LinearLayout ll_guanghuan;
        ImageView touxiang;
        TextView tv_getuser_name;
        TextView tv_getuser_points;

    }
}
