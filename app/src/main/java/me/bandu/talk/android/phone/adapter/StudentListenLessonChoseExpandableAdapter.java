package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;

/**
 * author by gaoye
 * time: 2016/2/19  15:06
 * description:
 * updateTime:
 * update description:
 */
public class StudentListenLessonChoseExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<LessonBean> lessons;
    private List<List<PartBean>> parts;
    private int selectGroupPosition = -1; // 选中的position
    private int selectChildPosition = -1; // 选中的position
    private int textColor = -1; // 原来默认的Color
    private boolean firstClick = false; // 为了获取默认的颜色

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    public StudentListenLessonChoseExpandableAdapter(Context context,List<LessonBean> lessons,
                                                     List<List<PartBean>> parts) {
        this.context = context;
        this.lessons = lessons;
        this.parts = parts;
    }

    @Override
    public int getGroupCount() {
        return lessons.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return parts.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lessons.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parts.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public static class GroupHolder {
        public TextView tv_text;
    }
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LessonBean lesson = lessons.get(groupPosition);
        final GroupHolder holder;
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = View.inflate(context, R.layout.layout_listen_lessonchose_group_item, null);
            holder.tv_text = (TextView) convertView.findViewById(R.id.tv_group_text);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.tv_text.setText(StringUtil.getShowText(lesson.getLesson_name()));
        return convertView;
    }


    public static class ChildHolder {
        public TextView tv_child;
    }
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PartBean part = parts.get(groupPosition).get(childPosition);
        ChildHolder holder;
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = View.inflate(context, R.layout.layout_listen_lessonchose_child_item, null);


            holder.tv_child = (TextView) convertView.findViewById(R.id.tv_child_text);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        if (!(selectGroupPosition == groupPosition && selectChildPosition == childPosition) && firstClick) {
            holder.tv_child.setTextColor(textColor);
        }
        if (selectGroupPosition == groupPosition && selectChildPosition == childPosition) {
            holder.tv_child.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }
        holder.tv_child.setText(StringUtil.getShowText(part.getPart_name()));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public int getSelectGroupPosition() {
        return selectGroupPosition;
    }

    public int getSelectChildPosition() {
        return selectChildPosition;
    }

    public void setSelectPosition(int groupPosition, int childPosition) {
        this.selectGroupPosition = groupPosition;
        this.selectChildPosition = childPosition;
        notifyDataSetChanged();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setFirstClick(boolean firstClick) {
        this.firstClick = firstClick;
    }
}
