package me.bandu.talk.android.phone.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：高楠
 * 时间：2015/11/23
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CapterOneHolder extends RecyclerView.ViewHolder {
    public TextView tv;

    public CapterOneHolder(View itemView) {
        super(itemView);
        tv = (TextView)itemView.findViewById(R.id.tv);
    }
}
