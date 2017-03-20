package me.bandu.talk.android.phone.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
public class TeacherHomeItemHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.tvClassName)
    public TextView tvClassName;

    @Bind(R.id.tvClassNum)
    public TextView tvClassNum;

    @Bind(R.id.ivSetting)
    public ImageView ivSetting;

    @Bind(R.id.tvCheckWork)
    public  TextView tvCheckWork;

    @Bind(R.id.tvStuList)
    public  TextView tvStuList;

    @Bind(R.id.ivCreateWork)
    public  ImageView ivCreateWork;

    @Bind(R.id.ivCheckWork)
    public ImageView ivCheckWork;

    @Bind(R.id.ivStuList)
    public  ImageView ivStuList;


    public TeacherHomeItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }



}
