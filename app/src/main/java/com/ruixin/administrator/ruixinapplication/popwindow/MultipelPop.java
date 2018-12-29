package com.ruixin.administrator.ruixinapplication.popwindow;

/**
 * 作者：Created by ${李丽} on 2018/4/12.
 * 邮箱：543815830@qq.com
 * 倍数弹出pop
 */
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ruixin.administrator.ruixinapplication.R;

import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.BetMultipelAdapter1;


import java.util.ArrayList;
import java.util.List;

public class MultipelPop extends PopupWindow {
    private View mMenuView; // PopupWindow 菜单布局
    private Context context; // 上下文参数
private BetMultipelAdapter1 adapter;
int index;
    private static List<String> list1 = new ArrayList<String>();
    private OnClickListener myOnClick;
    public MultipelPop(Context paramActivity, int paramInt1, int paramInt2, final int index, final EditText et_points){
        super(paramActivity);
        //窗口布局
        list1.clear();
        mMenuView = LayoutInflater.from(paramActivity).inflate(R.layout.multipel_pop, null);
        ListView muitipel_lv=mMenuView.findViewById(R.id.muitipel_lv);
        list1.add("0.5");
        list1.add("2");
        list1.add("10");
        adapter=new BetMultipelAdapter1(paramActivity,list1);
        muitipel_lv.setAdapter(adapter);
        muitipel_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!et_points.getText().toString().equals("")){
                    Double mp1=  Double.parseDouble(list1.get(i));
                    int mo= Integer.parseInt(et_points.getText().toString());
                    et_points.setText(new StringBuilder().append((int) Math.floor(mo * mp1)).toString());
                    if(RuiXinApplication.getInstance().getMoneyList()!=null){
                        for(int j=0;j<RuiXinApplication.getInstance().getMoneyList().size();j++){
                            if(index==RuiXinApplication.getInstance().getMoneyList().get(j).getNumber()){
                                //RuiXinApplication.getInstance().getMoneyList().add(new MoneyDb(array[i],viewHolder.et_points.getText().toString()));
                                RuiXinApplication.getInstance().getMoneyList().get(j).setMoney(et_points.getText().toString());
                            }
                        }
                    }

               //     RuiXinApplication.getInstance().getMoneyList().get(index).setMoney(et_points.getText().toString());

                }
                dismiss();
            }
        });
        // 导入布局
        this.setContentView(mMenuView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(paramInt2);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }
}
