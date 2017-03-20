package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：gy
 * 时间：2015/10/28  10:43
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DownLoadButton extends Button implements View.OnClickListener{
    private Context context;
    private float mWidth,mHeight;
    private int state;
    public static final int STATE_STOPPED = 0,STATE_DOWNLOADING = 1,STATE_HASDOWNLOAD = 2,STATE_WAITTING = 3,STATE_FAIL = 4;
    private Paint mPaint,mTextPaint;
    private int percent;
    private OnDownloadClickListener listener;

    public DownLoadButton(Context context) {
        this(context, null);
    }

    public DownLoadButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownLoadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public void setDownloadListener(OnDownloadClickListener listener){
        this.listener = listener;
    }

    private void initView() {
        setBackgroundResource(R.color.transparent);
        setGravity(Gravity.CENTER);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        setOnClickListener(this);
        reSet();
    }

    private void drawText(Canvas canvas){
        mTextPaint.setTextSize(mHeight / 2);
        float y = canvas.getHeight() / 2 - (mTextPaint.descent() / 2 + mTextPaint.ascent() / 2);
        String mText = null;
        float textWidth = 0;
        switch (state){
            case STATE_STOPPED:
                mText = "离线下载";
                textWidth = mTextPaint.measureText(mText);
                mTextPaint.setShader(null);
                mTextPaint.setColor(context.getResources().getColor(R.color.white));
                canvas.drawText(mText,(mWidth - textWidth) / 2,y,mTextPaint);
                break;
            case STATE_FAIL:
                mText = "重新下载";
                textWidth = mTextPaint.measureText(mText);
                mTextPaint.setShader(null);
                mTextPaint.setColor(context.getResources().getColor(R.color.white));
                canvas.drawText(mText,(mWidth - textWidth) / 2,y,mTextPaint);
                break;
            case STATE_HASDOWNLOAD:
                mText = "已下载";
                textWidth = mTextPaint.measureText(mText);
                mTextPaint.setShader(null);
                mTextPaint.setColor(context.getResources().getColor(R.color.text_dark_gray));
                canvas.drawText(mText,(mWidth - textWidth) / 2,y,mTextPaint);
                break;
            case STATE_WAITTING:
                mText = "等待中...";
                textWidth = mTextPaint.measureText(mText);
                mTextPaint.setShader(null);
                mTextPaint.setColor(context.getResources().getColor(R.color.text_dark_gray));
                canvas.drawText(mText,(mWidth - textWidth) / 2,y,mTextPaint);
                break;
            case STATE_DOWNLOADING:
                mText = "删除下载";
                textWidth = mTextPaint.measureText(mText);
                //进度条压过距离
                float coverlength = mWidth * percent / 100;
                //开始渐变指示器
                float indicator1 = mWidth / 2 - textWidth / 2;
                //结束渐变指示器
                float indicator2 = mWidth / 2 + textWidth / 2;
                //文字变色部分的距离
                float coverTextLength = textWidth / 2 - mWidth / 2 + coverlength;
                float textProgress = coverTextLength / textWidth;
                if (coverlength <= indicator1) {
                    mTextPaint.setShader(null);
                    mTextPaint.setColor(context.getResources().getColor(R.color.download_bg));
                } else if (indicator1 < coverlength && coverlength <= indicator2) {
                    LinearGradient mProgressTextGradient = new LinearGradient((mWidth - textWidth) / 2, 0, (mWidth + textWidth) / 2, 0,
                            new int[]{context.getResources().getColor(R.color.white), context.getResources().getColor(R.color.download_bg)},
                            new float[]{textProgress, textProgress + 0.001f},
                            Shader.TileMode.CLAMP);
                    mTextPaint.setColor(context.getResources().getColor(R.color.download_bg));
                    mTextPaint.setShader(mProgressTextGradient);
                } else {
                    mTextPaint.setShader(null);
                    mTextPaint.setColor(context.getResources().getColor(R.color.white));
                }
                canvas.drawText(mText,(mWidth - textWidth) / 2,y,mTextPaint);
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (state){
            case STATE_STOPPED:
                drawStopped(canvas);
                break;
            case STATE_DOWNLOADING:
                drawDowning(canvas);
                break;
            case STATE_FAIL:
                drawStopped(canvas);
        }
        drawText(canvas);
        super.onDraw(canvas);
    }

    private void drawInstalled(Canvas canvas) {
        mPaint.setColor(context.getResources().getColor(R.color.download_bg));
        mPaint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(0,0,mWidth,mHeight);
        canvas.drawRoundRect(rectF,10,10,mPaint);
    }

    private void drawDowning(Canvas canvas) {
        mPaint.setColor(context.getResources().getColor(R.color.nodown_bg));
        mPaint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(0,0,mWidth,mHeight);
        canvas.drawRoundRect(rectF,10,10,mPaint);
        mPaint.setColor(context.getResources().getColor(R.color.download_bg));
        float dWidth = percent * mWidth / 100;
        canvas.save();
        canvas.clipRect(new RectF(0,0,dWidth,mHeight));
        canvas.drawRoundRect(rectF,10,10,mPaint);
        canvas.restore();
    }

    private void drawStopped(Canvas canvas) {
        mPaint.setColor(context.getResources().getColor(R.color.download_bg));
        mPaint.setStyle(Paint.Style.FILL);
        /*Path path = new Path();
        path.moveTo(mHeight / 2,0);
        path.lineTo(mWidth - mHeight / 2,0);
        path.quadTo(mWidth,mHeight / 2,mWidth - mHeight / 2,mHeight);
        path.lineTo(mHeight / 2,mHeight);
        path.quadTo(0,mHeight / 2,mHeight / 2,0);
        path.close();
        canvas.drawPath(path,mPaint);*/
        RectF rectF = new RectF(0,0,mWidth,mHeight);
        canvas.drawRoundRect(rectF,10,10,mPaint);
    }

    public void reSet(){
        state = STATE_STOPPED;
        invalidate();
    }

    public void setComment(){
        reSet();
    }

    public void setPercent(int percent){
        startDownload(percent);
    }

    public void setDownloaded(){
        state = STATE_HASDOWNLOAD;
        invalidate();
    }

    public void setWaitting(){
        state = STATE_WAITTING;
        invalidate();
    }
    public void setFail(){
        state = STATE_FAIL;
        invalidate();
    }

    private void startDownload(int percent){
        state = STATE_DOWNLOADING;
        if (percent >= 0 && percent <= 100){
            this.percent = percent;
            invalidate();
        }
        invalidate();
    }

    @Override
    public void onClick(View v) {
        if (state == STATE_DOWNLOADING || state == STATE_WAITTING){
            if (listener != null)
                listener.downloadStop();
        }else if(state == STATE_STOPPED){
            if (listener != null)
                listener.downloadStart();
        }else if(state == STATE_HASDOWNLOAD){
            return;
        }else if(state == STATE_FAIL){
            if (listener != null)
                listener.downloadStart();
            return;
        }
    }

    public interface OnDownloadClickListener{
        public void downloadStart();
        public void downloadStop();
    }
}
