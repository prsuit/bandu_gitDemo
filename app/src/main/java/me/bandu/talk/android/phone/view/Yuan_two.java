package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * 创建者：高楠
 * 时间：on 2015/12/4
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Yuan_two extends View{


    public Yuan_two(Context context) {
        super(context);
        initPaint();
    }

    public Yuan_two(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public Yuan_two(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    private Paint paint_red;
    private Paint paint_green;
    private Paint paint_gray;
    private Paint paint_wihte;
    private Paint paint_black;
    private Paint line_gray;
    private Paint line_red;
    private int screenwidth;
    private int screenheight;
    private float bpercent = 0.4f;
    private int with;
    private int height;
    private float r_inside = 160;
    public static final int NORMAL = 1;//正常作业
    public static final int FINISH = 2;//已完成
    public static final int OVER = 3;//已过期
    public static final int REVOKE = 4;//已撤销
    private int status = 4;

    public void setAttr(float b,int status){
        this.bpercent = b/100;
        this.status = status;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float angle = 0;
        RectF oval2 = new RectF(0, 0, with,height);// 设置个新的长方形，扫描测量
//        canvas.drawArc(oval2,-90,360*(1-bpercent) , true, paint_gray);
//        angle = 360*(1-bpercent);
//        canvas.drawArc(oval2,angle-90,360-angle, true, paint_green);
          canvas.drawArc(oval2,-90,85 , true, line_red);
          canvas.drawArc(oval2,0,145 , true, line_gray);
          canvas.drawArc(oval2,150,115 , true, paint_green);
//        if (bpercent>=1f){
//            if (isUpload){
//                canvas.drawArc(oval2,angle-90,360-angle, true, paint_green);
//            }else {
//                canvas.drawArc(oval2,angle-90,360-angle, true, paint_red);
//            }
//        }else {
//            canvas.drawArc(oval2,angle-90,360-angle, true, paint_green);
//        }
        r_inside = (float) (with/3);
        canvas.drawCircle(with/2,height/2,r_inside ,paint_wihte);
        String percent = (int)(bpercent*100)+"%";
        int w = (int) (r_inside/1.6);
        if (status==NORMAL){
            paint_black.setTextSize(w);
            canvas.drawText(percent,with/2,height/2+w*3/8,paint_black);
            return;
        }
        if (status==FINISH){
            paint_green.setTextSize(w*2);
            canvas.drawText("√",with/2,height/2+w/2,paint_green);
            return;
        }
        if (status==OVER){
            paint_gray.setTextSize((float) (w*1.3));
            canvas.drawText("过",with/2,height/2+w/2,paint_gray);
            return;
        }
        if (status==REVOKE){
            paint_red.setTextSize((float) (w*1.3));
            canvas.drawText("撤",with/2,height/2+w/2,paint_red);
            return;
        }
    }


    public void initPaint(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        screenwidth = wm.getDefaultDisplay().getWidth();
        screenheight = wm.getDefaultDisplay().getHeight();
        paint_red = new Paint();
        paint_red.setColor(Color.rgb(234,108,109));
        paint_red.setAntiAlias(true);// 是否抗锯齿
        paint_red.setTextAlign(Paint.Align.CENTER);
        paint_gray = new Paint();
        paint_gray.setColor(Color.rgb(196,196,196));
        paint_gray.setAntiAlias(true);// 是否抗锯齿
        paint_gray.setTextAlign(Paint.Align.CENTER);
        paint_green = new Paint();
        paint_green.setColor(Color.rgb(158,208,77));
        paint_green.setAntiAlias(true);// 是否抗锯齿
        paint_green.setTextAlign(Paint.Align.CENTER);
        paint_wihte = new Paint();
        paint_wihte.setColor(Color.WHITE);
        paint_wihte.setAntiAlias(true);// 是否抗锯齿
        paint_wihte.setTextAlign(Paint.Align.CENTER);
        paint_black = new Paint();
        paint_black.setColor(Color.BLACK);
        paint_black.setAntiAlias(true);// 是否抗锯齿
        paint_black.setTextAlign(Paint.Align.CENTER);
        line_gray = new Paint();
        line_gray.setColor(Color.LTGRAY);
        line_gray.setAntiAlias(true);// 是否抗锯齿
        line_gray.setTextAlign(Paint.Align.CENTER);
        line_red = new Paint();
        line_red.setColor(Color.rgb(242, 191, 145));
        line_red.setAntiAlias(true);// 是否抗锯齿
        line_red.setTextAlign(Paint.Align.CENTER);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        with = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        with = screenwidth/9;
        height = with;
        setMeasuredDimension(with, height);
    }
}
