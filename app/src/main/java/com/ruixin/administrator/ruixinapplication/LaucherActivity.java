package com.ruixin.administrator.ruixinapplication;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.LotteryView;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.URL;

import java.text.SimpleDateFormat;
import java.util.Date;

/*启动界面*/
public class LaucherActivity extends Activity {
    String url;
    String message;
    String available;
    String title;
    AlertDialog mAlertDialog;
    int localVersion = 0;
    String download;
    Boolean user_first;
    SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher);
        settings = getSharedPreferences("Selector", 0);
        user_first = settings.getBoolean("FIRST", true);
        if (user_first) {
            settings.edit().putBoolean("FIRST", false).commit();
            startGuideActivity();
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            String random= String.valueOf((int)Math.random()*9000+1000);
            url="http://router.ruixinyunke.com/android/get/domain/101/"+str+random;
            new getdomainAsyncTask().execute();
        }

    }
    //获取域名信息
    private class getdomainAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.doGet(url);
            Log.e("result", "getRoundsAsyncTaskresult=" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() > 0) {
                int index = s.indexOf("{");
                if (index == 0) {
                    Gson gson = new Gson();
                    CheckUpDb checkUpDb=gson.fromJson(s,CheckUpDb.class);
                    if(checkUpDb.getStatus()==1){
                      //  URL.getInstance().setUrl(checkUpDb.getData().getDomain());
                        message=checkUpDb.getData().getFeature();
                        title=checkUpDb.getData().getPoptips();
                        download=checkUpDb.getData().getDownload();

                        startMainActivity(checkUpDb);



                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(LaucherActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LaucherActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startMainActivity(CheckUpDb checkUpDb) {

            RuiXinApplication.getInstance().setUrl(checkUpDb.getData().getDomain());
            Intent intent=new Intent(LaucherActivity.this,MainActivity.class);
            intent.putExtra("where","22");
            intent.putExtra("message",message);
            intent.putExtra("available",checkUpDb.getData().getAvailable());
            intent.putExtra("title",title);
            intent.putExtra("download",download);
            intent.putExtra("version",checkUpDb.getData().getVersion());
            startActivity(intent);
            //关闭当前界面
            finish();

    }

    private void startGuideActivity() {
        //启动主界面
        Intent intent=new Intent(this,GuideActivity.class);
        //intent.putExtra("where","2");
        startActivity(intent);
        //关闭当前界面
        finish();
    }

}
