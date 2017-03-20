package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.ExerciseDowanloadBean;
import me.bandu.talk.android.phone.db.mdao.MCentenceDao;
import me.bandu.talk.android.phone.db.mdao.MUnitDao;
import me.bandu.talk.android.phone.manager.MDownloadManager;
import me.bandu.talk.android.phone.manager.MDownloadTask;
import me.bandu.talk.android.phone.view.DownLoadButton;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentDownloadAdapter extends BaseAdapter{
	private Context context;
	private List<ExerciseDowanloadBean.DataEntity.ContentsEntity> units;
	private MCentenceDao centenceDao;
	private MUnitDao unitDao;
	private OnDownloadClickListener listener;
	private Map<Long,Integer> positions;
	private Map<Integer,DownLoadButton> buttons;

	public StudentDownloadAdapter(final Context context, List<ExerciseDowanloadBean.DataEntity.ContentsEntity> units,OnDownloadClickListener listener){
		this.context = context;
		this.units = units;
		this.listener = listener;
		this.centenceDao = new MCentenceDao(context);
		this.unitDao = new MUnitDao(context);
		this.positions = new HashMap<>();
		this.buttons = new HashMap<>();
	}



	@Override
	public int getCount() {
		return units.size();
	}

	@Override
	public Object getItem(int position) {
		return units.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ExerciseDowanloadBean.DataEntity.ContentsEntity unit = units.get(position);
		ViewHolder viewHolder = null;

		if (convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_exercise_download_item,null);
			viewHolder.tv_uname = (TextView) convertView.findViewById(R.id.tv_unitname);
			viewHolder.tv_state = (DownLoadButton) convertView.findViewById(R.id.tv_state);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_uname.setText(unit.getUnit_name());
		MUnitDao unitDao = new MUnitDao(context);
		if (unitDao.hasKey(unit.getUnit_id())){
			viewHolder.tv_state.setDownloaded();
		}else {
			int state = MDownloadManager.getInstence(context).getState(unit.getUnit_id());
			MDownloadTask task = MDownloadManager.getInstence(context).getTask(unit.getUnit_id());
				if (state == MDownloadTask.STATE_DOWNLOADING){
					int percent = task.getDownloadInfo().getDownload_progress();
					viewHolder.tv_state.setPercent(percent);
				}else if(state == MDownloadTask.STATE_NOTDOWN){
					viewHolder.tv_state.setComment();
				}else if(state == MDownloadTask.STATE_WAITTING){
					viewHolder.tv_state.setWaitting();
				}else if(state == MDownloadTask.STATE_DOWNLOADFAIL){
					viewHolder.tv_state.setFail();
				}else if(state == MDownloadTask.STATE_DOWNLOADSTOP){
					viewHolder.tv_state.setComment();
				}else if(state == MDownloadTask.STATE_DOWNLOADFINISH){
					viewHolder.tv_state.setComment();
				}else if(state == MDownloadTask.STATE_DOWNLOADFAIL){
					viewHolder.tv_state.setFail();
				}
		}

		positions.put(Long.parseLong(unit.getUnit_id() + ""),position);
		buttons.put(position,viewHolder.tv_state);

		/*if (state[0] == unit.getUnit_id()){
			viewHolder.tv_state.setBackgroundColor(ColorUtil.getResourceColor(context,R.color.transparent));
			viewHolder.tv_state.setTextColor(ColorUtil.getResourceColor(context,R.color.student_title_bg));
			viewHolder.tv_state.setEnabled(false);
			viewHolder.tv_state.setClickable(false);
		}*/

		/*viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null)
					listener.downloadClickListener(unit.getUnit_id());
			}
		});*/
		viewHolder.tv_state.setDownloadListener(new DownLoadButton.OnDownloadClickListener() {
			@Override
			public void downloadStart() {
				if (listener != null)
					listener.downloadStart(unit);
			}

			@Override
			public void downloadStop() {
				if (listener != null)
					listener.downloadStop(unit.getUnit_id());
			}
		});

		return convertView;
	}


	public int getPosition(long id){
		if (positions.containsKey(id))
			return positions.get(id);
		else return -1;
	}

	public DownLoadButton getButton(int position){
		return buttons.get(position);
	}

	public interface OnDownloadClickListener{
		public void downloadStart(ExerciseDowanloadBean.DataEntity.ContentsEntity unit);
		public void downloadStop(long unitid);
	}

	class ViewHolder {
		TextView tv_uname;
		DownLoadButton tv_state;
	}

}
