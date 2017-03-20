package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.SeeWorkBean;
import me.bandu.talk.android.phone.utils.ImageLoaderOption;

/**
 * 创建者:taoge
 * 时间：2015/11/23 14:21
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/11/23 14:21
 * 修改备注：
 */
public class SeeWorkListAdapter extends BaseAdapter {

    private Context context;
    private List<SeeWorkBean.DataEntity.DoneListEntity> mList;

    public SeeWorkListAdapter(Context context, List<SeeWorkBean.DataEntity.DoneListEntity> mList) {
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
        SeeWorkBean.DataEntity.DoneListEntity job = mList.get(position);
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_see_work_list, null);
            viewholder.header = (ImageView) convertView.findViewById(R.id.header);
            viewholder.name = (TextView) convertView.findViewById(R.id.name);
            viewholder.score = (TextView) convertView.findViewById(R.id.score);
            viewholder.write = (TextView) convertView.findViewById(R.id.write_tv);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        if (job != null) {
            viewholder.score.setText(job.getScore()+"分");
            if (job.getScore() >= 85) {
                viewholder.score.setTextColor(context.getResources().getColor(R.color.green_score));
            } else if (job.getScore() <= 84 && job.getScore() >= 55) {
                viewholder.score.setTextColor(context.getResources().getColor(R.color.blue_score));
            } else {
                viewholder.score.setTextColor(context.getResources().getColor(R.color.red_score));
            }
            viewholder.name.setText(job.getStu_name());
            ImageLoader.getInstance().displayImage(job.getAvatar(),viewholder.header, ImageLoaderOption.getOptions());
            if (job.getEvaluated() == 0) {
                viewholder.write.setText("未评价");
            } else {
                viewholder.write.setText("已评价");
            }
        }

        return convertView;
    }


    public class ViewHolder{
        private ImageView header;
        private TextView name;
        private TextView score;
        private TextView write;
    }

    public void setmList(List<SeeWorkBean.DataEntity.DoneListEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
    public String getStuJobId(int position){
        return mList.get(position).getStu_job_id();
    }
    public boolean isEvalued(){
        if (mList!=null&&mList.size()!=0){
            for (int i = 0 ; i < mList.size() ; i++){
                if (mList.get(i).getEvaluated()==0){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isEvaluated(int position){
        if (mList!=null&&mList.size()!=0){
            return mList.get(position).getEvaluated()==1;
        }
        return false;
    }
}
