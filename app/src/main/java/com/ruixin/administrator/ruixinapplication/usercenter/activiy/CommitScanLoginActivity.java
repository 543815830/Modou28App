package com.ruixin.administrator.ruixinapplication.usercenter.activiy;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.gamecenter.databean.ScanInfoDb;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 扫描二维码后的activity
 */
public class CommitScanLoginActivity extends Activity {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_login_address)
    TextView tvLoginAddress;
    @BindView(R.id.scan_login)
    Button scanLogin;
    @BindView(R.id.tv_cancel_login)
    TextView tvCancelLogin;
    String userId, userToken, scanResult, os;
    String result;
    String city = "";
    private HashMap<String, String> qr_code_prarams = new HashMap<>();
    private HashMap<String, String> prarams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_scan_login);
        ButterKnife.bind(this);
        initStatus();
        tvTitle.setText("扫一扫登录");
        userId = getIntent().getStringExtra("userId");
        userToken = getIntent().getStringExtra("userToken");
        scanResult = getIntent().getStringExtra("randnumber");
        os = getIntent().getStringExtra("os");
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        prarams.put("randnumber", scanResult);
        new QrCodeLoginInfoAsyncTask().execute();

    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    @OnClick({R.id.back_arrow, R.id.scan_login, R.id.tv_cancel_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.scan_login:
                qr_code_prarams.put("usertoken", userToken);
                qr_code_prarams.put("usersid", userId);
                qr_code_prarams.put("randnumber", scanResult);
                new QrCodeLoginAsyncTask().execute();
                finish();
                break;
            case R.id.tv_cancel_login:
                finish();
                break;
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class QrCodeLoginInfoAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().PreQrCode_URL, prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("扫一扫登录消息返回", "消息返回结果result" + result);
            if (result != null) {
                int status = 0;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                    Gson gson = new Gson();
                    ScanInfoDb scaninfo = gson.fromJson(result, ScanInfoDb.class);
                    if (scaninfo.getLocation() != null) {
                        city = scaninfo.getLocation().getCity().toString();
                    }
                    tvLoginAddress.setText("您的账号于" + scaninfo.getDate() + "申请在" + os + "登录，登录地点" + city + "市");
                }
                if (result.equals("flase")) {
                    Toast.makeText(CommitScanLoginActivity.this, "网页登录失败！", Toast.LENGTH_SHORT).show();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(CommitScanLoginActivity.this);
                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(prarams, result, new QrCodeLoginInfoAsyncTask());
                }
            } else {
                Toast.makeText(CommitScanLoginActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class QrCodeLoginAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 请求数据
            result = AgentApi.dopost3(URL.getInstance().QrCode_URL, qr_code_prarams);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("回调消息返回", "消息返回结果result" + result);
            if (result != null) {
                int status = 0;
                String msg = null;
                try {
                    JSONObject re = new JSONObject(result);
                    status = re.optInt("status");
                    msg = re.optString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (result.equals("flase")) {
                    Toast.makeText(CommitScanLoginActivity.this, "网页登录失败！", Toast.LENGTH_SHORT).show();
                } else if (result.equals("true")) {
                    Toast.makeText(CommitScanLoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                } else if (status == -97 || status == -99) {
                    Unlogin.doLogin(CommitScanLoginActivity.this);
                } else if (status == 99) {
                        /*抗攻击*/
                    Unlogin.doAtk(qr_code_prarams, result, new QrCodeLoginAsyncTask());
                } else {
                    if(msg==null){
                      msg="出错了!";
                    }
                    Toast.makeText(CommitScanLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CommitScanLoginActivity.this, "系统正在维护，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
