package com.ruixin.administrator.ruixinapplication;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruixin.administrator.ruixinapplication.utils.BackHandlerHelper;


/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * Fragment基类公共类
 */

public abstract class BaseFragment extends Fragment implements FragmentBackHandler{
    //上下文
    protected Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* 标题栏与状态栏颜色一致用这种*/
        return initView();
    }

    /**
     * 强制子类重写，实现子类特有的ui
     *
     * @return
     */

    protected abstract View initView() ;
    protected  void  initView(LayoutInflater inflater){};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }



    protected void onPageScrollStateChanged(int arg0){};

    protected  void onPageScrolled(int arg0, float arg1, int arg2){};

    protected  void onPageSelected(int id){};

    /**
     * 当孩子要初始化数据、或者联网请求，或者展示数据等可以重写
     */
    protected void initData() {
    }

    /**
     * 当孩子要初始化数据、或者联网请求，或者展示数据等可以重写
     */
    protected void initStatus() {
    }

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }
}
