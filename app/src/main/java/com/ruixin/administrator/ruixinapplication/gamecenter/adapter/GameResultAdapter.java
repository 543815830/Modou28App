package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GameHomeDb;
import com.ruixin.administrator.ruixinapplication.utils.GetGameResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/24.
 * 邮箱：543815830@qq.com
 */
public class GameResultAdapter extends BaseAdapter {
    Context mcontext;
    List<GameHomeDb.DataBean.OpenhistoryBean> list=new ArrayList<>();
    BetClickListener betClickListener;
    String gameType;
    public GameResultAdapter(Context context,List<GameHomeDb.DataBean.OpenhistoryBean> list, String gameType) {
        this.mcontext=context;
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(i) == null) {
            viewHolder=new ViewHolder();
            convertView= View.inflate(mcontext, R.layout.game_list_item,null);
            viewHolder.ll_game_open=convertView.findViewById(R.id.ll_game_open);
            viewHolder.ll_game_open2=convertView.findViewById(R.id.ll_game_open2);
            viewHolder.ll_game_open3=convertView.findViewById(R.id.ll_game_open3);
            viewHolder.ll_game_open4=convertView.findViewById(R.id.ll_game_open4);
            viewHolder.ll_game_open5=convertView.findViewById(R.id.ll_game_open5);
            viewHolder.ll_game_open6=convertView.findViewById(R.id.ll_game_open6);
            viewHolder.ll_game_open7=convertView.findViewById(R.id.ll_game_open7);
            viewHolder.iv_1=convertView.findViewById(R.id.iv_1);
            viewHolder.iv_2=convertView.findViewById(R.id.iv_2);
            viewHolder.iv_3=convertView.findViewById(R.id.iv_3);
            viewHolder.tv_game_noid=convertView.findViewById(R.id.tv_game_noid);
            viewHolder.tv_end_time=convertView.findViewById(R.id.tv_end_time);
            viewHolder.tv_game_open=convertView.findViewById(R.id.tv_game_open);
            viewHolder.tv_game_open2=convertView.findViewById(R.id.tv_game_open2);
            viewHolder.tv_game_open3=convertView.findViewById(R.id.tv_game_open3);
            viewHolder.tv_game_open4=convertView.findViewById(R.id.tv_game_open4);
            viewHolder.tv_game_open41=convertView.findViewById(R.id.tv_game_open41);
            viewHolder.tv_game_open42=convertView.findViewById(R.id.tv_game_open42);
            viewHolder.tv_game_open5=convertView.findViewById(R.id.tv_game_open5);
            viewHolder.tv_game_open7=convertView.findViewById(R.id.tv_game_open7);
            viewHolder.tv_game_open71=convertView.findViewById(R.id.tv_game_open71);
            viewHolder.tv_game_open72=convertView.findViewById(R.id.tv_game_open72);
            viewHolder.tv_game_open73=convertView.findViewById(R.id.tv_game_open73);
            viewHolder.tv_game_open74=convertView.findViewById(R.id.tv_game_open74);
            viewHolder.tv_open_sum=convertView.findViewById(R.id.tv_open_sum);
            viewHolder.bet_points_open=convertView.findViewById(R.id.bet_points_open);
            viewHolder.tv_get_points_open=convertView.findViewById(R.id.tv_get_points_open);
            viewHolder.btn_bet=convertView.findViewById(R.id.btn_bet);
            mHashMap.put(i, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_game_noid.setText(GetGameResult.getGno(list.get(i).getNo()));
        viewHolder.tv_end_time.setText(list.get(i).getStoptime());
        if(list.get(i).getIsopened().equals("1")){
            viewHolder.btn_bet.setBackgroundResource(R.drawable.dispress_btn_background1);
            viewHolder.btn_bet.setText("已开奖");
            viewHolder.btn_bet.setTextColor(Color.parseColor("#959595"));
        }else if(list.get(i).getIsopened().equals("2")){
            viewHolder.btn_bet.setBackgroundResource(R.drawable.dispress_btn_background1);
            viewHolder.btn_bet.setText("已作废");
            viewHolder.btn_bet.setTextColor(Color.parseColor("#959595"));
        }else if(list.get(i).getIsopened().equals("3")){
            viewHolder.btn_bet.setText("开奖中");
        }

        if(list.get(i).getIsopened().equals("1")||list.get(i).getIsopened().equals("2")){
            if(list.get(i).getOpenresult()!=null||!list.get(i).getOpenresult().equals("")){

            if(gameType.equals("28")||gameType.equals("16")||gameType.equals("11")||gameType.equals("gy")||gameType.equals("22")||gameType.equals("xn")){
                viewHolder.ll_game_open2.setVisibility(View.GONE);
                viewHolder.ll_game_open3.setVisibility(View.GONE);
                viewHolder.ll_game_open4.setVisibility(View.GONE);
                viewHolder.ll_game_open5.setVisibility(View.GONE);
                viewHolder.ll_game_open6.setVisibility(View.GONE);
                viewHolder.ll_game_open7.setVisibility(View.GONE);
                viewHolder.ll_game_open.setVisibility(View.VISIBLE);
                StringBuilder res = new StringBuilder();
                for(int j=0;j<list.get(i).getOpenresult().size()-1;j++){
                    res.append(list.get(i).getOpenresult().get(j));
                    res.append('+');
                }
                if(res.length()>0){
                    res.deleteCharAt(res.length()-1);
                }
                viewHolder.tv_game_open.setText(new StringBuilder().append(res).append("=").toString());
                viewHolder.tv_open_sum.setText(list.get(i).getOpenresult().get(list.get(i).getOpenresult().size()-1));

            }if(gameType.equals("36")){
                    viewHolder.ll_game_open2.setVisibility(View.GONE);
                    viewHolder.ll_game_open3.setVisibility(View.GONE);
                    viewHolder.ll_game_open4.setVisibility(View.GONE);
                    viewHolder.ll_game_open5.setVisibility(View.GONE);
                    viewHolder.ll_game_open6.setVisibility(View.GONE);
                    viewHolder.ll_game_open7.setVisibility(View.GONE);
                viewHolder.ll_game_open.setVisibility(View.VISIBLE);
                StringBuilder res = new StringBuilder();
                for(int j=0;j<list.get(i).getOpenresult().size()-1;j++){
                    res.append(list.get(i).getOpenresult().get(j));
                    res.append('+');
                }
                if(res.length()>0){
                    res.deleteCharAt(res.length()-1);
                }
                if(list.get(i).getOpenresult().size()>=3){
                    int num1= Integer.parseInt(list.get(i).getOpenresult().get(0));
                    int num2= Integer.parseInt(list.get(i).getOpenresult().get(1));
                    int num3= Integer.parseInt(list.get(i).getOpenresult().get(2));
                    viewHolder.tv_game_open.setText(new StringBuilder().append(res).append("=").toString());
                    viewHolder.tv_open_sum.setText(GetGameResult.get36rs(num1,num2,num3));
                }

            }if(gameType.equals("10")||gameType.equals("xs")){
                    viewHolder.ll_game_open.setVisibility(View.GONE);
                    viewHolder.ll_game_open3.setVisibility(View.GONE);
                    viewHolder.ll_game_open4.setVisibility(View.GONE);
                    viewHolder.ll_game_open5.setVisibility(View.GONE);
                    viewHolder.ll_game_open6.setVisibility(View.GONE);
                    viewHolder.ll_game_open7.setVisibility(View.GONE);
                    viewHolder.ll_game_open6.setVisibility(View.GONE);
                viewHolder.ll_game_open2.setVisibility(View.VISIBLE);
                    if(list.get(i).getOpenresult().size()>=1){
                viewHolder.tv_game_open2.setText(list.get(i).getOpenresult().get(0));}
            }if(gameType.equals("tbww")){
                    viewHolder.ll_game_open.setVisibility(View.GONE);
                    viewHolder.ll_game_open3.setVisibility(View.GONE);
                    viewHolder.ll_game_open4.setVisibility(View.GONE);
                    viewHolder.ll_game_open5.setVisibility(View.GONE);
                    viewHolder.ll_game_open7.setVisibility(View.GONE);
                    viewHolder.ll_game_open6.setVisibility(View.GONE);
                viewHolder.ll_game_open6.setVisibility(View.VISIBLE);
                    if(list.get(i).getOpenresult().size()>=3){
                viewHolder.iv_1.setImageResource(GetGameResult.getTbww(Integer.parseInt(list.get(i).getOpenresult().get(0))));
                viewHolder.iv_2.setImageResource(GetGameResult.getTbww(Integer.parseInt(list.get(i).getOpenresult().get(1))));
                viewHolder.iv_3.setImageResource(GetGameResult.getTbww(Integer.parseInt(list.get(i).getOpenresult().get(2))));}
            }if(gameType.equals("ww")){
                    viewHolder.ll_game_open.setVisibility(View.GONE);
                    viewHolder.ll_game_open3.setVisibility(View.GONE);
                    viewHolder.ll_game_open2.setVisibility(View.GONE);
                    viewHolder.ll_game_open5.setVisibility(View.GONE);
                    viewHolder.ll_game_open7.setVisibility(View.GONE);
                    viewHolder.ll_game_open6.setVisibility(View.GONE);
                viewHolder.ll_game_open4.setVisibility(View.VISIBLE);
                    if(list.get(i).getOpenresult().size()>=4){
                int num1= Integer.parseInt(list.get(i).getOpenresult().get(0));
                int num2= Integer.parseInt(list.get(i).getOpenresult().get(1));
                int num3= Integer.parseInt(list.get(i).getOpenresult().get(2));
                int num4= Integer.parseInt(list.get(i).getOpenresult().get(3));
                viewHolder.tv_game_open4.setText(list.get(i).getOpenresult().get(3));
                viewHolder.tv_game_open41.setText(GetGameResult.getwwrs(num1,num2,num3,num4));
                viewHolder.tv_game_open42.setText(list.get(i).getOpenresult().get(0)+"+"+list.get(i).getOpenresult().get(1)+"+"+list.get(i).getOpenresult().get(2));}
            }if(gameType.equals("dw")){
                    viewHolder.ll_game_open.setVisibility(View.GONE);
                    viewHolder.ll_game_open3.setVisibility(View.GONE);
                    viewHolder.ll_game_open2.setVisibility(View.GONE);
                    viewHolder.ll_game_open5.setVisibility(View.GONE);
                    viewHolder.ll_game_open7.setVisibility(View.GONE);
                    viewHolder.ll_game_open6.setVisibility(View.GONE);
                viewHolder.ll_game_open4.setVisibility(View.VISIBLE);
                    if(list.get(i).getOpenresult().size()>=4){
                int num4= Integer.parseInt(list.get(i).getOpenresult().get(3));
                viewHolder.tv_game_open4.setText(list.get(i).getOpenresult().get(3));
                viewHolder.tv_game_open41.setText(GetGameResult.getdwrs(num4));
                viewHolder.tv_game_open42.setText(list.get(i).getOpenresult().get(0)+"+"+list.get(i).getOpenresult().get(1)+"+"+list.get(i).getOpenresult().get(2));}
            }if(gameType.equals("xync")||gameType.equals("pkww")){
                    viewHolder.ll_game_open.setVisibility(View.GONE);
                    viewHolder.ll_game_open4.setVisibility(View.GONE);
                    viewHolder.ll_game_open2.setVisibility(View.GONE);
                    viewHolder.ll_game_open5.setVisibility(View.GONE);
                    viewHolder.ll_game_open7.setVisibility(View.GONE);
                    viewHolder.ll_game_open6.setVisibility(View.GONE);
                viewHolder.ll_game_open3.setVisibility(View.VISIBLE);
                StringBuilder res = new StringBuilder();
                for(int j=0;j<list.get(i).getOpenresult().size();j++){
                    res.append(list.get(i).getOpenresult().get(j));
                    res.append(',');
                }
                if(res.length()>0){
                    res.deleteCharAt(res.length()-1);
                }
                Log.e("res",res.toString());
                viewHolder.tv_game_open3.setText(res.toString());
            }if(gameType.equals("bjl")){
                    viewHolder.ll_game_open.setVisibility(View.GONE);
                    viewHolder.ll_game_open3.setVisibility(View.GONE);
                    viewHolder.ll_game_open4.setVisibility(View.GONE);
                    viewHolder.ll_game_open5.setVisibility(View.GONE);
                    viewHolder.ll_game_open7.setVisibility(View.GONE);
                    viewHolder.ll_game_open6.setVisibility(View.GONE);
                viewHolder.ll_game_open2.setVisibility(View.VISIBLE);
                    if(list.get(i).getOpenresult().get(0).equals("")){
                        viewHolder.tv_game_open2.setText("");
                    }else{
                        viewHolder.tv_game_open2.setText(GetGameResult.getbjlrs(Integer.parseInt(list.get(i).getOpenresult().get(0))));
                    }
            }if(gameType.equals("lh")){
                    viewHolder.ll_game_open.setVisibility(View.GONE);
                    viewHolder.ll_game_open3.setVisibility(View.GONE);
                    viewHolder.ll_game_open4.setVisibility(View.GONE);
                    viewHolder.ll_game_open5.setVisibility(View.GONE);
                    viewHolder.ll_game_open7.setVisibility(View.GONE);
                    viewHolder.ll_game_open6.setVisibility(View.GONE);
                viewHolder.ll_game_open2.setVisibility(View.VISIBLE);
                    if(list.get(i).getOpenresult().get(0)!=null||!list.get(i).getOpenresult().get(0).equals("")){
                viewHolder.tv_game_open2.setText(GetGameResult.getlhrs(Integer.parseInt(list.get(i).getOpenresult().get(0))));}
            }if(gameType.equals("ssc")){
                    viewHolder.ll_game_open.setVisibility(View.GONE);
                    viewHolder.ll_game_open3.setVisibility(View.GONE);
                    viewHolder.ll_game_open2.setVisibility(View.GONE);
                    viewHolder.ll_game_open5.setVisibility(View.GONE);
                    viewHolder.ll_game_open4.setVisibility(View.GONE);
                    viewHolder.ll_game_open6.setVisibility(View.GONE);
                viewHolder.ll_game_open7.setVisibility(View.VISIBLE);
                    if(list.get(i).getOpenresult().size()>=5){
                viewHolder.tv_game_open7.setText(list.get(i).getOpenresult().get(0));
                viewHolder.tv_game_open71.setText(list.get(i).getOpenresult().get(1));
                viewHolder.tv_game_open72.setText(list.get(i).getOpenresult().get(2));
                viewHolder.tv_game_open73.setText(list.get(i).getOpenresult().get(3));
                viewHolder.tv_game_open74.setText(list.get(i).getOpenresult().get(4));}
            }
            }
        }else{
            viewHolder.ll_game_open.setVisibility(View.GONE);
            viewHolder.ll_game_open3.setVisibility(View.GONE);
            viewHolder.ll_game_open2.setVisibility(View.GONE);
            viewHolder.ll_game_open4.setVisibility(View.GONE);
            viewHolder.ll_game_open7.setVisibility(View.GONE);
            viewHolder.ll_game_open6.setVisibility(View.GONE);
            viewHolder.ll_game_open5.setVisibility(View.VISIBLE);
            viewHolder.tv_game_open5.setText("未开奖");
        }
        viewHolder.bet_points_open.setText(""+list.get(i).getMybetpoints());
        viewHolder.tv_get_points_open.setText(""+list.get(i).getMywinpoints());
        viewHolder.btn_bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(betClickListener!=null){
                    if( viewHolder.btn_bet.getText().toString().equals("开奖中")){
                        betClickListener.OnBetlick(view,i,"3");
                    }else{
                        betClickListener.OnBetlick(view,i,"0");
                    }

                }
            }
        });

        return convertView;
    }
    static  class ViewHolder{
        LinearLayout ll_game_open;
        LinearLayout ll_game_open2;
        LinearLayout ll_game_open3;
        LinearLayout ll_game_open4;
        LinearLayout ll_game_open5;
        LinearLayout ll_game_open6;
        LinearLayout ll_game_open7;
        TextView tv_game_noid;
        TextView tv_end_time;
        TextView tv_game_open;
        TextView tv_game_open2;
        TextView tv_game_open3;
        TextView tv_game_open5;
        TextView tv_game_open7;
        TextView tv_game_open71;
        TextView tv_game_open72;
        TextView tv_game_open73;
        TextView tv_game_open74;
        TextView tv_game_open4;
        TextView tv_game_open41;
        TextView tv_game_open42;
        TextView tv_open_sum;
        TextView bet_points_open;
        TextView tv_get_points_open;
        ImageView iv_1;
        ImageView iv_2;
        ImageView iv_3;
      Button btn_bet;
    }
    public  interface BetClickListener{
        public  void OnBetlick(View view, int i,String tag);
    }
    public void setBetClickListener(BetClickListener betClickListener) {
        this.betClickListener = betClickListener;
    }
}
