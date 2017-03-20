package me.bandu.talk.android.phone.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxConstants;
import com.chivox.bean.YunZhiShengResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.ListViewBean;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.middle.WordsMiddle;
import me.bandu.talk.android.phone.middle.YouDaoMiddle;
import me.bandu.talk.android.phone.myenum.ExerciseEnum;
import me.bandu.talk.android.phone.myenum.ExerciseStateEnum;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.PopWordUtils;
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
public class StudentVideoAdapter_311 extends BaseAdapter implements BaseActivityIF {
	private Context context;
	private List<CentenceBean> centences;
	private ExerciseStateEnum exerciseStateEna;
	private View.OnClickListener listener;
	private View popView;
	private String clickstr = "";
	private int position;

	public StudentVideoAdapter_311(Context context, List<CentenceBean> centences, View.OnClickListener listener){
		this.context = context;
		this.centences = centences;
		this.listener = listener;
		this.exerciseStateEna = ExerciseStateEnum.ENGLISEANDCHINESE;
	}

	public void setExerciseState(ExerciseStateEnum exerciseStateEna){
		this.exerciseStateEna = exerciseStateEna;
	}

	public void setPosition(int position){
		if (position >= 0 && position < centences.size()){
			this.position = position;
		}
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
		if (this.position == position){
			viewHolder.ll_main.setBackgroundColor(Color.parseColor("#fbf4da"));
		}else {
			viewHolder.ll_main.setBackgroundColor(ColorUtil.getResourceColor(context,R.color.white));
		}
		viewHolder.tv_content.setText(StringUtil.getShowText(centence.getEnglish()), TextView.BufferType.SPANNABLE);

		viewHolder.tv_chinese.setText(StringUtil.getShowText(centence.getChines()));
			if (exerciseStateEna == ExerciseStateEnum.ENGLISEANDCHINESE){
				viewHolder.tv_content.setText(StringUtil.getShowText(centence.getEnglish()), TextView.BufferType.SPANNABLE);
				viewHolder.tv_chinese.setText(StringUtil.getShowText(centence.getChines()));
			}else if(exerciseStateEna == ExerciseStateEnum.ENGLISE){
				viewHolder.tv_content.setText(StringUtil.getShowText(centence.getEnglish()), TextView.BufferType.SPANNABLE);
				viewHolder.tv_chinese.setText("");
			}else if(exerciseStateEna == ExerciseStateEnum.NONE){
				viewHolder.tv_content.setText("", TextView.BufferType.SPANNABLE);
				viewHolder.tv_chinese.setText("");
			}
			getEachWord(viewHolder.tv_content);
			viewHolder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
		viewHolder.ll_main.setTag(position);
		viewHolder.ll_main.setOnClickListener(listener);
		return convertView;
	}

	@Override
	public void successFromMid(Object... obj) {
		if (YouDaoMiddle.YOUDAO == (int)obj[1]){
			final WordBean wordBean = (WordBean) obj[0];
			popView = PopWordUtils.getInstance().showWorkPop(context, ((Activity)context).findViewById(R.id.rlRoot), wordBean, new PopWordUtils.PopWordViewOnClick() {
				@Override
				public void btnOnClick(WordBean word) {
					//UIUtils.showToastSafe(word.getQuery());
					clickstr = word.getQuery();
					WordsMiddle wordsMiddle = new WordsMiddle(StudentVideoAdapter_311.this,context);
					wordsMiddle.createWord(word.getQuery(),word);
				}

				@Override
				public void imgOnClick(WordBean word) {
					//UIUtils.showToastSafe(word.getQuery());
					New_VoiceUtils.getInstance().startVoiceNet("http://dict.youdao.com/dictvoice?audio="+word.getQuery());
				}
			}, new PopupWindow.OnDismissListener() {
				@Override
				public void onDismiss() {
					notifyDataSetChanged();
				}
			});
		}else if(WordsMiddle.CREATE_WORDS == (int)obj[1]){
			BaseBean baseBean = (BaseBean) obj[0];
			UIUtils.showToastSafe(baseBean.getMsg());
			if (popView != null){
				Button btn = (Button) popView.findViewById(R.id.btnAddWord);
				btn.setText("已添加");

				btn.setBackgroundResource(R.drawable.corners_gray);
				//btn.setBackgroundColor(ColorUtil.getResourceColor(context,R.color.gray));
				btn.setTextColor(ColorUtil.getResourceColor(context,R.color.white));
				btn.setFocusable(false);
				btn.setClickable(false);
			}
		}
	}

	@Override
	public void failedFrom(Object... obj) {
		notifyDataSetChanged();
	}

	@Override
	public void onSuccess(Object result, int requestCode) {

	}

	@Override
	public void onFailed(int requestCode) {

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

	public interface OnItemMainClickListener{
		public void onItemMainClickList(int position);
		public void onVedioStopClick();
	}

	public void getEachWord(TextView textView) {
		Spannable spans = (Spannable) textView.getText();
		Integer[] indices = getIndices(textView.getText().toString().trim() + " ", ' ');
		int start = 0;
		int end = 0;
		for (int i = 0; i < indices.length; i++) {
			ClickableSpan clickSpan = getClickableSpan();
			end = (i < indices.length ? indices[i] : spans.length());
			spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			start = end + 1;
		}
//         改变选中文本的高亮颜色
		//textView.setHighlightColor(ColorUtil.getResourceColor(context,R.color.student_title_bg));
//        textView.setText(spans);
	}

	private ClickableSpan getClickableSpan() {
		return new ClickableSpan() {
			@Override
			public void onClick(View widget) {
				final TextView tv = (TextView) widget;
				if (listener != null)
					listener.onClick(tv);
				String str = tv.getText().toString().trim();
				String s = tv.getText().subSequence(tv.getSelectionStart(), tv.getSelectionEnd()).toString();

				SpannableStringBuilder style = new SpannableStringBuilder(str);
				style.setSpan(new ForegroundColorSpan(Color.WHITE),tv.getSelectionStart(),tv.getSelectionEnd(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

				style.setSpan(new BackgroundColorSpan(ColorUtil.getResourceColor(context,R.color.student_title_bg)),tv.getSelectionStart(),tv.getSelectionEnd(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

				tv.setText(style);

				YouDaoMiddle youDaoMiddle = new YouDaoMiddle(StudentVideoAdapter_311.this,context);
				youDaoMiddle.getWord(StringUtil.quBiaoDian(s));
			}

			@Override
			public void updateDrawState(TextPaint ds) {
				//ds.setColor(ColorUtil.getResourceColor(context,R.color.text_gray));
				ds.setUnderlineText(false);
			}
		};
	}

	public Integer[] getIndices(String ss, char c) {
		int pos = ss.indexOf(c, 0);
		List<Integer> integers = new ArrayList<>();
		while (pos != -1) {
			integers.add(pos);
			pos = ss.indexOf(c, pos + 1);
		}
		return integers.toArray(new Integer[0]);
	}
}
