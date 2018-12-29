package com.ruixin.administrator.ruixinapplication;

/**
 * Created by 李丽 on 2018/7/2.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by liaoyi on 2016/10/10 0010.
 */

public class MyScrollView extends ScrollView {

    private OnScrollListener mOnScrollListener;
    int  downY;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 监听ScroView的滑动情况
     *
     * @param l    变化后的X轴位置
     * @param t    变化后的Y轴的位置
     * @param oldl 原先的X轴的位置
     * @param oldt 原先的Y轴的位置
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != mOnScrollListener) {
            mOnScrollListener.onScroll(t);
        }
    }


    /**
     * 设置滚动接口
     *
     * @param listener
     */
    public void setOnScrollListener(OnScrollListener listener) {
        this.mOnScrollListener = listener;
    }

    /**
     * 滚动的回调接口
     */
    public interface OnScrollListener {
        /**
         * MyScrollView滑动的Y方向距离变化时的回调方法
         *
         * @param scrollY
         */
        void onScroll(int scrollY);

    }
   /* @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > 100) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }*/
}
