package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.RankActivity;
import me.bandu.talk.android.phone.bean.RankBean;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.WorkCatalogUtils;
import me.bandu.talk.android.phone.view.CircleImageView;


public class RankListviewAdapter extends BaseAdapter {
    //列表
    int flag;
    private List<RankBean.DataBean.StudentListBean> student_list;
    private Context context;

    public RankListviewAdapter(List<RankBean.DataBean.StudentListBean> student_list, Context context, int flag) {
        this.student_list = student_list;
        this.flag = flag;
        this.context = context;
    }

    @Override
    public int getCount() {
        return student_list.size();
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
            convertView = convertView.inflate(context, R.layout.item_rank, null);
            holder.civ_head = (CircleImageView) convertView.findViewById(R.id.civ_head);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.tv_score = (TextView) convertView.findViewById(R.id.tv_score);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RankBean.DataBean.StudentListBean bean = student_list.get(position);
        holder.tv_name.setText(bean.getName() + "");
        holder.tv_number.setText(position + 1 + "");
        if (flag == 1) {
            //排行榜
            holder.tv_score.setText(bean.getScore() + "分");
        } else if (flag == 2) {
            //修行榜
            holder.tv_score.setText("时长" + WorkCatalogUtils.getSpeendTime(String.valueOf(bean.getTotal_time())));
        }
        holder.civ_head.setBackgroundResource(R.mipmap.default_avatar);
        ImageLoader.getInstance().displayImage(bean.getAvatar(), holder.civ_head);
        return convertView;
    }

    static class ViewHolder {
        public TextView tv_number, tv_name, tv_score;
        public CircleImageView civ_head;
    }
}
