package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxConstants;
import com.chivox.bean.YunZhiShengResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.TextColorUtils;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentDoExerciseAdapter_NEW extends BaseAdapter {
	private Context context;
	private List<CentenceBean> centences;
	private String clickstr = "";
	private View popView;
	private int expandPosition,preReadposition = -1;
	private View.OnClickListener listener;
	private BaseActivityIF callback;

	public StudentDoExerciseAdapter_NEW(Context context, List<CentenceBean> centences,View.OnClickListener listener,BaseActivityIF callback){
		this.context = context;
		this.centences = centences;
		this.listener = listener;
		this.callback = callback;
	}

	@Override
	public int getCount() {
		return centences.size();
	}

	@Override
	public Object getItem(int position) {
		return centences.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final CentenceBean centence = centences.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_exercise_list_item,null);
			viewHolder.iv_exemple = (ImageView) convertView.findViewById(R.id.iv_voice);
			viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			viewHolder.tv_duration = (TextView) convertView.findViewById(R.id.tv_duration);
			viewHolder.tv_chinese = (TextView) convertView.findViewById(R.id.tv_chinese);
			viewHolder.ll_source = (LinearLayout) convertView.findViewById(R.id.ll_source);
			viewHolder.ll_main = (LinearLayout) convertView.findViewById(R.id.ll_main);
			viewHolder.tv_position = (TextView) convertView.findViewById(R.id.tv_position);
			viewHolder.params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ScreenUtil.dp2px(40,context));
			viewHolder.params.setMargins(ScreenUtil.dp2px(16,context),0,0,0);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String video_time = centences.get(position).getMp3_url();

		viewHolder.tv_position.setText((position + 1) + "/" + centences.size());
		viewHolder.tv_content.setText(StringUtil.getShowText(centence.getEnglish()), TextView.BufferType.SPANNABLE);
		viewHolder.tv_chinese.setText(StringUtil.getShowText(centence.getChines()));
		viewHolder.ll_source.setVisibility(View.VISIBLE);
		viewHolder.iv_exemple.setVisibility(View.VISIBLE);
		if (centence.getDone() != null && centence.getDone()){
			viewHolder.tv_content.setText(TextColorUtils.changTextColor(StringUtil.getShowText(centence.getEnglish()),StringUtil.getShowText(centence.getDetails())), TextView.BufferType.SPANNABLE);
			viewHolder.params.width = ScreenUtil.dp2px(StringUtil.timeToDp(StringUtil.getIntegerNotnull(centence.getSeconds()) / 1000),context);
			viewHolder.tv_duration.setText(StringUtil.getShowText(StringUtil.getIntegerNotnull(centence.getSeconds()) / 1000 + 1) + "\"");
			viewHolder.tv_duration.setLayoutParams(viewHolder.params);
			viewHolder.tv_duration.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					New_VoiceUtils.getInstance().startVoiceFile(new File(StringUtil.getShowText(centence.getUrl_current())));
				}
			});
			viewHolder.tv_duration.setVisibility(View.VISIBLE);
		}else {
			viewHolder.tv_duration.setVisibility(View.GONE);
		}



		if (position == expandPosition){
			if (position != preReadposition){
				UIUtils.postDelayed(new Runnable() {
					@Override
					public void run() {
						New_VoiceUtils.getInstance().startVoiceFile(new File(StringUtil.getShowText(centence.getUrl_exemple())));
						preReadposition = position;
					}
				},500);
			}


			viewHolder.ll_main.setBackgroundColor(Color.parseColor("#fbf4da"));
			viewHolder.ll_source.setVisibility(View.VISIBLE);
			viewHolder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
			StringUtil.getEachWord(viewHolder.tv_content,context,callback);
		}else {
			viewHolder.tv_content.setMovementMethod(null);
			viewHolder.ll_main.setBackgroundColor(ColorUtil.getResourceColor(context,R.color.white));
//			viewHolder.ll_source.setVisibility(View.GONE);
		}
		if(video_time != null) {
			viewHolder.ll_source.setVisibility(View.VISIBLE);
		}else {
			viewHolder.ll_source.setVisibility(View.GONE);
		}
		viewHolder.iv_exemple.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				New_VoiceUtils.getInstance().startVoiceFile(new File(StringUtil.getShowText(centence.getUrl_exemple())));
			}
		});
		viewHolder.ll_main.setTag(position);
		viewHolder.ll_main.setOnClickListener(listener);

		return convertView;
	}

	public void setPreReadposition(int position) {
		this.preReadposition = position;
	}

	public void setPosition(int position){
		if (position >= 0 && position < centences.size()){
			this.expandPosition = position;
			notifyDataSetChanged();
		}
	}

	class ViewHolder {
		ImageView iv_exemple;//iv_sort;
		TextView tv_content,tv_duration,tv_chinese,tv_position;//tv_sort,tv_chinese;
		LinearLayout ll_source,ll_main;
		LinearLayout.LayoutParams params;
	}

	public void cleanStatementResult() {
		if (ChivoxConstants.statementResult == null)
			ChivoxConstants.statementResult = new ArrayList<Map<String, List< YunZhiShengResult >>>();
		if (ChivoxConstants.statementResult != null && ChivoxConstants.statementResult.size() > 0) {
			ChivoxConstants.statementResult.clear();
		}
	}


}
