package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.SingleStatisBean;

/**
 * 创建者:taoge
 * 时间：2015/11/24 14:21
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/11/24 14:21
 * 修改备注：
 */
public class WorkStatisticsDetailListAdapter extends BaseAdapter {

    private Context context;
    private List<SingleStatisBean.DataEntity.SentenceListEntity> mList;
    private SingleStatisBean.DataEntity.StatEntity question;

    public WorkStatisticsDetailListAdapter(Context context, List<SingleStatisBean.DataEntity.
            SentenceListEntity> mList,SingleStatisBean.DataEntity.StatEntity question) {
        this.context = context;
        this.mList = mList;
        this.question = question;
    }

    public SingleStatisBean.DataEntity.StatEntity getQuestion() {
        return question;
    }

    public void setQuestion(SingleStatisBean.DataEntity.StatEntity question) {
        this.question = question;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        SingleStatisBean.DataEntity.SentenceListEntity info = mList.get(position);
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_work_statistics_detail_list, null);
            viewholder.title = (TextView) convertView.findViewById(R.id.title);
            viewholder.radio = (TextView) convertView.findViewById(R.id.radio);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        if (info != null) {
            viewholder.title.setText(info.getEn());
            viewholder.radio.setText(question.getA()+"/"+question.getDone());
        }

        return convertView;
    }


    public class ViewHolder{
        private TextView title;
        private TextView radio;
    }
}
