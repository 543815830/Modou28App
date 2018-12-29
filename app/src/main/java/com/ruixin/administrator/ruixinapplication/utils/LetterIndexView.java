package com.ruixin.administrator.ruixinapplication.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ruixin.administrator.ruixinapplication.R;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by wangsong on 2016/4/24.
 */
public class LetterIndexView extends View {
    //当前手指滑动到的位置
    private int choosedPosition = -1;
    //画文字的画笔
    private Paint paint;
    //右边的所有文字
    private String[] letters = new String[]{"最爱","热门","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    //页面正中央的TextView，用来显示手指当前滑动到的位置的文本
    private TextView textViewDialog;
    private Vibrator vibrator;//添加声音
    //接口变量，该接口主要用来实现当手指在右边的滑动控件上滑动时ListView能够跟着滚动
    private UpdateListView updateListView;

    public LetterIndexView(Context context) {
        this(context, null);
    }

    public LetterIndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(48);
        Log.e("tag",""+letters[0].charAt(0));
    }
    public void setTextViewDialog(TextView textViewDialog) {
        this.textViewDialog = textViewDialog;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //把字母画在控件上，【选中字母】用红色画笔，否则蓝色
        int perTextHeight = getHeight() / letters.length;
        for (int i = 0; i < letters.length; i++) {
            Log.e("choosedPosition","choosedPosition");
            if (i == choosedPosition) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.parseColor("#339ef9"));
            }
            canvas.drawText(letters[i], (getWidth() - paint.measureText(letters[i])) / 2, (i + 1) * perTextHeight, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("onTouchEvent","onTouchEvent");
        int perTextHeight = getHeight() / letters.length;
        float y = event.getY();

        int currentPosition = (int) (y / perTextHeight);

        if (currentPosition > -1 && currentPosition < letters.length) {
            String letter = letters[currentPosition];

            switch (event.getAction()) {

                case MotionEvent.ACTION_UP:

                    setBackgroundColor(Color.TRANSPARENT);

                    if (textViewDialog != null) {
                        textViewDialog.setVisibility(View.GONE);
                    }
                    break;

                default:
                    setBackgroundColor(Color.parseColor("#cccccc"));
                    if (currentPosition > -1 && currentPosition < letters.length) {
                        if (textViewDialog != null) {
                            textViewDialog.setVisibility(View.VISIBLE);
                            textViewDialog.setText(letter);
                            textViewDialog.setX(textViewDialog.getLeft());
                            textViewDialog.setY(y-perTextHeight);
                            Vibrator vibrator = (Vibrator)getContext().getSystemService(VIBRATOR_SERVICE);
                            long VIBRATE_DURATION = 1L;
                            vibrator.vibrate(VIBRATE_DURATION);

                        }
                        if (updateListView != null) {
                            updateListView.updateListView(letter);
                        }
                       // choosedPosition = currentPosition;
                    }
                    break;
            }

            invalidate();
        }
        return true;
    }

    public void setUpdateListView(UpdateListView updateListView) {
        this.updateListView = updateListView;
    }

    public interface UpdateListView {
        void updateListView(String currentChar);
    }

    public void updateLetterIndexView(int currentChar) {
        Log.e("updateLetterIndexView","updateLetterIndexView");
//根据索引号改变//根据手指位置设置高亮字母并重绘
        for (int i = 0; i < letters.length; i++) {
            if(currentChar==-2){
                choosedPosition=0;
                invalidate();
                break;
            }else if(currentChar==-1){
                choosedPosition=1;
                invalidate();
                break;
            }else if (currentChar == letters[i].charAt(0)) {
                choosedPosition = i;
                Log.e("currentChar",""+currentChar);
                Log.e("choosedPosition",""+choosedPosition);
                invalidate();
               // break;
            }
        }

    }
}
