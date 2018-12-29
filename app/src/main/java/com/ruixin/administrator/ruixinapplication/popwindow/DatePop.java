package com.ruixin.administrator.ruixinapplication.popwindow;

/**
 * 作者：Created by ${李丽} on 2018/4/12.
 * 邮箱：543815830@qq.com
 * 盈利统计时间pop
 */
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.DatepopAdapter;
import java.util.ArrayList;
import java.util.List;

public class DatePop extends PopupWindow {
    private View mMenuView; // PopupWindow 菜单布局
    private Context context; // 上下文参数
    private DatepopAdapter adapter;
    private static List<String> list= new ArrayList<String>();
    private OnClickListener myOnClick;
    private  String date;
    private  TextView textView;
    public DatePop(Context paramActivity, int paramInt1, int paramInt2, final List<String>list, View.OnClickListener onclick, final TextView textView){
        super(paramActivity);
        //窗口布局
        mMenuView = LayoutInflater.from(paramActivity).inflate(R.layout.pop_select_time, null);
        ListView date_lv=mMenuView.findViewById(R.id.date_lv);
        this.list=list;
        this.textView=textView;
        this.myOnClick=onclick;
        adapter=new DatepopAdapter(paramActivity,list);
        date_lv.setAdapter(adapter);
        date_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setTextSize(12);
                textView.setText(list.get(i));
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
