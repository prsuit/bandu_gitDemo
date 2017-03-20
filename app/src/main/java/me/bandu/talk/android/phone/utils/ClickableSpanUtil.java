package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：gaoye
 * 时间：2016/3/21 16:00
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ClickableSpanUtil extends ClickableSpan {
    private WordClickListener listener;
    private Context context;

    public ClickableSpanUtil(WordClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onClick(View widget) {
        final TextView tv = (TextView) widget;
        String str = tv.getText().toString().trim();
        String s = tv.getText().subSequence(tv.getSelectionStart(), tv.getSelectionEnd()).toString();
        s = StringUtil.quBiaoDian(s);
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(Color.WHITE),tv.getSelectionStart(),tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new BackgroundColorSpan(ColorUtil.getResourceColor(context, R.color.student_title_bg)),tv.getSelectionStart(),tv.getSelectionEnd(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(style);
        if (listener != null)
            listener.wordClick(s);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }

    public interface WordClickListener{
        public void wordClick(String word);
    }
}
