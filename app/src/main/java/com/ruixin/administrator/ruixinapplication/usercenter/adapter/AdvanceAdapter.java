package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.home.adapter.PRListAdapter;
import com.ruixin.administrator.ruixinapplication.popwindow.PopMenu4;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.AdvanceDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 李丽 on 2018/7/6.
 */

public class AdvanceAdapter extends BaseAdapter {
    Context mContext;
    List<AdvanceDb.DataBean> list;
    SparseBooleanArray mCheckStates=new SparseBooleanArray();
    private List <Integer>mposition=new ArrayList<>();
    public AdvanceAdapter(Context context, List<AdvanceDb.DataBean>list) {
        this.mContext=context;
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
            convertView=View.inflate(mContext, R.layout.item_advaance,null);
            mHashMap.put(i, convertView);
            viewHolder.rl_item_advance=  convertView.findViewById(R.id.rl_item_advance);
            viewHolder.cb_advance=  convertView.findViewById(R.id.cb_advance);
            viewHolder.tv_game_name=  convertView.findViewById(R.id.tv_game_name);
            viewHolder.tv_advance_num=  convertView.findViewById(R.id.tv_advance_num);
            viewHolder.tv_advance_ask=  convertView.findViewById(R.id.tv_advance_ask);
            viewHolder.tv_coin=  convertView.findViewById(R.id.tv_coin);
            viewHolder.tv_shenglv=  convertView.findViewById(R.id.tv_shenglv);
            viewHolder.tv_yingli=  convertView.findViewById(R.id.tv_yingli);
            viewHolder.tv_qishu=  convertView.findViewById(R.id.tv_qishu);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
     viewHolder.tv_game_name.setText(list.get(i).getGamechname());
     viewHolder.tv_advance_num.setText("已闯关数："+list.get(i).getCgnum());
        viewHolder.cb_advance.setTag(i);
        viewHolder.cb_advance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (int) buttonView.getTag();
                if (isChecked) {
                    mCheckStates.put(pos, true);
                    mposition.add(pos);
                } else {
                    mCheckStates.delete(pos);
                    Iterator<Integer> it1 = mposition.iterator();
                    while(it1.hasNext()){
                        int x = it1.next();
                        if(x==pos){
                            it1.remove();
                        }
                    }
                }
            }});
        viewHolder. rl_item_advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(i).getGetprize()==1){
                    if( viewHolder.cb_advance.isChecked()){
                        viewHolder.cb_advance.setChecked(false);
                    }else{
                        viewHolder.cb_advance.setChecked(true);
                    }
                }else  if(list.get(i).getGetprize()==2){
                    Toast.makeText(mContext,"不可领取",Toast.LENGTH_SHORT).show();
                }else  if(list.get(i).getGetprize()==0){
                    Toast.makeText(mContext,"已领取过",Toast.LENGTH_SHORT).show();
                }else  if(list.get(i).getGetprize()==3){
                    Toast.makeText(mContext,"领取完",Toast.LENGTH_SHORT).show();
                }

            }
        });
        viewHolder.cb_advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(i).getGetprize()==1){
                    if( viewHolder.cb_advance.isChecked()){
                        viewHolder.cb_advance.setChecked(false);
                    }else{
                        viewHolder.cb_advance.setChecked(true);
                    }
                }else  if(list.get(i).getGetprize()==2){
                    viewHolder.cb_advance.setChecked(false);
                    Toast.makeText(mContext,"不可领取",Toast.LENGTH_SHORT).show();
                }else  if(list.get(i).getGetprize()==0){
                    viewHolder.cb_advance.setChecked(false);
                    Toast.makeText(mContext,"已领取过",Toast.LENGTH_SHORT).show();
                }else  if(list.get(i).getGetprize()==3){
                    viewHolder.cb_advance.setChecked(false);
                    Toast.makeText(mContext,"领取完",Toast.LENGTH_SHORT).show();
                }

            }
        });
        viewHolder.cb_advance.setChecked(mCheckStates.get(i,false));
     if(list.get(i).getCgnum().equals("0")){
         viewHolder.tv_advance_ask.setText("领取要求：无");
     }else{
         viewHolder.tv_advance_ask.setText("领取要求："+"盈利达到："+list.get(i).getYingli()+"    胜率达到："+list.get(i).getSlv()+"%"+"    参与期数达到："+list.get(i).getQishu()+"期");
     }
     if(list.get(i).getJlmoney()==0){
         viewHolder.tv_coin.setText("无");
     }else{
         viewHolder.tv_coin.setText("+"+list.get(i).getJlmoney());
     }
     if(list.get(i).getNextslv().equals("0")){
         viewHolder.tv_shenglv.setText("0");
     }else if(list.get(i).getNextslv().equals("无")){
         viewHolder.tv_shenglv.setText("无");
     }else{
         viewHolder.tv_shenglv.setText(""+list.get(i).getNextslv()+"%");
     }

   viewHolder.tv_yingli.setText(""+list.get(i).getNextyingli());
   viewHolder.tv_qishu.setText(""+list.get(i).getNextqishu());

     viewHolder.tv_game_name.setText(list.get(i).getGamechname());
        return convertView;
    }
    static  class ViewHolder{
        RelativeLayout rl_item_advance;
        CheckBox cb_advance;
        TextView tv_game_name;
        TextView tv_advance_num;
        TextView tv_advance_ask;
        TextView tv_coin;
        TextView tv_shenglv;
        TextView tv_yingli;
        TextView tv_qishu;

    }
    public interface  SetParams{
        void setParams(List <Integer>mposition);
    }
    private SetParams setParams;
    public void SetPL(SetParams setParams){
        this.setParams=setParams;
        if(setParams!=null){
            setParams.setParams(mposition);
        }
    }
}
