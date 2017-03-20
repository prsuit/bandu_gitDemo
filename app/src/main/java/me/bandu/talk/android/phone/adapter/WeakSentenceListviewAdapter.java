package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONObject;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.MyselfPullateSingleBean;
import me.bandu.talk.android.phone.bean.MyselfWeakSentenceBean;
import me.bandu.talk.android.phone.utils.TextColorUtils;


public class WeakSentenceListviewAdapter extends BaseAdapter {
    private Context context;
    private List<MyselfPullateSingleBean.DataBean.WeakSentencesBean> lv_info;

    public WeakSentenceListviewAdapter(List<MyselfPullateSingleBean.DataBean.WeakSentencesBean> lv_info, Context context) {
        this.lv_info = lv_info;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lv_info.size();
    }

    @Override
    public Object getItem(int position) {
        return lv_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_weak_sentence, null);
            holder.tv_score = (TextView) convertView.findViewById(R.id.tv_score);
            holder.tv_sentence = (TextView) convertView.findViewById(R.id.tv_sentence);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyselfPullateSingleBean.DataBean.WeakSentencesBean sentencesBean = lv_info.get(position);
        holder.tv_score.setText(sentencesBean.getMin_score() + "分");
        String words_score = sentencesBean.getWords_score();
        List<MyselfWeakSentenceBean> been = JSONArray.parseArray(words_score, MyselfWeakSentenceBean.class);

        org.json.JSONArray jsonArray = new org.json.JSONArray();
        String string = "";
        try {
            for (int i = 0; i < been.size(); i++) {
                string +=" "+ been.get(i).getWord();
                JSONObject object = new JSONObject();
                object.put("score", Integer.valueOf(been.get(i).getScore()));
                object.put("word", been.get(i).getWord());
                jsonArray.put(object);
            }
        } catch (Exception e) {
            //TODO
        }
        holder.tv_sentence.setText(TextColorUtils.changTextColor(string, jsonArray.toString()),  TextView.BufferType.SPANNABLE);
        return convertView;
    }

    static class ViewHolder {
       public TextView tv_sentence, tv_score;
    }
}
