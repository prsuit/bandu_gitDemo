package me.bandu.talk.android.phone.holder;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.impl.OnRecyclerViewListener;


/**
 * 创建者：wanglei
 * <p>时间：16/5/25  10:53
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

    public View rootView;
    public Button column;//柱子
    public TextView topNum;
    public TextView bottomNum;
    public int position;
    private OnRecyclerViewListener onRecyclerViewListener;

    public PersonViewHolder(View itemView,OnRecyclerViewListener onRecyclerViewListener) {
        super(itemView);
        this.onRecyclerViewListener = onRecyclerViewListener;
        column = (Button) itemView.findViewById(R.id.recycler_view_test_item_person_name_tv);
        bottomNum = (TextView) itemView.findViewById(R.id.recycler_view_test_item_person_age_tv);
        topNum = (TextView) itemView.findViewById(R.id.recycler_view_test_item_person);
        rootView = itemView.findViewById(R.id.recycler_view_test_item_person_view);
        column.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        if (null != onRecyclerViewListener) {
            onRecyclerViewListener.onTouch(v,ev,position, (String) topNum.getText());
        }
        return false;
    }

}
