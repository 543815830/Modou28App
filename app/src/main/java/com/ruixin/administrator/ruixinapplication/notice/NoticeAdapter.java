package com.ruixin.administrator.ruixinapplication.notice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.utils.BadgeView;
import com.ruixin.administrator.ruixinapplication.utils.SlideDelete;
import com.ruixin.administrator.ruixinapplication.utils.SlideDelete1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by 李丽 on 2018/7/11.
 */

public class NoticeAdapter extends BaseAdapter {
    Context mContext;
    private int mTag;
    Handler handler;
    List<NoticeDb.DataBean> list;
    private List<SlideDelete> slideDeleteArrayList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private OnDelClickListener onDelClickListener;
    private List<Integer> mposition=new ArrayList();
    SparseBooleanArray mCheckStates=new SparseBooleanArray();
    BadgeView badge1;
    public NoticeAdapter( Context context,List<NoticeDb.DataBean> list,int mTag, Handler handler) {
        this.mContext=context;
        this.list=list;
        this.mTag=mTag;
        this.handler=handler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();

        if(mTag==1){
            if (mHashMap.get(i) == null) {
                viewHolder=new ViewHolder();
                convertView=View.inflate(mContext, R.layout.item_notice,null);
                viewHolder.mSlideDelete=  convertView.findViewById(R.id.mSlideDelete);
                viewHolder.mLlContent=  convertView.findViewById(R.id.mLlContent);
                viewHolder.unread=  convertView.findViewById(R.id.unread);
                viewHolder.tv_addresser=  convertView.findViewById(R.id.tv_addresser);
                viewHolder.tv_notice_time=  convertView.findViewById(R.id.tv_notice_time);
                viewHolder.tv_notice_content=  convertView.findViewById(R.id.tv_notice_content);
                viewHolder.tv_delete=  convertView.findViewById(R.id.tv_delete);
                viewHolder.tv_more=  convertView.findViewById(R.id.tv_more);
                mHashMap.put(i, convertView);
               // viewHolder.pr_tv=  convertView.findViewById(R.id.pr_tv);
                convertView.setTag(viewHolder);
            } else {
                convertView = (View) mHashMap.get(i);
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (list.get(i).getLook().equals("0")){
           viewHolder.unread.setVisibility(View.VISIBLE);
            }
            viewHolder.tv_addresser.setText(list.get(i).getTitle());
            viewHolder.tv_notice_time.setText(list.get(i).getTime());
            viewHolder.tv_notice_content.setText(list.get(i).getMag());
            viewHolder.mSlideDelete.setOnSlideDeleteListener(new SlideDelete.OnSlideDeleteListener() {
                @Override
                public void onOpen(SlideDelete slideDelete) {
                    closeOtherItem();
                    slideDeleteArrayList.add(slideDelete);
                    Log.e("Slide", "slideDeleteArrayList当前数量：" + slideDeleteArrayList.size());
                }
                @Override
                public void onClose(SlideDelete slideDelete) {
                    slideDeleteArrayList.remove(slideDelete);
                    Log.e("Slide", "slideDeleteArrayList当前数量：" + slideDeleteArrayList.size());
                }
                public void onItemClick(View view){
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(view,list.get(i).getSmsid(),i);
                    }
                }
            });
            viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onDelClickListener!=null){
                        onDelClickListener.OnDelClick(view,list.get(i).getSmsid(),i);
                    }
                }
            });
            viewHolder.tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTag=2;
                    notifyDataSetChanged();
                    Message msg1 = new Message();
                    msg1.what =1;
                    handler.sendMessage(msg1);
                }
            });
        }else{
            if (mHashMap.get(i) == null) {
                viewHolder=new ViewHolder();
                convertView=View.inflate(mContext, R.layout.item_notice1,null);
                viewHolder.mSlideDelete1=  convertView.findViewById(R.id.mSlideDelete1);
                viewHolder.mLlCheck=  convertView.findViewById(R.id.mLlCheck);
                viewHolder.mLlContent=  convertView.findViewById(R.id.mLlContent);
                viewHolder.unread=  convertView.findViewById(R.id.unread);
                viewHolder.tv_addresser=  convertView.findViewById(R.id.tv_addresser);
                viewHolder.tv_notice_time=  convertView.findViewById(R.id.tv_notice_time);
                viewHolder.tv_notice_content=  convertView.findViewById(R.id.tv_notice_content);
                viewHolder.cb_notice=  convertView.findViewById(R.id.cb_notice);
                mHashMap.put(i, convertView);
                convertView.setTag(viewHolder);
            } else {
                convertView = (View) mHashMap.get(i);
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (list.get(i).getLook().equals("0")){
                viewHolder.unread.setVisibility(View.VISIBLE);
            }

            viewHolder.tv_addresser.setText(list.get(i).getTitle());
            viewHolder.tv_notice_time.setText(list.get(i).getTime());
            viewHolder.tv_notice_content.setText(list.get(i).getMag());
            if(list.get(i).isFlag()){
                mCheckStates.put(i,true);
                mposition.add(i);
                Log.e("mpos1",""+mposition);
            }
            viewHolder.cb_notice.setTag(i);
            viewHolder.cb_notice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos =(int)buttonView.getTag();
                    if(isChecked){
                        mCheckStates.put(pos,true);
                        mposition.add(pos);
                        list.get(i).setFlag(true);
                        Log.e("mpos22",""+mposition);
                    }else{
                        mCheckStates.delete(pos);
                        list.get(i).setFlag(false);
                        Iterator<Integer> it = mposition.iterator();
                        while(it.hasNext()){
                            int x = it.next();
                            if(x==pos){
                                it.remove();
                            }
                        }

                    }
                }
            });


            viewHolder.cb_notice.setChecked(mCheckStates.get(i,false));
        }

        Log.e("mpos2",""+mposition);
        return convertView;
    }

    private void closeOtherItem(){
        // 采用Iterator的原因是for是线程不安全的，迭代器是线程安全的
        ListIterator<SlideDelete> slideDeleteListIterator = slideDeleteArrayList.listIterator();
        while(slideDeleteListIterator.hasNext()){
            SlideDelete slideDelete = slideDeleteListIterator.next();
            slideDelete.isShowDelete(false);
        }
        slideDeleteArrayList.clear();
    }
     class ViewHolder{
       SlideDelete mSlideDelete;
        SlideDelete1 mSlideDelete1;
        LinearLayout mLlContent;
        TextView unread;
        TextView tv_addresser;
        TextView tv_notice_time;
        TextView tv_notice_content;
        TextView tv_delete;
        TextView tv_more;
        LinearLayout mLlCheck;
        CheckBox cb_notice;
    }

    public  interface OnItemClickListener{
        public  void OnItemClick(View view, String data,int i);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public  interface OnDelClickListener{
        public  void OnDelClick(View view, String data,int i);
    }
    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        this.onDelClickListener = onDelClickListener;
    }
    public  void removeDuplicate(List list) {
        Log.e("removeDuplicate","removeDuplicate"+list);
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        System.out.println(list);
    }
    public interface  SetParams{
        void setParams(List <Integer>mposition);
    }
    private SetParams setParams;
    public void SetPL(SetParams setParams){
        this.setParams=setParams;
        if(setParams!=null){
            for(int i=0;i<list.size();i++){
                if(list.get(i).isFlag()){
                    mposition.add(i);
                    Log.e("mpos1",""+mposition);
                }
            }
            Log.e("mpos3",""+mposition);
                removeDuplicate(mposition);
            setParams.setParams(mposition);
        }
    }
}
