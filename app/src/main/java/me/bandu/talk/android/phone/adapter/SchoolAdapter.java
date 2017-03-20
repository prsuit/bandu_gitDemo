package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.SchoolBean;

public class SchoolAdapter extends BaseAdapter implements SectionIndexer {
	private List<SchoolBean.DataEntity.ListEntity> list = null;
	private Context mContext;

	public SchoolAdapter(Context mContext, List<SchoolBean.DataEntity.ListEntity> list) {
		this.mContext = mContext;
		this.list = list;
	}

	public void updateListView(List<SchoolBean.DataEntity.ListEntity> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_address, null);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.name = (TextView) view.findViewById(R.id.name);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		int section = getSectionForPosition(position);

		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setText(list.get(position).getSortLetters().substring(0,1));
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		viewHolder.name.setText(list.get(position).getName());
		return view;

	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView name;
		ImageView selectImg;
		RelativeLayout layout;
	}

	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}

	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}