package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.utils.GetImagePath;

import java.io.File;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 头像选择拍照或手机相册activity
 */
public class PhotoMainActivity extends Activity implements View.OnClickListener {
    public static final String HEAD_ICON_DIC = Environment
            .getExternalStorageDirectory()
            + File.separator + "headIcon";
    protected final String TAG = getClass().getSimpleName();
    private File headIconFile = null;// 相册或者拍照保存的文件
    private File headClipFile = null;// 裁剪后的头像
    private String headFileNameStr = "headIcon.jpg";
    private String clipFileNameStr = "clipIcon.jpg";
    Uri imageUri;
    private final String IMAGE_TYPE = "image/*";
    String filePath = "";
    //权限相关
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private final int CHOOSE_PHOTO_REQUEST_CODE = 0;
    private final int TAKE_PHOTO_REQUEST_CODE = 1;
    private final int CLIP_PHOTO_BY_SYSTEM_REQUEST_CODE = 2;
    private final int CLIP_PHOTO_BY_SELF_REQUEST_CODE = 3;
    private Button take_photo, choose_photo;
    private ImageView head_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_take_photo);
        init();
    }

    private void init() {
        take_photo = findViewById(R.id.take_photo);
        take_photo.setOnClickListener(this);
        choose_photo = findViewById(R.id.choose_photo);
        choose_photo.setOnClickListener(this);
        head_img = findViewById(R.id.head_img);
        initHeadIconFile();
    }

    private void initHeadIconFile() {
        headIconFile = new File(HEAD_ICON_DIC);
        Log.e(TAG, "initHeadIconFile()---headIconFile.exists() : " + headIconFile.exists());
        if (!headIconFile.exists()) {
            boolean mkdirs = headIconFile.mkdirs();
            Log.e(TAG, "initHeadIconFile()---mkdirs : " + mkdirs);
        }
        headIconFile = new File(HEAD_ICON_DIC, headFileNameStr);
        headClipFile = new File(HEAD_ICON_DIC, clipFileNameStr);
    }

    @Override
    public void onClick(View v) {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        switch (v.getId()) {
            case R.id.take_photo:

                if (isAllGranted) {
                    openCamera();
                    return;
                }
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSION_REQUEST_CODE
                );


                break;
            case R.id.choose_photo:
                if (isAllGranted) {
                    choosePhoto();
                    return;
                }
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSION_REQUEST_CODE
                );

                break;
        }
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
     * 打开系统摄像头拍照获取图片
     */
    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                imageUri = Uri.fromFile(headIconFile);
            } else {
                //FileProvider为7.0新增应用间共享文件,在7.0上暴露文件路径会报FileUriExposedException
                //为了适配7.0,所以需要使用FileProvider,具体使用百度一下即可
                imageUri = FileProvider.getUriForFile(this,
                        "com.channelst.headimgclip.fileprovider", headIconFile);//通过FileProvider创建一个content类型的Uri
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
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
                Log.d(TAG, "调用系统剪辑照片后返回.........");
                if (resultCode == RESULT_OK) {
                    Bitmap bm = BitmapFactory.decodeFile(headClipFile.getAbsolutePath());
                    head_img.setImageBitmap(bm);
                    Log.e(TAG, "onActivityResult()---bm : " + bm);
                } else {
                    Log.e(TAG, "onActivityResult()---resultCode : " + resultCode);
                }
                break;
            case TAKE_PHOTO_REQUEST_CODE:
                Log.i(TAG, "拍照后返回.........");
                if (resultCode == RESULT_OK) {
                    //拍照后返回,调用系统裁剪,系统裁剪无法裁剪成圆形
                    //clipPhotoBySystem(imageUri);
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
                            filePath = GetImagePath.getPath(this, originalUri);

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
                    filePath = headClipFile.getAbsolutePath();
                    Log.e("tag", "filePath" + filePath);
                    saveImagepath();
                    head_img.setImageBitmap(bm);
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
        intent.putExtra(ClipPictureActivity.IMAGE_PATH_ORIGINAL, filePath);
        intent.putExtra(ClipPictureActivity.IMAGE_PATH_AFTER_CROP,
                headClipFile.getAbsolutePath());

        startActivityForResult(intent, CLIP_PHOTO_BY_SELF_REQUEST_CODE);

    }

    private void saveImagepath() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imagepath", filePath);
        editor.commit();
    }

}
