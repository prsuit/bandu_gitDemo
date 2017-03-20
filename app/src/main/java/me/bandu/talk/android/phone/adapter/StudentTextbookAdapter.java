package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.ExerciseTextBookChoseBean;
import me.bandu.talk.android.phone.utils.ImageLoaderOption;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.view.StarView;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentTextbookAdapter extends BaseAdapter{
	private final String TAG = "com.fy.listview.TStudentAdapter";
	private Context context;
	private List<ExerciseTextBookChoseBean.DataEntity.ListEntity> books;

	public StudentTextbookAdapter(Context context, List<ExerciseTextBookChoseBean.DataEntity.ListEntity> books){
		this.context = context;
		this.books = books;
	}

	@Override
	public int getCount() {
		return books.size();
	}

	@Override
	public Object getItem(int position) {
		return books.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ExerciseTextBookChoseBean.DataEntity.ListEntity book = books.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_student_textbook_item,null);
			viewHolder.iv_stextbook = (ImageView) convertView.findViewById(R.id.iv_stextbook);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.tv_version = (TextView) convertView.findViewById(R.id.tv_version);
			viewHolder.tv_grade = (TextView) convertView.findViewById(R.id.tv_grade);
			viewHolder.sv = (StarView) convertView.findViewById(R.id.sv_stars);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(StringUtil.getShowText(book.getCover()),viewHolder.iv_stextbook, ImageLoaderOption.getOptions1());

		viewHolder.tv_name.setText(StringUtil.getShowText(book.getName()));
		if (book.getSubject() == 0){
			viewHolder.tv_version.setText(StringUtil.getShowText(book.getVersion()));
			viewHolder.tv_grade.setVisibility(View.VISIBLE);
			viewHolder.sv.setVisibility(View.GONE);
			viewHolder.tv_grade.setText("适用年级:" + book.getGrade());
		}else {
			viewHolder.tv_version.setText(StringUtil.getShowText(book.getEn_name()));
			viewHolder.tv_grade.setVisibility(View.GONE);
			viewHolder.sv.setVisibility(View.VISIBLE);
			viewHolder.sv.setNumber(book.getStar());
		}




		return convertView;
	}

	class ViewHolder {
		ImageView iv_stextbook;
		StarView sv;
		TextView tv_name,tv_version,tv_grade;
	}

}
