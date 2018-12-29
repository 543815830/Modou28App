package com.ruixin.administrator.ruixinapplication.usercenter.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseFragment;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.popwindow.MyFechPop;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.ClipPictureActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.MyBindbankActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.UpdateInfoActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.GoldDepositDb;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CommonUtil;
import com.ruixin.administrator.ruixinapplication.utils.GetImagePath;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 李丽 on 2018/11/26.
 */
/**
 * 作者：Created by ${李丽} on 2018/4/16.
 * 邮箱：543815830@qq.com
 * 绑定银行卡
 */
public class BindBankFragent extends BaseFragment {
    private View view;
    TextView my_bank;
    EditText et_bank_number;
    EditText et_real_name;
    String userId, userToken, cardname, realname, bankname;
    TextView et_fech;
    LinearLayout ll_fm_bank;
    LinearLayout ll_account_number;
    LinearLayout ll_qrcode;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    String url;
    MyFechPop pop;
    Button add;
    Button select_file;
    public static final String Bank_ICON_DIC = Environment
            .getExternalStorageDirectory()
            + File.separator + "bankIcon";//文件路徑
    String fileName = "bankIcon.jpg";

    List<GoldDepositDb.DataBean.BanklistBean> fechlist = new ArrayList<>();
    private final String IMAGE_TYPE = "image/*";
    private final int CHOOSE_PHOTO_REQUEST_CODE = 0;
    String filePath = "";
    ImageView iv_card_code;
    String result;
    File file;
    private File qrIconFile ;// 相册或者拍照保存的文件
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    @Override
    protected View initView() {
        if (view == null) {
            view = View.inflate(mContext, R.layout.fm_bind_bank, null);
            my_bank = view.findViewById(R.id.my_bank);
            et_fech = view.findViewById(R.id.et_fech);
            ll_fm_bank = view.findViewById(R.id.ll_fm_bank);
            ll_account_number = view.findViewById(R.id.ll_account_number);
            ll_qrcode = view.findViewById(R.id.ll_qrcode);
            et_bank_number = view.findViewById(R.id.et_bank_number);
            et_real_name = view.findViewById(R.id.et_real_name);
            add = view.findViewById(R.id.add);
            iv_card_code = view.findViewById(R.id.iv_card_code);
            select_file = view.findViewById(R.id.select_file);
            my_bank.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            my_bank.setOnClickListener(new MyOnclick());
            et_fech.setOnClickListener(new MyOnclick());
            et_fech.setText("支付宝");
            if (et_fech.getText().toString().equals("支付宝") || et_fech.getText().toString().equals("微信支付")) {
                ll_account_number.setVisibility(View.GONE);
                ll_qrcode.setVisibility(View.VISIBLE);
            } else {
                ll_account_number.setVisibility(View.VISIBLE);
                ll_qrcode.setVisibility(View.GONE);
            }
            et_fech.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (et_fech.getText().toString().equals("支付宝") || et_fech.getText().toString().equals("微信支付")) {
                        ll_account_number.setVisibility(View.GONE);
                        ll_qrcode.setVisibility(View.VISIBLE);
                    } else {
                        ll_account_number.setVisibility(View.VISIBLE);
                        ll_qrcode.setVisibility(View.GONE);
                    }
                }
            });
            select_file.setOnClickListener(new MyOnclick());
            add.setOnClickListener(new MyOnclick());
            userId = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id", "");
            userToken = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token", "");
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            url = URL.getInstance().deposit_info;
            new GoldDepositeAsyncTask().execute();
        }
        return view;
    }

    private class MyOnclick implements View.OnClickListener {
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

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.my_bank:
                    Intent intent = new Intent(mContext, MyBindbankActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    startActivity(intent);
                    break;
                case R.id.et_fech:
                    if (fechlist != null && fechlist.size() > 0) {
                        showPop();
                    }
                    break;
                case R.id.select_file:
                    if (isAllGranted) {
                        choosePhoto();
                        return;
                    }
                    ActivityCompat.requestPermissions(
                           getActivity(),
                            new String[] {
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            MY_PERMISSION_REQUEST_CODE
                    );
                    choosePhoto();

                    break;
                case R.id.add:
                    cardname = et_bank_number.getText().toString().trim();
                    realname = et_real_name.getText().toString().trim();
                    bankname = et_fech.getText().toString().trim();
                    if (isValid()) {
                        prarams.put("cardname", cardname);
                        prarams.put("realname", realname);
                        prarams.put("bankname", bankname);
                        url = URL.getInstance().deposit_info + "?act=bdCard";
                        new CGoldDepositeAsyncTask().execute();
                    }

                    break;
            }
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + mContext.getPackageName()));
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
    /*创建头像保存文件的路径并创建文件*/
    private void initQrIconFile() {
        //新建一个File，传入文件夹目录
        qrIconFile = new File(Bank_ICON_DIC);
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!qrIconFile.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            boolean mkdirs = qrIconFile.mkdirs();
            Log.e("tag", "initHeadIconFile()---mkdirs : " + mkdirs);

        }
        qrIconFile = new File(Bank_ICON_DIC, fileName);//原图的保存文件
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    if (data != null) {
                        initQrIconFile();
                        Uri originalUri = data.getData(); // 获得图片的uri
                        Log.e("tag",""+originalUri);
                     //  Uri originalUri = content://com.android.providers.media.documents/document/image%3A22817
                        if (originalUri != null) {
                            filePath = GetImagePath.getPath(mContext, originalUri);
                        }
                        Log.e("filePath", "filePath : " + filePath);
                        //获取请求设置的图片宽高
                        int reqWidth = iv_card_code.getWidth();
                        int reqHeight = iv_card_code.getHeight();
                        // 取bitmap的时候就压缩照片，同时解决了照片过大(>2048*2048px)的硬件加速问题(渲染不了)
                        // 和图片过大(20M),内存溢出问题
                   Bitmap     bitmap = decodeSampledBitmapFromFile(filePath,
                                reqWidth, reqHeight);
                        int rotation = CommonUtil.getImageRotationByPath(
                                mContext, filePath);
                        Log.e("tag","rotation"+rotation);
                        // 取出照片角度，解决某些手机上照片显示角度问题
       /* if (rotation != 0) {*/
                        Matrix m = new Matrix();
                        m.setRotate(rotation);
                        try {
                            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                    bitmap.getHeight(), m, true);
                        } catch (OutOfMemoryError e) {
                        }
                        iv_card_code.setImageBitmap(bitmap);
                       saveMyBitmap(qrIconFile,bitmap);


                      /*  Log.e("tag", "file" + file.getName());

                        Glide.with(mContext)
                                .load(file)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                                .skipMemoryCache(true)//跳过内存缓存
                                .into(iv_card_code);*/
                        Log.e("tag", "filePath" + filePath);
                        prarams.put("file", filePath);

                    }

                }
                break;
        }
    }
    /**
     * 压缩图片,并设置图片宽高
     *
     * @param filename  源图片文件路径
     * @param reqWidth  需要将图片设置的宽度
     * @param reqHeight 需要将图片设置的高度
     * @return 返回压缩后并设置宽高的bitmap
     */
    public Bitmap decodeSampledBitmapFromFile(String filename,
                                              int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //这里decodeFile只是为了获取到原图的宽高,
        //这样option里面就保存了原图的宽高,后面在计算inSampleSize才能有值
        BitmapFactory.decodeFile(filename, options);

        //inSampleSize 这个值是一个int，当它小于1的时候，将会被当做1处理，
        //如果大于1，那么就会按照比例（1 / inSampleSize）缩小bitmap的宽和高、降低分辨率
        //这个参数也是为了压缩图片,减小分辨率,避免出现加载超大图片是出现oom
        options.inSampleSize = calculateInSampSize(options, reqWidth, reqHeight);

        //inJustDecodeBounds 如果将这个值置为true，
        //那么在解码的时候将不会返回bitmap，只会返回这个bitmap的尺寸。
        //但我们需要返回bitmap,所以设为false
        options.inJustDecodeBounds = false;

        //inPreferredConfig 这个值是设置色彩模式
        // 默认值是ARGB_8888，在这个模式下，一个像素点占用4bytes空间
        //一般对透明度不做要求的话，一般采用RGB_565模式，这个模式下一个像素点占用2bytes。
        //也就是说RGB_565模式可以节省一些内存空间
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        try {
            Bitmap val = BitmapFactory.decodeFile(filename, options);
          /*  Log.i(TAG,"after decode image File:" + options.outWidth
                    + "|" + options.outHeight);*/
            return val;
        } catch (Exception e) {
          /*  Log.e(TAG, "decodeSampledBitmapFromFile - Exception"
                    + e);*/
        } catch (OutOfMemoryError e) {
           /* Log.e(TAG, "decodeSampledBitmapFromFile - OutOfMemoryError"
                    + e);*/
        } finally {

        }
        return null;
    }
    /**
     * 根据原图的宽高和请求设置的宽高,计算出inSampleSize并返回
     * @param options   已经保存原图宽高的options
     * @param reqWidth  请求设置图片的宽度
     * @param reqHeight 请求设置图片的高度
     * @return  option的inSampleSize
     */
    public int calculateInSampSize(BitmapFactory.Options options,
                                   int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
     /* //  Log.i(TAG, "calculateInSampSize()  height : " + height
                + ", height : " + width);*/
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            inSampleSize = ((Double) Math.floor(height * 1.0f / reqHeight))
                    .intValue();
            int wSampSize = ((Double) Math.floor(width * 1.0f / reqWidth))
                    .intValue();
            if (inSampleSize < wSampSize) {
                inSampleSize = wSampSize;
            }
        }
      //  Log.i(TAG, "calculateInSampSize()  inSampleSize : " + inSampleSize);
        return inSampleSize;
    }
    public static void saveMyBitmap(File file, Bitmap mBitmap) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPop() {
        if (pop == null) {
            pop = new MyFechPop(mContext, fechlist, et_fech);
        }

        //监听窗口的焦点事件，点击窗口外面则取消显示
        pop.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    pop.dismiss();
                }
            }
        });
        //设置默认获取焦点
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        // mytrusteePop.showAsDropDown(ll_choose_scheme,0,0);
        pop.showAtLocation(ll_fm_bank, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //如果窗口存在，则更新
        pop.update();
    }

    @SuppressLint("StaticFieldLeak")
    private class GoldDepositeAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(url, prarams);
            Log.e("金币提现的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    GoldDepositDb goldDepositDb = gson.fromJson(s, GoldDepositDb.class);
                    if (goldDepositDb.getStatus() == 1) {
                        fechlist = goldDepositDb.getData().getBanklist();
                    } else if (goldDepositDb.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams, s, new GoldDepositeAsyncTask());
                        //  new MyTrusteeAsyncTask().execute();
                    } else if (goldDepositDb.getStatus() == -97 || goldDepositDb.getStatus() == -99) {
                        Unlogin.doLogin(mContext);
                    } else {
                        Entry entry = gson.fromJson(s, Entry.class);
                        Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private class CGoldDepositeAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (filePath.equals("")) {
                result = AgentApi.dopost3(url, prarams);
            } else {
                result = AgentApi.dopost4(url, prarams, "file",qrIconFile);
            }
            Log.e("绑定的数据返回", "" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                int index = s.indexOf("{");
                //如果第一个字符是大括号则进行解析
                if (index == 0) {
                    Gson gson = new Gson();
                    GoldDepositDb goldDepositDb = gson.fromJson(s, GoldDepositDb.class);
                    if (goldDepositDb.getStatus() == 1) {
                        //  fechlist=goldDepositDb.getData().getBanklist();
                        Toast.makeText(mContext, "恭喜您，添加成功！", Toast.LENGTH_SHORT).show();
                    } else if (goldDepositDb.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams, s, new GoldDepositeAsyncTask());
                        //  new MyTrusteeAsyncTask().execute();
                    } else if (goldDepositDb.getStatus() == -97 || goldDepositDb.getStatus() == -99) {
                        Unlogin.doLogin(mContext);
                    } else {
                        Entry entry = gson.fromJson(s, Entry.class);
                        Toast.makeText(mContext, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(mContext, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }


    public boolean isValid() {
        if(bankname.contains("支付宝")||bankname.contains("微信")){
            if (filePath.equals("")) {
                Toast.makeText(mContext, "请选择文件!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            if (cardname.equals("")) {
                Toast.makeText(mContext, "请输入收款账号!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }




        if (realname.equals("")) {
            Toast.makeText(mContext, "请输入真实姓名!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bankname.equals("")) {
            Toast.makeText(mContext, "请选择收款方式!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
