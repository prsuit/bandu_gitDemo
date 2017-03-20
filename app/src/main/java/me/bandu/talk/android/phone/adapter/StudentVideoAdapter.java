package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DFHT.base.engine.BaseActivityIF;
import com.chivox.ChivoxConstants;
import com.chivox.bean.YunZhiShengResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.myenum.ExerciseStateEnum;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.StringUtil;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentVideoAdapter extends BaseAdapter {
	private Context context;
	private List<CentenceBean> centences;
	private ExerciseStateEnum exerciseState;
	private View.OnClickListener listener;
	private String clickstr = "";
	private int position;
	private BaseActivityIF baseActivityIF;
	//private ClickableSpanUtil clickableSpan;
	//private ClickableSpanUtil.WordClickListener wordClickListener;

	public StudentVideoAdapter(Context context, List<CentenceBean> centences, View.OnClickListener listener,BaseActivityIF baseActivityIF){
		this.context = context;
		this.centences = centences;
		this.listener = listener;
		this.baseActivityIF = baseActivityIF;
		this.exerciseState = ExerciseStateEnum.ENGLISEANDCHINESE;
	}

	public void setExerciseState(ExerciseStateEnum exerciseState){
		this.exerciseState = exerciseState;
	}

	public ExerciseStateEnum getExerciseState(){
		return exerciseState;
	}

	public void setPosition(int position){
		this.position = position;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_video_list_item,null);
			viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			viewHolder.tv_chinese = (TextView) convertView.findViewById(R.id.tv_chinese);
			viewHolder.ll_main = (LinearLayout) convertView.findViewById(R.id.ll_main);
			viewHolder.tv_position = (TextView) convertView.findViewById(R.id.tv_position);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_position.setText((position + 1) + "/" + centences.size());

			if (exerciseState == ExerciseStateEnum.ENGLISEANDCHINESE){
				viewHolder.tv_content.setText(StringUtil.getShowText(centence.getEnglish()), TextView.BufferType.SPANNABLE);
				viewHolder.tv_chinese.setText(StringUtil.getShowText(centence.getChines()));
			}else if(exerciseState == ExerciseStateEnum.ENGLISE){
				viewHolder.tv_content.setText(StringUtil.getShowText(centence.getEnglish()), TextView.BufferType.SPANNABLE);
				viewHolder.tv_chinese.setText("");
			}else if(exerciseState == ExerciseStateEnum.NONE){
				viewHolder.tv_content.setText("", TextView.BufferType.SPANNABLE);
				viewHolder.tv_chinese.setText("");
			}

		if (this.position == position){
			viewHolder.ll_main.setBackgroundColor(Color.parseColor("#fbf4da"));
			viewHolder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
			StringUtil.getEachWord(viewHolder.tv_content,context,baseActivityIF);
		}else {
			viewHolder.tv_content.setMovementMethod(null);
			viewHolder.ll_main.setBackgroundColor(ColorUtil.getResourceColor(context,R.color.white));
		}
		viewHolder.ll_main.setTag(position);
		viewHolder.ll_main.setOnClickListener(listener);
		return convertView;
	}

	class ViewHolder {
		TextView tv_content,tv_chinese,tv_position;//tv_sort,tv_chinese;
		LinearLayout ll_main;
	}

	public void cleanStatementResult() {
		if (ChivoxConstants.statementResult == null)
			ChivoxConstants.statementResult = new ArrayList<Map<String, List< YunZhiShengResult >>>();
		if (ChivoxConstants.statementResult != null && ChivoxConstants.statementResult.size() > 0) {
			ChivoxConstants.statementResult.clear();
		}
	}
}
