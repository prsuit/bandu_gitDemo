package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.TStudentBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.view.ListViewItemHorizontalScrollView;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ExerciseChoseAdapter extends BaseAdapter{
	private List<? extends Object> objs;
	private Context context;
	private int select;

	public ExerciseChoseAdapter(Context context, List<? extends Object> objs){
		this.context = context;
		this.objs = objs;
	}

	public void setSelected(int select){
		this.select = select;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return objs.size();
	}

	@Override
	public Object getItem(int position) {
		return objs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		Object obj = objs.get(position);

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_doexercise_radiobutton_311, null);
			convertView.setFocusable(false);
			convertView.setClickable(false);
			convertView.setPadding(ScreenUtil.dp2px(16,context),
					ScreenUtil.dp2px(10,context),
					ScreenUtil.dp2px(16,context),
					ScreenUtil.dp2px(10,context));
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,ScreenUtil.dp2px(50,context));
			convertView.setLayoutParams(params);

			viewHolder.rb_select = (RadioButton) convertView;

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (obj instanceof LessonBean){
			viewHolder.rb_select.setText(StringUtil.getShowText(((LessonBean) obj).getLesson_name()));
		}else if (obj instanceof PartBean){
			viewHolder.rb_select.setText(StringUtil.getShowText(((PartBean) obj).getPart_name()));
		}

		if (select == position){
			viewHolder.rb_select.setChecked(true);
		}else {
			viewHolder.rb_select.setChecked(false);
		}

		return convertView;
	}

	class ViewHolder {
		RadioButton rb_select;
	}

}
