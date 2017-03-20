package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.CheckWorkListActivity;
import me.bandu.talk.android.phone.bean.CheckWorkBean;
import me.bandu.talk.android.phone.bean.CheckWorkBean1;
import me.bandu.talk.android.phone.utils.TimeUtil;

/**
 * 创建者:taoge
 * 时间：2015/11/23 10:21
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/11/23 10:21
 * 修改备注：
 */
public class CheckWorkListAdapter1 extends BaseAdapter {

    private Context context;
    private List<CheckWorkBean1.DataBean.ListBean> mList;

    public CheckWorkListAdapter1(Context context, List<CheckWorkBean1.DataBean.ListBean> mList) {
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
        CheckWorkBean1.DataBean.ListBean work = mList.get(position);
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_check_work_list1, null);
            viewholder.date = (TextView) convertView.findViewById(R.id.date);
            viewholder.week = (TextView) convertView.findViewById(R.id.week);
            viewholder.title = (TextView) convertView.findViewById(R.id.title);
            viewholder.ivCheckWork = (ImageView) convertView.findViewById(R.id.ivCheckWork);
            viewholder.num = (TextView) convertView.findViewById(R.id.num_tv);
            viewholder.ivStatus = (ImageView) convertView.findViewById(R.id.status_img);
            viewholder.iv_icon1 = (ImageView) convertView.findViewById(R.id.iv_icon1);
            viewholder.iv_icon2 = (ImageView) convertView.findViewById(R.id.iv_icon2);
            viewholder.num_tv = (TextView) convertView.findViewById(R.id.num_tv);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        if (work != null) {
            /*if (Long.parseLong(work.getDeadline())*1000 <= System.currentTimeMillis()) {
                //viewholder.status.setText("已截止");
                viewholder.ivStatus.setImageResource(R.mipmap.end);
            } else {
                viewholder.ivStatus.setImageResource(R.mipmap.doing);
                if (work.getCount() == work.getUn_ci_count()) {
                   // viewholder.status.setText("已截止");
                } else {
                    //viewholder.status.setText("进行中");
                }
            }*/
            if (work.getIs_over() == 1) {
                //已截止
                viewholder.ivStatus.setImageResource(R.mipmap.end);
            } else if (work.getIs_over() == 0) {
                //进行中
                viewholder.ivStatus.setImageResource(R.mipmap.doing);
            }

            viewholder.date.setText(TimeUtil.getShortDate(work.getCdate()));
            viewholder.week.setText(work.getCday());
            viewholder.title.setText(work.getTitle());
            //设置图标
//            if(work.getUn_ci_count() != work.getCount()){
            if (work.getDone_total() != work.getTotal()) {
                //还有没完成的
                viewholder.num.setText(work.getDone_total() + "/" + work.getTotal());
                viewholder.iv_icon1.setVisibility(View.VISIBLE);
                viewholder.iv_icon2.setVisibility(View.GONE);
                viewholder.num_tv.setVisibility(View.VISIBLE);
            } else {
                viewholder.iv_icon1.setVisibility(View.GONE);
                viewholder.iv_icon2.setVisibility(View.VISIBLE);
                viewholder.num_tv.setVisibility(View.GONE);
            }
            if (work.getIs_unchecked() == 0) {
                viewholder.ivCheckWork.setVisibility(View.INVISIBLE);
            } else {
                viewholder.ivCheckWork.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView date;
        private TextView week;
        private TextView title;
        private TextView num;
        private ImageView ivCheckWork;
        private ImageView ivStatus;
        private ImageView iv_icon1, iv_icon2;
        private TextView num_tv;
    }
}
