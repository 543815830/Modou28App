package com.ruixin.administrator.ruixinapplication.exchangemall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.BaseActivity;
import com.ruixin.administrator.ruixinapplication.MainActivity;
import com.ruixin.administrator.ruixinapplication.MyScrollView;
import com.ruixin.administrator.ruixinapplication.OneFmAdapter;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.exchangemall.domain.ProductDb;
import com.ruixin.administrator.ruixinapplication.exchangemall.fragment.IntroduceProductFM;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.popwindow.PopMenu2;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.LoginActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.RedbagActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.CustomViewPager;
import com.ruixin.administrator.ruixinapplication.utils.DisplayUtil;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 商品详情界面
 */
public class ProductDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener, MyScrollView.OnScrollListener {

    @BindView(R.id.back_arrow)
    LinearLayout backArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.share)
    LinearLayout share;
    @BindView(R.id.select_more)
    LinearLayout selectMore;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_no_prize)
    TextView tvNoPrize;
    @BindView(R.id.tv_renqi)
    TextView tvRenqi;
    @BindView(R.id.tv_shu)
    TextView tvShu;
    @BindView(R.id.rb_de1)
    RadioButton rbDe1;
    @BindView(R.id.rb_de2)
    RadioButton rbDe2;
    @BindView(R.id.rb_de3)
    RadioButton rbDe3;
    @BindView(R.id.rg_detail)
    RadioGroup rgDetail;
    @BindView(R.id.product_vp)
    CustomViewPager product_vp;
    @BindView(R.id.conversion)
    Button conversion;
    @BindView(R.id.my_scroll)
    MyScrollView myScroll;
    @BindView(R.id.tv_prize_name)
    TextView tvPrizeName;
    @BindView(R.id.tv_prize_price)
    TextView tvPrizePrice;
    @BindView(R.id.tv_prize_id)
    TextView tvPrizeId;
    @BindView(R.id.tv_prize_renqi)
    TextView tvPrizeRenqi;
    @BindView(R.id.tv_convertnum)
    TextView tvConvertnum;
    @BindView(R.id.rl_product)
    RelativeLayout rlProduct;
    @BindView(R.id.tv_topView)
    LinearLayout tvTopView;
    @BindView(R.id.ll_tabView)
    LinearLayout llTabView;
    @BindView(R.id.ll_tabTopView)
    LinearLayout llTabTopView;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;
    int position;
    private List<String> imageArray;
    /**
     * 顶部固定的TabViewLayout
     */
    private LinearLayout mTopTabViewLayout;
    /**
     * 跟随ScrollView的TabviewLayout
     */
    private LinearLayout mTabViewLayout;

    /**
     * 要悬浮在顶部的View的子View
     */
    private LinearLayout mTopView;
    IntroduceProductFM inpfm;
    String userId;
    String userToken;
    String id;
    String path;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    LoadingAndRetryManager mLoadingAndRetryManager;
