package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.TextBookInfoBean;
/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BookAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private List<TextBookInfoBean.DataBean.ContentsBean> units;
	private List<List<TextBookInfoBean.DataBean.ContentsBean.LessonListBean>> lessons;

	public BookAdapter(Context mContext, List<TextBookInfoBean.DataBean.ContentsBean> units, List<List<TextBookInfoBean.DataBean.ContentsBean.LessonListBean>> lessons) {
		super();
		this.mContext = mContext;
		this.units = units;
		this.lessons = lessons;
	}


	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return lessons.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return lessons != null && lessons.size() > 0 ?  lessons.get(groupPosition).size() : 0;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		TextBookInfoBean.DataBean.ContentsBean.LessonListBean lesson = lessons.get(groupPosition).get(childPosition);

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.layout_books_child, null, false);
			holder.childTxt1 = (TextView) convertView.findViewById(R.id.tv1);
			/*holder.childTxt2 = (TextView) convertView.findViewById(R.id.tv2);
			holder.childTxt3 = (TextView) convertView.findViewById(R.id.tv3);*/
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.childTxt1.setText(lesson.getLesson_name());

		return convertView;
	}



	// ----------------Group----------------//
	@Override
	public Object getGroup(int groupPosition) {
		return units.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public int getGroupCount() {
		return units.size();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_books_group,null);
			holder.groupTxt1 = (TextView) convertView.findViewById(R.id.tv_group);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.groupTxt1.setText(units.get(groupPosition).getUnit_name());

		return convertView;

	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	private class ViewHolder {
		TextView groupTxt1;

		TextView childTxt1;
		TextView childTxt2;
		TextView childTxt3;
	}

}
