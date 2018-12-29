package com.ruixin.administrator.ruixinapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;

/**
 * 作者：Created by ${李丽} on 2017/8/25.
 * 邮箱：543815830@qq.com
 * 自定义圆形加载对话框
 */

public class CustomProgressDialogWechat extends Dialog {
    private Context context = null;
    private static CustomProgressDialogWechat customProgressDialog = null;

    public CustomProgressDialogWechat(Context context){
        super(context);
        this.context = context;

    }
    public CustomProgressDialogWechat(Context context, int theme) {
        super(context, theme);
    }
    public static CustomProgressDialogWechat createDialog(Context context){
        Log.e("createDialog", "createDialog" );
        customProgressDialog = new CustomProgressDialogWechat(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.customprogressdialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus){

        if (customProgressDialog == null){
            return;
        }

        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    public CustomProgressDialogWechat setTitile(String strTitle){
        return customProgressDialog;
    }

    public CustomProgressDialogWechat setMessage(String strMessage){
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);

        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }

        return customProgressDialog;
    }
}

