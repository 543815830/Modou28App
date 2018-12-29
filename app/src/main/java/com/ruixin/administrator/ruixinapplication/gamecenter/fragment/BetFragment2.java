package com.ruixin.administrator.ruixinapplication.gamecenter.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.BetModeAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetMode;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetModeDb;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/28.
 * 邮箱：543815830@qq.com
 */
public class BetFragment2 extends BaseFragment {
    RecyclerView rcy_bet_mode;
    private  View view;
    private static List<BetMode> list = new ArrayList<BetMode>();
    private static List<BetMode> list1 = new ArrayList<BetMode>();
    BetModeAdapter adapter;
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_bet_1,null);
            rcy_bet_mode=view.findViewById(R.id.rcy_bet_mode);
            list= BetModeDb.getList();
            for(int i=12;i<24;i++){

                list1.add(list.get(i));

            }
            adapter=new BetModeAdapter(mContext,list1);
            rcy_bet_mode.setAdapter(adapter);
            rcy_bet_mode.setLayoutManager(new GridLayoutManager(mContext,3, GridLayoutManager.HORIZONTAL,false));
            adapter.setOnItemClickListener(new BetModeAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, String data) {
                    EventBus.getDefault().post(new MessageEvent(data));
                }
            });

        }
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        list1.clear();
    }
}
