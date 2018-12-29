package com.ruixin.administrator.ruixinapplication.usercenter.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MailBox;
import com.ruixin.administrator.ruixinapplication.utils.SlideDelete;
import com.ruixin.administrator.ruixinapplication.utils.SlideDelete1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 作者：Created by ${李丽} on 2018/4/17.
 * 邮箱：543815830@qq.com
 */

public class MailBoxAdapter extends BaseAdapter {
    private Context mContext;
    SparseBooleanArray mCheckStates=new SparseBooleanArray();
    private List <MailBox.DataBean>list;
    private List <Integer>mposition;
    private List<Integer> newlist=new ArrayList();
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private int mTag;
    private OnItemClickListener onItemClickListener;
    private OnDelClickListener onDelClickListener;
    private List<SlideDelete> slideDeleteArrayList = new ArrayList<>();
    public MailBoxAdapter(Context mContext, List <MailBox.DataBean>list,int tag,HashMap<String, String> prarams,List position) {
        this.mContext = mContext;
        this.list = list;
        this.mposition = position;
        this.mTag=tag;
        this.prarams=prarams;
    }

    @Override
    public int getCount() {
        return  list.size();
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
        final HashMap mHashMap =new HashMap();
        if(mTag==1){
        if (mHashMap.get(i) == null) {
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_inside_mailbox,null);
            mHashMap.put(i, convertView);
            viewHolder.mSlideDelete=  convertView.findViewById(R.id.mSlideDelete);
            viewHolder.mLlContent=  convertView.findViewById(R.id.mLlContent);
            viewHolder.mLlDelete=  convertView.findViewById(R.id.mLlDelete);
            viewHolder.tv_addresser=  convertView.findViewById(R.id.tv_addresser);
            viewHolder.unread=  convertView.findViewById(R.id.unread);
            viewHolder.tv_mail_time=  convertView.findViewById(R.id.tv_mail_time);
            viewHolder.tv_mail_content=  convertView.findViewById(R.id.tv_mail_content);
            viewHolder.btn_delete=  convertView.findViewById(R.id.btn_delete);
            viewHolder.rb_mailbox=  convertView.findViewById(R.id.rb_mailbox);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
         if(list.get(i).getLook().equals("0")){
            viewHolder.unread.setVisibility(View.VISIBLE);
         }else{
             viewHolder.unread.setVisibility(View.GONE);
         }
        viewHolder.tv_addresser.setText(new StringBuilder().append("发件人:").append(list.get(i).getUserid()).toString());
        viewHolder.tv_mail_time.setText(list.get(i).getTime());
        viewHolder.tv_mail_content.setText(new StringBuilder().append("        ").append(list.get(i).getTitle()).toString());
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
        viewHolder.mLlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClick", "mLlDelete---setOnClickListener" );
                if(onDelClickListener!=null){
                    onDelClickListener.OnDelClick(view,list.get(i).getSmsid(), i);
                }
            }
        });

        }else{
            if (mHashMap.get(i) == null) {
                viewHolder=new ViewHolder();
                convertView=View.inflate(mContext, R.layout.item_inside_mailbox1,null);
                mHashMap.put(i, convertView);
                viewHolder.mSlideDelete1=  convertView.findViewById(R.id.mSlideDelete1);
                viewHolder.mLlCheck=  convertView.findViewById(R.id.mLlCheck);
                viewHolder.mLlContent=  convertView.findViewById(R.id.mLlContent);
                viewHolder.mLlDelete=  convertView.findViewById(R.id.mLlDelete);
                viewHolder.tv_addresser=  convertView.findViewById(R.id.tv_addresser);
                viewHolder.unread=  convertView.findViewById(R.id.unread);
                viewHolder.tv_mail_time=  convertView.findViewById(R.id.tv_mail_time);
                viewHolder.tv_mail_content=  convertView.findViewById(R.id.tv_mail_content);
                viewHolder.btn_delete=  convertView.findViewById(R.id.btn_delete);
                viewHolder.rb_mailbox=  convertView.findViewById(R.id.rb_mailbox);
                convertView.setTag(viewHolder);
            } else {
                convertView = (View) mHashMap.get(i);
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if(list.get(i).getLook().equals("0")){
                viewHolder.unread.setVisibility(View.VISIBLE);
            }else{
                viewHolder.unread.setVisibility(View.GONE);
            }
            viewHolder.tv_addresser.setText(new StringBuilder().append("发件人：").append(list.get(i).getUserid()).toString());
            viewHolder.tv_mail_time.setText(list.get(i).getTime());
            viewHolder.tv_mail_content.setText(new StringBuilder().append("        ").append(list.get(i).getTitle()).toString());
            viewHolder.rb_mailbox.setTag(i);
            viewHolder.rb_mailbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos =(int)buttonView.getTag();
                    if(isChecked){
                        mCheckStates.put(pos,true);
                        newlist.add(pos);
                        mposition.add(pos);
                    }else{
                        mCheckStates.delete(pos);
                        Iterator<Integer> it = newlist.iterator();
                        while(it.hasNext()){
                            int x = it.next();
                            if(x==pos){
                                it.remove();
                            }
                        }
                        Iterator<Integer> it1 = mposition.iterator();
                        while(it1.hasNext()){
                            int x = it1.next();
                            if(x==pos){
                                it1.remove();
                            }
                        }
                    }
                    List<String> id=new ArrayList<>();
                    for(int j=0;j<newlist.size();j++){
                        id.add(list.get(newlist.get(j)).getSmsid());
                    }
                    StringBuilder sb = new StringBuilder();
                    for(int i=0;i<id.size();i++){
                        sb.append(id.get(i));
                        sb.append(',');
                    }
                    if(sb.length()>0){
                        sb.deleteCharAt(sb.length()-1);
                    }

                    prarams.put("chkID", String.valueOf(sb));
                }
            });

viewHolder.mLlContent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(onItemClickListener!=null){
            onItemClickListener.OnItemClick(view,list.get(i).getSmsid(),i);
        }
    }
});
            viewHolder.rb_mailbox.setChecked(mCheckStates.get(i,false));
        }

        return convertView;
    }
    class ViewHolder{
        SlideDelete1 mSlideDelete1;
        SlideDelete mSlideDelete;
        LinearLayout mLlCheck;
        LinearLayout mLlContent;
        LinearLayout mLlDelete;
        CheckBox rb_mailbox;
        TextView tv_addresser;
        TextView unread;
        TextView tv_mail_time;
        TextView tv_mail_content;
        Button btn_delete;
        View view;

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
}
