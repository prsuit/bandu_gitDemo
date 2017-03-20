package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * 创建者：gaoye
 * 时间：2016/3/16 15:25
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：未使用
 */
public class MListView extends ListView implements GestureDetector.OnGestureListener {
    private Context context = null;
    private boolean outBound = false;
    private int distance;
    private int firstOut;

    public MListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public MListView(Context context) {
        super(context);
        this.context = context;
    }

    GestureDetector lisGestureDetector = new GestureDetector(context, this);

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int act = event.getAction();
        if ((act == MotionEvent.ACTION_UP || act == MotionEvent.ACTION_CANCEL)
                && outBound) {
            outBound = false;
            // scroll back
        }
        if (!lisGestureDetector.onTouchEvent(event)) {
            outBound = false;
        } else {
            outBound = true;
        }
        /*Rect rect = new Rect();
        getLocalVisibleRect(rect);
        TranslateAnimation am = new TranslateAnimation( 0, 0, -rect.top, 0);
        am.setDuration(1000);
        startAnimation(am);*/
        scrollTo(0, 0);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        int firstPos = getFirstVisiblePosition();
        int lastPos = getLastVisiblePosition();
        int itemCount = getCount();
        // outbound Top
        if (outBound && firstPos != 0 && lastPos != (itemCount - 1)) {
            scrollTo(0, 0);
            return false;
        }
        View firstView = getChildAt(firstPos);
        if (!outBound)
            firstOut = (int) e2.getRawY();
        if (firstView != null&& (outBound || (firstPos == 0
                && firstView.getTop() == 0 && distanceY < 0))) {
            // Record the length of each slide
            distance = firstOut - (int) e2.getRawY();
            scrollTo(0, distance / 2);
            return true;
        }
        // outbound Bottom
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        return false;
    }
    /*private static final int MAX_Y_OVERSCROLL_DISTANCE = 200;
    private Context mContext;
    private int mMaxYOverscrollDistance;
    public MListView(Context context) {
        this(context,null);
    }

    public MListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        final DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        final float density = metrics.density;
        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent){
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }*/
}
