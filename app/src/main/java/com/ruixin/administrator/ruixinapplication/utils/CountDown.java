package com.ruixin.administrator.ruixinapplication.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by 李丽 on 2018/6/12.
 */

public class CountDown extends CountDownTimer{
private  TextView textView;
private Handler handler;


    public CountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }
    public CountDown(long millisInFuture, long countDownInterval, TextView textView,Handler handler) {
        super(millisInFuture, countDownInterval);
        Log.e("CountDown", "CountDown"  );
        this.textView=textView;
        this.handler=handler;
    }

    @Override
    public void onTick(long millisInFuture) {
            textView.setTextSize(15);
            textView.setText(formatTime(millisInFuture));
    }

    @Override
    public void onFinish() {
        Log.e("onFinish", "onFinish"  );
        textView.setTextSize(12);
        textView.setText("正在开奖中，请稍后...");
        Message msg1 = new Message();
        msg1.what =5;
        handler.sendMessage(msg1);
    }
    /**
     * 将毫秒转化为 分钟：秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public String formatTime(long millisecond) {
        int second;//秒数
        second = (int) ((millisecond / 1000) );
        return""+ second;
    }


}
