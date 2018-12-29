package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.GetImagePath;
import com.ruixin.administrator.ruixinapplication.popwindow.PhotoPopupWindows;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 修改资料的界面
 */
public class UpdateInfoActivity extends Activity {
    LinearLayout back_arrow;//返回
    TextView tv_title;//标题
    String imagepath;
    ImageView iv_up_head;//头像图片
    TextView up_tv_head;//修改头像
    TextView et_user_id;//用户Id
    EditText et_user_name;//用户昵称
    EditText et_user_qq;//用户qq
    EditText et_user_alipy;//用户支付宝
    RelativeLayout rl_sex;
    RelativeLayout rl_birthday;
    TextView user_sex;//性别
    ImageView down_arrow;//向下箭头
    TextView user_birthday;//生日
    Button save;
    int mYear,mMonth,mDay;
    String userId="",userName="",userQq="",userAlipy="",userSex="",userBirthday="";
    File file;
    SharedPreferences sharedPreferences;
    PhotoPopupWindows popMenus;
    int REQUEST_CODE;
    String result;
    public static int RESULT_CODE = 4;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private final int CHOOSE_PHOTO_REQUEST_CODE = 0;
    private final int TAKE_PHOTO_REQUEST_CODE = 1;
    private final int CLIP_PHOTO_BY_SYSTEM_REQUEST_CODE = 2;
    private final int CLIP_PHOTO_BY_SELF_REQUEST_CODE = 3;
    public static final String HEAD_ICON_DIC = Environment
            .getExternalStorageDirectory()
            + File.separator + "headIcon";//文件路徑
    protected final String TAG = getClass().getSimpleName();
    private File headIconFile = null;// 相册或者拍照保存的文件
    private File headClipFile = null;// 裁剪后的头像
    private String headFileNameStr = "headIcon.jpg";
    private String clipFileNameStr = "clipIcon.jpg";
    String filePath = "",userToken="";
    Uri imageUri;
    private final String IMAGE_TYPE = "image/*";
    private HashMap<String, String> prarams = new HashMap<String, String>();
    private String[] sexArry = new String[] { "女", "男" };// 性别选择
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        initStatus();
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("资料修改");
        userId=getIntent().getStringExtra("userId");
        userToken=getIntent().getStringExtra("userToken");
        if(userId.equals("")){
           Toast.makeText(UpdateInfoActivity.this,"您尚未登录！",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(UpdateInfoActivity.this,LoginActivity.class);
            intent.putExtra("where","2");
            startActivity(intent);
        }else{
            if(userToken!=null){
            readParameter();
            initView();
            }else{
               Toast.makeText(UpdateInfoActivity.this,"您尚未登录！",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        initHeadIconFile();
    }
    /* 标题栏与状态栏颜色一致用这种*/
    protected void initStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView =getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }
    private void initView() {
        Log.e("tag","initView");
        rl_sex=findViewById(R.id.rl_sex);
        rl_birthday=findViewById(R.id.rl_birthday);
        user_sex=findViewById(R.id.et_user_sex);
        if(userSex.equals("")){
            user_sex.setText("男");
        }else{
            user_sex.setText(userSex);
        }

        user_birthday=findViewById(R.id.et_user_birthday);
        if(userBirthday.equals("")) {
            user_birthday.setText("1992-11-20");
        }else{
            user_birthday.setText(userBirthday);
        }

        back_arrow=findViewById(R.id.back_arrow);
        iv_up_head=findViewById(R.id.iv_up_head);
        Glide.with(UpdateInfoActivity.this)
                .load(imagepath)
                .bitmapTransform(new CropCircleTransformation(UpdateInfoActivity.this))
                .diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                .skipMemoryCache( true )//跳过内存缓存
                .placeholder(R.drawable.iv_user) //占位图
                .error(R.drawable.iv_user)  //出错的占位图
                .into(iv_up_head);
        Log.e("tag","imagepath"+imagepath);
        up_tv_head=findViewById(R.id.up_tv_head);
        et_user_id=findViewById(R.id.et_user_id);
        et_user_id.setText(userId);
        et_user_name=findViewById(R.id.et_user_name);
        et_user_name.setText(userName);
        if(!userName.equals("")){
            et_user_name.setCursorVisible(false);
            et_user_name.setFocusable(false);
            et_user_name.setFocusableInTouchMode(false);
        }
        et_user_qq=findViewById(R.id.et_user_qq);
        et_user_qq.setText(userQq);
        if(!userQq.equals("")){
            et_user_qq.setCursorVisible(false);
            et_user_qq.setFocusable(false);
            et_user_qq.setFocusableInTouchMode(false);
        }
        et_user_alipy=findViewById(R.id.et_user_alipy);
        et_user_alipy.setText(userAlipy);
        if(!userAlipy.equals("")){
            et_user_alipy.setCursorVisible(false);
            et_user_alipy.setFocusable(false);
            et_user_alipy.setFocusableInTouchMode(false);
        }
        down_arrow=findViewById(R.id.iv_down_arrow);
        save=findViewById(R.id.save);
        back_arrow.setOnClickListener(new MyOnclick());
        iv_up_head.setOnClickListener(new MyOnclick());
        up_tv_head.setOnClickListener(new MyOnclick());
        rl_sex.setOnClickListener(new MyOnclick());
        rl_birthday.setOnClickListener(new MyOnclick());
        user_sex.setOnClickListener(new MyOnclick());
       // down_arrow.setOnClickListener(new MyOnclick());
       // user_birthday.setOnClickListener(new MyOnclick());
        save.setOnClickListener(new MyOnclick());
    }


    /*读取用户信息*/
    private void readParameter() {
        sharedPreferences = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        userName=sharedPreferences.getString("user_name","");
        userQq=sharedPreferences.getString("qq","");
        userAlipy=sharedPreferences.getString("user_alipy","");
        boolean contains=sharedPreferences.getString("user_head","").contains("http");
        if (contains){
            imagepath=sharedPreferences.getString("user_head","");
        }else{
            imagepath= RuiXinApplication.getInstance().getUrl()+sharedPreferences.getString("user_head","");
        }

        userSex=sharedPreferences.getString("user_sex","");
        if(userSex.equals("M")){
            userSex="男";
        }else{
            userSex="女";
        }
        userBirthday=sharedPreferences.getString("user_birthday","");
    }
/*创建头像保存文件的路径并创建文件*/
    private void initHeadIconFile() {
        //新建一个File，传入文件夹目录
        headIconFile = new File(HEAD_ICON_DIC);
        Log.e(TAG, "initHeadIconFile()---headIconFile.exists() : " + headIconFile.exists());
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!headIconFile.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            boolean mkdirs = headIconFile.mkdirs();
            Log.e(TAG, "initHeadIconFile()---mkdirs : " + mkdirs);

        }
        headIconFile = new File(HEAD_ICON_DIC, headFileNameStr);//原图的保存文件
        headClipFile = new File(HEAD_ICON_DIC, clipFileNameStr);//裁剪后的保存文件
    }
    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            /**
             * 第 1 步: 检查是否有相应的权限
             */
            boolean isAllGranted = checkPermissionAllGranted(
                    new String[] {
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }
            );
            switch (view.getId()){
                case R.id.back_arrow:
                  finish();//返回，关闭当前页面
                    break;
                case R.id.iv_up_head:
                    showPopMenu();//出现选择拍照，相册框
                    break;
                case R.id.btn_alter_pic_camera://拍照
                    if (isAllGranted) {
                        openCamera();
                        return;
                    }
                    ActivityCompat.requestPermissions(
                           UpdateInfoActivity. this,
                            new String[] {
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            MY_PERMISSION_REQUEST_CODE
                    );
                    break;
                case R.id.btn_alter_pic_photo://相册
                    if (isAllGranted) {
                        choosePhoto();
                        return;
                    }
                    ActivityCompat.requestPermissions(
                            UpdateInfoActivity. this,
                            new String[] {
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            MY_PERMISSION_REQUEST_CODE
                    );
                    break;
                case R.id.up_tv_head:
                    showPopMenu();//出现选择拍照，相册框
                    break;
                case R.id.et_user_sex:
                    showSexChooseDialog();//性别选择框
                    break;
                case R.id.rl_sex:
                    Log.e("iv_down_arrow","iv_down_arrow---");
                    showSexChooseDialog();//性别选择框
                    break;
                case R.id.rl_birthday:
                    new DatePickerDialog(UpdateInfoActivity.this, onDateSetListener, mYear, mMonth, mDay).show();//日期选择框
                    break;
                case R.id.save://保存
                    userId=et_user_id.getText().toString();
                    if(userId.equals("")){
                       Toast.makeText(UpdateInfoActivity.this,"您尚未登录",Toast.LENGTH_SHORT).show();
                    }else{
                        userId=et_user_id.getText().toString();
                        prarams.put("usersid",userId);
                        prarams.put("usertoken",userToken);
                        userQq=et_user_qq.getText().toString();
                        prarams.put("tbUserQQ",userQq);
                        userAlipy=et_user_alipy.getText().toString();
                        prarams.put("tbAlipay",userAlipy);
                        userSex=user_sex.getText().toString();
                        if(userSex.equals("女")){
                            prarams.put("tbUserGender","F");
                        }else{
                            prarams.put("tbUserGender","M");
                        }
                        userBirthday=user_birthday.getText().toString();
                        prarams.put("tbBirthday",userBirthday);
                        Log.e("图片地址：",""+filePath);
                        file=new File(filePath);
                        Log.e("图片文件：",""+file);
                        Log.e("图片文件名称：",""+file.getName());
                       // Log.e("图片文件名称：",""+file.);

                       // prarams.put("tbUserPhoto",file);
                        //prarams.put("tbBirthday",userBirthday);
                        new UpdateInfoAsyncTask().execute();
                    }
                    break;
            }
        }
    }

    private void showPopMenu() {
       MyOnclick myOnclick=new MyOnclick();
        popMenus = new PhotoPopupWindows(this,myOnclick);
        popMenus.showAtLocation(this.findViewById(R.id.rl_person_info), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }
    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
    /**
     * 从系统图库中选择图片
     */
    private void choosePhoto() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openAlbumIntent.setType(IMAGE_TYPE);
            startActivityForResult(openAlbumIntent, CHOOSE_PHOTO_REQUEST_CODE);

        }
    }

