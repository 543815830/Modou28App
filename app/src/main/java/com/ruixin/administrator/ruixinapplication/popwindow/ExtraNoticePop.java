package com.ruixin.administrator.ruixinapplication.popwindow;

/**
 * 作者：Created by ${李丽} on 2018/4/12.
 * 邮箱：543815830@qq.com
 * 倍数弹出pop
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.exchangemall.domain.CPrizeDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.BetMultipelAdapter1;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class ExtraNoticePop extends PopupWindow {
    private View mMenuView; // PopupWindow 菜单布局
    private Context context; // 上下文参数
    private OnClickListener myOnClick;
    TextView tv_ls;
    TextView tv_dhls;
    TextView tv_mels;
    TextView tv_time_mels;
    String lsts;//●游戏流水手续费:您最近1日游戏流水：0,可免额外手续费兑奖0金币   您最近1日已兑奖：0 可免额外手续费兑奖0金币,本次兑奖105,000金币,超出部分：105,000按2%收取手续费：2,100金币//最近游戏流水天数/最近兑奖天数
    String sumls;//游戏流水
    String lscoin;//可免除额外手续费兑奖金额sumls/lsbs
    String sumtx;//最近已兑奖金额
    String sscoin;//可免除额外手续费兑奖金yxls/lsbs(已兑奖游戏流水/流水倍数）
    String lssxbl;//手续费2%
    String truePrice;
    String extraPrice;
    String shouxufei;
    String time_shouxufei;
    CPrizeDb cPrizeDb;
    EditText etnum;
    int num;
    int extra;
    int time;
    public ExtraNoticePop(Context paramActivity, int paramInt1, int paramInt2,  CPrizeDb cPrizeDb,EditText etnum){
        super(paramActivity);
        this.cPrizeDb=cPrizeDb;
        this.etnum=etnum;
        mMenuView = LayoutInflater.from(paramActivity).inflate(R.layout.extra_fei, null);
        tv_ls=mMenuView.findViewById(R.id.tv_ls);
        tv_dhls=mMenuView.findViewById(R.id.tv_dhls);
        tv_mels=mMenuView.findViewById(R.id.tv_mels);
        tv_time_mels=mMenuView.findViewById(R.id.tv_time_mels);
        lsts=cPrizeDb.getData().getLsts();
        sumls= String.valueOf(cPrizeDb.getData().getSumls());
        lscoin= String.valueOf(cPrizeDb.getData().getSumls()/cPrizeDb.getData().getLsbs());
        sumtx= String.valueOf(cPrizeDb.getData().getSumtx());
        sscoin= String.valueOf(cPrizeDb.getData().getYxls()/cPrizeDb.getData().getLsbs());
        num= Integer.parseInt(etnum.getText().toString());
        truePrice= String.valueOf(cPrizeDb.getData().getVipprice()*num);

        lssxbl= String.valueOf(cPrizeDb.getData().getLssxbl());
        if(cPrizeDb.getData().getLssxfopen().equals("1")){
            if(cPrizeDb.getData().getCssxfopen().equals("1")){//兑换次数手续费
                if(cPrizeDb.getData().getVipprice()*num-cPrizeDb.getData().getYxls()/cPrizeDb.getData().getLsbs()>0){
                    Log.e("tag","大于0");
                    extra= (int) (cPrizeDb.getData().getVipprice()*num-cPrizeDb.getData().getYxls()/cPrizeDb.getData().getLsbs());
                    shouxufei= String.valueOf(extra*cPrizeDb.getData().getLssxbl()/100);
                }else{
                    extra=0;
                    shouxufei="0";
                }
               time=cPrizeDb.getData().getCssxf().getSumtimes();
                time_shouxufei= String.valueOf(cPrizeDb.getData().getCssxf().getFee());
                extraPrice= String.valueOf(extra);
                tv_mels.setText("本次兑奖"+truePrice+"金币,超出部分："+extraPrice+"金币按"+lssxbl+"%收取手续费："+shouxufei+"金币");
                tv_time_mels.setText("兑奖次数手续费：您今天是第"+time+"次兑换，需收取"+time_shouxufei+"金币的手续费！");
            }else{
                if(cPrizeDb.getData().getVipprice()*num-cPrizeDb.getData().getYxls()/cPrizeDb.getData().getLsbs()>0){
                    Log.e("tag","大于0");
                    extra= (int) (cPrizeDb.getData().getVipprice()*num-cPrizeDb.getData().getYxls()/cPrizeDb.getData().getLsbs());
                    shouxufei= String.valueOf(extra*cPrizeDb.getData().getLssxbl()/100);
                }else{
                    extra=0;
                    shouxufei="0";
                }
                extraPrice= String.valueOf(extra);
                tv_mels.setText("本次兑奖"+truePrice+"金币,超出部分："+extraPrice+"金币按"+lssxbl+"%收取手续费："+shouxufei+"金币");
                tv_time_mels.setText("本次没有兑换次数手续费！");
            }


        }else if(cPrizeDb.getData().getLssxfopen().equals("0")&&cPrizeDb.getData().getCssxfopen().equals("1")){
            shouxufei= String.valueOf(cPrizeDb.getData().getCssxf().getFee());
            tv_mels.setText("本次兑奖"+truePrice+"金币,您今天是第："+cPrizeDb.getData().getCssxf().getSumtimes()+"次兑奖"+"收取手续费："+shouxufei+"金币");
            tv_time_mels.setText("本次没有兑换次数手续费！");
        }else{
            tv_mels.setTag("不收取额外手续费！");
            tv_time_mels.setText("本次没有兑换次数手续费！");
        }


        tv_ls.setText("您最近"+lsts+"日游戏流水："+sumls+",可免额外手续费兑奖"+lscoin+"金币");
        tv_dhls.setText("您最近"+lsts+"日已兑奖："+sumtx+",可免额外手续费兑奖"+sscoin+"金币");

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
