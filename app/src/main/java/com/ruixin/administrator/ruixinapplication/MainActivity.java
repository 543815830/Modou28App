package com.ruixin.administrator.ruixinapplication;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.exchangemall.activity.ProductDetailActivity;
import com.ruixin.administrator.ruixinapplication.gamecenter.GameCenterFragment;
import com.ruixin.administrator.ruixinapplication.home.HomeFragment;
import com.ruixin.administrator.ruixinapplication.notice.NoticeAdapter;
import com.ruixin.administrator.ruixinapplication.notice.NoticeCotentActivity;
import com.ruixin.administrator.ruixinapplication.notice.NoticeFragment;
import com.ruixin.administrator.ruixinapplication.notice.UnReadNoDb;
import com.ruixin.administrator.ruixinapplication.rank.RankFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.UserFragment;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.CommitScanLoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.RedbagActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.UserStateActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.User;
import com.ruixin.administrator.ruixinapplication.utils.AdDb;
import com.ruixin.administrator.ruixinapplication.utils.AdDialog;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.BackHandlerHelper;
import com.ruixin.administrator.ruixinapplication.utils.BadgeView;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.SharedPrefUtility;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.badgeview.QBadgeView;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 主界面
 */
public class MainActivity extends BaseActivity{
    @BindView(R.id.home_page)
    RadioButton homePage;
    @BindView(R.id.ranking_list)
    RadioButton rankingList;
    @BindView(R.id.game_center)
    RadioButton gameCenter;
    @BindView(R.id.notice)
    RadioButton notice;
    @BindView(R.id.user_center)
    RadioButton userCenter;
    static String userId;
    private RadioGroup mRg_main;
    private static String userToken;
    private List<BaseFragment> mBaseFragment;
    //上次切换的Fragment
    private Fragment mContent;
    //选中的Fragment的位置
    private int position;
    final String str = null;
    User user;
    private long lastBackPress;
    public static int REQUEST_CODE = 66;
    String where="2";
    BadgeView badge1;
    int notenum;
    Button btn_msg;
    private static HashMap<String, String> prarams = new HashMap<String, String>();
    String message;
    String available;
    String title;
    AlertDialog mAlertDialog;
    int localVersion = 0;
    String version;
    String download;
    String result;
    public final static int REQUEST_READ_PHONE_STATE = 1;
    TelephonyManager telephonyManager;
    String imei;
    AdDialog adDialog;
    boolean first=true;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("ontag","onNewIntent");
        Log.e("tag","intent"+intent);
        if(intent!=null){
            where = intent.getStringExtra("where");
            if(where!=null){
                setListener();
            }

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        if (sharedPreferences.getString("is_login", "").equals("true")) {
            userId = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
            userToken = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
            if (userToken == null || userId == null) {
               if(badge1!=null){
                   badge1.hide();
               }
            }
        } else {
            if(badge1!=null){
                badge1.hide();
            }
        }
        Log.e("ontag","onResume");
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
      //  Log.e("Copylistenerdemo", clipboard.getPrimaryClip().getItemAt(0).getText().toString());
        if (!clipboard.hasPrimaryClip()) {
            Log.e("tag","!clipboard");
            return;
        } //如果是文本信息
        if (clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            Log.e("tag","clipboard");
            ClipData cdText = clipboard.getPrimaryClip();
            ClipData.Item item = cdText.getItemAt(0);
            Log.e("tag","item.getText()"+item.getText());
            //此处是TEXT文本信息
            if(item.getText()!=null){
            String text=item.getText().toString();
            if (text.contains("【红包来啦】http://120.78.87.50/RedPack/")) {
                String str = item.getText().toString();

                String [] b = str.split("k/");//以A作为分割点,将字符串a分割为2个字符串数组分别为
                String clip=b[1];
                Log.e("tag","str"+str);
                Intent intent=new Intent(this,RedbagActivity.class);
                intent.putExtra("clip",clip);
                startActivity(intent);
               clipboard.setPrimaryClip(ClipData.newPlainText(null, null));
               // clipboard.setPrimaryClip(null);
               /* String key = "*";
                final int first = str.indexOf(key);
                if (first >= 0) {
                    String new1 = str.substring(first + 1);
                    int tow = new1.indexOf(key);
                    if (tow >= 0) {
                        String new2 = new1.substring(0, tow);
                        System.out.print(new2);
                        if (new2.length() == 8) {
                            //new2即为口令字符串
                        }
                    }
                }*/
            }else if(text.contains("看广告，玩游戏，拿奖品，立即体验http://120.78.87.50/Prize/")){
                String str = item.getText().toString();
                String [] b = str.split("Prize/");//以A作为分割点,将字符串a分割为2个字符串数组分别为
                String clip=b[1];
                Log.e("tag","str"+str);
                Intent intent=new Intent(this,ProductDetailActivity.class);
                intent.putExtra("clip",clip);
                startActivity(intent);
                clipboard.setPrimaryClip(ClipData.newPlainText(null, null));
            }
            }
        }
    }

