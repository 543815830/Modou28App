package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.AutoBetInfoDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MymodeDb;
import com.ruixin.administrator.ruixinapplication.popwindow.MyBetModePop;
import com.ruixin.administrator.ruixinapplication.popwindow.MymodePop;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class AutoBetAdapter extends BaseAdapter {
    private Context mContext;
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    MymodePop mymodePop;
    List<MymodeDb.DataBean.ModellistBean> aulist;
    List<String>newList=new ArrayList<>();
    List<String>newList2=new ArrayList<>();
    StringBuilder sb;
    StringBuilder nb;
    StringBuilder nb2;
    String type;
    Handler handler;
    List<AutoBetInfoDb.DataBean.CaselistBean.ModelBean> schmemodellist=new ArrayList<>();
    public AutoBetAdapter(Context context, List<MymodeDb.DataBean.ModellistBean> aulist, List<AutoBetInfoDb.DataBean.CaselistBean.ModelBean> schmemodellist,String type,Handler handler ){
        this.mContext=context;
        this.aulist=aulist;
        this.schmemodellist=schmemodellist;
        this.type=type;
        this.handler=handler;
    }
    @Override
    public int getCount() {
        if(type.equals("add")){
            return aulist.size();
        }else{
            return schmemodellist.size();
        }

    }

    @Override
    public Object getItem(int i) {

        return i;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(i) == null) {
            viewHolder=new ViewHolder();

            convertView=LayoutInflater.from(mContext).inflate(R.layout.item_auto_bet,null);
            viewHolder.tv_mode=convertView.findViewById(R.id.tv_mode);
            viewHolder.tv_bet_points=convertView.findViewById(R.id.tv_bet_points);
            viewHolder.tv_win_mode=convertView.findViewById(R.id.tv_win_mode);
            viewHolder.tv_win_mode_id=convertView.findViewById(R.id.tv_win_mode_id);
            viewHolder.tv_loss_mode_id=convertView.findViewById(R.id.tv_loss_mode_id);
            viewHolder.tv_loss_mode=convertView.findViewById(R.id.tv_loss_mode);
            mHashMap.put(i, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
if(type.equals("add")){
    Log.e("i",""+i);
    viewHolder.tv_mode.setText(aulist.get(i).getModelname());
    viewHolder.tv_bet_points.setText(aulist.get(i).getTzpoints());
    viewHolder.tv_win_mode.setText(aulist.get(0).getModelname());
    viewHolder.tv_win_mode_id.setText(aulist.get(0).getModelid());
    viewHolder.tv_loss_mode.setText(aulist.get(0).getModelname());
    viewHolder.tv_loss_mode_id.setText(aulist.get(0).getModelid());
    newList.add(aulist.get(0).getModelid());
  //  Log.e("newList",""+newList);
    newList2.add(aulist.get(0).getModelid());
}else{
    viewHolder.tv_mode.setText(schmemodellist.get(i).getModelname());
    viewHolder.tv_bet_points.setText(schmemodellist.get(i).getTzpoints());
    viewHolder.tv_win_mode.setText(schmemodellist.get(i).getWinname());
    viewHolder.tv_win_mode_id.setText(schmemodellist.get(i).getWinid());
    viewHolder.tv_loss_mode.setText(schmemodellist.get(i).getLostname());
    viewHolder.tv_loss_mode_id.setText(schmemodellist.get(i).getLostid());
    newList.add(schmemodellist.get(i).getWinid());
  //  Log.e("newList",""+newList);
    newList2.add(schmemodellist.get(i).getLostid());
}

        viewHolder.tv_win_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mymodePop = new MymodePop(mContext, aulist,null, viewHolder.tv_win_mode,viewHolder.tv_win_mode_id,handler);
                //监听窗口的焦点事件，点击窗口外面则取消显示
                mymodePop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            mymodePop.dismiss();
                        }
                    }
                });
                //设置默认获取焦点
                mymodePop.setFocusable(true);
                //以某个控件的x和y的偏移量位置开始显示窗口
                mymodePop.showAsDropDown(viewHolder.tv_win_mode, 0, 0);
               // mymodePop.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
                //如果窗口存在，则更新
                mymodePop.update();
                viewHolder.tv_win_mode_id.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        newList.remove(i);
                        newList.add(i,viewHolder.tv_win_mode_id.getText().toString());
                      //  Log.e("newList",""+newList);
                    }
                });

            }
        });
        viewHolder.tv_loss_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mymodePop = new MymodePop(mContext, aulist,null, viewHolder.tv_loss_mode,viewHolder.tv_loss_mode_id,handler);
                //监听窗口的焦点事件，点击窗口外面则取消显示
                mymodePop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            mymodePop.dismiss();
                        }
                    }
                });

                //设置默认获取焦点
                mymodePop.setFocusable(true);
                //以某个控件的x和y的偏移量位置开始显示窗口
                mymodePop.showAsDropDown(viewHolder.tv_loss_mode, 0, 0);
               // mymodePop.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
                //如果窗口存在，则更新
                mymodePop.update();
                viewHolder.tv_loss_mode_id.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        newList2.remove(i);
                        newList2.add(i,viewHolder.tv_loss_mode_id.getText().toString());
                    }
                });

            }
        });


        sb = new StringBuilder();
        for(int j=0;j<aulist.size();j++){
            sb.append(j);
            sb.append('=');
            sb.append(aulist.get(j).getModelid());
            sb.append(",");
          //  Log.e("sb",""+sb);
        }
        if(sb.length()>0){
           // Log.e("sb.length()",""+sb.length());
            sb.deleteCharAt(sb.length()-1);
        }

        return convertView;
    }

    static  class ViewHolder{
       TextView tv_mode;
       TextView tv_bet_points;
       TextView tv_win_mode;
       TextView tv_win_mode_id;
       TextView tv_loss_mode;
       TextView tv_loss_mode_id;
    }
    public  interface OnnotifyClickListener{
        void OnNotifyClick(String sID,String sWinModel,String sLossModel);
    }
    private OnnotifyClickListener onnotifyClickListener;

    public void setOnnotifyClickListener(OnnotifyClickListener onnotifyClickListener) {
        this.onnotifyClickListener = onnotifyClickListener;
       // Log.e("nb","setOnnotifyClickListener");
        if(type.equals("add")){
            newList=newList.subList(0,aulist.size());
            newList2=newList2.subList(0,aulist.size());
        }else{
            newList=newList.subList(0,schmemodellist.size());
            newList2=newList2.subList(0,schmemodellist.size());
        }

        nb= new StringBuilder();
        for(int j=0;j<newList.size();j++){
            nb.append(j);
            nb.append('=');
            nb.append(newList.get(j));
            nb.append(",");
          //  Log.e("nb",""+nb);
        }
        if(nb.length()>0){
           // Log.e("nb.length()",""+nb.length());
            nb.deleteCharAt(nb.length()-1);
        }
        nb2= new StringBuilder();
        for(int j=0;j<newList2.size();j++){
            nb2.append(j);
            nb2.append('=');
            nb2.append(newList2.get(j));
            nb2.append(",");
            Log.e("nb2",""+nb2);
        }
        if(nb2.length()>0){
            Log.e("nb.length()",""+nb2.length());
            nb2.deleteCharAt(nb2.length()-1);
        }
        if(onnotifyClickListener!=null){
            if(sb!=null&&nb!=null&nb2!=null){
                onnotifyClickListener.OnNotifyClick(sb.toString(),nb.toString(),nb2.toString());
            }

        }
    }
}

