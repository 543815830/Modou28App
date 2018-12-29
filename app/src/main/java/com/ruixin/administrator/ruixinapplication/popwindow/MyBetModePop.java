package com.ruixin.administrator.ruixinapplication.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.MyBetModeAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MybetModeDB;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MymodeDb;
import com.ruixin.administrator.ruixinapplication.gamecenter.fragment.Betmode28Fragment;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/31.
 * 邮箱：543815830@qq.com
 * 投注模式里面的我的模式的弹出pop
 */
public class MyBetModePop extends PopupWindow {
    private View mMenuView; // PopupWindow 菜单布局
    private Context context; // 上下文参数
    private View.OnClickListener myOnClick;
    ListView my_bet_mode_lv;
    MyBetModeAdapter adapter;
    TextView add_my_betmode;
    TextView tv_points;
    EditText et_mode;
    TextView tv_save;
    String userId;
    String userToken;
    String EgameName;
    String gameType;
    String Totalpoints;
    List<MybetModeDB.DataBean.ModeljsonBean>list=new ArrayList<>();
    int position;
    Handler handler;
    TextView tv_tbid;
    public MyBetModePop(Activity context, View.OnClickListener onclick, List<MybetModeDB.DataBean.ModeljsonBean> list, String userId, String userToken, String EgameName, String Totalpoints, String gameType, EditText et_mode,Handler handler,TextView tv_tbid) {
        super(context);
        this.context = context;
        this.myOnClick=onclick;
        this.list=list;
        this.userId=userId;
        this.userToken=userToken;
        this.EgameName=EgameName;
        this.gameType=gameType;
        this.Totalpoints=Totalpoints;
        this.et_mode=et_mode;
        this.handler=handler;
        this.tv_tbid=tv_tbid;
        Init();
    }

    private void Init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_my_bet_mode, null);
        add_my_betmode=mMenuView.findViewById(R.id.add_my_betmode);
        tv_points=mMenuView.findViewById(R.id.tv_points);
        tv_points.setText(Totalpoints);
        tv_save=mMenuView.findViewById(R.id.tv_save);
        add_my_betmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        my_bet_mode_lv=mMenuView.findViewById(R.id.my_bet_mode_lv);
        adapter=new MyBetModeAdapter(context,list,userId,userToken,EgameName);
        my_bet_mode_lv.setAdapter(adapter);
        my_bet_mode_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("tag", "" + list.get(i));
                position=i;
                if(gameType.equals("dw")){
                    Message msg1 = new Message();
                    msg1.what =3;
                    BetModeActivity.fragmentdw.handler.sendMessage(msg1);
                    dismiss();
                }else if(gameType.equals("tbww")){
                    Message msg1 = new Message();
                    msg1.what =3;
                    BetModeActivity.fragmenttbww.handler.sendMessage(msg1);
                    dismiss();
                }else if(gameType.equals("ssc")||gameType.equals("pkww")||gameType.equals("xync")){
                    Message msg1 = new Message();
                    msg1.what =3;
                    BetModeActivity.fragmentssc.handler.sendMessage(msg1);
                    dismiss();
                }else{
                    Message msg1 = new Message();
                    msg1.what =3;
                    BetModeActivity.fragment28.handler.sendMessage(msg1);
                    dismiss();
                }
                et_mode.setText(list.get(i).getModelName());
                tv_tbid.setText(""+list.get(i).getID());
                Message msg1 = new Message();
                msg1.what =3;
               handler.sendMessage(msg1);
            }
        });
        // 导入布局
        this.setContentView(mMenuView);
        // 设置动画效果
        this.setAnimationStyle(R.style.AnimationFade);
       // setAnimationStyle(R.style.AnimTools);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight( DisplayUtil.dp2px(context, 160));
        // 设置可触
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
        // 单击弹出窗以外处 关闭弹出窗
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mMenuView.findViewById(R.id.ll_my_bet_mode).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
    public interface  SetList{
        void setParams(MybetModeDB.DataBean.ModeljsonBean list1);
    }
    private SetList setParams;
    public void SetPL(SetList setParams){
        this.setParams=setParams;
        if(setParams!=null){
            setParams.setParams(list.get(position));
        }
    }
}
