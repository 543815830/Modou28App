package com.ruixin.administrator.ruixinapplication.exchangemall.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruixin.administrator.ruixinapplication.R;
import com.ruixin.administrator.ruixinapplication.RuiXinApplication;
import com.ruixin.administrator.ruixinapplication.domain.Entry;
import com.ruixin.administrator.ruixinapplication.exchangemall.domain.LuckyDb;
import com.ruixin.administrator.ruixinapplication.myprogessbar.LoadingAndRetryManager;
import com.ruixin.administrator.ruixinapplication.usercenter.activiy.RedbagActivity;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.ATK;
import com.ruixin.administrator.ruixinapplication.usercenter.databean.MessageEvent;
import com.ruixin.administrator.ruixinapplication.utils.AgentApi;
import com.ruixin.administrator.ruixinapplication.utils.FormatUtils;
import com.ruixin.administrator.ruixinapplication.utils.SelfDialog;
import com.ruixin.administrator.ruixinapplication.utils.SelfDialog2;
import com.ruixin.administrator.ruixinapplication.utils.URL;
import com.ruixin.administrator.ruixinapplication.utils.Unlogin;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by aquarius on 17-4-11.
 */
public class LotteryView extends View {

    private int mScreenWidth;   // 屏幕宽度
    private int mScreenHeight;  // 屏幕高度

    private int mSelfTotalWidth;    // 自身最大的宽度

    private static float DEFAULT_SIZE_FACTOR = 0.82f;   // 自身占用屏幕宽度的比例

    private int mOuterCircleWidth;  // 最外边圆环
    private Paint mOuterCirclePaint;
    private int mOuterCircleBackgroundColor;

    private Paint mInnerPaint;
    private int mInnerCircleBackgroundColor;

    private Paint mSmallCirclePaint;
    private int mSmallCircleBlueColor;
    private int mSmallCircleYellowColor;
    private int mInnerCardTextColor;

    private int mCenterCardBackgroundColor;
    private int mInnerCardDefaultColor;

    private int mSmallCircleRadius;  // 小圆圈半径
    private int mInnerCardWidth;    // 卡片宽度
    private int mInnerCardSpace;    // 卡片间隔


    private boolean mHadInitial = false;
    private ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> mCardPositionInfoList;
    private Context mContext;
    private AlertDialog mAlertDialog;

    private int[] mPicResId;
    private String[] mInfoResArray;
    private Rect mBounds = new Rect();
    private float mSmallInfoTextSize;
    private float mBigInfoTextSize;
    private float mCenterInfoTextSize;

    private boolean mNeedRandomTimes = false;
    private int mInvalidateCircleCount;
    private int mInvalidateInnerCardCount;
    private int mLotteryInvalidateTimes;
    private boolean mStartAnimation = false; // not real animation
    private boolean mLastEndSelected = false;
    private SelfDialog2 selfDialog;
    String userId, userToken;
    Context context;
    LuckyDb luckyDb;
    List<LuckyDb.ResultBean.SumBean> list = new ArrayList<>();
    List<String> infolist = new ArrayList<>();
    LoadingAndRetryManager mLoadingAndRetryManager;
    private HashMap<String, String> prarams = new HashMap<String, String>();
    String info = "";
    String minfo;
    int i;

    public LotteryView(Context context) {
        this(context, null);
    }

    public LotteryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LotteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Log.e("tag", "init()");
        mContext = context;
        acquireCustomAttValues(context, attrs);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;//屏幕宽度
        mScreenHeight = dm.heightPixels;//屏幕高度
        mSelfTotalWidth = mScreenWidth < mScreenHeight ?
                (int) (mScreenWidth * DEFAULT_SIZE_FACTOR) : (int) (mScreenHeight * DEFAULT_SIZE_FACTOR);

