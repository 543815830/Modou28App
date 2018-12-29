package com.ruixin.administrator.ruixinapplication.popwindow;

/**
 * 作者：Created by ${李丽} on 2018/4/12.
 * 邮箱：543815830@qq.com
 * 自动投注|对号投注点的我的模式
 */
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.AutoBetActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.MymodepopAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.AutoBetInfoDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MymodeDb;

import java.util.ArrayList;
import java.util.List;

public class MymodePop extends PopupWindow {
    private View mMenuView; // PopupWindow 菜单布局
    private Context context; // 上下文参数
    private MymodepopAdapter adapter;
    private static  List<MymodeDb.DataBean.ModellistBean>list= new ArrayList<>();
    private OnClickListener myOnClick;
    private  TextView textView;
    private  LinearLayout ll_head_view;
    private Handler handler;
    public MymodePop(Context paramActivity, final  List<MymodeDb.DataBean.ModellistBean>list, OnClickListener onclick, final TextView textView,final TextView tv_mode_id,final Handler handler){
        super(paramActivity);
        //窗口布局
        mMenuView = LayoutInflater.from(paramActivity).inflate(R.layout.pop_select_mymode2, null);
        ll_head_view=mMenuView.findViewById(R.id.ll_head_view);
        ListView my_mode_lv=mMenuView.findViewById(R.id.my_mode_lv);
        this.handler=handler;
        ll_head_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Message msg1 = new Message();
                msg1.what =4;
                handler.sendMessage(msg1);
                dismiss();
            }
        });
        this.list=list;
        this.textView=textView;
        this.myOnClick=onclick;
        if(list.size()>0){
            adapter=new MymodepopAdapter(paramActivity,list);
            my_mode_lv.setAdapter(adapter);
            setListViewHeight(my_mode_lv);
            my_mode_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    textView.setText(list.get(i).getModelname());
                    tv_mode_id.setText(list.get(i).getModelid());
                    dismiss();
                }
            });
        }

        // 导入布局
        this.setContentView(mMenuView);
        this.setAnimationStyle(R.style.AnimationFade);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);

        //设置背景透明
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#4D6b6b6b"));
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.ll_mode_pop).getTop();
                Log.e("height", "" + height);

                int y = (int) event.getY();
                Log.e("y", "" + y);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); //得到ListView 添加的适配器
        if (listAdapter == null) {
            return;
        }
        View itemView = listAdapter.getView(0, null, listView); //获取其中的一项 //进行这一项的测量，为什么加这一步，具体分析可以参考 https://www.jianshu.com/p/dbd6afb2c890这篇文章
        itemView.measure(0, 0);
        int itemHeight = itemView.getMeasuredHeight(); //一项的高度
        int itemCount = listAdapter.getCount();//得到总的项数
        LinearLayout.LayoutParams layoutParams = null; //进行布局参数的设置
        if (itemCount <= 6) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * itemCount);
        } else if (itemCount > 6) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * 6);
        }
        listView.setLayoutParams(layoutParams);

    }
}
