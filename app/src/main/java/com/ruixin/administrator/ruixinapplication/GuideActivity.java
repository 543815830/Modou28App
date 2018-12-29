package com.ruixin.administrator.ruixinapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private VpAdapter vpAdapter;
    private ArrayList<ImageView>imageViews;
    private int[] imgResArr = new int[]{R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3, R.mipmap.guide4};
    String url;
    String message;
    String available;
    String title;
    AlertDialog mAlertDialog;
    int localVersion = 0;
    String download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initImages();
        initView();
    }

    private void initView() {
       vpAdapter=new VpAdapter(imageViews);
        viewpager.setAdapter(vpAdapter);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initImages(){
        //设置每一张图片都填充窗口
        ViewPager.LayoutParams mParams = new ViewPager.LayoutParams();
        imageViews = new ArrayList<ImageView>();

        for(int i=0; i<imgResArr.length; i++)
        {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);//设置布局
            iv.setImageResource(imgResArr[i]);//为ImageView添加图片资源
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//这里也是一个图片的适配
            imageViews.add(iv);
            if (i == imgResArr.length -1 ){
                iv.setClickable(true);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String str = formatter.format(curDate);
                        String random= String.valueOf((int)Math.random()*9000+1000);
                        url="http://router.ruixinyunke.com/android/get/domain/101/"+str+random;
                        new getdomainAsyncTask().execute();
                    }
                });
               /* //为最后一张图片添加点击事件
                iv.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        //启动主界面
                        Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                        intent.putExtra("where","2");
                        startActivity(intent);
                        //关闭当前界面
                        finish();
                        return true;

                    }
                });*/
            }

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
                        message=checkUpDb.getData().getFeature();
                        title=checkUpDb.getData().getPoptips();
                        download=checkUpDb.getData().getDownload();
                        RuiXinApplication.getInstance().setUrl(checkUpDb.getData().getDomain());
                        startMainActivity(checkUpDb);



                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(GuideActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(GuideActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void startMainActivity(CheckUpDb checkUpDb) {
        Intent intent=new Intent(GuideActivity.this,MainActivity.class);
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
}
