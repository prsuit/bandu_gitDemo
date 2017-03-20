package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.db.bean.UnitBean;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentExerciseAdapter extends BaseAdapter{
	private final String TAG = "me.bandu.talk.android.phone.fragment.StudentExerciseFragment";
	private final int TYPE_COUNT = 2,TYPE_COMMON = 0,TYPE_CHOSE = 1;
	private Context context;
	private List<UnitBean> exercises;
	private boolean isdelete;
	private List<Map<String,Integer>> progress;
	private View.OnClickListener onClickListener;

	public StudentExerciseAdapter(Context context, List<UnitBean> exercises,List<Map<String,Integer>> progress,View.OnClickListener onClickListener){
		this.context = context;
		this.exercises = exercises;
		this.progress = progress;
		this.onClickListener = onClickListener;
	}

	public void setIsDelete(){
		isdelete = !isdelete;
		notifyDataSetChanged();
	}

	public boolean getIsDelete(){
		return isdelete;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0)
			return TYPE_CHOSE;
		else
			return TYPE_COMMON;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	@Override
	public int getCount() {
		return exercises.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return exercises.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (getItemViewType(position) == TYPE_CHOSE){
			if (convertView == null){
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.layout_exericise_chose,null);
				viewHolder.tv_exericise_edit = (TextView) convertView.findViewById(R.id.tv_exericise_edit);
				viewHolder.iv_exericise_add = (ImageView) convertView.findViewById(R.id.iv_exericise_add);
				viewHolder.tv_exericise_sum = (TextView) convertView.findViewById(R.id.tv_exericise_sum);
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv_exericise_edit.setOnClickListener(onClickListener);
			viewHolder.iv_exericise_add.setOnClickListener(onClickListener);
			viewHolder.tv_exericise_sum.setText("练习本(共" + exercises.size() + "个)");
			if(UserUtil.isLogin()){
				if (!isdelete)
					viewHolder.tv_exericise_edit.setText(StringUtil.getResourceString(context,R.string.edit));
				else
					viewHolder.tv_exericise_edit.setText(StringUtil.getResourceString(context,R.string.cancel));
			} else {
				viewHolder.tv_exericise_edit.setVisibility(View.GONE);
			}

		}else if (getItemViewType(position) == TYPE_COMMON){
			position--;
			if (convertView == null){
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.layout_exercise_item,null);
				viewHolder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_exericise_delete);
				viewHolder.tv_uname = (TextView) convertView.findViewById(R.id.tv_exericise_name);
				viewHolder.iv_lefticon = (ImageView) convertView.findViewById(R.id.iv_lefticon);
				viewHolder.tv_version = (TextView) convertView.findViewById(R.id.tv_exericise_version);
				viewHolder.tv_progress = (TextView) convertView.findViewById(R.id.tv_exericise_progress);
				viewHolder.ll_exercises = (LinearLayout) convertView.findViewById(R.id.ll_exericises);
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.iv_lefticon.setVisibility(View.GONE);
			if (exercises.get(position).getCategory() != null){
				if (exercises.get(position).getCategory() == 1){
					viewHolder.iv_lefticon.setVisibility(View.VISIBLE);
					viewHolder.iv_lefticon.setImageResource(R.mipmap.lefticon3);
				}else if (exercises.get(position).getCategory() == 2){
					viewHolder.iv_lefticon.setVisibility(View.VISIBLE);
					viewHolder.iv_lefticon.setImageResource(R.mipmap.lefticon2);
				}else if(exercises.get(position).getCategory() == 3){
					viewHolder.iv_lefticon.setVisibility(View.VISIBLE);
					viewHolder.iv_lefticon.setImageResource(R.mipmap.lefticon1);
				}else {
					viewHolder.iv_lefticon.setVisibility(View.VISIBLE);
					viewHolder.iv_lefticon.setImageResource(R.mipmap.lefticon1);
				}
			}else {
				viewHolder.iv_lefticon.setVisibility(View.VISIBLE);
				viewHolder.iv_lefticon.setImageResource(R.mipmap.lefticon2);
			}

			viewHolder.iv_delete.setTag(position);
			if (isdelete){
				viewHolder.iv_delete.setVisibility(View.VISIBLE);
				viewHolder.iv_delete.setOnClickListener(onClickListener);
			}else {
				viewHolder.iv_delete.setVisibility(View.GONE);
			}

			Map<String,Integer> map = progress.get(position);
			viewHolder.tv_progress.setText(StringUtil.getShowText("已练习" + map.get("yj") + "/" + map.get("zg")));
			viewHolder.tv_uname.setText(StringUtil.getShowText(exercises.get(position).getUnit_name()));
			viewHolder.tv_version.setText(StringUtil.getShowText(exercises.get(position).getTextbook_name()));
		}
		return convertView;
	}

	class ViewHolder {
		ImageView iv_delete,iv_exericise_add,iv_lefticon;
		TextView tv_uname,tv_progress,tv_version,tv_exericise_edit,tv_exericise_sum;
		LinearLayout ll_exercises;
	}
}
