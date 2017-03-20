package me.bandu.talk.android.phone.view;

/**
 * author by Mckiera
 * time: 2015/12/18  17:19
 * description:
 * updateTime:
 * update description:
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import me.bandu.talk.android.phone.R;


public class LoudView extends View implements Runnable{
    private Context context;
    private boolean starting;
    private int sum;

    private float mWidth,mHeight;
    private Paint mPaint;
    Bitmap bitmap2,bitmap1,bitmap3;

    public LoudView(Context context) {
        this(context,null);
    }

    public LoudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoudView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        this.context = context;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.loud03);
        this.bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.loud02);
        this.bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.loud01);
    }

    public void startChange(){
        if (!starting) {
            this.starting = true;
            sum = 0;
            new Thread(this).start();
        }
    }

    public void stopChange(){
        this.starting = false;
    }

    public void onDestory(){
        bitmap1.recycle();
        bitmap1 = null;
        bitmap2.recycle();
        bitmap2 = null;
        bitmap3.recycle();
        bitmap3 = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (sum) {
            case 3:
                canvas.drawBitmap(bitmap3, 0, 0, mPaint);
            case 2:
                canvas.drawBitmap(bitmap2, 0, 0,mPaint);
            case 1:
                canvas.drawBitmap(bitmap1, 0, 0,mPaint);
            case 0:
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap1.getWidth(), bitmap1.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    public void run() {
        while (starting) {
            try {
                Thread.sleep(500);
                sum++;
                if (sum > 3) {
                    sum = 0;
                }
                postInvalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
