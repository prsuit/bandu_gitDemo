package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogBean;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogQuiz;
import me.bandu.talk.android.phone.dao.DaoUtils;

/**
 * 创建者：高楠
 * 时间：on 2015/12/10
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkCommiteDueAdapter extends BaseAdapter implements View.OnClickListener{
    Context context;
    List<HomeWorkCatlogQuiz> listBeen ;
    public WorkCommiteDueAdapter(Context context, HomeWorkCatlogBean bean){
        this.context = context;
        if (DaoUtils.getInstance().isEmpty(bean)){
            listBeen = new ArrayList<>();
        }else {
            listBeen = bean.getData().getHomework().getQuizs();
        }
    }
    public void setBean(HomeWorkCatlogBean bean){
        if (DaoUtils.getInstance().isEmpty(bean)){
            listBeen = new ArrayList<>();
        }else {
            listBeen = bean.getData().getHomework().getQuizs();
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return listBeen==null?0:listBeen.size();
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
        ViewHolder holder = null;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item_work_catalog,null);
            holder = new ViewHolder();
            holder.tv_partname = (TextView) convertView.findViewById(R.id.tv_partname);
            holder.tv_read = (TextView) convertView.findViewById(R.id.tv_read);
            holder.tv_repead = (TextView) convertView.findViewById(R.id.tv_repead);
            holder.tv_recite = (TextView) convertView.findViewById(R.id.tv_recite);
            holder.tv_read.setOnClickListener(this);
            holder.tv_repead.setOnClickListener(this);
            holder.tv_recite.setOnClickListener(this);
            holder.tv_read.setTag(position);
            holder.tv_repead.setTag(position);
            holder.tv_recite.setTag(position);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomeWorkCatlogQuiz bean = listBeen.get(position);
        holder.tv_partname.setText(bean.getName());
        if (bean.getRecite()!=null&&bean.getRecite().getHw_quiz_id()!=0){
            holder.tv_recite.setVisibility(View.VISIBLE);
            if (bean.getRecite().getCount()==bean.getRecite().getTimes()){
                holder.tv_recite.setText("√");
            }else {
                holder.tv_recite.setText(bean.getRecite().getCount()+"/"+bean.getRecite().getTimes());
            }
        }else {
            holder.tv_recite.setVisibility(View.INVISIBLE);
        }
        if (bean.getReading()!=null&&bean.getReading().getHw_quiz_id()!=0){
            holder.tv_read.setVisibility(View.VISIBLE);
            if (bean.getReading().getCount()==bean.getReading().getTimes()){
                holder.tv_read.setText("√");
            }else {
                holder.tv_read.setText(bean.getReading().getCount()+"/"+bean.getReading().getTimes());
            }
        }else {
            holder.tv_read.setVisibility(View.INVISIBLE);;
        }
        if (bean.getRepeat()!=null&&bean.getRepeat().getHw_quiz_id()!=0){
            holder.tv_repead.setVisibility(View.VISIBLE);
            if (bean.getRepeat().getCount()==bean.getRepeat().getTimes()){
                holder.tv_repead.setText("√");
            }else {
                holder.tv_repead.setText(bean.getRepeat().getCount()+"/"+bean.getRepeat().getTimes());
            }
        }else {
            holder.tv_repead.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switch (v.getId()){
            case R.id.tv_read:
                UIUtils.showToastSafe(listBeen.get(position).getReading().getHw_quiz_id()+"");
                break;
            case R.id.tv_repead:
                UIUtils.showToastSafe(listBeen.get(position).getRepeat().getHw_quiz_id()+"");
                break;
            case R.id.tv_recite:
                UIUtils.showToastSafe(listBeen.get(position).getRecite().getHw_quiz_id()+"");
                break;
        }
    }

    public class ViewHolder{
        TextView tv_read,tv_repead,tv_recite,tv_partname;
    }
}
