package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MoneyDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.domain.GetNumber;
import com.ruixin.administrator.ruixinapplication.popwindow.MultipelPop;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/24.
 * 邮箱：543815830@qq.com
 */
public class TWGameNumberAdapter extends BaseAdapter {
    Context mcontext;
    int[]array;
    double[]rate;
    int[]money;
    Handler handler;
    MultipelPop multipelPop;
    int position;
    List<Integer> list=new ArrayList<>();
    List<String> MoneyPoints;
    private List <MoneyDb> moneyList2= new ArrayList<>();
    List<Integer> mybetpoints;
    String where;
    public TWGameNumberAdapter(Context context,  String where, int []array , double[]rate, int[]money, Handler handler,int position, List<String> MoneyPoints, List<Integer> mybetpoints ) {
        this.mcontext=context;
        this.where=where;
        this.array=array;
        this.rate=rate;
        this.money=money;
        this.handler=handler;
        this.position=position;
        this.MoneyPoints=MoneyPoints;
        this.mybetpoints=mybetpoints;
    }

    @Override
    public int getCount() {
        return array.length;
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
        HashMap mHashMap = new HashMap();
        if (mHashMap.get(i) == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mcontext, R.layout.item_game_number, null);
            mHashMap.put(i, convertView);
            viewHolder.ll_game_number = convertView.findViewById(R.id.ll_game_number);
            viewHolder.ll_mybetpoints = convertView.findViewById(R.id.ll_mybetpoints);
            viewHolder.tv_number = convertView.findViewById(R.id.tv_number);
            viewHolder.tv_mybet_points = convertView.findViewById(R.id.tv_mybet_points);
            viewHolder.iv_1= convertView.findViewById(R.id.iv_1);
            viewHolder.iv_2= convertView.findViewById(R.id.iv_2);
            viewHolder.iv_3= convertView.findViewById(R.id.iv_3);
            viewHolder.tv_peilv = convertView.findViewById(R.id.tv_peilv);
            viewHolder.tv_sub = convertView.findViewById(R.id.tv_sub);
            viewHolder.tv_add = convertView.findViewById(R.id.tv_add);
            viewHolder.et_points = convertView.findViewById(R.id.et_points);
             viewHolder.btn_multiple=convertView.findViewById(R.id.btn_multiple);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(position==0){
            viewHolder.tv_number.setVisibility(View.VISIBLE);
            viewHolder.tv_number.setText(GetNumber.getTws(array[i]));
        }else{
            viewHolder.tv_number.setVisibility(View.GONE);
            list=GetNumber.getTbww(array[i]);
            if(list.size()==3){
                viewHolder.iv_3.setVisibility(View.VISIBLE);
                viewHolder.iv_2.setVisibility(View.VISIBLE);
                viewHolder.iv_1.setVisibility(View.VISIBLE);
                viewHolder.iv_1.setImageResource(list.get(0));
                viewHolder.iv_2.setImageResource(list.get(1));
                viewHolder.iv_3.setImageResource(list.get(2));
            }else if(list.size()==2){
                viewHolder.iv_1.setVisibility(View.VISIBLE);
                viewHolder.iv_2.setVisibility(View.VISIBLE);
                viewHolder.iv_1.setImageResource(list.get(0));
                viewHolder.iv_2.setImageResource(list.get(1));
            }else if(list.size()==1){
                viewHolder.iv_1.setVisibility(View.VISIBLE);
                viewHolder.iv_1.setImageResource(list.get(0));
                //  viewHolder.iv_1.setGravity(Gravity.CENTER_VERTICAL);
            }else if(list.size()==0){
                viewHolder.tv_number.setVisibility(View.VISIBLE);
                viewHolder.tv_number.setText("全骰");
            }
        }
        if(RuiXinApplication.getInstance().getMoneyList()==null){
            viewHolder.et_points.setText("");
            viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#ffffff"));

        }else{
            for(int j=0;j<RuiXinApplication.getInstance().getMoneyList().size();j++){
                if(array[i]==RuiXinApplication.getInstance().getMoneyList().get(j).getNumber()){
                    //RuiXinApplication.getInstance().getMoneyList().add(new MoneyDb(array[i],viewHolder.et_points.getText().toString()));
                    viewHolder.et_points.setText( RuiXinApplication.getInstance().getMoneyList().get(j).getMoney());
                }
            }
            if(viewHolder.et_points.getText().toString().equals("")||viewHolder.et_points.getText().toString().equals("0")){
                viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#ffffff"));
                if(RuiXinApplication.getInstance().getMoneyList()!=null){
                    for(int j=0;j<RuiXinApplication.getInstance().getMoneyList().size();j++){
                        if(array[i]==RuiXinApplication.getInstance().getMoneyList().get(j).getNumber()){
                            RuiXinApplication.getInstance().getMoneyList().remove(j);
                            Message msg = new Message();
                            msg.what =1;
                            handler.sendMessage(msg);
                        }
                    }
                }
            }else{
                viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#d4d4d4"));
                Log.e("RuiXinApplication","RuiXinApplication.getInstance().getMoneyList()"+RuiXinApplication.getInstance().getMoneyList());
                if(RuiXinApplication.getInstance().getMoneyList()==null){
                    moneyList2.clear();
                    moneyList2.add(new MoneyDb(array[i],viewHolder.et_points.getText().toString()));
                    RuiXinApplication.getInstance().setMoneyList(moneyList2);
                }else{
                    for(int j=0;j<RuiXinApplication.getInstance().getMoneyList().size();j++){
                        if(array[i]==RuiXinApplication.getInstance().getMoneyList().get(j).getNumber()){
                            //RuiXinApplication.getInstance().getMoneyList().add(new MoneyDb(array[i],viewHolder.et_points.getText().toString()));
                            RuiXinApplication.getInstance().getMoneyList().get(j).setMoney(viewHolder.et_points.getText().toString());
                        }
                    }
                    RuiXinApplication.getInstance().getMoneyList().add(new MoneyDb(array[i],viewHolder.et_points.getText().toString()));
                }
                Message msg = new Message();
                msg.what =1;
                handler.sendMessage(msg);
            }

        }
        viewHolder.tv_peilv.setText(new StringBuilder().append(rate[i]).toString());
        if(where.equals("1")){
            if(mybetpoints!=null&&mybetpoints.size()>0){
                viewHolder.tv_mybet_points.setText(""+mybetpoints.get(array[i]));
                viewHolder.ll_mybetpoints.setVisibility(View.VISIBLE);
            } else{
                viewHolder.tv_mybet_points.setText("0");
                viewHolder.ll_mybetpoints.setVisibility(View.VISIBLE);
            }
        } else{
            viewHolder.ll_mybetpoints.setVisibility(View.GONE);
        }
        viewHolder.ll_game_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.et_points.getText().toString().equals("")){
                    viewHolder.et_points.setText(""+money[i]);
                    viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#d4d4d4"));
                }else{
                    viewHolder.et_points.setText("");
                    viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }
        });
        viewHolder.et_points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(viewHolder.et_points.getText().toString().equals("")||viewHolder.et_points.getText().toString().equals("0")){
                    viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#ffffff"));
                    for(int j=0;j<RuiXinApplication.getInstance().getMoneyList().size();j++){
                        if(RuiXinApplication.getInstance().getMoneyList().get(j).getNumber()==array[i]){
                            RuiXinApplication.getInstance().getMoneyList().remove(j);
                        }
                    }
                }else{
                    viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#d4d4d4"));
                    if(RuiXinApplication.getInstance().getMoneyList()==null){
                        moneyList2.add(new MoneyDb(array[i],viewHolder.et_points.getText().toString()));
                        RuiXinApplication.getInstance().setMoneyList(moneyList2);

                    }else{
                        RuiXinApplication.getInstance().getMoneyList().add(new MoneyDb(array[i],viewHolder.et_points.getText().toString()));
                    }
                }
                Message msg = new Message();
                msg.what =1;
                handler.sendMessage(msg);
            }
        });
        viewHolder.btn_multiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multipelPop = new MultipelPop(mcontext,  DisplayUtil.dp2px(mcontext, 170),  DisplayUtil.dp2px(mcontext, 120),array[i],viewHolder.et_points);
                //监听窗口的焦点事件，点击窗口外面则取消显示
                multipelPop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            multipelPop.dismiss();
                        }
                    }
                });
                //设置默认获取焦点
                multipelPop.setFocusable(true);
                //以某个控件的x和y的偏移量位置开始显示窗口
                multipelPop.showAsDropDown(view, 0, 0);
                //如果窗口存在，则更新
                multipelPop.update();
            }
        });
        return convertView;
    }
    public interface  SetParams{
        void getParams(String tbChk, String tbNum, String Totalpoints);

    }
    private  SetParams setParams;
  public void SetParamsS(SetParams setParams){
      this.setParams=setParams;
  }
    static  class ViewHolder{
           LinearLayout ll_game_number;
           LinearLayout ll_mybetpoints;
        ImageView iv_1;
        ImageView iv_2;
        ImageView iv_3;
           TextView tv_number;
           TextView tv_mybet_points;
           TextView tv_peilv;
           TextView tv_sub;
           TextView tv_add;
           EditText et_points;
           Button btn_multiple;
    }
}
