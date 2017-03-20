package me.bandu.talk.android.phone.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.view.CircleImageView;

/**
 * 创建者：高楠
 * 时间：2015/11/26
 * 类描述：作业预览
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SelectStudentHolder{
    public TextView tv_letter;
    public TextView tv_name;
    public CheckBox checkBox;
    public CircleImageView iv_cover;
    public int position;

    public SelectStudentHolder(View itemView) {
        tv_letter = (TextView)itemView.findViewById(R.id.letter);
        tv_name = (TextView)itemView.findViewById(R.id.name);
        checkBox = (CheckBox)itemView.findViewById(R.id.checkbox);
        iv_cover = (CircleImageView)itemView.findViewById(R.id.cover);
    }
}
