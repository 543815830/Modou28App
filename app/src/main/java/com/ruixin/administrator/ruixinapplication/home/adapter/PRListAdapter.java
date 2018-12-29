package com.ruixin.administrator.ruixinapplication.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.home.databean.HPR;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 * 适配新闻公告的适配器
 */

public class PRListAdapter extends BaseAdapter {
    private Context mContext;
    private List<HPR.DataBean> mDatas;

    public PRListAdapter(Context mContext, List<HPR.DataBean> list) {
       this.mContext=mContext;
       this.mDatas=list;

    }

    @Override
    public int getCount() {
      //  Log.e("tag",""+mDatas.size());
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        Log.e("tag","getItem"+i);
        return mDatas.get(i);
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
            convertView=View.inflate(mContext, R.layout.item_press_release,null);
            mHashMap.put(i, convertView);
            viewHolder.pr_tv=  convertView.findViewById(R.id.pr_tv);
            viewHolder.ll_pr=  convertView.findViewById(R.id.ll_pr);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (i%2==0){
            viewHolder.ll_pr.setBackgroundColor(Color.parseColor("#f2f8ff"));
        }
        if(mDatas.get(i).getTop().equals("1")){
            String mTag="[顶]";
            String builder= mTag + mDatas.get(i).getTitle();
            // Log.e("builder",""+builder);
            //设置新的颜色
            SpannableString mBuilder = new SpannableString(builder);
            mBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), 0, mTag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Log.e("tag",builder);
            viewHolder.pr_tv.setText(mBuilder);
        } else{
            viewHolder.pr_tv.setText(mDatas.get(i).getTitle());
        }

        return convertView;
    }
    static  class ViewHolder{
        LinearLayout ll_pr;
        TextView pr_tv;

    }
}
