package me.bandu.talk.android.phone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.UnitInfoEntity;
import me.bandu.talk.android.phone.bean.WorkDataBean;
import me.bandu.talk.android.phone.viewholder.CapterTwoHolder;

/**
 * 创建者：高楠
 * 时间：on 2016/3/29
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkLessonAdapter  extends BaseAdapter {
    Context context;
    List<UnitInfoEntity> list;
    WorkDataBean dataBean;
    public CreatWorkLessonAdapter(Context context, List<UnitInfoEntity> list, WorkDataBean dataBean){
        this.context = context;
        this.list = list;
        this.dataBean = dataBean;
    }
    public void setDataBean(WorkDataBean dataBean){
        this.dataBean = dataBean;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CapterTwoHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_t,null);
            holder = new CapterTwoHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (CapterTwoHolder) convertView.getTag();
        }
        holder.tv.setText(list.get(position).getLesson_name());
        return convertView;
    }


    public String getLesson_id(int position) {
        if (list!=null&&position<list.size()){
            return list.get(position).getLesson_id();
        }
        return null;
    }

    public String getLesson_name(int position) {
        if (list!=null&&position<list.size()){
            return list.get(position).getLesson_name();
        }
        return null;
    }
}
