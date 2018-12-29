package com.ruixin.administrator.ruixinapplication.gamecenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.activity.BetModeActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MybetModeDB;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.MymodeDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class MyBetModeAdapter extends BaseAdapter {
    private Context mContext;
    private  List<MybetModeDB.DataBean.ModeljsonBean>list;
    //本地字段，用户recyclerview保存状态
    public boolean isSelected = false;
    String userId;
    String userToken;
    String EgameName;
    String result;
    String msg;
    int status = 0;
    int position;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> gaprarams = new HashMap<String, String>();
    public MyBetModeAdapter(Context context,  List<MybetModeDB.DataBean.ModeljsonBean>list,String userId, String userToken,String EgameName){
        this.mContext=context;
        this.list=list;
        this.userId=userId;
        this.userToken=userToken;
        this.EgameName=EgameName;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        HashMap mHashMap =new HashMap();
        if (mHashMap.get(i) == null) {
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.item_my_bet_mode,null);
            viewHolder.rl_my_mode=convertView.findViewById(R.id.rl_my_mode);
          //  mLoadingAndRetryManager = LoadingAndRetryManager.generate(viewHolder.rl_my_mode, null);
            viewHolder.my_mode=convertView.findViewById(R.id.my_mode);
            viewHolder.btn_delete=convertView.findViewById(R.id.btn_delete);
            mHashMap.put(i, convertView);
            convertView.setTag(viewHolder);
        } else {
            convertView = (View) mHashMap.get(i);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.my_mode.setText(list.get(i).getModelName());
        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gaprarams.put("usersid", userId);
                gaprarams.put("usertoken", userToken);
                gaprarams.put("gamename", EgameName);
                gaprarams.put("modelid",list.get(i).getModelID());
                position=i;
                new DelModeAsyncTask().execute();

            }
        });
        return convertView;
    }

    static  class ViewHolder{
        RelativeLayout rl_my_mode;
       TextView my_mode;
       ImageView btn_delete;
    }
    private class DelModeAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          // mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().DelMyBetMode_URL,gaprarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取游戏名称列表", "消息返回结果result" + result);
            if (!(result == null||result.equals(""))) {

                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                    msg=re.optString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                   // mLoadingAndRetryManager.showContent();
                        list.remove(position);
                        notifyDataSetChanged();

                   Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();

                }  else if (status == 0) {
                   Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
                   // mLoadingAndRetryManager.showContent();

                }  else if (status == -11) {
                   Toast.makeText(mContext, "该模式在使用中，无法删除！", Toast.LENGTH_SHORT).show();
                   // mLoadingAndRetryManager.showContent();

                } else if (status == 99) {
                        /*抗攻击*/
                    Gson gson = new Gson();
                    ATK atK = gson.fromJson(result, ATK.class);
                    String vaild_str = atK.getVaild_str();
                    Log.e("tag", "" + vaild_str);
                    String vaildd_md5 = FormatUtils.md5(vaild_str);
                    Log.e("tag", "" + vaildd_md5);
                    gaprarams.put("vaild_str", vaildd_md5);
                    new DelModeAsyncTask().execute();
                }else{
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(mContext, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

