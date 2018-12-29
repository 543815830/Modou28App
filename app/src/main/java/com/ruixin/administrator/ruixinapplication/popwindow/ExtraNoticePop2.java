package com.ruixin.administrator.ruixinapplication.popwindow;

/**
 * 作者：Created by ${李丽} on 2018/4/12.
 * 邮箱：543815830@qq.com
 * 倍数弹出pop
 */

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.exchangemall.domain.CPrizeDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.GoldDepositDb;

public class ExtraNoticePop2 extends PopupWindow {
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
    EditText et_deposit_coins;
    int num;
    int extra;
    int time;
    GoldDepositDb goldDepositDb;
    public ExtraNoticePop2(Context paramActivity, int paramInt1, int paramInt2, GoldDepositDb goldDepositDb, EditText et_deposit_coins){
        super(paramActivity);
        this.goldDepositDb=goldDepositDb;
        this.et_deposit_coins=et_deposit_coins;
        mMenuView = LayoutInflater.from(paramActivity).inflate(R.layout.extra_fei, null);
        tv_ls=mMenuView.findViewById(R.id.tv_ls);
        tv_dhls=mMenuView.findViewById(R.id.tv_dhls);
        tv_mels=mMenuView.findViewById(R.id.tv_mels);
        tv_time_mels=mMenuView.findViewById(R.id.tv_time_mels);
        lsts= String.valueOf(goldDepositDb.getData().getLssxf().getDetail().getLsts());//最近几日的天数
        sumls= String.valueOf(goldDepositDb.getData().getLssxf().getDetail().getSumls());//游戏流水
        lscoin= String.valueOf(goldDepositDb.getData().getLssxf().getDetail().getSumls()/goldDepositDb.getData().getLssxf().getDetail().getLsbs());//可免额外手续费兑奖
        sumtx= String.valueOf(goldDepositDb.getData().getLssxf().getDetail().getSumtx());//已兑奖
        sscoin= String.valueOf(goldDepositDb.getData().getLssxf().getDetail().getYxls()/goldDepositDb.getData().getLssxf().getDetail().getLsbs());////可免额外手续费兑奖
        if(et_deposit_coins.getText().toString().equals("")){
            truePrice="0";
        }else{
            truePrice= String.valueOf(Integer.parseInt(et_deposit_coins.getText().toString())*1000);
        }
      //  truePrice= et_deposit_coins.getText().toString();//提现数额
       // truePrice= String.valueOf(cPrizeDb.getData().getVipprice()*num);

        lssxbl= String.valueOf(goldDepositDb.getData().getLssxf().getDetail().getLssxbl());
        if(goldDepositDb.getData().getLssxf().getState().equals("1")){
            if(goldDepositDb.getData().getCssxf().getState().equals("1")){//兑换次数手续费
                if(Integer.parseInt(truePrice)-goldDepositDb.getData().getLssxf().getDetail().getYxls()/goldDepositDb.getData().getLssxf().getDetail().getLsbs()>0){
                    Log.e("tag","大于0");
                    extra= (int) (Integer.parseInt(truePrice)-goldDepositDb.getData().getLssxf().getDetail().getYxls()/goldDepositDb.getData().getLssxf().getDetail().getLsbs());
                    shouxufei= String.valueOf(extra*goldDepositDb.getData().getLssxf().getDetail().getLssxbl()/100);
                }else{
                    extra=0;
                    shouxufei="0";
                }
                time=goldDepositDb.getData().getCssxf().getDetail().getSumtimes();
                time_shouxufei= String.valueOf(goldDepositDb.getData().getCssxf().getDetail().getFee());
                extraPrice= String.valueOf(extra);
                tv_mels.setText("本次提现"+truePrice+"金币,超出部分："+extraPrice+"金币按"+lssxbl+"%收取手续费："+shouxufei+"金币");
                tv_time_mels.setText("提现次数手续费：您今天是第"+time+"次提现，需收取"+time_shouxufei+"金币的手续费！");
            }else{
                if(Integer.parseInt(truePrice)-goldDepositDb.getData().getLssxf().getDetail().getYxls()/goldDepositDb.getData().getLssxf().getDetail().getLsbs()>0){
                    Log.e("tag","大于0");
                    extra= (int) (Integer.parseInt(truePrice)-goldDepositDb.getData().getLssxf().getDetail().getYxls()/goldDepositDb.getData().getLssxf().getDetail().getLsbs());
                    shouxufei= String.valueOf(extra*goldDepositDb.getData().getLssxf().getDetail().getLssxbl()/100);
                }else{
                    extra=0;
                    shouxufei="0";
                }
                extraPrice= String.valueOf(extra);
                tv_mels.setText("本次提现"+truePrice+"金币,超出部分："+extraPrice+"金币按"+lssxbl+"%收取手续费："+shouxufei+"金币");
                tv_time_mels.setText("本次没有提现次数手续费！");
            }


        }else if(goldDepositDb.getData().getLssxf().getState().equals("0")&&goldDepositDb.getData().getCssxf().getState().equals("1")){
            shouxufei=  String.valueOf(goldDepositDb.getData().getCssxf().getDetail().getFee());
            tv_mels.setText("本次提现"+truePrice+"金币,您今天是第："+goldDepositDb.getData().getCssxf().getDetail().getSumtimes()+"次提现"+"收取手续费："+shouxufei+"金币");
            tv_time_mels.setText("本次没有兑换次数手续费！");
        }else{
            tv_mels.setText("不收取额外手续费！");
            tv_time_mels.setText("本次没有兑换次数手续费！");
        }


        tv_ls.setText("您最近"+lsts+"日游戏流水："+sumls+",可免额外手续费提现"+lscoin+"金币");
        tv_dhls.setText("您最近"+lsts+"日已提现："+sumtx+",可免额外手续费提现"+sscoin+"金币");

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
