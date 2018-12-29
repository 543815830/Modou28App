package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

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
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.CheckUpDb;
import com.ruixin.administrator.ruixinapplication.LaucherActivity;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.utils.DataCleanManager;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.SharedPrefUtility;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 设置界面
 */
public class SetActivity extends Activity {
    Intent intent;
    String userId;
    String userToken;
    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_log_off)
    RelativeLayout rlLogOff;
    @BindView(R.id.rl_clear)
    RelativeLayout rlClear;
    @BindView(R.id.rl_checkup)
    RelativeLayout rlCheckup;
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
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        tvTitle.setText("更多功能");
        initStatus();
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick({R.id.back_arrow,R.id.rl_checkup, R.id.rl_log_off})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.rl_checkup:
                getLocalVersion(SetActivity.this);
                SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);
                String random= String.valueOf((int)Math.random()*9000+1000);
                url="http://router.ruixinyunke.com/android/get/domain/101/"+str+random;
             new   getdomainAsyncTask().execute();
                break;
            case R.id.rl_log_off://退出登录
                AlertDialog dialog = new AlertDialog.Builder(SetActivity.this)
                        .setTitle("温馨提示")//设置对话框的标题
                        .setMessage("确定要退出账号吗？")//设置对话框的内容
                        //设置对话框的按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPrefUtility.removeData(SetActivity.this);
                                RuiXinApplication.getInstance().setUserToken(null);
                                RuiXinApplication.getInstance().setUserId(null);
                               /* Intent intent = new Intent();
                                setResult(05, intent);*/
                                EventBus.getDefault().post(new MessageEvent("3"));
                                finish();
                                //readParameter();
                            }
                        }).create();
                dialog.show();
                break;
        }
    }

    public int getLocalVersion(Context ctx) {

        try {
            PackageInfo packageInfo = ctx.getApplicationContext().getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.e("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
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
                        title=checkUpDb.getData().getPoptips();

                        if(localVersion<Long.parseLong(checkUpDb.getData().getVersion().substring(0,8))){
                            message=checkUpDb.getData().getFeature();

                            download=checkUpDb.getData().getDownload();
                            if(checkUpDb.getData().getAvailable().equals("true")){
                                available="true";
                                showReminderDialog(SetActivity.this);
                            }else{
                                available="false";
                                showReminderDialog2(SetActivity.this);
                            }

                        }else{
                            Toast.makeText(SetActivity.this, "当前版本已是最新版本", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(SetActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                // mLoadingAndRetryManager.showContent();
                Toast.makeText(SetActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @OnClick(R.id.rl_clear)
    public void onViewClicked() {
        AlertDialog dialog = new AlertDialog.Builder(SetActivity.this)
                .setTitle("温馨提示")//设置对话框的标题
                .setMessage("确定要清除缓存吗？")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataCleanManager.cleanInternalCache(getApplicationContext());
                        DataCleanManager.cleanExternalCache(getApplicationContext());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.get(SetActivity.this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
                                Glide.get(SetActivity.this).clearMemory();//清理内存缓存  可以在UI主线程中进行
                            }
                        });
                        // DataCleanManager.cleanSharedPreference(getApplicationContext());
                        // getSharedPreferences("UserCache", Activity.MODE_PRIVATE).edit().clear();
                        SharedPreferences sp = getSharedPreferences("UserCache", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        dialog.dismiss();
                        //readParameter();
                    }
                }).create();
        dialog.show();
    }
    private void showReminderDialog(Context context) {
        Log.e("tag", "showReminderDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("新版本提示")
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mAlertDialog != null) mAlertDialog.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // new LotteryView.getRoundsAsyncTask().execute();
                        Intent intent = new Intent();
                        //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(download);
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                });
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }
    private void showReminderDialog2(Context context) {
        Log.e("tag", "showReminderDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("新版本提示")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // new LotteryView.getRoundsAsyncTask().execute();
                        Intent intent = new Intent();
                        //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(download);
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                });
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }
    private void showReminderDialog3(Context context) {
        Log.e("tag", "showReminderDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(title)
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mAlertDialog != null) mAlertDialog.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // new LotteryView.getRoundsAsyncTask().execute();
                        if (mAlertDialog != null) mAlertDialog.dismiss();
                    }
                });
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }
}
