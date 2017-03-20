package me.bandu.talk.android.phone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.DFHT.utils.UIUtils;
import java.util.List;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.LessonInfoEntity;
import me.bandu.talk.android.phone.bean.Quiz;
import me.bandu.talk.android.phone.bean.WorkDataBean;
import me.bandu.talk.android.phone.viewholder.CapterThreeHolder;

/**
 * 创建者：高楠
 * 时间：on 2016/3/29
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkQuizAdapter extends BaseAdapter{
    private Context context;
    private WorkDataBean dataBean;
    private List<LessonInfoEntity> list;
    public CreatWorkQuizAdapter(Context context, List<LessonInfoEntity> list,WorkDataBean dataBean){
        this.context = context;
        this.list = list;
        this.dataBean = dataBean;
    }
    public void setDataBean(WorkDataBean dataBean){
        this.dataBean = dataBean;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CapterThreeHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_t,null);
            holder = new CapterThreeHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (CapterThreeHolder) convertView.getTag();
        }
        holder.tv.setText(list.get(position).getQuiz_name());
        holder.tv_sentence_count.setVisibility(View.VISIBLE);
        holder.tv_sentence_count.setText(list.get(position).getSentences_count()+"句");
        return convertView;
    }

    public Quiz getQuiz(int position){
        if (list==null||list.size()<=position){
            return null;
        }else {
            Quiz quiz = new Quiz();
            quiz.setQuiz_id(list.get(position).getQuiz_id());
            quiz.setQuiz_name(list.get(position).getQuiz_name());
            quiz.setSentence_num(list.get(position).getSentences_count());
            return quiz;
        }
    }
}
