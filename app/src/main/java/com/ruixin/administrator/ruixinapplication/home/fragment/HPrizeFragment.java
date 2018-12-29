package com.ruixin.administrator.ruixinapplication.home.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.home.databean.PrizeTypeDb;
import com.ruixin.administrator.ruixinapplication.popwindow.PopMenu3;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.AdvanceActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 李丽 on 2018/7/2.
 */

public class HPrizeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private View view;
    private RadioGroup rg_prize;
    private ViewPager prize_vp;
    private RadioButton rb_prize1;
    private RadioButton rb_prize2;
    private RadioButton rb_prize3;
    int position;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
    boolean first=true;
   // HPrizeTypeFragment ptFm=  new HPrizeTypeFragment();
    PopMenu3 popmenu;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    List<PrizeTypeDb.DataBean.PrizetypeBean>typeList=new ArrayList<>();
    int Hight;
    static String  id;
    HPrizeMoreFragment pmoreFm;
    @Override
    protected View initView() {
        if(view==null){
            view=View.inflate(mContext, R.layout.fm_prize,null);
            rg_prize=view.findViewById(R.id.rg_prize);
            prize_vp=view.findViewById(R.id.prize_vp);
            rb_prize1=view.findViewById(R.id.rb_prize1);
            rb_prize2=view.findViewById(R.id.rb_prize2);
            Hight=DisplayUtil.px2dp(mContext,getActivity().getWindowManager().getDefaultDisplay().getHeight())-260;
            rb_prize2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPop();
                }
            });
            rb_prize2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    popmenu.SetPL(new PopMenu3.SetPid() {
                        @Override
                        public void setParams(String pid) {
                            id=pid;
                            Message msg1 = new Message();
                            msg1.what =1;
                            pmoreFm.handler.sendMessage(msg1);
                        }
                    });

                }
            });
            rb_prize3=view.findViewById(R.id.rb_prize3);

        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(first){
                initVp();
                setListener();
                new PrizeTypeAsyncTask().execute();
                first=false;
            }

        }
    }
    private void setListener() {
        Log.e("tag", "setListener()");
        rg_prize.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中
        rg_prize.check(R.id.rb_prize1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            rg_prize.check(R.id.rb_prize1);
        } else if (position == 1) {
            rg_prize.check(R.id.rb_prize2);
        } else if (position == 2) {
            rg_prize.check(R.id.rb_prize3);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_prize1://热门奖品
                    position = 0;
                    break;
                case R.id.rb_prize2://奖品分类
                    position =1 ;
                    break;
                case R.id.rb_prize3://最新奖品
                    position =2 ;
                    break;
            }
            prize_vp.setCurrentItem(position);
        }
    }

    private void showPop() {
        rb_prize2.setSelected(true);
        if(popmenu==null){
            popmenu = new PopMenu3(mContext, DisplayUtil.dp2px(mContext, Hight),typeList,rb_prize2);
        }

    //监听窗口的焦点事件，点击窗口外面则取消显示
    popmenu.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                popmenu.dismiss();
            }
        }
    });
    //设置默认获取焦点
    popmenu.setFocusable(true);
    popmenu.setOutsideTouchable(true);
    popmenu.showAsDropDown(rb_prize2,0,0);
    //如果窗口存在，则更新
    popmenu.update();
    popmenu.setOnDismissListener(new poponDismissListener());
    }
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
          rb_prize2.setSelected(false);
        }
    }
    private void initVp() {
        newsList.clear();
        for (int i = 0; i < 2; i++) {
            prize_vp.setCurrentItem(i);
        }
        newsList.add(new HHotPrizeFragment());//热门奖品
        pmoreFm=new HPrizeMoreFragment();//奖品分类
        Bundle bundle = new Bundle();
        bundle.putString("pid","");
        pmoreFm.setArguments(bundle);
        newsList.add(pmoreFm);
        newsList.add(new HNewPrizeFragment());//最新奖品
        //设置viewpager适配器
        adapter = new OneFmAdapter(getActivity().getSupportFragmentManager(), newsList);
        prize_vp.setAdapter(adapter);
        //两个viewpager切换不重新加载
        prize_vp.setOffscreenPageLimit(2);
        //设置默认
        prize_vp.setCurrentItem(0);
        //设置viewpager监听事件
        prize_vp.setOnPageChangeListener(this);

    }
    private class PrizeTypeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(first){
                // mLoadingAndRetryManager.showLoading();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result= AgentApi.dopost3(URL.getInstance().PrizeType_URL,prarams);
            Log.e("奖品分类结果result","result="+result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.length()>0){
                Log.e("tag","s结果长度"+s.length());
                int index = s.indexOf("{");
                if (index == 0) {
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        Gson gson1=new Gson();
                        PrizeTypeDb prizeTypeDb=gson1.fromJson(s,PrizeTypeDb.class);
                           typeList =prizeTypeDb.getData().getPrizetype();
                           typeList.add(0,new PrizeTypeDb.DataBean.PrizetypeBean("全部奖品","",false));
                           for(int i=0;i<typeList.size();i++){
                               Log.e("tagpid",""+typeList.get(i).getId());
                           }
                    }else if(entry.getStatus() == 99) {
                        Unlogin.doAtk(prarams,s,new PrizeTypeAsyncTask());
                        /*抗攻击*/
                    }
                }else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            }else {

               Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
