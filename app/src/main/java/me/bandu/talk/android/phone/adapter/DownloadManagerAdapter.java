package me.bandu.talk.android.phone.adapter;

import android.app.DownloadManager;
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
import me.bandu.talk.android.phone.db.bean.DownloadBean;
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
public class DownloadManagerAdapter extends BaseAdapter{
	private Context context;
	private List<DownloadBean> downloads;
	private OnDownloadClickListener listener;
	private Map<Long,Integer> positions;
	private Map<Integer,DownLoadButton> buttons;

	public DownloadManagerAdapter(Context context, List<DownloadBean> downloads, OnDownloadClickListener listener ){
		this.context = context;
		this.downloads = downloads;
		this.listener = listener;
		this.positions = new HashMap<>();
		this.buttons = new HashMap<>();
	}

	@Override
	public int getCount() {
		return downloads.size();
	}

	@Override
	public Object getItem(int position) {
		return downloads.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final DownloadBean downloadBean = downloads.get(position);
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
		viewHolder.tv_uname.setText(downloadBean.getDownload_name());

		if (MDownloadManager.getInstence(context).getTask(downloadBean.getDownload_id()) != null){
			if (downloadBean.getDownload_state() == MDownloadTask.STATE_DOWNLOADING){
				viewHolder.tv_state.setPercent(downloadBean.getDownload_progress());
			}else if(downloadBean.getDownload_state() == MDownloadTask.STATE_NOTDOWN){
				viewHolder.tv_state.setComment();
			}else if(downloadBean.getDownload_state() == MDownloadTask.STATE_WAITTING){
				viewHolder.tv_state.setWaitting();
			}else if(downloadBean.getDownload_state() == MDownloadTask.STATE_DOWNLOADFAIL){
				viewHolder.tv_state.setFail();
			}
		}else {
			viewHolder.tv_state.setFail();
		}


		viewHolder.tv_state.setDownloadListener(new DownLoadButton.OnDownloadClickListener() {
			@Override
			public void downloadStart() {
				if (listener != null)
					listener.downloadStart(downloadBean);
			}

			@Override
			public void downloadStop() {
				if (listener != null)
					listener.downloadStop(downloadBean);
			}
		});
		positions.put(downloadBean.getDownload_id(),position);
		buttons.put(position,viewHolder.tv_state);

		return convertView;
	}

	public int getPosition(long id){
		if (positions.containsKey(id))
			return positions.get(id);
		else return -1;
	}

	@Override
	public void notifyDataSetChanged() {
		positions.clear();
		buttons.clear();
		super.notifyDataSetChanged();
	}

	public DownLoadButton getButton(int position){
		return buttons.get(position);
	}

	public interface OnDownloadClickListener{
		public void downloadStart(DownloadBean downloadBean);
		public void downloadStop(DownloadBean downloadBean);
	}

	class ViewHolder {
		TextView tv_uname;
		DownLoadButton tv_state;
	}

}
