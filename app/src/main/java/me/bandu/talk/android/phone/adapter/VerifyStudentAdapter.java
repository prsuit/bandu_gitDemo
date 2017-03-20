package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.TStudentBean;
import me.bandu.talk.android.phone.utils.ImageLoaderOption;
import me.bandu.talk.android.phone.utils.StringUtil;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class VerifyStudentAdapter extends BaseAdapter{
	private final String TAG = "com.fy.listview.VerifyStudentAdapter";
	private List<TStudentBean> students;
	private Context context;
	private int[] selects;

	public VerifyStudentAdapter(Context context, List<TStudentBean> students,int[] selects){
		this.students = students;
		this.context = context;
		this.selects = selects;
	}

	@Override
	public int getCount() {
		return students.size();
	}

	@Override
	public Object getItem(int position) {
		return students.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		TStudentBean student = students.get(position);

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_verifystudent_item, null);
			viewHolder.iv1 = (ImageView) convertView.findViewById(R.id.iv_select);
			viewHolder.iv2 = (ImageView) convertView.findViewById(R.id.civ_head);
			viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_name);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (selects[position] == 0)
			viewHolder.iv1.setImageResource(R.mipmap.noselect);
		else
			viewHolder.iv1.setImageResource(R.mipmap.selected);

		viewHolder.tv.setText(StringUtil.getShowText(student.getName()));

		//ImageLoader.getInstance().displayImage(StringUtil.getShowText(student.getAvatar()),viewHolder.iv2);
		ImageLoader.getInstance().displayImage(StringUtil.getShowText(student.getAvatar()),viewHolder.iv2, ImageLoaderOption.getHeadOptions());

		return convertView;
	}

	class ViewHolder {
		ImageView iv1,iv2;
		TextView tv;
	}

}
