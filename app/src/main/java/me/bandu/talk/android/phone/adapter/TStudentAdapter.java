package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.TStudentBean;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.view.ListViewItemHorizontalScrollView;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TStudentAdapter extends BaseAdapter{
	private final String TAG = "com.fy.listview.TStudentAdapter";
	private List<TStudentBean> students;
	private Context context;
	private List<ViewHolder> holders;
	private int width;
	private OnRightDeleteClickListener listener;

	public TStudentAdapter(Context context, List<TStudentBean> students,OnRightDeleteClickListener listener){
		this.students = students;
		this.context = context;
		this.width = ScreenUtil.getScreenWidth(context);
		this.holders = new ArrayList<ViewHolder>();
		this.listener = listener;
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
		final TStudentBean student = students.get(position);

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_tstudent_item, null);
			viewHolder.lhs = (ListViewItemHorizontalScrollView) convertView.findViewById(R.id.lhv_main);
			View left = LayoutInflater.from(context).inflate(R.layout.layout_item_left,null);
			viewHolder.lhs.setLeftView(left);
			TextView tvr = new TextView(context);
			tvr.setText("删除");
			tvr.setTextSize(16f);
			tvr.setTextColor(Color.WHITE);
			tvr.setBackgroundColor(context.getResources().getColor(R.color.delete_red));
			tvr.setGravity(Gravity.CENTER);
			viewHolder.lhs.setRightView(tvr,width / 5);
			viewHolder.lhs.setMAdapter(this);

			viewHolder.tvr = tvr;
			viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_tstudentitem_alpha);
			viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv_tstudentitem_name);
			viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_tstudentitem_head);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		holders.add(viewHolder);
		viewHolder.tvr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (listener != null)
					listener.rightDeleteClick(position);
			}
		});

		if("".equals(student.getAvatar())){
			viewHolder.iv.setImageResource(R.mipmap.default_avatar);
		}else{
			ImageLoader.getInstance().displayImage(student.getAvatar(),viewHolder.iv);
		}
		int section = getSectionForPosition(position);

		if(position == getPositionForSection(section)){
			viewHolder.tv1.setVisibility(View.VISIBLE);
			viewHolder.tv1.setText(student.getAlpha());
		}else{
			viewHolder.tv1.setVisibility(View.GONE);
		}

		viewHolder.tv1.setText(this.students.get(position).getAlpha());
		viewHolder.tv2.setText(this.students.get(position).getName());

		return convertView;
	}

	public interface OnRightDeleteClickListener{
		public void rightDeleteClick(int position);
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		if (students.get(position).getAlpha().length() <= 0)
			return ' ';
		return students.get(position).getAlpha().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = students.get(i).getAlpha();
			char firstChar;
			if (sortStr.length()<=0){
				firstChar = ' ';
			}else {
				firstChar = sortStr.toUpperCase().charAt(0);
			}

			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	class ViewHolder {
		TextView tv1;
		TextView tv2;
		TextView tvr;
		ImageView iv;
		ListViewItemHorizontalScrollView lhs;
	}

	public void closeALL(){
		for (int i = 0; i < holders.size(); i++) {
			ViewHolder holder = holders.get(i);
			holder.lhs.closeRight();
		}
	}

}