    /**
     * 打开系统摄像头拍照获取图片并存储到headIconFile文件
     */
    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                imageUri = Uri.fromFile(headIconFile);// 传递路径
            } else {
                //FileProvider为7.0新增应用间共享文件,在7.0上暴露文件路径会报FileUriExposedException
                //为了适配7.0,所以需要使用FileProvider,具体使用百度一下即可
                imageUri = FileProvider.getUriForFile(this,
                        "com.channelst.headimgclip.fileprovider", headIconFile);//通过FileProvider创建一个content类型的Uri
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);// 更改系统默认存储路径
            startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
            Log.e(TAG, "openCamera()---intent" + intent);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult()---requestCode" + requestCode
                + ", resultCode : " + resultCode);
        switch (requestCode) {
            case CLIP_PHOTO_BY_SYSTEM_REQUEST_CODE:
                Log.d(TAG,"调用系统剪辑照片后返回.........");
                if (resultCode == RESULT_OK) {
                    Bitmap bm = BitmapFactory.decodeFile(headClipFile.getAbsolutePath());
                   iv_up_head.setImageBitmap(bm);
                    Log.e(TAG, "onActivityResult()---bm : " + bm);
                } else {
                    Log.e(TAG, "onActivityResult()---resultCode : " + resultCode);
                }
                break;
            case TAKE_PHOTO_REQUEST_CODE:
                Log.i(TAG,"拍照后返回.........");
                if (resultCode == RESULT_OK) {
                    //拍照后返回,调用系统裁剪,系统裁剪无法裁剪成圆形
                   // clipPhotoBySystem(imageUri);
                    //调用自定义裁剪
                 clipPhotoBySelf(headIconFile.getAbsolutePath());
                }
                break;
            case CHOOSE_PHOTO_REQUEST_CODE:
                Log.i(TAG, "从相册选取照片后返回....");
                if (resultCode == RESULT_OK) {

                    if (data != null) {

                        Uri originalUri = data.getData(); // 获得图片的uri
                        Log.i(TAG, "originalUri : " + originalUri);
                        if (originalUri != null) {
                            filePath = GetImagePath.getPath(this,originalUri);

                        }
                        Log.i(TAG, "filePath : " + filePath);

                        if (filePath != null && filePath.length() > 0) {
                            //clipPhotoBySystem(originalUri);
                            //调用自定义裁剪
                            clipPhotoBySelf(filePath);
                        }
                    }

                }
                break;
            case CLIP_PHOTO_BY_SELF_REQUEST_CODE:
                Log.i(TAG, "从自定义切图返回..........");
                if (resultCode == RESULT_OK) {
                    Bitmap bm = BitmapFactory.decodeFile(headClipFile.getAbsolutePath());
                    Log.e(TAG, "从自定义切图返回.........."+bm+REQUEST_CODE+"-----"+headClipFile.getAbsolutePath());
                    filePath=headClipFile.getAbsolutePath();
                    File file=new File(filePath);
                    Glide.with(UpdateInfoActivity.this)
                            .load(file)
                            .bitmapTransform(new CropCircleTransformation(UpdateInfoActivity.this))
                            .diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                            .skipMemoryCache( true )//跳过内存缓存
                            .placeholder(R.drawable.iv_user) //占位图
                            .error(R.drawable.iv_user)  //出错的占位图
                            .into(iv_up_head);
                    Log.e("tag","filePath"+filePath);
                    prarams.put("imagepath",filePath);
                   // iv_up_head.setImageBitmap(bm);
                    Log.e(TAG, " iv_up_head.setImageBitmap.........."+bm+"-----"+REQUEST_CODE);
                    Log.i(TAG, "onActivityResult()---bm : " + bm);

                } else {
                    Log.i(TAG, "onActivityResult()---resultCode : " + resultCode);
                }
                break;
        }
    }
    /**
     * 调用自定义切图方法
     *
     * @param filePath
     */
    protected void clipPhotoBySelf(String filePath) {
        Log.i(TAG, "通过自定义方式去剪辑这个照片");
        //进入裁剪页面,此处用的是自定义的裁剪页面而不是调用系统裁剪
        Intent intent = new Intent(this, ClipPictureActivity.class);
        intent.putExtra(ClipPictureActivity.IMAGE_PATH_ORIGINAL, filePath);//传递原图的路径
        intent.putExtra(ClipPictureActivity.IMAGE_PATH_AFTER_CROP,
                headClipFile.getAbsolutePath());//传递裁剪后的保存路径
        startActivityForResult(intent, CLIP_PHOTO_BY_SELF_REQUEST_CODE);

    }

    //保存信息异步请求
    @SuppressLint("StaticFieldLeak")
    private class UpdateInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            if(filePath.equals("")){
               result= AgentApi.dopost3(URL.getInstance().UserInfo_URL,prarams);
            }else{
                result= AgentApi.dopost4(URL.getInstance().UserInfo_URL,prarams,"tbUserPhoto",file);
            }
            Log.e("修改信息返回结果：",""+result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject=new JSONObject(result);
                String status=jsonObject.getString("status");

                if(status.equals("1")){
                   Toast.makeText(UpdateInfoActivity.this,"信息修改成功",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_name",userName);  //用户密保问题
                    editor.putString("qq",userQq);  //用户密保问题
                    editor.putString("user_alipy",userAlipy);  //用户密保问题
                    editor.apply();
                    EventBus.getDefault().post(new MessageEvent("3"));
                    finish();
                }else if(status.equals("-97")||status.equals("-99")){
                    Unlogin.doLogin(UpdateInfoActivity.this);
                }else if (status.equals("99")) {
                    /*抗攻击*/

                    Unlogin.doAtk(prarams,result, new UpdateInfoAsyncTask());
                   // new UpdateInfoAsyncTask().execute();
                }else {
                    String msg=jsonObject.getString("msg");
                   Toast.makeText(UpdateInfoActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /* 性别选择框 */
    private void showSexChooseDialog() {
        Log.e("showSexChooseDialog","showSexChooseDialog---");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框

        int checkedItem;
        if(user_sex.getText().toString().equals("女")){
            checkedItem=0;
        }else{
            checkedItem=1;
        }
        builder.setSingleChoiceItems(sexArry, checkedItem, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                user_sex.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }
    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            }
            user_birthday.setText(days);
        }
    };
}
