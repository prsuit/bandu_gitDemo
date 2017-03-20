package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.DFHT.base.engine.BaseActivityIF;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.middle.WordsMiddle;

/**
 * 创建者：gaoye
 * 时间：2015/12/28 15:32
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StringUtil {
    public static String getShowText(String str){
        return getShowText(str,false);
    }
    public static String getShowText(Boolean bool){
        if (bool == null)
            return "";
        else
            return getShowText(bool.toString());
    }
    public static String getShowText(Integer integ){
        if (integ == null)
            return "";
        else
            return getShowText(integ.toString());
    }
    public static int getIntegerNotnull(Integer integ){
        if (integ == null)
            return 0;
        else
            return integ;
    }
    public static long getLongNotnull(Long l){
        if (l == null)
            return 0;
        else
            return l;
    }
    public static String getShowText(Long l){
        if (l == null)
            return "";
        else
            return getShowText(l.toString());
    }

    public static String getShowText(String str,boolean nonull){
        if (TextUtils.isEmpty(str)){
            return "";
        }else {
            if (nonull && "null".equals(str))
                return "";
            else
                return str;
        }
    }

    public static String getResourceString(Context context,int resourceId){
        return getShowText(context.getResources().getString(resourceId));
    }

    public static int timeToDp(Integer integer){
        int length = 0;
        int time = getIntegerNotnull(integer);
        if (time == 0)
            time = 1;
        if (time < 0)
            time = 1;
        if (time > 5)
            time = 5;
        length = time * 40;
        return length;
    }


    public static String quBiaoDian(String str){
        if (TextUtils.isEmpty(str))
            return str;
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i<chars.length;i++){
            if ((chars[i] <= 'Z' && chars[i] >= 'A')
                    || (chars[i] <= 'z' && chars[i] >= 'a')) {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }

    public static void getEachWord(TextView textView,Context context, BaseActivityIF callback) {
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spans = (Spannable) textView.getText();
        Integer[] indices = getIndices(textView.getText().toString().trim() + " ", ' ');
        int start = 0;
        int end = 0;
        for (int i = 0; i < indices.length; i++) {
            ClickableSpan clickSpan = getClickableSpan(context,callback);
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
    }
    public static void getEachWord(TextView textView, ClickableSpan clickSpan) {
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spans = (Spannable) textView.getText();
        Integer[] indices = getIndices(textView.getText().toString().trim() + " ", ' ');
        int start = 0;
        int end = 0;
        for (int i = 0; i < indices.length; i++) {
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
    }

    private static ClickableSpan getClickableSpan(final Context context, final BaseActivityIF callback) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                final TextView tv = (TextView) widget;
                String str = tv.getText().toString().trim();
                if (tv.getSelectionEnd() > tv.getSelectionStart()){
                    String s = tv.getText().subSequence(tv.getSelectionStart(), tv.getSelectionEnd()).toString();
                    s = quBiaoDian(s);
                    SpannableStringBuilder style = new SpannableStringBuilder(str);
                    style.setSpan(new ForegroundColorSpan(Color.WHITE),tv.getSelectionStart(),tv.getSelectionEnd(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                    style.setSpan(new BackgroundColorSpan(ColorUtil.getResourceColor(context, R.color.student_title_bg)),tv.getSelectionStart(),tv.getSelectionEnd(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                    tv.setText(style);

                    WordsMiddle wordsMiddle = new WordsMiddle(callback,context);
                    wordsMiddle.getWord(s);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        };
    }

    public static Integer[] getIndices(String ss, char c) {
        int pos = ss.indexOf(c, 0);
        List<Integer> integers = new ArrayList<>();
        while (pos != -1) {
            integers.add(pos);
            pos = ss.indexOf(c, pos + 1);
        }
        return integers.toArray(new Integer[0]);
    }
}
