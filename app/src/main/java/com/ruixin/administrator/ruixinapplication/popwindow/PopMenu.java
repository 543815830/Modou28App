package com.ruixin.administrator.ruixinapplication.popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.GameCenterFragment;

/**
 * 作者：Created by ${李丽} on 2018/5/23.
 * 邮箱：543815830@qq.com
 */
public class PopMenu extends PopupWindow {
    private View mainView;
   private TextView my_bet;
   private TextView bet_mod;
   private TextView auto_bet;
   private TextView check_number_bet;
   private TextView doubling_bet;
   private TextView game_tendency;
   private TextView profit_total;
   private TextView game_help;
   private TextView get_xnb;
    public PopMenu(Context paramActivity,String gameType, GameCenterFragment.MyOnclickListener paramOnClickListener, int paramInt1){
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.popmenu, null);
        my_bet=mainView.findViewById(R.id.my_bet);
        bet_mod=mainView.findViewById(R.id.bet_mod);
        auto_bet=mainView.findViewById(R.id.auto_bet);
        check_number_bet=mainView.findViewById(R.id.check_number_bet);
        doubling_bet=mainView.findViewById(R.id.doubling_bet);
        game_tendency=mainView.findViewById(R.id.game_tendency);
        profit_total=mainView.findViewById(R.id.profit_total);
        game_help=mainView.findViewById(R.id.game_help);
        get_xnb=mainView.findViewById(R.id.get_xnb);
        if(gameType.equals("xn")){
            get_xnb.setVisibility(View.VISIBLE);
        }else{
            get_xnb.setVisibility(View.GONE);
        }

        //设置每个子布局的事件监听器
        if (paramOnClickListener != null){
            my_bet.setOnClickListener(paramOnClickListener);
            bet_mod.setOnClickListener(paramOnClickListener);
            auto_bet.setOnClickListener(paramOnClickListener);
            check_number_bet.setOnClickListener(paramOnClickListener);
            doubling_bet.setOnClickListener(paramOnClickListener);
            game_tendency.setOnClickListener(paramOnClickListener);
            profit_total.setOnClickListener(paramOnClickListener);
            game_help.setOnClickListener(paramOnClickListener);
            get_xnb.setOnClickListener(paramOnClickListener);
        }
        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }
}
