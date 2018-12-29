package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.TrusteeDb;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MailBoxAdapter;
import com.ruixin.administrator.ruixinapplication.utils.GetGameResult;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 李丽 on 2018/6/19.
 */

public class TrusteeAdapter extends BaseAdapter {
    private Context mcontext;
    List<TrusteeDb.DataBean.AutolistBean> list;
    public TrusteeAdapter(Context mcontext,List<TrusteeDb.DataBean.AutolistBean> list) {
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
            convertView= View.inflate(mcontext, R.layout.trustee_list_item,null);
            viewHolder.ll_trustee=convertView.findViewById(R.id.ll_trustee);
            viewHolder.ll_start_end=convertView.findViewById(R.id.ll_start_end);
            viewHolder.ll_update=convertView.findViewById(R.id.ll_update);
            viewHolder.ll_del=convertView.findViewById(R.id.ll_del);
            viewHolder.tv_noid=convertView.findViewById(R.id.tv_noid);
            viewHolder.trustee_name=convertView.findViewById(R.id.trustee_name);
            viewHolder.tv_status=convertView.findViewById(R.id.tv_status);
            viewHolder.game_name=convertView.findViewById(R.id.game_name);
            viewHolder.bet_mode_tv=convertView.findViewById(R.id.bet_mode_tv);
            viewHolder.tv_start_end=convertView.findViewById(R.id.tv_start_end);
            viewHolder.iv_update=convertView.findViewById(R.id.iv_update);
            viewHolder.iv_del=convertView.findViewById(R.id.iv_del);
            mHashMap.put(i, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_noid.setText(list.get(i).getId());
        if(list.get(i).getName()==null){
            viewHolder.trustee_name.setVisibility(View.GONE);
        }else{
            viewHolder.trustee_name.setText(list.get(i).getName());
        }


        if(list.get(i).getState().equals("1")){
            viewHolder.tv_status.setText("运行中");
            viewHolder.tv_start_end.setImageResource(R.mipmap.tingzhi);

        }else{
            viewHolder.tv_status.setText("已停止");
            viewHolder.tv_start_end.setImageResource(R.mipmap.yunxing);
        }

        viewHolder.game_name.setText(list.get(i).getChgamename());

        if(list.get(i).getType().equals("1")){
            viewHolder.bet_mode_tv.setText("高级模式");
        }else if(list.get(i).getType().equals("2")){
            viewHolder.bet_mode_tv.setText("对号模式");
        }else if(list.get(i).getType().equals("3")){
            viewHolder.bet_mode_tv.setText("翻倍模式");
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
        viewHolder.ll_start_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClick", "mLlDelete---setOnClickListener" );
                if(onStartOrEndClickListener!=null){
                    onStartOrEndClickListener.OnStartOrEndClick(view, i);
                }
            }
        });
        viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClick", "mLlDelete---setOnClickListener" );
                if(onDelClickListener!=null){
                    onDelClickListener.OnDelClick(view, i);
                }
            }
        });
        viewHolder.ll_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(onClickListener!=null){
                        onClickListener.OnivClick(view, i);
                    }
            }
        });
        return convertView;
    }
    static class ViewHolder{
        LinearLayout ll_trustee;
        LinearLayout ll_update;
        LinearLayout ll_start_end;
        LinearLayout ll_del;
               TextView tv_noid;
               TextView trustee_name;
               TextView tv_status;
               TextView game_name;
               TextView bet_mode_tv;
             ImageView  tv_start_end;
               ImageView iv_update;
               ImageView iv_del;


    }
    public  interface OnItemClickListener{
        public  void OnItemClick(View view,int i);
    }
    OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public  interface OnDelClickListener{
        public  void OnDelClick(View view,int i);
    }
    OnDelClickListener onDelClickListener;
    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        this.onDelClickListener = onDelClickListener;
    }
    public  interface OnStartOrEndClickListener{
        public  void OnStartOrEndClick(View view,int i);
    }
    OnStartOrEndClickListener onStartOrEndClickListener;
    public void setOnStartOrEndClickListener(OnStartOrEndClickListener onStartOrEndClickListener) {
        this.onStartOrEndClickListener = onStartOrEndClickListener;
    }

    public  interface OnClickListener{
        public  void OnivClick(View view,int i);
    }
    OnClickListener onClickListener;
    public void setOnivClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
