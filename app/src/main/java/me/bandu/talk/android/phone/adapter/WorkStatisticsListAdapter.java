package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.WorkStatisBean;

/**
 * 创建者:taoge
 * 时间：2015/11/24 14:21
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/11/24 14:21
 * 修改备注：
 */
public class WorkStatisticsListAdapter extends BaseAdapter {

    private Context context;
    private List<WorkStatisBean.DataEntity.QuizListEntity> mList;

    public WorkStatisticsListAdapter(Context context, List<WorkStatisBean.DataEntity.QuizListEntity> mList) {
        this.context = context;
        this.mList = mList;
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

        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_work_statistics_list, null);
            viewholder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.title.setText(mList.get(position).getQuiz_name());

        return convertView;
    }


    public class ViewHolder{
        private TextView title;
    }
}