    private void ShowAddialog() {
        SharedPreferences sharedPreferences =this.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        if (sharedPreferences.getString("is_login", "").equals("true")) {
            if((userId!=null)&&(!userId.equals(""))){
                prarams.put("usersid",userId);
                new GetAdInfoAsyncTask().execute();
            }
        }else{
            telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
            //动态获取权限

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    if(first){
                    ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                        first=false;
                    }
                } else {
                    imei = telephonyManager.getDeviceId();
                    Log.e("tag", "" + imei);
                    prarams.put("IMEI", imei);
                    new GetAdInfoAsyncTask().execute();
                }

            }


    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.e("tag", "onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                Log.e("tag", "onRequestPermissionsResult1");
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                    Log.e("tag", "onRequestPermissionsResult2");
                    imei = telephonyManager.getDeviceId();
                    Log.e("tag", "" + imei);
                }else{

                }
                break;

            default:
                break;
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class GetAdInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost(URL.getInstance().getAd_Url, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("获取弹出广告层", "消息返回结果result" + result);
            if (result != null) {
                Gson gson = new Gson();
                AdDb adDb = gson.fromJson(result, AdDb.class);
                if (adDb.getStatus() == 1) {
                    if (adDb.getList() != null&&adDb.getList().size()>0) {
                        String path = RuiXinApplication.getInstance().getUrl() + adDb.getList().get(0).getImgname();
                        String src=adDb.getList().get(0).getImgsrc();
                       adDialog= new AdDialog(MainActivity.this,path,src);
                       adDialog.show();
                    }
                }


            } else {
                Toast.makeText(MainActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
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
                        Intent intent = new Intent();
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
                    }
                });
        mAlertDialog = builder.create();
        mAlertDialog.show();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        where = getIntent().getStringExtra("where");
        if(where.equals("22")){
            message=getIntent().getStringExtra("message");
            title=getIntent().getStringExtra("title");
            available=getIntent().getStringExtra("available");
            download=getIntent().getStringExtra("download");
            version=getIntent().getStringExtra("version");
            getLocalVersion(MainActivity.this);
            if(localVersion<Long.parseLong(version.substring(0,8))){
                if(available.equals("true")){
                    showReminderDialog(MainActivity.this);
                }else{
                    showReminderDialog2(MainActivity.this);
                }

            }
            if(!(title.equals("")||title==null)){
                showReminderDialog3(MainActivity.this);
            }
        }
        if(where==null){
            where="2";
        }
        Log.e("tag","onCreate");
        //注册
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);

        //初始化view
        initView();
        //初始化fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShowAddialog();
        Log.e("ontag","onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ontag","onRestart");
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEnventBus(MessageEvent enevnt) {
       if(enevnt.result.equals("1")){
           Log.e("notenum", "" + notenum);
            userToken=getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
            userId=getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            new MyNunreadAsyncTask().execute();
        }else  if(enevnt.result.equals("2")){
           --notenum;
           remind(btn_msg);//绑定小红点
       }

    }
    /**
     * 初始化View
     */
    @SuppressLint("StaticFieldLeak")
    private class MyNunreadAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().unRedNotice_URL, prarams);
            Log.e("我的未读的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                String re = null;
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //第一层解析
                    if (jsonObject != null) {
                        re = jsonObject.optString("status");
                    }

                    if (re.equals("1")) {
                        Gson gson=new Gson();
                        UnReadNoDb unre=gson.fromJson(s,UnReadNoDb.class);
                        notenum= Integer.parseInt(unre.getData().getNotenum());
                        if(notenum>0){
                            Log.e("note",""+notenum);
                            remind(btn_msg);//绑定小红点
                        }
                    }else if (re.equals("99")) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams,s,   new MyNunreadAsyncTask());
                    }else if(re.equals("-99")||re.equals("-97")){
                        Unlogin.doLogin(MainActivity.this);
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(MainActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(MainActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void initView() {
        setContentView(R.layout.activity_main);
        mRg_main = findViewById(R.id.rg_main);
        btn_msg=findViewById(R.id.btn_msg);
        userToken=getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
        userId=getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
        if(!userToken.equals("")){
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    new MyNunreadAsyncTask().execute();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 2000);
        }


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            Log.e("onWindowFocusChanged", "onWindowFocusChanged");
          View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION//隐藏虚拟按键(导航栏)
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);// SDK21
            }
        }
    }

    private void remind(View view) { //BadgeView的具体使用
        if(badge1==null){
            badge1 = new BadgeView(this, view);// 创建一个BadgeView对象，view为你需要显示提醒的控件
        }
        badge1.setText(""+notenum); // 需要显示的提醒类容
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setTextColor(Color.WHITE); // 文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
        //badge1.setBackgroundResource(R.mipmap.icon_message_png); //设置背景图片
        badge1.setTextSize(12); // 文本大小
        //badge1.setBadgeMargin(3, 3); // 水平和竖直方向的间距
        badge1.setBadgeMargin(5); //各边间隔
        // badge1.toggle(); //显示效果，如果已经显示，则影藏，如果影藏，则显示
        if(notenum>0){
            badge1.show();// 只有显示
        }else{
            badge1.hide();
        }

        // badge1.hide();//影藏显示
    }
    /**
     * 设置监听
     */
    private void setListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中
        if(where.equals("3")){
            mRg_main.check(R.id.user_center);
        }else if(where.equals("4")){
            mRg_main.check(R.id.notice);
        }else{
            mRg_main.check(R.id.home_page);
        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId) {
                case R.id.home_page://首页
                    position = 0;
                    break;
                case R.id.ranking_list://排行榜
                    position = 1;
                    break;
                case R.id.game_center://游戏中心
                    position=2;
                  /*  userToken=getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
                    userId=getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
                    Log.e("tag3",""+userToken);
                    if(userToken!=null){
                        position = 2;
                    }else {
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        intent.putExtra("where","3");
                        startActivity(intent);
                       //finish();
                    }*/
                    break;
                case R.id.notice://消息通知
                    position = 3;
                  /*  userToken=getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");
                    userId=getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
                    Log.e("tag3",""+userToken);
                    if(userToken!=null){
                        position = 3;
                    }else {
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        intent.putExtra("where","5");
                        startActivity(intent);
                        //finish();
                    }*/
                    break;
                case R.id.user_center://个人中心
                    position = 4;
                    break;
                default:
                    position = 0;
                    break;
            }
            //根据位置得到对应的fragment
            BaseFragment to = getFragment();
            //替换掉framelayout
            switchFragment(mContent, to);
        }
    }
    /**
     * @param from 刚才显示的fragment，也就是马上要隐藏的fragment
     * @param to   将要切换到的fragment，要添加进来的
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            mContent = to;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            //才切换
            //判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加
                if (from != null) {//from隐藏
                    transaction.hide(from);
                }


                //to添加
                if (to != null) {
                    transaction.add(R.id.fl_content, to).commit();
                }

            } else {
                // to已经被添加
                //from隐藏
                if (from != null) {//from隐藏
                    transaction.hide(from);
                }
                //显示to
                if (to != null) {
                    transaction.show(to).commit();
                }
            }
        }
    }
    /**
     * 切换
     * 这种替换会每点一次都初始化一次，会浪费资源
     * 正确的切换方式应该是add（），再次切换时只需隐藏当前，显示另外一个

     private void switchFragment(BaseFragment fragment) {
     FragmentManager fm=getSupportFragmentManager();
     //开启事务
     FragmentTransaction transaction= fm.beginTransaction();
     //替换
     transaction.replace(R.id.fl_content,fragment);
     //提交事务
     transaction.commit();
     }
     */
    /**
     * 根据位置得到对应的fragment
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new HomeFragment());
        mBaseFragment.add(new RankFragment());
        mBaseFragment.add(new GameCenterFragment());
        mBaseFragment.add(new NoticeFragment());
        mBaseFragment.add(new UserFragment());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ontag","onPause");
        if(adDialog!=null){
            adDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ontag","onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }

    public FragmentBackListener backListener;
    private boolean isInterception = false;
    public FragmentBackListener getBackListener() {
        return backListener;
    }
    public void setBackListener(FragmentBackListener backListener) {
        this.backListener = backListener;
    }
    //是否拦截
    public boolean isInterception() {
        return isInterception;
    }

    public void setInterception(boolean isInterception) {
        this.isInterception = isInterception;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("tag","onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isInterception()) {
                if (backListener != null) {
                    Log.e("tag","onBackPressed2");
                    backListener.onBackForward();
                    return false;
                }
            }else{
                if (BackHandlerHelper.handleBackPress(this)) {
                    Log.e("tag","onBackPressed22");
                    if (System.currentTimeMillis() - lastBackPress < 1000) {
                        super.onBackPressed();
                    } else {
                        Log.e("tag","onBackPressed32");
                        lastBackPress = System.currentTimeMillis();
                       Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


 /*  @Override
    public void onBackPressed() {
        Log.e("tag","onBackPressed1");
        if (BackHandlerHelper.handleBackPress(this)) {
            Log.e("tag","onBackPressed2");
            if (System.currentTimeMillis() - lastBackPress < 1000) {
                super.onBackPressed();
            } else {
                Log.e("tag","onBackPressed3");
                lastBackPress = System.currentTimeMillis();
               Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }
    }
*/

}
