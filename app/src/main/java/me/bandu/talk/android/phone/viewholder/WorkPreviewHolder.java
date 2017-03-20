package me.bandu.talk.android.phone.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：高楠
 * 时间：2015/11/25
 * 类描述：作业预览
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkPreviewHolder extends RecyclerView.ViewHolder{
    public TextView tv;
    public ImageView imageView;

    public WorkPreviewHolder(View itemView) {
        super(itemView);
        tv = (TextView)itemView.findViewById(R.id.tv);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }
}
