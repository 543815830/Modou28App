package com.ruixin.administrator.ruixinapplication.popwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.home.adapter.MPrizeTypeAdapter;
import com.ruixin.administrator.ruixinapplication.home.databean.PrizeTypeDb;
import com.ruixin.administrator.ruixinapplication.usercenter.adapter.AdvanceTypeAdapter;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.AdvanceDb;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/23.
 * 邮箱：543815830@qq.com
 */
public class PopMenu4 extends PopupWindow {
    private View mainView;
    LinearLayout ll_game_pop;
  ListView advance_retype_lv;
    AdvanceTypeAdapter adapter;
    String  pid;
    int position=0;
    List<AdvanceDb.ListBean>typelist;
    Context paramActivity;
    public PopMenu4(Context paramActivity, final List<AdvanceDb.ListBean>typelist, final TextView recordType){
        super(paramActivity);
        //窗口布局
        this.paramActivity=paramActivity;
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.popmenu4, null);
        ll_game_pop=mainView.findViewById(R.id.ll_game_pop);
        advance_retype_lv=mainView.findViewById(R.id.advance_retype_lv);

        this.typelist=typelist;
        adapter=new AdvanceTypeAdapter(paramActivity,typelist);
        advance_retype_lv.setAdapter(adapter);
        setListViewHeight(advance_retype_lv);
        advance_retype_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j= 0; j <typelist.size(); j++) {
                    if(j==i){
                        typelist.get(j).setFlag(true);
                    }else{
                        typelist.get(j).setFlag(false);
                    }

                    adapter. notifyDataSetChanged();
                }
                position=i;
                recordType.setText(""+typelist.get(i).getGamechname());
                dismiss();
            }
        });
        setContentView(mainView);
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
        //设置每个子布局的事件监听器
        mainView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mainView.findViewById(R.id.ll_game_pop).getTop();
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

    public interface  Setname{
        void setParams(String name);
    }
    private Setname setParams;
    public void SetPL(Setname setParams){
        this.setParams=setParams;
        if(setParams!=null){
            if(position==0){
                setParams.setParams("");
            }else{
                setParams.setParams(typelist.get(position).getGamename());
            }

        }
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
        if (itemCount <= 5) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * itemCount);
        } else if (itemCount > 5) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * 5);
        }
        listView.setLayoutParams(layoutParams);

    }
}
