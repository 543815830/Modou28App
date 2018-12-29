package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.exchangemall.domain.CoRecordDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.TrusteeDb;
import com.ruixin.administrator.ruixinapplication.utils.GetGameResult;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 李丽 on 2018/6/19.
 */

public class ConversionRecordAdapter extends BaseAdapter {
    private Context mcontext;
    List<CoRecordDb .DataBean> list;
    public ConversionRecordAdapter(Context mcontext, List<CoRecordDb .DataBean> list) {
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
            convertView= View.inflate(mcontext, R.layout.conver_list_item,null);
            viewHolder.ll_trustee=convertView.findViewById(R.id.ll_trustee);
            viewHolder.tv_prize_name=convertView.findViewById(R.id.tv_prize_name);
            viewHolder.tv_status=convertView.findViewById(R.id.tv_status);
            viewHolder.tv_num=convertView.findViewById(R.id.tv_num);
            viewHolder.tv_time=convertView.findViewById(R.id.tv_time);
            viewHolder.tv_start_end=convertView.findViewById(R.id.tv_start_end);
            viewHolder.iv_del=convertView.findViewById(R.id.iv_del);
            mHashMap.put(i, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_prize_name.setText(list.get(i).getPrizename());
        viewHolder.tv_num.setText(list.get(i).getNum());
        viewHolder.tv_time.setText(list.get(i).getTime());
        if(list.get(i).getState().equals("1")){
            viewHolder.tv_status.setText("已发货");
            viewHolder.tv_start_end.setText("重发");
        }else if(list.get(i).getState().equals("2")){
            viewHolder.tv_status.setText("待发货");
            viewHolder.tv_start_end.setText("/");
        }else if(list.get(i).getState().equals("3")){
            viewHolder.tv_status.setText("已拒绝");
            viewHolder.tv_start_end.setText("—");
        }else{
            viewHolder.tv_status.setText("待审核");
            viewHolder.tv_start_end.setText("取消");
        }


        viewHolder.ll_trustee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClick", "mLlDelete---setOnClickListener" );
                if(onItemClickListener!=null){
                    onItemClickListener.OnItemClick(view, i);
                }
            }
        });
        viewHolder.tv_start_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClick", "mLlDelete---setOnClickListener" );
                if(onStartOrEndClickListener!=null){
                    onStartOrEndClickListener.OnStartOrEndClick(view, i);
                }
            }
        });

        return convertView;
    }
    static class ViewHolder{
        LinearLayout ll_trustee;
               TextView tv_prize_name;
               TextView tv_num;
               TextView tv_time;
               TextView tv_status;
               TextView tv_start_end;
               ImageView iv_del;


    }
    public  interface OnItemClickListener{
        public  void OnItemClick(View view, int i);
    }
    OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public  interface OnStartOrEndClickListener{
        public  void OnStartOrEndClick(View view, int i);
    }
    OnStartOrEndClickListener onStartOrEndClickListener;
    public void setOnStartOrEndClickListener(OnStartOrEndClickListener onStartOrEndClickListener) {
        this.onStartOrEndClickListener = onStartOrEndClickListener;
    }
}
