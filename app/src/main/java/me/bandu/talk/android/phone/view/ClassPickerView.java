package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.bandu.talk.android.phone.bean.UserClassListBean;
import me.bandu.talk.android.phone.greendao.bean.ClassTabelBean;
import me.bandu.talk.android.phone.greendao.utils.DBUtils;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 *
 * @author GaoNan
 * 选择器
 */
public class ClassPickerView extends View {

//	public static final String TAG = "PickerView";
	/**
	 * text之间间距和minTextSize之比
	 */
	public static final float MARGIN_ALPHA = 2.8f;
	/**
	 * 自动回滚到中间的速度
	 */
	public static final float SPEED = 2;
	private static final int CLASS_PICKER_CODE = 103;

	private List<UserClassListBean.DataBean.ClassListBean> mDataList;

	/**
	 * 选中的位置，这个位置是mDataList的中心位置，一直不变
	 */
	private int mCurrentSelected;
	private Paint mPaint;

	private float mMaxTextSize = 80;
	private float mMinTextSize = 40;

	public void setmMinTextSize(float mMinTextSize) {
		this.mMinTextSize = mMinTextSize;
	}

	public void setmMaxTextSize(float mMaxTextSize) {
		this.mMaxTextSize = mMaxTextSize;
	}

	private float mMaxTextAlpha = 255;
	private float mMinTextAlpha = 120;

	private int mColorText = 0x333333;

	private int mViewHeight;
	private int mViewWidth;

	private float mLastDownY;
	private boolean drawRed_falg;
	//数据库班级数据
	private List<ClassTabelBean> class_data;
	/**
	 * 滑动的距离
	 */
	private float mMoveLen = 0;
	private boolean isInit = false;
	private ClassPickerView.onClassSelectListener mSelectListener;
	private Timer timer;
	private MyTimerTask mTask;

	private Context context;
	private List<UserClassListBean.DataBean.ClassListBean> userData = new ArrayList<>();

