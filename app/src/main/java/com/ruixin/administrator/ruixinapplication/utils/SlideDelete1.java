package com.ruixin.administrator.ruixinapplication.utils;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：Created by ${李丽} on 2018/4/17.
 * 邮箱：543815830@qq.com
 */

public class SlideDelete1 extends ViewGroup {
    private View mCheck; // 选择部分
    private View mContent; // 内容部分
    private ViewDragHelper viewDragHelper;
    private int mContentWidth;
    private int mContentHeight;
    private int mCheckWidth;
    private int mCheckHeight;
    public SlideDelete1(Context context) {
        super(context);
    }
    public SlideDelete1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SlideDelete1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private SlideDelete.OnSlideDeleteListener onSlideDeleteListener;
    public void setOnSlideDeleteListener(SlideDelete.OnSlideDeleteListener onSlideDeleteListener){
        this.onSlideDeleteListener = onSlideDeleteListener;
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCheck = getChildAt(0);
        mContent = getChildAt(1);
        //public static ViewDragHelper create(ViewGroup forParent, Callback cb)
       // viewDragHelper = ViewDragHelper.create(this,new SlideDelete.MyDrawHelper());
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 这跟mContent的父亲的大小有关，父亲是宽填充父窗体，高度是和孩子一样是60dp
        mContent.measure(widthMeasureSpec,heightMeasureSpec); // 测量内容部分的大小
        LayoutParams layoutParams = mCheck.getLayoutParams();
        int checkWidth = MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY);
        int checkHeight = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
        // 这个参数就需要指定为精确大小
        mCheck.measure(checkWidth, checkHeight); // 测量删除部分的大小
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mCheckWidth = mCheck.getMeasuredWidth();
        mCheckHeight = mCheck.getMeasuredHeight();
        mCheck.layout(0, 0,
                mCheckWidth, mCheckHeight); // 摆放选择部分的位置
        mContentWidth = mContent.getMeasuredWidth();
        mContentHeight = mContent.getMeasuredHeight();
        mContent.layout(mCheckWidth, 0,mCheckWidth+ mContentWidth, mContentHeight); // 摆放内容部分的位置
    }
}
