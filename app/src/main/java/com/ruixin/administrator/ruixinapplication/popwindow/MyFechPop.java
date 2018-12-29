package com.ruixin.administrator.ruixinapplication.popwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.SchemeAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ScameDb;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.MyFechAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.GoldDepositDb;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/23.
 * 邮箱：543815830@qq.com
 * 托管方案的popwindow
 */
public class MyFechPop extends PopupWindow {
    private View mainView;
  ListView my_trustee_lv;
    MyFechAdapter adapter;
    String  pid;
    int position;
    List<GoldDepositDb.DataBean.BanklistBean> fechlist;
    public MyFechPop(Context paramActivity, final    List<GoldDepositDb.DataBean.BanklistBean> fechlist, final TextView etTrusteeName){
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.pop_mytrustee, null);
        my_trustee_lv=mainView.findViewById(R.id.my_trustee_lv);
        adapter=new MyFechAdapter(paramActivity,fechlist);
        this.fechlist=fechlist;
        my_trustee_lv.setAdapter(adapter);
        setListViewHeight(my_trustee_lv);
      adapter.setOnItemClickListener(new MyFechAdapter.OnItemClickListener() {
          @Override
          public void OnItemClick(View view, int i) {
                  position=i;
                 etTrusteeName.setText(fechlist.get(i).getName());
                  dismiss();

          }
      });
        //设置每个子布局的事件监听器
        setContentView(mainView);
        mainView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isShowing()){
                    dismiss();
                }
            }
        });
        //设置宽度
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
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
        mainView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mainView.findViewById(R.id.ll_trustee_pop).getTop();
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
    public interface  SetPid{
        void setParams(int i);
    }
    private SetPid setParams;
    public void SetPL(SetPid setParams){
        this.setParams=setParams;
        if(setParams!=null){
            setParams.setParams(position);
        }
    }
}
