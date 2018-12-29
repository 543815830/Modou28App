package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetMultipel;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MyBetDb;
import com.ruixin.administrator.ruixinapplication.utils.GetGameResult;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class MyBetAdapter extends BaseAdapter {
    private Context mContext;
    private  List<MyBetDb.DataBean.MybetlistBean> list;
    String gameType;
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    public MyBetAdapter(Context context, List<MyBetDb.DataBean.MybetlistBean> list, String gameType){
        this.mContext=context;
        this.list=list;
        this.gameType=gameType;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(i) == null) {
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.item_list_mybet,null);
            viewHolder.tv_game_id=convertView.findViewById(R.id.tv_game_id);
            viewHolder.ll_game_result=convertView.findViewById(R.id.ll_game_result);
            viewHolder.ll_game_result2=convertView.findViewById(R.id.ll_game_result2);
            viewHolder.ll_game_result3=convertView.findViewById(R.id.ll_game_result3);
            viewHolder.ll_game_result4=convertView.findViewById(R.id.ll_game_result4);
            viewHolder.ll_game_result5=convertView.findViewById(R.id.ll_game_result5);
            viewHolder.ll_game_result6=convertView.findViewById(R.id.ll_game_result6);
            viewHolder.tv_game_result=convertView.findViewById(R.id.tv_game_result);
            viewHolder.tv_game_result2=convertView.findViewById(R.id.tv_game_result2);
            viewHolder.tv_game_result4=convertView.findViewById(R.id.tv_game_result4);
            viewHolder.tv_game_result5=convertView.findViewById(R.id.tv_game_result5);
            viewHolder.tv_game_result6=convertView.findViewById(R.id.tv_game_result6);
            viewHolder.tv_game_result61=convertView.findViewById(R.id.tv_game_result61);
            viewHolder.tv_game_result62=convertView.findViewById(R.id.tv_game_result62);
            viewHolder.tv_game_result63=convertView.findViewById(R.id.tv_game_result63);
            viewHolder.tv_game_result64=convertView.findViewById(R.id.tv_game_result64);
            viewHolder.tv_game_result41=convertView.findViewById(R.id.tv_game_result41);
            viewHolder.tv_game_result42=convertView.findViewById(R.id.tv_game_result42);
            viewHolder.tv_game_result_sum=convertView.findViewById(R.id.tv_game_result_sum);
            viewHolder.iv_1=convertView.findViewById(R.id.iv_1);
            viewHolder.iv_2=convertView.findViewById(R.id.iv_2);
            viewHolder.iv_3=convertView.findViewById(R.id.iv_3);
            viewHolder.bet_points=convertView.findViewById(R.id.bet_points);
            viewHolder.get_points=convertView.findViewById(R.id.get_points);
            mHashMap.put(i, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_game_id.setText(GetGameResult.getGno(list.get(i).getId()));
        if(gameType.equals("28")||gameType.equals("16")||gameType.equals("11")||gameType.equals("22")||gameType.equals("gy")||gameType.equals("xn")){
            viewHolder.ll_game_result.setVisibility(View.VISIBLE);
            StringBuilder res = new StringBuilder();
            for(int j=0;j<list.get(i).getResult().size()-1;j++){
                res.append(list.get(i).getResult().get(j));
                res.append('+');
            }
            if(res.length()>0){
                res.deleteCharAt(res.length()-1);
            }
            viewHolder.tv_game_result.setText(new StringBuilder().append(res).append("=").toString());
            viewHolder.tv_game_result_sum.setText(list.get(i).getResult().get(list.get(i).getResult().size()-1));
            }if(gameType.equals("36")){
            viewHolder.ll_game_result.setVisibility(View.VISIBLE);
            StringBuilder res = new StringBuilder();
            for(int j=0;j<list.get(i).getResult().size()-1;j++){
                res.append(list.get(i).getResult().get(j));
                res.append('+');
            }
            if(res.length()>0){
                res.deleteCharAt(res.length()-1);
            }
            int num1= Integer.parseInt(list.get(i).getResult().get(0));
            int num2= Integer.parseInt(list.get(i).getResult().get(1));
            int num3= Integer.parseInt(list.get(i).getResult().get(2));
            viewHolder.tv_game_result.setText(new StringBuilder().append(res).append("=").toString());
            viewHolder.tv_game_result_sum.setText(GetGameResult.get36rs(num1,num2,num3));
        }if(gameType.equals("10")||gameType.equals("xs")){
            viewHolder.ll_game_result2.setVisibility(View.VISIBLE);
            viewHolder.tv_game_result2.setText(list.get(i).getResult().get(0));
        }if(gameType.equals("tbww")){
            viewHolder.ll_game_result3.setVisibility(View.VISIBLE);
            viewHolder.iv_1.setImageResource(GetGameResult.getTbww(Integer.parseInt(list.get(i).getResult().get(0))));
            viewHolder.iv_2.setImageResource(GetGameResult.getTbww(Integer.parseInt(list.get(i).getResult().get(1))));
            viewHolder.iv_3.setImageResource(GetGameResult.getTbww(Integer.parseInt(list.get(i).getResult().get(2))));
        }if(gameType.equals("ww")){
            viewHolder.ll_game_result4.setVisibility(View.VISIBLE);
            int num1= Integer.parseInt(list.get(i).getResult().get(0));
            int num2= Integer.parseInt(list.get(i).getResult().get(1));
            int num3= Integer.parseInt(list.get(i).getResult().get(2));
            int num4= Integer.parseInt(list.get(i).getResult().get(3));
            viewHolder.tv_game_result4.setText(list.get(i).getResult().get(3));
            viewHolder.tv_game_result41.setText(GetGameResult.getwwrs(num1,num2,num3,num4));
            viewHolder.tv_game_result42.setText(list.get(i).getResult().get(0)+"+"+list.get(i).getResult().get(1)+"+"+list.get(i).getResult().get(2));
        }if(gameType.equals("dw")){
            viewHolder.ll_game_result4.setVisibility(View.VISIBLE);
            int num4= Integer.parseInt(list.get(i).getResult().get(3));
            viewHolder.tv_game_result4.setText(list.get(i).getResult().get(3));
            viewHolder.tv_game_result41.setText(GetGameResult.getdwrs(num4));
            viewHolder.tv_game_result42.setText(list.get(i).getResult().get(0)+"+"+list.get(i).getResult().get(1)+"+"+list.get(i).getResult().get(2));
        }if(gameType.equals("xync")||gameType.equals("pkww")){
            viewHolder.ll_game_result5.setVisibility(View.VISIBLE);
            StringBuilder res = new StringBuilder();
           for(int j=0;j<list.get(i).getResult().size();j++){
                res.append(list.get(i).getResult().get(j));
                res.append(',');
            }
            if(res.length()>0){
                res.deleteCharAt(res.length()-1);
            }
            viewHolder.tv_game_result5.setText(res.toString());
        }if(gameType.equals("bjl")){
            viewHolder.ll_game_result2.setVisibility(View.VISIBLE);
            viewHolder.tv_game_result2.setText(GetGameResult.getbjlrs(Integer.parseInt(list.get(i).getResult().get(0))));
        }if(gameType.equals("lh")){
            viewHolder.ll_game_result2.setVisibility(View.VISIBLE);
            viewHolder.tv_game_result2.setText(GetGameResult.getlhrs(Integer.parseInt(list.get(i).getResult().get(0))));
        }if(gameType.equals("ssc")){
            viewHolder.ll_game_result6.setVisibility(View.VISIBLE);
            viewHolder.tv_game_result6.setText(list.get(i).getResult().get(0));
            viewHolder.tv_game_result61.setText(list.get(i).getResult().get(1));
            viewHolder.tv_game_result62.setText(list.get(i).getResult().get(2));
            viewHolder.tv_game_result63.setText(list.get(i).getResult().get(3));
            viewHolder.tv_game_result64.setText(list.get(i).getResult().get(4));
        }
            viewHolder.bet_points.setText(list.get(i).getPoints());
            viewHolder.get_points.setText(list.get(i).getHdpoints());
        return convertView;
    }

    static  class ViewHolder{
            LinearLayout ll_game_result;
            LinearLayout ll_game_result2;
            LinearLayout ll_game_result3;
            LinearLayout ll_game_result4;
            LinearLayout ll_game_result5;
            LinearLayout ll_game_result6;
            ImageView iv_1;
            ImageView iv_2;
            ImageView iv_3;
            TextView tv_game_id;
            TextView tv_game_result;
            TextView tv_game_result2;
            TextView tv_game_result4;
            TextView tv_game_result5;
            TextView tv_game_result6;
            TextView tv_game_result61;
            TextView tv_game_result62;
            TextView tv_game_result63;
            TextView tv_game_result64;
            TextView tv_game_result41;
            TextView tv_game_result42;
            TextView tv_game_result_sum;
            TextView bet_points;
            TextView get_points;

    }
}

