package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.TrusteeDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.TrusteeDetailDb;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 李丽 on 2018/6/19.
 */

public class TrusteeDetAdapter extends BaseAdapter {
    private Context mcontext;
    List<TrusteeDetailDb.DataBean.ModellistBean>list;
    public TrusteeDetAdapter(Context mcontext,  List<TrusteeDetailDb.DataBean.ModellistBean>list) {
        this.mcontext = mcontext;
        this.list=list;
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
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(i) == null) {
            viewHolder=new ViewHolder();
            convertView= View.inflate(mcontext, R.layout.item_trustee_det,null);
            viewHolder.ll_item_trustee=convertView.findViewById(R.id.ll_item_trustee);
            viewHolder.tv_model=convertView.findViewById(R.id.tv_model);
            viewHolder.tv_win_model=convertView.findViewById(R.id.tv_win_model);
            viewHolder.tv_loss_model=convertView.findViewById(R.id.tv_loss_model);
            mHashMap.put(i, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
           viewHolder.tv_model.setText(list.get(i).getModel());
           viewHolder.tv_win_model.setText(list.get(i).getWinmodel());
           viewHolder.tv_loss_model.setText(list.get(i).getLostmodel());
        if (i%2==0){
            viewHolder.ll_item_trustee.setBackgroundColor(Color.parseColor("#f2f8ff"));
        }
        return convertView;
    }
    static class ViewHolder{
        LinearLayout ll_item_trustee;
               TextView tv_model;
               TextView tv_win_model;
               TextView tv_loss_model;


    }
    public  interface OnItemClickListener{
        public  void OnItemClick(View view, int i);
    }
    OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public  interface OnDelClickListener{
        public  void OnDelClick(View view, int i);
    }
    OnDelClickListener onDelClickListener;
    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        this.onDelClickListener = onDelClickListener;
    }
    public  interface OnStartOrEndClickListener{
        public  void OnStartOrEndClick(View view, int i);
    }
    OnStartOrEndClickListener onStartOrEndClickListener;
    public void setOnStartOrEndClickListener(OnStartOrEndClickListener onStartOrEndClickListener) {
        this.onStartOrEndClickListener = onStartOrEndClickListener;
    }
}
