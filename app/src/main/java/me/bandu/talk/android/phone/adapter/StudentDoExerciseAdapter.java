package me.bandu.talk.android.phone.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxConstants;
import com.chivox.bean.YunZhiShengResult;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.ExerciseTextBookChoseBean;
import me.bandu.talk.android.phone.bean.ListViewBean;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.middle.WordsMiddle;
import me.bandu.talk.android.phone.middle.YouDaoMiddle;
import me.bandu.talk.android.phone.myenum.ExerciseEnum;
import me.bandu.talk.android.phone.myenum.ExerciseStateEnum;
import me.bandu.talk.android.phone.utils.Animation3DUtil;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.PopWordUtils;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.TextColorUtils;
import me.bandu.talk.android.phone.utils.TimeUtil;
import me.bandu.talk.android.phone.view.NestRadioGroup;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentDoExerciseAdapter extends BaseAdapter implements BaseActivityIF {
	private Context context;
	private List<CentenceBean> centences;
	private ListViewBean lvBean;
	private ExerciseEnum[] exerciseEnum;
	private ExerciseStateEnum[] exerciseStateEna;
	private ViewGroup.LayoutParams params;
	private View.OnClickListener listener;
	private OnItemMainClickListener itemClickListener;
	private String clickstr = "";
	private View popView;

	public StudentDoExerciseAdapter(Context context, List<CentenceBean> centences, ListViewBean lvBean, ExerciseEnum[] exerciseEnum, ExerciseStateEnum[] exerciseStateEna, View.OnClickListener listener,OnItemMainClickListener itemClickListener){
		this.context = context;
		this.centences = centences;
		this.lvBean = lvBean;
		this.exerciseEnum = exerciseEnum;
		this.exerciseStateEna = exerciseStateEna;
		this.listener = listener;
		this.itemClickListener = itemClickListener;
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
			//viewHolder.iv_sort = (ImageView) convertView.findViewById(R.id.iv_sort);
			viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			viewHolder.tv_duration = (TextView) convertView.findViewById(R.id.tv_duration);
			//viewHolder.tv_sort = (TextView) convertView.findViewById(R.id.tv_sort);
			viewHolder.tv_chinese = (TextView) convertView.findViewById(R.id.tv_chinese);
			viewHolder.ll_source = (LinearLayout) convertView.findViewById(R.id.ll_source);
			viewHolder.ll_main = (LinearLayout) convertView.findViewById(R.id.ll_main);
			viewHolder.tv_position = (TextView) convertView.findViewById(R.id.tv_position);
			//viewHolder.rl_main = (RelativeLayout) convertView.findViewById(R.id.rl_main);
			//viewHolder.rl_anima = (RelativeLayout) convertView.findViewById(R.id.rl_anima);
			viewHolder.mediaPlayer = new MediaPlayer();
			viewHolder.params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
			viewHolder.params.setMargins(ScreenUtil.dp2px(16,context),0,0,0);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_position.setText((position + 1) + "/" + centences.size());
		if (position == lvBean.getPosition()){
			viewHolder.ll_main.setBackgroundColor(Color.parseColor("#fbf4da"));
		}else {
			viewHolder.ll_main.setBackgroundColor(ColorUtil.getResourceColor(context,R.color.white));
		}
		viewHolder.tv_content.setText(StringUtil.getShowText(centence.getEnglish()), TextView.BufferType.SPANNABLE);

		viewHolder.iv_exemple.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				New_VoiceUtils.getInstance().startVoiceLocal(StringUtil.getShowText(centence.getUrl_exemple()),context);
//						File file = new File(StringUtil.getShowText(centence.getUrl_exemple()));
//						VoiceUtils.getInstance().startVoice(file,20000 + position,null);
			}
		});
		viewHolder.tv_chinese.setText(StringUtil.getShowText(centence.getChines()));
		if (exerciseEnum[0] == ExerciseEnum.VIDEOPLAY){
			viewHolder.ll_source.setVisibility(View.GONE);
			viewHolder.iv_exemple.setVisibility(View.GONE);
			if (exerciseStateEna[0] == ExerciseStateEnum.ENGLISEANDCHINESE){
				viewHolder.tv_content.setText(StringUtil.getShowText(centence.getEnglish()), TextView.BufferType.SPANNABLE);
				viewHolder.tv_chinese.setText(StringUtil.getShowText(centence.getChines()));
			}else if(exerciseStateEna[0] == ExerciseStateEnum.ENGLISE){
				viewHolder.tv_content.setText(StringUtil.getShowText(centence.getEnglish()), TextView.BufferType.SPANNABLE);
				viewHolder.tv_chinese.setText("");
			}else if(exerciseStateEna[0] == ExerciseStateEnum.NONE){
				viewHolder.tv_content.setText("", TextView.BufferType.SPANNABLE);
				viewHolder.tv_chinese.setText("");
			}
			getEachWord(viewHolder.tv_content);
			viewHolder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
		}else {
			viewHolder.ll_source.setVisibility(View.VISIBLE);
			viewHolder.iv_exemple.setVisibility(View.VISIBLE);
			if (centence.getDone() != null && centence.getDone()){
				viewHolder.tv_content.setText(TextColorUtils.changTextColor(StringUtil.getShowText(centence.getEnglish()),StringUtil.getShowText(centence.getDetails())), TextView.BufferType.SPANNABLE);
				getEachWord(viewHolder.tv_content);
				viewHolder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
				/*if (centence.getSorce_current() < 55)
					viewHolder.iv_sort.setImageResource(R.mipmap.icon_circle_c);
				else if (centence.getSorce_current() < 85)
					viewHolder.iv_sort.setImageResource(R.mipmap.icon_circle_b);
				else
					viewHolder.iv_sort.setImageResource(R.mipmap.icon_circle_a);*/
				viewHolder.params.width = ScreenUtil.dp2px(StringUtil.timeToDp(StringUtil.getIntegerNotnull(centence.getSeconds()) / 1000),context);
				viewHolder.tv_duration.setText(StringUtil.getShowText(StringUtil.getIntegerNotnull(centence.getSeconds()) / 1000 + 1));
				viewHolder.tv_duration.setLayoutParams(viewHolder.params);
				viewHolder.tv_duration.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						New_VoiceUtils.getInstance().startVoiceFile(new File(StringUtil.getShowText(centence.getUrl_current())));
					}
				});
				viewHolder.tv_duration.setVisibility(View.VISIBLE);
			}else {
				getEachWord(viewHolder.tv_content);
				viewHolder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
				viewHolder.tv_duration.setVisibility(View.GONE);
			}
			viewHolder.iv_exemple.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					New_VoiceUtils.getInstance().startVoiceFile(new File(StringUtil.getShowText(centence.getUrl_exemple())));
				}
			});

			if (position == lvBean.getPosition()){
				viewHolder.ll_source.setVisibility(View.VISIBLE);
			}else {
				viewHolder.ll_source.setVisibility(View.GONE);
			}
		}

		//viewHolder.tv_sort.setText(StringUtil.getShowText(centence.getSorce_current()));
		//final ViewHolder finalViewHolder = viewHolder;
		/*viewHolder.rl_anima.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (finalViewHolder.iv_sort.getVisibility() == View.VISIBLE){
					finalViewHolder.iv_sort.setVisibility(View.GONE);
					finalViewHolder.tv_sort.setVisibility(View.VISIBLE);
				}else {
					finalViewHolder.iv_sort.setVisibility(View.VISIBLE);
					finalViewHolder.tv_sort.setVisibility(View.GONE);
				}
			}
		});*/
		/*if (listener != null)
			viewHolder.tv_content.setOnClickListener(listener);*/
		viewHolder.ll_main.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (itemClickListener != null)
					itemClickListener.onItemMainClickList(position);
			}
		});

		return convertView;
	}

	@Override
	public void successFromMid(Object... obj) {
		if (YouDaoMiddle.YOUDAO == (int)obj[1]){
			if (itemClickListener != null)
				itemClickListener.onVedioStopClick();
			final WordBean wordBean = (WordBean) obj[0];
			popView = PopWordUtils.getInstance().showWorkPop(context, ((Activity)context).findViewById(R.id.rlRoot), wordBean, new PopWordUtils.PopWordViewOnClick() {
				@Override
				public void btnOnClick(WordBean word) {
					//UIUtils.showToastSafe(word.getQuery());
					clickstr = word.getQuery();
					WordsMiddle wordsMiddle = new WordsMiddle(StudentDoExerciseAdapter.this,context);
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
		ImageView iv_exemple;//iv_sort;
		TextView tv_content,tv_duration,tv_chinese,tv_position;//tv_sort,tv_chinese;
		//RelativeLayout ll_source,rl_anima;
		//RelativeLayout rl_main;
		MediaPlayer mediaPlayer;
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
				String str = tv.getText().toString().trim();
				String s = tv.getText().subSequence(tv.getSelectionStart(), tv.getSelectionEnd()).toString();

				SpannableStringBuilder style = new SpannableStringBuilder(str);
				style.setSpan(new ForegroundColorSpan(Color.WHITE),tv.getSelectionStart(),tv.getSelectionEnd(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

				style.setSpan(new BackgroundColorSpan(ColorUtil.getResourceColor(context,R.color.student_title_bg)),tv.getSelectionStart(),tv.getSelectionEnd(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

				tv.setText(style);

				YouDaoMiddle youDaoMiddle = new YouDaoMiddle(StudentDoExerciseAdapter.this,context);
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
