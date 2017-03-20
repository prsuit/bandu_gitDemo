package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import me.bandu.talk.android.phone.utils.ScreenUtil;

/**
 * 创建者：gaoye
 * 时间：2015/11/20  17:10
 * 类描述：右边有一竖的textview
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RightLineTextView extends TextView {
    private Context context;
    private Paint mPaint;
    private int lineColor = Color.rgb(238,238,238);
    private float length;
    private float lineWidth = 5;
    private float mWidth;
    private float mHeight;
    public RightLineTextView(Context context) {
        this(context, null);
    }

    public RightLineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        length = h / 4;
        mHeight = h;
        mWidth = w;
    }

    private void init() {
        lineWidth = ScreenUtil.dp2px(2, context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect((int)(mWidth - lineWidth),(int)length,(int)(mWidth),(int)(mHeight - length));
        canvas.drawRect(rect,mPaint);
        //canvas.drawLine(mWidth - lineWidth,length,mWidth - lineWidth,length * 3 / 4,mPaint);
    }
}
