package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.GameNameActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GameName1;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.Gamename;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/24.
 * 邮箱：543815830@qq.com
 */
public class TotalGameNameAdapter extends BaseAdapter implements SectionIndexer {
    Context mcontext;
    List<GameName1.DataBean.GamelistBean> list;
    List<GameName1.DataBean.HotgamelistBean> list1;
    List<GameName1.DataBean.LatesgamelistBean> list2;
    LatestGameAdapter adapter;
    HotGameAdapter adapter1;
    String gameName;
    String EgameName;
    String gameType;
    private  int Type;
    private final int VIEW_TYPE = 3;
    private final int TYPE_1 = 0;
    private final int TYPE_2 = 1;
    private final int TYPE_3 = 2;
    int sectionForPosition;
    Handler handler;
    public TotalGameNameAdapter(Context context,  List<GameName1.DataBean.LatesgamelistBean> list2,  List<GameName1.DataBean.HotgamelistBean> list1, List<GameName1.DataBean.GamelistBean> list,Handler handler ) {
        this.mcontext=context;
        this.list2=list2;
        this.list1=list1;
        this.list=list;
        this.handler=handler;
    }
    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     * @param list
     */
    public void updateListView(List<GameName1.DataBean.GamelistBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
       int count=list.size()+2;

        return count;
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
    public int getItemViewType(int position) {
        if(position==0){
            Type=TYPE_1;
            return  TYPE_1;
        }
        else if(position==1){
            Type=TYPE_2;
            return  TYPE_2;
        } else{
            Type=TYPE_3;
            return  TYPE_3;
        }
    }
    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 3;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        ViewHolder3 holder3 = null;
        int type = getItemViewType(i);
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(i) == null) {
            switch (type) {
                case TYPE_1:
                    holder1=new ViewHolder1();
                    convertView=View.inflate(mcontext, R.layout.item_list_1,null);
                    mHashMap.put(i, convertView);
                    holder1.faves=  convertView.findViewById(R.id.faves);
                    holder1.latest_game_name=  convertView.findViewById(R.id.latest_game_name);
                    convertView.setTag(holder1);
                    break;
                case TYPE_2:
                    holder2=new ViewHolder2();
                    convertView=View.inflate(mcontext, R.layout.item_list_2,null);
                    mHashMap.put(i, convertView);
                    holder2.hot_games=  convertView.findViewById(R.id.hot_games);
                    holder2.hot_geme_name=  convertView.findViewById(R.id.hot_geme_name);
                    convertView.setTag(holder2);
                    break;
                case TYPE_3:
                    holder3=new ViewHolder3();
                    convertView=View.inflate(mcontext, R.layout.game_name_list_item,null);
                    mHashMap.put(i, convertView);
                    holder3.showLetter=  convertView.findViewById(R.id.show_letter);
                    holder3.tv_game_name=  convertView.findViewById(R.id.tv_game_name);
                    convertView.setTag(holder3);
                    break;
            }

        } else {
            switch (type) {
                case TYPE_1:
                    convertView = (View) mHashMap.get(i);
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case TYPE_2:
                    convertView = (View) mHashMap.get(i);
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
                case TYPE_3:
                    convertView = (View) mHashMap.get(i);
                    holder3 = (ViewHolder3) convertView.getTag();
                    break;
            }

        }
        // 设置资源
        switch (type) {
            case TYPE_1:
                holder1.faves.setText("最爱游玩");
                if(list2!=null){
                    adapter=new LatestGameAdapter(mcontext,list2);
                    holder1.latest_game_name.setAdapter(adapter);
                    holder1.latest_game_name.setLayoutManager(new GridLayoutManager(mcontext, 3, GridLayoutManager.VERTICAL, false));
                    adapter.setOnItemClickListener(new LatestGameAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, String Lgamename,String LEgamename,String LgameType) {
                            gameName=Lgamename;
                            EgameName=LEgamename;
                            gameType=LgameType;
                            Message msg = new Message();
                            msg.what =1;
                            handler.sendMessage(msg);
                        }
                    });
                }

                break;
            case TYPE_2:
                holder2.hot_games.setText("最热游玩");
                if(list1!=null){
                    adapter1=new HotGameAdapter(mcontext,list1);
                    holder2.hot_geme_name.setAdapter(adapter1);
                    holder2.hot_geme_name.setLayoutManager(new GridLayoutManager(mcontext, 3, GridLayoutManager.VERTICAL, false));
                    adapter1.setOnItemClickListener(new HotGameAdapter.OnItemClickListener() {
                            @Override
                            public void OnItemClick(View view, String HgameName,String HEgameName, String HgameType) {
                                Log.e("tag7",""+HgameName);
                                gameName=HgameName;
                                EgameName=HEgameName;
                                gameType=HgameType;
                                Message msg = new Message();
                                msg.what =2;
                                handler.sendMessage(msg);
                            }
                        });
                }

                break;
            case TYPE_3:
                    GameName1.DataBean.GamelistBean gamename = list.get(i-2);
                    holder3.tv_game_name.setText(gamename.getGamechname());
                   sectionForPosition = getSectionForPosition(i);//通过该项的位置，获得所在分类组的索引号从2开始的北京28
                    //获得该分组第一项的position
                    int positionForSection = getPositionForSection(sectionForPosition);//通过字母索引找到该字母出现的第一项的位置
                Log.e("tag",""+positionForSection);
                    //查看当前position是不是当前item所在分组的第一个item
                    //如果是，则显示showLetter，否则隐藏
                    if (i== positionForSection) {
                        holder3.showLetter.setVisibility(View.VISIBLE);
                        holder3.showLetter.setText(gamename.getFirstLetter());
                    } else {
                        holder3.showLetter.setVisibility(View.GONE);
                    }


                break;
        }
        return convertView;
    }
    // 各个布局的控件资源
    class ViewHolder1 {
        TextView faves;
        RecyclerView latest_game_name;
    }

    class ViewHolder2 {
        TextView hot_games;
        RecyclerView hot_geme_name;
    }

    static  class ViewHolder3{
        TextView showLetter;
        TextView tv_game_name;

    }
    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {//根据分类列的索引号获得该序列的首个位置
        Log.e("getPositionForSection","getPositionForSection");
            if (list!=null && list.size()>0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getFirstLetter().charAt(0) == sectionIndex) {
                        return i+2;
                    }
                }


        }
        return -3;
    }

    @Override
    public int getSectionForPosition(int i) {//通过该项的位置，获得所在分类组的索引号
        if(i>=0){
            if(i==0){
                return -2;
            }else if(i==1){
                return -1;
            }else{
                if (list!=null && list.size()>0) {
                    return list.get(i-2).getFirstLetter().charAt(0);

                }
                Log.e("getSectionForPosition","getSectionForPosition");
        }


}

        return 0;
    }

    public  interface SetGameName{
        public  void getGameName( String gameName,String EgameName,String gameType);
    }
    private SetGameName setGameName;

    public void setOnSetGame(SetGameName setGameName) {
        this.setGameName = setGameName;
        if(setGameName!=null){
            setGameName.getGameName(gameName,EgameName,gameType);
        }
    }
}
