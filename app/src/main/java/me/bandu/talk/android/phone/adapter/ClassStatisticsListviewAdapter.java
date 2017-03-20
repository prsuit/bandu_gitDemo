package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.ClassStatisticsBean;
import me.bandu.talk.android.phone.utils.WorkCatalogUtils;
import me.bandu.talk.android.phone.view.CircleImageView;


public class ClassStatisticsListviewAdapter extends BaseAdapter {
    private Context context;
    private List<ClassStatisticsBean.DataBean.ListBean> list;

    public ClassStatisticsListviewAdapter(Context context, List<ClassStatisticsBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
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
            convertView = View.inflate(context, R.layout.item_statistics_class, null);
            holder.tv_work_count = (TextView) convertView.findViewById(R.id.tv_work_count);
            holder.tv_work_score_avg = (TextView) convertView.findViewById(R.id.tv_work_score_avg);
            holder.tv_work_time_avg = (TextView) convertView.findViewById(R.id.tv_work_time_avg);
            holder.class_icon = (CircleImageView) convertView.findViewById(R.id.class_icon);
            holder.tv_circle_text = (TextView) convertView.findViewById(R.id.tv_circle_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ClassStatisticsBean.DataBean.ListBean data = list.get(position);
        holder.tv_work_count.setText(data.getTotal() + "");
        holder.tv_work_score_avg.setText(data.getAvg_score() + "");
        int avg_spend = data.getAvg_spend();
        String speendTimeStr = WorkCatalogUtils.getSpeendTimeStr(String.valueOf(avg_spend));
//        if(!TextUtils.isEmpty(speendTimeStr))
        holder.tv_work_time_avg.setText(!TextUtils.isEmpty(speendTimeStr) ? speendTimeStr : "0");
        LogUtils.e("平均用时 = " + WorkCatalogUtils.getSpeendTimeStr(String.valueOf(avg_spend)));
        holder.tv_circle_text.setText(data.getName() + "");
        if (position % 2 == 0) {
            holder.class_icon.setBackgroundResource(R.drawable.class_bg_1);
        } else {
            holder.class_icon.setBackgroundResource(R.drawable.class_bg_2);
        }
        return convertView;
    }

    static class ViewHolder {
        CircleImageView class_icon;
        TextView tv_work_count;
        TextView tv_work_score_avg;
        TextView tv_work_time_avg;
        TextView tv_circle_text;

    }
}
