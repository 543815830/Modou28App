package com.ruixin.administrator.ruixinapplication.popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.ProductDetailActivity;

/**
 * 作者：Created by ${李丽} on 2018/5/23.
 * 邮箱：543815830@qq.com
 */
public class PopMenu2 extends PopupWindow {
    private View mainView;
   private TextView my_home;
   private TextView my_notice;
   private TextView my_usercenter;
    public PopMenu2(Context paramActivity, ProductDetailActivity.MyOnclickListenter paramOnClickListener, int paramInt1, int paramInt2){
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.popmenu2, null);
        my_home=mainView.findViewById(R.id.my_home);
        my_notice=mainView.findViewById(R.id.my_notice);
        my_usercenter=mainView.findViewById(R.id.my_usercenter);
        //设置每个子布局的事件监听器
        if (paramOnClickListener != null){
            my_usercenter.setOnClickListener(paramOnClickListener);
            my_notice.setOnClickListener(paramOnClickListener);
            my_home.setOnClickListener(paramOnClickListener);
        }
        setContentView(mainView);
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
