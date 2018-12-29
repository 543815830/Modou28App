package com.ruixin.administrator.ruixinapplication.usercenter.activiy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.popwindow.MyFechPop;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.GoldDepositDb;
import com.ruixin.administrator.ruixinapplication.usercenter.fragment.BindBankFragent;
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

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 修改收款账号的界面
 */
public class UpdateMybankActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_bank_number)
    EditText etBankNumber;
    @BindView(R.id.ll_account_number)
    LinearLayout llAccountNumber;
    @BindView(R.id.et_real_name)
    EditText etRealName;
    @BindView(R.id.et_fech)
    TextView etFech;
    @BindView(R.id.iv_card_code)
    ImageView ivCardCode;
    @BindView(R.id.select_file)
    Button selectFile;
    @BindView(R.id.ll_qrcode)
    LinearLayout llQrcode;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.ll_fm_bank)
    LinearLayout llFmBank;
    String userId,userToken,id,typename,cardname,realname,bankname,result;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    String url;
    List<GoldDepositDb.DataBean.BanklistBean> fechlist = new ArrayList<>();
    File file;
    MyFechPop pop;
    public static final String Bank_ICON_DIC = Environment
            .getExternalStorageDirectory()
            + File.separator + "bankIcon";//文件路徑
    String fileName = "bankIcon.jpg";
    private final String IMAGE_TYPE = "image/*";
    private final int CHOOSE_PHOTO_REQUEST_CODE = 0;
    String filePath = "";
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    public static final int Result_code=55;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mybank);
        ButterKnife.bind(this);
        initStatus();
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        id = getIntent().getStringExtra("id");
        typename = getIntent().getStringExtra("typename");
        cardname = getIntent().getStringExtra("cardname");
        realname = getIntent().getStringExtra("realname");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        url = URL.getInstance().deposit_info;
        new GoldDepositeAsyncTask().execute();
        tvTitle.setText("修改收款账号");
        initView();
        backArrow.setOnClickListener(new MyOnclick());
    }
    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }
    private void initView() {
        etRealName.setText(realname);
        etFech.setText(typename);
        etFech.setOnClickListener(new MyOnclick());
        if(etFech.getText().toString().contains("支付宝")||etFech.getText().toString().contains("微信")){
            llAccountNumber.setVisibility(View.GONE);
            llQrcode.setVisibility(View.VISIBLE);
            String path= RuiXinApplication.getInstance().getUrl()+cardname;
            Log.e("tag","u-----"+path);
            Glide.with(UpdateMybankActivity.this)
                    .load(path)
                    .into(ivCardCode);
        }else{
            llAccountNumber.setVisibility(View.VISIBLE);
            llQrcode.setVisibility(View.GONE);
            etBankNumber.setText(cardname);
        }

        etFech.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(etFech.getText().toString().contains("支付宝")||etFech.getText().toString().contains("微信")){
                    llAccountNumber.setVisibility(View.GONE);
                    llQrcode.setVisibility(View.VISIBLE);
                }else{
                    llAccountNumber.setVisibility(View.VISIBLE);
                    llQrcode.setVisibility(View.GONE);
                  //  etBankNumber.setText(cardname);
                }
            }
        });
      //  etBankNumber.setText(cardname);
       add.setOnClickListener(new MyOnclick());
        selectFile.setOnClickListener(new MyOnclick());
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
                        Unlogin.doLogin(UpdateMybankActivity.this);
                    } else {
                        Entry entry = gson.fromJson(s, Entry.class);
                        Toast.makeText(UpdateMybankActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(UpdateMybankActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(UpdateMybankActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


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
            switch (view.getId()) {
                case R.id.back_arrow:
                   finish();
                    break;
                case R.id.et_fech:
                    if (fechlist != null || fechlist.size() > 0) {
                        showPop();
                    }
                    break;
                case R.id.select_file:
                  if (isAllGranted) {
                        choosePhoto();
                        return;
                    }
                    ActivityCompat.requestPermissions(
                          UpdateMybankActivity.this,
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
                    prarams.clear();
                    prarams.put("usersid", userId);
                    prarams.put("usertoken", userToken);
                    cardname = etBankNumber.getText().toString().trim();
                    realname = etRealName.getText().toString().trim();
                    bankname = etFech.getText().toString().trim();
                    if (isValid()) {
                        prarams.put("cardname", cardname);
                        prarams.put("realname", realname);
                        prarams.put("bankname", bankname);
                        prarams.put("id",id);
                        url = URL.getInstance().deposit_info + "?act=upinfo";
                        new UGoldDepositeAsyncTask().execute();
                    }

                    break;
            }
        }
    }
    public boolean isValid() {
if(etFech.getText().toString().contains("支付宝")||etFech.getText().toString().contains("微信")){
    if(filePath.equals("")){
        Toast.makeText(UpdateMybankActivity.this, "请选择文件!", Toast.LENGTH_SHORT).show();
        return false;
    }
    Log.i("tag","tag");
}else{
    if (cardname.equals("")) {
        Toast.makeText(UpdateMybankActivity.this, "请输入收款账号!", Toast.LENGTH_SHORT).show();
        return false;
    }

}


        if (realname.equals("")) {
            Toast.makeText(UpdateMybankActivity.this, "请输入真实姓名!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bankname.equals("")) {
            Toast.makeText(UpdateMybankActivity.this, "请选择收款方式!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(UpdateMybankActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateMybankActivity.this);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" +getPackageName()));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    if (data != null) {

                        Uri originalUri = data.getData(); // 获得图片的uri
                        if (originalUri != null) {
                            filePath = GetImagePath.getPath(UpdateMybankActivity.this, originalUri);
                        }
                        //新建一个File，传入文件夹目录
                        file= new File(Bank_ICON_DIC,fileName);
                        //  file= new File(Qrcode_ICON_DIC,);
                        if (!file.exists()) {
                            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
                            Log.e("filePath", "filePath : " + file);
                            boolean mkdirs = file.mkdirs();
                        }
                        //获取请求设置的图片宽高
                        int reqWidth = ivCardCode.getWidth();
                        int reqHeight = ivCardCode.getHeight();
                        // 取bitmap的时候就压缩照片，同时解决了照片过大(>2048*2048px)的硬件加速问题(渲染不了)
                        // 和图片过大(20M),内存溢出问题
                        Bitmap bitmap = decodeSampledBitmapFromFile(filePath,
                                reqWidth, reqHeight);
                        int rotation = CommonUtil.getImageRotationByPath(
                                UpdateMybankActivity.this, filePath);
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
                        saveMyBitmap(file,bitmap);
                        ivCardCode.setImageBitmap(bitmap);

                      /*  Log.e("tag", "file" + file.getName());

                        Glide.with(mContext)
                                .load(file)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                                .skipMemoryCache(true)//跳过内存缓存
                                .into(iv_card_code);*/
                        Log.e("tag", "filePath" + filePath);
                        prarams.put("file", filePath);

                    }
                    break;
                }
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
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
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
            pop = new MyFechPop(UpdateMybankActivity.this, fechlist, etFech);
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
        pop.showAtLocation(llFmBank, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //如果窗口存在，则更新
        pop.update();
    }


    private class UGoldDepositeAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (filePath.equals("")) {
                result = AgentApi.dopost3(url, prarams);
            } else {
                result = AgentApi.dopost4(url, prarams,"file" ,file);
            }
            Log.e("修改的数据返回", "" + result);
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
                        Toast.makeText(UpdateMybankActivity.this, "恭喜您，修改成功！", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                       setResult(Result_code,intent);
                    } else if (goldDepositDb.getStatus() == 99) {
                        /*抗攻击*/
                        Unlogin.doAtk(prarams, s, new GoldDepositeAsyncTask());
                        //  new MyTrusteeAsyncTask().execute();
                    } else if (goldDepositDb.getStatus() == -97 || goldDepositDb.getStatus() == -99) {
                        Unlogin.doLogin(UpdateMybankActivity.this);
                    } else {
                        Entry entry = gson.fromJson(s, Entry.class);
                        Toast.makeText(UpdateMybankActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                    Toast.makeText(UpdateMybankActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(UpdateMybankActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