        mSmallInfoTextSize = context.getResources().getDimension(R.dimen.lotteryview_inner_card_text_size);//最小字体的size
        mBigInfoTextSize = context.getResources().getDimension(R.dimen.lotteryview_inner_card_big_text_size);//最大字体的size
        mCenterInfoTextSize = context.getResources().getDimension(R.dimen.lotteryview_inner_card_center_text_size);//居中字体的size
        mOuterCircleWidth = (int) context.getResources().getDimension(R.dimen.lotteryview_outer_circle_width);//最外围的圆环宽度
        mInnerCardSpace = (int) context.getResources().getDimension(R.dimen.lotteryview_inner_card_blank);//卡片之间的间距
        mInnerCardWidth = (mSelfTotalWidth - getPaddingLeft() - getPaddingRight() - mOuterCircleWidth * 2 - mInnerCardSpace * 5) / 4;//卡片的宽度
        mSmallCircleRadius = (int) context.getResources().getDimension(R.dimen.lotteryview_outer_small_circle_radius);//外围圆环里面的小圆半径

        mInnerCardTextColor = context.getResources().getColor(R.color.inner_card_text_color);//卡片字体颜色
        // mInnerCardBackgroundColor=context.getResources().getColor(R.color.inner_card_bg_color);//卡片的颜色
        mCenterCardBackgroundColor = context.getResources().getColor(R.color.center_card_bg_color);//最中间卡片的颜色

        mOuterCircleBackgroundColor = context.getResources().getColor(R.color.outer_circle_bg_color);//最外围圆环的背景色
        mOuterCirclePaint = new Paint();
        mOuterCirclePaint.setColor(mOuterCircleBackgroundColor);
        mOuterCirclePaint.setAntiAlias(true);//抗锯齿功能
        mOuterCirclePaint.setStrokeWidth(mOuterCircleWidth);//画笔宽度
        mOuterCirclePaint.setStyle(Paint.Style.FILL);

        mSmallCircleBlueColor = mSmallCircleBlueColor != 0 ? mSmallCircleBlueColor : context.getResources().getColor(R.color.small_circle_color_blue);//外围小圆的绿色
        mSmallCircleYellowColor = mSmallCircleYellowColor != 0 ? mSmallCircleYellowColor : context.getResources().getColor(R.color.small_circle_color_yellow);//外围小圆的黄色
        mSmallCirclePaint = new Paint();
        mSmallCirclePaint.setColor(mSmallCircleBlueColor);
        mSmallCirclePaint.setAntiAlias(true);
        mOuterCirclePaint.setStyle(Paint.Style.FILL);


