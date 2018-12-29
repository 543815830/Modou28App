package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MessageModeEvent;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MoneyDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MybetModeDB;
import com.ruixin.administrator.ruixinapplication.gamecenter.domain.GetNumber;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.BetmodeSSCFragment;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.SsCFragment;
import com.ruixin.administrator.ruixinapplication.popwindow.MultipelPop;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/24.
 * 邮箱：543815830@qq.com
 */
public class SSCGameNumberAdapter extends BaseAdapter {
    Context mcontext;
    String gameType;
    int[]array;
    double[]rate;
    int[]money;
    Handler handler;
    MultipelPop multipelPop;
    List<String> MoneyPoints;
    private List <MoneyDb> moneyList2= new ArrayList<>();
    String mulp;
    List<Integer> mybetpoints;
   String where;
    public SSCGameNumberAdapter(Context context,String where,  String gameType,int []array , double[]rate, int[]money, Handler handler, List<String> MoneyPoints, List<Integer> mybetpoints) {
        this.mcontext=context;
        this.where=where;
        this.gameType=gameType;
        this.array=array;
        this.rate=rate;
        this.money=money;
        this.handler=handler;
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
        if(gameType.equals("ssc")){
            viewHolder.tv_number.setText(GetNumber.getsscs(array[i]));
        } else if(gameType.equals("pkww")){
            viewHolder.tv_number.setText(GetNumber.getpkwws(array[i]));
        }else if(gameType.equals("xync")){
            viewHolder.tv_number.setText(GetNumber.getxyncs(array[i]));
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
      //  Log.e("mon",""+MoneyPoints);

        if(RuiXinApplication.getInstance().getMoneyList()==null){
            viewHolder.et_points.setText("");
            viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#ffffff"));
         //   RuiXinApplication.getInstance().setMoneyList(null);

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
                Log.e("sendMessage2","sendMessage2");
            }

        }
        viewHolder.ll_game_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.et_points.getText().toString().equals("")||viewHolder.et_points.getText().toString().equals("0")){
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
                Log.e("onTextChanged","onTextChanged");
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
                Log.e("sendMessage","sendMessage");
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
    static  class ViewHolder{
           LinearLayout ll_game_number;
           LinearLayout ll_mybetpoints;
           TextView tv_number;
           TextView tv_mybet_points;
           TextView tv_peilv;
           TextView tv_sub;
           TextView tv_add;
           EditText et_points;
           Button btn_multiple;

    }
}
