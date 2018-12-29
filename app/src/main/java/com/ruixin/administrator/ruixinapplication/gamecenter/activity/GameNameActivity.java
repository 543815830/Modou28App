package com.ruixin.administrator.ruixinapplication.gamecenter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.HotGameAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.LatestGameAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.adapter.TotalGameNameAdapter;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.DatabaseHelper;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.GameName1;
import com.ruixin.administrator.ruixinapplication.utils.ChineseToPinyinHelper;
import com.ruixin.administrator.ruixinapplication.utils.LetterIndexView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 游戏名称界面
 */
public class GameNameActivity extends Activity {
    TextView tvTitle;
    TextView show_letter_in_left;
    LinearLayout closeArrow;
    EditText serch_et;
    ImageView iv_search;
    ImageView iv_x;
    ListView total_game_name;
    String gameName;
    String EgameName;
    String gameType;
    String convert;
    boolean touch = false;
    int positionForSection;
    int sectionForPosition;
    TotalGameNameAdapter adapter;
    public static int RESULT_CODE = 400;
    List<GameName1.DataBean.GamelistBean> list = new ArrayList<>();//游戏列表
    List<GameName1.DataBean.GamelistBean> list4 = new ArrayList<GameName1.DataBean.GamelistBean>();
    List<GameName1.DataBean.HotgamelistBean> list1 = new ArrayList<>();//最热
    List<GameName1.DataBean.LatesgamelistBean> list2 = new ArrayList<>();//最爱
    List<GameName1.DataBean.LatesgamelistBean> list22 = new ArrayList<>();//最爱删除
    private DatabaseHelper dbHelper;
    List<Integer>array=new ArrayList<>();
    String substring;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                adapter.setOnSetGame(new TotalGameNameAdapter.SetGameName() {
                    @Override
                    public void getGameName(String gameName, String EgameName, String gameType) {
                        Intent intent = new Intent();
                        intent.putExtra("gameName", gameName);
                        intent.putExtra("EgameName", EgameName);
                        intent.putExtra("gameType", gameType);
                        setResult(RESULT_CODE, intent);
                        finish();
                    }
                });
            } else if (msg.what == 2) {
                adapter.setOnSetGame(new TotalGameNameAdapter.SetGameName() {
                    @Override
                    public void getGameName(String gameName, String EgameName, String gameType) {
                        Intent intent = new Intent();
                        intent.putExtra("gameName", gameName);
                        intent.putExtra("EgameName", EgameName);
                        intent.putExtra("gameType", gameType);
                        setResult(RESULT_CODE, intent);
                        finish();
                    }
                });
            }

        }

    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_name);
        initStatus();
        //获取数据库前六位并加入到list3里面去
        dbHelper = new DatabaseHelper(this, "FavorsGameStore.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT gamechname ,gamename ,gametype,count( * ) AS count\n" +
                "FROM Game\n" +
                "GROUP BY gamechname,gamename,gametype\n" +
                "ORDER BY count DESC\n" +
                "LIMIT 6", null);
        while (cursor1.moveToNext()) {
            gameName = cursor1.getString(cursor1.getColumnIndex("gamechname"));
            EgameName = cursor1.getString(cursor1.getColumnIndex("gamename"));
            gameType = cursor1.getString(cursor1.getColumnIndex("gametype"));
            GameName1.DataBean.LatesgamelistBean st = new GameName1.DataBean.LatesgamelistBean(gameName, EgameName, gameType);//student_info存一个条目的数据
            list22.add(st);//最爱
        }
        closeArrow = findViewById(R.id.close_arrow);
        total_game_name = findViewById(R.id.total_game_name);
        tvTitle = findViewById(R.id.tv_title);
        serch_et = findViewById(R.id.serch_et);
        serch_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!serch_et.getText().toString().equals("")) {
                    iv_x.setVisibility(View.VISIBLE);
                } else {
                    iv_x.setVisibility(View.GONE);
                }

            }
        });
        list = RuiXinApplication.getInstance().getList();//游戏列表
        list1 = RuiXinApplication.getInstance().getList1();//最热
        iv_search = findViewById(R.id.iv_search);
        iv_x = findViewById(R.id.iv_x);
        iv_search.setOnClickListener(new MyOnClick());
        iv_x.setOnClickListener(new MyOnClick());
        show_letter_in_left = findViewById(R.id.show_letter_in_left);
        tvTitle.setText("游戏选择列表");
        closeArrow.setOnClickListener(new MyOnClick());
        initData();

    for(int j=0;j<list4.size();j++){
        for(int i=0;i<list22.size();i++){
        if(list22.get(i).getGamechname().equals(list4.get(j).getGamechname())){
           list2.add(list22.get(i));
        }
    }
}
        adapter = new TotalGameNameAdapter(this, list2, list1, list4, handler);
        total_game_name.setAdapter(adapter);
        final LetterIndexView letterIndexView = findViewById(R.id.letter_index_view);
        letterIndexView.setTextViewDialog(show_letter_in_left);
        letterIndexView.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String currentChar) {
                Log.e("tag1111111", "currentChar" + currentChar);
                touch = true;
                if (currentChar.equals("最爱")) {
                    total_game_name.setSelection(0);
                    positionForSection = 0;
                } else if (currentChar.equals("热门")) {
                    total_game_name.setSelection(1);
                    positionForSection = 1;
                } else {
                    positionForSection = adapter.getPositionForSection(currentChar.charAt(0));
                    total_game_name.setSelection(positionForSection);
                }


            }
        });
        total_game_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://触摸屏幕时，只执行一次
                        Log.i("TAG", "执行触摸时的操作");
                        touch = false;
                        break;
                    case MotionEvent.ACTION_MOVE://触摸到屏幕时，反复执行
                        Log.i("TAG", "执行移动时的操作");
                        touch = false;
                        break;
                    case MotionEvent.ACTION_UP://离开屏幕时，只执行一次
                        Log.i("TAG", "执行触摸离开时的操作");
                        touch = false;
                        break;
                }
                return false;
            }

        });

        total_game_name.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("scrollState", "scrollState" + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("onScroll", "onScroll");
                Log.e("firstVisibleItem", "firstVisibleItem" + firstVisibleItem);
                Log.e("positionForSection", "positionForSection" + positionForSection);
                if (touch) {
                    sectionForPosition = adapter.getSectionForPosition(positionForSection);//通过第一个可见项的位置，获得所在分类组的索引号
                } else {
                    sectionForPosition = adapter.getSectionForPosition(firstVisibleItem);//通过第一个可见项的位置，获得所在分类组的索引号
                }

                letterIndexView.updateLetterIndexView(sectionForPosition);


            }
        });

        total_game_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("tag8", "onItemClick" + i);
                if (i == 0) {
                    Log.e("tag8", "" + i);
                    adapter.setOnSetGame(new TotalGameNameAdapter.SetGameName() {
                        @Override
                        public void getGameName(String gameName, String EgameName, String gameType) {
                            Intent intent = new Intent();
                            intent.putExtra("gameName", gameName);
                            intent.putExtra("EgameName", EgameName);
                            intent.putExtra("gameType", gameType);
                            setResult(RESULT_CODE, intent);
                            finish();
                        }
                    });

                } else if (i == 1) {
                    Log.e("tag8", "" + i);
                    adapter.setOnSetGame(new TotalGameNameAdapter.SetGameName() {
                        @Override
                        public void getGameName(String gameName, String EgameName, String gameType) {
                            Intent intent = new Intent();
                            intent.putExtra("gameName", gameName);
                            intent.putExtra("EgameName", EgameName);
                            intent.putExtra("gameType", gameType);
                            setResult(RESULT_CODE, intent);
                            finish();
                        }
                    });

                } else {
                    gameName = list4.get(i - 2).getGamechname();
                    gameType = list4.get(i - 2).getGametype();
                    EgameName = list4.get(i - 2).getGamename();
                    Intent intent = new Intent();
                    intent.putExtra("gameName", gameName);
                    intent.putExtra("EgameName", EgameName);
                    intent.putExtra("gameType", gameType);
                    setResult(RESULT_CODE, intent);
                    finish();
                }
            }
        });
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }


    private void initData() {
        list = RuiXinApplication.getInstance().getList();
        for (int i = 0; i < list.size(); i++) {
            GameName1.DataBean.GamelistBean gamename = new GameName1.DataBean.GamelistBean();
            gamename.setGamechname(list.get(i).getGamechname());
            gamename.setGametype(list.get(i).getGametype());
            gamename.setGamename(list.get(i).getGamename());
            if (list.get(i).getGamechname().equals("重庆时时彩")) {
                convert = "CHONGQINGSHISHICAI";
            } else if (list.get(i).getGamechname().equals("骰宝外围")) {
                convert = "TOUBAOWAIWEI";
            } else {
                convert = ChineseToPinyinHelper.getInstance().getPinyin(list.get(i).getGamechname()).toUpperCase();
            }
            // String convert = ChineseToPinyinHelper.getInstance().getPinyin(list.get(i).getGamechname()).toUpperCase();
            Log.e("nsc", "convert=" + convert);
            gamename.setPinyin(convert);
            String substring = convert.substring(0, 1);
            if (substring.matches("[A-Z]")) {
                gamename.setFirstLetter(substring);
            } else {
                gamename.setFirstLetter("#");
            }
            list4.add(gamename);
            Log.e("nsc", "list4=" + list4.size());
        }

        Collections.sort(list4, new Comparator<GameName1.DataBean.GamelistBean>() {
            @Override
            public int compare(GameName1.DataBean.GamelistBean lhs, GameName1.DataBean.GamelistBean rhs) {
                if (lhs.getFirstLetter().contains("#")) {
                    return 1;
                } else if (rhs.getFirstLetter().contains("#")) {
                    return -1;
                } else {
                    Log.e("lhs", "lhs.getFirstLetter().compareTo(rhs.getFirstLetter()=" + lhs.getFirstLetter().compareTo(rhs.getFirstLetter()));
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                }
            }
        });
    }

    private class MyOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.close_arrow://关闭选择游戏
                    finish();
                    break;
                case R.id.iv_search://搜索游戏名称
                    if (serch_et.getText().toString() != null) {
                        gameName = serch_et.getText().toString();
                        search(gameName);
                    }
                    break;
                case R.id.iv_x://clear
                    iv_x.setVisibility(View.GONE);
                    serch_et.setText("");
                    initData();
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    public void search(String string) {
        list4.clear();
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getGamechname().equals(string)) {
                GameName1.DataBean.GamelistBean gamename = new GameName1.DataBean.GamelistBean();
                gamename.setGamechname(list.get(i).getGamechname());
                gamename.setGamename(list.get(i).getGamename());
                gamename.setGametype(list.get(i).getGametype());
                if (list.get(i).getGamechname().equals("重庆时时彩")) {
                    convert = "CHONGQINGSHISHICAI";
                } else if (list.get(i).getGamechname().equals("骰宝外围")) {
                    convert = "TOUBAOWAIWEI";
                } else {
                    convert = ChineseToPinyinHelper.getInstance().getPinyin(list.get(i).getGamechname()).toUpperCase();
                }
                // String convert = ChineseToPinyinHelper.getInstance().getPinyin(list.get(i).getGamechname()).toUpperCase();
                Log.e("nsc", "convert=" + convert);
                gamename.setPinyin(convert);
                substring = convert.substring(0, 1);
                if (substring.matches("[A-Z]")) {
                    gamename.setFirstLetter(substring);
                } else {
                    gamename.setFirstLetter("#");
                }
                list4.clear();
                list4.add(gamename);
                break;
            } else if (!list.get(i).getGamechname().equals(string)) {
                convert = ChineseToPinyinHelper.getInstance().getPinyin(string).toUpperCase();
                Log.e("convert", "convert=" + convert);
                substring = convert.substring(0, 1);
                String convert2 = ChineseToPinyinHelper.getInstance().getPinyin(list.get(i).getGamechname()).toUpperCase();
                if (convert2.length() >= convert.length()) {
                    if (convert2.substring(0, convert.length()).equals(convert)) {
                        GameName1.DataBean.GamelistBean gamename = new GameName1.DataBean.GamelistBean();
                        gamename.setGamechname(list.get(i).getGamechname());
                        gamename.setGamename(list.get(i).getGamename());
                        gamename.setGametype(list.get(i).getGametype());
                        if (list.get(i).getGamechname().equals("重庆时时彩")) {
                            convert = "CHONGQINGSHISHICAI";
                        } else if (list.get(i).getGamechname().equals("骰宝外围")) {
                            convert = "TOUBAOWAIWEI";
                        } else {
                            convert = ChineseToPinyinHelper.getInstance().getPinyin(list.get(i).getGamechname()).toUpperCase();
                        }
                        // String convert = ChineseToPinyinHelper.getInstance().getPinyin(list.get(i).getGamechname()).toUpperCase();
                        Log.e("nsc", "convert=" + convert);
                        gamename.setPinyin(convert);
                        substring = convert.substring(0, 1);
                        if (substring.matches("[A-Z]")) {
                            gamename.setFirstLetter(substring);
                        } else {
                            gamename.setFirstLetter("#");
                        }
                        list4.add(gamename);
                    }
                }

            }

        }
        // 只在当前的listview上进行搜索
        adapter.notifyDataSetChanged();
        // total_game_name.setAdapter(new Adapter(MainActivity.this, arrayListlist));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy----", "onDestroy=");
    }
}