        mInnerCircleBackgroundColor = context.getResources().getColor(R.color.inner_circle_bg_color);//内围的颜色
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerCircleBackgroundColor);
        mInnerPaint.setStyle(Paint.Style.FILL);

        mCardPositionInfoList = new ArrayList<>();
        initResId(context);
    }

    private void acquireCustomAttValues(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LotteryView);
        mSmallCircleBlueColor = ta.getColor(R.styleable.LotteryView_outer_small_circle_color_default, 0);
        mSmallCircleYellowColor = ta.getColor(R.styleable.LotteryView_outer_small_circle_color_active, 0);
        mLotteryInvalidateTimes = ta.getInt(R.styleable.LotteryView_lottery_invalidate_times, 0);
        DEFAULT_SIZE_FACTOR = ta.getFloat(R.styleable.LotteryView_self_width_size_factor, DEFAULT_SIZE_FACTOR);
        mInnerCardDefaultColor = ta.getColor(R.styleable.LotteryView_inner_round_card_color_default, Color.parseColor("#5bb4ff"));//内部小卡片的颜色
        ta.recycle();
    }

    private void initResId(Context mContext) {
        mPicResId = new int[]{R.mipmap.choujianganniu};
        context = mContext;
//        mLoadingAndRetryManager = LoadingAndRetryManager.generate(context.getSystemService(Context.WINDOW_SERVICE), null);
        //   mInfoResArray = mContext.getResources().getStringArray(R.array.jifeng_array_info);
        userId = mContext.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_tv_id","");
        userToken =mContext.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE).getString("user_token","");;
        prarams.put("usersid", userId);
        prarams.put("usertoken", userToken);
        new getResourcesAsyncTask().execute();
    }

    private class getResourcesAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //   mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().luckyRound_URL, prarams);
            Log.e("result", "getResourcesAsyncTaskresult=" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() > 0) {
                int index = s.indexOf("{");
                if (index == 0) {
                    //  mLoadingAndRetryManager.showContent();
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        //Toast.makeText(context, "========1", Toast.LENGTH_SHORT).show();
                        luckyDb = gson.fromJson(s, LuckyDb.class);
                        list = luckyDb.getResult().getSum();
                        if (list.size() < 13) {
                            for (int i = 0; i < 13; i++) {
                                infolist.add(list.get(i % list.size()).getPrize());
                                //mInfoResArray[i] = list.get(i).getPrize();
                            }
                        }

                        infolist.add(12, "谢谢参与");
                        Log.e("list", "list=" + infolist);
                        mInfoResArray = new String[infolist.size()];
                        infolist.toArray(mInfoResArray);

                    }else if (entry.getStatus() == 99) {
/*抗攻击*/
                        Unlogin.doAtk(prarams,s,   new   getResourcesAsyncTask());
                    }else if(entry.getStatus() ==-99||entry.getStatus() ==-97){
                        Unlogin.doLogin(context);
                    } else {
                        Toast.makeText(context, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(context, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                // mLoadingAndRetryManager.showContent();
               Toast.makeText(context, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class getRoundsAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //   mLoadingAndRetryManager.showLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = AgentApi.dopost3(URL.getInstance().luckyRoundact_URL, prarams);
            Log.e("tag","getRoundsAsyncTask");
            Log.e("result", "getRoundsAsyncTaskresult=" + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() > 0) {
                int index = s.indexOf("{");
                if (index == 0) {
                    //  mLoadingAndRetryManager.showContent();
                    Gson gson = new Gson();
                    Entry entry = gson.fromJson(s, Entry.class);
                    if (entry.getStatus() == 1) {
                        //Toast.makeText(context, "========1", Toast.LENGTH_SHORT).show();
                        luckyDb = gson.fromJson(s, LuckyDb.class);
                        /*list = luckyDb.getResult().getSum();

                        for (int i = 0; i < 13; i++) {
                            infolist.add(list.get(i % list.size()).getPrize());
                            //mInfoResArray[i] = list.get(i).getPrize();
                        }
                        infolist.add(12, "谢谢参与");
                        Log.e("list", "list=" + infolist);
                        mInfoResArray = new String[infolist.size()];
                        infolist.toArray(mInfoResArray);*/
                        info = luckyDb.getResult().getPrize();

                        if (mNeedRandomTimes || mLotteryInvalidateTimes == 0) {
                            do {
                                mLotteryInvalidateTimes = (new Random().nextInt(12) + 1) * 9 + new Random().nextInt(12);//生成一个随机值
                                i = mLotteryInvalidateTimes % 12;
                                Log.e("tag", "i------" + i);
                                if (i == 4) {
                                    minfo = mInfoResArray[6];
                                } else if (i == 5) {
                                    minfo = mInfoResArray[8];
                                } else if (i == 6) {
                                    minfo = mInfoResArray[12];
                                } else if (i == 7) {
                                    minfo = mInfoResArray[11];
                                } else if (i == 8) {
                                    minfo = mInfoResArray[10];
                                } else if (i == 10) {
                                    minfo = mInfoResArray[7];
                                } else if (i == 11) {
                                    minfo = mInfoResArray[4];
                                } else {
                                    minfo = mInfoResArray[i];
                                }
                            } while (!minfo.equals(info));
                            mNeedRandomTimes = true;
                            mStartAnimation = true;//动画开始
                            invalidate();//刷新界面
                        }
                       // showReminderDialog(mContext);


                    }else if (entry.getStatus() == 99) {
/*抗攻击*/
                        Unlogin.doAtk(prarams,s,   new   getRoundsAsyncTask());
                    }else if(entry.getStatus() ==-99||entry.getStatus() ==-97){
                        Unlogin.doLogin(context);
                    } else {
                        Toast.makeText(context, entry.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else if (index != -1) {//如果是错误的json数据则进行截取解析并上传到友盟
                   Toast.makeText(context, "加载的错误数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                // mLoadingAndRetryManager.showContent();
               Toast.makeText(context, "加载数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mSelfTotalWidth, mSelfTotalWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        drawOuterRoundCircle(canvas);
        drawOuterDecorateSmallCircle(canvas);
        drawInnerBackground(canvas);
        drawInnerCards(canvas);
        loopSmallCircleAnimation();
        loopInnerRoundCardAnimation();
        //  drawInnerBackground(canvas);
    }

    /**
     * 外层带圆角矩形圆环
     */
    @SuppressLint("NewApi")
    private void drawOuterRoundCircle(Canvas canvas) {
        canvas.save();
        /*Log.e("tag",""+mOuterCircleWidth+getPaddingLeft()+ getPaddingTop()+mSelfTotalWidth+getPaddingRight()+getPaddingBottom());
        canvas.clipRect(
                mOuterCircleWidth + getPaddingLeft(),
                mOuterCircleWidth + getPaddingTop(),
                mSelfTotalWidth - mOuterCircleWidth - getPaddingRight(),
                mSelfTotalWidth - mOuterCircleWidth - getPaddingBottom(),
                Region.Op.DIFFERENCE);*/

        canvas.drawRoundRect(//圆角矩形
                getPaddingLeft(),
                getPaddingTop(),
                mSelfTotalWidth - getPaddingRight(),
                mSelfTotalWidth - getPaddingBottom(),
                18, 18, mOuterCirclePaint);
        canvas.restore();
    }

    //小圆球的位置
    private void drawOuterDecorateSmallCircle(Canvas canvas) {
        int result = mInvalidateCircleCount % 2;

        // top
        int x = 0, y = 0;
        int sideSize = mSelfTotalWidth + getPaddingLeft() + mOuterCircleWidth + getPaddingRight() + mSmallCircleRadius * 2; // 除去最外边圆环后的边长
        for (int i = 0; i < 5; i++) {
            mSmallCirclePaint.setColor(i % 2 == result ? mSmallCircleYellowColor : mSmallCircleBlueColor);
            x = mOuterCircleWidth / 2 + (sideSize - mSmallCircleRadius * 2 * 5) / 5 * i + mSmallCircleRadius * 2 * i + getPaddingLeft();
            y = (mOuterCircleWidth - mSmallCircleRadius * 2) / 2 + mSmallCircleRadius + getPaddingTop();
            canvas.drawCircle(x, y, mSmallCircleRadius, mSmallCirclePaint);
        }

        // bottom
        for (int i = 0; i < 5; i++) {
            mSmallCirclePaint.setColor(i % 2 == result ? mSmallCircleYellowColor : mSmallCircleBlueColor);
            x = mOuterCircleWidth / 2 + (sideSize - mSmallCircleRadius * 2 * 5) / 5 * i + mSmallCircleRadius * 2 * i + getPaddingLeft();
            y = mSelfTotalWidth - mOuterCircleWidth + (mOuterCircleWidth - mSmallCircleRadius * 2) / 2 + mSmallCircleRadius - getPaddingBottom();
            canvas.drawCircle(x, y, mSmallCircleRadius, mSmallCirclePaint);
        }

        // left
        for (int i = 0; i < 3; i++) {
            mSmallCirclePaint.setColor(i % 2 == (result == 0 ? 1 : 0) ? mSmallCircleYellowColor : mSmallCircleBlueColor);
            x = mOuterCircleWidth / 2 + getPaddingLeft();
            y = mOuterCircleWidth + mInnerCardWidth * (i + 1) + mSmallCircleRadius * 2 * (i + 1);
            canvas.drawCircle(x, y, mSmallCircleRadius, mSmallCirclePaint);
        }

        // right
        for (int i = 0; i < 3; i++) {
            mSmallCirclePaint.setColor(i % 2 == result ? mSmallCircleYellowColor : mSmallCircleBlueColor);
            x = mSelfTotalWidth - mOuterCircleWidth / 2 - getPaddingRight();
            y = mOuterCircleWidth + mInnerCardWidth * (i + 1) + mSmallCircleRadius * 2 * (i + 1);
            canvas.drawCircle(x, y, mSmallCircleRadius, mSmallCirclePaint);
        }
    }

    //内围的背景色
    @SuppressLint("NewApi")
    private void drawInnerBackground(Canvas canvas) {
        mInnerPaint.setColor(mInnerCircleBackgroundColor);

        canvas.drawRoundRect(mOuterCircleWidth, mOuterCircleWidth + 6,
                mSelfTotalWidth - mOuterCircleWidth,
                mSelfTotalWidth - mOuterCircleWidth - 6, 18, 18, mInnerPaint);
    }

    //绘制卡片的位置
    private void drawInnerCards(Canvas canvas) {
        int left = 0, top = 0, right = 0, bottom = 0;
        int spaceNum = 0;

        for (int i = 0; i < 13; i++) {
            spaceNum = i % 4 + 1;
            if (i < 5) {
                left = mOuterCircleWidth + mInnerCardWidth * (i % 4) + mInnerCardSpace * spaceNum + getPaddingLeft();
                top = mOuterCircleWidth + mInnerCardWidth * (i / 4) + mInnerCardSpace * (i / 4 + 1) + getPaddingTop();
                right = left + mInnerCardWidth;
                bottom = top + mInnerCardWidth;
            } else if (i == 5) {
                left = mOuterCircleWidth + mInnerCardWidth * (i % 4) + mInnerCardSpace * spaceNum + getPaddingLeft();
                top = mOuterCircleWidth + mInnerCardWidth * (i / 4) + mInnerCardSpace * (i / 4 + 1) + getPaddingTop();
                right = left + mInnerCardWidth * 2 + mInnerCardSpace;
                bottom = top + mInnerCardWidth * 2 + mInnerCardSpace;
            } else if (i == 6 || i == 7) {
                spaceNum = (i + 1) % 4 + 1;
                left = mOuterCircleWidth + mInnerCardWidth * ((i + 1) % 4) + mInnerCardSpace * spaceNum + getPaddingLeft();
                top = mOuterCircleWidth + mInnerCardWidth * ((i + 1) / 4) + mInnerCardSpace * ((i + 1) / 4 + 1) + getPaddingTop();
                right = left + mInnerCardWidth;
                bottom = top + mInnerCardWidth;
            } else if (i >= 8) {
                spaceNum = (i + 3) % 4 + 1;
                left = mOuterCircleWidth + mInnerCardWidth * ((i + 3) % 4) + mInnerCardSpace * spaceNum + getPaddingLeft();
                top = mOuterCircleWidth + mInnerCardWidth * ((i + 3) / 4) + mInnerCardSpace * ((i + 3) / 4 + 1) + getPaddingTop();
                right = left + mInnerCardWidth;
                bottom = top + mInnerCardWidth;
            }
            if (!mHadInitial) {
                mCardPositionInfoList.add(new Pair(new Pair(left, right), new Pair(top, bottom)));
            }
            drawInnerRoundCard(canvas, left, top, right, bottom, i);
        }
        mHadInitial = true;
    }

    @SuppressLint("NewApi")
    private void drawInnerRoundCard(Canvas canvas, int left, int top, int right, int bottom, int index) {

        boolean need = switchCardColorIfNeed(index);

        mInnerPaint.setColor(mInnerCardDefaultColor);

        if (mStartAnimation && need) {
            mInnerPaint.setColor(mOuterCircleBackgroundColor);
        }

        // 绘制内部小卡片
        if (index == 5) {
            mInnerPaint.setColor(Color.parseColor("#0038ff"));
        }

        canvas.drawRoundRect(left, top, right, bottom, 12, 12, mInnerPaint);
        int picHeight = 0;
        int picWidth = 0;
        if (index == 5) {
          /*  mInnerPaint.setColor(mCenterCardBackgroundColor);
            int space = mInnerCardWidth / 2;
            canvas.drawRoundRect(left + space, top + space, right - space, bottom - space, 12, 12, mInnerPaint);*/
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mPicResId[0]);
            picHeight = bitmap.getHeight();
            picWidth=bitmap.getWidth();
            //int picLeft = left - getPaddingLeft() + 3;
            int picLeft = left-getPaddingLeft()+(mInnerCardWidth*2+mInnerCardSpace*2-picWidth+getPaddingRight())/2 ;
            //int picTop = top - getPaddingTop() / 2;
            int picTop = top-getPaddingTop()+ (mInnerCardWidth*2+mInnerCardSpace*2-picHeight+getPaddingBottom())/2;
            canvas.drawBitmap(BitmapFactory.decodeResource(mContext.getResources(), mPicResId[0]), picLeft, picTop, mInnerPaint);
        }
       /* // 绘制卡片中的图片
        int picHeight = 0;
        if (mPicResId != null && mPicResId[index] != 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),mPicResId[index]);
            picHeight = bitmap.getHeight();
            int picLeft = left + (mInnerCardWidth - bitmap.getWidth()) / 2;
            int picTop  = top + mInnerCardWidth / 12;
            canvas.drawBitmap(BitmapFactory.decodeResource(mContext.getResources(),mPicResId[index]), picLeft, picTop, mInnerPaint);
        }*/
        // 绘制卡片说明文字
        if (mInfoResArray != null && !TextUtils.isEmpty(mInfoResArray[index])) {
          /*  if(index == 5) { // center text//立即抽奖的的字体颜色大小样式
                mInnerPaint.setColor(index == 5 ? Color.parseColor("#ffffff") : mInnerCardTextColor);
                mInnerPaint.setTextSize(mBigInfoTextSize);
                mInnerPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                int textX = left + (mInnerCardWidth *2- getTextWidth(mInfoResArray[index].substring(0, 2), mInnerPaint))/2;
                int textY_1 = top + (mInnerCardWidth/4 + getTextHeight(mInfoResArray[index].substring(0, 2), mInnerPaint)) - 8;
                int textY_2 = top + (mInnerCardWidth/4 + getTextHeight(mInfoResArray[index].substring(0, 2), mInnerPaint)*2 + mInnerCardWidth/10) -8;
                canvas.drawText(mInfoResArray[index], 0, 2, textX, textY_1, mInnerPaint);
                canvas.drawText(mInfoResArray[index], 2, 4, textX, textY_2, mInnerPaint);
                return;
            }*/
            if (index == 12) { // center text//立即抽奖的的字体颜色大小样式
                mInnerPaint.setColor(index == 12 ? Color.parseColor("#ffffff") : mInnerCardTextColor);
                mInnerPaint.setTextSize(mCenterInfoTextSize);
                // mInnerPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                int textX = left + (mInnerCardWidth - getTextWidth(mInfoResArray[index].substring(0, 2), mInnerPaint)) / 2;
                int textY_1 = top + (mInnerCardWidth / 4 + getTextHeight(mInfoResArray[index].substring(0, 2), mInnerPaint)) - 8;
                int textY_2 = top + (mInnerCardWidth / 4 + getTextHeight(mInfoResArray[index].substring(0, 2), mInnerPaint) * 2 + mInnerCardWidth / 10) - 8;
                canvas.drawText(mInfoResArray[index], 0, 2, textX, textY_1, mInnerPaint);
                canvas.drawText(mInfoResArray[index], 2, 4, textX, textY_2, mInnerPaint);
                return;
            } else if (index != 5) {
                mInnerPaint.setColor(mInnerCardTextColor);
                mSmallInfoTextSize = (mInnerCardWidth + getPaddingLeft() + getPaddingRight()) / mInfoResArray[index].length();
                mInnerPaint.setTextSize(mSmallInfoTextSize);
                //  mInnerPaint.setTextAlign(Paint.Align.CENTER);
                int textX = left + (mInnerCardWidth - getTextWidth(mInfoResArray[index], mInnerPaint)) / 2;
                // int textY = (int) (top + mInnerCardWidth / 2 + mInnerCardSpace / 2+getTextHeight(mInfoResArray[index],mInnerPaint)/2);
                //  int textY = (int) (top+ mInnerCardWidth / 2 +getTextHeight(mInfoResArray[index],mInnerPaint)/2);
                int textY = top + (mInnerCardWidth + getTextHeight(mInfoResArray[index], mInnerPaint)) / 2 - getPaddingTop() / 2;
                canvas.drawText(mInfoResArray[index], textX, textY, mInnerPaint);
            }

        }
    }


    private boolean switchCardColorIfNeed(int index) {
        int result = mInvalidateInnerCardCount % 12;
        if ((result == 0 && index == 0) || (result == 1 && index == 1) || (result == 2 && index == 2) || (result == 3 && index == 3) || (result == 9 && index == 9)) {
            return true;
        }
        if ((result == 4 && index == 6) || (result == 5 && index == 8) || (result == 6 && index == 12) || (result == 7 && index == 11) || (result == 8 && index == 10) || (result == 10 && index == 7) || (result == 11 && index == 4)) {
            return true;
        }
        return false;
    }

    private void loopSmallCircleAnimation() {
        // not real animation, just like it.
        if (mStartAnimation) {
            if (mInvalidateInnerCardCount % 12 == 0) {
                mInvalidateCircleCount++;
                //postInvalidate();
            }
        } else {
            mInvalidateCircleCount++;
            postInvalidateDelayed(800);
        }
    }

    private void loopInnerRoundCardAnimation() {//选中卡片动画
        if (!mStartAnimation || mLastEndSelected) return;

        if (mInvalidateInnerCardCount == mLotteryInvalidateTimes) {
            Log.e("tag1", "" + mInvalidateInnerCardCount + mLotteryInvalidateTimes);
            //mStartAnimation = false;
            mLastEndSelected = true;
            postInvalidate();//直接在线程中刷新界面
            postDelayed(new ResultTask(mLotteryInvalidateTimes), 300);
            return;
        }

        mInvalidateInnerCardCount++;
        postInvalidateDelayed(50);//控制界面从一个卡片跳下一个

    }

    private class ResultTask implements Runnable {
        int times;

        public ResultTask(int times) {
            this.times = times;
        }

        @Override
        public void run() {
            mInvalidateInnerCardCount = 0;
            mLastEndSelected = false;

            int i = times % 12;
            info = mInfoResArray[i];
            if (i == 4) info = mInfoResArray[6];
            if (i == 5) info = mInfoResArray[8];
            if (i == 6) info = mInfoResArray[12];
            if (i == 7) info = mInfoResArray[11];
            if (i == 8) info = mInfoResArray[10];
            if (i == 10) info = mInfoResArray[7];
            if (i == 11) info = mInfoResArray[4];
            showResultDialog(mContext, info);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.e("tag","onTouchEvent");
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_UP:
                int index = getTouchPositionInCardList(x, y);//获取卡片的点击下标
                if (index == 6) {
                    if(!mStartAnimation){
                        showReminderDialog(mContext);
                    }

                }
                break;
        }

        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("tag", "onDetachedFromWindow");
        mStartAnimation = false;
        mInvalidateCircleCount = 0;
        mInvalidateInnerCardCount = 0;
        mNeedRandomTimes = false;

    }

    //显示抽奖结果
    private void showResultDialog(Context context, String result) {
       /* final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.result_title)
                .setMessage(context.getString(R.string.result_message, result))
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
       builder.create().show();*/
        EventBus.getDefault().post(new MessageEvent("3"));
        selfDialog = new SelfDialog2(context);
        selfDialog.setMessage(result);
        selfDialog.show();
        selfDialog.setYesOnclickListener(new SelfDialog2.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Log.e("tag", "onYesClick");
                selfDialog.dismiss();
                mStartAnimation = false;
            }
        });

    }

    //确认抽奖对话框
    private void showReminderDialog(Context context) {
        Log.e("tag", "showReminderDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.choujiang)
                .setMessage(R.string.choujiang_desc)
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
                        new getRoundsAsyncTask().execute();

                    }
                });
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    //获取卡片点击位置的下标
    private int getTouchPositionInCardList(int x, int y) {
        if (mCardPositionInfoList != null) {
            int index = 1;
            for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> pair : mCardPositionInfoList) {
                if (x > pair.first.first && x < pair.first.second && y > pair.second.first && y < pair.second.second) {
                    return index;
                }
                index++;
                Log.e("tag", "" + index);
            }
        }
        return 0;
    }


    private int getTextWidth(String str, Paint paint) {
        paint.getTextBounds(str, 0, str.length(), mBounds);
        return mBounds.width();
    }

    private int getTextHeight(String text, Paint paint) {
        paint.getTextBounds(text, 0, text.length(), mBounds);
        return mBounds.height();
    }
}
