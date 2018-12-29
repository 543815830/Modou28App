package com.ruixin.administrator.ruixinapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import android.widget.Toast;


import java.lang.reflect.InvocationTargetException;

/**
 * Created by 李丽 on 2018/7/10.
 */

public class BaseActivity extends FragmentActivity {
    private RuiXinApplication application;
    private BaseActivity oContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (application == null) {
            // 得到Application对象
            application = (RuiXinApplication) getApplication();
        }
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法
    }

    // 添加Activity方法
    public void addActivity() {
        application.addActivity_(oContext);// 调用myApplication的添加Activity方法
    }
    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity_(oContext);// 调用myApplication的销毁单个Activity方法
    }
    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity_();// 调用myApplication的销毁所有Activity方法
    }

    /* 把Toast定义成一个方法  可以重复使用，使用时只需要传入需要提示的内容即可*/
    public void show_Toast(String text) throws InvocationTargetException, IllegalAccessException {
       Toast.makeText(oContext, text, Toast.LENGTH_SHORT).show();
    }
}
