package com.ruixin.administrator.ruixinapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;

/**
 * Created by 李丽 on 2018/8/3.
 */

public class SelfDialog extends Dialog {
    private ImageView yes;//确定按钮
    private TextView tv_get_redbag;//获取多少红包
    private String messageStr;//从外界设置的消息文本
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    public SelfDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redbag_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        if (messageStr != null) {
           tv_get_redbag.setText(messageStr);
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = (ImageView) findViewById(R.id.iv_commit);
       tv_get_redbag= (TextView) findViewById(R.id.tv_get_red_bag);
    }
    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

}
