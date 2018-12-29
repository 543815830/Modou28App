package com.ruixin.administrator.ruixinapplication.popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.GameCenterFragment;
import com.ruixin.administrator.ruixinapplication.home.adapter.MPrizeTypeAdapter;
import com.ruixin.administrator.ruixinapplication.home.databean.PrizeTypeDb;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/23.
 * 邮箱：543815830@qq.com
 */
public class PopMenu3 extends PopupWindow {
    private View mainView;
  ListView my_prizeType_lv;
    MPrizeTypeAdapter adapter;
    String  pid;
    int position;
    List<PrizeTypeDb.DataBean.PrizetypeBean> typeList;
    public PopMenu3(Context paramActivity, int hight, final List<PrizeTypeDb.DataBean.PrizetypeBean> typeList, final RadioButton radioButton){
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.pop_menu3, null);
        my_prizeType_lv=mainView.findViewById(R.id.my_prizeType_lv);
        adapter=new MPrizeTypeAdapter(paramActivity,typeList);
        this.typeList=typeList;
        my_prizeType_lv.setAdapter(adapter);
      adapter.setOnItemClickListener(new MPrizeTypeAdapter.OnItemClickListener() {
          @Override
          public void OnItemClick(View view, int i) {
                  position=i;
                  radioButton.setText(typeList.get(i).getName());
              Log.e("tagpid",""+i);

              Log.e("tagpid",""+position);
                  pid=typeList.get(i).getId();
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
        //设置高度
        setHeight(hight);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }
    public interface  SetPid{
        void setParams(String pid);
    }
    private SetPid setParams;
    public void SetPL(SetPid setParams){
        this.setParams=setParams;
        if(setParams!=null){
            setParams.setParams(typeList.get(position).getId());
            Log.e("tagpid",""+position);
            Log.e("tagpid",""+typeList.get(position).getId());
        }
    }
}
