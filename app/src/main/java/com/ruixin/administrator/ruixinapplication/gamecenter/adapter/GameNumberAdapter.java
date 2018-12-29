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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.domain.GetNumber;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.Betmode28Fragment;
import com.ruixin.administrator.ruixinapplication.popwindow.MultipelPop;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/24.
 * 邮箱：543815830@qq.com
 */
public class GameNumberAdapter extends BaseAdapter {
    Context mcontext;
    int[]array;
    double[]rate;
    int[]money;
    String mode;
    MultipelPop multipelPop;
    String gameType;
    Handler handler;
    private List <Integer>mposition=new ArrayList<>();
    private List <Integer>mybetpoints=new ArrayList<>();
    private List <String>moneyList=new ArrayList<>();
    String where;
    public GameNumberAdapter(Context context,String where, int []array , double[]rate, int[]money, String mode,String gameType,Handler handler,List <Integer>mybetpoints) {
        this.mcontext=context;
        this.where=where;
        this.array=array;
        this.rate=rate;
        this.money=money;
        this.mode=mode;
        this.gameType=gameType;
        this.handler=handler;
        this.mybetpoints=mybetpoints;
        Log.e("tag", "GameNumberAdapter" );
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
            viewHolder.tv_peilv = convertView.findViewById(R.id.tv_peilv);
            viewHolder.tv_sub = convertView.findViewById(R.id.tv_sub);
            viewHolder.tv_add = convertView.findViewById(R.id.tv_add);
            viewHolder.et_points = convertView.findViewById(R.id.et_points);
            viewHolder.tv_mybet_points = convertView.findViewById(R.id.tv_mybet_points);
            viewHolder.btn_multiple = convertView.findViewById(R.id.btn_multiple);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (gameType.equals("36")) {
            viewHolder.tv_number.setText(GetNumber.get36string(array[i]));
        } else if (gameType.equals("ww")) {
            viewHolder.tv_number.setText(GetNumber.getwws(array[i]));
        } else if (gameType.equals("lh")) {
            viewHolder.tv_number.setText(GetNumber.getlh(array[i]));
        } else if (gameType.equals("bjl")) {
            viewHolder.tv_number.setText(GetNumber.getbjlstring(array[i]));
        } else {
            viewHolder.tv_number.setText(new StringBuilder().append(array[i]).toString());
        }
        viewHolder.tv_peilv.setText(new StringBuilder().append(rate[i]).toString());
        viewHolder.et_points.setText(GetNumber.getMoney(mode,array,money,i,gameType));
        if(where.equals("1")){
            if(mybetpoints!=null&&mybetpoints.size()>0){
                viewHolder.tv_mybet_points.setText(""+mybetpoints.get(i));
                viewHolder.ll_mybetpoints.setVisibility(View.VISIBLE);
            }else{
                viewHolder.tv_mybet_points.setText("0");
                viewHolder.ll_mybetpoints.setVisibility(View.VISIBLE);
            }
        }else{
            viewHolder.ll_mybetpoints.setVisibility(View.GONE);
        }



        if(viewHolder.et_points.getText().toString().equals("")){
            viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#ffffff"));
        }else{
            viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#d4d4d4"));
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
                               }else{
                                   viewHolder.ll_game_number.setBackgroundColor(Color.parseColor("#d4d4d4"));
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
