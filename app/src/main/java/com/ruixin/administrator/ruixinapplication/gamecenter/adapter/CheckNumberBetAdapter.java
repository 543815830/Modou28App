package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetMode;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.BetNumber;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ChecknumberInfo;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MymodeDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.domain.GetNumber;
import com.ruixin.administrator.ruixinapplication.popwindow.MymodePop;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class CheckNumberBetAdapter extends BaseAdapter {
    private Context mContext;
    public int[]array;
    List<MymodeDb.DataBean.ModellistBean>list;
    MymodePop mymodePop;
    SparseBooleanArray mCheckStates=new SparseBooleanArray();
    String gameType;
    Handler handler;
    List<ChecknumberInfo.DataBean.CaselistBean.ModelBean> schmemodellist;
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    List<BetNumber>numlist=new ArrayList<>();
    public CheckNumberBetAdapter(Context context,  int[]array,List<MymodeDb.DataBean.ModellistBean>list, String gameType,Handler handler, List<ChecknumberInfo.DataBean.CaselistBean.ModelBean> schmemodellist){
        this.mContext=context;
        this.array=array;
        this.list=list;
        this.gameType=gameType;
        this.handler=handler;
        this.schmemodellist=schmemodellist;
    }
    @Override
    public int getCount() {
        return array.length;
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
            convertView=LayoutInflater.from(mContext).inflate(R.layout.item_check_number,null);
            viewHolder.ll_content=convertView.findViewById(R.id.ll_content);
            viewHolder.tv_bet_number=convertView.findViewById(R.id.tv_bet_number);
            viewHolder.tv_my_mode=convertView.findViewById(R.id.tv_my_mode);
            viewHolder.tv_mode_id=convertView.findViewById(R.id.tv_mode_id);
            viewHolder.cb_mode=convertView.findViewById(R.id.cb_mode);
            mHashMap.put(i, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(gameType.equals("36")){
            viewHolder.tv_bet_number.setText(GetNumber.get36string(array[i]));
        }else if(gameType.equals("bjl")){
            viewHolder.tv_bet_number.setText(GetNumber.getbjlstring(array[i]));
        }else if(gameType.equals("lh")){
            viewHolder.tv_bet_number.setText(GetNumber.getlh(array[i]));
        }else{
            viewHolder.tv_bet_number.setText(""+array[i]);
        }
if(schmemodellist==null){
    if(list.size()>0){
        viewHolder.tv_my_mode.setText(list.get(0).getModelname());
        viewHolder.tv_mode_id.setText(list.get(0).getModelid());
    }else{
        viewHolder.tv_my_mode.setText("");
        viewHolder.tv_mode_id.setText("");
    }
}else{
    for(int j=0;j<schmemodellist.size();j++){
        if(array[i]==Integer.parseInt(schmemodellist.get(j).getNum())){
            viewHolder.tv_my_mode.setText(schmemodellist.get(j).getModelName());
            viewHolder.tv_mode_id.setText(schmemodellist.get(j).getModel());
            mCheckStates.put(i,true);
            numlist.add(new BetNumber(i,viewHolder.tv_mode_id.getText().toString()));
            break;

        }  else {
            if(list.size()>0){
            viewHolder.tv_my_mode.setText(list.get(0).getModelname());
            viewHolder.tv_mode_id.setText(list.get(0).getModelid());
        }else{
            viewHolder.tv_my_mode.setText("");
            viewHolder.tv_mode_id.setText("");
        }
      }
    }

}

          viewHolder.tv_my_mode.setTag(i);
           viewHolder.tv_my_mode.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   int pos =(int)view.getTag();
                   mymodePop = new MymodePop(mContext,  list,null, viewHolder.tv_my_mode,viewHolder.tv_mode_id,handler);
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
                   mymodePop.showAsDropDown(viewHolder.tv_my_mode, 0, 0);
                   //如果窗口存在，则更新
                   mymodePop.update();

               }
           });
viewHolder.tv_mode_id.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void afterTextChanged(Editable editable) {
          viewHolder.cb_mode.setChecked(true);
          if(numlist.size()>0){
              for(int j=0;j<numlist.size();j++){
                  if(i==numlist.get(j).getPosition()){
                      numlist.get(j).setId(viewHolder.tv_mode_id.getText().toString());
                  }else{
                      numlist.add(new BetNumber(i,viewHolder.tv_mode_id.getText().toString()));
                  }
              }
          }else{
              numlist.add(new BetNumber(i,viewHolder.tv_mode_id.getText().toString()));
          }

    }
});

             viewHolder.tv_mode_id.setTag(i);
         viewHolder.cb_mode.setTag(i);
           viewHolder.cb_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos =(int)buttonView.getTag();
                Log.e("pos",""+pos);
                if(isChecked){
                    mCheckStates.put(pos,true);
                    numlist.add(new BetNumber(pos,viewHolder.tv_mode_id.getText().toString()));

                }else{
                    Log.e("delete",""+pos);
                    removeDuplicate2(numlist);
                    mCheckStates.delete(pos);
                    for(int j=0;j<numlist.size();j++){
                        Log.e("delete",""+numlist.get(j).getPosition());
                        if(numlist.get(j).getPosition()==pos){
                            numlist.remove(j);
                        }
                    }
                }

            }
        });

        viewHolder.cb_mode.setChecked(mCheckStates.get(i,false));
        return convertView;
    }

    static  class ViewHolder{
        LinearLayout ll_content;
        TextView tv_bet_number;
        TextView tv_my_mode;
        TextView tv_mode_id;
        CheckBox cb_mode;
    }
    public  interface OnItemClickListener{
        public  void OnItemClick(View view, String data);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public    List  removeDuplicate2(List<BetNumber>list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  ((list.get(j).getPosition())==(list.get(i).getPosition()))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    public interface  SetParams{
        void getParams(String tbChk, String tbNum);

    }
    private  SetParams setParams;
    public void SetParamsS(SetParams setParams){
        this.setParams=setParams;
        if(setParams!=null){
            removeDuplicate2(numlist);
            Log.e("numlist",""+numlist);
            StringBuilder sb = new StringBuilder();
            for(int j=0;j<numlist.size();j++){
                sb.append(array[numlist.get(j).getPosition()]);
                sb.append('=');
                sb.append("on");
                sb.append(",");
                Log.e("sb",""+sb);
            }
            if(sb.length()>0){
                Log.e("sb.length()",""+sb.length());
                sb.deleteCharAt(sb.length()-1);
            }
            StringBuilder nb= new StringBuilder();
            for(int j=0;j<numlist.size();j++){
                nb.append(array[numlist.get(j).getPosition()]);
                nb.append('=');
                nb.append(numlist.get(j).getId());
                nb.append(",");
                Log.e("nb",""+nb);
            }
            if(nb.length()>0){
                Log.e("nb.length()",""+nb.length());
                nb.deleteCharAt(nb.length()-1);
            }
            setParams.getParams(sb.toString(),nb.toString());
        }
    }
}

