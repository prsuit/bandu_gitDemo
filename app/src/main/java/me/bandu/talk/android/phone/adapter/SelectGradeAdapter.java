package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.GetGradesBean;
import me.bandu.talk.android.phone.model.Grade;

/**
 * 创建者：gaoye
 * 时间：2015/11/25  09:48
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SelectGradeAdapter extends BaseAdapter{
    private List<GetGradesBean.DataEntity.ListEntity> grades;
    private Context context;

    public SelectGradeAdapter(Context context,List<GetGradesBean.DataEntity.ListEntity> grades){
        this.context = context;
        this.grades = grades;
    }
    @Override
    public int getCount() {
        return grades.size();
    }

    @Override
    public Object getItem(int position) {
        return grades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        GetGradesBean.DataEntity.ListEntity grade = grades.get(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_selectgrade_item, null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_gradename);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv.setText(grade.getGrade_name());

        return convertView;
    }


    class ViewHolder {
        TextView tv;
    }
}
