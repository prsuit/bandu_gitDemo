package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 创建者：gaoye
 * 时间：2015/11/27  16:15
 * 类描述：与自定义的scrollview使用 一旦触发侧滑 则不会上下滑动
 * 修改人：
 * 修改时间：
 * 修改备注： 没有用到
 */
public class BaseListView extends ListView {
    private Context context;
    private boolean giveself = true;
    public BaseListView(Context context) {
        this(context, null);
    }

    public BaseListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    float startX,endX,startY,endY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                giveself = true;
                startX = endX = ev.getX();
                startY = endY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                endX = ev.getX();
                endY = ev.getY();
                if (Math.abs(endX - startX) > Math.abs(endY - startY) + 10 || giveself == false){
                    giveself = false;
                    startX = endX ;
                    startY = endY;
                    return false;
                }else{
                    startX = endX ;
                    startY = endY;
                    return super.onInterceptTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startY = startX = endX = endY = 0;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
