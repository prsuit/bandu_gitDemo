package me.bandu.talk.android.phone.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.CheckWorkListActivity;
import me.bandu.talk.android.phone.activity.ClassManagerActivity;
import me.bandu.talk.android.phone.activity.CreatWorkUnitActivity;
import me.bandu.talk.android.phone.activity.CreateClassActivity;
import me.bandu.talk.android.phone.bean.TeacherHomeList;
import me.bandu.talk.android.phone.bean.WorkDataBean;
import me.bandu.talk.android.phone.fragment.TeacherHomeListFragment;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.viewholder.FootViewHolder;
import me.bandu.talk.android.phone.viewholder.TeacherHomeAddItemHolder;
import me.bandu.talk.android.phone.viewholder.TeacherHomeItemHolder;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/25 15:44
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TeacherHomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private TeacherHomeListFragment home;
    private TeacherHomeList homeList;
    private List<TeacherHomeList.DataEntity.ListEntity> list;

    public void setList(TeacherHomeList homeList) {
        this.homeList = homeList;
        this.list = homeList.getData().getList();
    }

    public TeacherHomeListAdapter(TeacherHomeListFragment home, TeacherHomeList homeList) {
        this.home = home;
        this.homeList = homeList;
        list = homeList.getData().getList();
        LogUtils.i("当前 : list.size ; " + list.size());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BOTTOM)
            return new TeacherHomeAddItemHolder(home.getActivity().getLayoutInflater().from(home.getActivity()).inflate(R.layout.teacher_home_list_add_item, parent, false));
//        return new TeacherHomeItemHolder(View.inflate(home.getActivity(), R.layout.teacher_home_list_item, null));
        if(viewType == TYPE_TOP)
            return new FootViewHolder(LayoutInflater.from(home.getActivity()).inflate(R.layout.item_foot, parent,  false));
        return new TeacherHomeItemHolder(home.getActivity().getLayoutInflater().from(home.getActivity()).inflate(R.layout.teacher_home_list_item, parent, false));
    }

    private static final int TYPE_TOP = 0;
    private static final int TYPE_MIDDLE = 1;
    private static final int TYPE_BOTTOM = 2;

    @Override
    public int getItemViewType(int position) {
        if (position == homeList.getData().getList().size())
            return TYPE_BOTTOM;
        if(position+1 == getItemCount())
            return TYPE_TOP;
        return TYPE_MIDDLE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LogUtils.i("当前 : position" + position);
        if (position == homeList.getData().getList().size() ) {
            TeacherHomeAddItemHolder itemHolder = (TeacherHomeAddItemHolder) holder;
            itemHolder.llAddClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtils.showToastSafe("添加一个新班级");

                    Intent intent = new Intent(home.getActivity(), CreateClassActivity.class);
                    home.getActivity().startActivity(intent);

                }
            });
        } else {
            TeacherHomeItemHolder itemHolder = (TeacherHomeItemHolder) holder;
            itemHolder.ivStuList.setVisibility(list.get(position).isHas_wait_student()? View.VISIBLE : View.INVISIBLE);
            itemHolder.ivCheckWork.setVisibility(list.get(position).getCheck_job_num() > 0 ? View.VISIBLE : View.INVISIBLE);
            itemHolder.tvClassName.setText(list.get(position).getClass_name());
            itemHolder.tvClassNum.setText("班级编号:"+list.get(position).getCid());

            itemHolder.tvStuList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent classManagerIntent = new Intent(home.getActivity(), ClassManagerActivity.class);
                    classManagerIntent.putExtra("page",1);
                    classManagerIntent.putExtra("classInfo",list.get(position));
                    home.startActivity(classManagerIntent);
                   // UIUtils.showToastSafe("获取cid:"+list.get(position).getCid() + "班级学生界面");

                }
            });
            itemHolder.tvCheckWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(home.getActivity(), CheckWorkListActivity.class);
                    intent.putExtra("cid",list.get(position).getCid());
                    home.startActivity(intent);
                   //UIUtils.showToastSafe("检查cid:"+list.get(position).getCid() + "班级作业");
                }
            });
            itemHolder.ivSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent classManagerIntent = new Intent(home.getActivity(), ClassManagerActivity.class);
                    classManagerIntent.putExtra("classInfo",list.get(position));
                    home.startActivity(classManagerIntent);
                    //UIUtils.showToastSafe("设置cid:"+list.get(position).getCid() + "班级设置");
                }
            });
            itemHolder.ivCreateWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(home.getActivity(), CreatWorkUnitActivity.class);
                    WorkDataBean dataBean = new WorkDataBean();
                    dataBean.setBook_id(list.get(position).getBookid());
                    dataBean.setUid( String.valueOf(UserUtil.getUerInfo(home.getActivity()).getUid()));
                    dataBean.setCid(list.get(position).getCid());
                    dataBean.setClass_name(list.get(position).getClass_name());
                    intent.putExtra("data",dataBean);
                    home.getActivity().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return homeList.getData().getList().size()+1;
    }


}
