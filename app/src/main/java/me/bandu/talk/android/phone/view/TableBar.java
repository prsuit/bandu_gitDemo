package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：gaoye
 * 时间：2015/11/20  16:17
 * 类描述：页面切换滑动指示器
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TableBar extends View {
    private long changeTime = 100;
    private int noSelectColor = Color.WHITE;
    private int selectColor = Color.BLUE;
    private Paint mPaint;
    private float mWidth,mHeight;
    private int size = 1;
    private int current;
    private int state;
    private final int STATE_CHANGING = 1,STATE_NOCHANGE = 0;
    private float changeFloat;
    private int per;
    private Context context;

    public TableBar(Context context) {
        this(context, null);
    }

    public TableBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        selectColor = context.getResources().getColor(R.color.blue);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(selectColor);
        setBackgroundColor(noSelectColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w - getPaddingLeft() - getPaddingRight();
        mHeight = h - getPaddingTop() - getPaddingBottom();
    }

    public void setSize(int size){
        if (size > 0){
            this.size = size;
            invalidate();
        }
    }

    public void setCurrent(int current){
        if (current >= 0 && current < size && current != this.current){
            this.current = current;
            startChange();
        }
    }

    private void startChange() {
        if (state == STATE_NOCHANGE){
            state = STATE_CHANGING;
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    float f = 0;
                    if (current > per)
                        f = (current - per) * mWidth / size;
                    else
                        f = (current - per) * mWidth / size;
                    float cf = f / changeTime;

                    while (Math.abs(changeFloat) < Math.abs(f) ){
                        try {
                            changeFloat += cf;
                            postInvalidate();
                            sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    changeFloat = 0;
                    state = STATE_NOCHANGE;
                    per = current;
                    postInvalidate();
                }
            }.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (state == STATE_CHANGING){
            float left = per * mWidth / size + getPaddingRight() + changeFloat;
            float top = getPaddingTop();
            float right = (per + 1) * mWidth / size + getPaddingRight() + changeFloat;
            float buttom = mHeight - getPaddingBottom();
            canvas.drawRect(left,top,right,buttom,mPaint);
        }
        else{
            float left = current * mWidth / size + getPaddingRight();
            if (left != 0) left ++;
            float top = getPaddingTop();
            float right = (current + 1) * mWidth / size + getPaddingRight();
            float buttom = mHeight - getPaddingBottom();
            canvas.drawRect(left,top,right,buttom,mPaint);
        }

    }
}
