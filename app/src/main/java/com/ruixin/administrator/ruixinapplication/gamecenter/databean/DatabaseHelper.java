package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 李丽 on 2018/6/28.
 * 最爱游戏的数据库帮助类
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_Favors_Game = "CREATE TABLE Game ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + "gamename VARCHAR,"
            + "gamechname VARCHAR, "
            + "gametype VARCHAR) ";
    private Context mContext;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库的同时创建Book表
        db.execSQL(CREATE_Favors_Game);
        //提示数据库创建成功
        Log.e("tag", "数据库创建成功");
        //Toast.makeText(mContext, "数据库创建成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
