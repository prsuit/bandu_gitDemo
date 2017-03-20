package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.CheckWorkBean;
import me.bandu.talk.android.phone.utils.TimeUtil;

/**
 * 创建者:taoge
 * 时间：2015/11/23 10:21
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/11/23 10:21
 * 修改备注：
 */
public class CheckWorkListAdapter extends BaseAdapter {

    private Context context;
    private List<CheckWorkBean.DataEntity.ListEntity> mList;

    public CheckWorkListAdapter(Context context, List<CheckWorkBean.DataEntity.ListEntity> mList) {
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
        CheckWorkBean.DataEntity.ListEntity work = mList.get(position);
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_check_work_list, null);
            viewholder.date = (TextView) convertView.findViewById(R.id.date);
            viewholder.week = (TextView) convertView.findViewById(R.id.week);
            viewholder.title = (TextView) convertView.findViewById(R.id.title);
            viewholder.timeTv = (TextView) convertView.findViewById(R.id.time_tv);
            viewholder.time = (CountdownView) convertView.findViewById(R.id.time);
            viewholder.toCheck = (TextView) convertView.findViewById(R.id.to_check);
            viewholder.ivCheckWork = (ImageView) convertView.findViewById(R.id.ivCheckWork);
            viewholder.num = (TextView) convertView.findViewById(R.id.num);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        if (work != null) {
            if (Long.parseLong(work.getDeadline())*1000 <= System.currentTimeMillis()) {
                viewholder.timeTv.setText("未交"+work.getUn_ci_count());
                viewholder.time.setVisibility(View.GONE);
            } else {
                viewholder.time.setVisibility(View.VISIBLE);
                viewholder.timeTv.setText("倒计时:");
                long mills =  Long.parseLong(work.getDeadline())*1000-System.currentTimeMillis();
                viewholder.time.start(mills);
                if (mills > 24 * 60 * 60*1000) {
                    //如果大于一天则显示天
                    viewholder.time.customTimeShow(true, true, true, true, false);
                } else {
                    //小于一天不显示天
                    viewholder.time.customTimeShow(false, true, true, true, false);
                }
            }
            viewholder.toCheck.setText("待查");
            viewholder.date.setText(TimeUtil.getShortDate(work.getCdate()));
            viewholder.week.setText(work.getCday());
            viewholder.title.setText(work.getTitle());
            viewholder.num.setText(work.getUn_ck_count()+"");
            if (work.getUn_ck_count()==0){
                viewholder.ivCheckWork.setVisibility(View.INVISIBLE);
            }else {
                viewholder.ivCheckWork.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }


    public class ViewHolder{
        private TextView date;
        private TextView week;
        private TextView title;
        private TextView timeTv;
        private CountdownView time;
        private TextView toCheck;
        private TextView num;
        private ImageView ivCheckWork;
    }
}