PopMenu2 popMenu2;
    Intent intent;
    String clip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        tvTitle.setFocusable(true);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if(id!=null){
            prarams.put("id", id);
        }
        clip = intent.getStringExtra("clip");
        if (clip != null) {
            Log.e("Alex", "clip:" + clip);
            prarams.put("id", clip);
        }
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String host = uri.getHost();
                String dataString = intent.getDataString();
                //  String id = uri.getQueryParameter("id");
               id = uri.getQuery();
                   if (id.contains("=")) {
                      int index=id.indexOf("=");
                     id=id.substring(index+1,id.length());
                       // hbid = hbid.replaceAll("/", "");
                    }
                //  String path1 = uri.getEncodedPath();
                //   String queryString = uri.getQuery();
                Log.e("Alex", "host:" + host);
                Log.e("Alex", "dataString:" + dataString);
                //  Log.e("Alex", "id:" + id);
                Log.e("Alex", "id:" + id);
                //  Log.e("Alex", "path1:" + path1);
                //  Log.e("Alex", "queryString:" + queryString);
                prarams.put("id", id);
            }
        }
        initView();
        initStatus();
        mTabViewLayout = (LinearLayout) findViewById(R.id.ll_tabView);
        mTopTabViewLayout = (LinearLayout) findViewById(R.id.ll_tabTopView);
        mTopView = (LinearLayout) findViewById(R.id.tv_topView);
        //滑动监听
        myScroll.setOnScrollListener(this);
        backArrow.setOnClickListener(new MyOnclickListenter());
        share.setOnClickListener(new MyOnclickListenter());
        selectMore.setOnClickListener(new MyOnclickListenter());
        conversion.setOnClickListener(new MyOnclickListenter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("tag","onDestroy");
    }

    private void initView() {
        userId = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
        userToken = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");;
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(rlProduct, null);
        if (userId == null || userToken == null||userId.equals("")||userToken.equals("")) {
            Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
            intent.putExtra("where", "3");
            startActivity(intent);
            finish();
        } else {
            prarams.put("usersid", userId);
            prarams.put("usertoken", userToken);
            new PrizeDetailAsyncTask().execute();
            initVp();
            setListener();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == LoginActivity.RESULT_CODE) {
            Log.e("tag","onActivityResult");
            userId = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            userToken = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");;
            if(userId.equals("")||userToken.equals("")||userId==null||userToken==null){
                finish();
            }else{
                prarams.put("usersid", userId);
                prarams.put("usertoken", userToken);
                new PrizeDetailAsyncTask().execute();
                initVp();
                setListener();
            }

        }
    }

    private void initBanner() {
        Log.e("tag", "initBanner()");

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imageArray);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        // banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }

    /* 标题栏与状态栏颜色一致用这种*/
    private void initStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.StatusFColor));
        }
    }

    private void setListener() {
        Log.e("tag", "setListener()");
        rgDetail.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中
        rgDetail.check(R.id.rb_de1);
    }

    @Override
    public void onScroll(int scrollY) {
        int mHeight = mTabViewLayout.getTop();
        Log.e("tag", "mHeight()" + mHeight);
        //判断滑动距离scrollY是否大于0，因为大于0的时候就是可以滑动了，此时mTabViewLayout.getTop()才能取到值。
        if (scrollY >= mHeight) {

            if (mTopView.getParent() != mTopTabViewLayout) {
                mTabViewLayout.removeView(mTopView);
                mTopTabViewLayout.addView(mTopView);
                tvTitle.setText("图文详情");
            }
        } else {
            if (mTopView.getParent() != mTabViewLayout) {
                mTopTabViewLayout.removeView(mTopView);
                mTabViewLayout.addView(mTopView);
                tvTitle.setText("奖品详情");
            }

        }


    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_de1://选择模式1
                    position = 0;
                    break;
                case R.id.rb_de2://选择模式2
                    position = 1;
                    break;
                case R.id.rb_de3://选择模式3
                    position = 2;
                    break;
            }
            product_vp.setCurrentItem(position);
        }
    }

    private void initVp() {
        for (int i = 0; i < 3; i++) {
            product_vp.setCurrentItem(i);
            inpfm = new IntroduceProductFM();
            Bundle bundle = new Bundle();
            userId = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
            userToken = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");;
            bundle.putString("poision", String.valueOf(i));
            bundle.putString("id", id);
            bundle.putString("userId", userId);
            bundle.putString("usertoken", userToken);
            inpfm.setArguments(bundle);
            newsList.add(inpfm);
        }
       /* newsList.add(new IntroduceProductFM());
        newsList.add(new IntroduceProductFM());
        newsList.add(new IntroduceProductFM());*/
        //设置viewpager适配器
        adapter = new OneFmAdapter(getSupportFragmentManager(), newsList);
        product_vp.setAdapter(adapter);
        //两个viewpager切换不重新加载
        product_vp.setOffscreenPageLimit(2);
        //设置默认
        product_vp.setCurrentItem(0);
        //设置viewpager监听事件
        product_vp.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            rgDetail.check(R.id.rb_de1);
        } else if (position == 1) {
            rgDetail.check(R.id.rb_de2);
        } else if (position == 2) {
            rgDetail.check(R.id.rb_de3);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, final ImageView imageView) {
            Log.e("tag", "displayImage()");
            //Glide 加载图片简单用法
            Glide.with(context)
                    .load((String) path)
                  //  .placeholder(R.drawable.banner) //占位图
                   // .error(_round)  //出错的占位图
                    .dontAnimate()
                    .into(imageView);
        }
    }

    public class MyOnclickListenter implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back_arrow:
                    finish();
                    break;
                case R.id.share:
                    showShare();
                    break;
                case R.id.select_more:
                    showpop();
                    break;
                case R.id.my_home:
                    popMenu2.dismiss();
                  //  removeALLActivity();
                    //启动主界面
                    intent=new Intent(ProductDetailActivity.this,MainActivity.class);
                    intent.putExtra("where","2");
                    startActivity(intent);
                    //关闭当前界面
                   // finish();
                    break;
                case R.id.my_notice:
                  //  removeALLActivity();
                    //启动主界面
                    intent=new Intent(ProductDetailActivity.this,MainActivity.class);
                    intent.putExtra("where","4");
                    startActivity(intent);
                    popMenu2.dismiss();
                    //关闭当前界面
                   // finish();
                    break;
                case R.id.my_usercenter:
                   // removeALLActivity();
                    //启动主界面
                    intent=new Intent(ProductDetailActivity.this,MainActivity.class);
                    intent.putExtra("where","3");
                    startActivity(intent);
                    popMenu2.dismiss();
                    //关闭当前界面
                  //  finish();
                    break;
                case R.id.conversion:
                    Log.e("tag","conversion");
                    intent = new Intent(ProductDetailActivity.this, ConversionActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userToken", userToken);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void showpop() {
        if (popMenu2 == null) {
            //自定义的单击事件
            MyOnclickListenter onClickLintener = new MyOnclickListenter();
            popMenu2 = new PopMenu2(ProductDetailActivity.this, onClickLintener, DisplayUtil.dp2px(ProductDetailActivity.this, 70), DisplayUtil.dp2px(ProductDetailActivity.this, 120));
            //监听窗口的焦点事件，点击窗口外面则取消显示
            popMenu2.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        popMenu2.dismiss();
                    }
                }
            });
        }
        //设置默认获取焦点
        popMenu2.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        popMenu2.showAsDropDown(selectMore, 0, 0);
        //如果窗口存在，则更新
        popMenu2.update();
    }

    private class PrizeDetailAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().PrizeDetail_URL, prarams);
            Log.e("result", "result=" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() > 0) {
                int index = s.indexOf("{");
                if (index == 0) {
                    mLoadingAndRetryManager.showContent();
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        Gson gson1 = new Gson();
                        ProductDb productDb = gson1.fromJson(s, ProductDb.class);
                        imageArray = new ArrayList<>();
                        path = RuiXinApplication.getInstance().getUrl() + productDb.getData().getImgsrc();
                        imageArray.add(path);
                        initBanner();
                        tvPrizeId.setText("" + productDb.getData().getPrizeid());
                        tvPrizeName.setText("" + productDb.getData().getPrizename());
                        String mTag = "兑换价：";
                        String builder = mTag + FormatUtils.formatString(String.valueOf(productDb.getData().getVipprice()));
                        // Log.e("builder",""+builder);
                        //设置新的颜色
                        SpannableString mBuilder = new SpannableString(builder);
                        mBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#454545")), 0, mTag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#339ef9")), mTag.length(), builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPrizePrice.setText(mBuilder);
                        tvConvertnum.setText("" + productDb.getData().getConvertnum());
                        tvPrizeRenqi.setText("" + productDb.getData().getPrizehits());
                        Log.e("getAmounts",""+productDb.getData().getAmouts());
                        if(productDb.getData().getShoptype().equals("0")||productDb.getData().getShoptype().equals("1")){
                            conversion.setClickable(true);
                        }else{
                            if(productDb.getData().getAmouts()==0||productDb.getData().getAmouts()<0){
                                conversion.setBackgroundColor(Color.parseColor("#b5b5b5"));
                                conversion.setClickable(false);
                                conversion.setText("库存不足");
                            }
                        }

                    } else if (entry.getStatus() == 99) {
/*抗攻击*/
                        Unlogin.doAtk(prarams,s,   new  PrizeDetailAsyncTask());
                    }else if(entry.getStatus() ==-99||entry.getStatus() ==-97) {
                        Unlogin.doLogin(ProductDetailActivity.this);
                    }else {
                        Toast.makeText(ProductDetailActivity.this, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(ProductDetailActivity.this, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(ProductDetailActivity.this, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showShare() {
        Log.e("tag","showShare");
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");
        // titleUrl QQ和QQ空间跳转链接
         oks.setTitleUrl("http://120.78.87.50/Prize/"+ id);
        // text是分享文本，所有平台都需要这个字段
        String text = "看广告，玩游戏，拿奖品，立即体验http://120.78.87.50/Prize/" + id;
        //Log.e("tag")
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath(path);//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        // oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        // oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
              // Toast.makeText(ProductDetailActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
               Toast.makeText(ProductDetailActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
               Toast.makeText(ProductDetailActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

