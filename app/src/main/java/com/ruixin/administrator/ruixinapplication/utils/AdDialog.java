package com.ruixin.administrator.ruixinapplication.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.home.databean.JumpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 李丽 on 2018/12/27.
 */

public class AdDialog extends Dialog implements android.view.View.OnClickListener {
    private ImageButton cancel;
    private ImageButton experienceNow;
    Context context;
    View adView;
    String path;
    String src;
    public AdDialog(Context context, String path,String src) {
        super(context);
        this.context = context;
        this.path = path;
        this.src = src;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 这句代码换掉dialog默认背景，否则dialog的边缘发虚透明而且很宽
        // 总之达不到想要的效果
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        adView = View.inflate(this.context, R.layout.ad_dialog, null);
        setContentView(adView);
        // 这句话起全屏的作用
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        initView();
        initListener();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.dismiss();
        return super.onTouchEvent(event);
    }

    private void initListener() {
        cancel.setOnClickListener(this);
        experienceNow.setOnClickListener(this);
    }

    private void initView() {
        cancel = (ImageButton) findViewById(R.id.ib_close);
        experienceNow = (ImageButton) findViewById(R.id.ib_fora_now);
        Glide.with(context)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(experienceNow);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close:
                this.dismiss();
                break;
            case R.id.ib_fora_now:
                JumpMethod.insideJump(src, context);
               this. dismiss();
                break;
        }
    }


}
