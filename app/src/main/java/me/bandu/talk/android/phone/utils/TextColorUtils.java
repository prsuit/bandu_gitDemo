package me.bandu.talk.android.phone.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.DFHT.base.AIRecorderDetails;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：高楠
 * 时间：on 2016/1/7
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TextColorUtils {
    public static SpannableStringBuilder changTextColor(String string,String word_score){
        //[{word:'hello',score:34},{word:'world',score}:45]
        List<AIRecorderDetails> data = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(word_score);
            for (int i = 0 ;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                AIRecorderDetails air = new AIRecorderDetails();
                air.setCharStr(jsonObject.getString("word"));
                air.setScore(jsonObject.getInt("score"));
                data.add(air);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data.size()==0){
            return new SpannableStringBuilder(string);
        }else {
            string = string.trim();
            SpannableStringBuilder builder = new SpannableStringBuilder(string);
            ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
            //默认为红色字体
            builder.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            int start = 0;
            string = string.toLowerCase();
            for (int i = 0; i < data.size(); i++) {
                try {
                    String s = data.get(i).getCharStr().toLowerCase();
                    int score = data.get(i).getScore();
                    start = string.indexOf(s, start);
                    if (score<55) {
                        span = new ForegroundColorSpan(Color.RED);
                    }else if(score>=85){
                        span = new ForegroundColorSpan(Color.GREEN);
                    }else {
                        span = new ForegroundColorSpan(Color.BLUE);
                    }
                    builder.setSpan(span, start, start+s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    for (int j = start+s.length(); j < string.length(); j++) {
                        if (string.substring(j, j+1).matches("[a-zA-Z0-9]")) {
                            start = j;
                            break;
                        }else {
                            builder.setSpan(span, start, j+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            data.clear();
            return builder;
        }

    }
    public static SpannableStringBuilder changTextColor(String string,List<AIRecorderDetails> data){
        //[{word:'hello',score:34},{word:'world',score}:45]
        if (data.size()==0){
            return new SpannableStringBuilder(string);
        }else {
            int start = 0;
            string = string.trim();
            SpannableStringBuilder builder = new SpannableStringBuilder(string);
            string = string.toLowerCase();
            for (int i = 0; i < data.size(); i++) {
                try {
                    String s = data.get(i).getCharStr();
                    int score = data.get(i).getScore();
                    start = string.indexOf(s, start);
                    ForegroundColorSpan span;
                    if (score<55) {
                        span = new ForegroundColorSpan(Color.RED);
                    }else if(score>=85){
                        span = new ForegroundColorSpan(Color.GREEN);
                    }else {
                        span = new ForegroundColorSpan(Color.BLACK);
                    }
                    builder.setSpan(span, start, start+s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    for (int j = start+s.length(); j < string.length(); j++) {
                        if (string.substring(j, j+1).matches("[a-zA-Z0-9]")) {
                            start = j;
                            break;
                        }else {
                            builder.setSpan(span, start, j+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            data.clear();
            return builder;
        }

    }

}
