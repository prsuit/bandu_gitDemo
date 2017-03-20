package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.WorkStatisticsBean;

public class WorkStatisticsListviewAdapter extends BaseAdapter {

    private Context context;
    private List<WorkStatisticsBean.DataBean.WeakSentencesBean> weak_sentences;
    public WorkStatisticsListviewAdapter(Context context,List<WorkStatisticsBean.DataBean.WeakSentencesBean> weak_sentences) {
        this.weak_sentences = weak_sentences;
        this.context = context;
    }

    @Override
    public int getCount() {
        return weak_sentences.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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

        holder.tv_score.setText(weak_sentences.get(position).getAvg_score()+"分");
        // 解决新题型的答案显示
        String answer = weak_sentences.get(position).getAnswer();
        if (null != answer){
            holder.tv_sentence.setText(answer);
        } else {
            holder.tv_sentence.setText(weak_sentences.get(position).getEn_content());
        }
        return convertView;
    }
    static class ViewHolder {
        public TextView tv_sentence, tv_score;
    }
}
