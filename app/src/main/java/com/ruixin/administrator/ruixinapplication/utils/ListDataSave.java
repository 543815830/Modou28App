package com.ruixin.administrator.ruixinapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李丽 on 2018/12/26.
 */

public class ListDataSave {

    public static   void setDataList(Context context, String tag,String strJson) {
        Log.e("tag","str"+strJson);
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserMenu",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public static  List<List<String>> getDataList(Context context,String tag) {
        SharedPreferences preferences = context.getSharedPreferences("UserMenu",
                Activity.MODE_PRIVATE);
        List<List<String>> datalist=new ArrayList<List<String>>();
        String strJson = preferences.getString(tag, null);
        Log.e("tag","str"+strJson);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<List<String>>>() {
        }.getType());
        return datalist;

    }

}
