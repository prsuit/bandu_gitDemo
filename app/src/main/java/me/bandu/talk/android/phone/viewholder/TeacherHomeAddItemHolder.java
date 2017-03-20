package me.bandu.talk.android.phone.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.bandu.talk.android.phone.R;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/25 15:46
 * 类描述：教书主页列表的item
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TeacherHomeAddItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.llAddClass)
    public LinearLayout llAddClass;

    public TeacherHomeAddItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
