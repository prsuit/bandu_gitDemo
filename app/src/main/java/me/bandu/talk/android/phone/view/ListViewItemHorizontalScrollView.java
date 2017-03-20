package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.List;

import me.bandu.talk.android.phone.adapter.TStudentAdapter;
import me.bandu.talk.android.phone.utils.ScreenUtil;
/**
 * 创建者：gaoye
 * 时间：2015/11/25  09:49
 * 类描述：为解决首字母和左滑冲突的横向srcollview
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ListViewItemHorizontalScrollView extends HorizontalScrollView{
	private final String TAG = "com.fy.listview.ListViewItemHorizontalScrollView";
	private Context context;
	private LayoutInflater inflater;
	private LinearLayout ll_main;
	private int rightViewWidth,leftViewWidth;
	//private final float STARTVALUE = 5;
	private int state;
	private final int STATE_CLOSED = 0,STATE_OPENED = 1,STATE_SCROLLING = 2;
	private ItemStateChangeListener listener;
	private List<Integer> stateControl;
	private int currentPosition;
	private RightOnclickListener rlistener;
	private TStudentAdapter adapter;
	private VelocityTracker vTracker = null;

	public ListViewItemHorizontalScrollView(Context context) {
		this(context,null);
	}
	
	public ListViewItemHorizontalScrollView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	
	public ListViewItemHorizontalScrollView(Context context,
											AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init();
	}

	private void init() {
		inflater = LayoutInflater.from(context);
		ll_main = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		ll_main.setLayoutParams(params);
		addView(ll_main);
	}
	
	public void setContral(List<Integer> stateControl,int currentPosition){
		this.stateControl = stateControl;
		this.currentPosition = currentPosition;
	}

	public void setCurrentPosition(int currentPosition){
		this.currentPosition = currentPosition;
	}
	
	public List<Integer> getContralList(){
		return stateControl;
	}
	
	public int getCurrentPosition() {
		return currentPosition;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public void setLeftView(int leftRes){
		View view = inflater.inflate(leftRes, null);
		leftViewWidth = ScreenUtil.getScreenWidth(context);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(leftViewWidth,LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		ll_main.removeAllViews();
		ll_main.addView(view);
	}
	
	public void setRightView(int rightRes,int width){
		View view = inflater.inflate(rightRes, null);
		setRightView(view, width);
	}
	
	public void setLeftView(View view){
		leftViewWidth = ScreenUtil.getScreenWidth(context) - ScreenUtil.dp2px(16,context) - ScreenUtil.dp2px(30,context);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(leftViewWidth,LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		ll_main.removeAllViews();
		ll_main.addView(view);
	}

	public void setMAdapter(TStudentAdapter adapter){
		this.adapter = adapter;
	}
	
	public void setRightView(View view,int width){
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width,LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		rightViewWidth = width;
		ll_main.addView(view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rlistener != null) {
					rlistener.rightOnclick(currentPosition);
				}
			}
		});
	}
	
	private float startX;
	private float endX;
	private float distance;
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (state == STATE_SCROLLING) {
			return true;
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = ev.getX();
			endX = ev.getX();
			distance = 0;
			if(vTracker == null){
				vTracker = VelocityTracker.obtain();
			}else{
				vTracker.clear();
			}
			vTracker.addMovement(ev);
			break;
		case MotionEvent.ACTION_MOVE:
			vTracker.addMovement(ev);
			vTracker.computeCurrentVelocity(1000);
			endX = ev.getX();
			if (Math.abs(distance) > 5){
				smoothScrollBy(-(int) (endX - startX), 0);
			}
			distance += (endX - startX);
			startX = ev.getX();

			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			vTracker.addMovement(ev);
			vTracker.computeCurrentVelocity(1000);
			if (Math.abs(distance) > 5){
				if (vTracker.getXVelocity() > rightViewWidth / 4 ){
					if (state == STATE_CLOSED && distance < 0){
						if (adapter != null){
							adapter.closeALL();
						}
						openRight();
						break;
					}
				}
				if (Math.abs(distance) < rightViewWidth / 2) {
					if (distance < 0) {
						if (state == STATE_CLOSED) {
							state = STATE_SCROLLING;
							if (adapter != null){
								adapter.closeALL();
							}
							smoothScrollBy((int) distance, 0);
							state = STATE_CLOSED;
							if (stateControl != null) {
								stateControl.set(currentPosition, STATE_CLOSED);
							}
						}
					}else {
						if (state == STATE_OPENED) {
							state = STATE_SCROLLING;
							smoothScrollTo(-rightViewWidth - 1, 0);
							state = STATE_CLOSED;
							if (stateControl != null) {
								stateControl.set(currentPosition, STATE_CLOSED);
							}
						}else {
							closeRight();
						}
					}
				}else if (Math.abs(distance) > rightViewWidth / 2){
					if (distance < 0) {
						if (state == STATE_CLOSED) {
							if (adapter != null){
								adapter.closeALL();
							}
							openRight();
							if (stateControl != null) {
								stateControl.set(currentPosition, STATE_OPENED);
							}
						}
					}else {
						if (state == STATE_OPENED) {
							closeRight();
							if (stateControl != null) {
								stateControl.set(currentPosition, STATE_CLOSED);
							}
						}
					}
				}
			}
			break;
		}
		return true;
	}
	
	public void closeRight(){
			state = STATE_SCROLLING;
			smoothScrollTo(-rightViewWidth - 1, 0);
			state = STATE_CLOSED;
	}
	
	public void openRight(){
			state = STATE_SCROLLING;
			smoothScrollTo(rightViewWidth, 0);
			state = STATE_OPENED;
	}

	public void setStateChangeListener(ItemStateChangeListener listener){
		this.listener = listener;
	}
	public void setRightOnclickListener(RightOnclickListener rlistener){
		this.rlistener = rlistener;
	}
	public interface ItemStateChangeListener{
		public void stateChange(int state);
	}
	public interface RightOnclickListener{
		public void rightOnclick(int currentPosition);
	}
}