	Handler updateHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (Math.abs(mMoveLen) < SPEED) {
				mMoveLen = 0;
				if (mTask != null) {
					mTask.cancel();
					mTask = null;
					performSelect();
				}
			} else
				// 这里mMoveLen / Math.abs(mMoveLen)是为了保有mMoveLen的正负号，以实现上滚或下滚
				mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * SPEED;
			invalidate();
		}

	};
	//构造器
	public ClassPickerView(Context context) {
		super(context);
		init(context);
	}
	public ClassPickerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	//设置选中的监听器
	public void setOnSelectListener(ClassPickerView.onClassSelectListener listener)
	{
		mSelectListener = listener;
	}

	private void performSelect(){
		if (mSelectListener != null)
			mSelectListener.onSelect(mDataList.get(mCurrentSelected).getClass_name(),String.valueOf(mDataList.get(mCurrentSelected).getCid()),String.valueOf(mDataList.get(mCurrentSelected).getStatus()));
	}
	//设置数据
	public void setData(List<UserClassListBean.DataBean.ClassListBean> datas, List<ClassTabelBean> bean){
		mDataList = datas;
		mCurrentSelected = datas.size() / 2;
		this.class_data = bean;
		invalidate();
	}
	//设置数据
	public void setData(List<UserClassListBean.DataBean.ClassListBean> datas){
		mDataList = datas;
		mCurrentSelected = datas.size() / 2;
		invalidate();
	}

	/**
	 * 选择选中的item的index
	 *
	 * @param selected
	 */
	public void setSelected(int selected) {
		mCurrentSelected = selected;
		int distance = mDataList.size() / 2 - mCurrentSelected;
		if (distance < 0)
			for (int i = 0; i < -distance; i++){
				moveHeadToTail();
				mCurrentSelected--;
			}
		else if (distance > 0)
			for (int i = 0; i < distance; i++){
				moveTailToHead();
				mCurrentSelected++;
			}
		invalidate();
	}

	/**
	 * 选择选中的内容
	 *
	 * @param mSelectItem
	 */
	public void setSelected(String mSelectItem) {
		for (int i = 0; i < mDataList.size(); i++) {
			if (mDataList.get(i).getClass_name().equals(mSelectItem)) {
				setSelected(i);
				break;
			}
		}
	}

	private void moveHeadToTail() {
		if (mDataList!=null&&mDataList.size()!=0){
			UserClassListBean.DataBean.ClassListBean head = mDataList.get(0);
			mDataList.remove(0);
			mDataList.add(head);
		}
	}

	private void moveTailToHead() {
		UserClassListBean.DataBean.ClassListBean tail = mDataList.get(mDataList.size() - 1);
		mDataList.remove(mDataList.size() - 1);
		if(!TextUtils.isEmpty(tail.getClass_name()))
			mDataList.add(0, tail);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mViewHeight = getMeasuredHeight();
		mViewWidth = getMeasuredWidth();
		// 按照View的高度计算字体大小
		mMaxTextSize = mViewHeight / 8.0f;
		mMinTextSize = mMaxTextSize / 2f;
		isInit = true;
		invalidate();
	}

	private void init(Context context) {
		this.context = context;
		timer = new Timer();
		mDataList = new ArrayList<>();
//		Paint.FILTER_BITMAP_FLAG是使位图过滤的位掩码标志
//		Paint.ANTI_ALIAS_FLAG是使位图抗锯齿的标志
//		Paint.DITHER_FLAG是使位图进行有利的抖动的位掩码标志
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.FILL);// 当然也可以设置为"实心"(Paint.Style.FILL)
		mPaint.setTextAlign(Align.CENTER);//设置绘制文字的对齐方向
		mPaint.setColor(mColorText);//字体颜色
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 根据index绘制view
		if (isInit)
			drawData(canvas);
	}


	private void drawData(Canvas canvas) {
//		Log.e("drawData","mCurrentSelected->"+mCurrentSelected);
		// 先绘制选中的text再往上往下绘制其余的text
		float scale = parabola(mViewHeight / 4.0f, mMoveLen);
		float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
		mPaint.setTextSize(size);
		mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
		// text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
		float x = (float) (mViewWidth / 2.0);
		float y = (float) (mViewHeight / 2.0 + mMoveLen);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
		if (mDataList!=null&&mDataList.size()!=0){
			mPaint.setColor(Color.BLACK);
			canvas.drawText(mDataList.get(mCurrentSelected).getClass_name(), x, baseline, mPaint);

			if(isShowRed()) {
				mPaint.setColor(Color.RED);
				canvas.drawCircle(x + mDataList.get(mCurrentSelected).getClass_name().length() / 2f * size + 10f,
						baseline - 50.0f, 8f, mPaint);
			}
//			canvas.drawCircle(x + getTextWidth(mDataList.get(mCurrentSelected),size) + 5f ,baseline - 50.0f ,8f,mPaint);
			if(onTextChanged != null)
				onTextChanged.onTextChanged(mDataList.get(mCurrentSelected).getClass_name());
		}

		// 绘制上方data
		for (int i = 1; (mCurrentSelected - i) >= 0; i++){
			drawOtherText(canvas, i, -1);
		}
		// 绘制下方data
		for (int i = 1; (mCurrentSelected + i) < mDataList.size(); i++){
			drawOtherText(canvas, i, 1);
		}
	}

	private boolean isShowRed() {
		boolean showReddot = false;
		for (int i = 0; i < class_data.size(); i++) {
			if(class_data.get(i).getClassID() == mDataList.get(mCurrentSelected).getCid()) {
				showReddot = class_data.get(i).isShowReddot();
			}
		}

		return showReddot;
	}

	private float getTextWidth(String str, float size){
		TextView tv = new TextView(context);
		tv.setText(str.substring(str.length()/2));
		tv.setTextSize(size);

		return 0f;
	}

	/**
	 * @param canvas
	 * @param position
	 *            距离mCurrentSelected的差值
	 * @param type
	 *            1表示向下绘制，-1表示向上绘制
	 */
	private void drawOtherText(Canvas canvas, int position, int type) {
		float d = (float) (MARGIN_ALPHA * mMinTextSize * position + type
				* mMoveLen);
		float scale = parabola(mViewHeight / 4.0f, d);
		float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
		mPaint.setTextSize(size);
		mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
		float y = (float) (mViewHeight / 2.0 + type * d);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));

		mPaint.setColor(Color.BLACK);
			canvas.drawText(mDataList.get(mCurrentSelected + type * position).getClass_name(),
					(float) (mViewWidth / 2.0), baseline, mPaint);

		if(isShowRed()) {
			mPaint.setColor(Color.RED);
			canvas.drawCircle((float) (mViewWidth / 2.0) + mDataList.get(mCurrentSelected).getClass_name().length() / 2 * size + 10f, baseline - 30.0f, 8f, mPaint);
		}

	}

	/**
	 * 抛物线
	 *
	 * @param zero
	 *            零点坐标
	 * @param x
	 *            偏移量
	 * @return scale
	 */
	private float parabola(float zero, float x) {
		float f = (float) (1 - Math.pow(x / zero, 2));
		return f < 0 ? 0 : f;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
			doDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			doMove(event);
			break;
		case MotionEvent.ACTION_UP:
			doUp(event);
			break;
		}
		return true;
	}

	private void doDown(MotionEvent event) {
		if (mTask != null)
		{
			mTask.cancel();
			mTask = null;
		}
		mLastDownY = event.getY();
	}

	private void doMove(MotionEvent event) {

		mMoveLen += (event.getY() - mLastDownY);

		if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2)
		{
			// 往下滑超过离开距离
			moveTailToHead();
			mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;
		} else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2)
		{
			// 往上滑超过离开距离
			moveHeadToTail();
			mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
		}

		mLastDownY = event.getY();
		invalidate();
	}

	private void doUp(MotionEvent event) {
		// 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
		if (Math.abs(mMoveLen) < 0.0001)
		{
			mMoveLen = 0;
			return;
		}
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}
		mTask = new MyTimerTask(updateHandler);
		timer.schedule(mTask, 0, 10);
	}



	class MyTimerTask extends TimerTask {
		Handler handler;

		public MyTimerTask(Handler handler)
		{
			this.handler = handler;
		}

		@Override
		public void run()
		{
			handler.sendMessage(handler.obtainMessage());
		}

	}

	public interface onClassSelectListener {
		void onSelect(String classname,String cid,String state);
	}

	private onTextChanged onTextChanged;

	public void setOnTextChanged(ClassPickerView.onTextChanged onTextChanged) {
		this.onTextChanged = onTextChanged;
	}

	public interface onTextChanged{
		void onTextChanged(String text);
	}

	//是否画小红点
	public void setDrawRed(boolean flag) {
		this.drawRed_falg = flag;
	}
}
